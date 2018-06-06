package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * 这个状态从被攻击开始 到 落地结束 被攻击的物体退后并飞起一段距离 期间可以 攻击别人 但是不能移动 然后又回到原来的状态
 * Created by luying on 17/2/7.
 */
public class BeAttackState extends State {


    public BeAttackState(LivingThingBean livingThing, GameCmd gameCmd){
        //if the lvingthing is player than syn the data to server  form the data to walkcmd2
        super(livingThing);

        int dir=1;
        if(from== null || to == null){
            LogUtil.err("from and to can't be null ");
        }
        computeDest(dir);
        //

    }

    public BeAttackState(LivingThingBean livingThing, GL_Vector from, GL_Vector to){

        super(livingThing);
        if(from== null || to == null){
            LogUtil.err("from and to can't be null ");
        }

        computeFromTo(from,to);

    }
    //private LivingThing livingThing;
    float speedForward;
    float speedLeft;
    float speedRight;
    float speedBack;
    long forwardPressedTime;
    long leftTime;
    long backTime;
    GL_Vector from ;
    GL_Vector to;
    long startTime;
    GL_Vector walkDir;
    float distance ;

    //经常用的是这个 拿到对方的walcmd2 之后就用他来构造walkstate
    //
    public void computeFromTo(GL_Vector from,GL_Vector to){
        this.from = from.copyClone();    //设置from
        //改版之后就不能调教了 因为所有的行走命令都是怪物的以此位置同步 我们应该允许walcmd2的from值是空的 如果是空的 就from值为null
        if(from != null ) {
            this.livingThing.position = from.copyClone();  //调教初始移动位置 发现人物的位置经常不能同步


        }
        if(to!=null){
            this.to =to.copyClone();
            this.livingThing.setDest(to);//设置目的地
            //根据目的地没阵去计算当前位置
            //
        }

        this.startTime = TimeUtil.getNowMills();


        walkDir = GL_Vector.sub(livingThing.getPosition(),from);

        this.distance = GL_Vector.length(walkDir);

        if( !livingThing .isPlayer()) { //如果不是玩家的话就要调整面对的方向
            this.walkDir.normalize();
            livingThing.walkDir = walkDir.copyClone();
            livingThing.setBodyAngle(GL_Vector.getAnagleFromXZVectory(walkDir));
        }
        livingThing.changeAnimationState("walkerFoward");
    }
    //这个已经废弃不用了
    public void computeDest(int dir){
       walkDir =  livingThing.getWalkDir().normalize().copyClone();
        livingThing.setRightVector(GL_Vector.crossProduct(livingThing.walkDir, livingThing.upVector));
        GL_Vector add= null;
        if(dir == WalkCmd.FORWARD){
            add=livingThing.getWalkDir().normalize().copyClone().mult(livingThing.speed);
        }else if(dir == WalkCmd.RIGHT){
            add=livingThing.getRightVector().copyClone().mult(livingThing.speed);
        }else if(dir == WalkCmd.LEFT){
            add=livingThing.getRightVector().copyClone().mult(-livingThing.speed);
        }else if(dir == WalkCmd.BACK){
            add=livingThing.getWalkDir().copyClone().mult(-livingThing.speed);
        }

        this.to =livingThing.getPosition().copyClone().add(add);

        computeFromTo(livingThing.getPosition(),to);


    }
    //任何命令都应该是一次性的不应该保存任何内部状态,或者状态的
    Queue<GameCmd> queue = new LinkedList<>();

    public void receive(GameCmd gameCmd){//11
        if(gameCmd .getCmdType() == CmdType.WALK2){
            queue.clear();//queue 存在的好处在于未来可以给运行轨迹插上小旗子
            this.queue.offer(gameCmd);

            WalkCmd2 walkCmd2 =(WalkCmd2)gameCmd;
            this.livingThing.setBodyAngle(walkCmd2.bodyAngle);

            if(walkCmd2.stop==true){
                this.livingThing.setPosition(walkCmd2.from.x,walkCmd2.from.y,walkCmd2.from.z);
                this.livingThing.changeState(new IdleState(this.livingThing,null));
                return;
            }
            computeFromTo(walkCmd2.from,walkCmd2.to);
        }else
       if(gameCmd .getCmdType() == CmdType.WALK){
           queue.clear();
            this.queue.offer(gameCmd);

        }else if(gameCmd .getCmdType() == CmdType.DIED) {
            this.livingThing.changeState( new DiedState(this.livingThing,gameCmd));

        }else if(gameCmd .getCmdType() == CmdType.ATTACK) {

            Long notTime = TimeUtil.getNowMills();
            if(notTime-livingThing.getLastAttackTime() >intervalTime){
                livingThing.setLastAttackTime(notTime);
            }else{
                return;
            }
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            AttackCmd cmd =(AttackCmd) gameCmd;
            if(cmd.getAttackType()== AttackType.KAN){
                livingThing.changeAnimationState("kan");
                //这里要判断target 是否为null

                if(livingThing.getTarget()!=null){
                    livingThing.getTarget().beAttack(cmd.getAttackValue());
                    Document.needUpdate=true;
                }

                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            }else if(cmd.getAttackType()== AttackType.ARROW){
                this.livingThing.changeState( new ShootState(this.livingThing));
                livingThing.getTarget().beAttack(cmd.getAttackValue());
            }

        }
    }
    long lastMoveTime ;

    public void update(){
        this.updatePosition();
    }
    public void updatePosition(){
       if(this.disposed){
           LogUtil.err("该state 应该已经结束了 错误了");
           return;
       }
        long nowTime = TimeUtil.getNowMills();
        if (nowTime - lastMoveTime >50) {
            //Player player= CoreRegistry.get(Player.class);
            //AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            //manager.apply(getModel().bodyComponent, "walkerFoward");
           GL_Vector newPosition =  GL_Vector.add(from, GL_Vector.multiplyWithoutY(walkDir,
                    livingThing.speed/10* (nowTime-startTime)/1000));
//            livingThing.getPosition().x=newPosition.x;
//            livingThing.getPosition().z=newPosition.z;

            //如果newposition 的位置
            if(GL_Vector.length(GL_Vector.sub(livingThing.getPosition(),from))>distance){
               // this.livingThing.setPosition(this.to);

                this.livingThing.move(this.to.x,livingThing.getPosition().y,this.to.z);
                if(queue.peek()!=null){
                    //从新开始
                    GameCmd gameCmd =queue.poll();
                    if(gameCmd.getCmdType()==CmdType.WALK){
                        computeDest(((WalkCmd)gameCmd).dir);
                    }else{
                        computeFromTo(((WalkCmd2)gameCmd).from,((WalkCmd2)gameCmd).to);
                    }
                }else {
                    this.livingThing.changeState(new IdleState(this.livingThing,null));
                }
            }

            this.livingThing.move(newPosition.x,livingThing.getPosition().y,newPosition.z);
            /*else{
                this.livingThing.changeState(new IdleState(this.livingThing));
            }*/
//        LogUtil.println(position+"");
            lastMoveTime = TimeUtil.getNowMills();
            // System.out.printf("position: %f %f %f viewdir: %f %f %f
            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
            //}
        }

       // livingThing.setPosition(G);


    }
}
