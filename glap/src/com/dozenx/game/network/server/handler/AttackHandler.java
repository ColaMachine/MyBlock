package com.dozenx.game.network.server.handler;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.game.network.server.service.impl.BagService;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/18.
 */
public class AttackHandler extends GameServerHandler {
    private EnemyService enemyService;
    private UserService userService;
    public AttackHandler(ServerContext serverContext){
        super(serverContext);
        enemyService = (EnemyService)serverContext.getService(EnemyService.class);
        userService = (UserService)serverContext.getService(UserService.class);
    }
    public ResultCmd handler(GameServerRequest request, GameServerResponse response){

        AttackCmd cmd =(AttackCmd) request.getCmd();
        LivingThingBean from = userService.getOnlinePlayerById(cmd.getUserId());
        if(from!=null && ! from.isDied()&& cmd.getTargetId()>0) {//如果攻击者活着并且攻击者 的攻击时间合适 如果是有目标的
            Long nowTime= TimeUtil.getNowMills();
            if(nowTime-from.getLastAttackTime() < 500){
                    return null;
            }
            LivingThingBean target = enemyService.getEnemyById(cmd.getTargetId());//并且目标存在在列表里

            if (target != null &&!target.isDied() ) {//并且 目标是活着的 且 在攻击方位内
                //判断距离
                float length = GL_Vector.length(GL_Vector.sub(from.getPosition(),target.getPosition()));
                if(cmd.getAttackType()== AttackType.KAN && length>5){


                    return null;

                }else if(cmd.getAttackType()== AttackType.ARROW){

                }
                from.setLastAttackTime(nowTime);

                target.setTargetId(cmd.getUserId());
                target.setLastHurtTime(TimeUtil.getNowMills());

                //结算伤害
                int shanghai = from.getPattack() - target.getDefense();
                cmd.setAttackValue(shanghai);
                int nowBlood = target.getNowHP() - shanghai;
                if(nowBlood<=0){
                    nowBlood=0;
                    broadCast(new DiedCmd(target.getId()));
                   // target.setDied(true);
                }else{
                    nowBlood-=shanghai;
                }


                target.setNowHP(nowBlood);
                broadCast(request.getCmd());
            }

        }

        //更新其他附近人的此人的装备属性
        return null;
    }
    public void broadCast(GameCmd cmd){
        serverContext. broadCast(cmd.toBytes());
    }
}
