package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.server.bean.LivingThingBean;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/9/25.
 */
public class Ball  {
    LivingThingBean from ;
    GL_Vector position;
    GL_Vector direction;
   float sumDistance=0;
    int distance=40;
    boolean died=false;
    int width;
    float speed;
    int height;
    Long startTime;
    Component component;
    public Ball(GL_Vector position ,GL_Vector direction,float speed,Shape shape,LivingThingBean from){
        this.from = from;
        component= new Component(1,1,1);
startTime=System.currentTimeMillis();
        this.speed = speed;
        this.position =position;
        this.direction=direction;
        component.setShape(shape);
        //component.setItem();
    }
   /* public Ball(GL_Vector position ,GL_Vector direction,float speed,ItemD shape){
        component= new Component(1,1,1);
        startTime=System.currentTimeMillis();
        this.speed = speed;
        this.position =position;
        this.direction=direction;
        component.setShape(shape);
        component.setItem();
    }*/
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
//        GL11.glTranslatef(position.x, position.y, position.z);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
        //float xzDegree = Math.atan(direction.z,direction.x);
        float angle=GL_Vector.angleXZ(this.direction , new GL_Vector(0,0,-1));
        float angleY= GL_Vector.updownAngle(direction);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(angleY-(float)(45*3.14/180),angle*3.14f/180,0);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


       //GL_Matrix.rotateMatrix(direction.x,direction.y,direction.z);
        component.build(ShaderManager.anotherShaderConfig,rotateMatrix);
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
