package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.ColorBlock;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 物品模型 单面模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class IconModel implements Model {
    GL_Vector p1 =new GL_Vector(0,0,0);
    GL_Vector p2 =new GL_Vector(1,0,0);
    GL_Vector p3 =new GL_Vector(1,1,0);
    GL_Vector p4 =new GL_Vector(0,1,0);
    GL_Vector normal =new GL_Vector(0,0,1);


    public IconModel(TextureInfo ti){
        this.icon =ti;
    }
    public TextureInfo getIcon() {
        return icon;
    }

    public void setIcon(TextureInfo icon) {
        this.icon = icon;
    }

    TextureInfo icon;


    @Override
    public Component getRootComponent() {
        return null;
    }

    public void build(ShaderConfig config ,GL_Matrix translateMatrix){
        //GL_Matrix translateMatrix = GL_Matrix.translateMatrix(this.position.x, this.position.y, this.position.z);
        float angle = /*(float)(Math.PI)+*/-GamingState.player.getHeadAngle()-3.14f/2;
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


        ShaderUtils.draw3dImage(p2, p1, p4, p3, rotateMatrix, normal, icon, config.getVao().getVertices(), config);

        ShaderUtils.draw3dImage(p1,p2,p3,p4,rotateMatrix,normal,icon,config.getVao().getVertices(),config);


    }


}