package org.CreadoresProgram.minetest.packetlib.udp.luantimt.io;
import com.github.steveice10.packetlib.tcp.io.ByteBufNetOutput;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;

public class ByteBufMtNetOutput extends ByteBufNetOutput{
    public ByteBufMtNetOutput(ByteBuf buf){
        super(buf);
    }

    @Override
    public String writeString(String s) throws IOException{
        if(s == null) {
            throw new IllegalArgumentException("String cannot be null!");
        }
        byte[] sby = s.getBytes(StandardCharsets.UTF_8);
        this.writeShort(sby.length);
        if (sby.length == 0) return;
        this.writeBytes(sby);
    }
}