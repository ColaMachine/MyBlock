package com.dozenx.game.network.server;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.math.Vector2i;
import cola.machine.game.myblocks.world.chunks.ServerChunkProvider;
import com.dozenx.game.engine.command.DropCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.engine.command.RebornCmd;
import com.dozenx.game.engine.item.bean.ItemServerBean;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.ServerContext;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TimerTask;

/**
 * 该类的主要作用是将内存中的重要信息同步到磁盘里
 * Created by luying on 16/10/7.
 */
public class ServerSaveTask extends TimerTask {
  ServerContext serverContext;

    UserService userService;
    BagService bagService;
    EnemyService enemyService;
    public ServerSaveTask(ServerContext serverContext){
        this.serverContext = serverContext;
        userService = (UserService)serverContext.getService(UserService.class);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        bagService = (BagService)serverContext.getService(BagService.class);
    }


    //mainLoop=====>
    //             1 负责怪物的重生 应该把这个实情交给enemyservice 去处理 然后创建一个enemyDefinition
    //             2 同步怪物的所有信息
    //             3 负责玩家的重生信息
    //             4 负责玩家的所有信息
    //             5 保存玩家的数据
    //             6 保存玩家的背包信息
    //             7 保存玩家的
    //如何把小怪物的动作行动同步到各个客户端呢 一个办法是告诉客户端 怪物的一个5秒动作 即是 要到哪个点 要执行哪个动作 这样能减少客户端的数据交互 所以最终还是得看指令
    public void run(){
        try {
            Long now = TimeUtil.getNowMills();
            for (LivingThingBean livingThing : enemyService.getAllEnemies()) {

                    //livingThing.setNowHP(livingThing.getHP());
                    livingThing.setY(livingThing.getY());
                    //livingThing.setDied(false);
                    int nowHP = livingThing.nowHP + 10;
                    if (nowHP > livingThing.HP) {
                        nowHP = livingThing.HP;
                    }
                if (livingThing.isDied() && now - livingThing.getLastHurtTime() > 10 * 1000) {
                    livingThing.setTarget(null);
                    livingThing.setTargetId(0);
                    livingThing.getExecutor().setCurrentState(new IdleState(livingThing));
                    livingThing.setNowHP(nowHP);
                    RebornCmd rebornCmd = new RebornCmd(livingThing.getId());
                    serverContext.getMessages().offer(rebornCmd.toBytes());
                    livingThing.receive(new RebornCmd(livingThing.getId()));

                }

                serverContext.getMessages().offer(new PlayerSynCmd(livingThing.getInfo()).toBytes());

            }
            //保存用户信息
            //保存包裹信息
            for (LivingThingBean livingThing : userService.getAllOnlinePlayer()) {
                int nowHP = livingThing.nowHP + 10;
                if (nowHP > livingThing.HP) {
                    nowHP = livingThing.HP;
                }

                if (livingThing.isDied() && now - livingThing.getLastHurtTime() > 10 * 1000) {//复活的时间
                    RebornCmd rebornCmd =new RebornCmd(livingThing.getId());
                    serverContext.getMessages().offer(rebornCmd.toBytes());
                    livingThing.receive(rebornCmd);
                }
                livingThing.setNowHP(nowHP);

                userService.save(livingThing.getInfo());
                serverContext.getMessages().offer(new PlayerSynCmd(livingThing.getInfo()).toBytes());
                bagService.save(livingThing.getId(), bagService.getItemByUserId(livingThing.getId()));

            }
            for (ItemServerBean itemServerBean : bagService.getWorldItem()) {
                DropCmd dropCmd = new DropCmd(0, itemServerBean.getId());
                dropCmd.setX(itemServerBean.getX());
                dropCmd.setY(itemServerBean.getY());
                dropCmd.setZ(itemServerBean.getZ());
                dropCmd.setItemType(ItemType.values()[itemServerBean.getItemType()]);
                serverContext.getMessages().offer(dropCmd.toBytes());
            }
            ;
           serverContext.chunkProvider.save();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
