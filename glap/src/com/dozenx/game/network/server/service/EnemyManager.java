package com.dozenx.game.network.server.service;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.AttackType;
import com.dozenx.game.engine.command.CmdType;
import com.dozenx.game.engine.command.PosCmd;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/26.
 */
public class EnemyManager implements  Runnable {
    private ServerContext serverContext  ;

    public EnemyManager(ServerContext serverContext ){
        this.serverContext =serverContext;
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.doSomeThing();
                //this.findThing();
               // this.changeDir();
                this.moveOrAttack();
                try {
                    Thread.sleep(1000);//1秒同步一次
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void findThing(){
        for(LivingThingBean enemy : serverContext.getAllEnemies()) {
            if (enemy.getTargetId() >0) {
                //检查举例 如果举例过远 放弃追逐
                LivingThingBean player= serverContext.getOnlinePlayerById(enemy.getTargetId());
                if(player==null){
                    enemy.setTargetId(0);
                }else
                if (GL_Vector.length(GL_Vector.sub(player.getPosition(), player.getPosition())) >15 ) {
                    if(TimeUtil.getNowMills()-enemy.getLastHurtTime()<10*1000) {//如果上次伤害还没超过多少时间

                        enemy.setTargetId(0);
                    }
                    //enemy.setTarget(player);
                }
            }

            for(LivingThingBean player: serverContext.getAllOnlinePlayer()){


                    if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                        enemy.setTargetId(player.getId());
                        //enemy.setTarget(player);
                    }

            }

        }
    }

    public void doSomeThing(){
        for(LivingThingBean enemy : serverContext.getAllEnemies()) {
           enemy.doSomeThing(serverContext);

        }
    }
    public void moveOrAttack() {
        for (LivingThingBean enemy : serverContext.getAllEnemies()) {
            if (enemy.getTargetId() > 0) {
                LivingThingBean target = serverContext.getOnlinePlayerById(enemy.getTargetId());
                if(target== null){
                    continue;
                }
                GL_Vector direction = GL_Vector.sub(target.getPosition(),enemy.getPosition());
                enemy.setWalkDir(direction);
                   if(GL_Vector.length(direction)<12){

                      /* enemy.
                        attack(enemy,enemy.getTarget());*/
                        //livingThing.nextPosition=null;
                    }else{
                       // this.getAnimationManager().apply(livingThing.bodyComponent,"walkerFoward");
                       GL_Vector newPosition = GL_Vector.add(enemy.getPosition(),GL_Vector.multiply(direction.normalize(),1*1));
                        enemy .setPosition(newPosition);
                       serverContext.broadCast(new PosCmd(enemy.getInfo()).toBytes());
                   }

            }
        }
    }

    public void attack(LivingThingBean source ,LivingThingBean target){
           serverContext.getAllHandlerMap().get(CmdType.ATTACK).handler(new GameServerRequest(new AttackCmd(source.getId(), AttackType.ARROW,target.getId()),null),new GameServerResponse());

    }
}
