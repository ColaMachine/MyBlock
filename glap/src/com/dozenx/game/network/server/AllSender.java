package com.dozenx.game.network.server;

import core.log.LogUtil;

import java.util.*;

/**
 * Created by luying on 16/10/7.
 */
public class AllSender extends Thread{
    Queue<byte[]> messages;
    Map<Integer, Worker> workerMap;
    public AllSender(Queue<byte[]> messages,Map<Integer,Worker> workerMap){
        this.messages =messages;
        this.workerMap=workerMap;
    }
 Queue<Integer> waitDelList =new LinkedList<>();

    public void  syncStatus(byte[] bytes){
        /*GameCmd cmd = CmdUtil.getCmd(bytes);
        GameServerHandler handler = ChatServer.allHandlerMap.get(cmd.getCmdType());
        if(handler!= null ){
            handler.handler(new GameServerRequest(cmd,));
        }
        if (cmd.getCmdType()== CmdType.EQUIP) {//equip


        } else if (cmd.getCmdType()== CmdType.POS) {

        } else if (cmd.getCmdType()== CmdType.SAY) {

        }else if (cmd.getCmdType()== CmdType.LOGIN) {
            LoginCmd loginCmd = (LoginCmd) cmd;
            String userName = loginCmd.getUserName();
            String pwd = loginCmd.getPwd();
            if(StringUtil.isBlank(userName)){

            }
           *//* CoreRegistry.get(UserService.class).getUserInfoByUserName(userName);
            //vertify the pwd
            loginCmd.pwd;
*//*
            //notice other player
        }*/


    }
    public void getCmd(byte[] bytes){

    }
    public void run(){
        while(true) {
            if (messages.size() > 0) {
                byte[] bytes  = messages.peek();
                if (bytes != null) {
                    messages.poll();
                    Iterator iter = workerMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<Integer,Worker> entry = (Map.Entry) iter.next();

                        Worker val = (Worker) entry.getValue();
                        if(val.end || !val.isAlive()){
                            LogUtil.println("删除socket成功");
                            val.interrupt();   val.end=true;
                            waitDelList.offer(entry.getKey());





                        }else {
                            val.send(bytes);
                        }
                    }

                    while(waitDelList.size()>0&& waitDelList.peek()!=null){
                        Integer id = waitDelList.poll();
                        workerMap.remove(id);
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

}
