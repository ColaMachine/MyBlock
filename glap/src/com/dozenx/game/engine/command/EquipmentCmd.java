package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class EquipmentCmd implements  GameCmd{
    private boolean deleted;
   // private LivingThing livingThing;

    public ItemDefinition getItem() {
        return item;
    }

    public void setItem(ItemDefinition item) {
        this.item = item;
    }

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

    private ItemDefinition item;
    private int userId;
    private CmdType cmdType = CmdType.EQUIP;
    private EquipPartType part;
    public EquipmentCmd(byte[] bytes){
        parse(bytes);
    }
    public EquipmentCmd(LivingThing live, EquipPartType pos,ItemDefinition item){
       // this.livingThing =live;
        this.item = item;
        this.part =pos;
        this.userId =live.id;

    }

    //equip 4 |userId|part 2|item |itemId|
    public byte[] toBytes(){
       /* return ByteUtil.getBytes(ByteUtil.getBytes((byte)cmdType.ordinal()),
        ByteUtil.getBytes((byte)part.ordinal()),
        ByteUtil.getBytes((byte)item.getItemType().ordinal()));*/
     /*   new byte[]{(byte)cmdType.ordinal(),(byte)part.ordinal(),(byte)item.getItemType().ordinal()};*/
        byte[] bytes =new byte[8];
        byte[] userIdByteAry = ByteUtil.getBytes(userId);
        bytes[0]= (byte)cmdType.ordinal();
        bytes[1]= userIdByteAry[0];
        bytes[2]= userIdByteAry[1];
        bytes[3]= userIdByteAry[2];
        bytes[4]= userIdByteAry[3];
        bytes[5]= (byte)part.ordinal();
        if(item!= null){
            bytes[6]= (byte)item.getItemType().ordinal();
        }else{
            bytes[6]= -1;
        }

       // byte[] userIdByteAry = ByteUtil.getBytes(userId);
       // bytes[7]=
       // ByteUtil.getBytes();
        return bytes;
    }
    public void parse(byte[] bytes){
       // byte[] bytes = ByteUtil.getBytes(byteArray,1,1);
        if(bytes[0]!=CmdType.EQUIP.ordinal()){
            LogUtil.err("hello");
        }
        this.userId =ByteUtil.getInt( ByteUtil.getBytes(bytes,1,4));
        this.part = EquipPartType.values()[bytes[5]];

        if(bytes[6]!=-1){
            this.item = TextureManager.getItemDefinition( ItemType.values()[ bytes[6]]);
        }
        //bytes = ByteUtil.getBytes(byteArray,8,4);
      //  this.item = TextureManager.getItemDefinition( ItemType.values()[ ByteUtil.getInt(bytes)]);
    }
    @Override
    public void delete() {
        this.deleted=true;
    }

    public int val;

    @Override
    public int val(){
        return val;

    }

    public void setVal(int val){
        this.val=val;
    }
}
