package org.CreadoresProgram.minetest.packetlib.udp.luantimt;

import com.github.steveice10.packetlib.BuiltinFlags;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.dns.DefaultDnsQuestion;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DefaultDnsRecordDecoder;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsNameResolverBuilder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class UdpClientSession extends UdpSession {
    private Client client;
    private EventLoopGroup group;

    public UdpClientSession(String host, int port, PacketProtocol protocol, Client client) {
        super(host, port, protocol);
        this.client = client;
    }

    @Override
    public void connect(boolean wait) {
        if(this.disconnected) {
            throw new IllegalStateException("Session has already been disconnected.");
        } else if(this.group != null) {
            return;
        }

        try {
            this.group = new NioEventLoopGroup();

            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioDatagramChannel.class);
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                public void initChannel(Channel channel) throws Exception {
                    getPacketProtocol().newClientSession(client, UdpClientSession.this);

                    channel.config().setOption(ChannelOption.IP_TOS, 0x18);

                    ChannelPipeline pipeline = channel.pipeline();

                    refreshReadTimeoutHandler(channel);
                    refreshWriteTimeoutHandler(channel);

                    pipeline.addLast("encryption", new UdpPacketEncryptor(UdpClientSession.this));
                    pipeline.addLast("sizer", new UdpPacketSizer(UdpClientSession.this));
                    pipeline.addLast("codec", new UdpPacketCodec(UdpClientSession.this));
                    pipeline.addLast("manager", UdpClientSession.this);
                }
            }).group(this.group);

            Runnable connectTask = new Runnable() {
                @Override
                public void run() {
                    try {
                        InetSocketAddress remoteAddress = resolveAddress();
                        bootstrap.remoteAddress(remoteAddress);
                        bootstrap.localAddress(client.getBindAddress(), client.getBindPort());

                        ChannelFuture future = bootstrap.connect().sync();
                        if(future.isSuccess()) {
                            while(!isConnected() && !disconnected) {
                                try {
                                    Thread.sleep(5);
                                } catch(InterruptedException e) {
                                }
                            }
                        }
                    } catch(Throwable t) {
                        exceptionCaught(null, t);
                    }
                }
            };

            if(wait) {
                connectTask.run();
            } else {
                new Thread(connectTask).start();
            }
        } catch(Throwable t) {
            exceptionCaught(null, t);
        }
    }

    private InetSocketAddress resolveAddress() {
        boolean debug = getFlag(BuiltinFlags.PRINT_DEBUG, false);

        String name = this.getPacketProtocol().getSRVRecordPrefix() + "._udp." + this.getHost();
        if(debug) {
            System.out.println("[PacketLib] Attempting UDP SRV lookup for \"" + name + "\".");
        }

        DnsNameResolver resolver = null;
        AddressedEnvelope<DnsResponse, InetSocketAddress> envelope = null;
        try {
            resolver = new DnsNameResolverBuilder(this.group.next())
                    .channelType(NioDatagramChannel.class)
                    .build();
            envelope = resolver.query(new DefaultDnsQuestion(name, DnsRecordType.SRV)).get();

            DnsResponse response = envelope.content();
            if(response.count(DnsSection.ANSWER) > 0) {
                DefaultDnsRawRecord record = response.recordAt(DnsSection.ANSWER, 0);
                if(record.type() == DnsRecordType.SRV) {
                    ByteBuf buf = record.content();
                    buf.skipBytes(4); 

                    int port = buf.readUnsignedShort();
                    String host = DefaultDnsRecordDecoder.decodeName(buf);
                    if(host.endsWith(".")) {
                        host = host.substring(0, host.length() - 1);
                    }

                    if(debug) {
                        System.out.println("[PacketLib] Found UDP SRV record: \"" + host + ":" + port + "\".");
                    }

                    this.host = host;
                    this.port = port;
                }
            }
        } catch(Exception e) {
            if(debug) {
                System.out.println("[PacketLib] Failed to resolve UDP SRV record.");
            }
        } finally {
            if(envelope != null) envelope.release();
            if(resolver != null) resolver.close();
        }

        try {
            InetAddress resolved = InetAddress.getByName(getHost());
            return new InetSocketAddress(resolved, getPort());
        } catch (UnknownHostException e) {
            return InetSocketAddress.createUnresolved(getHost(), getPort());
        }
    }

    @Override
    public void disconnect(String reason, Throwable cause) {
        super.disconnect(reason, cause);
        if(this.group != null) {
            this.group.shutdownGracefully();
            this.group = null;
        }
    }
}