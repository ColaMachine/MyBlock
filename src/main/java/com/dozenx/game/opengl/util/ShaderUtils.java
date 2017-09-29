package com.dozenx.game.opengl.util;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.html.Image;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.font.FontUtil;
import com.dozenx.game.font.Glyph;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.util.FileUtil;
import com.dozenx.util.FloatBufferWrap;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.Util;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class ShaderUtils {
    public static final GL_Vector BLUE = new GL_Vector(0,0,1) ;
    public static final GL_Vector RED = new GL_Vector(1,0,0) ;
    public static final GL_Vector GREEN = new GL_Vector(0,1,0) ;
    public static final GL_Vector WHITE = new GL_Vector(0,0,0) ;
    public static final GL_Vector BLACK = new GL_Vector(1,1,1) ;

   /*public static void initShader(ShaderConfig shaderConfig){

        try {
            shaderConfig.setVertShaderId(CreateVertShaders(shaderConfig.getVertPath()));

            shaderConfig.setFragShaderId(CreateFragShaders(shaderConfig.getFragPath()));

            shaderConfig.setProgramId(CreateProgram(shaderConfig.getVertShaderId(),shaderConfig.getFragShaderId()));
            initProModelView(shaderConfig);
            //createCubeVao(shaderConfig,new Vao());

        } catch (IOException e) {
            LogUtil.println(shaderConfig.getVertPath()+ "load shader failed");
            e.printStackTrace();
            System.exit(0);

        }
    }*/

    /**
     *
     * @param shaderConfig
     *
     * init2dSahder
     *
     */
   /* public static void init2dShader(ShaderConfig shaderConfig){

        try {
            shaderConfig.setVertShaderId(CreateVertShaders(shaderConfig.getVertPath()));

            shaderConfig.setFragShaderId(CreateFragShaders(shaderConfig.getFragPath()));

            shaderConfig.setProgramId(CreateProgram(shaderConfig.getVertShaderId(),shaderConfig.getFragShaderId()));
            //initProModelView(shaderConfig);
            //createCubeVao(shaderConfig,new Vao());

        } catch (IOException e) {
            LogUtil.println(shaderConfig.getVertPath()+ "load shader failed");
            e.printStackTrace();
            System.exit(0);

        }
    }
*/

   /* public static void initProModelView(ShaderConfig config){
        GL_Matrix model= GL_Matrix.rotateMatrix((float)(45*3.14/180.0),0,0);
        GL_Matrix model2=GL_Matrix.multiply(model,GL_Matrix.translateMatrix(config.getPosition().x+2,config.getPosition().y,config.getPosition().z)) ;


        model=GL_Matrix.multiply(model,GL_Matrix.translateMatrix(config.getPosition().x,config.getPosition().y,config.getPosition().z)) ;



        config.setModel(model);
        config.setModel2(model2);
        GL_Matrix view= GL_Matrix.translateMatrix(0,0,0);
        config.setView(view);

        GL_Matrix projection= GL_Matrix.perspective3(45,600/600,1f,1000.0f);
        config.setProjection(projection);

        glUseProgram(config.getProgramId());
        //unifrom赋值===========================================================
        int projectionLoc= glGetUniformLocation(config.getProgramId(), "projection");
        config.setProjLoc(projectionLoc);
         OpenglUtils.checkGLError();
        int modelLoc= glGetUniformLocation(config.getProgramId(), "model");
        config.setModelLoc(modelLoc);
         OpenglUtils.checkGLError();
        int viewLoc= glGetUniformLocation(config.getProgramId(), "view");
        config.setViewLoc(viewLoc);
         OpenglUtils.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());

        // config.setModel(viewLoc);
         OpenglUtils.checkGLError();
        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
         OpenglUtils.checkGLError();
        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );



    }*/
    /*public static void initObjectColor(ShaderConfig config ){
        glUseProgram(config.getProgramId());
        int objectColorLoc= glGetUniformLocation(config.getProgramId(), "objectColor");
        config.setObejctColorLoc(objectColorLoc);
        //float[] objectColorAry = new float[]{1,0.5f,0.31f,};
        // FloatBuffer objectColorBbuffer = BufferUtils.createFloatBuffer(3);
        // objectColorBbuffer.put(objectColorAry);objectColorBbuffer.rewind();
        glUniform3f(objectColorLoc,1.0f,0.5f,0.31f);
        // glUniformMatrix4(objectColorLoc, false, objectColorBbuffer);
        OpenglUtils.checkGLError();
    }
    public static void initLightColor(ShaderConfig config ){
        glUseProgram(config.getProgramId());
        //环境光颜色

        int lightColorLoc= glGetUniformLocation(config.getProgramId(), "lightColor");
//        float[] lightColorAry = new float[]{1,1f,1f,};
//        FloatBuffer lightColorBbuffer = BufferUtils.createFloatBuffer(3);
//        lightColorBbuffer.put(lightColorAry);lightColorBbuffer.rewind();
//        glUniformMatrix4(lightColorLoc,  false,lightColorBbuffer );
        glUniform3f(lightColorLoc,1.0f,1f,1f);
        OpenglUtils.checkGLError();
        config.setLightColorLoc(lightColorLoc);


    }
    public static void createCubeVao(Vao vao){
        //生成vaoid
        if(vao.getVaoId()>0){
            LogUtil.err("vao have been initialized");
        }
        vao.setVaoId( glGenVertexArrays());


        OpenglUtils.checkGLError();
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        float width = 1;

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        float VerticesArray[]= {

                -width, -width, -width,  0.0f, 0.0f,
                width, -width, -width,  1.0f, 0.0f,
                width,  width, -width,  1.0f, 1.0f,
                width,  width, -width,  1.0f, 1.0f,
                -width,  width, -width,  0.0f, 1.0f,
                -width, -width, -width,  0.0f, 0.0f,

                -width, -width,  width,  0.0f, 0.0f,
                width, -width,  width,  1.0f, 0.0f,
                width,  width,  width,  1.0f, 1.0f,
                width,  width,  width,  1.0f, 1.0f,
                -width,  width,  width,  0.0f, 1.0f,
                -width, -width,  width,  0.0f, 0.0f,

                -width,  width,  width,  1.0f, 0.0f,
                -width,  width, -width,  1.0f, 1.0f,
                -width, -width, -width,  0.0f, 1.0f,
                -width, -width, -width,  0.0f, 1.0f,
                -width, -width,  width,  0.0f, 0.0f,
                -width,  width,  width,  1.0f, 0.0f,

                width,  width,  width,  1.0f, 0.0f,
                width,  width, -width,  1.0f, 1.0f,
                width, -width, -width,  0.0f, 1.0f,
                width, -width, -width,  0.0f, 1.0f,
                width, -width,  width,  0.0f, 0.0f,
                width,  width,  width,  1.0f, 0.0f,

                -width, -width, -width,  0.0f, 1.0f,
                width, -width, -width,  1.0f, 1.0f,
                width, -width,  width,  1.0f, 0.0f,
                width, -width,  width,  1.0f, 0.0f,
                -width, -width,  width,  0.0f, 0.0f,
                -width, -width, -width,  0.0f, 1.0f,

                -width,  width, -width,  0.0f, 1.0f,
                width,  width, -width,  1.0f, 1.0f,
                width,  width,  width,  1.0f, 0.0f,
                width,  width,  width,  1.0f, 0.0f,
                -width,  width,  width,  0.0f, 0.0f,
                -width,  width, -width,  0.0f, 1.0f,
        };



         FloatBuffer Vertices ;

        Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        //config.setVertices(Vertices);
        int vboId=glGenBuffers();//create vbo
        //config.setVboId(vboId);

        glBindBuffer(GL_ARRAY_BUFFER, vboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data


        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0);//0 1 2 决定了location 位置

        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        vao.setPoints(VerticesArray.length/5);


        OpenglUtils.checkGLError();
        glBindVertexArray(0);
         OpenglUtils.checkGLError();

        //glUseProgram(ProgramId);



    }*/
    public static int CreateProgram(String vertexPath,String fragPath)throws Exception {
        int vertShaderId =CreateVertShaders(vertexPath);
        int fragShaderId = CreateFragShaders(fragPath);
        int programId = CreateProgram(vertShaderId,fragShaderId);
        return programId;

    }/*
    public static void shaderBindTexture(ShaderConfig config,int texHandle){

    }*/
    //init2dshader
    public static int CreateProgram(int vertexShaderId, int fragmentShaderId){
        int newProgramId = glCreateProgram();
         OpenglUtils.checkGLError();

        // Attach vertex shader
        glAttachShader(newProgramId, vertexShaderId);
         OpenglUtils.checkGLError();

        // Attach fragment shader
        glAttachShader(newProgramId, fragmentShaderId);
         OpenglUtils.checkGLError();

        // We tell the program how the vertex attribute indices will map
        // to named "in" variables in the vertex shader. This must be done
        // before compiling.
        // glBindAttribLocation(ProgramId, POSITION_INDEX, "in_Position");
        // glBindAttribLocation(ProgramId, COLOR_INDEX, "in_Color");

        glLinkProgram(newProgramId);
         OpenglUtils.checkGLError();

        // Print possible compile errors
        System.out.println("Program linking:");
        printProgramLog(newProgramId);


        //System.out.println("transformLoc:"+transformLoc);
         OpenglUtils.checkGLError();
        //glUseProgram(ProgramId);
        //  OpenglUtils.checkGLError();
        return newProgramId;


    }

    public static int  CreateVertShaders(String path) throws IOException {
        OpenglUtils.checkGLError();
        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/main/java/gldemo/learnOpengl/"+path).toString());
        //创建着色器
        OpenglUtils.checkGLError();
        int  newShaderId = glCreateShader(GL_VERTEX_SHADER);
         OpenglUtils.checkGLError();
        //源码
        glShaderSource(newShaderId, VertexShader);
         OpenglUtils.checkGLError();
        //编译
        glCompileShader(newShaderId);
         OpenglUtils.checkGLError();
        //打印日志
        printShaderLog(newShaderId);
         OpenglUtils.checkGLError();
        return newShaderId;
    }
    public static int CreateFragShaders(String path) throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/main/java/gldemo/learnOpengl/"+path).toString());

        int newFragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
         OpenglUtils.checkGLError();

        glShaderSource(newFragmentShaderId, FragmentShader);
         OpenglUtils.checkGLError();

        glCompileShader(newFragmentShaderId);
         OpenglUtils.checkGLError();

        // Print possible compile errors
        System.out.println("Fragment shader compilation:");
        printShaderLog(newFragmentShaderId);
        return newFragmentShaderId;

    }

    public static String readShaderSourceCode(String filePath) throws IOException {
        return  FileUtil.readFile2Str(filePath);


    }
    /**
     * Print log of shader object.
     *
     * @param id
     */
    public static void printShaderLog(int id) {
        int logLength = glGetShader(id, GL_INFO_LOG_LENGTH);
         OpenglUtils.checkGLError();

        System.out.println("  Log (length " + logLength + " chars)");
        String log = glGetShaderInfoLog(id, logLength);
         OpenglUtils.checkGLError();
        for (String line : log.split("\n")) {
            System.out.println("  " + line);
        }
        System.out.println("");
    }

    /**
     * Print log of program object
     *
     * @param programId
     */
    public static void printProgramLog(int programId) {
        int logLength = glGetProgram(programId, GL_INFO_LOG_LENGTH);
         OpenglUtils.checkGLError();

        System.out.println("  Log (length " + logLength + " chars)");
        String log = glGetProgramInfoLog(programId, logLength);
         OpenglUtils.checkGLError();
        for (String line : log.split("\n")) {
            System.out.println("  " + line);
        }
        System.out.println("");
    }

   /* public static int createVAO(FloatBuffer floatBuffer){floatBuffer.flip();
        int lightVaoId = glGenVertexArrays();
         OpenglUtils.checkGLError();

        glBindVertexArray(lightVaoId);
         OpenglUtils.checkGLError();

        CreateLightVBO(floatBuffer);
        glBindVertexArray(0);
         OpenglUtils.checkGLError();
        return lightVaoId;
    }*/
   /* public static void CreateLightVBO(FloatBuffer Vertices){
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL15.GL_STATIC_DRAW);//put data

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8* 4, 0);
         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
         OpenglUtils.checkGLError();
    }*/


   /* public static void drawCubeWithShader(ShaderConfig config,Vao vao){

        glUseProgram(config.getProgramId());
         OpenglUtils.checkGLError();

        glBindVertexArray(vao.getVaoId());
         OpenglUtils.checkGLError();
        glDrawArrays(GL_TRIANGLES,0,vao.getPoints());
         OpenglUtils.checkGLError();
        glBindVertexArray(0);
         OpenglUtils.checkGLError();



    }*/

    public static void checkGLError() {
        //try{
             OpenglUtils.checkGLError();
        //}catch (Exception e ){
         //   e.printStackTrace();
            //LogUtil.println(e.getMessage());
            //throw e;
       // }
    }

   // public static ShaderConfig twodImgConfig ;
   // public static int image2DShaderProgram;

    /*public static void init2dImageShaderProgram(ShaderConfig config){
        try {
            config.setVertShaderId(CreateVertShaders(config.getVertPath()));

            config.setFragShaderId(CreateFragShaders(config.getFragPath()));

            config.setProgramId(CreateProgram(config.getVertShaderId(),config.getFragShaderId()));

          //  create2dimageVao(config);

        } catch (IOException e) {
            LogUtil.println(config.getVertPath()+ "load shader failed");
            e.printStackTrace();
            System.exit(0);

        }

    }*/



  /*  public static ShaderConfig get2DImgConfig(){
         if(twodImgConfig==null ){
             twodImgConfig =new ShaderConfig("2dimg","chapt7/2dimg.frag","chapt7/2dimg.vert");
             //twodImgConfig.setVertPath();
             //twodImgConfig.setFragPath("chapt7/2dimg.frag");
            init2dShader(twodImgConfig);

        }
        return twodImgConfig;
    }*/
    //static ShaderConfig twodColorConfig =null;
   /* public static ShaderConfig get2DColorConfig(){
        if(twodColorConfig==null ){
            twodColorConfig =new ShaderConfig("2dcoloor","chapt7/2dcolor.frag","chapt7/2dcolor.vert");
           // twodColorConfig.setVertPath("chapt7/2dcolor.vert");
            //twodColorConfig.setFragPath("chapt7/2dcolor.frag");
            init2dShader(twodColorConfig);
            //initObjectColor(d2ColorShaderConfig);
        }
        return twodColorConfig;
    }*/

    /*public static ShaderConfig getBorderShaderConfig(){
        if(twodColorConfig==null ){
            twodColorConfig =new ShaderConfig();
            twodColorConfig.setVertPath("chapt7/2dcolor.vert");
            twodColorConfig.setFragPath("chapt7/2dcolor.frag");
            init2dShader(twodColorConfig);
            initObjectColor(twodColorConfig);
        }
        return twodColorConfig;
    }*/
    /*
     * use shader to draw image
     */
    /*public static void draw2DImageWithShader(ShaderConfig config,Vao vao) {
        *//*if(shaderImageConfig==null ){
            shaderImageConfig =new ShaderConfig();
            shaderImageConfig.setVertPath("chapt7/chapt7.vert");
            shaderImageConfig.setFragPath("chapt7/chapt7.frag");
            initShader(shaderImageConfig);

        }*//*
       // renderImageShader(shaderImageConfig);
        *//*if(image2DShaderProgram==0 ){
            createImage2DShaderProgram();
        }*//*

try {
    glUseProgram(config.getProgramId());
    ShaderUtils.checkGLError();
    // glUniform4f(0, 0.0f, greenValue, 0.0f, 1.0f);
    //glBindTexture(GL_TEXTURE_2D, config.getTextureHanle());
    glBindVertexArray(vao.getVaoId());
    ShaderUtils.checkGLError();
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    ShaderUtils.checkGLError();
    glBindVertexArray(0);
    ShaderUtils.checkGLError();
    ShaderUtils.checkGLError();
}catch(Exception e){
    e.printStackTrace();
    LogUtil.err(e);
}

    }*/

 /*   public static void draw2DColorWithShader(Vector4f backgroundColor,ShaderConfig shaderConfig ,Vao vao) {
        glUseProgram(ShaderUtils.get2DColorConfig().getProgramId());
        glUniform3f(shaderConfig.getObejctColorLoc(),  backgroundColor.x,backgroundColor.y,backgroundColor.z);
        glBindVertexArray(vao.getVaoId());
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

         OpenglUtils.checkGLError();

    }*/

   /* public static void finalDraw2DColor(){
        ShaderConfig config = ShaderUtils.get2DColorConfig();
       // ShaderUtils.get2dcolor
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
       // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
       // OpenglUtils.checkGLError();
        glBindVertexArray(twodColorVao.getVaoId());
        OpenglUtils.checkGLError();
        glDrawArrays(GL_TRIANGLES,0, twodColorVao.getPoints());
        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
    }
*/
    /*public static void finalDraw(ShaderConfig config){
        //TextureManager.getTextureInfo("soil").bind();
        // ShaderUtils.get2dcolor
        assert config.getProgramId()>0;
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
        // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        // OpenglUtils.checkGLError();
        assert config.getVao().getVaoId()>0;
        glBindVertexArray(config.getVao().getVaoId());
        OpenglUtils.checkGLError();
        assert config.getVao().getPoints()>0;
        glDrawArrays(GL_TRIANGLES,0, config.getVao().getPoints());
        //config.getVao().getVertices().rewind();

        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
        glUseProgram(0);
    }*/

    /**
     * 和正式的finaldraw 相比 temp 会遍历buffer 生成vao
     * @param config
     * @param vao
     */
    public static void freshVaoAndDraw(ShaderConfig config,Vao vao){
        //TextureManager.getTextureInfo("soil").bind();
        // ShaderUtils.get2dcolor

        assert config.getProgramId()>0;
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
        // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        // OpenglUtils.checkGLError();
        assert vao.getVaoId()>0;
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        assert vao.getPoints()>0;
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        for(int i=0;i<=vao.getVertices().listLimit;i++){
            int Points =vao.getVertices().buffers.get(i).position()/config.getParamTotalLen();
            vao.setPoints(Points);
            vao.getVertices().buffers.get(i).rewind();
            //vao.getVertices().rewind();
            glBufferData(GL_ARRAY_BUFFER,vao.getVertices().buffers.get(i), GL_STATIC_DRAW);
            // vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);
            glDrawArrays(GL_TRIANGLES,0, vao.getPoints());
        }

        //config.getVao().getVertices().rewind();

        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
        //glUseProgram(0);
    }
    public static void finalDraw(ShaderConfig config,Vao vao){
        //TextureManager.getTextureInfo("soil").bind();
        // ShaderUtils.get2dcolor
        if(vao.getPoints()==0){
            return;
        }
        assert config.getProgramId()>0;
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
        // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        // OpenglUtils.checkGLError();
        assert vao.getVaoId()>0;
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        assert vao.getPoints()>0;
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
    /*    for(int i=0;i<vao.getVertices().listLimit;i++){

            vao.setPoints(vao.getVertices().buffers.get(i).limit()/config.getParamTotalLen());

            //vao.getVertices().rewind();
            glBufferData(GL_ARRAY_BUFFER,vao.getVertices().buffers.get(i), GL_STATIC_DRAW);*/
           // vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);
            glDrawArrays(GL_TRIANGLES,0, vao.getPoints());
      //  }

        //config.getVao().getVertices().rewind();

        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
        //glUseProgram(0);
    }

    public static void finalDrawVbo(ShaderConfig config,Vao vao){
        //TextureManager.getTextureInfo("soil").bind();
        // ShaderUtils.get2dcolor
        if(vao.getPoints()==0){
            return;
        }
        assert config.getProgramId()>0;
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
        // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        // OpenglUtils.checkGLError();
        assert vao.getVaoId()>0;
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        assert vao.getPoints()>0;
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
    /*    for(int i=0;i<vao.getVertices().listLimit;i++){

            vao.setPoints(vao.getVertices().buffers.get(i).limit()/config.getParamTotalLen());

            //vao.getVertices().rewind();
            glBufferData(GL_ARRAY_BUFFER,vao.getVertices().buffers.get(i), GL_STATIC_DRAW);*/
        // vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);
        glDrawArrays(GL_TRIANGLES,0, vao.getPoints());
        //  }

        //config.getVao().getVertices().rewind();

        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
        //glUseProgram(0);
    }
    /*
    public static void finalDraw2DImage(){
        ShaderConfig config = ShaderUtils.get2DImgConfig();
        // ShaderUtils.get2dcolor
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
        // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        // OpenglUtils.checkGLError();
        glBindVertexArray(ShaderManager.uiShaderConfig.getVao().getVaoId());
        OpenglUtils.checkGLError();
        glDrawArrays(GL_TRIANGLES, 0, ShaderManager.uiShaderConfig.getVao().getPoints());
        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
    }
    public static void drawBorderWithShader(Vector4f backgroundColor,ShaderConfig shaderConfig ,Vao vao) {
        glUseProgram(ShaderUtils.get2DColorConfig().getProgramId());
        OpenglUtils.checkGLError();
        glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        OpenglUtils.checkGLError();
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        glDrawElements(GL_LINES, 8, GL_UNSIGNED_INT, 0);
        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();


    }*/

    /*public static void createImage2DShaderProgram(){
        try {
            image2DShaderProgram = OpenglUtils.CreateProgram("twodimg/twodimg.vert", "twodimg/twodimg.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }*/
   /* public void drawLine() {

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(12f);
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_LINES); // draw triangles
        GL11.glVertex3f(lineStart.x, lineStart.y,
                lineStart.z); // A1-A2
        GL11.glVertex3f(mouseEnd.x, mouseEnd.y, mouseEnd.z);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

    }*/

  /*  public static void create2dimageVao(Vao vao,float left,float top,float width,float height){

        //生成vaoid
        //create vao
        if(vao.getVaoId()>0){

            //glBindVertexArray(vao.getVaoId());
           // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
             OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
            int eboId = glGenBuffers();
            vao.setEboId(eboId);
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
         OpenglUtils.checkGLError();

        //create vbo

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        left= left/800-1f;
        top=top/600+1f;
        float right=left+width/800;
        float bottom=top-height/600;
        float VerticesArray[]= {left, top, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top Right
                left,bottom, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // Bottom Right
                right, bottom, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom Left
                right, top, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top Left
        };


       FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty

        vao.setVertices(Vertices);


        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        //create ebo


       // float width = 1;


        int[] indices={
                0,1,3,
                1,2,3
        };
        IntBuffer Indices = BufferUtils.createIntBuffer(indices.length);
        Indices.put(indices);
        Indices.rewind();


         OpenglUtils.checkGLError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vao.getEboId());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Indices,GL_STATIC_DRAW);

        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);

        //颜色
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4);

         OpenglUtils.checkGLError();
        //
        glEnableVertexAttribArray(1);
        //纹理位置
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8* 4, 6*4);

         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(2);

         OpenglUtils.checkGLError();
        glBindVertexArray(0);
         OpenglUtils.checkGLError();

    }*/
    //public static Vao twodImageVao=new Vao();

  /*  public static void update2dColorVao(){
        Vao vao = twodColorVao;
        //生成vaoid
        //create vao
        if(vao.getVaoId()>0){

            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);

        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();

        //create vbo

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组

        vao.setPoints(twoDColorBuffer.position()/6);
        twoDColorBuffer.rewind();

        vao.setVertices(twoDColorBuffer);


        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, twoDColorBuffer, GL_STATIC_DRAW);//put data

        //create ebo


        // float width = 1;





        OpenglUtils.checkGLError();


        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);

        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);

        //颜色


        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6* 4, 3*4);

        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(1);

        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();

    }*/
   /* public static void updateVao(Vao vao){
        //生成vaoid
        //create vao

        if(vao.getVaoId()>0){
            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
           *//* int eboId = glGenBuffers();
            vao.setEboId(eboId);*//*
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        vao.setPoints(vao.getVertices().position()/10);
        //LogUtil.println("twoDImgBuffer:"+twoDImgBuffer.position());
        vao.getVertices().rewind();
        //vao.setVertices(twoDImgBuffer);
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



    }*/


   /* public static void updateLivingVao(Vao vao){

        int stride= 8*4;
        //生成vaoid
        //create vao

        if(vao.getVaoId()>0){
            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
           *//* int eboId = glGenBuffers();
            vao.setEboId(eboId);*//*
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        vao.setPoints(vao.getVertices().position()/9);
        //LogUtil.println("twoDImgBuffer:"+twoDImgBuffer.position());
        vao.getVertices().flip();
        //vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(1);
        OpenglUtils.checkGLError();
        //纹理位置
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(2);
        OpenglUtils.checkGLError();
        //textureHandle
      *//*  glVertexAttribPointer(3, 1, GL_FLOAT, false, stride, 8 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(3);
        OpenglUtils.checkGLError();*//*


        //color
        *//*glVertexAttribPointer(3, 4, GL_FLOAT, false, stride, 6 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(3);
        OpenglUtils.checkGLError();*//*


        glBindVertexArray(0);
        OpenglUtils.checkGLError();



    }*/

   /* public static void updateLivingVao1(Vao vao){

        int stride= 9*4;
        //生成vaoid
        //create vao

        if(vao.getVaoId()>0){
            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
           *//* int eboId = glGenBuffers();
            vao.setEboId(eboId);*//*
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        vao.setPoints(vao.getVertices().position()/9);
        //LogUtil.println("twoDImgBuffer:"+twoDImgBuffer.position());
        vao.getVertices().rewind();
        //vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(1);
        OpenglUtils.checkGLError();
        //纹理位置
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(2);
        OpenglUtils.checkGLError();
        //textureHandle
        glVertexAttribPointer(3, 1, GL_FLOAT, false, stride, 8 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(3);
        OpenglUtils.checkGLError();
        //color
        *//*glVertexAttribPointer(3, 4, GL_FLOAT, false, stride, 6 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(3);
        OpenglUtils.checkGLError();*//*


        glBindVertexArray(0);
        OpenglUtils.checkGLError();



    }*/
  /*  public static void update2dImageVao(ShaderConfig config ){
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
           *//* int eboId = glGenBuffers();
            vao.setEboId(eboId);*//*
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
         vao.setPoints(vao.getVertices().position()/10);
//        LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
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



    }*/
  /*  public static void createFinal2dimageVao(Vao vao){
        //生成vaoid
        //create vao
        if(vao.getVaoId()>0){

            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
             OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);

        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
         OpenglUtils.checkGLError();

        //create vbo

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组

        vao.setPoints(twoDImgBuffer.position());
       twoDImgBuffer.rewind();

        vao.setVertices(twoDImgBuffer);


        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, twoDImgBuffer, GL_STATIC_DRAW);//put data

        //create ebo


        // float width = 1;





         OpenglUtils.checkGLError();


        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);

        //颜色

        //纹理位置
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 8* 4, 6*4);

         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(1);

         OpenglUtils.checkGLError();
        glBindVertexArray(0);
         OpenglUtils.checkGLError();

    }*/




   /* public static void createBorderVao(Vao vao,float left,float top,float width,float height){
        //生成vaoid
        //create vao
        if(vao.getVaoId()>0){

            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        }else {
            vao.setVaoId(glGenVertexArrays());
             OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);
            int eboId = glGenBuffers();
            vao.setEboId(eboId);
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
         OpenglUtils.checkGLError();

        //create vbo

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        left= left/800-1f;
        top=top/600+1f;
        float right=left+width/800;
        float bottom=top-height/600;
        float VerticesArray[]= {left, top, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top Right
                left,bottom, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // Bottom Right
                right, bottom, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom Left
                right, top, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top Left
        };


        FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty

        vao.setVertices(Vertices);


        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        //create ebo


        // float width = 1;


        int[] indices={
                0,1,1,2,2,3,3,0

        };
*//*
        int[] indices={
                0,1,3,
                1,2,3
        };*//*
        IntBuffer Indices = BufferUtils.createIntBuffer(indices.length);
        Indices.put(indices);
        Indices.rewind();


         OpenglUtils.checkGLError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vao.getEboId());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Indices,GL_STATIC_DRAW);

        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);

        //颜色
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4);

         OpenglUtils.checkGLError();
        //
        glEnableVertexAttribArray(1);
        //纹理位置
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8* 4, 6*4);

         OpenglUtils.checkGLError();
        glEnableVertexAttribArray(2);

         OpenglUtils.checkGLError();
        glBindVertexArray(0);
         OpenglUtils.checkGLError();

    }*/


    /*public static void createFinalBorderVao(Vao vao){
        //生成vaoid
        //create vao
        if(vao.getVaoId()>0){


        }else {
            vao.setVaoId(glGenVertexArrays());
             OpenglUtils.checkGLError();
            int VboId=glGenBuffers();//create vbo
            vao.setVboId(VboId);

        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
         OpenglUtils.checkGLError();

        //create vbo

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        int points = twoDBorderBuffer.position();
        vao.setPoints(twoDImgBuffer.position());
        twoDBorderBuffer.rewind();

        vao.setVertices(twoDBorderBuffer);


        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
        glBufferData(GL_ARRAY_BUFFER, twoDBorderBuffer, GL_STATIC_DRAW);//put data





        OpenglUtils.checkGLError();


        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);

        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);

        //颜色
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);

        OpenglUtils.checkGLError();
        //
        glEnableVertexAttribArray(1);
        //纹理位置


         OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();

    }*/



   // public static FloatBuffer twoDImgBuffer =BufferUtils.createFloatBuffer(10240);
   // public static FloatBuffer twoDBorderBuffer = BufferUtils.createFloatBuffer(10240);
  //  public static FloatBuffer twoDColorBuffer = BufferUtils.createFloatBuffer(10240);

    /**
     * 不带颜色的绘制图片
     * @param image
     * @param posX
     * @param posY
     * @param z
     * @param width
     * @param height
     */
  public static void draw2dImg(Image image, int posX, int posY, float z,int width, int height) {
        Vao vao = ShaderManager.uiShaderConfig.getVao();

        TextureInfo ti = image.getTexture();

        if(ti==null)return;
      if(ti.getSplitx()!=null){
          ti.draw2dImg(posX,posY,z,width,height);
          return;
      }
        float left =( (float)posX)/Constants.WINDOW_WIDTH*2-1f;
        float top=(Constants.WINDOW_HEIGHT- ( (float)posY))/Constants.WINDOW_HEIGHT*2-1f;
        float _height = ( (float)height)/Constants.WINDOW_HEIGHT*2;
        float _width =( (float)width)/Constants.WINDOW_WIDTH*2;
        GL_Vector p1 = new GL_Vector(left,top-_height,z);
        GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
        GL_Vector p3 = new GL_Vector(left+_width,top,z);
        GL_Vector p4 = new GL_Vector(left,top,z);
        int index = ShaderUtils.bindAndGetTextureIndex(ShaderManager.uiShaderConfig,ti.textureHandle);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(0).put(0).put(0).put(0);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(ti.maxX).put(ti.minY).put(index).put(0).put(0).put(0).put(0);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(0).put(0).put(0).put(0);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(ti.minX).put(ti.maxY).put(index).put(0).put(0).put(0).put(0);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(0).put(0).put(0).put(0);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(0).put(0).put(0).put(0);
    }


    public static void draw2dImg(Image image, int posX, int posY, float z,int width, int height,Vector4f color,ShaderConfig config) {
        Vao vao = config.getVao();

        TextureInfo ti = image.getTexture();

        float left =( (float)posX)/Constants.WINDOW_WIDTH*2-1f;
        float top=(Constants.WINDOW_HEIGHT- ( (float)posY))/Constants.WINDOW_HEIGHT*2-1f;
        float _height = ( (float)height)/Constants.WINDOW_HEIGHT*2;
        float _width =( (float)width)/Constants.WINDOW_WIDTH*2;
        GL_Vector p1 = new GL_Vector(left,top-_height,z);
        GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
        GL_Vector p3 = new GL_Vector(left+_width,top,z);
        GL_Vector p4 = new GL_Vector(left,top,z);
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(ti.maxX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(ti.minX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
    }

    public static void draw2dImg(ShaderConfig config ,Vao vao, int textureHandler) {
        int index = ShaderUtils.bindAndGetTextureIndex(config,textureHandler);
     /*   float left =-0.5f;
        float width=1;
        float top=0.5f;
        float height=1;
        GL_Vector p1 = new GL_Vector(left,top-_height,z);
        GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
        GL_Vector p3 = new GL_Vector(left+_width,top,z);
        GL_Vector p4 = new GL_Vector(left,top,z);*/

        vao.getVertices().put(-1f).put(-1f).put(0f).put(0f);
        vao.getVertices().put(1f).put(-1f).put(1f).put(0f);
        vao.getVertices().put(1f).put(1f).put(1f).put(1f);
        vao.getVertices().put(-1f).put(1f).put(0f).put(1f);
        vao.getVertices().put(-1f).put(-1f).put(0f).put(0f);
        vao.getVertices().put(1f).put(1f).put(1f).put(1f);
    }

    /**
     * 带颜色的绘制图片
     * @param image
     * @param posX
     * @param posY
     * @param z
     * @param width
     * @param height
     * @param color
     */
    public static void draw2dImg(Image image, int posX, int posY, float z,int width, int height,Vector4f color) {
        Vao vao = ShaderManager.uiShaderConfig.getVao();

        TextureInfo ti = image.getTexture();
        if(ti.getSplitx()!=null){
            ti.draw2dImg(posX,posY,z,width,height);
            return;
        }

        float left =( (float)posX)/Constants.WINDOW_WIDTH*2-1f;
        float top=(Constants.WINDOW_HEIGHT- ( (float)posY))/Constants.WINDOW_HEIGHT*2-1f;
        float _height = ( (float)height)/Constants.WINDOW_HEIGHT*2;
        float _width =( (float)width)/Constants.WINDOW_WIDTH*2;
        GL_Vector p1 = new GL_Vector(left,top-_height,z);
        GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
        GL_Vector p3 = new GL_Vector(left+_width,top,z);
        GL_Vector p4 = new GL_Vector(left,top,z);
        int index = ShaderUtils.bindAndGetTextureIndex(ShaderManager.uiShaderConfig,ti.textureHandle);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(ti.maxX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(ti.minX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
    }




    public static void draw2dColor(Vector4f color, int posX, int posY,float z, int width, int height) {
        Vao vao =ShaderManager.uiShaderConfig.getVao();
        float left =( (float)posX)/Constants.WINDOW_WIDTH*2-1f;
        float top=(Constants.WINDOW_HEIGHT- ( (float)posY))/Constants.WINDOW_HEIGHT*2-1f;
        float _height = ( (float)height)/Constants.WINDOW_HEIGHT*2;
        float _width =( (float)width)/Constants.WINDOW_WIDTH*2;
        GL_Vector p1 = new GL_Vector(left,top-_height,z);
        GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
        GL_Vector p3 = new GL_Vector(left+_width,top,z);
        GL_Vector p4 = new GL_Vector(left,top,z);
        if(color==null){
            LogUtil.err("color can't be null");
        }
      /*  if(vao.getVertices().position() >= vao.getVertices().capacity()-60 ){
            FloatBuffer buffer = vao.getVertices();
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(vao.getVertices().capacity()+10000);
            newBuffer.put(buffer);
            vao.setVertices(BufferUtils.createFloatBuffer(vao.getVertices().capacity()+10000));
        }*/
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(0).put(0).put(-1).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(0).put(0).put(-1).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(0).put(0).put(-1).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(0).put(0).put(-1).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(0).put(0).put(-1).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(0).put(0).put(-1).put(color.x).put(color.y).put(color.z).put(color.w);
    }

    /*public static void draw2dBorder(Vector4f color, int posX, int posY, int width, int height) {
        LogUtil.err("this method Abandoned");
        Vao vao =ShaderManager.uiShaderConfig.getVao();

        float left = posX/Constants.WINDOW_WIDTH*2-1f;
        float top=(Constants.WINDOW_HEIGHT- posY)/Constants.WINDOW_HEIGHT*2-1f;


        GL_Vector p1 = new GL_Vector(left,top-height,0);
        GL_Vector p2 = new GL_Vector(left+width,top-height,0);
        GL_Vector p3 = new GL_Vector(left+width,top,0);
        GL_Vector p4 = new GL_Vector(left,top,0);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(color.x).put(color.y).put(color.z);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
    }*/
    public static HashMap<Character,Glyph> glyphMap = null;
    public static HashMap<Character,Glyph> getGlyMap(){
        if(glyphMap==null){
            glyphMap= FontUtil.readGlyph();
        }
        return glyphMap;
    }
    public static void printText(String s, int innerX, int innerY,float z, float fontSize,Vector4f color) {
        int preX=0;
        int preY=0;
        TextureInfo ti = TextureManager.getTextureInfo("zhongwen");
        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if(ch=='\n'){
                preX=0-(int)fontSize;
                preY+=fontSize;
            }else{
                if(i!=0)
                preX+=fontSize;
            }
            if(FontUtil.zhongwenMap==null)
            {LogUtil.err("font load failed");

            }
            Glyph location = FontUtil.zhongwenMap.get(ch);
            float height =ti.getImgHeight();
            float width = ti.getImgWidth();
            if(location!=null){
                ti.minX=location.x/width;
                ti.minY=(height-location.y-location.height)/height;
                ti.maxX=(location.x+location.width)/width;
                ti.maxY=(height-location.y)/height;
                ShaderUtils.draw2dImg(new Image(ti), innerX+preX,innerY+preY,z,(int)fontSize,(int)fontSize,color);
                OpenglUtils.checkGLError();
            }

        }


    }

    public static void draw3dText(String s, GL_Matrix matrix, float fontSize,Vector4f color,ShaderConfig shaderConfig) {
        int preX=0;
        int preY=0;
        fontSize= fontSize/50;
        GL_Vector p1 =new GL_Vector(0,0,0);
        GL_Vector p2 =new GL_Vector(fontSize,0,0);
        GL_Vector p3 =new GL_Vector(fontSize,fontSize,0);
        GL_Vector p4 =new GL_Vector(0,fontSize,0);
        GL_Vector normal =new GL_Vector(0,0,fontSize);

        TextureInfo ti = TextureManager.getTextureInfo("zhongwen");
        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if(ch=='\n'){
                preX=0-(int)fontSize;
                preY+=fontSize;
            }else{
                if(i!=0)
                    preX+=fontSize;
            }
            if(FontUtil.zhongwenMap==null)
            {LogUtil.err("font load failed");

            }
            Glyph location = FontUtil.zhongwenMap.get(ch);
            float height =ti.getImgHeight();
            float width = ti.getImgWidth();
            if(location!=null){
                ti.minX=location.x/width;
                ti.minY=(height-location.y-location.height)/height;
                ti.maxX=(location.x+location.width)/width;
                ti.maxY=(height-location.y)/height;

               // ShaderUtils.draw2dImg(new Image(ti), innerX+preX,innerY+preY,z,(int)fontSize,(int)fontSize,color);
                p1.x+=fontSize;
                p2.x+=fontSize;
                p3.x+=fontSize;
                p4.x+=fontSize;
                ShaderUtils.draw3dImage(p1,p2,p3,p4,matrix,normal,ti,shaderConfig.getVao().getVertices(),shaderConfig);

               // ShaderUtils.draw3dImg(p1,p2,p3,p4,new Image(ti), x,y,z,(int)fontSize,(int)fontSize,color);
                OpenglUtils.checkGLError();
            }

        }


    }

    /*public static void draw3dImg(Image image, int posX, int posY, float z,int width, int height,Vector4f color,ShaderConfig config) {
        Vao vao = config.getVao();

        TextureInfo ti = image.getTexture();

        float left =( (float)posX)/Constants.WINDOW_WIDTH*2-1f;
        float top=(Constants.WINDOW_HEIGHT- ( (float)posY))/Constants.WINDOW_HEIGHT*2-1f;
        float _height = ( (float)height)/Constants.WINDOW_HEIGHT*2;
        float _width =( (float)width)/Constants.WINDOW_WIDTH*2;
        GL_Vector p1 = new GL_Vector(left,top-_height,z);
        GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
        GL_Vector p3 = new GL_Vector(left+_width,top,z);
        GL_Vector p4 = new GL_Vector(left,top,z);
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(ti.maxX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(ti.minX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
        vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(color.x).put(color.y).put(color.z).put(color.w);
    }*/
    public static void printText(String s, int innerX, int innerY,float z, float fontSize,Vector4f color,ShaderConfig  config) {
        int preX=0;
        int preY=0;
        TextureInfo ti = TextureManager.getTextureInfo("zhongwen");
        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if(ch=='\n'){
                preX=0-(int)fontSize;
                preY+=fontSize;
            }else{
                if(i!=0)
                    preX+=fontSize;
            }
            if(FontUtil.zhongwenMap==null)
            {LogUtil.err("font load failed");

            }
            Glyph location = FontUtil.zhongwenMap.get(ch);
            float height =ti.getImgHeight();
            float width = ti.getImgWidth();
            if(location!=null){
                ti.minX=location.x/width;
                ti.minY=(height-location.y-location.height)/height;
                ti.maxX=(location.x+location.width)/width;
                ti.maxY=(height-location.y)/height;
                ShaderUtils.draw2dImg(new Image(ti), innerX+preX,innerY+preY,z,(int)fontSize,(int)fontSize,color,config);
                OpenglUtils.checkGLError();
            }

        }


    }

    /*public static void drawLine(Vector4f color,int startX,int startY,int endX,int endY) {

        float startXF = startX/Constants.WINDOW_WIDTH*2-1f;

        float startYF = (Constants.WINDOW_HEIGHT- startY)/Constants.WINDOW_HEIGHT*2-1f;

        float endXF = endX/Constants.WINDOW_WIDTH*2-1f;

        float endYF = (Constants.WINDOW_HEIGHT- endY)/Constants.WINDOW_HEIGHT*2-1f;


        GL_Vector p1 = new GL_Vector(startXF,startYF,0);
        GL_Vector p2 = new GL_Vector(startXF,startYF+width,0);
        GL_Vector p3 = new GL_Vector(endXF,endYF+width,0);
        GL_Vector p4 = new GL_Vector(endXF,endYF,0);
        twoDColorBuffer.put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        twoDColorBuffer.put(p2.x).put(p2.y).put(p2.z).put(color.x).put(color.y).put(color.z);
        twoDColorBuffer.put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
        twoDColorBuffer.put(p4.x).put(p4.y).put(p4.z).put(color.x).put(color.y).put(color.z);
        twoDColorBuffer.put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        twoDColorBuffer.put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
    }*/
 //public static  Vao twodColorVao=new Vao();
/*    public static Vao update2dColorVao(){
            if(twodColorVao==null){
                twodColorVao= new Vao();
                getFinal2dColorVao(twodColorVao);
            }
        return twodColorVao;
    }*/

   //public static int textureIndex=0;
  //  public static HashMap<Integer,Integer> textureIndexMap=new HashMap();
    /*public static Integer bindAndGetTextureIndex(int textureHandle) {
        glUseProgram(get2DImgConfig().getProgramId());
        Integer index = textureIndexMap.get(textureHandle);
        if(index==null){
            index =textureIndex;
            textureIndex++;
            textureIndexMap.put(textureHandle,index);
            //uniform texture
            if(index==0){

                GL13.glActiveTexture(GL13.GL_TEXTURE0);

                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(glGetUniformLocation(get2DImgConfig().getProgramId(), "ourTexture0"), 0);
                OpenglUtils.checkGLError();
            }else if(index==1){
                GL13.glActiveTexture(GL13.GL_TEXTURE1);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(glGetUniformLocation(get2DImgConfig().getProgramId(), "ourTexture1"), 1);
                OpenglUtils.checkGLError();
            }else if(index==2){
                GL13.glActiveTexture(GL13.GL_TEXTURE2);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(glGetUniformLocation(get2DImgConfig().getProgramId(), "ourTexture2"), 2);   OpenglUtils.checkGLError();
            }else if(index==3){
                GL13.glActiveTexture(GL13.GL_TEXTURE3);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(glGetUniformLocation(get2DImgConfig().getProgramId(), "ourTexture3"), 3);   OpenglUtils.checkGLError();
            }

        }
        return index;

    }*/

    public static void main(String args[]){


GL_Vector tt=GL_Matrix.multiply(ortho,new GL_Vector(-10,-10,10));
      System.out.println(tt  );
    }
    public static HashMap<Integer ,Integer> texHandle2glTexLocMap =new HashMap<Integer ,Integer>();
    public static int globalActiveIndex=0;

    /**
     * 一般绑定到使用一个纹理经过这几个步骤  先申请一个纹理glGenTextures 在告诉系统我要使用这个纹理给他赋予一些基本的信息 GL11.glBindTexture
     *  GL11.glTexImage2D 赋予图片信息
     *  然后  GL11.glTexParameteri 赋予 延伸的属性
     *  在绘制的时候 我们先激活一个纹理顺序
     *  GL13.glActiveTexture(GL13.GL_TEXTURE0);

     再把这个shader激活纹理顺序和纹理id绑定 glBindTexture(GL_TEXTURE_2D, textureHandle);

     * 从静态变量texHandle2glTexLocMap(textureHandle (生成的纹理id)==>映射 绑定激活的GL_TEXTUREN 序号 TexLoc(glActiveTexture的纹理位置GL13.GL_TEXTURE__N__))
     * @param textureHandle
     * @return
     */
    public  static Integer getActiveTextureLoc(int textureHandle){

        if(textureHandle==0){
            LogUtil.err("texutureid is 0");
        }
        Integer activeTextureLoc = texHandle2glTexLocMap.get(textureHandle);
        if(activeTextureLoc==null){

            activeTextureLoc = globalActiveIndex;
            texHandle2glTexLocMap.put(textureHandle,activeTextureLoc);
            globalActiveIndex++;
            if(activeTextureLoc==0){
                GL13.glActiveTexture(GL13.GL_TEXTURE0);

                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }else if(activeTextureLoc==1){
                GL13.glActiveTexture(GL13.GL_TEXTURE1);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }else if(activeTextureLoc==2){
                GL13.glActiveTexture(GL13.GL_TEXTURE2);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }else if(activeTextureLoc==3){
                GL13.glActiveTexture(GL13.GL_TEXTURE3);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }
            else if(activeTextureLoc==4){
                GL13.glActiveTexture(GL13.GL_TEXTURE4);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            } else if(activeTextureLoc==5){
                GL13.glActiveTexture(GL13.GL_TEXTURE5);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }else if(activeTextureLoc==6){
                GL13.glActiveTexture(GL13.GL_TEXTURE6);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }
            else if(activeTextureLoc==7){
                GL13.glActiveTexture(GL13.GL_TEXTURE7);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }
            else if(activeTextureLoc==8){
                GL13.glActiveTexture(GL13.GL_TEXTURE8);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }else if(activeTextureLoc==9){
                GL13.glActiveTexture(GL13.GL_TEXTURE9);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }
            else if(activeTextureLoc==10){
                GL13.glActiveTexture(GL13.GL_TEXTURE10);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            } else if(activeTextureLoc==11){
                GL13.glActiveTexture(GL13.GL_TEXTURE11);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            } else if(activeTextureLoc==12){
                GL13.glActiveTexture(GL13.GL_TEXTURE12);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            } else if(activeTextureLoc==13){
                GL13.glActiveTexture(GL13.GL_TEXTURE13);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            } else if(activeTextureLoc==14){
                GL13.glActiveTexture(GL13.GL_TEXTURE14);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            } else if(activeTextureLoc==15){
                GL13.glActiveTexture(GL13.GL_TEXTURE15);
                glBindTexture(GL_TEXTURE_2D, textureHandle);

            }else{
                LogUtil.err("超出纹理数量");
            }


        }
        return activeTextureLoc;
    }


    public static void bindTextureFromAnotherConfig(ShaderConfig to,ShaderConfig from){
        HashMap<Integer,Integer > map = from.getSampleLocMap();
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            int glTexLoc = entry.getKey();
            int sampleLoc=entry.getValue();
            if(sampleLoc==0){

                glUniform1i(to.getTexture0Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==1){

                glUniform1i(to.getTexture1Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==2){

                glUniform1i(to.getTexture2Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==3){

                glUniform1i(to.getTexture3Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }
            else if(sampleLoc==4){
                glUniform1i(to.getTexture4Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            } else if(sampleLoc==5){
                glUniform1i(to.getTexture5Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==6){
                glUniform1i(to.getTexture6Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==7){
                glUniform1i(to.getTexture7Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==8){
                glUniform1i(to.getTexture8Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else{
                LogUtil.err("超出预想的范围了");
            }


        }
    }
    /**
     * 绑定纹理到全局的 纹理index 再把纹理index 对应到本地的texture0~9
     * @param config
     * @param textureHandle
     * @return 在shader frag 程序中的 纹理顺序
     */
    public static Integer bindAndGetTextureIndex(ShaderConfig config,int textureHandle) {
        if(config.getProgramId()==0){
            LogUtil.err("no programid");
        }
        if(textureHandle==0){
            LogUtil.err("textureId is  0 ");
        }
        //先根据纹理id 得到 绑定的纹理顺序值  如果已经绑定了 就返回这个顺序, 如果没绑定就自增并返回
        Integer glTexLoc = getActiveTextureLoc(textureHandle);//从

        //根据纹理顺序从config的sample 中返回值 没有就让textureINdex自增并绑定到这个值上
        Integer sampleLoc = config.getSampleLocMap().get(glTexLoc);


       /* if(index == null){
            index = config.getTextureIndexMap().get(textureHandle);
            if(index !=null){

            }
        }*/
        //texturenhande ===> ative texture====>uniformloc
        if(sampleLoc==null){

            glUseProgram(config.getProgramId());
            OpenglUtils.checkGLError();
           //index =textureIndex;
            sampleLoc=config.textureIndex;
            if(sampleLoc>10){
                LogUtil.err("no it's un possible");
            }
            config.getSampleLocMap().put(glTexLoc,sampleLoc);
           config.textureIndex++;
            //textureIndex++;
            //uniform texture
            if(sampleLoc==0){

                glUniform1i(config.getTexture0Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==1){

                glUniform1i(config.getTexture1Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==2){

                glUniform1i(config.getTexture2Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==3){

                glUniform1i(config.getTexture3Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }
            else if(sampleLoc==4){
                glUniform1i(config.getTexture4Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            } else if(sampleLoc==5){
                glUniform1i(config.getTexture5Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==6){
                glUniform1i(config.getTexture6Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==7){
                glUniform1i(config.getTexture7Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else if(sampleLoc==8){
                glUniform1i(config.getTexture8Loc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else{
                LogUtil.err("超出预想的范围了");
            }
            glUseProgram(0);
        }
        return sampleLoc;

    }

   /* public static Integer bindDepth(ShaderConfig config,int textureHandle) {
        if(config.getProgramId()==0){
            LogUtil.err("no programid");
        }
        if(textureHandle==0){
            LogUtil.err("textureId is  0 ");
        }
        //先根据纹理id 得到 绑定的纹理顺序值  如果已经绑定了 就返回这个顺序, 如果没绑定就自增并返回
        Integer glTexLoc = getActiveTextureLoc(textureHandle);//从

        //根据纹理顺序从config的sample 中返回值 没有就让textureINdex自增并绑定到这个值上
        Integer sampleLoc = config.getSampleLocMap().get(glTexLoc);

       *//* if(index == null){
            index = config.getTextureIndexMap().get(textureHandle);
            if(index !=null){

            }
        }*//*
        //texturenhande ===> ative texture====>uniformloc
        if(sampleLoc==null){

            glUseProgram(config.getProgramId());
            OpenglUtils.checkGLError();
            //index =textureIndex;
            sampleLoc=99;
            config.getSampleLocMap().put(glTexLoc,sampleLoc);
            config.textureIndex++;
            //textureIndex++;
            //uniform texture
            if(sampleLoc==99){

                glUniform1i(config.getDepthMapLoc(), glTexLoc);
                OpenglUtils.checkGLError();
            }else{
                LogUtil.err("超出预想的范围了");
            }
            glUseProgram(0);
        }
        return sampleLoc;

    }*/

    public static Integer bindAndGetTextureIndex123(ShaderConfig config,int textureHandle) {
        if(config.getProgramId()==0){
            LogUtil.err("no programid");
        }

        String name = TextureManager.textureIndex2NameMap.get(textureHandle);
        Integer index = texHandle2glTexLocMap.get(textureHandle);

        if(index==null){

            glUseProgram(config.getProgramId());
            OpenglUtils.checkGLError();
            index =globalActiveIndex;

            texHandle2glTexLocMap.put(textureHandle,index);
            LogUtil.println(name+":"+textureHandle+":"+index);
            globalActiveIndex++;

            //uniform texture
            if(index==0){
                GL13.glActiveTexture(GL13.GL_TEXTURE0);

                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture0Loc(), 0);
                OpenglUtils.checkGLError();
            }else if(index==1){
                GL13.glActiveTexture(GL13.GL_TEXTURE1);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture1Loc(), 1);
                OpenglUtils.checkGLError();
            }else if(index==2){
                GL13.glActiveTexture(GL13.GL_TEXTURE2);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture2Loc(), 2);
                OpenglUtils.checkGLError();
            }else if(index==3){
                GL13.glActiveTexture(GL13.GL_TEXTURE3);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture3Loc(), 3);
                OpenglUtils.checkGLError();
            }
            else if(index==4){
                GL13.glActiveTexture(GL13.GL_TEXTURE4);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture4Loc(), 4);
                OpenglUtils.checkGLError();
            } else if(index==5){
                GL13.glActiveTexture(GL13.GL_TEXTURE5);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture5Loc(), 5);
                OpenglUtils.checkGLError();
            }else if(index==6){
                GL13.glActiveTexture(GL13.GL_TEXTURE6);
                glBindTexture(GL_TEXTURE_2D, textureHandle);
                glUniform1i(config.getTexture6Loc(), 6);
                OpenglUtils.checkGLError();
            }
            glUseProgram(0);
        }
        return index;

    }

    /**
     * this function is for terrain draw
     * @param config
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @param normal
     * @param ti
     */
 public static void draw3dImage(ShaderConfig config,Vao vao ,GL_Vector p1,GL_Vector p2,GL_Vector p3,GL_Vector p4,GL_Vector normal,TextureInfo ti){
        //ti=TextureManager.getTextureInfo("mantle");

        if(ti==null){
            LogUtil.err("ti should not be null");
        }
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
           /* if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            if(ti.color!=null){
                veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p1
                veticesBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p2
                veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p3
                veticesBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p4
                veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p1
                veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p3

            }else {
                veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);//p1
                veticesBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY).put(0).put(index);//p2
                veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);//p3
                veticesBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY).put(0).put(index);//p4
                veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);//p1
                veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);//p3
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void draw3dImage(ShaderConfig config,Vao vao ,float x,float y,float z,GL_Vector p1,GL_Vector p2,GL_Vector p3,GL_Vector p4,GL_Vector normal,TextureInfo ti){
        //ti=TextureManager.getTextureInfo("mantle");

        if(ti==null){
            LogUtil.err("ti should not be null");
        }
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
           /* if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            if(ti.color!=null){
                veticesBuffer.put(x+p1.x).put(y+p1.y).put(z+p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p1
                veticesBuffer.put(x+p2.x).put(y+p2.y).put(z+p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p2
                veticesBuffer.put(x+p3.x).put(y+p3.y).put(z+p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p3
                veticesBuffer.put(x+p4.x).put(y+p4.y).put(z+p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p4
                veticesBuffer.put(x+p1.x).put(y+p1.y).put(z+p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p1
                veticesBuffer.put(x+p3.x).put(y+p3.y).put(z+p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.color.x).put(ti.color.y).put(ti.color.z).put(-1);//p3

            }else {
                veticesBuffer.put(x+p1.x).put(y+p1.y).put(z+p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);//p1
                veticesBuffer.put(x+p2.x).put(y+p2.y).put(z+p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY).put(0).put(index);//p2
                veticesBuffer.put(x+p3.x).put(y+p3.y).put(z+p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);//p3
                veticesBuffer.put(x+p4.x).put(y+p4.y).put(z+p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY).put(0).put(index);//p4
                veticesBuffer.put(x+p1.x).put(y+p1.y).put(z+p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);//p1
                veticesBuffer.put(x+p3.x).put(y+p3.y).put(z+p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);//p3
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void draw3dImage(ShaderConfig config,Vao vao ,float[][] vertices,float[][] texoords,float[][] normal,int[] faces,TextureInfo ti,int x,int y,int z){
        //ti=TextureManager.getTextureInfo("mantle");

        if(ti==null){
            LogUtil.err("ti should not be null");
        }
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
           /* if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/

            for(int i=0;i<faces.length;i++){

                veticesBuffer.put(vertices[faces[i]][0]+x).
                        put(vertices[faces[i]][1]+y).
                        put(vertices[faces[i]][2]+z).
                        put(normal[faces[i]][0]).
                        put(normal[faces[i]][1]).
                        put(normal[faces[i]][2]).
                        put(texoords[faces[i]][0]).
                        put(texoords[faces[i]][1]).
                        put(0).
                        put(index);//p1

            }
            veticesBuffer.put(vertices[faces[0]][0]+x).
                    put(vertices[faces[0]][1]+y).
                    put(vertices[faces[0]][2]+z).
                    put(normal[faces[0]][0]).
                    put(normal[faces[0]][1]).
                    put(normal[faces[0]][2]).
                    put(texoords[faces[0]][0]).
                    put(texoords[faces[0]][1]).
                    put(0).
                    put(index);//p1
            if(faces.length<2){
                LogUtil.err("lenght too short");
            }
            veticesBuffer.put(vertices[faces[2]][0]+x).
                    put(vertices[faces[2]][1]+y).
                    put(vertices[faces[2]][2]+z).
                    put(normal[faces[2]][0]).
                    put(normal[faces[2]][1]).
                    put(normal[faces[2]][2]).
                    put(texoords[faces[2]][0]).
                    put(texoords[faces[2]][1]).
                    put(0).
                    put(index);//p1
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }



    public static void draw3dImage(ShaderConfig config,Vao vao ,ShapeFace shapeFace ,TextureInfo ti,int x,int y,int z){
        //ti=TextureManager.getTextureInfo("mantle");

        if(ti==null){
            LogUtil.err("ti should not be null");
        }
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
           /* if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            int[][] faces = shapeFace.faces;
            float[][] vertices = shapeFace.vertices;
            float[][] normals = shapeFace.normals;
            float[][] texoords = shapeFace.getTexcoords();
            for(int i=0;i<faces.length;i++){
                for(int j=0;j<6;j++){

                    LogUtil.println("x:"+(vertices[faces[i][j]][0]+x)+"y:"+(vertices[faces[i][j]][1]+y)
                            +"z:"+(vertices[faces[i][j]][2]+z));
                    veticesBuffer.put(vertices[faces[i][j]][0]+x).
                            put(vertices[faces[i][j]][1]+y).
                            put(vertices[faces[i][j]][2]+z).
                            put(normals[faces[i][j]][0]).
                            put(normals[faces[i][j]][1]).
                            put(normals[faces[i][j]][2]).
                            put(texoords[faces[i][j]][0]).
                            put(texoords[faces[i][j]][1]).
                            put(0).
                            put(index);//p1
                }


            }

            if(faces.length<2){
                LogUtil.err("lenght too short");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }



    public static void draw3dImage(ShaderConfig config,Vao vao ,ShapeFace shapeFace ,TextureInfo ti,int x,int y,int z,GL_Matrix matrix){
        //ti=TextureManager.getTextureInfo("mantle");

        if(ti==null){
            LogUtil.err("ti should not be null");
        }
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
         /*   if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            int[][] faces = shapeFace.faces;
            float[][] vertices = shapeFace.vertices;
            float[][] normals = shapeFace.normals;
            float[][] texoords = shapeFace.getTexcoords();
            float vertx=0;
            float verty=0;
            float vertz=0;
            GL_Vector vector =new GL_Vector();
            for(int i=0;i<faces.length;i++){
                for(int j=0;j<6;j++){
                    vertx=vertices[faces[i][j]][0];
                    verty=vertices[faces[i][j]][0];
                    vector.set(vertices[faces[i][j]][0],vertices[faces[i][j]][1],vertices[faces[i][j]][2]);
                    GL_Vector newVec = GL_Matrix.multiply(matrix,vector);
                  /*  LogUtil.println("x:"+(vertices[faces[i][j]][0]+x)+"y:"+(vertices[faces[i][j]][1]+y)
                            +"z:"+(vertices[faces[i][j]][2]+z));*/
                    veticesBuffer.put(newVec.x+x).
                            put(newVec.y+y).
                            put(newVec.z+z).
                            put(normals[faces[i][j]][0]).
                            put(normals[faces[i][j]][1]).
                            put(normals[faces[i][j]][2]).
                            put(texoords[faces[i][j]][0]).
                            put(texoords[faces[i][j]][1]).
                            put(0).
                            put(index);//p1
                }


            }

            if(faces.length<2){
                LogUtil.err("lenght too short");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public static void draw3dImage(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, TextureInfo ti, FloatBufferWrap floatBuffer, ShaderConfig config){
        //ti= TextureManager.getTextureInfo("mantle");
        try {
            int index = ShaderUtils.bindAndGetTextureIndex(config, ti.textureHandle);

            p1 = GL_Matrix.multiply(matrix, p1);

            p2 = GL_Matrix.multiply(matrix, p2);
            p3 = GL_Matrix.multiply(matrix, p3);
            p4 = GL_Matrix.multiply(matrix, p4);
            normal = GL_Matrix.multiply(matrix, normal);
            floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);
            floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY).put(0).put(index);
            floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);
            floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY).put(0).put(index);
            floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);
            floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }


    public static void draw3dImage(ShaderConfig config,Vao vao , GL_Matrix matrix,GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4,  GL_Vector normal, TextureInfo ti ){
        //ti= TextureManager.getTextureInfo("mantle");
        FloatBufferWrap floatBuffer = vao.getVertices();
        try {
            int index = ShaderUtils.bindAndGetTextureIndex(config, ti.textureHandle);

            p1 = GL_Matrix.multiply(matrix, p1);

            p2 = GL_Matrix.multiply(matrix, p2);
            p3 = GL_Matrix.multiply(matrix, p3);
            p4 = GL_Matrix.multiply(matrix, p4);
            normal = GL_Matrix.multiply(matrix, normal);
            floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);
            floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY).put(0).put(index);
            floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);
            floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY).put(0).put(index);
            floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY).put(0).put(index);
            floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY).put(0).put(index);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    public static void draw3dImage(ShaderConfig config,Vao vao ,float[][] vertices,float[][] texoords,float[][] normal,int[] faces,TextureInfo ti,int x,int y,int z,GL_Matrix transMatrix){
        //ti=TextureManager.getTextureInfo("mantle");

        if(ti==null){
            LogUtil.err("ti should not be null");
        }
        int index = ShaderUtils.bindAndGetTextureIndex(config,ti.textureHandle);
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
           /* if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/

            for(int i=0;i<faces.length;i++){
                GL_Vector vector =new GL_Vector(vertices[faces[i]][0],vertices[faces[i]][1],vertices[faces[i]][2]);
                vector = GL_Matrix.multiply(transMatrix, vector);
                veticesBuffer.put(vector.x+x).
                        put(vector.y+y).
                        put(vector.z+z).
                        put(normal[faces[i]][0]).
                        put(normal[faces[i]][1]).
                        put(normal[faces[i]][2]).
                        put(texoords[faces[i]][0]).
                        put(texoords[faces[i]][1]).
                        put(0).
                        put(index);//p1

            }
            GL_Vector vector =new GL_Vector(vertices[faces[0]][0],vertices[faces[0]][1],vertices[faces[0]][2]);
            vector = GL_Matrix.multiply(transMatrix, vector);
            veticesBuffer.put(vector.x+x).
                    put(vector.y+y).
                    put(vector.z+z).
                    put(normal[faces[0]][0]).
                    put(normal[faces[0]][1]).
                    put(normal[faces[0]][2]).
                    put(texoords[faces[0]][0]).
                    put(texoords[faces[0]][1]).
                    put(0).
                    put(index);//p1
            if(faces.length<2){
                LogUtil.err("lenght too short");
            }
            vector =new GL_Vector(vertices[faces[2]][0],vertices[faces[2]][1],vertices[faces[2]][2]);
            vector = GL_Matrix.multiply(transMatrix, vector);
            veticesBuffer.put(vector.x+x).
                    put(vector.y+y).
                    put(vector.z+z).
                    put(normal[faces[2]][0]).
                    put(normal[faces[2]][1]).
                    put(normal[faces[2]][2]).
                    put(texoords[faces[2]][0]).
                    put(texoords[faces[2]][1]).
                    put(0).
                    put(index);//p1
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }



    public static void draw3dColorBox(ShaderConfig config, Vao vao, GL_Vector[] points, GL_Vector color, float alpha) {

        FloatBufferWrap veticesBuffer = vao.getVertices();
        int[][] faceAry = BoxModel.facesAry;
        GL_Vector[] dirAry = BoxModel.dirAry;
        for(int i=0;i<6;i++){
            int[] faceAry2 = faceAry[i];

            veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(points[faceAry2[1]].x).put(points[faceAry2[1]].y).put(points[faceAry2[1]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(points[faceAry2[3]].x).put(points[faceAry2[3]].y).put(points[faceAry2[3]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

            veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

        }
    }
    
    
    public static void draw3dColorBox(ShaderConfig config, Vao vao,GL_Matrix matrix, GL_Vector[] points, GL_Vector color, float alpha) {
        GL_Vector[] newPoints = new GL_Vector[8];
        for(int i =0 ;i<8;i++){
            newPoints[i]=GL_Matrix.multiply(matrix,points[i]);
        }
       
        FloatBufferWrap veticesBuffer = vao.getVertices();
        int[][] faceAry = BoxModel.facesAry;
        GL_Vector[] dirAry = BoxModel.dirAry;
        for(int i=0;i<6;i++){
            int[] faceAry2 = faceAry[i];

            veticesBuffer.put(newPoints[faceAry2[0]].x).put(newPoints[faceAry2[0]].y).put(newPoints[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(newPoints[faceAry2[1]].x).put(newPoints[faceAry2[1]].y).put(newPoints[faceAry2[1]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(newPoints[faceAry2[2]].x).put(newPoints[faceAry2[2]].y).put(newPoints[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(newPoints[faceAry2[3]].x).put(newPoints[faceAry2[3]].y).put(newPoints[faceAry2[3]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

            veticesBuffer.put(newPoints[faceAry2[0]].x).put(newPoints[faceAry2[0]].y).put(newPoints[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
            veticesBuffer.put(newPoints[faceAry2[2]].x).put(newPoints[faceAry2[2]].y).put(newPoints[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

        }
    }

    /**
     * this function is for terrain draw
     * @param config
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @param normal
     */
    public static void draw3dColor(ShaderConfig config,Vao vao ,GL_Vector p1,GL_Vector p2,GL_Vector p3,GL_Vector p4,GL_Vector normal,GL_Vector color){
        //ti=TextureManager.getTextureInfo("mantle");

        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
           /* if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p1
            veticesBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p2
            veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p3
            veticesBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p4
            veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p1
            veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p3

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void draw3dColorTriangle(ShaderConfig config,Vao vao ,GL_Vector p1,GL_Vector p2,GL_Vector p3,GL_Vector normal,GL_Vector color){
        //ti=TextureManager.getTextureInfo("mantle");

        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();

            veticesBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p1
            veticesBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p2
            veticesBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);//p3


        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void draw3dColorTriangle(ShaderConfig config,Vao vao ,
            float x1,float y1,float z1,
                                           float x2,float y2,float z2,
                                           float x3,float y3,float z3
            ,GL_Vector normal,float rf,float gf,float bf){

        //ti=TextureManager.getTextureInfo("mantle");

        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();

            veticesBuffer.put(x1).put(y1).put(z1).put(normal.x).put(normal.y).put(normal.z).put(rf).put(gf).put(bf).put(-1);//p1
            veticesBuffer.put(x2).put(y2).put(z2).put(normal.x).put(normal.y).put(normal.z).put(rf).put(gf).put(bf).put(-1);//p2
            veticesBuffer.put(x3).put(y3).put(z3).put(normal.x).put(normal.y).put(normal.z).put(rf).put(gf).put(bf).put(-1);//p3


        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void draw3dColorBox(ShaderConfig config,Vao vao ,float x,float y,float z ,GL_Vector color,float width,float height,float thick,float alpha){
        //ti=TextureManager.getTextureInfo("mantle");

        if(width<0){
            x+=width;width=-width;
        }
        if(height<0){
            y+=height;height=-height;
        }
        if(thick<0){
            z+=thick;thick=-thick;
        }
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
         /*   if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            GL_Vector[] dirAry = BoxModel.dirAry;
            int[][] faceAry = BoxModel.facesAry;
            GL_Vector[] points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
            for(int i=0;i<6;i++){
                int[] faceAry2 = faceAry[i];

                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[1]].x).put(points[faceAry2[1]].y).put(points[faceAry2[1]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[3]].x).put(points[faceAry2[3]].y).put(points[faceAry2[3]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

            }

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void draw3dColorBox(ShaderConfig config,Vao vao ,float x,float y,float z ,GL_Vector color,float width,float height,float thick,float alpha,boolean top,boolean bottom,boolean left ,boolean right,boolean front ,boolean back){
        //ti=TextureManager.getTextureInfo("mantle");

        if(width<0){
            x+=width;width=-width;
        }
        if(height<0){
            y+=height;height=-height;
        }
        if(thick<0){
            z+=thick;thick=-thick;
        }
        boolean[] faceExists = new boolean[]{top,bottom,left,right,front,back };
        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
         /*   if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            GL_Vector[] dirAry = BoxModel.dirAry;
            int[][] faceAry = BoxModel.facesAry;
            GL_Vector[] points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
            for(int i=0;i<6;i++){

                if(!faceExists[i]){
                    continue;
                }
                int[] faceAry2 = faceAry[i];

                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[1]].x).put(points[faceAry2[1]].y).put(points[faceAry2[1]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[3]].x).put(points[faceAry2[3]].y).put(points[faceAry2[3]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

            }

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public static void draw3dColorBox(ShaderConfig config,Vao vao ,float x,float y,float z ,GL_Vector[] points,GL_Vector[] dirAry,float red,float green,float blue,float alpha){
        //ti=TextureManager.getTextureInfo("mantle");


        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();


            int[][] faceAry = BoxModel.facesAry;

            for(int i=0;i<6;i++){
                int[] faceAry2 = faceAry[i];


                veticesBuffer.put(x+points[faceAry2[0]].x).put(y+points[faceAry2[0]].y).put(z+points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[1]].x).put(y+points[faceAry2[1]].y).put(z+points[faceAry2[1]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[2]].x).put(y+points[faceAry2[2]].y).put(z+points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[3]].x).put(y+points[faceAry2[3]].y).put(z+points[faceAry2[3]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1

                veticesBuffer.put(x+points[faceAry2[0]].x).put(y+points[faceAry2[0]].y).put(z+points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[2]].x).put(y+points[faceAry2[2]].y).put(z+points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1

            }

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void draw3dColorBox(ShaderConfig config,Vao vao ,float x,float y,float z ,GL_Vector[] points,GL_Vector[] dirAry,float red,float green,float blue,float alpha,boolean[] faceExistAry){
        //ti=TextureManager.getTextureInfo("mantle");


        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();


            int[][] faceAry = BoxModel.facesAry;

            for(int i=0;i<6;i++){
                if(!faceExistAry[i])
                    continue;
                int[] faceAry2 = faceAry[i];


                veticesBuffer.put(x+points[faceAry2[0]].x).put(y+points[faceAry2[0]].y).put(z+points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[1]].x).put(y+points[faceAry2[1]].y).put(z+points[faceAry2[1]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[2]].x).put(y+points[faceAry2[2]].y).put(z+points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[3]].x).put(y+points[faceAry2[3]].y).put(z+points[faceAry2[3]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1

                veticesBuffer.put(x+points[faceAry2[0]].x).put(y+points[faceAry2[0]].y).put(z+points[faceAry2[0]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(x+points[faceAry2[2]].x).put(y+points[faceAry2[2]].y).put(z+points[faceAry2[2]].z).put(dirAry[i].x).put(dirAry[i].y).put(dirAry[i].z).put(red/*+0.03f*(i-3)*/).put(green/*+0.03f*(i-3)*/).put(blue/*+0.03f*(i-3)*/).put(-alpha);//p1

            }

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }



    public static void draw3dColorBoxLine(ShaderConfig config,Vao vao ,float x,float y,float z ,float width,float height,float thick){
        //ti=TextureManager.getTextureInfo("mantle");


        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
         /*   if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
          /*  GL_Vector[] dirAry = BoxModel.dirAry;
            int[][] faceAry = BoxModel.facesAry;
            GL_Vector[] points = BoxModel.getSmallPoint(x,y,z,width,height,thick);*/

            veticesBuffer.put(x).put(y).put(z);//p1
            veticesBuffer.put(x+width).put(y).put(z);//p1

            veticesBuffer.put(x+width).put(y).put(z);//p1
            veticesBuffer.put(x+width).put(y).put(z+thick);//p1

            veticesBuffer.put(x+width).put(y).put(z+thick);//p1
            veticesBuffer.put(x).put(y).put(z+thick);//p1

            veticesBuffer.put(x).put(y).put(z+thick);//p1
            veticesBuffer.put(x).put(y).put(z);//p1


            veticesBuffer.put(x).put(y+height).put(z);//p1
            veticesBuffer.put(x+width).put(y+height).put(z);//p1

            veticesBuffer.put(x+width).put(y+height).put(z);//p1
            veticesBuffer.put(x+width).put(y+height).put(z+thick);//p1

            veticesBuffer.put(x+width).put(y+height).put(z+thick);//p1
            veticesBuffer.put(x).put(y+height).put(z+thick);//p1

            veticesBuffer.put(x).put(y+height).put(z+thick);//p1
            veticesBuffer.put(x).put(y+height).put(z);//p1



            veticesBuffer.put(x).put(y+height).put(z);//p1
            veticesBuffer.put(x).put(y).put(z);//p1

            veticesBuffer.put(x+width).put(y+height).put(z);//p1
            veticesBuffer.put(x+width).put(y).put(z);//p1

            veticesBuffer.put(x+width).put(y+height).put(z+thick);//p1

            veticesBuffer.put(x+width).put(y).put(z+thick);//p1

            veticesBuffer.put(x).put(y+height).put(z+thick);//p1
            veticesBuffer.put(x).put(y).put(z+thick);//p1


          /*  veticesBuffer.put(x).put(y+1).put(z);//p1
            veticesBuffer.put(x+1).put(y+1).put(z);//p1
            veticesBuffer.put(x+1).put(y+1).put(z+1);//p1
            veticesBuffer.put(x).put(y+1).put(z+1);//p1*/
/*



            for(int i=0;i<6;i++){
                int[] faceAry2 = faceAry[i];


                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z);//p1

                veticesBuffer.put(points[faceAry2[1]].x).put(points[faceAry2[1]].y).put(points[faceAry2[1]].z);//p1

                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z);//p1
               veticesBuffer.put(points[faceAry2[3]].x).put(points[faceAry2[3]].y).put(points[faceAry2[3]].z);//p1


            }*/

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public static void draw3dColorBox(ShaderConfig config,Vao vao ,float x,float y,float z ,GL_Vector color,float size,float alpha){
        //ti=TextureManager.getTextureInfo("mantle");


        try {
            FloatBufferWrap veticesBuffer = vao.getVertices();
            /*if (veticesBuffer.position() > veticesBuffer.limit() -100) {
                LogUtil.println("overflow");
                vao.expand();
                veticesBuffer=vao.getVertices();
            }*/
            GL_Vector[] dirAry = BoxModel.dirAry;
            int[][] faceAry = BoxModel.facesAry;
            GL_Vector[] points = BoxModel.getSmallPoint(x,y,z,size);
            for(int i=0;i<6;i++){
                int[] faceAry2 = faceAry[i];


                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[0].x).put(dirAry[0].y).put(dirAry[0].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[1]].x).put(points[faceAry2[1]].y).put(points[faceAry2[1]].z).put(dirAry[1].x).put(dirAry[1].y).put(dirAry[1].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[2].x).put(dirAry[2].y).put(dirAry[2].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[3]].x).put(points[faceAry2[3]].y).put(points[faceAry2[3]].z).put(dirAry[3].x).put(dirAry[3].y).put(dirAry[3].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

                veticesBuffer.put(points[faceAry2[0]].x).put(points[faceAry2[0]].y).put(points[faceAry2[0]].z).put(dirAry[0].x).put(dirAry[0].y).put(dirAry[0].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1
                veticesBuffer.put(points[faceAry2[2]].x).put(points[faceAry2[2]].y).put(points[faceAry2[2]].z).put(dirAry[2].x).put(dirAry[2].y).put(dirAry[2].z).put(color.x/*+0.03f*(i-3)*/).put(color.y/*+0.03f*(i-3)*/).put(color.z/*+0.03f*(i-3)*/).put(-alpha);//p1

            }

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public static void draw3dColor(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, GL_Vector color, FloatBufferWrap floatBuffer,ShaderConfig config){
        //ti= TextureManager.getTextureInfo("mantle");

      /*  if(floatBuffer.position()>floatBuffer.limit()-100){
            FloatBuffer  newfloatBuffer=BufferUtils.createFloatBuffer(floatBuffer.limit()+10000);
            newfloatBuffer.put(floatBuffer);
            config.getVao().setVertices(newfloatBuffer);
            floatBuffer = newfloatBuffer;
        }*/
        p1 =GL_Matrix.multiply(matrix,p1);

        p2 =GL_Matrix.multiply(matrix,p2);
        p3 =GL_Matrix.multiply(matrix,p3);
        p4 =GL_Matrix.multiply(matrix,p4);
        normal=GL_Matrix.multiply(matrix,normal);
        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
        floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
        floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
      floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(color.x).put(color.y).put(color.z).put(-1);
    }


    public static void draw3dColorSimple(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4,  GL_Vector normal, GL_Vector color, FloatBufferWrap floatBuffer,ShaderConfig config){
        //ti= TextureManager.getTextureInfo("mantle");



        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
    }

  public static void draw3dColorSimpleReverse(GL_Vector p4, GL_Vector p3, GL_Vector p2, GL_Vector p1,  GL_Vector normal, GL_Vector color, FloatBufferWrap floatBuffer,ShaderConfig config){
        //ti= TextureManager.getTextureInfo("mantle");



        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(color.x).put(color.y).put(color.z);
      floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(color.x).put(color.y).put(color.z);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(color.x).put(color.y).put(color.z);
    }

    public static void createVao123(ShaderConfig config ,Vao vao,int[] attris){
        int position = vao.getVertices().position();
      /*  if(vao.getVertices().position()==0){

        }*/
        if(position==0)
            return;



        if(vao.getVaoId()>0){


            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
           // glBindVertexArray(vao.getVaoId());
            //设置顶点组的数目
            vao.setPoints(vao.getVertices().position()/config.getParamTotalLen());
            glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
            vao.getVertices().rewind();
            vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);
           //glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data

        }else {

            int length =0;
            for(int i=0;i<attris.length;i++){
                length += attris[i];
            }
            //设置参数总长度
            config.setParamTotalLen(length);
            //设置参数长度数组
            config.setParamLenAry(attris);
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            //  glBindVertexArray(vao.getVaoId());
            int VboId = glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
            //}
            //绑定vao
            glBindVertexArray(vao.getVaoId());
            OpenglUtils.checkGLError();
            //create vbo
            //顶点 vbo
            //create vbo 创建vbo  vertex buffer objects
            //创建顶点数组


            vao.setPoints(vao.getVertices().buffers.get(0).position() / length);

            //LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
            vao.getVertices().rewind();
            // vao.setVertices(twoDImgBuffer);
            glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo

            vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);//put data
            //create ebo
            // float width = 1;
            OpenglUtils.checkGLError();
            // System.out.println("float.size:" + FlFLOAToat.SIZE);
            //图片位置 //0代表再glsl里的变量的location位置值.

            int sum = 0;

            for (int i = 0; i < attris.length; i++) {
                glVertexAttribPointer(i, attris[i], GL_FLOAT, false, length * 4, sum * 4);

                Util.checkGLError();
                glEnableVertexAttribArray(i);
                Util.checkGLError();
                sum += attris[i];
            }
        }
        // System.out.println("float.size:" + FlFLOAToat.SIZE);



    }


    public static void freshVao(ShaderConfig config ,Vao vao){
        int position = vao.getVertices().position();

     /*   if(position==0)
            return;*/



        if(vao.getVaoId()>0){


            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
            // glBindVertexArray(vao.getVaoId());
            //设置顶点组的数目
            vao.setPoints(vao.getVertices().position()/config.getParamTotalLen());
            glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
           // vao.getVertices().rewind();
            vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);
            //glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data

        }else {

          LogUtil.err("dangerous");
        }
        // System.out.println("float.size:" + FlFLOAToat.SIZE);



    }


    /**
     *
     * @param attri
     * createVbo(int[]{3});
     * Float
     */
    public static void testVboCreate(){
        testVboId =  createVbo(new int[]{3});
        testVboDotbufferWrap=new FloatBufferWrap(150);
        testVboDotbufferWrap.put(0).put(0).put(0).put(1).put(1).put(1).put(2).put(2).put(2);

    }
    static FloatBufferWrap testVboDotbufferWrap  ;
    static int testVboId;

    public static void drawLine(GL_Vector startPoint,GL_Vector endPoint){
        ShaderManager.lineShaderConfig.getVao().getVertices().put(startPoint.x).put(startPoint.y).put(startPoint.z);
        ShaderManager.lineShaderConfig.getVao().getVertices().put(endPoint.x).put(endPoint.y).put(endPoint.z);
    }

    public static void finalDrawLine(ShaderConfig config ,Vao vao){
        glUseProgram(config.getProgramId());
        OpenglUtils.checkGLError();
        // glUniform3f(shaderConfig.getObejctColorLoc(), backgroundColor.x, backgroundColor.y, backgroundColor.z);
        // OpenglUtils.checkGLError();
        assert vao.getVaoId()>0;
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        assert vao.getPoints()>0;
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo

       // vao.getVertices().glBufferData(GL_ARRAY_BUFFER,GL_STATIC_DRAW);
//        for(int i=0;i<=vao.getVertices().listLimit;i++){
//            int Points =vao.getVertices().buffers.get(i).position()/config.getParamTotalLen();
//            vao.setPoints(Points);
//            vao.getVertices().buffers.get(i).rewind();
//            //vao.getVertices().rewind();
//            glBufferData(GL_ARRAY_BUFFER,vao.getVertices().buffers.get(i), GL_STATIC_DRAW);
//            // vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);
//            glDrawArrays(GL_LINES,0, vao.getPoints());
//        }

        glDrawArrays(GL_LINES,0, vao.getPoints());

        //config.getVao().getVertices().rewind();

        OpenglUtils.checkGLError();
        glBindVertexArray(0);
        OpenglUtils.checkGLError();






    }
    public static void testDrawVbo(){

        drawBufferWrap(testVboDotbufferWrap,3,testVboId);
    }
    public static int   createVbo(int[] attri){

        int VboId = glGenBuffers();//create vbo

        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo


        //vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);

        GL15.glBufferData(GL_ARRAY_BUFFER,  ByteBuffer.allocateDirect(150), GL_STATIC_DRAW);//put data
        int sum = 0;
        int paramLen=0;
        for(int i=0;i<attri.length;i++){
            paramLen += attri[i];
        }
        for (int i = 0; i <attri.length; i++) {
           ;

            Util.checkGLError();
            glEnableVertexAttribArray(i);
            glVertexAttribPointer(i, attri[i], GL_FLOAT, false, paramLen * 4, sum * 4);
            Util.checkGLError();
            sum += attri[i];
        }
        OpenglUtils.checkGLError();



        //in use

    return VboId;
    }
    public static void drawBufferWrap(FloatBufferWrap floatBufferWrap,int paramLen,int vboId){
        for(int i=0;i<floatBufferWrap.listLimit;i++){//遍历所有的floatbufferWrap
            int position = floatBufferWrap.buffers.get(i).position();
            int points = position/paramLen; //计算points的个数
            finalDrawVbo(vboId,points);
        }
    }
   public void drawDotInVbo(FloatBuffer floatBufferWrap){

       GL15.glBufferData(GL_ARRAY_BUFFER,  floatBufferWrap, GL_STATIC_DRAW);//put data
    }
    public static void finalDrawVbo(int vboId,int points){

        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glDrawArrays(GL_POINTS, 0, points);
        glDisableVertexAttribArray(0);
    }
    public static void initVao(ShaderConfig config ,Vao vao){
        if(vao.getVaoId()>0){

            LogUtil.err("dangerous");

        }else {

            int length =0;
            for(int i=0;i<config.getParamLenAry().length;i++){
                length += config.getParamLenAry()[i];
            }
            //设置参数总长度

            //设置参数长度数组

            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            //  glBindVertexArray(vao.getVaoId());
            int VboId = glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
            //}
            //绑定vao
            glBindVertexArray(vao.getVaoId());
            OpenglUtils.checkGLError();
            //create vbo
            //顶点 vbo
            //create vbo 创建vbo  vertex buffer objects
            //创建顶点数组

            vao.setPoints(10);

            //LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
            vao.getVertices().rewind();
            // vao.setVertices(twoDImgBuffer);
            glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo

            GL15.glBufferData(GL_ARRAY_BUFFER, 3000, GL_STATIC_DRAW);//put data

            // Step3: 填充具体的数据
          /*  glBufferSubData(GL_ARRAY_BUFFER, 0, sizeof(vertPos), vertPos);
            glBufferSubData(GL_ARRAY_BUFFER, sizeof(vertPos), sizeof(vertColor), vertColor);*/



            vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);//put data
            //create ebo
            // float width = 1;
            OpenglUtils.checkGLError();


            int sum = 0;

            for (int i = 0; i <config.getParamLenAry().length; i++) {
                glVertexAttribPointer(i, config.getParamLenAry()[i], GL_FLOAT, false, config.getParamTotalLen() * 4, sum * 4);

                Util.checkGLError();
                glEnableVertexAttribArray(i);
                Util.checkGLError();
                sum += config.getParamLenAry()[i];
            }
        }
    }

   static  float near_plane = 1f, far_plane =150.5f;
    public static GL_Matrix ortho = GL_Matrix.ortho(-130.0f, 130.0f, -130.0f, 130.0f, near_plane, far_plane);


    public static void drawLine(float x, float y, float z, float x1, float y1, float z1) {

        ShaderManager.lineShaderConfig.getVao().getVertices().put(x).put(y).put(z);
        ShaderManager.lineShaderConfig.getVao().getVertices().put(x1).put(y1).put(z1);
    }
}


