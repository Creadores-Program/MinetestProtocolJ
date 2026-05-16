package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.MediaFile;

import java.io.IOException;

public class ServerMediaPacket implements Packet{
  private int totalBunches;
  private int bunchIndex;
  private MediaFile[] files;

  @SuppressWarnings("unused")
  private ServerMediaPacket() {
  }
  
  /*
		u16 total number of bunches
		u16 index of this bunch
		u32 number of files in this bunch
		for each file {
			u16 length of name
			string name
			u32 length of data
			data (zstd-compressed)
		}
  */
  public ServerMediaPacket(int totalBunches, int bunchIndex, MediaFile[] files) {
    this.totalBunches = totalBunches;
    this.bunchIndex = bunchIndex;
    this.files = files;
  }

  public int getTotalBunches(){
    return this.totalBunches;
  }
  public int getBunchIndex(){
    return this.bunchIndex;
  }
  public double getFiles(){
    return this.files;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.totalBunches = in.readUnsignedShort();
    this.bunchIndex = in.readUnsignedShort();
    long filesCount = in.readUnsignedInt();
    this.files = new MediaFile[(int) filesCount];
    for(long i = 0; i < filesCount; i++){
        MediaFile file = new MediaFile();
        file.name = in.readString();
        long dataLen = in.readUnsignedInt();
        file.compressedData = in.readBytes((int) dataLen);
        this.files[i] = file;
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.totalBunches);
    out.writeShort(this.bunchIndex);
    out.writeInt(this.files.length);
    for(MediaFile file : this.files){
        out.writeString(file.name);
        out.writeInt(file.compressedData.length);
        out.writeBytes(file.compressedData);
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}