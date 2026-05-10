package org.CreadoresProgram.minetest.protocol.data.game;
public final class MapBlockFlags {

    public static final int IS_UNDERGROUND = 0x01;

    public static final int DAY_NIGHT_DIFFERS = 0x02;

    public static final int GENERATED = 0x08;

    public static final int NOT_MODIFIED = 0x10;

    public static final int IS_MONO_BLOCK = 0x20;

    private MapBlockFlags() {}
}