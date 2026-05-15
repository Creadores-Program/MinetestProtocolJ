package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerMovePlayerPacket implements Packet{
  private double x, y, z;
  private float pitch;
  private float yaw;

  @SuppressWarnings("unused")
  private ServerMovePlayerPacket() {
  }
  
  /*
		v3f1000 player position
		f1000 player pitch
		f1000 player yaw
  */
  public ServerMovePlayerPacket(double x, double y, double z, float pitch, float yaw) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
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
  public float getPitch(){
    return this.pitch;
  }
  public float getYaw(){
    return this.yaw;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readInt() / 1000.0;
    this.y = in.readInt() / 1000.0;
    this.z = in.readInt() / 1000.0;
    this.pitch = in.readInt() / 1000.0f;
    this.yaw = in.readInt() / 1000.0f;
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeInt((int) (this.x * 1000));
    out.writeInt((int) (this.y * 1000));
    out.writeInt((int) (this.z * 1000));
    out.writeInt((int) (this.pitch * 1000));
    out.writeInt((int)(this.yaw * 1000));
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}