package gldemo.learnOpengl.chapt12;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import de.matthiasmann.twl.GUI;
import glapp.GLApp;
import glapp.GLImage;
import gldemo.learnOpengl.FatherLeanr;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * 利用cup 换算出透视投影矩阵 再换算出变换后的ndc坐标  带入shader进行渲染
 * Created by dozen.zhang on 2016/10/11.
 */
public class LearnOpenglColor extends FatherLeanr{
    public String vertPath="chapt12/box.vert";
    public String fragPath="chapt12/box.frag";



    public String getVertPath(){
        return vertPath;
    }
    public String getFragPath(){
        return fragPath;
    }

    int vao2Id;

    int lightVertexShaderId;
    int lightFragmentShaderId;
    public void CreateLightVertexShader()throws Exception {


        //顶点着色器
        //编译顶点着色器

        //GLSL opengl shader language


        // Put the shaders "chap2.vert" and "chap2.frag" in the working
        // directory ...

        //==========================================================
        // Load and compile vertex shader

        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+this.getVertPath()).toString());
        //创建着色器
        lightVertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        Util.checkGLError();
        //源码
        glShaderSource(VertexShaderId, VertexShader);
        Util.checkGLError();
        //编译
        glCompileShader(VertexShaderId);
        Util.checkGLError();
        //打印日志

        // Print possible compile errors
        //System.out.println("Vertex shader compilation:");
        printShaderLog(VertexShaderId);
        Util.checkGLError();
    }
    void CreateLightFragShaders() throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+this.getFragPath()).toString());

        lightFragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        Util.checkGLError();

        glShaderSource(FragmentShaderId, FragmentShader);
        Util.checkGLError();

        glCompileShader(FragmentShaderId);
        Util.checkGLError();

        // Print possible compile errors
        System.out.println("Fragment shader compilation:");
        printShaderLog(FragmentShaderId);


    }
    public void CreateVBO(){



        VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float VerticesArray[]= {

                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
        };


        GL_Matrix model= GL_Matrix.rotateMatrix((float)(45*3.14/180.0),0,0);

        GL_Matrix view= GL_Matrix.translateMatrix(0,0,-3);
        GL_Matrix projection= GL_Matrix.perspective3(45,600/600,1f,1000.0f);
        Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data



        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);




        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

        //glUseProgram(ProgramId);
        glUseProgram(ProgramId);
        //unifrom赋值===========================================================
        int projectionLoc= glGetUniformLocation(ProgramId, "projection");
        Util.checkGLError();
        int modelLoc= glGetUniformLocation(ProgramId, "model");
        Util.checkGLError();
        int viewLoc= glGetUniformLocation(ProgramId, "view");
        Util.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        Util.checkGLError();
        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
        Util.checkGLError();
        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );
        Util.checkGLError();
        //物体颜色
        int objectColorLoc= glGetUniformLocation(ProgramId, "objectColor");
        //float[] objectColorAry = new float[]{1,0.5f,0.31f,};
       // FloatBuffer objectColorBbuffer = BufferUtils.createFloatBuffer(3);
       // objectColorBbuffer.put(objectColorAry);objectColorBbuffer.rewind();
        glUniform3f(objectColorLoc,1.0f,0.5f,0.31f);
       // glUniformMatrix4(objectColorLoc, false, objectColorBbuffer);
        Util.checkGLError();
        //环境光颜色

        int lightColorLoc= glGetUniformLocation(ProgramId, "lightColor");
//        float[] lightColorAry = new float[]{1,1f,1f,};
//        FloatBuffer lightColorBbuffer = BufferUtils.createFloatBuffer(3);
//        lightColorBbuffer.put(lightColorAry);lightColorBbuffer.rewind();
//        glUniformMatrix4(lightColorLoc,  false,lightColorBbuffer );
        glUniform3f(lightColorLoc,1.0f,1f,1f);
        Util.checkGLError();


        glBindVertexArray(0);
        Util.checkGLError();
         //创建vao2=========================================================

        vao2Id = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(vao2Id);
        Util.checkGLError();

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        GL_Matrix view2= GL_Matrix.translateMatrix(3,0,-4);
          model = GL_Matrix.multiply(view2,model);
       // VboId=glGenBuffers();//create vbo




        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo


        //glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data



        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);




        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();
try {
    LightProgramId = this.CreateProgram("chapt12/light.vert", "chapt12/light.frag");
}catch(Exception e){
    e.printStackTrace();
    System.exit(0);
}
        glUseProgram(LightProgramId);
         projectionLoc= glGetUniformLocation(LightProgramId, "projection");
         modelLoc= glGetUniformLocation(LightProgramId, "model");
         viewLoc= glGetUniformLocation(LightProgramId, "view");




        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );

        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );


    }

    int LightProgramId;
    public void render() throws LWJGLException {


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        Long time =System.currentTimeMillis();
        float greenValue = (float)(Math.sin(time.doubleValue())/2+0.5);

        glUseProgram(this.ProgramId);
        Util.checkGLError();






       /* int transformLoc= glGetUniformLocation(ProgramId,"transform");
        glUniformMatrix4(0,  false,matrixBuffer );
        matrixBuffer.rewind();*/
        //glBindTexture(GL_TEXTURE_2D, this.textureHandle);
        glBindVertexArray(VaoId);
        Util.checkGLError();
//        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glDrawArrays(GL_TRIANGLES,0,36);
        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

        glUseProgram(this.LightProgramId);
        Util.checkGLError();

       /* int transformLoc= glGetUniformLocation(ProgramId,"transform");
        glUniformMatrix4(0,  false,matrixBuffer );
        matrixBuffer.rewind();*/
       // glBindTexture(GL_TEXTURE_2D, this.textureHandle);
        glBindVertexArray(vao2Id);
//        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glDrawArrays(GL_TRIANGLES,0,36);
        glBindVertexArray(0);
    }
    public void cameraPosChangeListener(GL_Vector cameraPos){
        GL_Matrix view=
                GL_Matrix.LookAt(cameraPos,viewDir);


         glUseProgram(ProgramId);
        int viewLoc= glGetUniformLocation(ProgramId,"view");


        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
        glUseProgram(LightProgramId);
         viewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
    }

    public static void main(String[] args) {
        LWJGLHelper.initNativeLibs();//加载lib包
        LearnOpenglColor main = null;
        try {
            main = new LearnOpenglColor();
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
