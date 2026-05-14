package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerChatMessagePacket implements Packet{
  private byte version;
  private byte type;
  private String senderName;
  private String message;

  @SuppressWarnings("unused")
  private ServerChatMessagePacket() {
  }

  /*
		u8 version
		u8 message_type
		u16 sendername length
		wstring sendername
		u16 length
		wstring message
  */
  public ServerChatMessagePacket(byte version, byte type, String senderName, String message){
    this.version = version;
    this.type = type;
    this.senderName = senderName;
    this.message = message;
  }
  public byte getVersion(){
    return this.version;
  }
  public byte getType(){
    return this.type;
  }
  public String getSenderName(){
    return this.senderName;
  }
  public String getMessage(){
    return this.message;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.version = in.readByte();
    this.type = in.readByte();
    this.senderName = in.readString();
    this.message = in.readString();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.version);
    out.writeByte(this.type);
    out.writeString(this.senderName);
    out.writeString(this.message);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}