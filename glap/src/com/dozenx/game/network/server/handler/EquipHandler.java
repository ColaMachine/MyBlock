package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.model.human.Player;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.ChatServer;
import com.dozenx.game.network.server.PlayerStatus;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.StringUtil;
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
           PlayerStatus info =  serverContext.id2PlayerMap.get(cmd.getUserId());
            if(info==null){
                LogUtil.println(" Player info can't be null userId:"+cmd.getUserId());
                return new ResultCmd(1,"失败",0);

            }
            if(cmd.getPart()==EquipPartType.HEAD){
                info.setHeadEquip(cmd.getItemType().ordinal());
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.BODY){
                info.setBodyEquip(cmd.getItemType().ordinal());
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.LEG){
                info.setHeadEquip(cmd.getItemType().ordinal());
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.SHOE){
                info.setShoeEquip(cmd.getItemType().ordinal());
                //TODO Item 's num may be over 256
            }
            if(cmd.getPart()==EquipPartType.HAND){
                info.setHandEquip(cmd.getItemType().ordinal());
                //TODO Item 's num may be over 256
            }
        }
        //更新其他附近人的此人的装备属性
        broadCast(cmd);
       return new ResultCmd(0,"成功",0);
    }
    public void broadCast(GameCmd cmd){
        serverContext. messages.offer(cmd.toBytes());
    }
}
