package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientSrpBytesAPacket implements Packet{

  private byte[] bytesA;
  private int basedOn;
  
  @SuppressWarnings("unused")
  private ClientSrpBytesAPacket() {
  }

  /*
		Belonging to AUTH_MECHANISM_SRP,
			depending on current_login_based_on.

		std::string bytes_A
		u8 current_login_based_on : on which version of the password's
		                            hash this login is based on (0 legacy hash,
		                            or 1 directly the password)
  */
  public ClientSrpBytesAPacket(byte[] bytesA, int basedOn){
    this.bytesA = bytesA;
    this.basedOn = basedOn;
  }

  public byte[] getBytesA(){
    return this.bytesA;
  }
  public int getBasedOn(){
    return this.basedOn;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    int len = in.readUnsignedShort();
    this.bytesA = in.readBytes(len);
    this.basedOn = in.readByte() & 0xFF;
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.bytesA.length);
    out.writeBytes(this.bytesA);
    out.writeByte(this.basedOn);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
