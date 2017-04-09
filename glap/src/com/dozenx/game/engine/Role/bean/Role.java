package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.engine.Role.bean.item.ComplexEquipProperties;
import com.dozenx.game.engine.Role.excutor.Executor;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.network.client.Client;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.lang.ref.WeakReference;

/**
 * Created by luying on 17/3/5.
 */
public class Role extends ComplexEquipProperties {
    private WeakReference<Role> target;
    private boolean isPlayer;

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public Role getTarget(){
        if(target==null)return null;

        return target.get()!=null ? target.get() :null;
    }
    public void   finalize(){
        LogUtil.println("回收了");
    }
    public void setTarget(Role target){
        if(target==null){
            this.target=null;
            this.targetId = 0;
        }else {
            this.target = new WeakReference<Role>(target);
            this.targetId = target.getId();
        }
    }

   // Role target;
    int targetId;
    public GL_Vector RightVector=new GL_Vector(1,0,0); ;
    public GL_Vector upVector =new GL_Vector(0,1,0);

//    public GL_Vector viewDir = new GL_Vector(0,0,-1);  //  观察方向
//    public GL_Vector walkDir = new GL_Vector(0,0,-1);    //  行走方向
    public float attackDistance=1;
//    public GL_Vector position= new GL_Vector(0,0,0);    //  位置
    public GL_Vector nextPosition= new GL_Vector(0,0,0);    //  位置
    public GL_Vector oldPosition=new GL_Vector();   //  旧位置
    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

   /* public Role getTarget() {
        return target;
    }

    public void setTarget(Role target) {
        this.target = target;
    }*/

    Executor executor ;

    public PlayerModel getModel(){
        return (PlayerModel)executor.getModel();
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


    public float speed=1;





    public float nextZ = 0;
    public int limit = 0;
    //public boolean exist=true;



    public boolean stable = true;
    public void setStable(boolean flag) {
        this.stable = flag;
    }

    public long lastTime = 0;
    public long lastMoveTime = 0;


    public int mark = 0;
    public int preY = 0;


    public long updateTime;

    public float distance;


    public int sight=5;  //  视力




    public int level;          //  现在的等级




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


}
