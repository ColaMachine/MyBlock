package com.dozenx.game.network.client;

import cola.machine.game.myblocks.registry.CoreRegistry;
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

           // livingThingManager.netWorkUpdate();

               //Thread.sleep(100);
                //同步玩家的数据给服务器 服务器再下发给客户端
                if(livingThingManager.player.updateTime>=lastUpdateTime-400) {
                    lastUpdateTime= TimeUtil.getNowMills();
                    /*String message = "move:" + LivingThingManager.player.id + "," + LivingThingManager.player.position.x
                            + "," + LivingThingManager.player.position.y
                            + "," + LivingThingManager.player.position.z + "," + LivingThingManager.player.WalkDir.x + "," + LivingThingManager.player.WalkDir.y + "," + LivingThingManager.player.WalkDir.z;*/
                    client.send(new PosCmd(livingThingManager.player));
                }
                Thread.sleep(200);//200 ms
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
