package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.ActiveObject;

import java.io.IOException;

public class ServerActiveObjectRemoveAddPacket implements Packet{
  private ActiveObject[] activeObjectsAdd;
  private int[] activeObjectsRemoveIds;

  @SuppressWarnings("unused")
  private ServerActiveObjectRemoveAddPacket() {
  }

  /*
		u16 count of removed objects
		for all removed objects {
			u16 id
		}
		u16 count of added objects
		for all added objects {
			u16 id
			u8 type
			u32 initialization data length
			string initialization data
		}
  */
  public ServerActiveObjectRemoveAddPacket(ActiveObject[] activeObjectsAdd, int[] activeObjectsRemoveIds){
    this.activeObjectsAdd = activeObjectsAdd;
    this.activeObjectsRemoveIds = activeObjectsRemoveIds;
  }
  public ActiveObject[] getActiveObjectsAdd(){
    return this.activeObjectsAdd;
  }
  public int[] getActiveObjectsRemoveIds(){
    return this.activeObjectsRemoveIds;
  }

  @Override
  public void read(NetInput in) throws IOException {
    int lenR = in.readUnsignedShort();
    this.activeObjectsRemoveIds = new int[lenR];
    for(int i = 0; i < lenR; i++){
      this.activeObjectsRemoveIds[i] = in.readUnsignedShort();
    }
    int lenA = in.readUnsignedShort();
    this.activeObjectsAdd = new ActiveObject[lenA];
    for(int i = 0; i < lenA; i++){
      ActiveObject aob = new ActiveObject();
      aob.id = in.readUnsignedShort();
      aob.type = in.readUnsignedByte();
      long lenO = in.readUnsignedInt();
      aob.data = in.readBytes((int) lenO);
      this.activeObjectsAdd[i] = aob;
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.activeObjectsRemoveIds.length);
    for(int activeObjectId : this.activeObjectsRemoveIds){
      out.writeShort(activeObjectId);
    }
    out.writeShort(this.activeObjectsAdd.length);
    for(ActiveObject activeObject : this.activeObjectsAdd){
      out.writeShort(activeObject.id);
      out.writeByte(activeObject.type);
      byte[] data = activeObject.data;
      out.writeInt(data.length);
      out.writeBytes(data);
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}