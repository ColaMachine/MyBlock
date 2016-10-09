package cola.machine.game.myblocks.lifething.manager;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.inventory.HeadDialog;
import glmodel.GL_Vector;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/9/17.
 */
public class BehaviorManager extends    Thread{
    public void drop(){

    }
    public void crash(){

    }
    public void attack(){

    }
    public void animation(){

    }
    AnimationManager animationManager;

    public AnimationManager getAnimationManager(){
        if(  animationManager == null){
            synchronized (this) {
                if(animationManager!=null)return animationManager;

                animationManager = CoreRegistry.get(AnimationManager.class);
            }
        }
        return animationManager;
    }
    public void attack(LivingThing from,LivingThing to){
        getAnimationManager().apply(from.bodyComponent,"attack");
        getAnimationManager().apply(to.bodyComponent,"beattack");

    }

    public void moveOrAttack(){
        for(LivingThing livingThing : LivingThingManager.livingThings){

            if(livingThing.target!=null ){
                GL_Vector direction =  GL_Vector.sub(LivingThingManager.player.position,livingThing.position);
                livingThing.WalkDir= direction;
                if(GL_Vector.length(direction)<livingThing.attackDistance){
                    attack(livingThing,livingThing.target);
                    livingThing.nextPosition=null;
                }else{
                    this.getAnimationManager().apply(livingThing.bodyComponent,"walkerFoward");
                    livingThing.position = GL_Vector.add(livingThing.position,GL_Vector.multiply(direction.normalize(),livingThing.speed*1));
                }

            }
        }
    }
    public void findThing(){
        for(LivingThing livingThing : LivingThingManager.livingThings){
            if(livingThing.target!=null )continue;
            if(GL_Vector.length(GL_Vector.sub(livingThing.position,LivingThingManager.player.position))<livingThing.sight){
                livingThing.target=LivingThingManager.player;
            }
        }
    }
    public void findWay(){
        /*for(LivingThing livingThing : LivingThingManager.livingThings){
            if(livingThing.target!=null   ){

            }
        }*/

    }
    public void changeDir(){

    }

    public void run(){
        while(true) {
            try {
                //this.findThing();
                //this.changeDir();
                //this.moveOrAttack();
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch(Exception e ){
                e.printStackTrace();
            }
        }

    }

}
