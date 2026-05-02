package org.CreadoresProgram.minetest.protocol.data.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PointedThing{

    public static final byte TYPE_NOTHING = 0;
    public static final byte TYPE_NODE = 1;
    public static final byte TYPE_OBJECT = 2;

    private byte type;

    //Node
    private short underX, underY, underZ;
    private short aboveX, aboveY, aboveZ;

    //Object
    private int objectId;

    @SuppressWarnings("unused")
    private PointedThing() {
    }

    public static PointedThing deserialize(ByteBuf in){
        byte type = in.readByte();
        if(type == TYPE_NOTHING){
            return nothing();
        }
        if(type == TYPE_NODE){
            return node(in.readShort(), in.readShort(), in.readShort(), in.readShort(), in.readShort(), in.readShort());
        }
        if(type == TYPE_OBJECT){
            return object(in.readUnsignedShort());
        }
        return nothing();
    }

    public void serialize(ByteBuf out){
        out.writeByte(this.type);
        if(this.type == TYPE_NOTHING){
            return;
        }
        switch(this.type){
            case TYPE_NODE:
                out.writeShort(this.underX);
                out.writeShort(this.underY);
                out.writeShort(this.underZ);
                out.writeShort(this.aboveX);
                out.writeShort(this.aboveY);
                out.writeShort(this.aboveZ);
                break;
            case TYPE_OBJECT:
                out.writeShort(this.objectId);
                break;
        }
    }

    public static PointedThing nothing(){
        PointedThing pt = new PointedThing();
        pt.type = TYPE_NOTHING;
        return pt;
    }

    public static PointedThing node(short ux, short uy, short uz, short ax, short ay, short az){
        PointedThing pt = new PointedThing();
        pt.underX = ux;
        pt.underY = uy;
        pt.underZ = uz;
        pt.aboveX = ax;
        pt.aboveY = ay;
        pt.aboveZ = az;
        pt.type = TYPE_NODE;
        return pt;
    }
    public static PointedThing object(int oi){
        PointedThing pt = new PointedThing();
        pt.objectId = oi;
        pt.type = TYPE_OBJECT;
        return pt;
    }

    public byte getType(){
        return this.type;
    }

    //node
    public short getUnderX(){
        return this.underX;
    }
    public short getUnderY(){
        return this.underY;
    }
    public short getUnderZ(){
        return this.underZ;
    }
    public short getAboveX(){
        return this.aboveX;
    }
    public short getAboveY(){
        return this.aboveY;
    }
    public short getAboveZ(){
        return this.aboveZ;
    }
    //object
    public int getObjectId(){
        return this.objectId;
    }
}