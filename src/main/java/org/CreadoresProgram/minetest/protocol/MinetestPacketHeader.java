package org.CreadoresProgram.minetest.protocol;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.PacketHeader;

import java.io.IOException;

/**
 * Packet header used by Minetest Luanti packets.
 */
public class MinetestPacketHeader implements PacketHeader {
    @Override
    public boolean isLengthVariable() {
        return false;
    }

    @Override
    public int getLengthSize() {
        return 0;
    }

    @Override
    public int getLengthSize(int length) {
        return 0;
    }

    @Override
    public int readLength(NetInput in, int available) throws IOException {
        return available;
    }

    @Override
    public void writeLength(NetOutput out, int length) throws IOException {
    }

    @Override
    public int readPacketId(NetInput in) throws IOException {
        int id = in.readShort();
        return id;
    }

    @Override
    public void writePacketId(NetOutput out, int packetId) throws IOException {
        out.writeShort(packetId);
    }
}