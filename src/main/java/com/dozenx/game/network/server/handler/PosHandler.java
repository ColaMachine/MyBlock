package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.PosCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.UserService;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class PosHandler extends GameServerHandler {
    UserService userService;
    public PosHandler(ServerContext serverContext){
        super(serverContext);
        userService = (UserService)serverContext.getService(UserService.class);
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        PosCmd posCmd =(PosCmd) request.getCmd();
        int userId = posCmd.getUserId();
        LivingThingBean livingThingBean  = userService.getOnlinePlayerById(userId);
       // PlayerStatus playerStatus =  userService.getOnlinePlayerById(userId).getInfo();
        if(livingThingBean==null){
            LogUtil.err("userId:"+userId+" find playerStatus is null");
        }

        livingThingBean.setX(posCmd.getX());
        livingThingBean.setY(posCmd.getY());
        livingThingBean.setZ(posCmd.getZ());

        //TODO
        livingThingBean.setBodyAngle(posCmd.getBodyAngle());
        livingThingBean.setHeadAngle(posCmd.getBodyAngle());
        livingThingBean.setHeadAngle2(posCmd.getBodyAngle());

        broadCast(request.getCmd());
        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
