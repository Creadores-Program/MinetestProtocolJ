package org.CreadoresProgram.minetest.protocol.packet.server;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ServerFovPacket implements Packet{
  private float fov;
  private boolean multiplier;
  private float transitionTime;

  @SuppressWarnings("unused")
  private ServerFovPacket() {
  }
  
  /*
		Sends an FOV override/multiplier to client.

		f32 fov
		bool is_multiplier
		f32 transition_time
  */
  public ServerFovPacket(float fov, boolean multiplier, float transitionTime) {
    this.fov = fov;
    this.multiplier = multiplier;
    this.transitionTime = transitionTime;
  }

  public float getFov(){
    return this.fov;
  }
  public boolean isMultiplier(){
    return this.multiplier;
  }
  public float getTransitionTime(){
    return this.transitionTime;
  }

  @Override
  public void read(NetInput in) throws IOException {
    this.fov = in.readFloat();
    this.multiplier = in.readBoolean();
    this.transitionTime = in.readFloat();
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeFloat(this.fov);
    out.writeBoolean(this.multiplier);
    out.writeFloat(this.transitionTime);
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}