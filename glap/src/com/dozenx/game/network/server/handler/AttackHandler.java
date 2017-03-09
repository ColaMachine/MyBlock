package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.util.TimeUtil;

/**
 * Created by luying on 17/2/18.
 */
public class AttackHandler extends GameServerHandler {
    private EnemyService enemyService;
    public AttackHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        AttackCmd cmd =(AttackCmd) request.getCmd();

        if(cmd.getTargetId()>0) {
            LivingThingBean playerStatus = enemyService.getEnemyById(cmd.getTargetId());
            if (playerStatus != null) {
                playerStatus.setTargetId(cmd.getUserId());
                playerStatus.setLastHurtTime(TimeUtil.getNowMills());
            }
            broadCast(request.getCmd());
        }

        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
