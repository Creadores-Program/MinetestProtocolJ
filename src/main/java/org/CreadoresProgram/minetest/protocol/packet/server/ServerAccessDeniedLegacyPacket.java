package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerAccessDeniedLegacyPacket implements Packet{
  private String reason;

  @SuppressWarnings("unused")
  private ServerAccessDeniedLegacyPacket() {
  }
  
  /*
		u16 reason_length
		wstring reason
  */
  public ServerAccessDeniedLegacyPacket(String reason) {
    this.reason = reason;
  }

  public int getReason(){
    return this.reason;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.reason = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeString(this.reason);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}