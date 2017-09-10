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
public class DelayBuffer {
   private   int textureId;
    private  int fboId;



    public DelayBuffer(){

        init();
    }
    int gPosition, gNormal, gColorSpec;
    public void init(){
        //创建hdrFBo帧缓冲
        fboId = glGenFramebuffers();
        //绑定告诉系统当前使用的帧缓冲是这个
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);//gBuffer
//        int colorTexture0= glGenTextures();
//        int colorTexture1= glGenTextures();
        //创建一个帧缓冲的纹理和创建普通纹理差不多：
        gPosition =glGenTextures();

       test(gPosition,0);

        gNormal =glGenTextures();
        test(gNormal,1);
        gColorSpec =glGenTextures();
        test(gColorSpec,2);

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        ShaderUtils.checkGLError();




    }
    public void test(int textureId,int index){

        Integer glTexLoc = ShaderUtils.getActiveTextureLoc(textureId);//从


        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // 帧缓冲连接上纹理


        ShaderUtils.checkGLError();

        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 +index, GL_TEXTURE_2D, textureId, 0
        );


    }
    public void initVao(){
        ShaderUtils.initVao(ShaderManager.delayConfig,ShaderManager.delayConfig.getVao());

        ShaderUtils.draw2dImg(ShaderManager.delayConfig,ShaderManager.delayConfig.getVao(),colorBuffers[0]);
        ShaderUtils.freshVao(ShaderManager.delayConfig,ShaderManager.delayConfig.getVao());


    }
    public void renderVbo(){

    }

    //通过hdr的方式 把世界缓存到帧里 hdrfbo
    public void render(WorldRenderer worldRenderer){

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fboId);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // 我们现在不用模板缓冲
        OpenglUtils.checkGLError();
        OpenglUtils.checkGLError();
        worldRenderer.render();
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
