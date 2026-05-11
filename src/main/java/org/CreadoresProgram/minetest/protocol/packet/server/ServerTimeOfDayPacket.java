package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerTimeOfDayPacket implements Packet{
  private int time;
  public float timeSpeed;

  @SuppressWarnings("unused")
  private ServerTimeOfDayPacket() {
  }

  /*
		u16 time (0-23999)
		f1000 time_speed
  */
  public ServerTimeOfDayPacket(int time, float timeSpeed){
    this.time = time;
    this.timeSpeed = timeSpeed;
  }
  public int getTime(){
    return this.time;
  }
  public float getTimeSpeed(){
    return this.timeSpeed;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.time = in.readUnsignedShort();
    if(in.available() < 4){
        return;
    }
    this.timeSpeed = in.readInt() / 1000.0f;
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.time);
    out.writeInt((int) (this.timeSpeed * 1000));
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}