package cola.machine.game.myblocks.network;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;

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
    public void run(){


while (true) {
    try {
    String message = "move:"+LivingThingManager.player.id+","+LivingThingManager.player.position.x
            +","+LivingThingManager.player.position.y
            +","+LivingThingManager.player.position.z;
    client.send(message);
    Thread.sleep(500);
    livingThingManager.update();

        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
    }
}
