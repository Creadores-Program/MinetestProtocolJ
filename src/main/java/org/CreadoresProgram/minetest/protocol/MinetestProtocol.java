package org.CreadoresProgram.minetest.protocol;

import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.crypt.PacketEncryption;
import com.github.steveice10.packetlib.packet.PacketHeader;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import org.CreadoresProgram.minetest.protocol.packet.client.*;

public class MinetestProtocol extends PacketProtocol{
  @SuppressWarnings("unused")
	private MinetestProtocol() {
  }
  @Override
  public String getSRVRecordPrefix() {
      return "_minetest_luanti";
  }
  @Override
	public void newClientSession(Client client, Session session) {
    this.registerOutgoing(0x02, ClientInitPacket.class);
    this.registerOutgoing(0x11, ClientInit2Packet.class);
	this.registerOutgoing(0x17, ClientModChannelJoinPacket.class);
  }
  @Override
	public void newServerSession(Server server, Session session) {
    this.registerIncoming(0x02, ClientInitPacket.class);
    this.registerIncoming(0x11, ClientInit2Packet.class);
	this.registerIncoming(0x17, ClientModChannelJoinPacket.class);
  }
}
