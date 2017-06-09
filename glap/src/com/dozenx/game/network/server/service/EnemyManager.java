package com.dozenx.game.network.server.service;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.*;
import com.dozenx.game.network.server.service.impl.EnemyService;
import com.dozenx.game.network.server.service.impl.UserService;
import com.dozenx.util.TimeUtil;
import com.sun.jna.platform.win32.Netapi32Util;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/26.
 */
public class EnemyManager implements  Runnable {
    private ServerContext serverContext  ;

    public EnemyManager(ServerContext serverContext ){
        this.serverContext =serverContext;
        this.userService  =(UserService) serverContext.getService(UserService.class);
        this.enemyService =(EnemyService) serverContext.getService(EnemyService.class);
    }
    UserService userService;
    EnemyService enemyService;
    @Override
    public void run() {
        while(true) {
            try {


                enemyServerLoop4CreateCmd();
                //this.findThing();
               // this.doSomeThing();
               // this.changeDir();
                //this.moveOrAttack();
                //this.testCmd();
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
    public void enemyServerLoop4CreateCmd(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
            if (!enemy.isDied() ) {//如果自身是有效单位

                if(enemy.getTargetId() > 0){//并且是有目标
                    if(checkEnemyTarget(enemy))//释放无用target 补全缺少target
                    {
                        moveOrAttack(enemy);
                    }
                    //追击或者攻击

                }else{//暂时没有目标
                    if(enemy.getExecutor().getCurrentState() instanceof  IdleState){
                        //找寻目标

                        findTarget(enemy);
                    }
                }
                enemy.getExecutor().getCurrentState().update();

            }
        }
    }

    public boolean checkEnemyTarget(LivingThingBean livingThingBean){
        LivingThingBean player= userService.getOnlinePlayerById(livingThingBean.getTargetId());
        if(player==null){ //如果没有目标
            livingThingBean.setTargetId(0);
            livingThingBean.setTarget(null);
            return false;
        }else
        if (GL_Vector.length(GL_Vector.sub(livingThingBean.getPosition(), player.getPosition())) >100 ) {
            if(TimeUtil.getNowMills()-livingThingBean.getLastHurtTime()>10*1000) {//如果上次伤害还没超过多少时间

                livingThingBean.setTargetId(0);
                livingThingBean.setTarget(null);
            }
            livingThingBean.setTarget(player);
            //enemy.setTarget(player);
            return false;
        }else{
            livingThingBean.setTarget(player);
            return true;
        }

    }
    public void findTarget(LivingThingBean enemy){

        for(LivingThingBean player: userService.getAllOnlinePlayer()){

            if(player!=null)
            if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                enemy.setTarget(player);
                //enemy.setTarget(player);
            }

        }

    }

    public void testCmd(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {


            if (!enemy.isDied() &&enemy.getTargetId() > 0) {

                if(enemy.getExecutor().getCurrentState() instanceof IdleState ){
                    LivingThingBean player= userService.getOnlinePlayerById(enemy.getTargetId());
                    if(player!= null) {
                        WalkCmd2 walkCmd2 = new WalkCmd2(enemy.getPosition(), player.getPosition(), enemy.getId());
                        serverContext.broadCast(walkCmd2.toBytes());
                        enemy.getExecutor().receive(walkCmd2);
                    }
                }



            }
        }
    }
/*
    public void findThing(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
            if (enemy.getTargetId() >0) {
                //检查举例 如果举例过远 放弃追逐
                LivingThingBean player= userService.getOnlinePlayerById(enemy.getTargetId());
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

            for(LivingThingBean player: userService.getAllOnlinePlayer()){


                    if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                        enemy.setTargetId(player.getId());
                        //enemy.setTarget(player);
                    }

            }

        }
    }*/
/*
    public void doSomeThing(){
        for(LivingThingBean enemy : enemyService.getAllEnemies()) {
           enemy.doSomeThing(serverContext);

        }
    }*/
    public void moveOrAttack(LivingThingBean enemy) {


                GL_Vector direction = GL_Vector.sub(enemy.getTarget().getPosition(),enemy.getPosition());
                //enemy.setWalkDir(direction);
                   if(GL_Vector.length(direction)<5){
                       AttackCmd attackCmd = new AttackCmd( enemy.getId(),AttackType.KAN,enemy.getTargetId());
                       serverContext.broadCast(attackCmd.toBytes());
                       enemy.getExecutor().receive(attackCmd);
                        //开始攻击
                      /* enemy.
                        attack(enemy,enemy.getTarget());*/
                        //livingThing.nextPosition=null;
                    }else{
                       // this.getAnimationManager().apply(livingThing.bodyComponent,"walkerFoward");
                       /*GL_Vector newPosition = GL_Vector.add(enemy.getPosition(),GL_Vector.multiply(direction.normalize(),1*1));
                        enemy .setPosition(newPosition);
                       serverContext.broadCast(new PosCmd(enemy.getInfo()).toBytes());

*/                      //这段代码会导致一个问题 怪物到了目的地后才会再计算下一个目的地
                       //每隔一段时间就修正怪物的walkstate
                       if(enemy.getExecutor().getCurrentState() instanceof IdleState ){//就是说还没开始追击
                           LivingThingBean player= userService.getOnlinePlayerById(enemy.getTargetId());
                           if(player!= null) {
                               /*WalkCmd2 walkCmd2 = new WalkCmd2(enemy.getPosition(), player.getPosition(), enemy.getId());
                               serverContext.broadCast(walkCmd2.toBytes());
                               enemy.getExecutor().receive(walkCmd2);*/
                               ChaseCmd chaseCmd = new ChaseCmd(enemy.getId(), enemy.getTargetId());
                               serverContext.broadCast(chaseCmd.toBytes());
                               enemy.getExecutor().receive(chaseCmd);
                           }
                       }



                   }

    }
/*
    public void attack(LivingThingBean source ,LivingThingBean target){
           serverContext.getAllHandlerMap().get(CmdType.ATTACK).handler(new GameServerRequest(new AttackCmd(source.getId(), AttackType.ARROW,target.getId()),null),new GameServerResponse());

    }*/
}
