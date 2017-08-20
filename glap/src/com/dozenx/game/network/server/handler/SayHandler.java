package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.generator.ChunkGenerators.Random;
import com.dozenx.game.engine.Role.bean.Wolf;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.network.server.bean.GameServerRequest;
import com.dozenx.game.network.server.bean.GameServerResponse;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.MathUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/18.
 */
public class SayHandler extends GameServerHandler {
    UserService userService;
    EnemyService enemyService;
    BagService bagService;
    public SayHandler(ServerContext serverContext){
        super(serverContext);
        userService = (UserService)serverContext.getService(UserService.class);
        bagService = (BagService)serverContext.getService(BagService.class);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
    }
    public ResultCmd  handler(GameServerRequest request, GameServerResponse response){
        SayCmd cmd =(SayCmd)request.getCmd();

        LogUtil.println("server接收到说话了 "+((SayCmd)request.getCmd()).getMsg());

        //更新其他附近人的此人的装备属性
       /* if(cmd.getMsg().startsWith("/chunk")) {
            String[] arr = cmd.getMsg().split(" ");
            if(arr.length>=4  && StringUtil.isNumeric(arr[3])) {
                if(arr[0].equals("reload")){
                    if(StringUtil.isNumeric(arr[1])){
                        int x=Integer.valueOf(arr[1]);

                        if(StringUtil.isNumeric(arr[2])){
                            int z=Integer.valueOf(arr[2]);

                            Chunk chunk =  serverContext.chunkProvider.getChunk(x,0,z);
                            ChunkResponseCmd chunkCmd =new ChunkResponseCmd(chunk);
                            broadCast(chunkCmd);
                        }
                    }
                }
            }
        }*/
        if(cmd.getMsg().startsWith("/fly")){
            // create wolf x y z
            String[] arr = cmd.getMsg().split(" ");
            if(arr.length>=5  && StringUtil.isNumeric(arr[3])) {
                String name = arr[1];
                int x= Integer.valueOf(arr[2]);
                int y= Integer.valueOf(arr[3]);
                int z= Integer.valueOf(arr[4]);


                LivingThingBean  wolf  =enemyService.getEnemyById(Integer.valueOf(name));
                if(wolf!=null){
                    wolf.setPosition(x,y,z);
                }else{
                      wolf  =userService.getOnlinePlayerById(Integer.valueOf(name));
                    if(wolf!=null){
                        wolf.setPosition(x,y,z);
                    }
                }


            }

        }else
        if(cmd.getMsg().startsWith("/move")){
            // create wolf x y z
            String[] arr = cmd.getMsg().split(" ");
            if(arr.length>=5  && StringUtil.isNumeric(arr[3])) {
                String name = arr[1];
                int x= Integer.valueOf(arr[2]);
                int y= Integer.valueOf(arr[3]);
                int z= Integer.valueOf(arr[4]);


                LivingThingBean  wolf  =enemyService.getEnemyById(Integer.valueOf(name));
                if(wolf!=null){
                    wolf.setFinalDest(new GL_Vector(x,y,z));
                }


            }

        }else
        if(cmd.getMsg().startsWith("/create")){
            // create wolf x y z
            String[] arr = cmd.getMsg().split(" ");
            if(arr.length>=5  && StringUtil.isNumeric(arr[3])) {
                String name = arr[1];
                int x= Integer.valueOf(arr[2]);
                int y= Integer.valueOf(arr[3]);
                int z= Integer.valueOf(arr[4]);

                if("wolf".equals(name)){
                    LivingThingBean  wolf  = new LivingThingBean((int)(Math.random()*1000));
                    wolf.setName("wolf");
                    wolf.setPosition(x,y,z);
                    wolf.species=1;
                    LogUtil.println("create wolf:"+wolf.getId());
                    enemyService.addNewMonster(wolf);
                }
            }

        }else
        if(cmd.getMsg().startsWith("/give")){
            String[] arr = cmd.getMsg().split(" ");
            if(arr.length>=4  && StringUtil.isNumeric(arr[3])) {
                String name = arr[1];
                String item = arr[2];
                String num = arr[3];
                LivingThingBean livingThingBean = userService.getAllPlayerByName(name);
                if(livingThingBean!=null){
                    ItemServerBean[] beans = bagService.getItemAryUserId(livingThingBean.getId());
                    for(int i=0;i<beans.length;i++){
                        if(beans[i]==null){
                            beans[i]=new ItemServerBean();
                            beans[i].setPosition(i);
                            beans[i].setId((int)(Math.random()*1000));
                            if(StringUtil.isNumeric(arr[2])){
                                beans[i].setItemType(Integer.valueOf(arr[2]));
                            }else{
                                beans[i].setItemType(ItemManager.getItemDefinition(arr[2]).id);
                            }

                            beans[i].setNum(Integer.valueOf(arr[3]));
                            BagCmd bagCmd =new BagCmd(livingThingBean.getId(),bagService.getItemByUserId(livingThingBean.getId()));

                            request.getWorker().send(bagCmd.toBytes());
                            break;
                        }
                    }
                }
            }
        }else{
            broadCast(request.getCmd());
        }
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext.broadCast(cmd.toBytes());
    }
}
