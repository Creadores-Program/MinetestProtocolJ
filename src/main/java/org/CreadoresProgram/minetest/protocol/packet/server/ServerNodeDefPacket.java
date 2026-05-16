package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.MediaFile;

import java.io.IOException;

public class ServerNodeDefPacket implements Packet{
  private byte[] compressedNodeDef;

  @SuppressWarnings("unused")
  private ServerNodeDefPacket() {
  }
  
  
  public ServerNodeDefPacket(byte[] compressedNodeDef) {
    this.compressedNodeDef = compressedNodeDef;
  }

  public byte[] getCompressedNodeDef(){
    return this.compressedNodeDef;
  }

  @Override
  public void read(NetInput in) throws IOException {
    long len = in.readUnsignedInt();
    this.compressedNodeDef = in.readBytes((int) len);
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeInt(this.compressedNodeDef.length);
    out.writeBytes(this.compressedNodeDef);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}