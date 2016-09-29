package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.Component;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;

/**
 * Created by luying on 16/9/25.
 */
public class Ball  {
    GL_Vector position;
    GL_Vector direction;
   float sumDistance=0;
    int distance=23;
    boolean died=false;
    int width;
    float speed;
    int height;
    Long startTime;
    Component component;
    public Ball(GL_Vector position ,GL_Vector direction,float speed){
        component= new Component(1,1,1);
startTime=System.currentTimeMillis();
        this.speed = speed;
        this.position =position;
        this.direction=direction;
        component.setShape(TextureManager.getShape("human_head"));
    }
    public void update(){
        Long nowTime=System.currentTimeMillis()-startTime;
        startTime= System.currentTimeMillis();
        float _distance = speed*nowTime/1000;
        sumDistance+=_distance;
        this.position.x+= this.direction.x*_distance;
        this.position.y+= this.direction.y*_distance;
        this.position.z+= this.direction.z*_distance;
        if(sumDistance>distance){
            this.died=true;
        }
    }
    public void render(){
//        LogUtil.println(position.toString());
        GL11.glTranslatef(position.x, position.y, position.z);
        component.render();
        GL11.glTranslatef(-position.x, -position.y, -position.z);
    }

}
