package com.dozenx.game.network.client;

import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.PosCmd;
import com.dozenx.util.TimeUtil;

/**
 * Created by luying on 16/10/7.
 */

/**
 * 用来驱动livingThingManager 做networkupdate
 * 并更新主角的状态到服务器上
 */
public class Client500msTask extends Thread{
    Client client ;
    LivingThingManager livingThingManager;
    public Client500msTask(){
        client=CoreRegistry.get(Client.class);
        livingThingManager=CoreRegistry.get(LivingThingManager.class);
    }
    public long lastUpdateTime;
    public void run(){


        while (true) {
            try {
                if(Switcher.edit){

                }else {
                    livingThingManager.checkPlayerDrop();
                }

                livingThingManager.removeAllDiedOne();
                Thread.sleep(500);//200 ms
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
