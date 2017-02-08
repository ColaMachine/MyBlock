package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.StringUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Util;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by luying on 16/11/14.
 */
public class ShaderManager {


    //Vao terrainVao = new Vao();

    /*public  int terrainProgramId;
    public  int VaoId;
    public  int VboId;
    public  int viewPosLoc;
    public  int LightProgramId;
    public  int viewLoc;//glGetUniformLocation(ProgramId,"view");
    public  int lightViewLoc;// glGetUniformLocation(LightProgramId,"view");
    */

    /*int lightColorLoc;
   int lightPosInTerrainLoc;
   int lightPosLoc;*/


    public static ShaderConfig terrainShaderConfig = new ShaderConfig("terrain","chapt16/box.frag","chapt16/box.vert");
    public static ShaderConfig lightShaderConfig = new ShaderConfig("light","chapt13/light.frag","chapt13/light.vert");

    public static ShaderConfig skyShaderConfig = new ShaderConfig("light","chapt13/light.frag","chapt13/light.vert");

    public static ShaderConfig uiShaderConfig = new ShaderConfig("ui","chapt7/2dimg.frag","chapt7/2dimg.vert");
    public static ShaderConfig livingThingShaderConfig = new ShaderConfig("living","chapt7/3dimg.frag","chapt7/3dimg.vert");
    public static ShaderConfig anotherShaderConfig = new ShaderConfig("another","chapt7/3dimg.frag","chapt7/3dimg.vert");


    public HashMap<String,ShaderConfig> configMap =new HashMap<>();
    public void registerConfig(ShaderConfig config) throws Exception {
        if(StringUtil.isBlank(config.getName())){
            throw new Exception("not allow shader config has no name!");
        }
        configMap.put(config.getName(),config);

    }
    public void init(){
        //Vao terrainVao = new Vao();
        this.createProgram(terrainShaderConfig);
        //terrainShaderConfig.getVao().setVertices(BufferUtils.createFloatBuffer(902400));
        this.createProgram(lightShaderConfig);
        this.createProgram(skyShaderConfig);
        this.createProgram(uiShaderConfig);
        this.createProgram(livingThingShaderConfig);
        this.createProgram(anotherShaderConfig);

       // this.CreateTerrainVAO();
       // ShaderUtils.initProModelView(terrainShaderConfig);
        //ShaderUtils.initProModelView(lightShaderConfig);
        //ShaderUtils.initProModelView(livingThingShaderConfig);

        this.initUniform(terrainShaderConfig);
        this.initUniform(lightShaderConfig);
        this.initUniform(livingThingShaderConfig);
        this.initUniform(uiShaderConfig);
        this.initUniform(skyShaderConfig);
        this.initUniform(anotherShaderConfig);
        //this.createProgram(lightShaderConfig);
        this.CreateLightVAO(lightShaderConfig);
        this.CreateUiVAO(uiShaderConfig);
        //this.CreateTerrainVAO(terrainShaderConfig);
      //  this.CreateLivingVAO(livingThingShaderConfig);
        //this.uniformLight();
    }


    public void CreateTerrainVAO1() {


       int  VaoId = glGenVertexArrays();
        OpenglUtils.checkGLError();

        glBindVertexArray(VaoId);
        OpenglUtils.checkGLError();
        this.CreateTerrainVBO123();

        glBindVertexArray(0);
        OpenglUtils.checkGLError();
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

        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);

        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(1);
        OpenglUtils.checkGLError();


    }

    GL_Matrix projection = GL_Matrix.perspective3(45, Constants.WINDOW_WIDTH / Constants.WINDOW_HEIGHT, 1f, 1000.0f);
    FloatBuffer cameraViewBuffer = BufferUtils.createFloatBuffer(16);

    public static void humanPosChangeListener(){


        GamingState.instance.lightPos.x= GamingState.instance.human.position.x;
        GamingState.instance.lightPos.y= GamingState.instance.human.position.y+30;
        GamingState.instance.lightPos.z= GamingState.instance.human.position.z;

        glUseProgram(lightShaderConfig.getProgramId());

        GL_Matrix model = GL_Matrix.translateMatrix( GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        glUniformMatrix4(lightShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();


        glUseProgram(skyShaderConfig.getProgramId());


        glUniformMatrix4(skyShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();
        lightPosChangeListener();

    }
    public static void lightPosChangeListener() {

        glUseProgram(terrainShaderConfig.getProgramId());

        glUniform3f(terrainShaderConfig.getLightPosLoc(), GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        OpenglUtils.checkGLError();

        glUseProgram(lightShaderConfig.getProgramId());

        GL_Matrix model = GL_Matrix.translateMatrix( GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        glUniformMatrix4(lightShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();


       // glUseProgram(livingThingShaderConfig.getProgramId());

       // glUniform3f(livingThingShaderConfig.getLightPosLoc(), GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        //OpenglUtils.checkGLError();
        /*
        glUseProgram(livingThingShaderConfig.getProgramId());


        glUniformMatrix4(livingThingShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();*/

        glUseProgram(0);
    }



    public void initUniform(ShaderConfig config ) {
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
        int programId =config.getProgramId();

        glUseProgram(config.getProgramId());
        //unifrom赋值===========================================================
        //投影矩阵
        int projectionLoc = glGetUniformLocation(config.getProgramId(), "projection");
        config.setProjLoc(projectionLoc);
        OpenglUtils.checkGLError();

        glUniformMatrix4(projectionLoc, false, projection.toFloatBuffer());
        OpenglUtils.checkGLError();

        //相机位置
        GL_Matrix view ;
       if(GamingState.instance!=null)
         view =
                GL_Matrix.LookAt( GamingState.instance.camera.Position,  GamingState.instance.camera.ViewDir);
        else{
            view =GL_Matrix.LookAt( new GL_Vector( 0,0,4),  new GL_Vector( 0,0,-1));
       }
        view.fillFloatBuffer(cameraViewBuffer);
        int viewLoc = glGetUniformLocation(programId, "view");
        config.setViewLoc(viewLoc);
        OpenglUtils.checkGLError();
        glUniformMatrix4(viewLoc, false, view.toFloatBuffer());
        OpenglUtils.checkGLError();


        GL_Matrix model = GL_Matrix.rotateMatrix((float) (0 * 3.14 / 180.0), 0, 0);
        int modelLoc = glGetUniformLocation(programId, "model");
        config.setModelLoc(modelLoc);
        OpenglUtils.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
        OpenglUtils.checkGLError();


        /*viewLoc = glGetUniformLocation(terrainProgramId, "view");
        OpenglUtils.checkGLError();
        terrainShaderConfig.setViewLoc(viewLoc);*/

        //物体颜色
        int objectColorLoc = glGetUniformLocation(programId, "objectColor");
        if(objectColorLoc>0){
            config.setObejctColorLoc(objectColorLoc);
            glUniform3f(objectColorLoc, 1.0f, 0.5f, 0.31f);
            OpenglUtils.checkGLError();
        }


        //环境光颜色

       /*  lightColorLoc= glGetUniformLocation(ProgramId, "lightColor");
        glUniform3f(lightColorLoc,1.0f,1f,1f);
        OpenglUtils.checkGLError();*/
        int lightPosInTerrainLoc = glGetUniformLocation(programId, "light.position");
        GL_Vector lightPos = new GL_Vector();
        if(GamingState.instance==null){

        }else{
            lightPos= GamingState.instance.lightPos;
        }
        if (lightPosInTerrainLoc >0) {
            config.setLightPosLoc(lightPosInTerrainLoc);
           // LogUtil.println("light.position not found ");
            //System.exit(1);
            glUniform3f(lightPosInTerrainLoc,  lightPos.x,  lightPos.y,  lightPos.z);
            OpenglUtils.checkGLError();
        }


        int viewPosLoc = glGetUniformLocation(programId, "viewPos");//camerapos

        if (viewPosLoc > 0) {
            config.setViewPosLoc(viewPosLoc);
            glUniform3f(viewPosLoc,  lightPos.x,  lightPos.y,  lightPos.z);
            OpenglUtils.checkGLError();
        }



        //int matAmbientLoc = glGetUniformLocation(ProgramId, "material.ambient");
        //int matDiffuseLoc = glGetUniformLocation(ProgramId, "material.diffuse");
        int matSpecularLoc = glGetUniformLocation(programId, "material.specular");
        if(matSpecularLoc>0){
            glUniform3f(matSpecularLoc, 0.5f, 0.5f, 0.5f);
        }

        int matShineLoc = glGetUniformLocation(programId, "material.shininess");
        if(matShineLoc>0){
            glUniform1f(matShineLoc, 32.0f);//光斑大小
        }
        //glUniform3f(matAmbientLoc, 1.0f, 0.5f, 0.31f);
        //glUniform3f(matDiffuseLoc, 1.0f, 0.5f, 0.31f);



        int lightMatAmbientLoc = glGetUniformLocation(programId, "light.ambient");
        if(lightMatAmbientLoc>0){
            glUniform3f(lightMatAmbientLoc, 0.5f, 0.5f, 0.5f);
        }
        int lightMatDiffuseLoc = glGetUniformLocation(programId, "light.diffuse");
        if(lightMatDiffuseLoc>0) {
            glUniform3f(lightMatDiffuseLoc, 0.5f, 0.5f, 0.5f);
        }
        int lightMatSpecularLoc = glGetUniformLocation(programId, "light.specular");
        if(lightMatSpecularLoc>0) {
            glUniform3f(lightMatSpecularLoc, 0.5f, 0.5f, 0.5f);
        }
        int lightMatShineLoc = glGetUniformLocation(programId, "light.shininess");
        if(lightMatSpecularLoc>0) {
            glUniform1f(lightMatShineLoc, 32.0f);
        }


        int lightConstantLoc = glGetUniformLocation(programId, "light.constant");
        if(lightConstantLoc>0) {
            glUniform1f(lightConstantLoc, 1f);
        }

        int lightLinearLoc = glGetUniformLocation(programId, "light.linear");
        if(lightLinearLoc>0) {
            glUniform1f(lightLinearLoc, 0.01f);
        }

        int lightQuadraticLoc = glGetUniformLocation(programId, "light.quadratic");
        if(lightQuadraticLoc>0) {
            glUniform1f(lightQuadraticLoc, 0.007f);
        }

     /*   for(int i=0;i<8;i++){
            int ourTexture0Loc = glGetUniformLocation(config.getProgramId(), "ourTexture"+i);
            if(ourTexture0Loc>0) {
                config.setTexture0Loc(ourTexture0Loc);
            }
        }*/
        int ourTexture0Loc = glGetUniformLocation(config.getProgramId(), "ourTexture0");
        if(ourTexture0Loc>0) {
            config.setTexture0Loc(ourTexture0Loc);
        }

        int ourTexture1Loc = glGetUniformLocation(config.getProgramId(), "ourTexture1");
        if(ourTexture1Loc>0) {
            config.setTexture1Loc(ourTexture1Loc);
        }
        int ourTexture2Loc = glGetUniformLocation(config.getProgramId(), "ourTexture2");
        if(ourTexture2Loc>0) {
            config.setTexture2Loc(ourTexture2Loc);
        }
        int ourTexture3Loc = glGetUniformLocation(config.getProgramId(), "ourTexture3");
        if(ourTexture3Loc>0) {
            config.setTexture3Loc(ourTexture3Loc);
        }
        int ourTexture4Loc = glGetUniformLocation(config.getProgramId(), "ourTexture4");
        if(ourTexture4Loc>0) {
            config.setTexture4Loc(ourTexture4Loc);
        }
        int ourTexture5Loc = glGetUniformLocation(config.getProgramId(), "ourTexture5");
        if(ourTexture5Loc>0) {
            config.setTexture5Loc(ourTexture5Loc);
        }
        int ourTexture6Loc = glGetUniformLocation(config.getProgramId(), "ourTexture6");
        if(ourTexture6Loc>0) {
            config.setTexture6Loc(ourTexture6Loc);
        }
        int ourTexture7Loc = glGetUniformLocation(config.getProgramId(), "ourTexture7");
        if(ourTexture7Loc>0) {
            config.setTexture7Loc(ourTexture7Loc);
        }
        int ourTexture8Loc = glGetUniformLocation(config.getProgramId(), "ourTexture8");
        if(ourTexture8Loc>0) {
            config.setTexture8Loc(ourTexture8Loc);
        }
        glUseProgram(0);
        //glUniform1f(glGetUniformLocation(terrainProgramId, "light.constant"), 1.0f);
       // glUniform1f(glGetUniformLocation(terrainProgramId, "light.linear"), 0.07f);
        //glUniform1f(glGetUniformLocation(terrainProgramId, "light.quadratic"), 0.017f);

    }



    public void createProgram(ShaderConfig  config) {
        try {
            int programId = ShaderUtils.CreateProgram(config.getVertPath(),config.getFragPath());
            config.setProgramId(programId);
            //terrainProgramId = ShaderUtils.CreateProgram("chapt16/box.vert", "chapt16/box.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static void addTestTerrainVao(){
        terrainShaderConfig.addVao(new Vao("test"));
        Vao vao =new Vao("test");
        int x =0,y=0,z=-10 ;
        TextureInfo ti = TextureManager.getTextureInfo("soil");
        FloatBuffer veticesBuffer= vao.getVertices();

        // x += this.chunkPos.x * getChunkSizeX();
        // z += this.chunkPos.z * getChunkSizeZ();
        // this.faceIndex = 1;
        // count++;//left front top
        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1); //left front right
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);//left behind right
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);

        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);

        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);


        veticesBuffer.put(x + 1); //left behind right
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);
    }
    public static void CreateTerrainVAO(ShaderConfig  config,Vao vao) {
        //config.getVao().setVertices(BufferUtils.createFloatBuffer(902400));
        //glUseProgram(config.getProgramId());
        //生成vaoid
        //create vao
       // Vao vao =config.getVao();

        /*VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();
        this.CreateTerrainVBO();

        glBindVertexArray(0);
        Util.checkGLError();
        */
        int length =10;
        if(vao.getVaoId()>0){


            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
          //  glBindVertexArray(vao.getVaoId());
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        int position = vao.getVertices().position();
      /*  if(vao.getVertices().position()==0){

        }*/
        assert vao.getVertices().position()>0;
        vao.setPoints(vao.getVertices().position()/length);
        assert vao.getVaoId()>0;
        assert vao.getVaoId()>0;
        assert vao.getPoints()>0;
        //LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
        vao.getVertices().rewind();
        // vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo

        glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.




        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, length * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, length * 4, 3 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();

        glVertexAttribPointer(2, 3, GL_FLOAT, false, length * 4, 6 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(2);
        Util.checkGLError();

        glVertexAttribPointer(3, 1, GL_FLOAT, false, length * 4, 9 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(3);
        Util.checkGLError();



        glBindVertexArray(0);
        OpenglUtils.checkGLError();
    }

    public static void CreateLivingVAO(ShaderConfig config,Vao vao ) {

    int length =10;
        if(vao.getVaoId()>0){


            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            //  glBindVertexArray(vao.getVaoId());
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        int position = vao.getVertices().position();
      /*  if(vao.getVertices().position()==0){

        }*/
        assert vao.getVertices().position()>0;
        vao.setPoints(vao.getVertices().position()/length);
        assert vao.getVaoId()>0;
        assert vao.getVaoId()>0;
        assert vao.getPoints()>0;
        //LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
        vao.getVertices().rewind();
        // vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo

        glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.




        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, length * 4, 0);
        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false,length * 4, 3 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();

        glVertexAttribPointer(2, 3, GL_FLOAT, false, length * 4, 6 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(2);
        Util.checkGLError();

        glVertexAttribPointer(3, 1, GL_FLOAT, false, length * 4, 9 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(3);
        Util.checkGLError();


        glBindVertexArray(0);
        OpenglUtils.checkGLError();
    }

    public void CreateTerrainProgram(ShaderConfig  config) {
        try {
            int terrainProgramId = ShaderUtils.CreateProgram(config.getVertPath(),config.getFragPath());
            config.setProgramId(terrainProgramId);
            //terrainProgramId = ShaderUtils.CreateProgram("chapt16/box.vert", "chapt16/box.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void CreateLightProgram(ShaderConfig config) {
        try {
            int LightProgramId = ShaderUtils.CreateProgram(config.getVertPath(),config.getFragPath());
            config.setProgramId(LightProgramId);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void CreateLightVAO(ShaderConfig config) {
        glUseProgram(config.getProgramId());
        int lightVaoId = glGenVertexArrays();
        //config.getVao().setVaoId(lightVaoId);
        //config.getVao().setVaoId(lightVaoId);
        OpenglUtils.checkGLError();

        glBindVertexArray(lightVaoId);
        config.getVao().setVaoId(lightVaoId);
        OpenglUtils.checkGLError();

        this.CreateLightVBO(config);
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
        glUseProgram(0);

    }

    public void CreateUiVAO(ShaderConfig config) {


        //生成vaoid
        //create vao
        Vao vao =config.getVao();
        if(vao.getVaoId()>0){
            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        int position = vao.getVertices().position();
        if(vao.getVertices().position()==0){
            ShaderUtils.printText("it's test ",50,50,0,24);
        }
        vao.setPoints(vao.getVertices().position()/10);
        LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
        vao.getVertices().rewind();
        // vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 10 * 4, 0);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();
        //纹理位置
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 10 * 4, 3 * 4);
        glEnableVertexAttribArray(1);
        //textureHandle
        glVertexAttribPointer(2, 1, GL_FLOAT, false, 10 * 4, 5 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(2);
        OpenglUtils.checkGLError();
        //color
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 10 * 4, 6 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(3);
        OpenglUtils.checkGLError();


        glBindVertexArray(0);
        OpenglUtils.checkGLError();


    }

    public void CreateLightVBO(ShaderConfig config ) {

        //创建vao2=========================================================
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects

        // model = GL_Matrix.multiply(view2,model);
        // model = GL_Matrix.multiply(view2,model);
        int  VboId=glGenBuffers();//create vbo
        config.getVao().setVboId(VboId);
        int minX = -1;
        int minY = -1;
        int minZ = -1;
        int maxX = 1;
        int maxY = 1;
        int maxZ = 1;
        GL_Vector color = new GL_Vector(1.0f,1f, 1f);
        GL_Vector P1 = new GL_Vector(minX, minY, maxZ);
        GL_Vector P2 = new GL_Vector(maxX, minY, maxZ);
        GL_Vector P3 = new GL_Vector(maxX, minY, minZ);
        GL_Vector P4 = new GL_Vector(minX, minY, minZ);

        GL_Vector P5 = new GL_Vector(minX, maxY, maxZ);
        GL_Vector P6 = new GL_Vector(maxX, maxY, maxZ);
        GL_Vector P7 = new GL_Vector(maxX, maxY, minZ);
        GL_Vector P8 = new GL_Vector(minX, maxY, minZ);
        Vao vao = config.getVao();
        FloatBuffer floatBuffer = vao.getVertices();
        floatBuffer.rewind();
        ShaderUtils.draw3dColorSimple(P1, P2, P6, P5, new GL_Vector(0, 0, -1f), color, floatBuffer, config);


        //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
        ShaderUtils.draw3dColorSimple(P3, P4, P8, P7, new GL_Vector(0, 0, 1), color, floatBuffer, config);

        //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P5,P6,P7,P8,new GL_Vector(0,1,0f),front);
        ShaderUtils.draw3dColorSimple(P5, P6, P7, P8, new GL_Vector(0, -1, 0), color, floatBuffer, config);

        ShaderUtils.draw3dColorSimple(P4, P3, P2, P1, new GL_Vector(0, 1, 0), color, floatBuffer, config);

        ShaderUtils.draw3dColorSimple(P2, P3, P7, P6, new GL_Vector(1, 0, 0f), color, floatBuffer, config);

        ShaderUtils.draw3dColorSimple(P4, P1, P5, P8, new GL_Vector(-1, 0, 0), color, floatBuffer, config);
        ShaderUtils.createVao(config,config.getVao(),new int[]{3,3});
    }


    public void CreateSkyVBO(ShaderConfig config ) {

        //创建vao2=========================================================
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects

        // model = GL_Matrix.multiply(view2,model);
        int  VboId=glGenBuffers();//create vbo
        config.getVao().setVboId(VboId);
        GL_Vector p1 = new GL_Vector(-0.5f, -0.5f, -0.5f);
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
        config.getVao().setPoints(Vertices.position()/6);
        Vertices.rewind(); //


        glBindBuffer(GL_ARRAY_BUFFER, config.getVao().getVboId());//bind vbo
        OpenglUtils.checkGLError();
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();
    }

   // int lightModelLoc;

    public void uniformLight() {
        GL_Matrix model = GL_Matrix.translateMatrix( GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        GL_Matrix view = GL_Matrix.translateMatrix(0, 0, 0);
        glUseProgram(lightShaderConfig.getProgramId());
        int lightProjectionLoc = glGetUniformLocation(lightShaderConfig.getProgramId(), "projection");
        glUniformMatrix4(lightProjectionLoc, false, projection.toFloatBuffer());
        OpenglUtils.checkGLError();
        int lightModelLoc = glGetUniformLocation(lightShaderConfig.getProgramId(), "model");
        lightShaderConfig.setModelLoc(lightModelLoc);
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        OpenglUtils.checkGLError();

      //  int lightViewLoc = ;
        lightShaderConfig.setViewLoc(glGetUniformLocation(lightShaderConfig.getProgramId(), "view"));
        glUniformMatrix4(lightShaderConfig.getViewLoc(), false, view.toFloatBuffer());
        OpenglUtils.checkGLError();

    }

    public void initViewModelProjectionLoc(ShaderConfig config,GL_Matrix model ,GL_Matrix view){
        assert config.getProgramId()>0;
        glUseProgram(config.getProgramId());
        int lightProjectionLoc = glGetUniformLocation(config.getProgramId(), "projection");
        glUniformMatrix4(lightProjectionLoc, false, projection.toFloatBuffer());
        OpenglUtils.checkGLError();
        int lightModelLoc = glGetUniformLocation(config.getProgramId(), "model");
        lightShaderConfig.setModelLoc(lightModelLoc);
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        OpenglUtils.checkGLError();
        //  int lightViewLoc = ;
        lightShaderConfig.setViewLoc(glGetUniformLocation(lightShaderConfig.getProgramId(), "view"));
        glUniformMatrix4(lightShaderConfig.getViewLoc(), false, view.toFloatBuffer());
        OpenglUtils.checkGLError();
    }

/*
    public void cameraPosChangeListener() {
        LogUtil.println("please use camera changeCallBack");
        GL_Matrix view =
                GL_Matrix.LookAt( GamingState.instance.camera.Position,  GamingState.instance.camera.ViewDir);
        view.fillFloatBuffer(cameraViewBuffer);

        glUseProgram(terrainShaderConfig.getProgramId());


        glUniformMatrix4(terrainShaderConfig.getViewLoc(), false, cameraViewBuffer);
        OpenglUtils.checkGLError();
        //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(terrainShaderConfig.getViewPosLoc(),  GamingState.instance.camera.Position.x,  GamingState.instance.camera.Position.y,  GamingState.instance.camera.Position.z);
        OpenglUtils.checkGLError();


        glUseProgram(lightShaderConfig.getProgramId());
        OpenglUtils.checkGLError();
        //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(lightShaderConfig.getViewLoc(), false, view.toFloatBuffer());

        OpenglUtils.checkGLError();

      *//*  glUseProgram(livingThingShaderConfig.getProgramId());
        OpenglUtils.checkGLError();
        //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(livingThingShaderConfig.getViewLoc(), false, view.toFloatBuffer());

        OpenglUtils.checkGLError();*//*


        glUseProgram(livingThingShaderConfig.getProgramId());


        glUniformMatrix4(livingThingShaderConfig.getViewLoc(), false, cameraViewBuffer);
        OpenglUtils.checkGLError();
        //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(livingThingShaderConfig.getViewPosLoc(),  GamingState.instance.camera.Position.x,  GamingState.instance.camera.Position.y,  GamingState.instance.camera.Position.z);
        OpenglUtils.checkGLError();


    }*/

    public void update(){

    }
    public void render(){

    }
}
