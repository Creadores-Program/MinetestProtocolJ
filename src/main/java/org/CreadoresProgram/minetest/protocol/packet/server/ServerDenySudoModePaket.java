package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerDenySudoModePacket implements Packet{

  /*
		Signals client that sudo mode auth failed.
  */
  public ServerDenySudoModePacket() {
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