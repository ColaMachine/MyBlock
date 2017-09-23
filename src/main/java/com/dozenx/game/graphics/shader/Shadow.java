package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;

/**
 * Created by luying on 17/4/22.
 */
public class Shadow {
    //阴影纹理
    private  int depthMap;
    //阴影缓冲帧
    private int depthMapFBO;
    //阴影生成的时候用到的光线视角矩阵

    private GL_Matrix lightViewMatrix;
    public Shadow(){
        init();//初始化
    }
    /**
     * 阴影缓冲帧的初始化
     */

    int SHADOW_WIDTH = 1024, SHADOW_HEIGHT = 1024;// Constants.WINDOW_WIDTH, SHADOW_HEIGHT =  Constants.WINDOW_HEIGHT
    public void init() {
        //初始化正交矩阵 阴影灯的视角用
        float near_plane = 1.0f, far_plane = 107.5f;
        GL_Matrix ortho = GL_Matrix.ortho(-10.0f, 10.0f, -10.0f, 10.0f, near_plane, far_plane);
        lightViewMatrix = GL_Matrix.multiply(ortho, GL_Matrix.LookAt(new GL_Vector(0, 15, 5), new GL_Vector(0, -0.3f, -1f)));

        //首先，我们要为渲染的深度贴图创建一个帧缓冲对象：
        depthMapFBO = glGenFramebuffers(); OpenglUtils.checkGLError();

        //然后，创建一个2D纹理，提供给帧缓冲的深度缓冲使用：

        depthMap = GL11.glGenTextures(); OpenglUtils.checkGLError();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthMap);
        OpenglUtils.checkGLError();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D,
                0,                        // level of detail
                GL11.GL_DEPTH_COMPONENT,            // internal format for texture is RGB with Alpha
                SHADOW_WIDTH, SHADOW_HEIGHT,                    // size of texture image
                0,                        // no border
                GL11.GL_DEPTH_COMPONENT,            // incoming pixel format: 4 bytes in RGBA order
                GL11.GL_UNSIGNED_BYTE,    // incoming pixel data type: unsigned bytes
                (ByteBuffer) null);                // incoming pixels

        OpenglUtils.checkGLError();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT); OpenglUtils.checkGLError();

        //把我们把生成的深度纹理作为帧缓冲的深度缓冲：
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, depthMapFBO); OpenglUtils.checkGLError();
        //将texture 和fbo结合 这个fbo最后用于存储我们绘制的一帧 这个帧画面存储到纹理中 这里的GL_DEPTH_ATTACHMENT 是
        //当把一个纹理链接到帧缓冲的颜色缓冲上时，我们可以指定一个颜色附件
        //一直使用着GL_COLOR_ATTACHMENT0，  也可指定GL_COLOR_ATTACHMENT1
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthMap, 0);
        GL11.glDrawBuffer(GL11.GL_NONE); OpenglUtils.checkGLError();
        GL11.glReadBuffer(GL11.GL_NONE); OpenglUtils.checkGLError();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        ShaderUtils.checkGLError();
        //阴影矩阵unfiorm 已经移入initUniform里了 shadowLightVeiwMatrix model矩阵其实可以弃用了
    }

    public void render(ShaderManager shaderManager,WorldRenderer worldRenderer){

        GL20.glUseProgram(shaderManager.shadowShaderConfig.getProgramId());
        // glEnable(GL_DEPTH_TEST);
        //glUniformMatrix4fv(lightSpaceMatrixLocation, 1, GL_FALSE, glm::value_ptr(lightSpaceMatrix));
        glViewport(0, 0,SHADOW_WIDTH,   SHADOW_HEIGHT);
        //绑定使用帧缓冲 fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, shaderManager.shadow.depthMapFBO);
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        //GL30.RenderScene(simpleDepthShader);
        glCullFace(GL_FRONT);
        if(!Switcher.hideTerrain) {
            worldRenderer.render(shaderManager.shadowShaderConfig);
        }
        ShaderUtils.finalDraw(shaderManager.shadowShaderConfig,ShaderManager.anotherShaderConfig.getVao());
        ShaderUtils.finalDraw(shaderManager.shadowShaderConfig,ShaderManager.livingThingShaderConfig.getVao());

        glCullFace(GL_BACK);
        //去掉fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        glViewport(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        // glBindTexture(GL_TEXTURE_2D, shaderManager.depthMap);
    }
    public GL_Matrix getLightViewMatrix() {
        return lightViewMatrix;
    }

    public void setLightViewMatrix(GL_Matrix lightViewMatrix) {
        this.lightViewMatrix = lightViewMatrix;
    }

    public int getDepthMap() {
        return depthMap;
    }

    public void setDepthMap(int depthMap) {
        this.depthMap = depthMap;
    }

    public int getDepthMapFBO() {
        return depthMapFBO;
    }

    public void setDepthMapFBO(int depthMapFBO) {
        this.depthMapFBO = depthMapFBO;
    }
}
