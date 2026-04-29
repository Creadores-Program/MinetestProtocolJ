package org.CreadoresProgram.minetest.protocol.packet.client;

import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientPlayerPosPacket extends Packet{
    private double x, y, z;
    private float speedX, speedY, speedZ;
    private float pitch;
    private float yaw;
    
    private long keyPressed;
    private float fov;
    private int wantedRange;
    private boolean cameraInverted;
    
    private float movementSpeed;
    private float movementDirection;

    @SuppressWarnings("unused")
    private ClientPlayerPosPacket() {
    }

    /*
        v3s32 position*100
		v3s32 speed*100
		s32 pitch*100
		s32 yaw*100
		u32 keyPressed
		u8 fov*80
		u8 ceil(wanted_range / MAP_BLOCKSIZE)
		u8 camera_inverted (bool)
		f32 movement_speed
		f32 movement_direction
    */
    public ClientPlayerPosPacket(double x, double y, double z, float yaw, float pitch, float speedX, float speedY, float speedZ, long keyPressed, float fov, int wantedRange, boolean cameraInverted, float movementSpeed, float movementDirection){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.speedX = speedX;
        this.speedY = speedY;
        this.speedZ = speedZ;
        this.keyPressed = keyPressed;
        this.fov = fov;
        this.wantedRange = wantedRange;
        this.cameraInverted = cameraInverted;
        this.movementSpeed = movementSpeed;
        this.movementDirection = movementDirection;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getZ(){
        return this.z;
    }
    public float getSpeedX(){
        return this.speedX;
    }
    public float getSpeedY(){
        return this.speedY;
    }
    public float getSpeedZ(){
        return this.speedZ;
    }
    public long getKeyPressed(){
        return this.keyPressed;
    }
    public float getFov(){
        return this.fov;
    }
    public int getWantedRange(){
        return this.wantedRange;
    }
    public boolean isCameraInverted(){
        return this.cameraInverted;
    }
    public float getMovementSpeed(){
        return this.movementSpeed;
    }
    public float getMovementDirection(){
        return this.movementDirection;
    }

    @Override
    public void read(NetInput in) throws IOException{
        this.x = in.readInt() / 100.0;
        this.y = in.readInt() / 100.0;
        this.z = in.readInt() / 100.0;
        this.speedX = in.readInt() / 100.0f;
        this.speedY = in.readInt() / 100.0f;
        this.speedZ = in.readInt() / 100.0f;
        this.pitch = in.readInt() / 100.0f;
        this.yaw = in.readInt() / 100.0f;
        this.keyPressed = in.readUnsignedInt();
        this.fov = in.readUnsignedByte() / 80.0f;
        this.wantedRange = in.readUnsignedByte();
        this.cameraInverted = in.readBoolean();
        this.movementSpeed = in.readFloat();
        this.movementDirection = in.readFloat();
    }
    @Override
    public void write(NetOutput out) throws IOException{
        out.writeInt((int) (this.x * 100));
        out.writeInt((int) (this.y * 100));
        out.writeInt((int) (this.z * 100));
        out.writeInt((int) (this.speedX * 100));
        out.writeInt((int) (this.speedY * 100));
        out.writeInt((int) (this.speedZ * 100));
        out.writeInt((int) (this.pitch * 100));
        out.writeInt((int) (this.yaw * 100));
        out.writeInt(this.keyPressed);
        out.writeByte((int) (this.fov * 80));
        out.writeByte(this.wantedRange);
        out.writeBoolean(this.cameraInverted);
        out.writeFloat(this.movementSpeed);
        oyt.writeFloat(this.movementDirection);
    }
    @Override
    public boolean isPriority() {
        return false;
    }
}