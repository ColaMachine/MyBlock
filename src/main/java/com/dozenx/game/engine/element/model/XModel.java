package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

/**
 * 物品模型 单面模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class XModel implements Model {
    GL_Vector p1 =new GL_Vector(0,0,1);
    GL_Vector p2 =new GL_Vector(1,0,1);
    GL_Vector p3 =new GL_Vector(1,0,0);
    GL_Vector p4 =new GL_Vector(0,0,0);

    GL_Vector p5 =new GL_Vector(0,1,1);
    GL_Vector p6 =new GL_Vector(1,1,1);
    GL_Vector p7 =new GL_Vector(1,1,0);
    GL_Vector p8 =new GL_Vector(0,1,0);

    GL_Vector normal =new GL_Vector(0,0,1);


    public XModel(TextureInfo ti){
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
        float angle = /*(float)(Math.PI)+*/3.14f/2;
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


        ShaderUtils.draw3dImage(p2, p1, p4, p3, rotateMatrix, normal, icon, config.getVao().getVertices(), config);

        ShaderUtils.draw3dImage(p1,p2,p3,p4,rotateMatrix,normal,icon,config.getVao().getVertices(),config);

         angle = /*(float)(Math.PI)+*/-3.14f/2;
         rotateMatrix = GL_Matrix.rotateMatrix(0,angle/**3.14f/180,0*/,0);

        rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);


        ShaderUtils.draw3dImage(p2, p1, p4, p3, rotateMatrix, normal, icon, config.getVao().getVertices(), config);

        ShaderUtils.draw3dImage(p1,p2,p3,p4,rotateMatrix,normal,icon,config.getVao().getVertices(),config);


    }
GL_Vector getNormal1 = new GL_Vector(1,0,1).normalize();
    public void build(ShaderConfig config , Vao vao, int x ,int y,int z){
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(x, y, z);

        GL_Vector newp1 = new GL_Vector(p1.x+x,p1.y+y,p1.z+z);
        GL_Vector newp3 = new GL_Vector(p3.x+x,p3.y+y,p3.z+z);
        GL_Vector newp7 = new GL_Vector(p7.x+x,p7.y+y,p7.z+z);
        GL_Vector newp5 = new GL_Vector(p5.x+x,p5.y+y,p5.z+z);
        ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,newp1, newp3, newp7, newp5, getNormal1, icon);
        ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,newp3, newp1, newp5, newp7, getNormal1, icon);
        GL_Vector newp2 = new GL_Vector(p2.x+x,p2.y+y,p2.z+z);
        GL_Vector newp4 = new GL_Vector(p4.x+x,p4.y+y,p4.z+z);
        GL_Vector newp6 = new GL_Vector(p6.x+x,p6.y+y,p6.z+z);
        GL_Vector newp8 = new GL_Vector(p8.x+x,p8.y+y,p8.z+z);

        ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,newp2, newp4, newp8, newp6, getNormal1, icon);
        ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,newp4, newp2, newp6, newp8, getNormal1, icon);


    }

}
