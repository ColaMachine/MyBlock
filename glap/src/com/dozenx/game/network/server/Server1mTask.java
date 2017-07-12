package com.dozenx.game.network.server;

import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.network.client.Client;

/**
 * Created by luying on 16/10/7.
 */

/**
 * 用来驱动livingThingManager 做networkupdate
 * 并更新主角的状态到服务器上
 */
public class Server1mTask extends Thread{
    Client client ;
    LivingThingManager livingThingManager;
    public Server1mTask(){
        client=CoreRegistry.get(Client.class);
        livingThingManager=CoreRegistry.get(LivingThingManager.class);
    }
    public long lastUpdateTime;
    public void run(){


        while (true) {
            try {
                livingThingManager.update500ms();
                Thread.sleep(500);//200 ms
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
