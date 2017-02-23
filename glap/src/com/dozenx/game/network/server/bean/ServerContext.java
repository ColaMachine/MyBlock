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
    public  Map<String,PlayerStatus> name2PlayerMap  =new HashMap();
    public  Map<Integer,PlayerStatus> id2PlayerMap  =new HashMap();
    public  Map<CmdType,GameServerHandler> allHandlerMap =new HashMap<>();
    //public HashMap<String ,PlayerStatus> name2InfoMap =new HashMap();


    //public HashMap<Integer , Socket> socketMap =new HashMap();
    public Map<Integer,Worker> workerMap =new Hashtable();
    public Queue<byte[]> messages=new LinkedList<>();
    public Queue<PlayerStatus> livingThings=new LinkedList<>();


    public void addLivingThing(PlayerStatus status){

        id2PlayerMap.put(status.getId(),status);
    }
    public void removeLivingThing(PlayerStatus status){
        id2PlayerMap.remove(status.getId());
    }

    public Queue<byte[]> getMessages() {
        return messages;
    }

    public void setMessages(Queue<byte[]> messages) {
        this.messages = messages;
    }



    public Map<CmdType, GameServerHandler> getAllHandlerMap() {
        return allHandlerMap;

    }

    public void setAllHandlerMap(HashMap<CmdType, GameServerHandler> allHandlerMap) {
        this.allHandlerMap = allHandlerMap;
    }

    public Map<String, PlayerStatus> getName2PlayerMap() {

        return name2PlayerMap;
    }

    public void setName2PlayerMap(Map<String, PlayerStatus> name2PlayerMap) {
        this.name2PlayerMap = name2PlayerMap;
    }

    public Map<Integer, PlayerStatus> getId2PlayerMap() {
        return id2PlayerMap;
    }

    public void setId2PlayerMap(Map<Integer, PlayerStatus> id2PlayerMap) {
        this.id2PlayerMap = id2PlayerMap;
    }

    public Map<Integer, Worker> getWorkerMap() {
        return workerMap;
    }

    public void setWorkerMap(Map<Integer, Worker> workerMap) {
        this.workerMap = workerMap;
    }


}
