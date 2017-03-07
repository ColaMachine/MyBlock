package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.*;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class EquipHandler extends GameServerHandler {

    public EquipHandler(ServerContext serverContext){
        super(serverContext);

    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        LogUtil.println("server接收到装备变化了");
        EquipCmd cmd =(EquipCmd)request.getCmd();
        if(cmd.getCmdType()== CmdType.EQUIP){
            LivingThingBean bean = serverContext.getOnlinePlayerById(cmd.getUserId());
            if(bean ==null ){
                return new ResultCmd(1,"失败",0);
            }
           PlayerStatus info = bean.getInfo() ;//id2PlayerMap.get(cmd.getUserId());

            if(info==null){
                LogUtil.println(" Player info can't be null userId:"+cmd.getUserId());
                return new ResultCmd(1,"失败",0);

            }
            if(cmd.getPart()==EquipPartType.HEAD){
                info.setHeadEquip(ItemType.getTypeVal(cmd.getItemType()));
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.BODY){
                info.setBodyEquip(ItemType.getTypeVal(cmd.getItemType()));
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.LEG){
                info.setHeadEquip(ItemType.getTypeVal(cmd.getItemType()));
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.FOOT){
                info.setFootEquip(ItemType.getTypeVal(cmd.getItemType()));
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.HAND){
                info.setHandEquip(ItemType.getTypeVal(cmd.getItemType()));
                //TODO Item 's num may be over 256
            }
        }
        //更新其他附近人的此人的装备属性
        broadCast(cmd);
       return new ResultCmd(0,"成功",0);
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
