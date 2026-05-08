package org.CreadoresProgram.minetest.packetlib.udp.luantimt;
import com.github.steveice10.packetlib.Session;

import org.CreadoresProgram.minetest.packetlib.udp.luantimt.io.ByteBufMtNetInput;
import org.CreadoresProgram.minetest.packetlib.udp.luantimt.io.ByteBufMtNetOutput;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class UdpPacketSizer extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private Session session;

    public UdpPacketSizer(Session session) {
        this.session = session;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        int headerSize = this.session.getPacketProtocol().getPacketHeader().getLengthSize(length);
        ByteBuf result = ctx.alloc().buffer(headerSize + length);

        this.session.getPacketProtocol().getPacketHeader().writeLength(new ByteBufMtNetOutput(result), length);
        result.writeBytes(in);
        
        out.add(result);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        int lengthSize = this.session.getPacketProtocol().getPacketHeader().getLengthSize();
        
        if (lengthSize > 0) {
            try {
                int length = this.session.getPacketProtocol().getPacketHeader().readLength(new ByteBufMtNetInput(buf), buf.readableBytes());
                
                if (buf.readableBytes() < length) {
                    throw new CorruptedFrameException("UDP Packet length mismatch: expected " + length + " but got " + buf.readableBytes());
                }

                out.add(buf.readBytes(length));
            } catch (Exception e) {
                throw new CorruptedFrameException("Failed to read UDP packet length.", e);
            }
        } else {
            out.add(buf.readBytes(buf.readableBytes()));
        }
    }
}