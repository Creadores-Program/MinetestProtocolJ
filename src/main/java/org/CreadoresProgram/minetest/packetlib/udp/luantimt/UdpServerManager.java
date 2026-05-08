package org.CreadoresProgram.minetest.packetlib.udp.luantimt;

import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.Map;

public class UdpServerManager extends SimpleChannelInboundHandler<DatagramPacket> {
    private final Server server;
    private final Map<InetSocketAddress, UdpServerSession> sessions;

    public UdpServerManager(Server server, Map<InetSocketAddress, UdpServerSession> sessions) {
        this.server = server;
        this.sessions = sessions;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        InetSocketAddress sender = packet.sender();
        UdpServerSession session = sessions.get(sender);

        if (session == null) {
            PacketProtocol protocol = server.createPacketProtocol();
            session = new UdpServerSession(sender.getHostName(), sender.getPort(), protocol, server);
            session.getPacketProtocol().newServerSession(server, session);
            sessions.put(sender, session);
            session.channelActive(ctx);
        }
        ctx.fireChannelRead(packet.retain());
    }
}