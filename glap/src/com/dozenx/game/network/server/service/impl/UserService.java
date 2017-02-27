package com.dozenx.game.network.server.service.impl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.alibaba.fastjson.TypeReference;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.IUserService;
import com.dozenx.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by luying on 17/2/18.
 */
public class UserService implements IUserService {
    private ServerContext serverContext;
    public UserService(ServerContext serverContext){
        this.serverContext = serverContext;

    }

    public LivingThingBean getUserInfoByUserName(String userName){

        return serverContext.getAllPlayerByName(userName);

    }

    public void addNew(PlayerStatus playerStatus){
       // serverContext.addNew(playerStatus);

        serverContext.addNewPlayer(playerStatus);
       // serverContext.name2PlayerMap.put(playerStatus.getName(),playerStatus);
       // serverContext.id2PlayerMap.put(playerStatus.getId(),playerStatus);

    }
    /*public PlayerStatus getUserInfoByUserName(int id){
        if(serverContext.name2PlayerMap == null){
            loadAllUserInfo();

        }
        return serverContext.id2PlayerMap.get(id);

    }*/


}
