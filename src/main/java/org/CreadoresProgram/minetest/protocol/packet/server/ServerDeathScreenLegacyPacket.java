package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerDeathScreenLegacyPacket implements Packet{
  private boolean setCamera;
  private double x, y, z;

  @SuppressWarnings("unused")
  private ServerDeathScreenLegacyPacket() {
  }
  
  /*
		u8 bool unused
		v3f1000 unused
  */
  public ServerDeathScreenLegacyPacket(boolean setCamera, double x, double y, double z) {
    this.setCamera = setCamera;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public boolean isSetCamera(){
    return this.setCamera;
  }
  public double getX(){
    return this.x;
  }
  public double getY(){
    return this.y;
  }
  public double getZ(){
    return this.z;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.setCamera = in.readBoolean();
    this.x = in.readInt() / 1000.0;
    this.y = in.readInt() / 1000.0;
    this.z = in.readInt() / 1000.0;
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeBoolean(this.setCamera);
    out.writeInt((int) (this.x * 1000));
    out.writeInt((int) (this.y * 1000));
    out.writeInt((int) (this.z * 1000));
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}