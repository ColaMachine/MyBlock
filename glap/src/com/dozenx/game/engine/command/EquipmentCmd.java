package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class EquipmentCmd implements  GameCmd{
    private boolean deleted;
    private LivingThing livingThing;
    private ItemDefinition item;
    private CmdType cmdType = CmdType.EQUIP;
    private EquipPosType pos;
    public EquipmentCmd(LivingThing live, EquipPosType pos,ItemDefinition item){
        this.livingThing =live;
        this.item = item;
        this.pos =pos;
    }

    //equip |pos|item |itemId|
    public byte[] toByte(){
        return ByteUtil.getBytes(ByteUtil.getBytes((byte)cmdType.ordinal()),
        ByteUtil.getBytes((byte)pos.ordinal()),
        ByteUtil.getBytes((byte)item.getItemType().ordinal()));
    }
    public void parse(byte[] byteArray){
        byte[] bytes = ByteUtil.getBytes(byteArray,4,4);
        this.pos = pos.values()[ ByteUtil.getInt(bytes)];
        bytes = ByteUtil.getBytes(byteArray,8,4);
        this.item = TextureManager.getItemDefinition( ItemType.values()[ ByteUtil.getInt(bytes)]);
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
