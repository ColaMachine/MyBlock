package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.log.LogUtil;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import glmodel.GL_Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/9/25.
 */
public class AttackManager {
    public static List<Ball> list =new ArrayList<>();
    public static void add(Ball ball){
        list.add(ball);
    }
    public static void update(){
        ShaderManager.anotherShaderConfig.getVao().getVertices().rewind();
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.position;
            for(int j=LivingThingManager.livingThings.size()-1;j>=0;j--){

                //LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");
                if(GL_Vector.length(new GL_Vector(vector,LivingThingManager.livingThings.get(j).position))<0.5){
                    ball.died=true;
                    LivingThingManager.livingThings.get(j).beAttack(5);
                }
            }
            ball.update();
            if(ball.died){
                list.remove(i);
            }
        }
        ShaderUtils.createVao(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),new int[]{3,3,3,1});
    }

    public static void update2(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.position;
            for(int j=LivingThingManager.livingThings.size()-1;j>=0;j--){
                //LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");

//                LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");

                if(GL_Vector.length(new GL_Vector(vector,LivingThingManager.livingThings.get(j).position))<0.5){
                    ball.died=true;
                    LivingThingManager.livingThings.get(j).beAttack(5);
                }
            }
            ball.update();
            if(ball.died){
                list.remove(i);
            }
        }

    }
    public static void render(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            ball.render();
        }

        ShaderUtils.finalDraw(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao());
    }

}
