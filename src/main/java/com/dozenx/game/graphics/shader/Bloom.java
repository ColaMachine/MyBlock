package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * Created by luying on 17/4/17.
 */
public class Bloom {
   // ShaderConfig config ;
   public int horizontalLoc;
    public int pingpongFBO[]=new int[2];
    public int pingpongBuffer[]=new int[2];
   public  int colorBuffers[]=new int[2];//用于存储两个颜色缓冲帧
    private void init(){
        this.createLightFBO();
        this.createGaosiFBO();


    }
    public Bloom(){
        this.init();
    }
    /**
     * 用来存储原始图 和 高亮区域的图
     * 这里只用到了1个buffer 但是 会输出两张texture
     */
    public int lightFBO;

    public void createLightFBO123(){//测试用 因为colorbuffer[0]始终缓存不到当前图像 我试图用他来模仿hdr 来存储图片 奇怪了 发现取消掉 bloom.initVaoAndBindTexture(this); 就可以存储到图片
//        int hdrFBO
//        = glGenFramebuffers();
//        glBindFramebuffer(GL_FRAMEBUFFER, hdrFBO);
        lightFBO = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, lightFBO);
        colorBuffers[0]= glGenTextures();
        //colorBuffers[1]= glGenTextures();
//        for (int i = 0; i < 2; i++)
//        {
            glBindTexture(GL_TEXTURE_2D, colorBuffers[0]);
            glTexImage2D(
                    GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer)null
            );
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            // 帧缓冲连接上纹理
            glFramebufferTexture2D(
                    GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 , GL_TEXTURE_2D, colorBuffers[0], 0
            );
//        }

//        int attachments[]={GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1};
//        bloomAttachments = BufferUtils.createIntBuffer(2);//
//        bloomAttachments.put(GL_COLOR_ATTACHMENT0).put(GL_COLOR_ATTACHMENT1);
//        bloomAttachments.rewind();
//
//        GL20.glDrawBuffers( bloomAttachments);OpenglUtils.checkGLError();

        //glDrawBuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        OpenglUtils.checkGLError();
    }

    public void createLightFBO(){
//        int hdrFBO
//        = glGenFramebuffers();
//        glBindFramebuffer(GL_FRAMEBUFFER, hdrFBO);
         lightFBO = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, lightFBO);
        colorBuffers[0]= glGenTextures();
        colorBuffers[1]= glGenTextures();
        for (int i = 0; i < 2; i++)
        {
            glBindTexture(GL_TEXTURE_2D, colorBuffers[i]);
            glTexImage2D(
                    GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer)null
            );
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            // 帧缓冲连接上纹理
            glFramebufferTexture2D(
                    GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, colorBuffers[i], 0
            );
        }

        int attachments[]={GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1};
         bloomAttachments = BufferUtils.createIntBuffer(2);//
        bloomAttachments.put(GL_COLOR_ATTACHMENT0).put(GL_COLOR_ATTACHMENT1);
        bloomAttachments.rewind();

        GL20.glDrawBuffers( bloomAttachments);OpenglUtils.checkGLError();

        //glDrawBuffers();
        OpenglUtils.checkGLError();
    }
    IntBuffer bloomAttachments;
    /**
     * 用来暂存高斯模糊的临时
     * 这里用到了两张帧缓存 互相渲染
     */
    public void createGaosiFBO(){

        pingpongFBO[0]= glGenFramebuffers();
        pingpongFBO[1]= glGenFramebuffers();
        pingpongBuffer[0]= glGenTextures();
        pingpongBuffer[1]= glGenTextures();
        for (int i = 0; i < 2; i++)
        {
            glBindFramebuffer(GL_FRAMEBUFFER, pingpongFBO[i]);
            //GL13.glActiveTexture(GL13.GL_TEXTURE2);
            glBindTexture(GL_TEXTURE_2D, pingpongBuffer[i]);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT,(ByteBuffer) null);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
           glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
           glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            glFramebufferTexture2D(
                    GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, pingpongBuffer[i], 0
            );
        }
        OpenglUtils.checkGLError();
    }
    //初次就绑定创建vao 和 绑定纹理
    public void initVaoAndBindTexture(ShaderManager shaderManager){ //这个程序出了些问题 导致colorBUffer[0]存不住图像
        //这里肯定出了什么问题 我想让hdr的图像 进入bloom
        //创建 亮纹理 和绑定提取两纹理的shader vao
        //ShaderUtils.bindDepth();
        //glUseProgram(0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        ShaderUtils.initVao(shaderManager.bloomShaderConfig,shaderManager.bloomShaderConfig.getVao());

        ShaderUtils.draw2dImg(shaderManager.bloomShaderConfig,shaderManager.bloomShaderConfig.getVao(),shaderManager.hdr.getTextureId());
        ShaderUtils.freshVao(shaderManager.bloomShaderConfig,shaderManager.bloomShaderConfig.getVao());

        OpenglUtils.checkGLError();
        //高斯vao 和 绑定高亮纹理
        ShaderUtils.initVao(shaderManager.gaosiShaderConfig,shaderManager.gaosiShaderConfig.getVao());

        ShaderUtils.draw2dImg(shaderManager.gaosiShaderConfig,shaderManager.gaosiShaderConfig.getVao(),colorBuffers[0]);
        ShaderUtils.freshVao(shaderManager.gaosiShaderConfig,shaderManager.gaosiShaderConfig.getVao());


        //ShaderUtils.bindAndGetTextureIndex();
        //glUniform1("gaosi中纹理的位置",colorBuffers[1]或者pingpongBuffer[0]或者pingPongBUffer[1]);



         OpenglUtils.checkGLError();
        glUseProgram(shaderManager.gaosihebingShaderConfig.getProgramId());

        glUniform1i(shaderManager.gaosihebingShaderConfig.getTexture0Loc(),ShaderUtils.getActiveTextureLoc(colorBuffers[0]));
        glUniform1i(shaderManager.gaosihebingShaderConfig.getTexture1Loc(),ShaderUtils.getActiveTextureLoc(pingpongBuffer[1]));
        ShaderUtils.initVao(shaderManager.gaosihebingShaderConfig,shaderManager.gaosihebingShaderConfig.getVao());

        ShaderUtils.draw2dImg(shaderManager.gaosihebingShaderConfig,shaderManager.gaosihebingShaderConfig.getVao(),shaderManager.hdr.getTextureId());
        ShaderUtils.freshVao(shaderManager.gaosihebingShaderConfig,shaderManager.gaosihebingShaderConfig.getVao());



    }
    Vao horizontalVao = new Vao(ShaderManager.bloomShaderConfig);
    public void getBrightTexture(ShaderManager shaderManager){
        //现在出现了这么一个问题
        //shaderManager.bloomShaderConfig.getVao().getVertices().rewind();
        //ShaderUtils.draw2dImg(shaderManager.bloomShaderConfig,shaderManager.bloomShaderConfig.getVao(),shaderManager.hdrTextureHandler);
        //ShaderUtils.createVao(shaderManager.bloomShaderConfig,shaderManager.bloomShaderConfig.getVao(),new int[]{2,2});
        glBindFramebuffer(GL_FRAMEBUFFER, shaderManager.bloom.lightFBO);
       // bloomAttachments.rewind();
       // GL20.glDrawBuffers( bloomAttachments);
        glClearColor(0.1f, 0.1f,0.1f, 0.1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // 我们现在不用模板缓冲

        ShaderUtils.finalDraw(shaderManager.bloomShaderConfig,shaderManager.bloomShaderConfig.getVao());
        OpenglUtils.checkGLError();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
    /**
     * 高斯模糊 10次
     * @param shaderManager
     */
    public void renderGaosi(ShaderManager shaderManager){//这里出现了一个问题 渲染出来的效果不是我想要的效果
        boolean horizontal = true, first_iteration = true;
        int amount = 10;
        //使用高斯模糊shader 进行兵乓球式的渲染
       glUseProgram( shaderManager.gaosiShaderConfig.getProgramId());

        //GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //glUniform1i(glGetUniformLocation(shaderManager.gaosiShaderConfig.getProgramId(), "ourTexture0"), 0);
        OpenglUtils.checkGLError();
        //获得是否水平参数位置
        int hirizonTalLoc = glGetUniformLocation(shaderManager.gaosiShaderConfig.getProgramId(), "horizontal");
        //10次乒乓球式横竖模糊
        for (int i = 0; i < amount; i++)
        {
            glBindFramebuffer(GL_FRAMEBUFFER, pingpongFBO[horizontal?1:0]);OpenglUtils.checkGLError();
            //1 为true 0 为false

            glUniform1i(hirizonTalLoc, horizontal?1:0);OpenglUtils.checkGLError();//为什么这里第二次赋值就会报错 1282错误
            //glUniform1i(hirizonTalLoc, 1);OpenglUtils.checkGLError();
            //先是colorbuffer再是1 再是 0
            //glUseProgram(shaderManager.gaosiShaderConfig.getProgramId());
            glUseProgram( shaderManager.gaosiShaderConfig.getProgramId());
            glUniform1i(shaderManager.gaosiShaderConfig.getTexture0Loc(),first_iteration ?
                    ShaderUtils.getActiveTextureLoc(
                            colorBuffers[1]
                            //TextureManager.getTextureInfo("items").textureHandle
                    ) :
                    ShaderUtils.getActiveTextureLoc(pingpongBuffer[horizontal?0:1]));
          /*  glBindTexture(
                    GL_TEXTURE_2D, first_iteration ? colorBuffers[1] : pingpongBuffer[horizontal?0:1]
            );*/OpenglUtils.checkGLError();

            shaderManager.gaosiShaderConfig.getVao().getVertices().rewind();OpenglUtils.checkGLError();
            //这里需要注意 需要提前绑定纹理 并且创建vao 需要在shader 创建结束后干这件事
            ShaderUtils.finalDraw(shaderManager.gaosiShaderConfig,shaderManager.gaosiShaderConfig.getVao());OpenglUtils.checkGLError();
            horizontal = !horizontal;
            if (first_iteration)
                first_iteration = false;
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        OpenglUtils.checkGLError();
    }
}
