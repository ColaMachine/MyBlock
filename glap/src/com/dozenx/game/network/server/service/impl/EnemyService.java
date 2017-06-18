package com.dozenx.game.network.server.service.impl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.GameServerService;
import com.dozenx.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/3/9.
 */
public class EnemyService extends GameServerService {
    private ServerContext serverContext;
    public EnemyService(ServerContext serverContext){
        this.serverContext = serverContext;
       loadAllEnemy();
    }

   public List<LivingThingBean> getAllEnemies(){
        return serverContext.enemyList;
    }
    public LivingThingBean getEnemyById(int id ){
        for(LivingThingBean player : serverContext.enemyList){
            if(player.getId() == id){

                return player;
            }
        }
        return null;

    }
    public void addNewMonster(LivingThingBean livingThingBean){
        serverContext.enemyList.add(livingThingBean);
    }
    public void loadAllEnemy(){
        File player = PathManager.getInstance().getHomePath().resolve("saves").resolve("enemy").toFile();
        if(!player.exists()){
            player.mkdirs();
        }
        List<File> files =  FileUtil.listFile(player);
        for(File file :files){
            try {
                if(file.getName().indexOf(".")>-1 &&!file.getName() .endsWith("txt")){//filter the rubish file
                    continue;
                }
                String s = FileUtil.readFile2Str(file);

                PlayerStatus playerStatus = JSON.parseObject(s,
                        new TypeReference<PlayerStatus>() {
                        });
                LivingThingBean livingThingBean =new LivingThingBean(playerStatus.getId());
                livingThingBean.setInfo(playerStatus);
                serverContext.enemyList.add(livingThingBean);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
