package cola.machine.game.myblocks.network;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import com.dozenx.game.engine.command.PosCmd;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by luying on 16/10/7.
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
                    String message = "move:" + LivingThingManager.player.id + "," + LivingThingManager.player.position.x
                            + "," + LivingThingManager.player.position.y
                            + "," + LivingThingManager.player.position.z + "," + LivingThingManager.player.WalkDir.x + "," + LivingThingManager.player.WalkDir.y + "," + LivingThingManager.player.WalkDir.z;
                    client.send(new PosCmd(LivingThingManager.player));
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
