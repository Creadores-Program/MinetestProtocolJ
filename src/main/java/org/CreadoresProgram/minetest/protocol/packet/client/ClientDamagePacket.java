package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientDamagePacket implements Packet{

  private int amount;
  
  @SuppressWarnings("unused")
  private ClientDamagePacket() {
  }

  /*
		u8 amount
  */
  public ClientDamagePacket(int amount){
    this.amount = amount;
  }

  public int getAmount(){
    return this.amount;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.amount = in.readUnsignedByte();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.amount);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
