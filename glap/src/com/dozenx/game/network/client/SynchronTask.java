package com.dozenx.game.network.client;

import com.dozenx.game.engine.Role.controller.LivingThingManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.PosCmd;

/**
 * Created by luying on 16/10/7.
 */

/**
 * 用来驱动livingThingManager 做networkupdate
 * 并更新主角的状态到服务器上
 */
public class SynchronTask extends Thread{
    Client client ;
    LivingThingManager livingThingManager;
    public SynchronTask(){
        client=CoreRegistry.get(Client.class);
        livingThingManager=CoreRegistry.get(LivingThingManager.class);
    }
    public long lastUpdateTime;
    public void run(){


        while (true) {
            try {

            livingThingManager.netWorkUpdate();

               //Thread.sleep(100);

                if(LivingThingManager.player.updateTime>=lastUpdateTime-1000) {
                    lastUpdateTime=System.currentTimeMillis();
                    /*String message = "move:" + LivingThingManager.player.id + "," + LivingThingManager.player.position.x
                            + "," + LivingThingManager.player.position.y
                            + "," + LivingThingManager.player.position.z + "," + LivingThingManager.player.WalkDir.x + "," + LivingThingManager.player.WalkDir.y + "," + LivingThingManager.player.WalkDir.z;*/
                    client.send(new PosCmd(LivingThingManager.player));
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
