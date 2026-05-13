package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerMediaPushPacket implements Packet{
  private String rawHash;
  private String filename;
  private long callbackToken;
  private boolean shouldCache;

  @SuppressWarnings("unused")
  private ServerMediaPushPacket() {
  }

  /*
		std::string raw_hash
		std::string filename
		u32 callback_token
		bool should_be_cached
	*/
  public ServerMediaPushPacket(String rawHash, String filename, long callbackToken, boolean shouldCache){
    this.rawHash = rawHash;
    this.filename = filename;
    this.callbackToken = callbackToken;
    this.shouldCache = shouldCache;
  }
  public String getRawHash(){
    return this.rawHash;
  }
  public String getFilename(){
    return this.filename;
  }
  public long getCallbackToken(){
    return this.callbackToken;
  }
  public boolean isShouldCache(){
    return this.shouldCache;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.rawHash = in.readString();
    this.filename = in.readString();
    this.callbackToken = in.readUnsignedInt();
    this.shouldCache = in.readBoolean();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeString(this.rawHash);
    out.writeString(this.filename);
    out.writeInt((int) this.callbackToken);
    out.writeBoolean(this.shouldCache);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}