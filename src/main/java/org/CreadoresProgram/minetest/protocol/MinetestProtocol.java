package org.CreadoresProgram.minetest.protocol;

import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.crypt.PacketEncryption;
import com.github.steveice10.packetlib.packet.PacketHeader;
import com.github.steveice10.packetlib.packet.PacketProtocol;
import org.CreadoresProgram.minetest.protocol.packet.client.*;
import org.CreadoresProgram.minetest.protocol.packet.server.*;

public class MinetestProtocol extends PacketProtocol{

  private PacketHeader header = new MinetestPacketHeader();
  @SuppressWarnings("unused")
	private MinetestProtocol() {
  }
  
  @Override
  public String getSRVRecordPrefix() {
    return "_minetest_luanti";
  }

  @Override
	public PacketHeader getPacketHeader() {
		return this.header;
	}

	@Override
	public PacketEncryption getEncryption() {
		return null;
	}

  @Override
	public void newClientSession(Client client, Session session) {
    this.registerIncoming(0x02, ServerInitPacket.class);
    this.registerIncoming(0x03, ServerAuthAcceptPacket.class);
    this.registerIncoming(0x04, ServerAcceptSudoModePacket.class);
    this.registerIncoming(0x05, ServerDenySudoModePacket.class);
    this.registerIncoming(0x0A, ServerAccessDeniedPacket.class);
    this.registerIncoming(0x20, ServerBlockDataPacket.class);
    this.registerIncoming(0x21, ServerAddNodePacket.class);
    this.registerIncoming(0x22, ServerRemoveNodePacket.class);
    this.registerIncoming(0x27, ServerInventoryPacket.class);
    this.registerIncoming(0x29, ServerTimeOfDayPacket.class);
    this.registerIncoming(0x2A, ServerCsmRestrictionFlagsPacket.class);
    this.registerIncoming(0x2B, ServerPlayerSpeedPacket.class);
    this.registerIncoming(0x2C, ServerMediaPushPacket.class);

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

    this.registerOutgoing(0x02, ServerInitPacket.class);
    this.registerOutgoing(0x03, ServerAuthAcceptPacket.class);
    this.registerOutgoing(0x04, ServerAcceptSudoModePacket.class);
    this.registerOutgoing(0x05, ServerDenySudoModePacket.class);
    this.registerOutgoing(0x0A, ServerAccessDeniedPacket.class);
    this.registerOutgoing(0x20, ServerBlockDataPacket.class);
    this.registerOutgoing(0x21, ServerAddNodePacket.class);
    this.registerOutgoing(0x22, ServerRemoveNodePacket.class);
    this.registerOutgoing(0x27, ServerInventoryPacket.class);
    this.registerOutgoing(0x29, ServerTimeOfDayPacket.class);
    this.registerOutgoing(0x2A, ServerCsmRestrictionFlagsPacket.class);
    this.registerOutgoing(0x2B, ServerPlayerSpeedPacket.class);
    this.registerOutgoing(0x2C, ServerMediaPushPacket.class);
  }
}
