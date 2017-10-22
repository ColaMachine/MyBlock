package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/9/25.
 */
public class TextBall {
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
    public TextBall(String text,GL_Vector position){
        this.id=id;

        //component.setItem();
    }

    public boolean jinzhi;
    int type=1;
    GL_Vector p1 =new GL_Vector(0,0,0);
    GL_Vector p2 =new GL_Vector(1,0,0);
    GL_Vector p3 =new GL_Vector(1,1,0);
    GL_Vector p4 =new GL_Vector(0,1,0);
    GL_Vector normal =new GL_Vector(0,0,1);
    public void update(ShaderConfig shaderConfig ){


            GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
            float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


            ShaderUtils.draw3dImage(shaderConfig,shaderConfig.getVao(),rotateMatrix,p1,p4,p3,p2,normal,itemDefinition.getItemModel().getIcon());

            ShaderUtils.draw3dImage(shaderConfig,shaderConfig.getVao(),rotateMatrix,p1,p2,p3,p4,normal,itemDefinition.getItemModel().getIcon());



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
