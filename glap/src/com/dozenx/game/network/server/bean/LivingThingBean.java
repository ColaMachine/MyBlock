package com.dozenx.game.network.server.bean;

import cola.machine.game.myblocks.lifething.bean.GameActor;
import cola.machine.game.myblocks.manager.TextureManager;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.engine.live.state.WalkState;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.lang.ref.WeakReference;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThingBean extends PlayerStatus {

    public LivingThingBean(){

    }
    public long getLastHurtTime() {
        return lastHurtTime;
    }
    public void setWalkDir(GL_Vector dir){
        this.WalkDir = dir;
        this.setBodyAngle((float) Math.atan(dir.z / dir.x));
    }


    public void setLastHurtTime(long lastHurtTime) {
        this.lastHurtTime = lastHurtTime;
    }

    private long lastHurtTime = 0;

    public GL_Vector getPosition(){
        return new GL_Vector(getX(),getY(),getZ());
    }

    private BagBean bag =new BagBean();
    //private PlayerStatus status ;
    protected IdleState currentState  = new WalkState(this);
    public LivingThingBean(PlayerStatus playerStatus){

        setPlayerStatus(playerStatus);





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
        return "力量:"+basePower+"/"+totalPower+"\n"
                +"智力:"+baseIntell+"/"+totalIntell+"\n"
                +"敏捷:"+baseAgility+"/"+totalAgility+"\n"
                +"精神:"+baseSpirit+"/"+totalSpirit+"\n"
                +"血量:"+nowBlood+"/"+blood+"\n"
                +"魔法:"+nowEnergy+"/"+energy+"\n"
                +"防御:"+fangyu+"";
    }

    public boolean died=false;
    public void died(){
        this.nowBlood=0;
        died=true;

    }
    public void setPosition(GL_Vector position) {

        setPosition(position.x,position.y,position.z);
        //




    }
    /*public PlayerStatus getStatus(){
        return this.status;
    }*/
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
        this.setX(posx);
        this.setY(posy);
        this.setZ(posz);
       /* this.position.x=posx;
        this.position.y=posy;
        this.position.z=posz;*/



    }






    public void setPlayerStatus(PlayerStatus status){

        setX(status.getX());
        setY(status.getY());
        setZ(status.getZ());

        setName(status.getName());

        setPwd(status.getPwd());
        setBodyAngle(status.getBodyAngle());
        setHeadAngle(status.getHeadAngle());
        setHeadAngle2(status.getHeadAngle2());
        setHeadEquip(status.getHeadEquip());
        setBodyEquip(status.getBodyEquip());
        setHandEquip(status.getHandEquip());
        setShoeEquip(status.getShoeEquip());
        setLegEquip(status.getLegEquip());
        setTargetId(status.getTargetId());
        setIsplayer(status.isplayer());

        this.id = status.getId();
        this.position.x = status.getX();
        this.position.y = status.getY();
        this.position.z = status.getZ();
        this.headAngle =status.getHeadAngle();
        this.bodyAngle =status.getBodyAngle();
        this.headAngle2 = status.getHeadAngle2();

        this.setIsplayer(status.isIsplayer());
        this.name=status.getName();

    }

    public void doSomeThing(ServerContext serverContext){
       /* this.currentState.update();
            if (this.getTargetId() >0) {
                //检查举例 如果举例过远 放弃追逐
                LivingThingBean player= serverContext.getOnlinePlayerById(enemy.getTargetId());
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

            for(LivingThingBean player: serverContext.getAllOnlinePlayer()){


                if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                    enemy.setTargetId(player.getId());
                    //enemy.setTarget(player);
                }

            }*/

    }

    public void changeState(IdleState humanState){
        if(this.currentState!=null &&this.currentState != humanState ){
            currentState.dispose();
            this.currentState =humanState;
        }else{
            this.currentState =humanState;
        }
    }
}
