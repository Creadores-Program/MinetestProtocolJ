package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerHpPacket implements Packet{
  private int hp;

  @SuppressWarnings("unused")
  private ServerHpPacket() {
  }
  
  /*
		u8 hp
  */
  public ServerHpPacket(int hp) {
    this.hp = hp;
  }

  public int getHp(){
    return this.hp;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.hp = in.readUnsignedByte();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.hp);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}