package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerAccessDeniedPacket implements Packet{

  private int reasonCode;
  private String customReason;
  private boolean reconnect;

  @SuppressWarnings("unused")
  private ServerAccessDeniedPacket() {
  }

  /*
		u8 reason
		std::string custom reason (if needed, otherwise "")
		u8 (bool) reconnect
  */
  public ServerAccessDeniedPacket(int reasonCode, String customReason, boolean reconnect){
    this.reasonCode = reasonCode;
    this.customReason = customReason;
    this.reconnect = reconnect;
  }

  public int getReasonCode(){
    return this.reasonCode;
  }
  public String getCustomReason(){
    return this.customReason;
  }
  public boolean isReconnect(){
    return this.reconnect;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.reasonCode = in.readUnsignedByte();
    if(in.available() >= 2){
        this.customReason = in.readString();
    }
    if(in.available() >= 1){
        this.reconnect = in.readBoolean();
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.reasonCode);
    out.writeString(this.customReason);
    out.writeBoolean(this.reconnect);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}