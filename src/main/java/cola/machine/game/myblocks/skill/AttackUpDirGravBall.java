package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.model.IBlock;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

/**
 * Created by luying on 16/9/25.
 */
public class AttackUpDirGravBall extends  AttackBall{
    public GL_Vector startPosition;


    public AttackUpDirGravBall(int id, GL_Vector position, GL_Vector direction, float speed, Integer itemType, LivingThingBean from,int species) {
        super(id, position, direction, speed, itemType, from,species);
        this.startPosition= position.copyClone();
        this.speed=3;
    }
    GL_Vector newDirection =new GL_Vector();
    public void update(ShaderConfig shaderConfig ){
        if(died){
            // LogUtil.err("ball already died");
            return;
        }
        if(readyDied){
            size+=0.01f;
            ShaderUtils.draw3dColorBox(shaderConfig,shaderConfig.getVao(),this.position.x,this.position.y,this.position.z,new GL_Vector(0,0,0),size,1-size*2f );
            if(size>0.3){
                died=true;
            }
            return;
        }
        Long nowTime =  TimeUtil.getNowMills() - startTime;
        //如果有速度
        if( speed>0) {

            //startTime =  TimeUtil.getNowMills();
           // float _distance = speed * nowTime / 1000;

            //float downSpeed=0.5f * 19.6f * nowTime * nowTime / 1000000;//�˶��ľ���


            this.position.x = this.direction.x *speed*nowTime/1000;
            this.position.y = (float)((this.direction.y +0.3)* speed*nowTime/1000-0.5*0.6f*nowTime*nowTime/1000000);

            this.position.z =  this.direction.z *speed*nowTime/1000;
            sumDistance = this.position.length();
            this.position.add(this.startPosition);

        }
         newDirection .x=direction.x*speed;
        newDirection.z= direction.z*speed;
        newDirection.y= direction.y*speed-0.6f*nowTime/1000;
       // LogUtil.println("newDirection:"+newDirection);
        if(sumDistance>distance){
           // this.readyDied=true;
            // this.died=true;
        }
        //如果撞到方块停止
        if((int)this.position.y<0){
            //  this.died=true;
            this.readyDied=true;
            return;
        }
        //检查碰撞
        IBlock block = chunkProvider.getBlockAt((int)this.position.x,(int)this.position.y,(int)this.position.z);
        if(block!=null && block.getId()!=0){
            this.readyDied=true;
        }

        {
            //        GL11.glTranslatef(position.x, position.y, position.z);
            GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
            //float xzDegree = Math.atan(direction.z,direction.x);

//(float)(Math.atan2(newDirection.z,newDirection.x));
            float angle=GL_Vector.angleXZ2(new GL_Vector(newDirection.x,0,newDirection.z) , new GL_Vector(0,0,-1));
            float angleY= GL_Vector.updownAngle(newDirection);

//          LogUtil.println("angle:"+angle+"angleY:"+angleY+" y:"+newDirection.y);
//-(float)(45*3.14/180)
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(angleY,angle,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


            //GL_Matrix.rotateMatrix(direction.x,direction.y,direction.z);
            // component.build2d (shaderConfig,rotateMatrix);
            this.itemDefinition.getShape().renderShader(shaderConfig, shaderConfig.getVao(),rotateMatrix);
        }

    }

    public void render(){

    }


}
