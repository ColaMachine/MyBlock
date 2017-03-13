package com.dozenx.game.engine.item.action;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.Ball;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import glmodel.GL_Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 16/9/25.
 */
public class ItemManager {
    public static List<Ball> list =new ArrayList<>();
    public static void add(Ball ball){
        list.add(ball);
    }
    public static void update(){
        ShaderManager.anotherShaderConfig.getVao().getVertices().rewind();
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.getPosition();
                LivingThing player = CoreRegistry.get(Player.class);
                if( GL_Vector.length(new GL_Vector(vector,player.getPosition()))<0.5){

                    //TODO 拾取
                   // ball.=true;
                   // LivingThingManager.livingThings.get(j).beAttack(5);
                }

            ball.update();
           /* if(ball.died){
                list.remove(i);
            }*/
        }
        ShaderUtils.createVao(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), new int[]{3, 3, 3, 1});
    }


    public static void render(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            ball.render();
        }

        ShaderUtils.finalDraw(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao());
    }

}
