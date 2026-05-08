package org.CreadoresProgram.minetest.protocol.packet.client;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;

import org.CreadoresProgram.minetest.protocol.data.game.InventoryLocation;
import org.CreadoresProgram.minetest.protocol.data.game.InventoryActionType;

import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class ClientInventoryActionPacket implements Packet{

  private static final String SERIALIZE_MOVE = InventoryActionType.MOVE + " %d %s %s %d %s %s %d";
  private static final String SERIALIZE_MOVESOMEWHERE = InventoryActionType.MOVESOMEWHERE + " %d %s %s %d %s";
  private static final String SERIALIZE_DROP = InventoryActionType.DROP + " %d %s %s %d";
  private static final String SERIALIZE_CRAFT = InventoryActionType.CRAFT + " %d %s";

  private String serializedAction;
  private String actionType;
  private int count;
  private int fromIdx = -1, toIdx = -1;
  private InventoryLocation from, to;
  private String fromList, toList;

  @SuppressWarnings("unused")
  private ClientInventoryActionPacket() {
  }

  //MOVE
  public ClientInventoryActionPacket(int count, InventoryLocation from, String fromList, int fromIdx, InventoryLocation to, String toList, int toIdx){
    this.actionType = InventoryActionType.MOVE;
    this.serializedAction = String.format(SERIALIZE_MOVE, count, from.toString(), fromList, fromIdx, to.toString(), toList, toIdx);
    this.count = count;
    this.from = from;
    this.fromList = fromList;
    this.fromIdx = fromIdx;
    this.to = to;
    this.toList = toList;
    this.toIdx = toIdx;
  }

  //MOVESOMEWHERE
  public ClientInventoryActionPacket(int count, InventoryLocation from, String fromList, int fromIdx, InventoryLocation to, String toList){
    this.actionType = InventoryActionType.MOVESOMEWHERE;
    this.serializedAction = String.format(SERIALIZE_MOVESOMEWHERE, count, from.toString(), fromList, fromIdx, to.toString(), toList);
    this.count = count;
    this.from = from;
    this.fromList = fromList;
    this.fromIdx = fromIdx;
    this.to = to;
    this.toList = toList;
  }

  //DROP
  public ClientInventoryActionPacket(int count, InventoryLocation from, String fromList, int fromIdx){
    this.actionType = InventoryActionType.DROP;
    this.serializedAction = String.format(SERIALIZE_DROP, count, from.toString(), fromList, fromIdx);
    this.count = count;
    this.from = from;
    this.fromList = fromList;
    this.fromIdx = fromIdx;
  }

  //CRAFT
  public ClientInventoryActionPacket(int count, InventoryLocation from){
    this.actionType = InventoryActionType.CRAFT;
    this.serializedAction = String.format(SERIALIZE_CRAFT, count, from.toString());
    this.count = count;
    this.from = from;
  }

  public String getSerializedAction(){
    return this.serializedAction;
  }
  public int getCount(){
    return this.count;
  }
  public String getActionType(){
    return this,actionType;
  }
  public InventoryLocation getFrom(){
    return this.from;
  }
  public String getFromList(){
    return this.fromList;
  }
  public int getFromIdx(){
    return this.fromIdx;
  }
  public InventoryLocation getTo(){
    return this.to;
  }
  public String getToList(){
    return this.toList;
  }
  public int getToIdx(){
    return this.toIdx;
  }
  
  @Override
  public void read(NetInput in) throws IOException {
    this.serializedAction = new String(in.readBytes(in.available()), StandardCharsets.UTF_8).trim();
    String[] parts = this.serializedAction.split(" ");
    if (parts.length < 2) return;
    this.actionType = parts[0];
    this.count = Integer.parseInt(parts[1]);
    switch(actionType){
      case InventoryActionType.MOVE:
        this.from = InventoryLocation.parseLocation(parts[2]);
        this.fromList = parts[3];
        this.fromIdx = Integer.parseInt(parts[4]);
        this.to = InventoryLocation.parseLocation(parts[5]);
        this.toList = parts[6];
        this.toIdx = parts[7];
        break;
      case InventoryActionType.MOVESOMEWHERE:
        this.from = InventoryLocation.parseLocation(parts[2]);
        this.fromList = parts[3];
        this.fromIdx = Integer.parseInt(parts[4]);
        this.to = InventoryLocation.parseLocation(parts[5]);
        this.toList = parts[6];
        break;
      case InventoryActionType.DROP:
        this.from = InventoryLocation.parseLocation(parts[2]);
        this.fromList = parts[3];
        this.fromIdx = Integer.parseInt(parts[4]);
        break;
      case InventoryActionType.CRAFT:
        this.from = InventoryLocation.parseLocation(parts[2]);
        break;
    }
  }
  @Override
  public void write(NetOutput out) throws IOException {
    out.writeBytes(this.serializedAction.getBytes(StandardCharsets.UTF_8));
  }
  @Override
  public boolean isPriority() {
    return false;
  }
}
