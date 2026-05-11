package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerCsmRestrictionFlagsPacket implements Packet{
  private long flags;

  @SuppressWarnings("unused")
  private ServerCsmRestrictionFlagsPacket() {
  }

  /*
		u32 CSMRestrictionFlags byteflag
	*/
  public ServerCsmRestrictionFlagsPacket(long flags){
    this.flags = flags;
  }
  public long getFlags(){
    return this.flags;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.flags = in.readUnsignedInt();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeInt((int) this.flags);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}