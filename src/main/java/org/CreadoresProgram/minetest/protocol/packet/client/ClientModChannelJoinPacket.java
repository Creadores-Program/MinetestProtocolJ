package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientModChannelJoinPacket implements Packet{

  private String channelName;
  
  @SuppressWarnings("unused")
  private ClientModChannelJoinPacket() {
  }

  /*
		u16 channel name length
	 	std::string channel name
	 */
  public ClientModChannelJoinPacket(String channelName){
    this.channelName = channelName;
  }

  public String getChannelName(){
    return this.channelName;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.channelName = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeString(this.channelName);
  }
}
