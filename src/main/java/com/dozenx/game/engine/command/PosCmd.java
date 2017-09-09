package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/7.
 */
public class PosCmd extends   BaseGameCmd{

    public int userId;
    public float x;
    public float y;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getBodyAngle() {
        return bodyAngle;
    }

    public void setBodyAngle(float bodyAngle) {
        this.bodyAngle = bodyAngle;
    }

    public float getHeadAngle() {
        return headAngle;
    }

    public void setHeadAngle(float headAngle) {
        this.headAngle = headAngle;
    }

    public float getHeadAngle2() {
        return headAngle2;
    }

    public void setHeadAngle2(float headAngle2) {
        this.headAngle2 = headAngle2;
    }

    public float z;
    public float bodyAngle;
    public float headAngle;
    public float headAngle2;
    final CmdType cmdType = CmdType.POS;
    //private EquipPartType part;
    // userId | x | y | z | bodyAngle | headAngle | headAngle2|

    public PosCmd(byte[] bytes){

        parse(bytes);
    }
    public PosCmd(LivingThing livingThing){
        if(livingThing.getId()==1024 || livingThing.getId()<=0){
            LogUtil.println("that 's bug");
        }
        this.userId =livingThing.getId();
        if(userId<=0){
            LogUtil.err("userId should not be 0");
        }
        this.x = livingThing.position.x;
        this.y = livingThing.position.y;
        if(this.y<0){
            LogUtil.println("1");
        }
        this.z = livingThing.position.z;
        this.bodyAngle = livingThing.getBodyAngle();
        this.headAngle = livingThing.getHeadAngle();
        this.headAngle2 = livingThing.getHeadAngle2();
       // this.part =pos;
    }
    public PosCmd(PlayerStatus playerStatus){

        this.userId =playerStatus.getId();
        if(userId<=0){
            LogUtil.err("userId should not be 0");
        }
        this.x = playerStatus.getX();
        this.y = playerStatus.getY();
        this.z = playerStatus.getZ();
        this.bodyAngle = playerStatus.getBodyAngle();
        this.headAngle = playerStatus.getHeadAngle();
        this.headAngle2 = playerStatus.getHeadAngle2();
        // this.part =pos;
    }


    public byte[] toBytes(){


        return  ByteUtil.createBuffer().put(cmdType.getType())
                .put( userId)
                .put(x)
                .put(y)
                .put(z)
                .put(bodyAngle)
                .put(headAngle)
                .put(headAngle2).array();




    }
    public void parse(byte[] bytes){

        ByteBufferWrap byteBufferWrap = ByteUtil.createBuffer(bytes);
        byteBufferWrap.getInt();
        this.userId= byteBufferWrap.getInt();
        this.x=byteBufferWrap.getFloat();
        this.y=byteBufferWrap.getFloat();
        this.z=byteBufferWrap.getFloat();
        this.bodyAngle = byteBufferWrap.getFloat();
        this.headAngle = byteBufferWrap.getFloat();
        this.headAngle2 = byteBufferWrap.getFloat();


        if(userId==0) LogUtil.err("userId shold not be 0");
       // this.item = TextureManager.getItemDefinition( ItemType.values()[ bytes[1]]);
        //bytes = ByteUtil.getBytes(byteArray,8,4);
      //  this.item = TextureManager.getItemDefinition( ItemType.values()[ ByteUtil.getInt(bytes)]);
    }
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
