package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientRemovedSoundsPacket implements Packet{

  private int[] sounds;
  
  @SuppressWarnings("unused")
  private ClientRemovedSoundsPacket() {
  }

  /*
		u16 len
		s32[len] sound_id
  */
  public ClientRemovedSoundsPacket(int[] sounds){
    this.sounds = sounds;
  }

  public int[] getSounds(){
    return this.sounds;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    int len = in.readUnsignedShort();
    this.sounds = in.readInts(len);
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.sounds.length);
    out.writeInts(this.sounds);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
