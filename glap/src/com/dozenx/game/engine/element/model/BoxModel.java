package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.ImageUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 物品模型-盒子模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class BoxModel implements Model {
    public TextureInfo front; //如果是盒状的话有四面 其实应该纳入什么模型具体对象中的
    public TextureInfo back;
    public TextureInfo top;
    public TextureInfo left;
    public TextureInfo right;
    public TextureInfo bottom;
    public GL_Vector P1;
    public GL_Vector P2;
    public  GL_Vector P3;
    public  GL_Vector P4;
    public GL_Vector P5;
    public GL_Vector P6;
    public GL_Vector P7;
    public GL_Vector P8;



    /*public void setEightFace(String name,TextureManager textureManager){

        // this.name =name;
        this.front= textureManager.getTextureInfo(name+"_front");
        this.back= textureManager.getTextureInfo(name+"_back");
        this.left= textureManager.getTextureInfo(name+"_left");
        this.right= textureManager.getTextureInfo(name+"_right");
        this.top= textureManager.getTextureInfo(name+"_top");
        this.bottom= textureManager.getTextureInfo(name+"_bottom");
    }*/

    @Override
    public Component getRootComponent() {
        return null;
    }

    public void build(ShaderConfig config , GL_Matrix rotateMatrix){
        FloatBuffer floatBuffer = config.getVao().getVertices();
        if(front!=null) {
            // ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P1,P2,P6,P5,new GL_Vector(0,0,1f),front);
            ShaderUtils.draw3dImage(P1, P2, P6, P5, rotateMatrix, new GL_Vector(0, 0, 1f), front, floatBuffer, config);
        }
        if(back!=null) {
            //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
            ShaderUtils.draw3dImage(P3,P4,P8,P7,rotateMatrix,new GL_Vector(0,0,-1),back,floatBuffer, config);
        }
        if(top!=null) {
            //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P5,P6,P7,P8,new GL_Vector(0,1,0f),front);
            ShaderUtils.draw3dImage(P5,P6,P7,P8,rotateMatrix,new GL_Vector(0,1,0),top,floatBuffer, config);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(P4,P3,P2,P1,rotateMatrix,new GL_Vector(0,-1,0),bottom,floatBuffer, config);
        }
        if(left!=null) {
            ShaderUtils.draw3dImage(P2,P3,P7,P6,rotateMatrix,new GL_Vector(-1,0,0f),left,floatBuffer, config);
        }
        if(right!=null) {
            ShaderUtils.draw3dImage(P4,P1,P5,P8,rotateMatrix,new GL_Vector(1,0,0),right,floatBuffer, config);
        }
    }

    public  BoxModel(Shape shape){//这个逻辑是错误的 shape 是不应该有itemDefinition属性的
        float thick =shape.getThick();
        float width = shape.getWidth();
        float height= shape.getHeight();
        this.front= shape.getFront();
        this.back= shape.getBack();
        this.left=shape.getLeft();
        this.right=shape.getRight();
        this.top= shape.getTop();
        this.bottom= shape.getBottom();


        this.P1= new GL_Vector(0,0,thick);
        this.P2= new GL_Vector(width,0,thick);
        this.P3= new GL_Vector(width,0,0);
        this.P4= new GL_Vector(0,0,0);
        this.P5= new GL_Vector(0,height,thick);
        this.P6= new GL_Vector(width,height,thick);
        this.P7= new GL_Vector(width,height,0);
        this.P8= new GL_Vector(0,height,0);
    }
}
