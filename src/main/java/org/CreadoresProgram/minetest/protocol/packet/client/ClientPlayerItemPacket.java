package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientPlayerItemPacket implements Packet{

  private int item;
  
  @SuppressWarnings("unused")
  private ClientPlayerItemPacket() {
  }

  /*
		u16 item
  */
  public ClientPlayerItemPacket(int item){
    this.item = item;
  }

  public int getItem(){
    return this.item;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.item = in.readUnsignedShort();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.item);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
