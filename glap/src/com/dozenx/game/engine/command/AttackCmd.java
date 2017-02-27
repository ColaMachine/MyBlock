package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class AttackCmd extends BaseGameCmd{
    private AttackType attackType;
    private int userId;
    private int targetId;

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

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public void setCmdType(CmdType cmdType) {
        this.cmdType = cmdType;
    }

    private CmdType cmdType = CmdType.ATTACK;
    public AttackCmd(byte[] bytes){
        parse(bytes);
    }
    public AttackCmd(LivingThing human,AttackType type,LivingThing target){
        this.userId = human.id;
        this.attackType =type;
        if(target == null){
            this.targetId=0;
        }else {
            this.targetId = target.id;
        }
    }
    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(attackType.ordinal())
                .put(targetId)
               .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();

        int attackTypeVal = byteBufferWrap.getInt();
        this.targetId= byteBufferWrap.getInt();

        if(attackTypeVal>=0){
            this.attackType=AttackType.values()[attackTypeVal];
        }

    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
