package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerRemoveNodePacket implements Packet{
  private short x, y, z;

  @SuppressWarnings("unused")
  private ServerRemoveNodePacket() {
  }

  /*
		v3s16 position
  */
  public ServerRemoveNodePacket(short x, short y, short z){
    this.x = x;
    this.y = y;
    this.z = z;
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

  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readShort();
    this.y = in.readShort();
    this.z = in.readShort();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.x);
    out.writeShort(this.y);
    out.writeShort(this.z);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}