package org.CreadoresProgram.minetest.packetlib.udp.luantimt.io;
import com.github.steveice10.packetlib.tcp.io.ByteBufNetInput;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;

public class ByteBufMtNetInput extends ByteBufNetInput{
    public ByteBufMtNetInput(ByteBuf buf){
        super(buf);
    }

    @Override
    public String readString() throws IOException{
        int len = this.readUnsignedShort();
        return new String(this.readBytes(len), StandardCharsets.UTF_8);
    }
}