package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.engine.Role.bean.item.ComplexEquipProperties;
import com.dozenx.game.engine.Role.excutor.Executor;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.server.bean.PlayerStatus;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.lang.ref.WeakReference;

/**
 * Created by luying on 17/3/5.
 */
public class Role extends ComplexEquipProperties {
    private WeakReference<Role> target;
    private boolean isPlayer;
    private boolean dirChanged;

    private GL_Vector dest;
    private GL_Vector finalDest;
    public GL_Vector getDest() {
        return dest;
    }

    public GL_Vector getFinalDest(){
        return finalDest;
    }

    public void setFinalDest(GL_Vector finalDest){
        this.finalDest = finalDest;
    }



    public void setDest(GL_Vector dest) {
        this.dest = dest;
    }

    public boolean isDirChanged() {
        return dirChanged;
    }

    public void setDirChanged(boolean dirChanged) {
        this.dirChanged = dirChanged;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
       /* if(!isPlayer){
            LogUtil.println(" not a player");
        }*/
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


    public float speed=10f;





    public float nextZ = 0;
    public int limit = 0;
    //public boolean exist=true;



    public boolean stable = true;
    public void setStable(boolean flag) {
        this.stable = flag;
    }

    public long lastTime = 0;
    public long lastMoveTime = 0;


//    public int mark = 0;
//    public int preY = 0;


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
