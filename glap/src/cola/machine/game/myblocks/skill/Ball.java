package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.block.Block;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.element.bean.Component;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/9/25.
 */
public class Ball  {
   public  long lastSynTime;
    public  long lastPickTime;
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
    ChunkProvider chunkProvider  = CoreRegistry.get(ChunkProvider.class);
    public Ball(int id ,GL_Vector position , GL_Vector direction, float speed, ItemType itemType, LivingThingBean from){
        this.id=id;
        this.from = from;
        component= new Component(1,1,1);
        startTime= TimeUtil.getNowMills();
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
    int type=2;
    GL_Vector p1 =new GL_Vector(0,0,0);
    GL_Vector p2 =new GL_Vector(1,0,0);
    GL_Vector p3 =new GL_Vector(1,1,0);
    GL_Vector p4 =new GL_Vector(0,1,0);
    GL_Vector normal =new GL_Vector(0,0,1);
    float size=0.2f;
    boolean readyDied = false;
    public void update(ShaderConfig shaderConfig ){

        if(readyDied){
            size+=0.002f;
            ShaderUtils.draw3dColorBox(shaderConfig,shaderConfig.getVao(),this.position.x,this.position.y,this.position.z,new GL_Vector(0,0,0),size,1-size );
            if(size>1){
                died=true;
            }
            return;
        }


        if( speed>0) {
            Long nowTime =  TimeUtil.getNowMills() - startTime;
            startTime =  TimeUtil.getNowMills();
            float _distance = speed * nowTime / 1000;
            sumDistance += _distance;
            this.position.x += this.direction.x * _distance;
            this.position.y += this.direction.y * _distance;
            this.position.z += this.direction.z * _distance;

        }
        if(sumDistance>distance){
            this.died=true;
        }
        //如果撞到方块停止
        cola.machine.game.myblocks.model.Block block = chunkProvider.getBlockAt((int)this.position.x,(int)this.position.y,(int)this.position.z);
        if(block!=null && block.getId()!=0){
            this.readyDied=true;
        }if(type==2) {

            ShaderUtils.draw3dColorBox(shaderConfig,shaderConfig.getVao(),this.position.x,this.position.y,this.position.z,new GL_Vector(0,0,0),0.1f,1 );
        }else if(type==1){
            GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
            float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


            ShaderUtils.draw3dImage(p2,p1,p4,p3,rotateMatrix,normal,itemDefinition.getItemModel().getIcon(),shaderConfig.getVao().getVertices(),shaderConfig);

            ShaderUtils.draw3dImage(p1,p2,p3,p4,rotateMatrix,normal,itemDefinition.getItemModel().getIcon(),shaderConfig.getVao().getVertices(),shaderConfig);

        }else{
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
