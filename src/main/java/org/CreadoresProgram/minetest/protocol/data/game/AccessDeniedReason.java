package org.CreadoresProgram.minetest.protocol.data.game;
/**
 * TOCLIENT_ACCESS_DENIED Reasons
 */
public final class AccessDeniedReason {
    public static final int SERVER_FAIL            = 0x00;
    public static final int WRONG_PASSWORD         = 0x01;
    public static final int UNEXPECTED_DATA        = 0x02;
    public static final int SINGLEPLAYER_ONLY      = 0x03;
    public static final int WRONG_VERSION          = 0x04;
    public static final int SESSION_NOT_FOUND      = 0x05;
    public static final int USER_RESTRICTED        = 0x06;
    public static final int PLAYER_HERE            = 0x07;
    public static final int SERVER_FULL            = 0x08;
    public static final int TOO_MANY_CONNECTIONS   = 0x09;
    public static final int SHUTDOWN               = 0x0A;
    public static final int CRASHED                = 0x0B;

    private AccessDeniedReason() {}
}