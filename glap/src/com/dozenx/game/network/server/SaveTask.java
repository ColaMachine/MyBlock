package com.dozenx.game.network.server;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.UserService;
import core.log.LogUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TimerTask;

/**
 * Created by luying on 16/10/7.
 */
public class SaveTask extends TimerTask {
  ServerContext serverContext;

    UserService userService;
    BagService bagService;
    public SaveTask(ServerContext serverContext){
        this.serverContext = serverContext;
        userService = (UserService)serverContext.getService(UserService.class);
        bagService = (BagService)serverContext.getService(BagService.class);
    }
    public void run(){

        //保存用户信息
        //保存包裹信息
        for(LivingThingBean livingThing:userService.getAllOnlinePlayer()){
            userService.save(livingThing.getInfo());
            bagService.save(livingThing.getId(),bagService.getItemByUserId(livingThing.getId()));

        }
    }

}
