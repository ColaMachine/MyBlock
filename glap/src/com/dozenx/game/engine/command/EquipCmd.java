package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.util.ByteBufferWrap;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class EquipCmd extends   BaseGameCmd{
    private boolean deleted;
   // private LivingThing livingThing;

   /* public ItemDefinition getItem() {
        return item;
    }

    public void setItem(ItemDefinition item) {
        this.item = item;
    }*/

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public EquipPartType getPart() {
        return part;
    }

    public void setPart(EquipPartType part) {
        this.part = part;
    }
    private ItemType itemType;
   // private ItemDefinition item;
    private int userId;
    final CmdType cmdType = CmdType.EQUIP;

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    private EquipPartType part;

    public EquipCmd(byte[] bytes){
        parse(bytes);
    }
    public EquipCmd(LivingThing live, EquipPartType pos, ItemDefinition item){
       // this.livingThing =live;
        if(item!=null){
            this.itemType = item.getItemType();
        }else{

        }

        this.part =pos;
        this.userId =live.getId();

    }

    //equip 4 |userId|part 2|item |itemId todo|
    @Override
    public byte[] toBytes(){

        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(getPart().getType())
                .put(itemType == null ?-1: itemType.getType()).array();




    }

    @Override
    public CmdType getCmdType() {
        return cmdType;
    }

    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        this.part = EquipPartType.values()[byteBufferWrap.get()];
        int itemTypeVal = byteBufferWrap.getInt();

        if(itemTypeVal>=0){
            this.itemType=ItemType.values()[itemTypeVal];
        }

    }

}
