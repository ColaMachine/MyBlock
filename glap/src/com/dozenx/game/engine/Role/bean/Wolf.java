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
        GamingState.cameraChanged = true;
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

    /**
     * x 是俯仰角度
     * y 是左右角度
     */

    public void headRotate(float leftRightDegree, float updownDegree) {
        leftRightDegree=leftRightDegree/2;
        updownDegree=updownDegree/2;
        //LogUtil.println("左右看"+( (float) Math.toRadians(leftRightDegree)) +"上下看"+updownDegree/100);
        if(leftRightDegree!=0) {

            if(leftRightDegree>0){
                //leftRightDegree=1;
            }else{
               // leftRightDegree=-1;
            }
            headAngle += Math.toRadians(-leftRightDegree);

            headAngle=(float)(headAngle%(2*Math.PI));
        }
        //headAngle = headAngle;

        if(updownDegree!=0) {

            if(updownDegree>0){
               // updownDegree=1;
            }else{
               // updownDegree=-1;
            }
           // updownDegree=1;
            headAngle2 += Math.toRadians(updownDegree);
            headAngle2 = (float) (headAngle2 % (2 * Math.PI));
            LogUtil.println("updownDegree:"+updownDegree);
        }
        //if (leftRightDegree != 0) {

            viewDir.x = (float) Math.cos(headAngle);
            viewDir.z = (float) Math.sin(headAngle);
       // }
        /*GL_Matrix M = GL_Matrix.rotateMatrix(*//*(float) Math.toRadians(updownDegree)/5,*//*0, (float) Math.toRadians(leftRightDegree),
                0);

		//计算俯角
		double xy= Math.sqrt(ViewDir.x*ViewDir.x + ViewDir.z*ViewDir.z);
		double jiaojiao = Math.atan(ViewDir.y/xy);
		jiaojiao+=Math.toRadians(updownDegree);*/
        if (headAngle2 <= Switcher.FUJIAO)
            headAngle2 = Switcher.FUJIAO;
        if (headAngle2 >= Switcher.YANGJIAO)
            headAngle2 = Switcher.YANGJIAO;
       // if(updownDegree!=0) {
            viewDir.y = (float) Math.tan(headAngle2);//(float)(Math.tan(jiaojiao)*xy);
        //}
        //ViewDir.y+=updownDegree/100;
        /*GL_Vector vd = M.transform(ViewDir);
        ViewDir = vd;*/


        viewDir.normalize();

        this.updateTime = TimeUtil.getNowMills();
        //  System.out.println(vd);
    }

    public void bodyRotate(float leftRightDegree, float updownDegree) {
        leftRightDegree=leftRightDegree/2;
        updownDegree=updownDegree/2;
        headRotate(leftRightDegree, updownDegree);
        bodyAngle = headAngle;
        //bodyAngle+=Math.toRadians(leftRightDegree);
        /*GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(leftRightDegree),
                0);*/
        walkDir.x = viewDir.x;//(float)Math.cos(bodyAngle);
        walkDir.z = viewDir.z;//(float)Math.sin(bodyAngle);
        // GL_Vector vd = M.transform(WalkDir);
        RightVector = GL_Vector.crossProduct(walkDir, upVector);
		/*vd.y=0;
		WalkDir.x= ViewDir.x;
		WalkDir.y= 0;
		WalkDir.z= ViewDir.z;*/
        GamingState.cameraChanged = true;
      /*  WalkDir = vd;
        ViewDir.x= vd.x;
        ViewDir.z = vd.z;*/

/*        GLApp.setSpotLight(GL11.GL_LIGHT1,
                new float[]{0f, 0f, 0f, 0.0f},//diffuseGL_AMBIENT表示各种光线照射到该材质上，经过很多次反射后最终遗留在环境中的光线强度（颜色）。
                new float[]{0.5f, 0.5f, 0.0f, 1.0f},//ambient GL_DIFFUSE表示光线照射到该材质上，经过漫反射后形成的光线强度（颜色）。
                new float[]{position.x,position.y+5,position.z,0}, new float[]{WalkDir.x,WalkDir.y,WalkDir.z,0}, 50);*/


    }

    public void StrafeRight(float Distance) { if(isDied())return;
        if (Math.abs(Distance) > 0.02) {

            this.changeAnimationState("walkerLeft");
            //if (this.stable) {
            lastMoveTime = Sys.getTime();
            this.move(GL_Vector.add(position, GL_Vector.multiply(RightVector,
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
            GamingState.cameraChanged = true;
        } else if (this.stable) {
            this.v = 10.2f;
            preY = (int) this.position.y;
            lastTime = TimeUtil.getNowMills();//Sys.getTime();
            this.stable = false;
        }
    }


}
