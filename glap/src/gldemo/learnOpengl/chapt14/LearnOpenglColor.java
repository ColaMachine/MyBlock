package gldemo.learnOpengl.chapt14;

import cola.machine.game.myblocks.ui.login.LoginDemo;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import gldemo.learnOpengl.FatherLeanr;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Util;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * 利用cup 换算出透视投影矩阵 再换算出变换后的ndc坐标  带入shader进行渲染
 * Created by dozen.zhang on 2016/10/11.
 */
public class LearnOpenglColor extends FatherLeanr{
    public String vertPath="chapt13/box.vert";
    public String fragPath="chapt13/box.frag";



    public String getVertPath(){
        return vertPath;
    }
    public String getFragPath(){
        return fragPath;
    }

    int vao2Id;

    int lightVertexShaderId;
    int lightFragmentShaderId;
    GUI gui;
    GL_Vector lightPos=new GL_Vector(5,0,2);
    public void CreateVBO(){

        LoginDemo demo = new LoginDemo();

        LWJGLRenderer renderer = null;
        try {
            renderer = new LWJGLRenderer();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
         gui = new GUI(demo, renderer);

       // demo.efName.requestKeyboardFocus();



        VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float VerticesArray[]= { -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,

                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
                -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f};


        GL_Matrix model= GL_Matrix.rotateMatrix((float)(45*3.14/180.0),0,0);

        //GL_Matrix view= GL_Matrix.translateMatrix(0,0,-3);


        GL_Matrix view=
                GL_Matrix.LookAt(cameraPos,viewDir);
        view.fillFloatBuffer(cameraViewBuffer);


        GL_Matrix projection= GL_Matrix.perspective3(45,600/600,1f,1000.0f);
        Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data



        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3*4);

        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();



        glBindVertexArray(0);
        Util.checkGLError();

        //glUseProgram(ProgramId);
        glUseProgram(ProgramId);
        //unifrom赋值===========================================================
        //投影矩阵
        int projectionLoc= glGetUniformLocation(ProgramId, "projection");
        Util.checkGLError();
        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );
        Util.checkGLError();

        //相机位置
        viewLoc = glGetUniformLocation(ProgramId,"view");
        viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");
        Util.checkGLError();
        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
        Util.checkGLError();

        int modelLoc= glGetUniformLocation(ProgramId, "model");
        Util.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        Util.checkGLError();


        viewLoc= glGetUniformLocation(ProgramId, "view");
        Util.checkGLError();



        //物体颜色
        int objectColorLoc= glGetUniformLocation(ProgramId, "objectColor");
        glUniform3f(objectColorLoc,1.0f,0.5f,0.31f);
        Util.checkGLError();
        //环境光颜色

        int lightColorLoc= glGetUniformLocation(ProgramId, "lightColor");
        glUniform3f(lightColorLoc,1.0f,1f,1f);
        Util.checkGLError();

        int lightPosLoc= glGetUniformLocation(ProgramId, "lightPos");
        glUniform3f(lightPosLoc,lightPos.x,lightPos.y,lightPos.z);
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
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);





        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

        try {
            LightProgramId = this.CreateProgram("chapt13/light.vert", "chapt13/light.frag");
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        glUseProgram(LightProgramId);
         projectionLoc= glGetUniformLocation(LightProgramId, "projection");
        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );
        Util.checkGLError();

         modelLoc= glGetUniformLocation(LightProgramId, "model");
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        Util.checkGLError();

         lightViewLoc= glGetUniformLocation(LightProgramId, "view");
            glUniformMatrix4(lightViewLoc,  false,view.toFloatBuffer() );
        Util.checkGLError();









    }
    public void intLocation(){

    }
    public void uniform(){

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
        //gui.update();
    }
    int viewLoc;//glGetUniformLocation(ProgramId,"view");
    int lightViewLoc;// glGetUniformLocation(LightProgramId,"view");
    FloatBuffer cameraViewBuffer = BufferUtils.createFloatBuffer(16);
    public void cameraPosChangeListener(GL_Vector cameraPos){
        GL_Matrix view=
                GL_Matrix.LookAt(cameraPos,viewDir);
        view.fillFloatBuffer(cameraViewBuffer);

         glUseProgram(ProgramId);



        glUniformMatrix4(viewLoc,  false,cameraViewBuffer);
        Util.checkGLError();
         //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(viewPosLoc,  cameraPos.x,cameraPos.y,cameraPos.z);
        Util.checkGLError();


        glUseProgram(LightProgramId);
        Util.checkGLError();
          //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(lightViewLoc,  false,view.toFloatBuffer() );

        Util.checkGLError();

    }
    int viewPosLoc;

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
