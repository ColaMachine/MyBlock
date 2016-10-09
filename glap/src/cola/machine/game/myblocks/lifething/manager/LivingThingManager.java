package cola.machine.game.myblocks.lifething.manager;

import check.CrashCheck;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.human.Player;
import cola.machine.game.myblocks.network.Client;
import cola.machine.game.myblocks.network.SynchronTask;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.ui.inventory.HeadDialog;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import glapp.GLApp;
import glmodel.GL_Vector;
import util.MathUtil;
import util.OpenglUtil;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luying on 16/9/17.
 */
public class LivingThingManager {
    public static List<LivingThing> livingThings = new ArrayList<>();

    public LivingThing target ;
    public static LivingThing player;
    Component component;
    public LivingThingManager(){

        LivingThing livingThing =new LivingThing();
        livingThing.position=new GL_Vector(0,4,0);
        livingThings.add(livingThing);
         component =new Component(2,16,2);
        component.bend(180,50);
        component.setShape(TextureManager.getShape("human_body"));
CoreRegistry.put(LivingThingManager.class,this);
       // Thread thread = new Thread(behaviorManager);

        //behaviorManager.run();
    // livingThings.add(CoreRegistry.get(Human.class));


    }
    public void setPlayer(LivingThing livingThing){
        this.player=livingThing;
    }
    public void add(LivingThing livingThing){
        livingThings.add(livingThing);
        livingThingsMap.put(livingThing.id,livingThing);
    }
    public LivingThing getLivingThingById(int id){
return livingThingsMap.get(id);
    }
    public Map<Integer,LivingThing> livingThingsMap =new HashMap();

    public void render(){
        for(LivingThing livingThing:livingThings){
            livingThing.render();
            //livingThing.renderBloodBar();
           livingThing.distance = GL_Vector.length( GL_Vector.sub(player.position,livingThing.position));

        }
        this.player.render();
       // component.renderBend();


    }


    public void cycle(){

    }

    public void CrashCheck(  DropControlCenter dcc){
        for(LivingThing livingThing:livingThings){
            dcc.check(livingThing);

        }
        dcc.check(player);
    }

    /*public LivingThing findTarget(Point3f position){
        for(int i=0;i<livingThings.size();i++){
            LivingThing livingThing=livingThings.get(i);
            if(Math.sqrt((livingThing.position.x-position.x)*(livingThing.position.x-position.x) +
                    (livingThing.position.y-position.y)*(livingThing.position.y-position.y)+
                    (livingThing.position.y-position.y)*(livingThing.position.y-position.y))<4){

                return  livingThing;
            }


        }
        return null;
    }*/

    public LivingThing chooseObject(GL_Vector from, GL_Vector direction){
        LogUtil.println("开始选择");
        Vector3f fromV= new Vector3f(from.x,from.y,from.z);
        Vector3f directionV= new Vector3f(direction.x,direction.y,direction.z);
        for(int i=0;i<livingThings.size();i++){
            LivingThing livingThing=livingThings.get(i);
            AABB aabb = new AABB(new Vector3f(livingThing.position.x-0.5f,livingThing.position.y,livingThing.position.z-0.5f),new Vector3f(livingThing.position.x+0.5f,livingThing.position.y+1.5f,livingThing.position.z+0.5f));

            LogUtil.println(fromV.toString() );
            LogUtil.println(directionV.toString() );
           if( aabb.intersectRectangle(fromV,directionV)){
               LogUtil.println("选中了");
               this.target=livingThing;
               player.target=livingThing;
               CoreRegistry.get(HeadDialog.class).bind(livingThing).show();
                return livingThing;
           }


        }
        LogUtil.println("未选中");
        return null;
    }
    public void findWay(){


    }

    public void move(){

    }
    Client client =CoreRegistry.get(Client.class);

    public void update(){

        while(client.movements.size()>0 && client.movements.peek()!=null){
            String[] msg = client.movements.pop();
            int id = Integer.valueOf(msg[0]);


            float x = Float.valueOf(msg[1]);
            float y = Float.valueOf(msg[2]);
            float z = Float.valueOf(msg[3]);
            if(player.id == id){
                player.setPosition(x,y,z);
                continue;
            }
            //appendRow("color"+curColor, msg);
            LivingThing livingThing = this.getLivingThingById(id);
            if(livingThing==null ){
                 livingThing =new LivingThing();
                livingThing.id=id;
               // livingThing.name=name;
                livingThing.setPosition(x,y,z);

                this.add(livingThing);
            }
            livingThing.setPosition(x,y,z);

        }
       /* while(client.newborns.size()>0 && client.newborns.peek()!=null){
            String[] msg = client.movements.pop();
            int id = Integer.getInteger(msg[0]);
            String name = msg[1];
            float x = Float.valueOf(msg[2]);
            float y = Float.valueOf(msg[3]);
            float z = Float.valueOf(msg[4]);
            if(id== player.id){

                player.id=id;
                player.name=name;
                player.setPosition(x,y,z);
                continue;
            }

            //appendRow("color"+curColor, msg);
            LivingThing livingThing =new LivingThing();
            livingThing.id=id;
            livingThing.name=name;
            livingThing.setPosition(x,y,z);

           this.add(livingThing);

        }*/

    }
   /* public void attack(){
        if(player.target!=null)
        if(GL_Vector.length(GL_Vector.sub(player.target.position,player.position))<4){
            player.target.nowBlood-=5;
            if(player.target.nowBlood<=0){
                player.target.died();
                AnimationManager manager = CoreRegistry.get(AnimationManager.class);
                manager.clear(player.target.bodyComponent);
                manager.apply(player.target.bodyComponent,"died");
                player.target=null;
                target=null;
            }
        }

    }*/
    public void beAttack(){

    }
}
