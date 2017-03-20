package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class TotalAttackCmd extends BaseGameCmd{
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
    private int attackValue ;

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public TotalAttackCmd(byte[] bytes){
        parse(bytes);
    }
    public TotalAttackCmd(int sourceId, AttackType type, int targetId){
        this.userId = sourceId;
        this.attackType =type;

            this.targetId = targetId;

    }
    @Override
    public byte[] toBytes(){
        return ByteUtil.createBuffer().put(cmdType.getType())
                .put(userId)
                .put(attackType.ordinal())
                .put(targetId)
                .put(attackValue)
               .array();

    }
    public void parse(byte[] bytes){
        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();

        int attackTypeVal = byteBufferWrap.getInt();
        this.targetId= byteBufferWrap.getInt();
        this.attackValue= byteBufferWrap.getInt();
        if(attackTypeVal>=0){
            this.attackType=AttackType.values()[attackTypeVal];
        }

    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
