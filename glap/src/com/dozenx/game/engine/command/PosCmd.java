package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import com.dozenx.util.ByteUtil;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

/**
 * Created by luying on 17/2/7.
 */
public class PosCmd implements  GameCmd{
    private boolean deleted;
    public int userId;
    public float x;
    public float y;
    public float z;
    public float bodyAngle;
    public float headAngle;
    public float headAngle2;
    private CmdType cmdType = CmdType.POS;
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

    //equip 4 |part 2|item |itemId|
    public byte[] toBytes(){
        //ByteBuffer byteBuffer = BufferUtils.createByteBuffer(10);
        //byteBuffer.putFloat();
        return /*ByteUtil.getBytes(ByteUtil.getBytes((byte)cmdType.ordinal()),
        ByteUtil.getBytes((byte)part.ordinal()),
        ByteUtil.getBytes((byte)item.getItemType().ordinal()));*/
                ByteUtil.getBytes(new byte[]{(byte)cmdType.ordinal()},ByteUtil.getBytes(x),ByteUtil.getBytes(y),ByteUtil.getBytes(z),ByteUtil.getBytes(bodyAngle),
                        ByteUtil.getBytes(headAngle),
                        ByteUtil.getBytes(headAngle2));
       // new byte[]{(byte)cmdType.ordinal(),(byte)part.ordinal(),(byte)item.getItemType().ordinal()};
    }
    public void parse(byte[] bytes){
       // byte[] bytes = ByteUtil.getBytes(byteArray,1,1);
        //this.part = EquipPartType.values()[bytes[1]];
        this.x = ByteUtil.getFloat(ByteUtil.getBytes(bytes,1,4));
        this.y = ByteUtil.getFloat(ByteUtil.getBytes(bytes,5,4));
        this.z = ByteUtil.getFloat(ByteUtil.getBytes(bytes,9,4));
        this.bodyAngle = ByteUtil.getFloat(ByteUtil.getBytes(bytes,13,4));
        this.headAngle = ByteUtil.getFloat(ByteUtil.getBytes(bytes,17,4));
        this.headAngle2 = ByteUtil.getFloat(ByteUtil.getBytes(bytes,21,4));
        this.userId = ByteUtil.getInt(ByteUtil.getBytes(bytes, 25, 4));
        if(userId==0) LogUtil.err("userId shold not be 0");
       // this.item = TextureManager.getItemDefinition( ItemType.values()[ bytes[1]]);
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
