package org.CreadoresProgram.minetest.protocol.data.game;

public final class CSMRestrictionFlags {
    public static final long NONE                = 0x00000000L;

    public static final long LOAD_CLIENT_MODS    = 0x00000001L;

    public static final long CHAT_MESSAGES       = 0x00000002L;
    
    public static final long READ_ITEMDEFS       = 0x00000004L;
    
    public static final long READ_NODEDEFS       = 0x00000008L;
    
    public static final long LOOKUP_NODES        = 0x00000010L;
    
    public static final long READ_PLAYERINFO     = 0x00000020L;
    
    public static final long ALL                 = 0xFFFFFFFFL;

    private CSMRestrictionFlags() {}

    public static boolean isRestricted(long serverMask, long flag) {
        return (serverMask & flag) != 0;
    }
}