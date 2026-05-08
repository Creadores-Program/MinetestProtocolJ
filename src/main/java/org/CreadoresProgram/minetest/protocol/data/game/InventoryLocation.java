package org.CreadoresProgram.minetest.protocol.data.game;
public class InventoryLocation {
    public enum Type { CURRENT_PLAYER, PLAYER, NODEMETA, DETACHED }

    private final Type type;
    private String name; // PLAYER and DETACHED
    private int x, y, z; //NODEMETA

    private static final InventoryLocation CURRENT_PLAYER_INS = new InventoryLocation(Type.CURRENT_PLAYER, null, 0, 0, 0);

    public static InventoryLocation currentPlayer() {
        return CURRENT_PLAYER_INS;
    }

    public static InventoryLocation player(String name) {
        return new InventoryLocation(Type.PLAYER, name, 0, 0, 0);
    }

    public static InventoryLocation nodeMeta(int x, int y, int z) {
        return new InventoryLocation(Type.NODEMETA, null, x, y, z);
    }
    public static InventoryLocation detached(String name){
        return new InventoryLocation(Type.DETACHED, name, 0, 0, 0);
    }
    public static InventoryLocation parseLocation(String locStr) {
        if (locStr.equals("current_player")) {
            return currentPlayer();
        } else if (locStr.startsWith("player:")) {
            return player(locStr.substring(7));
        } else if (locStr.startsWith("nodemeta:")) {
            String coords = locStr.substring(9);
            String[] xyz = coords.split(",");
            int x = Integer.parseInt(xyz[0]);
            int y = Integer.parseInt(xyz[1]);
            int z = Integer.parseInt(xyz[2]);
            return nodeMeta(x, y, z);
        } else if (locStr.startsWith("detached:")) {
            return detached(locStr.substring(9));
        }
    }

    private InventoryLocation(Type type, String name, int x, int y, int z) {
        this.type = type;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Type getType(){
        return this.type;
    }
    public String getName(){
        return this.name;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getZ(){
        return this.z;
    }
    private static final CURRENT_PLAYER_SRT = "current_player";
    private static final PLAYER_SRT = "player:";
    private static final NODEMETA_STR = "nodemeta:";
    private static final DETACHED_STR = "detached:";
    private static final COM_STR = ",";
    @Override
    public String toString() {
        return switch (type) {
            case CURRENT_PLAYER -> CURRENT_PLAYER_SRT;
            case PLAYER -> PLAYER_SRT + name;
            case NODEMETA -> NODEMETA_STR + x + COM_STR + y + COM_STR + z;
            case DETACHED -> DETACHED_STR + name;
        };
    }
}