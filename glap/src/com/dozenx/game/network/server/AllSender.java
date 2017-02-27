package com.dozenx.game.network.server;

import core.log.LogUtil;

import java.util.*;

/**
 * Created by luying on 16/10/7.
 */
public class AllSender extends Thread{
    Queue<byte[]> messages;
    List<Worker>  workers;
    public AllSender(Queue<byte[]> messages,List<Worker> workers){
        this.messages =messages;
        this.workers=workers;
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
               // LogUtil.println("messages 结果大于0");
                byte[] bytes  = messages.peek();
                if (bytes != null) {
                    messages.poll();

                    for(int i=workers.size()-1;i>=0;i--){
                        Worker worker = workers.get(i);
                        if(worker.end || !worker.isAlive()){
                            LogUtil.println("删除socket成功");
                            worker.interrupt();   worker.end=true;

                            workers.remove(i);
                        }else {
                            worker.send(bytes);
                        }
                    }

                   /* while(waitDelList.size()>0&& waitDelList.peek()!=null){
                        Integer id = waitDelList.poll();
                        workerMap.remove(id);
                    }*/
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
