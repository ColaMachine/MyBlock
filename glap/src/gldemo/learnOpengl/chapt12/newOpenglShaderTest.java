package gldemo.learnOpengl.chapt12;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import glapp.GLApp;
import glapp.GLImage;
import gldemo.learnOpengl.FatherLeanr;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import javax.vecmath.Vector3f;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * 利用cup 换算出透视投影矩阵 再换算出变换后的ndc坐标  带入shader进行渲染
 * Created by dozen.zhang on 2016/10/11.
 */
public class newOpenglShaderTest extends  FatherLeanr{


    ShaderConfig cubeConfig;
    ShaderConfig config2;
    ShaderConfig squareConfig;

    Vao cubeVao ;
    Vao squareVao;
    public void initGL() throws IOException {
        // GL11.glEnable(GL11.GL_DEPTH_TEST);
     /*   glClearColor(0, 0, 0, 0);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);*/
        String vertPath="chapt12/box.vert";
        String fragPath="chapt12/box.frag";

        cubeConfig =new ShaderConfig();
        cubeConfig.setPosition(new Vector3f(0,0,-3));
        cubeConfig.setVertPath(vertPath);
        cubeConfig.setFragPath(fragPath);
        ShaderUtils.initShader(cubeConfig);

        config2 =new ShaderConfig("test",fragPath,vertPath);
        config2.setPosition(new Vector3f(0,3,0));
        config2.setVertPath(vertPath);
        config2.setFragPath(fragPath);
        ShaderUtils.initShader(config2);
        ShaderUtils.initObjectColor(cubeConfig);
        ShaderUtils.initLightColor(cubeConfig);

        squareConfig = new ShaderConfig();
//        imageConfig.setMinX(-0.5f);
//        imageConfig.setMaxX(0.5f);
//        imageConfig.setMinY(-0.5f);
//        imageConfig.setMaxY(0.5f);
        GLImage image;
        image= OpenglUtils.makeTexture("assets/images/items.png");
        glBindTexture(GL_TEXTURE_2D, image.textureHandle);
       // squareConfig.setTextureHanle(image.textureHandle);
        squareConfig.setVertPath("chapt7/chapt7.vert");
        squareConfig.setFragPath("chapt7/chapt7.frag");
        ShaderUtils.initShader(squareConfig);

        cubeVao =new Vao();
        ShaderUtils.createCubeVao(cubeVao);

        squareVao =new Vao();
        ShaderUtils.create2dimageVao(squareVao,-0.5f,-0.5f,0.5f,0.5f);

    }

    public void render() throws LWJGLException {


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        Long time =System.currentTimeMillis();

       // glUseProgram(config.getProgramId());
        Util.checkGLError();
       // glUniform3();
        //glBindVertexArray(config.getVaoId());
        Util.checkGLError();


       // glUniformMatrix4(config.getModelLoc(), false, config.getModel().toFloatBuffer());
       // glDrawArrays(GL_TRIANGLES,0,config.getPoints());


        //glUniformMatrix4(config.getModelLoc(), false, config.getModel2().toFloatBuffer());
       // glDrawArrays(GL_TRIANGLES,config.getPoints()/2,config.getPoints());

//        glBindVertexArray(config2.getVaoId());
//        Util.checkGLError();
//        glDrawArrays(GL_TRIANGLES,0,config.getPoints());
//
//        Util.checkGLError();
//        glBindVertexArray(0);
//        Util.checkGLError();



//
        ShaderUtils.drawCubeWithShader(cubeConfig,this.cubeVao);

        ShaderUtils.draw2DImageWithShader(squareConfig,this.squareVao);
//        OpenglUtils.drawCubeWithShader(config2);
    }
    public void cameraPosChangeListener(GL_Vector cameraPos){
        GL_Matrix view=
                GL_Matrix.LookAt(cameraPos,viewDir);
        glUseProgram(cubeConfig.getProgramId());
        glUniformMatrix4(cubeConfig.getViewLoc(),  false,view.toFloatBuffer() );
        glUseProgram(config2.getProgramId());
        glUniformMatrix4(config2.getViewLoc(),  false,view.toFloatBuffer() );
    }

    public static void main(String[] args) {
        LWJGLHelper.initNativeLibs();//加载lib包
        newOpenglShaderTest main = null;
        try {
            main = new newOpenglShaderTest();
            main.create();
            main.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (main != null) {
                main.destroy();
            }
        }
    }



}
