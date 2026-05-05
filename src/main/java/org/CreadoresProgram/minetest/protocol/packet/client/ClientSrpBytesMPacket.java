package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientSrpBytesMPacket implements Packet{

  private byte[] bytesM;
  
  @SuppressWarnings("unused")
  private ClientSrpBytesMPacket() {
  }

  /*
		Belonging to AUTH_MECHANISM_SRP.

		std::string bytes_M
  */
  public ClientSrpBytesMPacket(byte[] bytesM){
    this.bytesM = bytesM;
  }

  public byte[] getBytesM(){
    return this.bytesM;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    int len = in.readUnsignedShort();
    this.bytesM = in.readBytes(len);
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.bytesM.length);
    out.writeBytes(this.bytesM);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
