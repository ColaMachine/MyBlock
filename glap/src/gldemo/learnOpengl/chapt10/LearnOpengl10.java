package gldemo.learnOpengl.chapt10;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import glapp.GLApp;
import glapp.GLImage;
import gldemo.learnOpengl.FatherLeanr;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
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
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * 利用cup 换算出透视投影矩阵 再换算出变换后的ndc坐标  带入shader进行渲染
 * Created by dozen.zhang on 2016/10/11.
 */
public class LearnOpengl10 extends FatherLeanr{


    public void CreateVBO(){
        VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float z=0f;
        float multi=1;
        float VerticesArray[]= {0.5f*multi, 0.5f*multi,z, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top Right
                0.5f*multi, -0.5f*multi, z, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // Bottom Right
                -0.5f*multi, -0.5f*multi,z, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom Left
                -0.5f*multi, 0.5f*multi, z, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top Left
        };
        GL_Matrix model= GL_Matrix.rotateMatrix((float)(45*3.14/180.0),0,0);

        GL_Matrix view=
        GL_Matrix.LookAt(new GL_Vector(0, 0, 3), new GL_Vector(0, 0f, -1));
        GL_Matrix view2=GL_Matrix.translateMatrix(0,0,-3);
        GL_Matrix projection= GL_Matrix.perspective3(45,600/600,1f,1000.0f);




        Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        int[] indices={
                0,1,3,
                1,2,3
        };
        IntBuffer Indices = BufferUtils.createIntBuffer(indices.length);
        Indices.put(indices);
        Indices.rewind();

        eboId = glGenBuffers();
        Util.checkGLError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Indices,GL_STATIC_DRAW);

       // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8* 4, 3*4);

        Util.checkGLError();
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8* 4, 6*4);

        Util.checkGLError();
        glEnableVertexAttribArray(2);

        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

        glUseProgram(ProgramId);


        int projectionLoc= glGetUniformLocation(ProgramId,"projection");
        int modelLoc= glGetUniformLocation(ProgramId,"model");
        int viewLoc= glGetUniformLocation(ProgramId,"view");

        glUniformMatrix4(modelLoc,  false,model.toFloatBuffer() );
       glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );

     glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );


    }




    public void render() throws LWJGLException {


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        Long time =System.currentTimeMillis();
        float greenValue = (float)(Math.sin(time.doubleValue())/2+0.5);

        glUseProgram(this.ProgramId);

//        int projectionLoc= glGetUniformLocation(ProgramId,"projection");
//        int modelLoc= glGetUniformLocation(ProgramId,"model");
//        int viewLoc= glGetUniformLocation(ProgramId,"view");
        // projectionLoc=0;
        //modelLoc=1;
        // viewLoc=2;
       // glUniformMatrix4(modelLoc,  false,new GL_Matrix().toFloatBuffer() );
        //glUniformMatrix4(modelLoc,  false,GL_Matrix.rotateMatrix((float)(45.0*3.14/180.0),0,0).toFloatBuffer() );
        // glUniformMatrix4(modelLoc,  false,GL_Matrix.multiply(GL_Matrix.translateMatrix(0.0f,0f,-100),GL_Matrix.rotateMatrix((float)(45*3.14/180),0,0)).toFloatBuffer() );
        //glUniformMatrix4(viewLoc,  false,new GL_Matrix().toFloatBuffer() );
        //glUniformMatrix4(viewLoc,  false,GL_Matrix.translateMatrix(0.0f,0f,-3f).toFloatBuffer() );
        //glUniformMatrix4(projectionLoc,  false,GL_Matrix.perspective2(45.0f,600/600,-0.1f,-100.0f).toReverseFloatBuffer() );

       // glUniformMatrix4(projectionLoc,  false,GL_Matrix.perspective3(90.0f,600/600,0.1f,100.0f).toFloatBuffer() );

       /* int transformLoc= glGetUniformLocation(ProgramId,"transform");
        glUniformMatrix4(0,  false,matrixBuffer );
        matrixBuffer.rewind();*/
        glBindTexture(GL_TEXTURE_2D, this.textureHandle);
        glBindVertexArray(VaoId);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

        Util.checkGLError();
    }


    public static void main(String[] args) {
        LWJGLHelper.initNativeLibs();//加载lib包
        LearnOpengl10 main = null;
        try {
            main = new LearnOpengl10();
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
