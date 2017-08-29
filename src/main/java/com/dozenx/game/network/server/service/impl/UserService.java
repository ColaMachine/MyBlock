package com.dozenx.game.network.server.service.impl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.GameServerService;
import com.dozenx.util.FileUtil;
import core.log.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 17/2/18.
 */
public class UserService extends GameServerService {
    private ServerContext serverContext;
    public UserService(ServerContext serverContext){
        this.serverContext = serverContext;
        this.loadAllUserInfo();

    }

    public LivingThingBean getUserInfoByUserName(String userName){

        return getAllPlayerByName(userName);

    }

    public LivingThingBean addNew(PlayerStatus playerStatus){
       // serverContext.addNew(playerStatus);

       return  addNewPlayer(playerStatus);
       // serverContext.name2PlayerMap.put(playerStatus.getName(),playerStatus);
       // serverContext.id2PlayerMap.put(playerStatus.getId(),playerStatus);

    }


    public LivingThingBean getAllPlayerByName(String name){
        for(LivingThingBean player : serverContext.allPlayer){
            if(name.equals(player.getName())){

                return player;
            }
        }
        return null;
    }
    public  LivingThingBean  getOnlinePlayerById(int id ){
        for(LivingThingBean player : serverContext.onLinePlayer){
            if(player.getId() == id){

                return player;
            }
        }
        return null;

    }
    public void addOnlinePlayer(PlayerStatus status){
        for(LivingThingBean player : serverContext.onLinePlayer){
            if(player.getId() == status.getId()){
                LogUtil.println("player:"+status.getId()+"already exist in online list");
                return;
            }
        }
        LivingThingBean livingThingBean=new LivingThingBean(status.getId());
        livingThingBean.setInfo(status);
        serverContext.onLinePlayer.add(livingThingBean);
    }

    public void addOnlinePlayer(LivingThingBean status){
        for(LivingThingBean player : serverContext.onLinePlayer){
            if(player.getId() == status.getId()){
                LogUtil.println("player:"+status.getId()+"already exist in online list");
                return;
            }
        }

        serverContext.onLinePlayer.add(status);
    }

    public void removeOnlinePlayer(int id ){
        synchronized (serverContext.onLinePlayer) {
        for(int i=serverContext.onLinePlayer.size()-1;i>=0;i--){
            LivingThingBean player = serverContext.onLinePlayer.get(i);
            if(player.getId()==id){

                    serverContext.onLinePlayer.remove(i);
                    break;

            }
        }
        }
    }

    public LivingThingBean addNewPlayer(PlayerStatus info){
        info.setIsplayer(true);
        File file = PathManager.getInstance().getHomePath().resolve("saves").resolve("player").resolve(info.getId()+".txt").toFile();
        try {
            FileUtil.writeFile(file, JSON.toJSONString(info));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //添加新人员物品
        List<ItemServerBean> items= new ArrayList<>();
        ItemServerBean item =new ItemServerBean();
        item.setId(1);
        item.setItemType(ItemType.arrow.id);
        items.add(item);
        item.setPosition(0);
        File file2 = PathManager.getInstance().getHomePath().resolve("saves").resolve("item").resolve(info.getId()+"").toFile();
        try {
            FileUtil.writeFile(file2, JSON.toJSONString(items));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ItemServerBean[] itemServerBeenAry =new ItemServerBean[25];
        itemServerBeenAry[0]=item;

        serverContext.itemArrayMap.put(info.getId(),itemServerBeenAry);


        LivingThingBean playerBean=new LivingThingBean(info.getId());

        if(!playerBean.isPlayer()){
            LogUtil.err("player should be true");
            playerBean.setPlayer(true);
        }
        serverContext.allPlayer.add(playerBean);
        serverContext.onLinePlayer.add(playerBean);
        playerBean.setInfo(info);
        return playerBean;
    }
    public void loadAllUserInfo(){
        File player =PathManager.getInstance().getHomePath().resolve("saves").resolve("player").toFile();
        if(!player.exists()){
            player.mkdirs();
        }
        List<File> files =  FileUtil.listFile(player);
        for(File file :files){
            try {
                String s = FileUtil.readFile2Str(file);
                PlayerStatus info = JSON.parseObject(s,
                        new TypeReference<PlayerStatus>() {
                        });
                LivingThingBean livingThingBean =new LivingThingBean(info.getId());
                livingThingBean.setInfo(info);
                if(!livingThingBean.isPlayer()){//shoud be true other wise walkstate will adjust the bodyAngle we dont want
                    LogUtil.err("player should be true");
                    livingThingBean.setPlayer(true);
                }
                serverContext.allPlayer.add(livingThingBean);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public List<LivingThingBean> getAllOnlinePlayer(){
        return serverContext.onLinePlayer;
    }
    /*public PlayerStatus getUserInfoByUserName(int id){
        if(serverContext.name2PlayerMap == null){
            loadAllUserInfo();

        }
        return serverContext.id2PlayerMap.get(id);

    }*/

    public void save(PlayerStatus info){
        if(!info.isPlayer()){
            LogUtil.err("player should be true");
            info.setIsplayer(true);
        }
        File file = PathManager.getInstance().getHomePath().resolve("saves").resolve("player").resolve(info.getId()+".txt").toFile();
        try {
            FileUtil.writeFile(file, JSON.toJSONString(info));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
