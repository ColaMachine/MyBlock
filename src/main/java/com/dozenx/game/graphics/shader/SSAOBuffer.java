package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.ByteUtil;
import com.sun.prism.ps.Shader;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by luying on 17/4/22.
 */
public class SSAOBuffer {

    private  int ssaoFBO,ssaoBlurFBO;

   public  int ssaoColorBuffer, ssaoColorBufferBlur,noiseTexture;
    List<GL_Vector> ssaoKernel =new ArrayList<>();
    public SSAOBuffer(){

        init();
    }

    
    
    public void init(){
        //创建ssaoFBO  ssaoBlurFBO 帧缓冲
        ssaoFBO = glGenFramebuffers();
        ssaoBlurFBO = glGenFramebuffers();
        //绑定告诉系统当前使用的帧缓冲是这个
        glBindFramebuffer(GL_FRAMEBUFFER, ssaoFBO);//gBuffer
        //创建一个帧缓冲的纹理和创建普通纹理差不多：
        // SSAO color buffer

        ssaoColorBuffer =glGenTextures();

        ShaderUtils.getActiveTextureLoc(ssaoColorBuffer);

        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RED, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        // 帧缓冲连接上纹理
        ShaderUtils.checkGLError();
        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 , GL_TEXTURE_2D, ssaoColorBuffer, 0
        );
        //=====================================
        // and blur stage

        glBindFramebuffer(GL_FRAMEBUFFER, ssaoBlurFBO);//gBuffer
        //创建一个帧缓冲的纹理和创建普通纹理差不多：
        ssaoColorBufferBlur =glGenTextures();
        ShaderUtils.getActiveTextureLoc(ssaoColorBufferBlur);

        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RED, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        // 帧缓冲连接上纹理
        ShaderUtils.checkGLError();
        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 , GL_TEXTURE_2D, ssaoColorBufferBlur, 0
        );

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        //================================
        //创建随机样本 generate sample kernel
        float randomFloats = (float)Math.random();



        for ( int i = 0; i < 64; ++i)
        {
            GL_Vector sample = new GL_Vector((float)Math.random()*2-1,(float)Math.random()*2-1,(float)Math.random());

            sample=sample.normalize();
            sample=sample.mult((float)Math.random());

            float scale = (float) i / 64f;

            scale = lerp(0.1f, 1.0f, scale * scale);
            sample=sample.mult(scale);
            ssaoKernel.add(sample);
        }
        // generate noise texture
        // ----------------------

        List<GL_Vector> ssaoNoise =new ArrayList<>();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(48*4);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(48);

        for ( int i = 0; i < 16; i++)
        {GL_Vector noise = new GL_Vector((float)Math.random()*2-1,(float)Math.random()*2-1,0);
            ssaoNoise.add(noise);

            byteBuffer.put(ByteUtil.getBytes(noise.x)).put(ByteUtil.getBytes(noise.y)).put(ByteUtil.getBytes(noise.z));
            floatBuffer . put(noise.x).put(noise.y).put(noise.z);
        }

        byteBuffer.flip();
        floatBuffer.flip();
        noiseTexture   = glGenFramebuffers();
        ShaderUtils.getActiveTextureLoc(noiseTexture);

        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGB16F, 4,4, 0, GL_RGB, GL_FLOAT,floatBuffer
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);


        ///=======================


        ShaderManager.shaderSSAO.use();
        ShaderManager.shaderSSAO.setInt("gPosition", ShaderUtils.getActiveTextureLoc(DelayBuffer.gPosition));
        ShaderManager. shaderSSAO.setInt("gNormal", ShaderUtils.getActiveTextureLoc(DelayBuffer.gNormal));
        ShaderManager.shaderSSAO.setInt("texNoise", ShaderUtils.getActiveTextureLoc(this.noiseTexture));
        ShaderManager.  shaderSSAOBlur.use();
        ShaderManager. shaderSSAOBlur.setInt("ssaoInput", ShaderUtils.getActiveTextureLoc(ssaoColorBuffer));




        //创建vao
        float quadVertices[] = {
                // positions        // texture Coords
                -1.0f,  1.0f, 0.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
                1.0f,  1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 0.0f, 1.0f, 0.0f,
                1.0f,  1.0f, 0.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
        };
        ShaderManager.shaderSSAO.getVao().getVertices().put(quadVertices);

        ShaderUtils.freshVao(ShaderManager.shaderSSAO,ShaderManager.shaderSSAO.getVao());

        ShaderManager.shaderSSAOBlur.getVao().getVertices().put(quadVertices);

        ShaderUtils.freshVao(ShaderManager.shaderSSAOBlur,ShaderManager.shaderSSAOBlur.getVao());



        ShaderManager.shaderLightingPass.use();
        ShaderManager.shaderLightingPass.setInt("ssao",ShaderUtils.getActiveTextureLoc(ssaoColorBufferBlur));

    }
    
    List<GL_Vector> lightPositions  =new ArrayList<>();
    
    List<GL_Vector> lightColors  =new ArrayList<>();
    
    


    //通过hdr的方式 把世界缓存到帧里 hdrfbo
    public void render(ShaderManager shaderManager,WorldRenderer worldRenderer){


        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ssaoFBO);     OpenglUtils.checkGLError();
        glClear(GL_COLOR_BUFFER_BIT);
        GL20.glUseProgram(shaderManager.shaderSSAO.getProgramId()); OpenglUtils.checkGLError();


        for ( int i = 0; i < 64; ++i)
        ShaderManager.shaderSSAO.setVec3("samples[" + i + "]", ssaoKernel.get(i));

      /*  GL13.glActiveTexture(GL13.GL_TEXTURE12);
        glBindTexture(GL_TEXTURE_2D,gPosition );
        GL13. glActiveTexture(GL13.GL_TEXTURE13);
        glBindTexture(GL_TEXTURE_2D, gNormal);
        GL13. glActiveTexture(GL13.GL_TEXTURE17);
        glBindTexture(GL_TEXTURE_2D, noiseTexture);
*/


        ShaderUtils.finalDraw(ShaderManager.shaderSSAO,ShaderManager.shaderSSAO.getVao());
        //去掉fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0); OpenglUtils.checkGLError();
        //  glViewport(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);      OpenglUtils.checkGLError();
//===============================================================



        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ssaoBlurFBO);     OpenglUtils.checkGLError();
        glClear(GL_COLOR_BUFFER_BIT);
        GL20.glUseProgram(shaderManager.shaderSSAOBlur.getProgramId()); OpenglUtils.checkGLError();




 /*       GL13.glActiveTexture(GL13.GL_TEXTURE15);
        glBindTexture(GL_TEXTURE_2D,ssaoColorBuffer );*/



        ShaderUtils.finalDraw(ShaderManager.shaderSSAOBlur,ShaderManager.shaderSSAOBlur.getVao());
        //去掉fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0); OpenglUtils.checkGLError();




    }

    public  void  renderQuad(){




    }


    float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }

}
