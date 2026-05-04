package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientHaveMediaPacket implements Packet{

  private long[] tokens;
  
  @SuppressWarnings("unused")
  private ClientHaveMediaPacket() {
  }

  /*
		u8 number of callback tokens
		for each:
			u32 token
  */
  public ClientHaveMediaPacket(long[] tokens){
    this.tokens = tokens;
  }

  public long[] getTokens(){
    return this.tokens;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    int len = in.readUnsignedByte();
    this.tokens = new int[len];
    for(int i = 0; i < len; i++){
        this.tokens[i] = in.readInt() & 0xFFFFFFFFL;
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    int len = this.tokens.length;
    out.writeByte(len);
    for(int i = 0; i < len; i++){
        out.writeInt((int) this.tokens[i]);
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}