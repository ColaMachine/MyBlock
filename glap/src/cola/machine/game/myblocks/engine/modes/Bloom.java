package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * Created by luying on 17/4/17.
 */
public class Bloom {
    ShaderConfig config ;
    int horizontalLoc;
    int pingpongFBO[]=new int[2];
    int pingpongBuffer[]=new int[2];
    int colorBuffers[]=new int[2];//用于存储两个颜色缓冲帧
    public void init(){
        this.createLightFBO();
        this.createGaosiFBO();
    }
    /**
     * 用来存储原始图 和 高亮区域的图
     */
    public void createLightFBO(){
        int hdrFBO
        = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, hdrFBO);

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
        OpenglUtils.checkGLError();
    }

    /**
     * 用来暂存高斯模糊的临时
     */
    public void createGaosiFBO(){

        pingpongFBO[0]= glGenFramebuffers();
        pingpongFBO[1]= glGenFramebuffers();
        pingpongBuffer[0]= glGenTextures();
        pingpongBuffer[1]= glGenTextures();
        for (int i = 0; i < 2; i++)
        {
            glBindFramebuffer(GL_FRAMEBUFFER, pingpongFBO[i]);
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

    /**
     * 高斯模糊 10次
     * @param shaderManager
     */
    public void render(ShaderManager shaderManager){
        boolean horizontal = true, first_iteration = true;
        int amount = 10;

        config.use();
        for (int i = 0; i < amount; i++)
        {
            glBindFramebuffer(GL_FRAMEBUFFER, pingpongFBO[horizontal?1:0]);
            glUniform1i(glGetUniformLocation(config.getProgramId(), "horizontal"), horizontal?1:0);
            glBindTexture(
                    GL_TEXTURE_2D, first_iteration ? colorBuffers[1] : pingpongBuffer[horizontal?0:1]
            );
            //RenderQuad();
            horizontal = !horizontal;
            if (first_iteration)
                first_iteration = false;
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        OpenglUtils.checkGLError();
    }
}
