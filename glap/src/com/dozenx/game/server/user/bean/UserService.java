package com.dozenx.game.server.user.bean;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.server.PlayerStatus;
import com.alibaba.fastjson.JSON;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luying on 17/2/18.
 */
public class UserService {
    private HashMap<String ,PlayerStatus> name2InfoMap ;
    private HashMap<Integer ,PlayerStatus> id2InfoMap ;

    public PlayerStatus getUserInfoByUserName(String userName){
        if(name2InfoMap == null){
            loadAllUserInfo();

        }
        return name2InfoMap.get(userName);

    }
    public PlayerStatus getUserInfoByUserName(int id){
        if(name2InfoMap == null){
            loadAllUserInfo();

        }
        return id2InfoMap.get(id);

    }

    public void loadAllUserInfo(){
        File file = PathManager.getInstance().getHomePath().resolve("upd.java").toFile();

        try {
            List<String> list  = FileUtil.readFile2List(file);
            for(String s : list){
                if(StringUtil.isNotEmpty(s)){
                    PlayerStatus playerStatus = JSON.parseObject(s,PlayerStatus.class);
                    name2InfoMap.put(playerStatus.getName(),playerStatus);
                    id2InfoMap.put(playerStatus.getId(),playerStatus);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
