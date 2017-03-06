package com.dozenx.game.network.server.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.GameActor;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.command.EquipPartType;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.engine.live.state.WalkState;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.lang.ref.WeakReference;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThingBean extends Role {

    public LivingThingBean(){

    }


    /*public GL_Vector getPosition(){
        return this.getPosition());
    }*/

   // private BagBean bag =new BagBean();
    //private PlayerStatus status ;
    //protected IdleState currentState  = new WalkState(this);
    /*public LivingThingBean(PlayerStatus playerStatus){

        setPlayerStatus(playerStatus);





    }*/






    /*public void setPosition(GL_Vector position) {

        setPosition(position.x,position.y,position.z);
        //




    }*/
    /*public PlayerStatus getStatus(){
        return this.status;
    }*/
  /*  public void setPosition(float posx, float posy, float posz) {
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
       *//* this.position.x=posx;
        this.position.y=posy;
        this.position.z=posz;*//*



    }*/





   /* public void setPlayerStatus(PlayerStatus status){



        setName(status.getName());

        setPwd(status.getPwd());
        setBodyAngle(status.getBodyAngle());
        setHeadAngle(status.getHeadAngle());
        setHeadAngle2(status.getHeadAngle2());
        setHeadEquip( this.getItemBean(status.getHeadEquip()));
        setBodyEquip(status.getBodyEquip());
        setHandEquip(status.getHandEquip());
        setShoeEquip(status.getShoeEquip());
        setLegEquip(status.getLegEquip());
        setTargetId(status.getTargetId());
        setIsplayer(status.isplayer);

        this.id = status.getId();
        setPosition(status.x,status.y,status.z);

        this.headAngle =status.getHeadAngle();
        this.bodyAngle =status.getBodyAngle();
        this.headAngle2 = status.getHeadAngle2();




    }*/
    public static void main(String args[]){
        PlayerStatus playerStatus =new PlayerStatus();
        playerStatus.setId(4);
       byte[] byteAry= new PlayerSynCmd(playerStatus).toBytes();
        byte[] newAry =  ByteUtil.slice(byteAry,4,byteAry.length-4);


        ResultCmd result = new ResultCmd(0,newAry,1);

        byte[] resultAry = result.toBytes();
        ResultCmd receiveResultCmd =  new ResultCmd( resultAry);

        PlayerSynCmd cmd = new PlayerSynCmd(receiveResultCmd.getMsg());
        PlayerStatus newPlayerStatus= cmd.getPlayerStatus();
        System.out.println(newPlayerStatus.getId());

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
        if(this.getExecutor().getCurrentState()!=null &&getExecutor().getCurrentState() != humanState ){
            getExecutor().getCurrentState().dispose();

        }
        getExecutor().setCurrentState(humanState) ;
    }

    public void changeAnimationState(String animationName){

    }

    public void attack(){

    }

    public void unSelect(){

    }

    public void beAttack(){

    }
}
