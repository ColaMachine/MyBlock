package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.ColorBlock;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector4f;
import java.util.*;

/**
 * Created by luying on 16/9/25.
 */
public class AttackManager {
    public AttackManager(LivingThingManager livingThingManager){
        this.livingThingManager =livingThingManager;
    }
    private LivingThingManager livingThingManager;
    public static List<Ball> list =new ArrayList<>();

    public static List<Ball> drawThings =new ArrayList<>();
    public static ColorBlock selectThing =null;

    public static List<Ball> diedList =new ArrayList<>();
    Vector4f color =new Vector4f(1,0,0,1);
    public static Queue<TimeString> texts= new LinkedList<TimeString>();
    public static void add(Ball ball){
        list.add(ball);
    }
    public static void addText(TimeString timeString){
        texts.offer(timeString);
    }
    GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
    public  void update(){
        ShaderManager.anotherShaderConfig.getVao().getVertices().rewind();
        if(selectThing!=null){
            ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),selectThing.x,selectThing.y,selectThing.z,new GL_Vector(0,0,0),selectThing.width,selectThing.height,selectThing.thick,0.5f);

        }

        for(int i=diedList.size()-1;i>=0;i--) {
            Ball ball = diedList.get(i);
            if(ball.died) {
                list.remove(ball);
                diedList.remove(i);
            }else{
                ball.update(ShaderManager.anotherShaderConfig);
            }

        }
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.position;
            for(int j=livingThingManager.livingThings.size()-1;j>=0;j--){
                LivingThing livingThing = livingThingManager.livingThings.get(j);
                //LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");
                if(ball.from!= livingThing&& GL_Vector.length(new GL_Vector(vector,livingThing.getPosition()))<1){
                    livingThingManager.livingThings.get(j).beAttack(10);
                    ball.readyDied=true;
                    //stexts.offer(new TimeString(5+""));
                    //Vector2f xy = OpenglUtils.wordPositionToXY(projection,livingThing.getPosition(),GamingState.instance.camera.Position,GamingState.instance.camera.getViewDir());
                    //texts.offer(new TimeString(5+"",xy.x*Constants.WINDOW_WIDTH,xy.y*Constants.WINDOW_HEIGHT));
                    //livingThingManager.livingThings.get(j).beAttack(5);
                }
            }
           /** for(int j=0;j<livingThingManager.livingThings.size();j++){
                if(livingThingManager.livingThings.get(j).getPosition().sub(ball.getPosition()).length()<1){
                    ball.died=true;
                    livingThingManager.livingThings.get(j).beAttack(10);
                }
            }**/
            ball.update(ShaderManager.anotherShaderConfig);


            if(ball.readyDied){
                ;
                diedList.add(list.remove(i));
            }
        }
        ShaderManager.uifloatShaderConfig.getVao().getVertices().rewind();
      // Iterator<TimeString> shanghais = texts.iterator();
        int index=0;
        Long now= TimeUtil.getNowMills();
        while (texts.peek()!=null && (now -texts.peek().getStartTime()) >5000) {


                texts.poll();

        }
        Iterator<TimeString> shanghais = texts.iterator();
        //伤害文字
        while (shanghais.hasNext()){
            index++;
            TimeString shanghai = shanghais.next();
                //GL11.glColor4f(1, 1, 1, 1);
            color.w= 1-(now- shanghai.getStartTime())/5000;
                ShaderUtils.printText(shanghai.getText(),(int)shanghai.x,(int)shanghai.y -(int)(now- shanghai.getStartTime())*100/5000,0,24,color,ShaderManager.uifloatShaderConfig );
                OpenglUtils.checkGLError();
                //GLApp.print((int)minX,(int)minY,this.innerText);
        }

        //ShaderUtils.createVao(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),anotherShaderConfig);


    }
    int anotherShaderConfig[] = new int[]{3,3,3,1};
    int uiFloatShaderConfig[] = new int[]{3,2,1,4};

   /* public static void update2(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.position;
            for(int j=LivingThingManager.livingThings.size()-1;j>=0;j--){
                //LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");

//                LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");

                if(GL_Vector.length(new GL_Vector(vector,LivingThingManager.livingThings.get(j).getPosition()))<0.5){
                    ball.died=true;
                    LivingThingManager.livingThings.get(j).beAttack(5);
                }
            }
            ball.update();
            if(ball.died){
                list.remove(i);
            }
        }

    }*/
    public  void render(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            ball.render();
        }
        ShaderUtils.freshVao(ShaderManager.uifloatShaderConfig,ShaderManager.uifloatShaderConfig.getVao());

        ShaderUtils.freshVao(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao());
        ShaderUtils.finalDraw(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao());
//        ShaderUtils.tempfinalDraw(ShaderManager.terrainShaderConfig,ShaderManager.anotherShaderConfig.getVao());
        ShaderUtils.finalDraw(ShaderManager.uifloatShaderConfig,ShaderManager.uifloatShaderConfig.getVao());

    }

}
