package com.dozenx.game.network.server.handler;

import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/18.
 */
public class PickHandler extends GameServerHandler {
    private EnemyService enemyService;
    private UserService userService;
    private BagService bagService;
    public PickHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        userService = (UserService)serverContext.getService(UserService.class);
        bagService =(BagService)serverContext.getService(BagService.class);
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        PickCmd cmd =(PickCmd) request.getCmd();

        LivingThingBean from = userService.getOnlinePlayerById(cmd.getUserId());
        if(from!=null){
            ItemServerBean item = bagService.getWorldItemById(cmd.getItemId());

            if(item!=null){
                if(GL_Vector.length(GL_Vector.sub(from.getPosition(),new GL_Vector(item.getX(),item.getY(),item.getZ())))<4){



                    ItemServerBean[] itemAry = bagService.getItemAryUserId(from.getId());

                    for(int i=0;i<itemAry.length;i++){
                        if(itemAry[i]==null ){
                            itemAry[i]=item;

                            bagService.removeWorldItem( item);
                            
                            broadCast(cmd);
                            return null;
                        }
                    }
                }
            }

            //worldItem add something

        }


        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
