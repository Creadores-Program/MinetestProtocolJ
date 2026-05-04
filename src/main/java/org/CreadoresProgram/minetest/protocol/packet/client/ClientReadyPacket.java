package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;
import org.CreadoresProgram.minetest.protocol.MinetestConstants;

import java.io.IOException;

public class ClientInitPacket implements Packet{
  private byte reserved;
  private byte major;
  private byte minor;
  private byte patch;
  private String fullVersion;
  @SuppressWarnings("unused")
  private ClientReadyPacket() {
  }
  /*
		u8 major
		u8 minor
		u8 patch
		u8 reserved
		u16 len
		u8[len] full_version_string
  */
  public ClientReadyPacket(byte reserved){
    this.reserved = reserved;
    this.major = MinetestConstants.MAJOR_VERSION;
    this.minor = MinetestConstants.MINOR_VERSION;
    this.patch = MinetestConstants.PATCH_VERSION;
    this.fullVersion = MinetestConstants.FULL_VERSION;
  }
  public byte getReserved(){
    return this.reserved;
  }
  public byte getMajor(){
    return this.major;
  }
  public byte getMinor(){
    return this.minor;
  }
  public byte getPatch(){
    return this.patch;
  }
  public String getFullVersion(){
    return this.maxProtocolVersion;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.major = in.readUnsignedByte();
    this.minor = in.readUnsignedByte();
    this.patch = in.readUnsignedByte();
    this.reserved = in.readByte();
    this.fullVersion = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.major);
    out.writeByte(this.minor);
    out.writeByte(this.patch);
    out.writeShort(this.reserved);
    out.writeString(this.fullVersion);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}