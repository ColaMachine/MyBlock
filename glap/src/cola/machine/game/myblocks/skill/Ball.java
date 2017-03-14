package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.ShaderConfig;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/9/25.
 */
public class Ball  {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    LivingThingBean from ;
    GL_Vector position;

    public GL_Vector getPosition() {
        return position;
    }

    public void setPosition(GL_Vector position) {
        this.position = position;
    }

    GL_Vector direction;
   float sumDistance=0;
    int distance=40;
    boolean died=false;
    int width;
    float speed;
    int height;
    Long startTime;
    Component component;
    ItemDefinition itemDefinition;
    public Ball(GL_Vector position , GL_Vector direction, float speed, ItemType itemType, LivingThingBean from){
        this.from = from;
        component= new Component(1,1,1);
        startTime=System.currentTimeMillis();
        this.speed = speed;
        this.position =position;
        this.direction=direction;
        itemDefinition = ItemManager.getItemDefinition(itemType);
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
    public boolean jinzhi;
    public void update(ShaderConfig shaderConfig ){
        if( speed>0) {
            Long nowTime = System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            float _distance = speed * nowTime / 1000;
            sumDistance += _distance;
            this.position.x += this.direction.x * _distance;
            this.position.y += this.direction.y * _distance;
            this.position.z += this.direction.z * _distance;

        }
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
       // component.build2d (shaderConfig,rotateMatrix);
        this.itemDefinition.getItemModel().renderShader(shaderConfig, rotateMatrix);
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
