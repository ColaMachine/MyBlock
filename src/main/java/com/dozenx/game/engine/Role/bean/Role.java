package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.engine.Role.bean.item.ComplexEquipProperties;
import com.dozenx.game.engine.Role.excutor.Executor;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.server.bean.PlayerStatus;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.lang.ref.WeakReference;

/**
 *
 * Created by luying on 17/3/5.
 */
public class Role extends ComplexEquipProperties {
    public float speed=15f;//单位的跑动速度
    //public float nextZ = 0;
   // public int limit = 0;
    public float attackDistance=1;//攻击的距离
    //public boolean exist=true;
    public boolean stable = true;
    public void setStable(boolean flag) {
        this.stable = flag;
    }

   // public long lastTime = 0;
  //  public long lastMoveTime = 0;
//    public int mark = 0;
//    public int preY = 0;
    public long updateTime;
    Executor executor ;

  //  public float distance;

    public int sight=5;  //  视力 观察视野

    public int level;          //  现在的等级


    private boolean isPlayer;


    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
       /* if(!isPlayer){
            LogUtil.println(" not a player");
        }*/
        this.isPlayer = isPlayer;
    }


    public void   finalize(){

        LogUtil.println("回收了");
    }



    private GL_Vector RightVector=new GL_Vector(1,0,0); ;

    public GL_Vector getRightVector() {
        return RightVector;
    }

    public void setRightVector(GL_Vector rightVector) {
        RightVector = rightVector;
    }

    public GL_Vector upVector =new GL_Vector(0,1,0);

//    public GL_Vector viewDir = new GL_Vector(0,0,-1);  //  观察方向
//    public GL_Vector walkDir = new GL_Vector(0,0,-1);    //  行走方向

//    public GL_Vector position= new GL_Vector(0,0,0);    //  位置
    public GL_Vector nextPosition= new GL_Vector(0,0,0);    //  位置
    public GL_Vector oldPosition=new GL_Vector();   //  旧位置


   /* public Role getTarget() {
        return target;
    }

    public void setTarget(Role target) {
        this.target = target;
    }*/



    public Model getModel(){
        return executor.getModel();
    }

    public Executor getExecutor() {
        return executor;
    }


    public void setWalkDir(GL_Vector dir){
        this.walkDir = dir;
        this.setBodyAngle((float) Math.atan(dir.z / dir.x));
    }


    public void setExecutor(Executor executor) {
        this.executor = executor;
    }


    public void attack(){

    }
    public void beAttack(int harmValue){
        if(this.isDied()){

            return;

        }
        /*CoreRegistry.get(AnimationManager.class)
                .apply(getModel().bodyComponent,"kan");*/
        int damage=harmValue;
        this.nowHP-=damage;
        Document.needUpdate=true;
        Client.messages.push(new SayCmd(this.getId(),this.name,"被攻击 损失"+damage+"点血"));
        if(this.nowHP<=0){
           // this.died=true;
            this.nowHP=0;
            return;


        }

    }
    @Override
    public void getInfo(PlayerStatus info) {
        super.getInfo(info);
        info.setIsplayer(this.isPlayer);
        //this.setPlayer(info.isPlayer());
    }

    public void setInfo( PlayerStatus info ){
        super.setInfo(info);
        this.setPlayer(info.isPlayer());
    }


}
