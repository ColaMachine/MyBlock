package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import core.log.LogUtil;
import glmodel.GL_Vector;

import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by luying on 17/4/22.
 */
public class DelayBuffer {
   private   int textureId;
    private  int gBuffer;



    public DelayBuffer(){

        init();
    }
    public int gPosition, gNormal, gColorSpec;
    
    
    public void init(){
        //创建hdrFBo帧缓冲
        gBuffer = glGenFramebuffers();
        //绑定告诉系统当前使用的帧缓冲是这个
        glBindFramebuffer(GL_FRAMEBUFFER, gBuffer);//gBuffer
//        int colorTexture0= glGenTextures();
//        int colorTexture1= glGenTextures();
        //创建一个帧缓冲的纹理和创建普通纹理差不多：
        gPosition =glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE12);
        glBindTexture(GL_TEXTURE_2D,gPosition );


        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // 帧缓冲连接上纹理


        ShaderUtils.checkGLError();

        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 , GL_TEXTURE_2D, gPosition, 0
        );
        //=====================================

        gNormal =glGenTextures();
        GL13. glActiveTexture(GL13.GL_TEXTURE13);
        glBindTexture(GL_TEXTURE_2D, gNormal);

        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // 帧缓冲连接上纹理


        ShaderUtils.checkGLError();

        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, gNormal, 0
        );

        //========================================

        gColorSpec =glGenTextures();
        GL13. glActiveTexture(GL13.GL_TEXTURE14);
        glBindTexture(GL_TEXTURE_2D, gColorSpec);


        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // 帧缓冲连接上纹理


        ShaderUtils.checkGLError();

        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, gColorSpec, 0
        );
//       test(gPosition,0);
//
//        gNormal =glGenTextures();
//        test(gNormal,1);
//        gColorSpec =glGenTextures();
//        test1(gColorSpec,2);





        //int attachments[]={GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2};
       IntBuffer bloomAttachments = BufferUtils.createIntBuffer(3);//
        bloomAttachments.put(GL_COLOR_ATTACHMENT0).put(GL_COLOR_ATTACHMENT1).put(GL_COLOR_ATTACHMENT2);
        bloomAttachments.flip();

        GL20.glDrawBuffers( bloomAttachments);OpenglUtils.checkGLError();


         int rboDepth
        =glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboDepth);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboDepth);
        // finally check if framebuffer is complete
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
          LogUtil.err(  "Framebuffer not complete!" );
        glBindFramebuffer(GL_FRAMEBUFFER, 0);



        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        ShaderUtils.checkGLError();

/*
        //把我们把生成的深度纹理作为帧缓冲的深度缓冲：
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, gBuffer); OpenglUtils.checkGLError();
        //将texture 和fbo结合 这个fbo最后用于存储我们绘制的一帧 这个帧画面存储到纹理中 这里的GL_DEPTH_ATTACHMENT 是
        //当把一个纹理链接到帧缓冲的颜色缓冲上时，我们可以指定一个颜色附件
        //一直使用着GL_COLOR_ATTACHMENT0，  也可指定GL_COLOR_ATTACHMENT1

        GL11.glDrawBuffer(GL11.GL_NONE); OpenglUtils.checkGLError();
        GL11.glReadBuffer(GL11.GL_NONE); OpenglUtils.checkGLError();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        */

        
        
        
        //开始绑定纹理
        ShaderManager.shaderLightingPass.use();OpenglUtils.checkGLError();
        
        int NR_LIGHTS = 32;
        for ( int i = 0; i < NR_LIGHTS; i++)
        {
            // calculate slightly random offsets
            float xPos = (float)((Math.random() *16) );
            float yPos = (float)((Math.random()  *2+1) );
            float zPos = (float)((Math.random() *16) );
            lightPositions.add(new GL_Vector(xPos, yPos, zPos));
            // also calculate random color
            float rColor = (float)((Math.random() % 100) / 200.0f) + 0.5f; // between 0.5 and 1.0
            float gColor = (float)((Math.random() % 100) / 200.0f) + 0.5f; // between 0.5 and 1.0
            float bColor = (float)((Math.random() % 100) / 200.0f) + 0.5f; // between 0.5 and 1.0
            lightColors.add(new GL_Vector(rColor, gColor, bColor));
        }
        OpenglUtils.checkGLError();
        
        
        ShaderUtils.checkGLError();
        
        
        // shader configuration
        
       ShaderManager.shaderLightingPass.use();
   /*    ShaderManager.shaderLightingPass.setInt("gPosition", 0);
       ShaderManager.shaderLightingPass.setInt("gNormal", 1);
       ShaderManager.shaderLightingPass.setInt("gAlbedoSpec", 2);*/


      int gPositionTextureLoc = glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(), "gPosition");
        if(gPositionTextureLoc>=0){
            glUniform1i(gPositionTextureLoc, 12);
        }else{
            LogUtil.err("gPosition hasnot find ");
        }


        int gNormalTextureLoc = glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(), "gNormal");
        if(gNormalTextureLoc>=0){
            glUniform1i(gNormalTextureLoc, 13);
        }else{
            LogUtil.err("gNormal hasnot find ");
        }

        int gAlbedoSpecTextureLoc = glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(), "gAlbedoSpec");
        if(gAlbedoSpecTextureLoc>0){
            glUniform1i(gAlbedoSpecTextureLoc, 14);
        }else{
            LogUtil.err("gAlbedoSpec hasnot find ");
        }

        OpenglUtils.checkGLError();


        ShaderManager.shaderLightingPass.use();
        //传送灯光位置
        for ( int i = 0; i < lightPositions.size(); i++)
        {

           // glUniform1i(gNormalTextureLoc, ShaderUtils.getActiveTextureLoc(gNormal));

            int positionLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Position");
            if(positionLoc>=0){
                GL20.glUniform3f(positionLoc, lightPositions.get(i).x,lightPositions.get(i).y,lightPositions.get(i).z);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }


            int colorLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Color");
            if(colorLoc>=0){
                GL20.glUniform3f(colorLoc, lightColors.get(i).x,lightColors.get(i).y,lightColors.get(i).z);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }



            // update attenuation parameters and calculate radius
             float constant = 1.0f; // note that we don't send this to the shader, we assume it is always 1.0 (in our case)
             float linear = 0.7f;
             float quadratic = 1.8f;

            int linearLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Linear");
            if(linearLoc>=0){
                GL20.glUniform1f(linearLoc, linear);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }

            int QuadraticLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Quadratic");
            if(QuadraticLoc>=0){
                GL20.glUniform1f(QuadraticLoc,quadratic);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }



        }
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
        ShaderManager.shaderLightingPass.getVao().getVertices().put(quadVertices);

        ShaderUtils.freshVao(ShaderManager.shaderLightingPass,ShaderManager.shaderLightingPass.getVao());

    }
    
    List<GL_Vector> lightPositions  =new ArrayList<>();
    
    List<GL_Vector> lightColors  =new ArrayList<>();
    
    
    public void test(int textureId,int index){

        Integer glTexLoc = ShaderUtils.getActiveTextureLoc(textureId);//从
       // glBindTexture(GL_TEXTURE_2D, textureId);

        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // 帧缓冲连接上纹理


        ShaderUtils.checkGLError();

        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 +index, GL_TEXTURE_2D, textureId, 0
        );


    }
    public void test1(int textureId,int index){

       // glBindTexture(GL_TEXTURE_2D, index);
       Integer glTexLoc = ShaderUtils.getActiveTextureLoc(textureId);//从


        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null
        );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // 帧缓冲连接上纹理


        ShaderUtils.checkGLError();

        glFramebufferTexture2D(
                GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 +index, GL_TEXTURE_2D, textureId, 0
        );


    }
    public void initVao(){
       // ShaderUtils.initVao(ShaderManager.delayConfig,ShaderManager.delayConfig.getVao());

        //ShaderUtils.draw2dImg(ShaderManager.delayConfig,ShaderManager.delayConfig.getVao(),colorBuffers[0]);
       // ShaderUtils.freshVao(ShaderManager.delayConfig,ShaderManager.delayConfig.getVao());


    }
    public void renderVbo(){

    }

    //通过hdr的方式 把世界缓存到帧里 hdrfbo
    public void render(ShaderManager shaderManager,WorldRenderer worldRenderer){

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //将terrain的纹理全部重新赋值一遍


        GL20.glUseProgram(shaderManager.shaderGeometryPass.getProgramId()); OpenglUtils.checkGLError();
        ShaderUtils.bindTextureFromAnotherConfig(ShaderManager.shaderGeometryPass,ShaderManager.terrainShaderConfig);
        // glEnable(GL_DEPTH_TEST);
        //glUniformMatrix4fv(lightSpaceMatrixLocation, 1, GL_FALSE, glm::value_ptr(lightSpaceMatrix));
      //  glViewport(0, 0, 1024, 1024);   OpenglUtils.checkGLError();
        //绑定使用帧缓冲 fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, gBuffer);     OpenglUtils.checkGLError();
        //glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT); OpenglUtils.checkGLError();
        //GL30.RenderScene(simpleDepthShader);
//        ShaderUtils.draw3dColorBox(shaderManager.shaderGeometryPass,shaderManager.shaderGeometryPass.getVao(),10,5,10,new GL_Vector(1,1,1),1,1,1,1);
//        ShaderUtils.freshVao(shaderManager.shaderGeometryPass,shaderManager.shaderGeometryPass.getVao());
//        ShaderUtils.finalDraw(shaderManager.shaderGeometryPass,shaderManager.shaderGeometryPass.getVao());
       worldRenderer.render(shaderManager.shaderGeometryPass); OpenglUtils.checkGLError();
        ShaderUtils.tempfinalDraw(shaderManager.shaderGeometryPass,ShaderManager.terrainShaderConfig.getVao());
        //去掉fbo
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0); OpenglUtils.checkGLError();
      //  glViewport(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);      OpenglUtils.checkGLError();


        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        ShaderManager.shaderLightingPass.use();
/*
        for ( int i = 0; i < lightPositions.size(); i++)
        {

            // glUniform1i(gNormalTextureLoc, ShaderUtils.getActiveTextureLoc(gNormal));

            int positionLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Position");
            if(positionLoc>=0){
                GL20.glUniform3f(positionLoc, lightPositions.get(i).x,2,lightPositions.get(i).z);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }


            int colorLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Color");
            if(colorLoc>=0){
                GL20.glUniform3f(colorLoc,lightColors.get(i));
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }



            // update attenuation parameters and calculate radius
            float constant = 1.0f; // note that we don't send this to the shader, we assume it is always 1.0 (in our case)
            float linear = 0.7f;
            float quadratic = 1.8f;

            int linearLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Linear");
            if(linearLoc>=0){
                GL20.glUniform1f(linearLoc, linear);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }

            int QuadraticLoc= glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(),"lights[" +i + "].Quadratic");
            if(QuadraticLoc>=0){
                GL20.glUniform1f(QuadraticLoc,quadratic);
            }else{
                LogUtil.err("positionLoc can't not be negative");
            }



        }*/

       /* int gPositionTextureLoc = glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(), "gPosition");
        if(gPositionTextureLoc>=0){
            glUniform1i(gPositionTextureLoc, ShaderUtils.getActiveTextureLoc(gPosition));
        }else{
            LogUtil.err("gPosition hasnot find ");
        }


        int gNormalTextureLoc = glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(), "gNormal");
        if(gNormalTextureLoc>=0){
            glUniform1i(gNormalTextureLoc, ShaderUtils.getActiveTextureLoc(gNormal));
        }else{
            LogUtil.err("gNormal hasnot find ");
        }

        int gAlbedoSpecTextureLoc = glGetUniformLocation(ShaderManager.shaderLightingPass.getProgramId(), "gAlbedoSpec");
        if(gAlbedoSpecTextureLoc>0){
            glUniform1i(gAlbedoSpecTextureLoc, ShaderUtils.getActiveTextureLoc(gColorSpec));
        }else{
            LogUtil.err("gAlbedoSpec hasnot find ");
        }
*/

       GL13.glActiveTexture(GL13.GL_TEXTURE12);
        glBindTexture(GL_TEXTURE_2D,gPosition );
        GL13. glActiveTexture(GL13.GL_TEXTURE13);
        glBindTexture(GL_TEXTURE_2D, gNormal);
        GL13. glActiveTexture(GL13.GL_TEXTURE14);
        glBindTexture(GL_TEXTURE_2D, gColorSpec);

        renderQuad();

    /*   glBindFramebuffer(GL_READ_FRAMEBUFFER, gBuffer);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0); // write to default framebuffer
        // blit to default framebuffer. Note that this may or may not work as the internal formats of both the FBO and default framebuffer have to match.
        // the internal formats are implementation defined. This works on all of my systems, but if it doesn't on yours you'll likely have to write to the
        // depth buffer in another shader stage (or somehow see to match the default framebuffer's internal format with the FBO's internal format).
        glBlitFramebuffer(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, GL_DEPTH_BUFFER_BIT, GL_NEAREST);
*/

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public  void  renderQuad(){

        //ShaderUtils.freshVao(ShaderManager.shaderLightingPass,ShaderManager.shaderLightingPass.getVao());
        ShaderUtils.finalDraw(ShaderManager.shaderLightingPass,ShaderManager.shaderLightingPass.getVao());

    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }



}
