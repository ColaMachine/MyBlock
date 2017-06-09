package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.UserService;
import core.log.LogUtil;

/**
 * Created by luying on 17/2/18.
 */
public class WalkHandler extends GameServerHandler {
    UserService userService;
    public WalkHandler(ServerContext serverContext){
        super(serverContext);
        userService = (UserService)serverContext.getService(UserService.class);
    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        LogUtil.println("server接收到走路变化了");
        WalkCmd2 cmd =(WalkCmd2)request.getCmd();
        LivingThingBean livingThingBean = userService.getOnlinePlayerById(cmd.getUserId());

        livingThingBean.receive(cmd);
        //更新其他附近人的此人的装备属性
        broadCast(cmd);
       return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
