package com.dozenx.game.network.server.bean;

import cola.machine.game.myblocks.lifething.bean.GameActor;
import com.dozenx.game.engine.live.state.HumanState;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.lang.ref.WeakReference;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThingBean extends GameActor {
    public long getLastHurtTime() {
        return lastHurtTime;
    }
    public void setWalkDir(GL_Vector dir){
        this.WalkDir = dir;
        this.status.setBodyAngle((float) Math.atan(dir.z / dir.x));
    }
    public int getTargetId(){
        return this.status.getTargetId();
    }

    public void setTargetId(int id ){
        this.status.setTargetId(id);
    }

    public void setLastHurtTime(long lastHurtTime) {
        this.lastHurtTime = lastHurtTime;
    }

    private long lastHurtTime = 0;
    public String getName(){
        return this.status.getName();
    }
    public GL_Vector getPosition(){
        return new GL_Vector(this.status.getX(),this.status.getY(),this.status.getZ());
    }
    public int getId(){
        return this.status.getId();
    }
    private BagBean bag =new BagBean();
    private PlayerStatus status ;
    HumanState currentState ;
    public LivingThingBean(PlayerStatus playerStatus){

        this.status =playerStatus;
    }

    public long updateTime;

    public float distance;


    public int sight=5;  //  视力




    public int level;          //  现在的等级


    public float speed=1;

    public GL_Vector RightVector=new GL_Vector(1,0,0); ;
    public GL_Vector UpVector =new GL_Vector(0,1,0);

    public GL_Vector ViewDir = new GL_Vector(0,0,-1);  //  观察方向
    public GL_Vector WalkDir = new GL_Vector(0,0,-1);    //  行走方向
    public float attackDistance=1;
    public GL_Vector position= new GL_Vector(0,0,0);    //  位置
    public GL_Vector nextPosition= new GL_Vector(0,0,0);    //  位置
    public GL_Vector oldPosition=new GL_Vector();   //  旧位置

    public boolean stable = true;
    public void setStable(boolean flag) {
        this.stable = flag;
    }

    public long lastTime = 0;
    public long lastMoveTime = 0;

    public float nextZ = 0;
    public int limit = 0;
    public boolean exist=true;
    private WeakReference<LivingThingBean> target;
    public LivingThingBean getTarget(){
        if(target==null)return null;
        return target.get()!=null ? target.get() :null;
    }
    public void   finalize(){
        LogUtil.println("回收了");
    }
    public void setTarget(LivingThingBean target){
        if(target==null){
            this.target=null;
        }else
        this.target=new WeakReference<LivingThingBean>(target);
    }
    public int mark = 0;
    public int preY = 0;

    public String getState(){
        return "力量:"+status.basePower+"/"+status.totalPower+"\n"
                +"智力:"+status.baseIntell+"/"+status.totalIntell+"\n"
                +"敏捷:"+status.baseAgility+"/"+status.totalAgility+"\n"
                +"精神:"+status.baseSpirit+"/"+status.totalSpirit+"\n"
                +"血量:"+status.nowBlood+"/"+status.blood+"\n"
                +"魔法:"+status.nowEnergy+"/"+status.energy+"\n"
                +"防御:"+status.fangyu+"";
    }

    public boolean died=false;
    public void died(){
        this.status.nowBlood=0;
        died=true;
        
    }
    public void setPosition(GL_Vector position) {

        setPosition(position.x,position.y,position.z);
        //




    }
    public PlayerStatus getStatus(){
        return this.status;
    }
    public void setPosition(float posx, float posy, float posz) {
        this.minX=posx-0.5f;
        this.minY=posy;
        this.minZ=posz-0.5f;

        this.maxX=posx+0.5f;
        this.maxY=posy+4;
        this.maxZ=posz+0.5f;
     //
        position.x= posx;
        position.y = posy;
        position.z = posz;
        this.status.setX(posx);
        this.status.setY(posy);
        this.status.setZ(posz);
       /* this.position.x=posx;
        this.position.y=posy;
        this.position.z=posz;*/



    }



    public void setPlayerStatus(PlayerStatus status){
        this.status=status;
        this.position.x = status.getX();
        this.position.y = status.getY();
        this.position.z = status.getZ();




    }









}
