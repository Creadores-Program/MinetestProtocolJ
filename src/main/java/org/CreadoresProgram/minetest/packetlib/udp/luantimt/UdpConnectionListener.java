package org.CreadoresProgram.minetest.packetlib.udp.luantimt;

import com.github.steveice10.packetlib.ConnectionListener;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UdpConnectionListener implements ConnectionListener {
    private String host;
    private int port;
    private Server server;

    private EventLoopGroup group;
    private Channel channel;
    
    private final Map<InetSocketAddress, UdpServerSession> sessions = new ConcurrentHashMap<>();

    public UdpConnectionListener(String host, int port, Server server) {
        this.host = host;
        this.port = port;
        this.server = server;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public boolean isListening() {
        return this.channel != null && this.channel.isOpen();
    }

    @Override
    public void bind() {
        this.bind(true);
    }

    @Override
    public void bind(boolean wait) {
        this.bind(wait, null);
    }

    @Override
    public void bind(boolean wait, final Runnable callback) {
        if(this.group != null || this.channel != null) {
            return;
        }

        this.group = new NioEventLoopGroup();
        
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(this.group)
                 .channel(NioDatagramChannel.class)
                 .handler(new ChannelInitializer<Channel>() {
            @Override
            public void initChannel(Channel channel) throws Exception {
                channel.config().setOption(ChannelOption.IP_TOS, 0x18);
                
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast("sizer", new UdpPacketSizer(null));
                pipeline.addLast("codec", new UdpPacketCodec(null));

                pipeline.addLast("manager", new UdpServerManager(server, sessions));
            }
        }).localAddress(this.host, this.port);

        ChannelFuture future = bootstrap.bind();

        if(wait) {
            try {
                future.sync();
            } catch(InterruptedException e) {
            }

            channel = future.channel();
            if(callback != null) {
                callback.run();
            }
        } else {
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        channel = future.channel();
                        if(callback != null) {
                            callback.run();
                        }
                    } else {
                        System.err.println("[ERROR] Failed to asynchronously bind UDP connection listener.");
                        if(future.cause() != null) {
                            future.cause().printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void close() {
        this.close(false);
    }

    @Override
    public void close(boolean wait) {
        this.close(wait, null);
    }

    @Override
    public void close(boolean wait, final Runnable callback) {
        for(UdpServerSession session : sessions.values()) {
            session.disconnect("Server closed.");
        }
        sessions.clear();

        if(this.channel != null) {
            if(this.channel.isOpen()) {
                ChannelFuture future = this.channel.close();
            }
            this.channel = null;
        }

        if(this.group != null) {
            Future<?> future = this.group.shutdownGracefully();
            this.group = null;
        }
    }
}