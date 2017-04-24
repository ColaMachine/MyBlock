package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by luying on 17/4/22.
 */
public class Hdr {
   private   int textureId;
    private  int fboId;



    public Hdr(){

        init();
    }
    public void init(){
        //创建hdrFBo帧缓冲
        fboId = glGenFramebuffers();
        //绑定告诉系统当前使用的帧缓冲是这个
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
//        int colorTexture0= glGenTextures();
//        int colorTexture1= glGenTextures();
        //创建一个帧缓冲的纹理和创建普通纹理差不多：
        textureId =glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        // glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        //  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        // glBindTexture(GL_TEXTURE_2D, 0);
        // 帧缓冲连接上纹理

        //GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, hdrFBO);
        ShaderUtils.checkGLError();
        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 , GL_TEXTURE_2D, textureId, 0
        );

        // GL11.glDrawBuffer(GL11.GL_NONE);
        // GL11.glReadBuffer(GL11.GL_NONE);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        ShaderUtils.checkGLError();
//        ///=====================
//        depthMapFBO = glGenFramebuffers();
//
//        //然后，创建一个2D纹理，提供给帧缓冲的深度缓冲使用：
//        int SHADOW_WIDTH = 1024, SHADOW_HEIGHT = 1024;
//        depthMap = GL11.glGenTextures();
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthMap);
//
//        GL11.glTexImage2D(GL11.GL_TEXTURE_2D,
//                0,                        // level of detail
//                GL11.GL_DEPTH_COMPONENT,            // internal format for texture is RGB with Alpha
//                SHADOW_WIDTH, SHADOW_HEIGHT,                    // size of texture image
//                0,                        // no border
//                GL11.GL_DEPTH_COMPONENT,            // incoming pixel format: 4 bytes in RGBA order
//                GL11.GL_UNSIGNED_BYTE,    // incoming pixel data type: unsigned bytes
//                (ByteBuffer) null);                // incoming pixels
//
//
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
//
//        //把我们把生成的深度纹理作为帧缓冲的深度缓冲：
//        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, depthMapFBO);
//        //将texture 和fbo结合 这个fbo最后用于存储我们绘制的一帧 这个帧画面存储到纹理中 这里的GL_DEPTH_ATTACHMENT 是
//        //当把一个纹理链接到帧缓冲的颜色缓冲上时，我们可以指定一个颜色附件
//        //一直使用着GL_COLOR_ATTACHMENT0，  也可指定GL_COLOR_ATTACHMENT1
//        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthMap, 0);
//        GL11.glDrawBuffer(GL11.GL_NONE);
//        GL11.glReadBuffer(GL11.GL_NONE);
//        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
//        ShaderUtils.checkGLError();
        //int attachments[]={GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1};
        //glDrawBuffers(2, attachments);
    }

    public void initVbo(){

    }
    public void renderVbo(){

    }

    //通过hdr的方式 把世界缓存到帧里 hdrfbo
    public void render(WorldRenderer worldRenderer){

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fboId);
        //glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        // glClear(GL_DEPTH_BUFFER_BIT);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // 我们现在不用模板缓冲
        OpenglUtils.checkGLError();
        //  ShaderUtils.finalDraw(ShaderManager.hdrShaderConfig,ShaderManager.lightShaderConfig.getVao());

        //取一个帧缓冲

        //worldRenderer.render();
        // worldRenderer.render(shaderManager.hdrShaderConfig);
        //ShaderUtils.finalDraw(ShaderManager.lightShaderConfig,ShaderManager.lightShaderConfig.getVao());
        OpenglUtils.checkGLError();
        worldRenderer.render();
        // livingThingManager.render();
        //去掉fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        OpenglUtils.checkGLError();
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public int getFboId() {
        return fboId;
    }

    public void setFboId(int fboId) {
        this.fboId = fboId;
    }

}
