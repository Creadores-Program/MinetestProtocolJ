package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientInit2Packet implements Packet{

  /*
		Sent as an ACK for TOCLIENT_AUTH_ACCEPT.
		After this, the server can send data.
	*/
  public ClientInit2Packet() {
  }
  @Override
  public void read(NetInput in) throws IOException {
  }
  @Override
  public void write(NetOutput out) throws IOException {
  }
}
