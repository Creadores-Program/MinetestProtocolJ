package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientChatMessagePacket implements Packet{

  private String message;
  
  @SuppressWarnings("unused")
  private ClientChatMessagePacket() {
  }

  /*
		u16 message length
	 	std::string message
	 */
  public ClientChatMessagePacket(String message){
    this.message = message;
  }

  public String getMessage(){
    return this.message;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.message = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeString(this.message);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
