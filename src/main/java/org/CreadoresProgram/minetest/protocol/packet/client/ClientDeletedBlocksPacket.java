package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.BlockPos;

import java.io.IOException;

public class ClientDeletedBlocksPacket implements Packet{

  private BlockPos[] blocksPos;
  
  @SuppressWarnings("unused")
  private ClientDeletedBlocksPacket() {
  }

  /*
		[0] u16 command
		[2] u8 count
		[3] v3s16 pos_0
		[3+6] v3s16 pos_1
		...
  */
  public ClientDeletedBlocksPacket(BlockPos[] blocksPos){
    this.blocksPos = blocksPos;
  }

  public BlockPos[] getBlocksPos(){
    return this.blocksPos;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    int len = in.readUnsignedByte();
    this.blocksPos = new BlockPos[len];
    for(int i = 0; i < len; i++){
        this.blocksPos[i] = new BlockPos(in.readShort(), in.readShort(), in.readShort());
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeByte(this.blocksPos.length);
    for(BlockPos blockPos : this.blocksPos){
        out.writeShort(blockPos.getX());
        out.writeShort(blockPos.getY());
        out.writeShort(blockPos.getZ());
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}