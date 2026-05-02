package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.PointedThing;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

public class ClientInteractPacket implements Packet{

  private int action;

  private int itemIndex;

  private PointedThing pointed;

  @SuppressWarnings("unused")
  private ClientInteractPacket() {
  }

  /*
  		[0] u16 command
        [2] u8 action
        [3] u16 item
        [5] u32 length of the next item
        [9] serialized PointedThing
    	actions:
        0 start digging (from undersurface) or use
        1: stop digging (all parameters ignored)
        2: digging completed
        3: place block or item (to abovesurface)
        4: use item
  */
  public ClientInteractPacket(int action, int itemIndex, PointedThing pointed){
    this.action = action;
    this.itemIndex = itemIndex;
    this.pointed = pointed;
  }
  public int getAction(){
    return this.action;
  }
  public int getItemIndex(){
    return this.itemIndex;
  }
  public PointedThing getPointed(){
    return this.pointed;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.action = in.readByte();
    this.itemIndex = in.readShort();
    int length = in.readInt();
    ByteBuf temBuf = Unpooled.wrappedBuffer(in.readBytes(length));
    this.pointed = PointedThing.deserialize(temBuf);
    temBuf.release();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.action);
    out.writeShort(this.itemIndex);
    ByteBuf temBuf = Unpooled.buffer();
    this.pointed.serialize(temBuf);
    out.writeInt(temBuf.readableBytes());
    out.writeBytes(temBuf.array());
    temBuf.release();
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
