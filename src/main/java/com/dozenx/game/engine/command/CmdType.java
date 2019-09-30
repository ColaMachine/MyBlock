package com.dozenx.game.engine.command;

import com.dozenx.game.network.server.handler.*;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum CmdType {
    SAY(SayCmd.class,SayHandler.class),//0 讲话
    RESULT(ResultCmd.class),//1 结果 服务端返回给客户端
    MSG(MsgCmd.class),//2
    LOGIN(LoginCmd.class,LoginHandler.class),//3  登录
    EQUIP(EquipCmd.class, EquipHandler.class),//4   装备
    POS(PosCmd.class, PosHandler.class),//5  pos 位置变化
    PLAYERSTATUS(PlayerSynCmd.class),//6
    BAG(BagCmd.class,BagHandler.class),//7
    BAGCHANGE(BagChangeCmd.class,BagChangeHandler.class),//8
    BOXITEM(BoxItemsReqCmd.class,BoxChangeHandler.class),//8
    BOXOPEN(BoxOpenCmd.class,BoxChangeHandler.class),//8
    GET(GetCmd.class,GetHandler.class),//9


    FileStart(FileStartCmd.class,FileHandler.class),//9
    FileData(FileDataCmd.class,FileHandler.class),//9
    FileEnd(FileEndCmd.class,FileHandler.class),//9

    PICK(PickCmd.class,PickHandler.class),//10
    ATTACK(AttackCmd.class,AttackHandler.class),//1
    BEATTACK(BeAttackCmd.class,AttackHandler.class),//1
    DROP(DropCmd.class,DropHandler.class),//12
    //GROUP(),//13
    CHUNKREQUEST(ChunkRequestCmd.class,ChunkHandler.class),//14
    CHUNKRESPONSE(ChunkResponseCmd.class,ChunkHandler.class),//15
    CHUNKS(ChunksCmd.class),//16
    CHUNKSS(ChunkssCmd.class),//17
    BACK(WalkBackCmd.class),
    JUMP(JumpCmd.class,JumpHandler.class),
    DIED(DiedCmd.class),
    REBORN(RebornCmd.class),
    CHASE(ChaseCmd.class),
    ROTATE(RotateCmd.class),
    /*LIVI(),

    */

    WALK(WalkCmd.class),
    WALK2(WalkCmd.class);

    /*public CmdType(class<T> extends BaseGameCmd,class<T> extends GameServerHandler){


    }*/
    public static Map<Integer ,Class< ? extends BaseGameCmd>> idToClassMap =new HashMap<>();


    static{//遍历 放入

        for(CmdType cmdType:CmdType.values()){
            idToClassMap.put(cmdType.ordinal(),cmdType.cmdClass);
        }


    }


    CmdType(Class< ? extends BaseGameCmd> cmd,Class<? extends  GameServerHandler> cmdClass){
        this.cmdClass = cmd;
         this.gameHandlerClass = cmdClass;
    }
    public Class cmdClass;
    public Class gameHandlerClass;
    CmdType(Class< ? extends BaseGameCmd> cmdClass){
        this.cmdClass = cmdClass;
    }
    public int getType(){
        return this.ordinal();
    }


    public static void printSend(GameCmd type){


        if(type.getCmdType() != CmdType.POS){
            LogUtil.println("send "+type.getCmdType());
        }
    }

    public static void printReceive(GameCmd type){
        if(type.getCmdType() != CmdType.POS) {
            LogUtil.println("receive " + type.getCmdType());
        }
    }

    public static void printSend(byte[] bytes){
        //LogUtil.println("send "+CmdType.values()[ByteUtil.getInt(bytes)]);
        if(ByteUtil.getInt(bytes) != CmdType.POS.ordinal()){
            LogUtil.println("send "+CmdType.values()[ByteUtil.getInt(bytes) ] +" content"+bytes.length);
        }
    }

    public static void printReceive(byte[] bytes){
        if(ByteUtil.getInt(bytes) != CmdType.POS.ordinal()){
           // LogUtil.println("receive "+CmdType.values()[ByteUtil.getInt(bytes)]+" content"+bytes.length);
        }

    }
}

