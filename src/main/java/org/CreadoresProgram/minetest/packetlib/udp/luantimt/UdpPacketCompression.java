package org.CreadoresProgram.minetest.packetlib.udp.luantimt;

import com.github.steveice10.packetlib.Session;

import org.CreadoresProgram.minetest.packetlib.udp.luantimt.io.ByteBufMtNetInput;
import org.CreadoresProgram.minetest.packetlib.udp.luantimt.io.ByteBufMtNetOutput;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class UdpPacketCompression extends MessageToMessageCodec<ByteBuf, ByteBuf> {
    private static final int MAX_COMPRESSED_SIZE = 2097152;

    private Session session;
    private Deflater deflater = new Deflater();
    private Inflater inflater = new Inflater();
    private byte[] buffer = new byte[8192];

    public UdpPacketCompression(Session session) {
        this.session = session;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        ByteBuf result = ctx.alloc().buffer();
        ByteBufMtNetOutput output = new ByteBufMtNetOutput(result);

        if(readable < this.session.getCompressionThreshold()) {
            output.writeVarInt(0);
            result.writeBytes(in);
        } else {
            byte[] bytes = new byte[readable];
            in.readBytes(bytes);
            output.writeVarInt(bytes.length);
            
            this.deflater.setInput(bytes, 0, readable);
            this.deflater.finish();
            while(!this.deflater.finished()) {
                int length = this.deflater.deflate(this.buffer);
                result.writeBytes(this.buffer, 0, length);
            }
            this.deflater.reset();
        }
        out.add(result);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if(buf.readableBytes() == 0) {
            return;
        }

        ByteBufMtNetInput in = new ByteBufMtNetInput(buf);
        int size = in.readVarInt();
        
        if(size == 0) {
            out.add(buf.readBytes(buf.readableBytes()));
        } else {
            if(size < this.session.getCompressionThreshold()) {
                throw new DecoderException("Badly compressed UDP packet: size of " + size + " is below threshold of " + this.session.getCompressionThreshold() + ".");
            }

            if(size > MAX_COMPRESSED_SIZE) {
                throw new DecoderException("Badly compressed UDP packet: size of " + size + " is larger than protocol maximum.");
            }

            byte[] compressedBytes = new byte[buf.readableBytes()];
            buf.readBytes(compressedBytes);
            
            this.inflater.setInput(compressedBytes);
            byte[] inflated = new byte[size];
            try {
                int resultLength = this.inflater.inflate(inflated);
                if (resultLength != size) {
                    throw new DecoderException("Inconsistent compression size: expected " + size + " but got " + resultLength);
                }
                out.add(Unpooled.wrappedBuffer(inflated));
            } finally {
                this.inflater.reset();
            }
        }
    }
}