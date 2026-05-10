package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerAuthAcceptPacket implements Packet{
  private float x, y, z;
  private long mapSeed;
  private float sendInterval;
  private long sudoAuthMethods;

  @SuppressWarnings("unused")
  private ServerAuthAcceptPacket() {
  }

  /*
		Message from server to accept auth.

		v3f unused
		u64 map seed
		f1000 recommended send interval
		u32 : supported auth methods for sudo mode
		      (where the user can change their password)
  */
  public ServerAuthAcceptPacket(float x, float y, float z, long mapSeed, float sendInterval, long sudoAuthMethods){
    this.x = x;
    this.y = y;
    this.z = z;
    this.mapSeed = mapSeed;
    this.sendInterval = sendInterval;
    this.sudoAuthMethods = sudoAuthMethods;
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
  public long getMapSeed(){
    return this.mapSeed;
  }
  public float getSendInterval(){
    return this.sendInterval;
  }
  public long getSudoAuthMethods(){
    return this.sudoAuthMethods;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readFloat();
    this.y = in.readFloat();
    this.z = in.readFloat();
    this.mapSeed = in.readLong();
    this.sendInterval = in.readInt() / 1000.0f;
    this.sudoAuthMethods = in.readUnsignedInt();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeFloat(this.x);
    out.writeFloat(this.y);
    out.writeFloat(this.z);
    out.writeLong(this.mapSeed);
    out.writeInt((int) (this.sendInterval * 1000));
    out.writeInt((int) this.sudoAuthMethods);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}