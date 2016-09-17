package cola.machine.game.myblocks.lifething.manager;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.human.Player;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import glmodel.GL_Vector;
import util.MathUtil;

import javax.vecmath.Point3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/9/17.
 */
public class LivingThingManager {
    public static List<LivingThing> livingThings = new ArrayList<>();
    public LivingThingManager(){
        LivingThing livingThing =new LivingThing();
        livingThing.position=new Point3f(0,4,0);
        livingThings.add(livingThing);

    }
    public void render(){
        for(LivingThing livingThing:livingThings){
            livingThing.render();
        }
    }
    public void cycle(){

    }

    public LivingThing findTarget(Point3f position){
        for(int i=0;i<livingThings.size();i++){
            LivingThing livingThing=livingThings.get(i);
            if(Math.sqrt((livingThing.position.x-position.x)*(livingThing.position.x-position.x) +
                    (livingThing.position.y-position.y)*(livingThing.position.y-position.y)+
                    (livingThing.position.y-position.y)*(livingThing.position.y-position.y))<4){

                return  livingThing;
            }


        }
        return null;
    }

    public LivingThing chooseObject(GL_Vector from, GL_Vector direction){
        for(int i=0;i<livingThings.size();i++){
            LivingThing livingThing=livingThings.get(i);
            AABB aabb = new AABB(new Vector3f(livingThing.position.x-0.5f,),new Vector3f());

           livingThing.intersectRectangle(from,direction);


        }
        return null;
    }
    public void findWay(){


    }

    public void move(){

    }
    public void attack(){

    }
    public void beAttack(){

    }
}
