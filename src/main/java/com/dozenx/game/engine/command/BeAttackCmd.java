package com.dozenx.game.engine.command;

import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import com.dozenx.util.SimpleByteBufferWrap;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class BeAttackCmd extends BaseGameCmd{
    private int weaponId;
    private int userId;
    private int targetId;
    private ItemDefinition itemDefinition;
    public GL_Vector direction;
    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getUserId() {
        return userId;

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public void setCmdType(CmdType cmdType) {
        this.cmdType = cmdType;
    }

    private CmdType cmdType = CmdType.ATTACK;
    private int attackValue ;

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public BeAttackCmd(byte[] bytes){
        parse(bytes);
    }



    public BeAttackCmd(int sourceId, ItemDefinition itemDefinition, int  targetId, GL_Vector direction){
        this.userId = sourceId;
        this.itemDefinition=itemDefinition;

            this.targetId = targetId;
        this.direction= direction;

    }
    @Override
    public byte[] toBytes(){
        return ByteUtil.createSimpleBuffer(125).put(cmdType.getType())
                .put(userId)
                .put(itemDefinition.id)
                .put(targetId)
                .put(attackValue)
                .put(direction.x)
                .put(direction.y)
                .put(direction.z)
               .array();

    }
    public void parse(byte[] bytes){
        SimpleByteBufferWrap byteBufferWrap = ByteUtil.createSimpleBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();

        int attackTypeVal = byteBufferWrap.getInt();
        this.targetId= byteBufferWrap.getInt();
        this.itemDefinition = ItemManager.getItemDefinition(byteBufferWrap.getInt());
        this.attackValue= byteBufferWrap.getInt();
        this.direction = new GL_Vector(byteBufferWrap.getFloat(),byteBufferWrap.getFloat(),byteBufferWrap.getFloat());


    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
