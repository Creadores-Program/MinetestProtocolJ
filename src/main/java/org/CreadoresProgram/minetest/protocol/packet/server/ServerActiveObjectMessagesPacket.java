package org.CreadoresProgram.minetest.protocol.packet.server;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.ActiveObjectMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerActiveObjectMessagesPacket implements Packet{
  private List<ActiveObjectMessage> activeObjectMessages;

  @SuppressWarnings("unused")
  private ServerActiveObjectMessagesPacket() {
    this.activeObjectMessages = new ArrayList<>();
  }

  /*
		for all objects
		{
			u16 id
			u16 message length
			string message
		}
  */
  public ServerActiveObjectMessagesPacket(List<ActiveObjectMessage> activeObjectMessages){
    this.activeObjectMessages = activeObjectMessages;
  }
  public List<ActiveObjectMessage> getActiveObjectMessages(){
    return this.activeObjectMessages;
  }

  @Override
  public void read(NetInput in) throws IOException {
    while(in.available() > 0){
        ActiveObjectMessage aom = new ActiveObjectMessage();
        aom.id = in.readUnsignedShort();
        int lenAom = in.readUnsignedShort();
        aom.payload = in.readBytes(lenAom);
        this.activeObjectMessages.add(aom);
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    for(ActiveObjectMessage aom : this.activeObjectMessages){
        out.writeShort(aom.id);
        out.writeShort(aom.payload.length);
        out.writeBytes(aom.payload);
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}