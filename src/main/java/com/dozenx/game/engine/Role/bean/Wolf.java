package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackBall;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.LinedAttackBall;
import cola.machine.game.myblocks.skill.TimeString;
import com.dozenx.game.engine.PhysicsEngine;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.Role.model.BaseModel;
import com.dozenx.game.engine.Role.model.WolfModel;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.live.state.AttackState;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

import javax.vecmath.Vector2f;

public class Wolf extends LivingThing {

    public Wolf(int id) {
        super(id);
        this.idleAnimation=new String[]{"wag_tail","sniffer"};
       // this.speed=10;//由于服务器的所有生物都是livingthing 所以速度都是15
        this.getExecutor().setModel( new WolfModel(this));
        this.name="wolf";
    }

    public Wolf(int id,ItemDefinition itemDefinition) {
        super(id);
        this.idleAnimation=new String[]{"wag_tail","sniffer"};
        // this.speed=10;//由于服务器的所有生物都是livingthing 所以速度都是15


        BaseModel model = new WolfModel(this);
        this.getExecutor().setModel(model);
        model.itemDefinition = itemDefinition;
        this.name="wolf";

    }


  /*  public void render() {
        super.getrender();
    }*/


    public void move(float x, float y, float z) {

        super.move(x,y,z);


    }

    public void attack(){
    super.attack();
//        LogUtil.println("player 攻击");
//        this.getTarget().beAttack(1);
//
////        Vector2f screenXY= OpenglUtils.wordPositionToXY(ShaderManager.projection,this.getTarget().getPosition().copyClone().add(new GL_Vector(0,this.getExecutor().getModel().getRootComponent().height,0)), GamingState.getInstance().camera.Position,GamingState.getInstance().camera.ViewDir);
////        screenXY.x *= Constants.WINDOW_WIDTH;
////        screenXY.y *= Constants.WINDOW_HEIGHT;
////        AttackManager.addText(new TimeString("-1", screenXY.x,screenXY.y));
////        //AttackBall ball =new LinedAttackBall(5,this.getPosition().copyClone(),this.getWalkDir().copyClone(),5,2,this,1);
////        //this.changeState(new AttackState(this));
//        AttackManager.addAttackEvent(this,this.getTarget());
//        this.changeAnimationState("attack");
//        //AttackManager.addAttack(ball);//这里发现一个问题是 宠物发起的攻击会砸死自己
//        this.setLastAttackTime(TimeUtil.getNowMills());
    }

    //public boolean needJudgeCrash=false;
    public void move(GL_Vector vector) {
//        float x = vector.x;
//        float y = vector.y;
//        float z = vector.z;
        this.move(vector.x, vector.y, vector.z);


    }

    public void moveOld() {
        this.position = oldPosition;
        //make some adjust for float not Precision
    }


    public void StrafeRight(float Distance) { if(isDied())return;
        if (Math.abs(Distance) > 0.02) {

            this.changeAnimationState("walkerLeft");
            //if (this.stable) {
            lastMoveTime = Sys.getTime();
            this.move(GL_Vector.add(position, GL_Vector.multiply(getRightVector(),
                    Distance)));
        }
        //}
    }

    public void MoveForward(float Distance) {
        if(isDied())return;
        // System.out.printf("%f %f %f
//        LogUtil.println("MoveForward");							// \r\n",ViewDir.x,ViewDir.y,ViewDir.z);
        //if (this.stable) {
        if (Math.abs(Distance) > 0.02) {
            //Player player= CoreRegistry.get(Player.class);
            AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            manager.apply(getModel().getRootComponent(), "walkerFoward");
            this.move(GL_Vector.add(position, GL_Vector.multiplyWithoutY(walkDir,
                    Distance)));
//        LogUtil.println(position+"");
            lastMoveTime = Sys.getTime();
            // System.out.printf("position: %f %f %f viewdir: %f %f %f
            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
            //}
        }
    }


    int du = 30;


    public void jumpHigh() {
        super.jump();
    }

    public void jump() {

        super.jump();


    }



    public void idleProcess(){
        //找到下一个节点
    }

//
//
//    public void doSomeThing(LivingThingManager livingThingManager){
//        //如果是普通生物
//        if (!isDied()) {//如果自身是有效单位 没有死
//
//            if (getTargetId() > 0) {//并且是有目标
//                if (checkEnemyTarget(this))//释放无用target 补全缺少target
//                {//追击或者攻击
//                    livingThingManager.moveOrAttack(this);
//                }
//            } else {//暂时没有目标
//                //if(enemy.getExecutor().getCurrentState() instanceof  IdleState){
//                //找寻目标
//
//                //希望没1000执行一次
//                livingThingManager.findTarget(this);
//            }
//
//
//
//            CoreRegistry.get(PhysicsEngine.class).checkIsDrop(this);
//            CoreRegistry.get(PhysicsEngine.class).gravitation(this);
//        }
//        //this.getExecutor().getCurrentState().update();
//
//
//    }
}
