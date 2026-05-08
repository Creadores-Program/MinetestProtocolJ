package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientUpdateInfoPacket implements Packet{
  private short renderW, renderH;
  private float guiScaling;
  private float hudScaling;
  private float fsInfoX, fsInfoY;
  @SuppressWarnings("unused")
  private ClientUpdateInfoPacket() {
  }

  /*
		v2s16 render_target_size
		f32 gui_scaling
		f32 hud_scaling
		v2f32 max_fs_info
  */
  public ClientUpdateInfoPacket(short renderW, short renderH, float guiScaling, float hudScaling, float fsInfoX, float fsInfoY){
    this.renderW = renderW;
    this.renderH = renderH;
    this.guiScaling = guiScaling;
    this.hudScaling = hudScaling;
    this.fsInfoX = fsInfoX;
    this.fsInfoY = fsInfoY;
  }

  public short getRenderW(){
    return this.renderW;
  }
  public short getRenderH(){
    return this.renderW;
  }
  public float getGuiScaling(){
    return this.guiScaling;
  }
  public float getHudScaling(){
    return this.hudScaling;
  }
  public float getFsInfoX(){
    return this.fsInfoX;
  }
  public float getFsInfoY(){
    return this.fsInfoY;
  }
  @Override
  public void read(NetInput in) throws IOException {
    this.renderW = in.readShort();
    this.renderH = in.readShort();
    this.guiScaling = in.readFloat();
    this.hudScaling = in.readFloat();
    this.fsInfoX = in.readFloat();
    this.fsInfoY = in.readFloat();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.renderW);
    out.writeShort(this.renderH);
    out.writeFloat(this.guiScaling);
    out.writeFloat(this.hudScaling);
    out.writeFloat(this.fsInfoX);
    out.writeFloat(this.fsInfoY);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}