package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FloatBufferWrap;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import java.nio.FloatBuffer;

/**
 * 物品模型 单面模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class DoorModel implements Model {
    public TextureInfo front; //如果是盒状的话有四面 其实应该纳入什么模型具体对象中的
    public TextureInfo back;
    static GL_Vector P1 =new GL_Vector(0,0,0.2f);
    static GL_Vector P2 =new GL_Vector(1,0,0.2f);
    static GL_Vector P3 =new GL_Vector(1,0,0);
    static GL_Vector P4 =new GL_Vector(0,0,0);

    static GL_Vector P5 =new GL_Vector(0,1,0.2f);
    static GL_Vector P6 =new GL_Vector(1,1,0.2f);
    static GL_Vector P7 =new GL_Vector(1,1,0);
    static GL_Vector P8 =new GL_Vector(0,1,0);

    GL_Vector normal =new GL_Vector(0,0,1);


    public DoorModel(TextureInfo ti){
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
        FloatBufferWrap floatBuffer = config.getVao().getVertices();
        if(front!=null) {
            // ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P1,P2,P6,P5,new GL_Vector(0,0,1f),front);
            ShaderUtils.draw3dImage(P1, P2, P6, P5, translateMatrix, new GL_Vector(0, 0, 1f), front, floatBuffer, config);
        }
        if(back!=null) {
            //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
            ShaderUtils.draw3dImage(P3,P4,P8,P7,translateMatrix,new GL_Vector(0,0,-1),back,floatBuffer, config);
        }


    }

    @Override
    public void build(ShaderConfig config, Vao vao, int x, int y, int z) {
        FloatBufferWrap floatBuffer = config.getVao().getVertices();
        if(front!=null) {

            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,P1, P2, P6, P5, BoxModel.FRONT_DIR, icon);
        }



        if(back!=null) {

            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig,vao,P3,P4,P8,P7, BoxModel.BACK_DIR, icon);
        }
    }


}
