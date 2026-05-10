package org.CreadoresProgram.minetest.protocol.data.game;

public final class AuthMechanism {

    public static final int NONE = 0;

    public static final int LEGACY_PASSWORD = 1 << 0;

    public static final int SRP = 1 << 1;

    public static final int FIRST_SRP = 1 << 2;

    private AuthMechanism() {}

    public static boolean isSupported(int mask, int mechanism) {
        return (mask & mechanism) != 0;
    }
}