package com.dozenx.game.engine.command;

import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.util.ByteBufferWrap;
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
    private Integer itemType;

    private int itemId;
   // private ItemDefinition item;
    private int userId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    final CmdType cmdType = CmdType.EQUIP;

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    private EquipPartType part;

    public EquipCmd(byte[] bytes){
        parse(bytes);
    }
    public EquipCmd(Role live, EquipPartType pos, ItemBean itemBean){
       // this.livingThing =live;
        if(itemBean!=null){
            this.itemType = itemBean.getItemDefinition().getItemType();
            this.itemId=itemBean.getId();
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
                .put(itemType == null ?-1: itemType).put(itemId).array();




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
            this.itemType=itemTypeVal;
        }
        this.itemId = byteBufferWrap.getInt();;

    }

}
