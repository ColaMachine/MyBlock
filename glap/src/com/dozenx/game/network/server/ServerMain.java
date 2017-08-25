package com.dozenx.game.network.server;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import com.dozenx.game.engine.PhysicsEngine;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.handler.*;
import com.dozenx.game.network.server.service.ServerEnemyManager;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import core.log.LogUtil;

import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by luying on 16/10/7.
 */
public class ServerMain {

    Timer timer;
    ServerContext serverContext =new ServerContext();
    public static void main(String args[]){
        ServerMain server =new ServerMain();
        server.start();
    }


    public  void start(){
        CoreRegistry.put(BlockManager.class,new BlockManagerImpl());
        CoreRegistry.put(UserService.class , new UserService(serverContext));
        //注册所有服务
        //注册所有service



       serverContext.registerService(BagService.class,new BagService(serverContext));
        serverContext.registerService(EnemyService.class,new EnemyService(serverContext));
        serverContext.registerService(UserService.class,new UserService(serverContext));
        //注册所有handler
       /*  serverContext. getAllHandlerMap().put(CmdType.LOGIN,new LoginHandler(serverContext));
        serverContext. registerHandler(CmdType.WALK2,new WalkHandler(serverContext));
        serverContext. registerHandler(CmdType.WALK,new WalkHandler(serverContext));
        serverContext. registerHandler(CmdType.EQUIP,new EquipHandler(serverContext));
        serverContext. registerHandler(CmdType.SAY,new SayHandler(serverContext));
        serverContext. registerHandler(CmdType.GET,new GetHandler(serverContext));
        serverContext. registerHandler(CmdType.POS,new PosHandler(serverContext));
        serverContext. registerHandler(CmdType.GET,new GetHandler(serverContext));
        serverContext. registerHandler(CmdType.BAG,new BagHandler(serverContext));
        serverContext. registerHandler(CmdType.BAGCHANGE,new BagChangeHandler(serverContext));
        serverContext. registerHandler(CmdType.ATTACK,new AttackHandler(serverContext));
        serverContext. registerHandler(CmdType.DROP,new DropHandler(serverContext));
        serverContext. registerHandler(CmdType.PICK,new PickHandler(serverContext));
        serverContext. registerHandler(CmdType.JUMP,new JumpHandler(serverContext));
        serverContext. registerHandler(CmdType.BOX,new BoxChangeHandler(serverContext));
        serverContext. registerHandler(CmdType.CHUNKREQUEST,new ChunkHandler(serverContext));*/
        for(CmdType cmd :CmdType.values()){
            try {
               if(cmd.gameHandlerClass!=null) {
                   Constructor c = cmd.gameHandlerClass.getConstructor(ServerContext.class);
                   serverContext.registerHandler(cmd, (GameServerHandler) c.newInstance(serverContext));
               }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ItemManager itemManager =new ItemManager();
        CoreRegistry.put(ItemManager.class,itemManager);
        PhysicsEngine physicsEngine =new PhysicsEngine();
        try {
            itemManager.loadItem();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.err(e);
        }

        /*  loadAllUserInfo();
        loadAllEnemy();
        loadItems();*/


       // GameServerHandler serverHandler = serverContext. allHandlerMap.get(CmdType.LOGIN);
        ServerSocket s = null;


        //将message 及时发送
        Thread allSender =new ServerAllSender(serverContext.getMessages(),serverContext.getWorkers());
        allSender.start();

        ServerEnemyManager enemyManager =new ServerEnemyManager(serverContext);
       // enemyManager.run();
        new Thread(enemyManager).start();




        //ServerSynTask serverSynTask =new ServerSynTask();
        //timer.schedule(serverSynTask,0,200);

        timer = new Timer();
        timer.schedule(new ServerGrowTask(serverContext),0, 10*1000);
        timer.schedule(new ServerSaveTask(serverContext),0, 60*1000);
        System.out.println("Task scheduled.");
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
                serverContext.addWorker(worker);
               // serverContext.workerMap.put(worker.hashCode(),worker);
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
