package org.CreadoresProgram.minetest.packetlib.udp.luantimt;


import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.*;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import io.netty.channel.*;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutException;
import io.netty.handler.timeout.WriteTimeoutHandler;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.*;

public abstract class UdpSession extends SimpleChannelInboundHandler<Packet> implements Session {
    protected String host;
    protected int port;
    private PacketProtocol protocol;

    private int compressionThreshold = -1;
    private int connectTimeout = 30;
    private int readTimeout = 30;
    private int writeTimeout = 0;

    private Map<String, Object> flags = new HashMap<>();
    private List<SessionListener> listeners = new CopyOnWriteArrayList<>();

    private Channel channel;
    private InetSocketAddress remoteAddress;
    protected boolean disconnected = false;

    private BlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
    private Thread packetHandleThread;

    public UdpSession(String host, int port, PacketProtocol protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.remoteAddress = new InetSocketAddress(host, port);
    }

    @Override
    public void connect() { this.connect(true); }

    @Override
    public abstract void connect(boolean wait);

    @Override
    public String getHost() { return this.host; }

    @Override
    public int getPort() { return this.port; }

    @Override
    public SocketAddress getLocalAddress() {
        return this.channel != null ? this.channel.localAddress() : null;
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    @Override
    public PacketProtocol getPacketProtocol() { return this.protocol; }

    @Override
    public Map<String, Object> getFlags() { return Collections.unmodifiableMap(this.flags); }

    @Override
    public boolean hasFlag(String key) { return this.flags.containsKey(key); }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getFlag(String key) { return this.getFlag(key, null); }

    @Override
    public <T> T getFlag(String key, T def) {
        Object value = this.flags.get(key);
        return value == null ? def : (T) value;
    }

    @Override
    public void setFlag(String key, Object value) { this.flags.put(key, value); }

    @Override
    public List<SessionListener> getListeners() { return Collections.unmodifiableList(this.listeners); }

    @Override
    public void addListener(SessionListener listener) { this.listeners.add(listener); }

    @Override
    public void removeListener(SessionListener listener) { this.listeners.remove(listener); }

    @Override
    public void callEvent(SessionEvent event) {
        try {
            for (SessionListener listener : this.listeners) {
                event.call(listener);
            }
        } catch (Throwable t) {
            exceptionCaught(null, t);
        }
    }

    @Override
    public int getCompressionThreshold() { return this.compressionThreshold; }

    @Override
    public void setCompressionThreshold(int threshold) {
        this.compressionThreshold = threshold;
        if (this.channel != null) {
            ChannelPipeline pipeline = this.channel.pipeline();
            if (this.compressionThreshold >= 0) {
                if (pipeline.get("compression") == null) {
                    pipeline.addBefore("codec", "compression", new UdpPacketCompression(this));
                }
            } else if (pipeline.get("compression") != null) {
                pipeline.remove("compression");
            }
        }
    }

    @Override
    public int getConnectTimeout() { return this.connectTimeout; }

    @Override
    public void setConnectTimeout(int timeout) { this.connectTimeout = timeout; }

    @Override
    public int getReadTimeout() { return this.readTimeout; }

    @Override
    public void setReadTimeout(int timeout) {
        this.readTimeout = timeout;
        this.refreshReadTimeoutHandler();
    }

    @Override
    public int getWriteTimeout() { return this.writeTimeout; }

    @Override
    public void setWriteTimeout(int timeout) {
        this.writeTimeout = timeout;
        this.refreshWriteTimeoutHandler();
    }

    @Override
    public boolean isConnected() {
        return this.channel != null && this.channel.isOpen() && !this.disconnected;
    }

    @Override
    public void send(Packet packet) {
        if (this.channel == null) return;

        PacketSendingEvent sendingEvent = new PacketSendingEvent(this, packet);
        this.callEvent(sendingEvent);

        if (!sendingEvent.isCancelled()) {
            final Packet toSend = sendingEvent.getPacket();
            this.channel.writeAndFlush(toSend).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    callEvent(new PacketSentEvent(UdpSession.this, toSend));
                } else {
                    exceptionCaught(null, future.cause());
                }
            });
        }
    }

    @Override
    public void disconnect(String reason) { this.disconnect(reason, null); }

    @Override
    public void disconnect(final String reason, final Throwable cause) {
        if (this.disconnected) return;
        this.disconnected = true;

        if (this.packetHandleThread != null) {
            this.packetHandleThread.interrupt();
            this.packetHandleThread = null;
        }

        if (this.channel != null && this.channel.isOpen()) {
            this.callEvent(new DisconnectingEvent(this, reason, cause));
            this.channel.close().addListener((ChannelFutureListener) future -> {
                callEvent(new DisconnectedEvent(UdpSession.this, reason != null ? reason : "Connection closed.", cause));
            });
        } else {
            this.callEvent(new DisconnectedEvent(this, reason != null ? reason : "Connection closed.", cause));
        }
        this.channel = null;
    }

    protected void refreshReadTimeoutHandler() { refreshReadTimeoutHandler(this.channel); }

    protected void refreshReadTimeoutHandler(Channel channel) {
        if (channel != null) {
            if (this.readTimeout <= 0) {
                if (channel.pipeline().get("readTimeout") != null) channel.pipeline().remove("readTimeout");
            } else {
                if (channel.pipeline().get("readTimeout") == null) {
                    channel.pipeline().addFirst("readTimeout", new ReadTimeoutHandler(this.readTimeout));
                } else {
                    channel.pipeline().replace("readTimeout", "readTimeout", new ReadTimeoutHandler(this.readTimeout));
                }
            }
        }
    }

    protected void refreshWriteTimeoutHandler() { refreshWriteTimeoutHandler(this.channel); }

    protected void refreshWriteTimeoutHandler(Channel channel) {
        if (channel != null) {
            if (this.writeTimeout <= 0) {
                if (channel.pipeline().get("writeTimeout") != null) channel.pipeline().remove("writeTimeout");
            } else {
                if (channel.pipeline().get("writeTimeout") == null) {
                    channel.pipeline().addFirst("writeTimeout", new WriteTimeoutHandler(this.writeTimeout));
                } else {
                    channel.pipeline().replace("writeTimeout", "writeTimeout", new WriteTimeoutHandler(this.writeTimeout));
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (this.disconnected || this.channel != null) {
            ctx.channel().close();
            return;
        }
        this.channel = ctx.channel();

        this.packetHandleThread = new Thread(() -> {
            try {
                Packet packet;
                while ((packet = packets.take()) != null) {
                    callEvent(new PacketReceivedEvent(UdpSession.this, packet));
                }
            } catch (InterruptedException ignored) {}
        });
        this.packetHandleThread.start();
        this.callEvent(new ConnectedEvent(this));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel() == this.channel) {
            this.disconnect("Connection closed.");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String message;
        if (cause instanceof ConnectTimeoutException) {
            message = "Connection timed out.";
        } else if (cause instanceof ReadTimeoutException) {
            message = "Read timed out.";
        } else if (cause instanceof WriteTimeoutException) {
            message = "Write timed out.";
        } else {
            message = cause.toString();
        }
        this.disconnect(message, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        if (packet.isPriority()) {
            this.callEvent(new PacketReceivedEvent(this, packet));
        } else {
            this.packets.add(packet);
        }
    }
}