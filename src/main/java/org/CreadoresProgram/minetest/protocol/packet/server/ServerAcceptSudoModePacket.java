package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerAcceptSudoModePacket implements Packet{

  /*
		Sent to client to show it is in sudo mode now.
	*/
  public ServerAcceptSudoModePacket() {
  }

  @Override
  public void read(NetInput in) throws IOException {
  }
  @Override
  public void write(NetOutput out) throws IOException {
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}