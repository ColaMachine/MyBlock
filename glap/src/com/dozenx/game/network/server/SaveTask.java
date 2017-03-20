package com.dozenx.game.network.server;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.PlayerSynCmd;
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
 * Created by luying on 16/10/7.
 */
public class SaveTask extends TimerTask {
  ServerContext serverContext;

    UserService userService;
    BagService bagService;
    EnemyService enemyService;
    public SaveTask(ServerContext serverContext){
        this.serverContext = serverContext;
        userService = (UserService)serverContext.getService(UserService.class);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        bagService = (BagService)serverContext.getService(BagService.class);
    }
    //如何把小怪物的动作行动同步到各个客户端呢 一个办法是告诉客户端 怪物的一个5秒动作 即是 要到哪个点 要执行哪个动作 这样能减少客户端的数据交互 所以最终还是得看指令
    public void run(){
        Long now = TimeUtil.getNowMills();
        for(LivingThingBean livingThing:enemyService.getAllEnemies()){
            if(livingThing.isDied()&& now - livingThing.getLastHurtTime()>10*1000 ){
                livingThing.setNowHP(livingThing.getHP());
                livingThing.setY(livingThing.getY());
                //livingThing.setDied(false);
                int nowHP = livingThing.nowHP+10;
                if(nowHP > livingThing.HP){
                    nowHP=livingThing.HP;
                }
                livingThing.setTarget(null);
                livingThing.setTargetId(0);
                livingThing.getExecutor().setCurrentState(new IdleState( livingThing));
                livingThing.setNowHP(nowHP);

            }

            serverContext.getMessages().offer(new PlayerSynCmd(livingThing.getInfo()).toBytes());

        }
        //保存用户信息
        //保存包裹信息
        for(LivingThingBean livingThing:userService.getAllOnlinePlayer()){
            int nowHP = livingThing.nowHP+10;
            if(nowHP > livingThing.HP){
                nowHP=livingThing.HP;
            }
            livingThing.setNowHP(nowHP);
            userService.save(livingThing.getInfo());
            serverContext.getMessages().offer(new PlayerSynCmd(livingThing.getInfo()).toBytes());
            bagService.save(livingThing.getId(),bagService.getItemByUserId(livingThing.getId()));

        }
    }

}
