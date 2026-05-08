package org.CreadoresProgram.minetest.packetlib.udp.luantimt;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketErrorEvent;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.packetlib.udp.luantimt.io.ByteBufMtNetInput;
import org.CreadoresProgram.minetest.packetlib.udp.luantimt.io.ByteBufMtNetOutput;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class UdpPacketCodec extends MessageToMessageCodec<DatagramPacket, Packet> {
    private Session session;

    public UdpPacketCodec(Session session) {
        this.session = session;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        ByteBuf buf = ctx.alloc().buffer();
        try {
            NetOutput netOut = new ByteBufMtNetOutput(buf);

            this.session.getPacketProtocol().getPacketHeader().writePacketId(netOut, this.session.getPacketProtocol().getOutgoingId(packet));
            packet.write(netOut);

            out.add(new DatagramPacket(buf, ((UdpSession) this.session).getRemoteAddress()));
        } catch(Throwable t) {
            buf.release();
            PacketErrorEvent e = new PacketErrorEvent(this.session, t);
            this.session.callEvent(e);
            if(!e.shouldSuppress()) {
                throw t;
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagram, List<Object> out) throws Exception {
        ByteBuf buf = datagram.content();
        int initial = buf.readerIndex();

        try {
            NetInput in = new ByteBufMtNetInput(buf);

            int id = this.session.getPacketProtocol().getPacketHeader().readPacketId(in);
            if(id == -1) {
                buf.readerIndex(initial);
                return;
            }

            Packet packet = this.session.getPacketProtocol().createIncomingPacket(id);
            packet.read(in);

            if(buf.readableBytes() > 0) {
                throw new IllegalStateException("Packet \"" + packet.getClass().getSimpleName() + "\" not fully read from datagram.");
            }

            out.add(packet);
        } catch(Throwable t) {
            buf.readerIndex(buf.readerIndex() + buf.readableBytes());

            PacketErrorEvent e = new PacketErrorEvent(this.session, t);
            this.session.callEvent(e);
            if(!e.shouldSuppress()) {
                throw t;
            }
        }
    }
}