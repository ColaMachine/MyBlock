package com.dozenx.game.network.server.bean;

import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.network.server.PlayerStatus;
import com.dozenx.game.network.server.Worker;
import com.dozenx.game.network.server.handler.GameServerHandler;

import java.util.*;

/**
 * Created by dozen.zhang on 2017/2/21.
 */
public class ServerContext {
    public  HashMap<String,PlayerStatus> name2PalyerMap  =new HashMap();
    public  HashMap<Integer,PlayerStatus> id2PalyerMap  =new HashMap();
    public  HashMap<CmdType,GameServerHandler> allHandlerMap =new HashMap<>();
    public HashMap<String ,PlayerStatus> name2InfoMap =new HashMap();


    //public HashMap<Integer , Socket> socketMap =new HashMap();
    public Map<Integer,Worker> workerMap =new Hashtable();
    public Queue<byte[]> messages=new LinkedList<>();
    public Queue<PlayerStatus> livingThings=new LinkedList<>();


    public void addLivingThing(PlayerStatus status){

        id2PalyerMap.put(status.getId(),status);
    }
    public void removeLivingThing(PlayerStatus status){
        id2PalyerMap.remove(status.getId());
    }

    public Queue<byte[]> getMessages() {
        return messages;
    }

    public void setMessages(Queue<byte[]> messages) {
        this.messages = messages;
    }



    public HashMap<CmdType, GameServerHandler> getAllHandlerMap() {
        return allHandlerMap;

    }

    public void setAllHandlerMap(HashMap<CmdType, GameServerHandler> allHandlerMap) {
        this.allHandlerMap = allHandlerMap;
    }

    public HashMap<String, PlayerStatus> getName2PalyerMap() {

        return name2PalyerMap;
    }

    public void setName2PalyerMap(HashMap<String, PlayerStatus> name2PalyerMap) {
        this.name2PalyerMap = name2PalyerMap;
    }

    public HashMap<Integer, PlayerStatus> getId2PalyerMap() {
        return id2PalyerMap;
    }

    public void setId2PalyerMap(HashMap<Integer, PlayerStatus> id2PalyerMap) {
        this.id2PalyerMap = id2PalyerMap;
    }

    public Map<Integer, Worker> getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Map<Integer, Worker> workerMap) {
        this.workerMap = workerMap;
    }


}
