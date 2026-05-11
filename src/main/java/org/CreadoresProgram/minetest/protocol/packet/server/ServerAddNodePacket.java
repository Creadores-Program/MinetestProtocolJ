package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerAddNodePacket implements Packet{
  private short x, y, z;
  private int content;
  private byte param1;
  private byte param2;
  private boolean keepMetadata;

  @SuppressWarnings("unused")
  private ServerAddNodePacket() {
  }

  /*
		v3s16 position
		serialized mapnode
		u8 keep_metadata
  */
  public ServerAddNodePacket(short x, short y, short z, int content, byte param1, byte param2, boolean keepMetadata){
    this.x = x;
    this.y = y;
    this.z = z;
    this.content = content;
    this.param1 = param1;
    this.param2 = param2;
    this.keepMetadata = keepMetadata;
  }
  public short getX(){
    return this.x;
  }
  public short getY(){
    return this.y;
  }
  public short getZ(){
    return this.z;
  }
  public int getContent(){
    return this.content;
  }
  public byte getParam1(){
    return this.param1;
  }
  public byte getParam2(){
    return this.param2;
  }
  public boolean isKeepMetadata(){
    return this.keepMetadata;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readShort();
    this.y = in.readShort();
    this.z = in.readShort();
    this.content = in.readUnsignedShort();
    this.param1 = in.readByte();
    this.param2 = in.readByte();
    this.keepMetadata = in.readBoolean();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.x);
    out.writeShort(this.y);
    out.writeShort(this.z);
    out.writeShort(this.content);
    out.writeByte(this.param1);
    out.writeByte(this.param2);
    out.writeBoolean(this.keepMetadata);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}