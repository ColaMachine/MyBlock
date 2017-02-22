package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.util.ByteBufferWrap;
import core.log.LogUtil;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public class PosCmd extends   BaseGameCmd{

    public int userId;
    public float x;
    public float y;
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
        this.userId =livingThing.id;
        this.x = livingThing.position.x;
        this.y = livingThing.position.y;
        this.z = livingThing.position.z;
        this.bodyAngle = livingThing.bodyAngle;
        this.headAngle = livingThing.headAngle;
        this.headAngle2 = livingThing.headAngle2;
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

       // byte[] bytes = ByteUtil.getBytes(byteArray,1,1);
        //this.part = EquipPartType.values()[bytes[1]];
        this.userId = ByteUtil.getInt(ByteUtil.getBytes(bytes,1,4));
        this.x = byteBufferWrap.getFloat();
        this.y = byteBufferWrap.getFloat();
        this.z =byteBufferWrap.getFloat();
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
