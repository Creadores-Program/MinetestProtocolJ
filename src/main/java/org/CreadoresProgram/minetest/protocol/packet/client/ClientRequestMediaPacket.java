package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientRequestMediaPacket implements Packet{

  private String[] filesNames;
  
  @SuppressWarnings("unused")
  private ClientRequestMediaPacket() {
  }

  /*
		u16 number of files requested
		for each file {
			u16 length of name
			string name
		}
  */
  public ClientRequestMediaPacket(String[] filesNames){
    this.filesNames = filesNames;
  }

  public String[] getFilesNames(){
    return this.filesNames;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    int len = in.readUnsignedShort();
    this.filesNames = new String[len];
    for(int i = 0; i < len; i++){
        this.filesNames[i] = in.readString();
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.filesNames.length);
    for(String fileName : this.filesNames){
        out.writeString(fileName);
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}