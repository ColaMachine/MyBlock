package com.dozenx.game.engine.Role.bean;

import check.CrashCheck;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.Role.model.WolfModel;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

public class Wolf extends LivingThing {

    public Wolf(int id) {
        super(id);
       // this.speed=10;//由于服务器的所有生物都是livingthing 所以速度都是15
        this.getExecutor().setModel( new WolfModel(this));
        this.name="wolf";
    }


  /*  public void render() {
        super.getrender();
    }*/


    public void move(float x, float y, float z) {
		/*float distance = GL_Vector.length(GL_Vector.sub(oldPosition,position));
        if(distance>0.02){*/

        this.oldPosition.copy(this.position);
        GamingState.livingThingChanged = true;

        GamingState.setCameraChanged(true);
        ShaderManager.humanPosChangeListener();


        this.position.set(x, y, z);
        this.updateTime =  TimeUtil.getNowMills();
        if (!Switcher.IS_GOD)
            if (CoreRegistry.get(CrashCheck.class).check(this)) {
                this.position.copy(oldPosition);
            }
        //client.send("move:");
        //this.stable=false;
        //client.send("move:"+this.id+","+this.position.x+","+this.position.y+","+this.position.z+"");

        // }


	/*	String message = "move:"+ id+","+position.x
				+","+position.y
				+","+position.z+","+WalkDir.x+","+WalkDir.y+","+WalkDir.z;
		client.send(message);*/

    }

    //public boolean needJudgeCrash=false;
    public void move(GL_Vector vector) {
        float x = vector.x;
        float y = vector.y;
        float z = vector.z;
        this.move(x, y, z);


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
        if(isDied())return;
        // ��¼��ǰ��ʱ��
        if (this.stable) {
            this.v = 12.6f;
            preY = (int) this.position.y;
            lastTime =  TimeUtil.getNowMills();//Sys.getTime();
            this.stable = false;
        }
    }

    public void jump() { if(isDied())return;
        //this.position.y+=1;
        // ��¼��ǰ��ʱ��
        if (Switcher.IS_GOD) {
            this.position.y += 2;
         //   GamingState.cameraChanged = true;
            GamingState.setCameraChanged(true);
        } else if (this.stable) {
            this.v = 10.2f;
            preY = (int) this.position.y;
            lastTime = TimeUtil.getNowMills();//Sys.getTime();
            this.stable = false;
        }
    }


}
