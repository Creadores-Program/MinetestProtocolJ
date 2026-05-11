package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerInventoryPacket implements Packet{
  private String inventoryData;
  private boolean skipWieldAnim;

  @SuppressWarnings("unused")
  private ServerInventoryPacket() {
  }

  /*
		<long string> serialized inventory
		bool skip_wield_anim
  */
  public ServerInventoryPacket(String inventoryData, boolean skipWieldAnim){
    this.inventoryData = inventoryData;
    this.skipWieldAnim = skipWieldAnim;
  }
  public String getInventoryData(){
    return this.inventoryData;
  }
  public boolean isSkipWieldAnim(){
    return this.skipWieldAnim;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.inventoryData = in.readString();
    if(in.available() < 1){
        return;
    }
    this.skipWieldAnim = in.readBoolean();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeString(this.inventoryData);
    out.writeBoolean(this.skipWieldAnim);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}