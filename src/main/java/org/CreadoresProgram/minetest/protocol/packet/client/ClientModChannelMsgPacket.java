package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;

public class ClientModChannelMsgPacket implements Packet{
    private String channelName;
    private String message;

    @SuppressWarnings("unused")
    private ClientModChannelMsgPacket() {
    }

    /*
        u16 channel name length
	 	std::string channel name
	 	u16 message length
	 	std::string message
    */
    public ClientModChannelMsgPacket(String channelName, String message){
        this.channelName = channelName;
        this.message = message;
    }

    public String getChannelName(){
        return this.channelName;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public void read(NetInput in) throws IOException {
        this.channelName = in.readString();
        this.message = in.readString();
    }
    @Override
    public void write(NetOutput out) throws IOException {
        out.writeString(this.channelName);
        out.writeString(this.message);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}