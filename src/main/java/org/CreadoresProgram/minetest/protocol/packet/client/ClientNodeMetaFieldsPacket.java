package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class ClientNodeMetaFieldsPacket implements Packet{

  private short x, y, z;

  private String formName;

  private Map<String, String> fields;
  
  @SuppressWarnings("unused")
  private ClientNodeMetaFieldsPacket() {
    this.fields = new HashMap<>();
  }

  /*
		v3s16 p
		u16 len
		u8[len] form name (reserved for future use)
		u16 number of fields
		for each field:
			u16 len
			u8[len] field name
			u32 len
			u8[len] field value
  */
  public ClientNodeMetaFieldsPacket(short x, short y, short z, String formName, Map<String, String> fields){
    this.x = x;
    this.y = y;
    this.z = z;
    this.formName = formName;
    this.fields = fields;
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
  public String getFormName(){
    return this.formName;
  }
  public Map<String, String> getFields(){
    return this.fields;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.x = in.readShort();
    this.y = in.readShort();
    this.z = in.readShort();
    this.formName = in.readString();
    int len = in.readUnsignedShort();
    for(int i = 0; i < len; i++){
        this.fields.put(in.readString(), in.readString());
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeShort(this.x);
    out.writeShort(this.y);
    out.writeShort(this.z);
    out.writeString(this.formName);
    out.writeShort(this.fields.size());
    for(Map.Entry<String, String> entry : this.fields.entrySet()){
        out.writeString(entry.getKey());
        out.writeString(entry.getValue());
    }
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
