package com.dozenx.game.network.server;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.handler.*;
import com.dozenx.game.network.server.service.impl.UserService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
 */
public class ChatServer {
    ServerContext serverContext =new ServerContext();
    public static void main(String args[]){
        ChatServer server =new ChatServer();
        server.start();
    }


    public  void start(){
        CoreRegistry.put(UserService.class , new UserService(serverContext));
        //注册所有服务
        serverContext. getAllHandlerMap().put(CmdType.LOGIN,new LoginHandler(serverContext));
        serverContext. allHandlerMap.put(CmdType.EQUIP,new EquipHandler(serverContext));
        serverContext. allHandlerMap.put(CmdType.SAY,new SayHandler(serverContext));
        serverContext. allHandlerMap.put(CmdType.GET,new GetHandler(serverContext));
        serverContext. allHandlerMap.put(CmdType.POS,new PosHandler(serverContext));


        GameServerHandler serverHandler = serverContext. allHandlerMap.get(CmdType.LOGIN);
        ServerSocket s = null;

        Thread allSender =new AllSender(serverContext.messages,serverContext.workerMap);allSender.start();
        //Thread workerCheck =new WorkerCheck(messages,workerMap);allSender.start();
        try {
            //设定服务端的端口号
            s = new ServerSocket(Constants.serverPort);
            System.out.println("ServerSocket Start:"+s);
            //等待请求,此方法会一直阻塞,直到获得请求才往下走

            while(true){
                Socket socket = s.accept();
                Worker worker =new Worker(socket,serverContext);
                worker.start();
                serverContext.workerMap.put(worker.hashCode(),worker);
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            System.out.println("Close.....");
            try {

                s.close();
            } catch (Exception e2) {

            }
        }
    }
}
