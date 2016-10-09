package cola.machine.game.myblocks.server;

import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.network.Client;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.chat.ChatFrame;

/**
 * Created by luying on 16/10/9.
 */
public class NetWorkManager {

    Client client;
    ChatFrame chatFrame;
    LivingThingManager livingThingManager;

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
