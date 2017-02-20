package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import com.dozenx.game.network.client.Client;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;
import com.dozenx.game.engine.command.GameCmd;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by luying on 16/10/9.
 */
public class NetWorkManager {

    Client client;
    ChatFrame chatFrame;
    LivingThingManager livingThingManager;
    public static Queue<GameCmd> queue = new LinkedList<GameCmd>();

    public static void push(GameCmd cmd ){
        queue.add(cmd);
    }
    public NetWorkManager(){
        this.client= CoreRegistry.get(Client.class);
        livingThingManager= CoreRegistry.get(LivingThingManager.class);
        this.chatFrame= CoreRegistry.get(ChatFrame.class);

        if(client==null || livingThingManager==null || chatFrame==null){

        }
    }

    public void update(){


    }
}
