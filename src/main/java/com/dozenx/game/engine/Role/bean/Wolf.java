package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackBall;
import cola.machine.game.myblocks.skill.AttackManager;
import com.dozenx.game.engine.Role.model.BaseModel;
import com.dozenx.game.engine.Role.model.WolfModel;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

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
        LogUtil.println("player 攻击");

        AttackBall ball =new AttackBall(5,this.getPosition().copyClone(),this.getViewDir().copyClone(),5,2,this);

        AttackManager.addAttack(ball);//这里发现一个问题是 宠物发起的攻击会砸死自己
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

}
