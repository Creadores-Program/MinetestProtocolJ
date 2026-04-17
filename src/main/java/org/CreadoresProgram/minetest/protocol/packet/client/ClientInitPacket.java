package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;
//import org.CreadoresProgram.minetest.protocol.MinetestConstants;

import java.io.IOException;

public class ClientInitPacket implements Packet{
  private short serializationVersion;
  private int compressionModes;
  private int minProtocolVersion;
  private int maxProtocolVersion;
  private String playerName;
  @SuppressWarnings("unused")
  private ClientInitPacket() {
  }
  /*
		Sent first after connected.

		u8 serialization version (=SER_FMT_VER_HIGHEST_READ)
		u16 unused (supported network compression modes, never implemeneted)
		u16 minimum supported network protocol version
		u16 maximum supported network protocol version
		std::string player name
	*/
  public ClientInitPacket(short serializationVersion, int compressionModes, String playerName){
    this.serializationVersion = serializationVersion;
    this.compressionModes = compressionModes;
    /*
    this.minProtocolVersion = MinetestConstants.MIN_PROTOCOL_VERSION;
    this.maxProtocolVersion = MinetestConstants.MAX_PROTOCOL_VERSION;
    */
    this.playerName = playerName;
  }
  public short getSerializationVersion(){
    return this.serializationVersion;
  }
  public int getCompressionModes(){
    return this.compressionModes;
  }
  public int getMinProtocolVersion(){
    return this.minProticolVersion;
  }
  public int getMaxProtocolVersion(){
    return this.maxProtocolVersion;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.serializationVersion = (short) in.readUnsignedByte();
    this.compressionModes = in.readUnsignedShort();
    this.minProtocolVersion = in.readUnsignedShort();
    this.maxProtocolVersion = in.readUnsignedShort();
    this.playerName = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte((int) this.serializationVersion);
    out.writeShort(this.compressionModes);
    out.writeShort(this.minProtocolVersion);
    out.writeShort(this.maxProtocolVersion);
    out.writeString(this.playerName);
  }
}
