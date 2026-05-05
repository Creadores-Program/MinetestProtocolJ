package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientFirstSrpPacket implements Packet{

  private String salt;
  private String verifier;
  private boolean empty;
  
  @SuppressWarnings("unused")
  private ClientFirstSrpPacket() {
  }

  /*
		Belonging to AUTH_MECHANISM_FIRST_SRP.

		std::string srp salt
		std::string srp verification key
		u8 is_empty (=1 if password is empty, 0 otherwise)
  */
  public ClientFirstSrpPacket(String salt, String verifier, boolean empty){
    this.salt = salt;
    this.verifier = verifier;
    this.empty = empty;
  }

  public String getSalt(){
    return this.salt;
  }

  public String getVerifier(){
    return this.verifier;
  }

  public boolean isEmpty(){
    return this.empty;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.salt = in.readString();
    this.verifier = in.readString();
    this.empty = in.readBoolean();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeString(this.salt);
    out.writeString(this.verifier);
    out.writeBoolean(this.empty);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}