package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;
import org.CreadoresProgram.minetest.protocol.MinetestConstants;

import java.io.IOException;

public class ServerInitPacket implements Packet{
  private int serializationVersion;
  private long authMethods;
  private int protocolVersion;
  private String playerName;
  private int compressionMode;
  @SuppressWarnings("unused")
  private ServerInitPacket() {
  }
  /*
		Sent after TOSERVER_INIT.

		u8 deployed serialization version
		u16 unused (network compression, never implemeneted)
		u16 deployed protocol version
		u32 supported auth methods
		std::string unused (used to be username)
	*/
  public ServerInitPacket(int serializationVersion, long authMethods, String playerName, int compressionMode){
    this.serializationVersion = serializationVersion;
    this.authMethods = authMethods;
    this.protocolVersion = MinetestConstants.MAX_PROTOCOL_VERSION;
    this.playerName = playerName;
    this.compressionMode = compressionMode;
  }
  public int getSerializationVersion(){
    return this.serializationVersion;
  }
  public long getAuthMethods(){
    return this.compressionModes;
  }
  public int getProtocolVersion(){
    return this.proticolVersion;
  }
  public String getPlayerName(){
    return this.playerName;
  }
  public int getCompressionMode(){
    return this.compressionMode;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.serializationVersion = in.readUnsignedByte();
    this.compressionMode = in.readUnsignedShort();
    this.protocolVersion = in.readUnsignedShort();
    this.authMethods = in.readUnsignedInt();
    if(in.available() < 2){
      return;
    }
    this.playerName = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte((int) this.serializationVersion);
    out.writeShort(this.compressionMode);
    out.writeShort(this.protocolVersion);
    out.writeInt(this.authMethods);
    out.writeString(this.playerName);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}