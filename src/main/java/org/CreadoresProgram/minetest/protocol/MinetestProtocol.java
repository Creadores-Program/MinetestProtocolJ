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
    this.registerOutgoing(0x18, ClientModChannelLeavePacket.class);
    this.registerOutgoing(0x19, ClientModChannelMsgPacket.class);
    this.registerOutgoing(0x23, ClientPlayerPosPacket.class);
    this.registerOutgoing(0x24, ClientGotBlocksPacket.class);
    this.registerOutgoing(0x25, ClientDeletedBlocksPacket.class);
    this.registerOutgoing(0x31, ClientInventoryActionPacket.class);
    this.registerOutgoing(0x32, ClientChatMessagePacket.class);
    this.registerOutgoing(0x35, ClientDamagePacket.class);
    this.registerOutgoing(0x37, ClientPlayerItemPacket.class);
    this.registerOutgoing(0x38, ClientRespawnLegacyPacket.class);
    this.registerOutgoing(0x39, ClientInteractPacket.class);
    this.registerOutgoing(0x3a, ClientRemovedSoundsPacket.class);
    this.registerOutgoing(0x3b, ClientNodeMetaFieldsPacket.class);
    this.registerOutgoing(0x3c, ClientInventoryFieldsPacket.class);
    this.registerOutgoing(0x40, ClientRequestMediaPacket.class);
    this.registerOutgoing(0x41, ClientHaveMediaPacket.class);
    this.registerOutgoing(0x43, ClientReadyPacket.class);
    this.registerOutgoing(0x50, ClientFirstSrpPacket.class);
    this.registerOutgoing(0x51, ClientSrpBytesAPacket.class);
    this.registerOutgoing(0x52, ClientSrpBytesMPacket.class);
    this.registerOutgoing(0x53, ClientUpdateInfoPacket.class);
    this.registerOutgoing(0x54, ClientNumMsgTypesPacket.class);
  }
  @Override
	public void newServerSession(Server server, Session session) {
    this.registerIncoming(0x02, ClientInitPacket.class);
    this.registerIncoming(0x11, ClientInit2Packet.class);
	  this.registerIncoming(0x17, ClientModChannelJoinPacket.class);
    this.registerIncoming(0x18, ClientModChannelLeavePacket.class);
    this.registerIncoming(0x19, ClientModChannelMsgPacket.class);
    this.registerIncoming(0x23, ClientPlayerPosPacket.class);
    this.registerIncoming(0x24, ClientGotBlocksPacket.class);
    this.registerIncoming(0x25, ClientDeletedBlocksPacket.class);
    this.registerIncoming(0x31, ClientInventoryActionPacket.class);
    this.registerIncoming(0x32, ClientChatMessagePacket.class);
    this.registerIncoming(0x35, ClientDamagePacket.class);
    this.registerIncoming(0x37, ClientPlayerItemPacket.class);
    this.registerIncoming(0x38, ClientRespawnLegacyPacket.class);
    this.registerIncoming(0x39, ClientInteractPacket.class);
    this.registerIncoming(0x3a, ClientRemovedSoundsPacket.class);
    this.registerIncoming(0x3b, ClientNodeMetaFieldsPacket.class);
    this.registerIncoming(0x3c, ClientInventoryFieldsPacket.class);
    this.registerIncoming(0x40, ClientRequestMediaPacket.class);
    this.registerIncoming(0x41, ClientHaveMediaPacket.class);
    this.registerIncoming(0x43, ClientReadyPacket.class);
    this.registerIncoming(0x50, ClientFirstSrpPacket.class);
    this.registerIncoming(0x51, ClientSrpBytesAPacket.class);
    this.registerIncoming(0x52, ClientSrpBytesMPacket.class);
    this.registerIncoming(0x53, ClientUpdateInfoPacket.class);
    this.registerIncoming(0x54, ClientNumMsgTypesPacket.class);
  }
}
