package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.AABB;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.graphics.shader.ShaderManager;
import glmodel.GL_Matrix;
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
    int distance=123;
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

       /* GamingState.instance.lightPos.x= this.position.x;
        GamingState.instance.lightPos.y= this.position.y;
        GamingState.instance.lightPos.z= this.position.z;
        GamingState.lightPosChanged=true;*/
        if(sumDistance>distance){
            this.died=true;
        }
        GL11.glTranslatef(position.x, position.y, position.z);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
        component.build(ShaderManager.anotherShaderConfig,translateMatrix);
    }
    public void render(){
//        LogUtil.println(position.toString());

        if(!Switcher.SHADER_ENABLE)
        GL11.glTranslatef(position.x, position.y, position.z);
      //  GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
       // component.build(ShaderManager.anotherShaderConfig,translateMatrix);
        if(!Switcher.SHADER_ENABLE)
        GL11.glTranslatef(-position.x, -position.y, -position.z);
        return;
    }

}
