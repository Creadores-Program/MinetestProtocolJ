package org.CreadoresProgram.minetest.protocol.packet.server;

import org.CreadoresProgram.minetest.protocol.data.game.MapBlock;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerBlockDataPacket implements Packet{
  private short x, y, z;
  private MapBlock mapBlock;

  @SuppressWarnings("unused")
  private ServerBlockDataPacket() {
  }

  /*
		v3s16 position
		serialized MapBlock
  */
  public ServerBlockDataPacket(short x, short y, short z, MapBlock mapBlock){
    this.x = x;
    this.y = y;
    this.z = z;
    this.mapBlock = mapBlock;
  }
  public short getX(){
    return this.x;
  }
  public short getY(){
    return this.y;
  }
  public short getZ(){
    return this.z;
  }
  public MapBlock getMapBlock(){
    return this.mapBlock;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readShort();
    this.y = in.readShort();
    this.z = in.readShort();
    this.mapBlock = MapBlock.decode(in.readBytes(in.available()));
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.x);
    out.writeShort(this.y);
    out.writeShort(this.z);
    out.writeBytes(this.mapBlock.encode());
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}