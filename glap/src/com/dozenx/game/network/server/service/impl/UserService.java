package com.dozenx.game.network.server.service.impl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.alibaba.fastjson.TypeReference;
import com.dozenx.game.network.server.PlayerStatus;
import com.alibaba.fastjson.JSON;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.IUserService;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luying on 17/2/18.
 */
public class UserService implements IUserService {
    private ServerContext serverContext;
    public UserService(ServerContext serverContext){
        this.serverContext = serverContext;
        loadAllUserInfo();
    }

    public PlayerStatus getUserInfoByUserName(String userName){

        return serverContext.name2PlayerMap.get(userName);

    }
    public void save(PlayerStatus playerStatus){

        serverContext.name2PlayerMap.put(playerStatus.getName(),playerStatus);
       // serverContext.id2PlayerMap.put(playerStatus.getId(),playerStatus);
        File file = PathManager.getInstance().getHomePath().resolve("upd.txt").toFile();
        try {
            FileUtil.writeFile(file,JSON.toJSONString(serverContext.name2PlayerMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PlayerStatus getUserInfoByUserName(int id){
        if(serverContext.name2PlayerMap == null){
            loadAllUserInfo();

        }
        return serverContext.id2PlayerMap.get(id);

    }

    public void loadAllUserInfo(){
        File file = PathManager.getInstance().getHomePath().resolve("upd.txt").toFile();

        try {
            String s = FileUtil.readFile2Str(file);;
            serverContext.name2PlayerMap = JSON.parseObject(s,
                    new TypeReference<Map<String, PlayerStatus>>() {
                    });
            /*List<String> list  = FileUtil.readFile2List(file);
            for(String s : list){
                if(StringUtil.isNotEmpty(s)){
                    JSON.parse(s);
                    PlayerStatus playerStatus = JSON.parseObject(s,PlayerStatus.class);
                    serverContext.name2InfoMap.put(playerStatus.getName(),playerStatus);
                    //serverContext.id2PlayerMap.put(playerStatus.getId(),playerStatus);
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
