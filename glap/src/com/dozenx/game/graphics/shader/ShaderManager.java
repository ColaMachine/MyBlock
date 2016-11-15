package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.log.LogUtil;
import com.dozenx.game.opengl.util.OpenglUtils;
import glmodel.GL_Matrix;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Util;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by luying on 16/11/14.
 */
public class ShaderManager {
    public void init(){
        this.CreateTerrainProgram();
       // this.CreateTerrainVAO();
        this.uniformTerrian();

        this.CreateLightProgram();
        this.CreateLightVAO();
        this.uniformLight();
    }


    public void CreateTerrainVAO1() {


       int  VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();
        this.CreateTerrainVBO123();

        glBindVertexArray(0);
        Util.checkGLError();
    }

    public void CreateTerrainVBO123() {
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float VerticesArray[] = {-0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,

                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,

                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,

                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f};
        FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);


        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        int VboId = glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);

        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();


    }

    GL_Matrix projection = GL_Matrix.perspective3(45, 600 / 600, 1f, 1000.0f);
    FloatBuffer cameraViewBuffer = BufferUtils.createFloatBuffer(16);

    public  int terrainProgramId;
    public  int VaoId;
    public  int VboId;
    public  int viewPosLoc;
    public  int LightProgramId;
    public  int viewLoc;//glGetUniformLocation(ProgramId,"view");
    public  int lightViewLoc;// glGetUniformLocation(LightProgramId,"view");
    public void lightPosChangeListener() {

        glUseProgram(terrainProgramId);

        glUniform3f(lightPosInTerrainLoc, GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        Util.checkGLError();

        glUseProgram(LightProgramId);

        GL_Matrix model = GL_Matrix.translateMatrix( GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        Util.checkGLError();


    }



    public void uniformTerrian() {
        /*
        uniform mat4 projection;
        uniform mat4 view;
        uniform mat4 model;
        uniform vec3 objectColor;物体的颜色
        uniform vec3 lightColor;灯光的颜色
        uniform vec3 lightPos;灯光的位置
        uniform vec3 viewPos;相机的位置*/


        //GL_Matrix view= GL_Matrix.translateMatrix(0,0,-3);


        //glUseProgram(ProgramId);
        glUseProgram(terrainProgramId);
        //unifrom赋值===========================================================
        //投影矩阵
        int projectionLoc = glGetUniformLocation(terrainProgramId, "projection");
        Util.checkGLError();
        glUniformMatrix4(projectionLoc, false, projection.toFloatBuffer());
        Util.checkGLError();

        //相机位置
        GL_Matrix view =
                GL_Matrix.LookAt( GamingState.instance.camera.Position,  GamingState.instance.camera.ViewDir);
        view.fillFloatBuffer(cameraViewBuffer);
        viewLoc = glGetUniformLocation(terrainProgramId, "view");

        Util.checkGLError();
        glUniformMatrix4(viewLoc, false, view.toFloatBuffer());
        Util.checkGLError();


        GL_Matrix model = GL_Matrix.rotateMatrix((float) (0 * 3.14 / 180.0), 0, 0);
        int modelLoc = glGetUniformLocation(terrainProgramId, "model");
        Util.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        Util.checkGLError();


        viewLoc = glGetUniformLocation(terrainProgramId, "view");
        Util.checkGLError();


        //物体颜色
        int objectColorLoc = glGetUniformLocation(terrainProgramId, "objectColor");
        glUniform3f(objectColorLoc, 1.0f, 0.5f, 0.31f);
        Util.checkGLError();

        //环境光颜色

       /*  lightColorLoc= glGetUniformLocation(ProgramId, "lightColor");
        glUniform3f(lightColorLoc,1.0f,1f,1f);
        Util.checkGLError();*/

        lightPosInTerrainLoc = glGetUniformLocation(terrainProgramId, "light.position");
        if (lightPosInTerrainLoc == -1) {
            LogUtil.println("light.position not found ");
            System.exit(1);
        }
        glUniform3f(lightPosInTerrainLoc,  GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        Util.checkGLError();

        viewPosLoc = glGetUniformLocation(terrainProgramId, "viewPos");
        if (viewPosLoc == -1) {
            LogUtil.println("viewPos not found ");
            System.exit(1);
        }
        glUniform3f(viewPosLoc,  GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        Util.checkGLError();


        //int matAmbientLoc = glGetUniformLocation(ProgramId, "material.ambient");
        //int matDiffuseLoc = glGetUniformLocation(ProgramId, "material.diffuse");
        int matSpecularLoc = glGetUniformLocation(terrainProgramId, "material.specular");
        int matShineLoc = glGetUniformLocation(terrainProgramId, "material.shininess");

        //glUniform3f(matAmbientLoc, 1.0f, 0.5f, 0.31f);
        //glUniform3f(matDiffuseLoc, 1.0f, 0.5f, 0.31f);
        glUniform3f(matSpecularLoc, 0.5f, 0.5f, 0.5f);
        glUniform1f(matShineLoc, 32.0f);

        int lightMatAmbientLoc = glGetUniformLocation(terrainProgramId, "light.ambient");
        int lightMatDiffuseLoc = glGetUniformLocation(terrainProgramId, "light.diffuse");
        int lightMatSpecularLoc = glGetUniformLocation(terrainProgramId, "light.specular");
        int lightMatShineLoc = glGetUniformLocation(terrainProgramId, "light.shininess");

        glUniform3f(lightMatAmbientLoc, 0.5f, 0.5f, 0.5f);
        glUniform3f(lightMatDiffuseLoc, 0.5f, 0.5f, 0.5f);
        glUniform3f(lightMatSpecularLoc, 0.5f, 0.5f, 0.5f);
        glUniform1f(lightMatShineLoc, 32.0f);

        glUniform1f(glGetUniformLocation(terrainProgramId, "light.constant"), 1.0f);
        glUniform1f(glGetUniformLocation(terrainProgramId, "light.linear"), 0.07f);
        glUniform1f(glGetUniformLocation(terrainProgramId, "light.quadratic"), 0.017f);

    }

    int lightColorLoc;
    int lightPosInTerrainLoc;
    int lightPosLoc;

    public int lightVaoId;

    public void CreateTerrainProgram() {
        try {
            terrainProgramId = OpenglUtils.CreateProgram("chapt16/box.vert", "chapt16/box.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void CreateLightProgram() {
        try {
            LightProgramId = OpenglUtils.CreateProgram("chapt13/light.vert", "chapt13/light.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void CreateLightVAO() {
        lightVaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(lightVaoId);
        Util.checkGLError();

        this.CreateLightVBO();
        glBindVertexArray(0);
        Util.checkGLError();

    }

    public void CreateLightVBO() {

        //创建vao2=========================================================
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects

        // model = GL_Matrix.multiply(view2,model);
        // VboId=glGenBuffers();//create vbo
        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        // glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();
    }

    int lightModelLoc;

    public void uniformLight() {
        GL_Matrix model = GL_Matrix.translateMatrix( GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        GL_Matrix view = GL_Matrix.translateMatrix(0, 0, 0);
        glUseProgram(LightProgramId);
        int lightProjectionLoc = glGetUniformLocation(LightProgramId, "projection");
        glUniformMatrix4(lightProjectionLoc, false, projection.toFloatBuffer());
        Util.checkGLError();
        lightModelLoc = glGetUniformLocation(LightProgramId, "model");
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        Util.checkGLError();

        lightViewLoc = glGetUniformLocation(LightProgramId, "view");
        glUniformMatrix4(lightViewLoc, false, view.toFloatBuffer());
        Util.checkGLError();

    }


    public void cameraPosChangeListener() {
        LogUtil.println("please use camera changeCallBack");
        GL_Matrix view =
                GL_Matrix.LookAt( GamingState.instance.camera.Position,  GamingState.instance.camera.ViewDir);
        view.fillFloatBuffer(cameraViewBuffer);

        glUseProgram(terrainProgramId);


        glUniformMatrix4(viewLoc, false, cameraViewBuffer);
        Util.checkGLError();
        //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(viewPosLoc,  GamingState.instance.camera.Position.x,  GamingState.instance.camera.Position.y,  GamingState.instance.camera.Position.z);
        Util.checkGLError();


        glUseProgram(LightProgramId);
        Util.checkGLError();
        //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(lightViewLoc, false, view.toFloatBuffer());

        Util.checkGLError();

    }

    public void update(){

    }
    public void render(){

    }
}
