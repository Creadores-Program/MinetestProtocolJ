package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerPlayerSpeedPacket implements Packet{
  private float x, y, z;

  @SuppressWarnings("unused")
  private ServerPlayerSpeedPacket() {
  }

  /*
		v3f added_vel
	*/
  public ServerPlayerSpeedPacket(float x, float y, float z){
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public float getX(){
    return this.x;
  }
  public float getY(){
    return this.y;
  }
  public float getZ(){
    return this.z;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readFloat();
    this.y = in.readFloat();
    this.z = in.readFloat();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeFloat(this.x);
    out.writeFloat(this.y);
    out.writeFloat(this.z);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}