package com.dozenx.game.network.server.handler;

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
        int userId =0;
        if(request.getCmd().getCmdType() == CmdType.WALK){
            WalkCmd cmd = (WalkCmd) request.getCmd();
             userId = cmd.getUserId();
        }
        if(request.getCmd().getCmdType() == CmdType.WALK2){
            WalkCmd2 cmd = (WalkCmd2) request.getCmd();
             userId = cmd.getUserId();
        }


        //WalkCmd2 cmd =(WalkCmd2)request.getCmd();
        LivingThingBean livingThingBean = userService.getOnlinePlayerById(userId);
        if(livingThingBean!=null){
            livingThingBean.receive(request.getCmd());
        }

        //更新其他附近人的此人的装备属性
        broadCast(request.getCmd());
       return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
