package org.CreadoresProgram.minetest.protocol.data.game;
public class ActiveObjectMessage {
    public int id;
    public byte[] payload;
    public static final byte GENERIC_CMD_SET_PROPERTIES = 0x00;
    public static final byte GENERIC_CMD_UPDATE_POSITION = 0x01;
    public static final byte GENERIC_CMD_SET_TEXTURE_MOD = 0x02;
    public static final byte GENERIC_CMD_SET_ANIMATION = 0x03;
}