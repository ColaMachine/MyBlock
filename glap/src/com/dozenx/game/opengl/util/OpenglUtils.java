package com.dozenx.game.opengl.util;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.util.FileUtil;
import de.matthiasmann.twl.renderer.Texture;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import glapp.GLApp;
import glapp.GLImage;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class OpenglUtils {


    public static void WorldToScreen(GL_Vector worldPoint){
        IntBuffer viewport = ByteBuffer.allocateDirect((Integer.SIZE / 8) * 16)
                .order(ByteOrder.nativeOrder()).asIntBuffer();
        FloatBuffer modelview = ByteBuffer
                .allocateDirect((Float.SIZE / 8) * 16)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        FloatBuffer projection = ByteBuffer
                .allocateDirect((Float.SIZE / 8) * 16)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        FloatBuffer pickingRayBuffer = ByteBuffer
                .allocateDirect((Float.SIZE / 8) * 3)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        FloatBuffer zBuffer = ByteBuffer.allocateDirect((Float.SIZE / 8) * 1)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);




        //   GL11.glReadPixels( (int)(cursorX), (int)(winY), 1, 1, GL11.GL_DEPTH_COMPONENT,GL11. GL_FLOAT,pickingRayBuffer );
        //float  winZ= pickingRayBuffer.get(0);
        // pickingRayBuffer.rewind();
        GLU.gluProject(worldPoint.x, worldPoint.y, worldPoint.z, modelview, projection, viewport,
                pickingRayBuffer);
        GL_Vector nearVector = new GL_Vector(pickingRayBuffer.get(0),
                pickingRayBuffer.get(1), pickingRayBuffer.get(2));

        LogUtil.println("viewport3 x:"+pickingRayBuffer.get(0)+"y:"+pickingRayBuffer.get(1)+"y:"+pickingRayBuffer.get(2));




    }

    public static void glFillRect(int leftX,int leftY,int width,int height,int lineWidth,byte[] borderColor,byte color[]){
        //GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
       // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(lineWidth);

//        GL11.glColor3ub(borderColor[0],borderColor[1],borderColor[2]);

       GL11. glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);

        //GL11.glColor3ub(borderColor[0],borderColor[1],borderColor[2]);
        glRect(leftX,leftY,leftX+width,leftY+height,GL11.GL_LINE_LOOP);
       // GL11.glColor3ub(color[0],color[1],color[2]);
       // GL11.glRectf(-25.0f, 25.0f, 25.0f, -25.0f);
        //GL11.glRecti(leftX+lineWidth/2,leftY-lineWidth/2,leftX+width-lineWidth/2,leftY+height+lineWidth/2);
       glRect(leftX+lineWidth/2,leftY+lineWidth/2,leftX+width-lineWidth/2,leftY+height-lineWidth/2,GL11.GL_POLYGON);
       // GL11.glFlush ();
        GL11. glPolygonMode(GL11.GL_FRONT, GL11.GL_POLYGON);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    public static void //画矩形，传入的是左下角XY坐标和右上角XY坐标
     glRect(int minX,int minY,int maxX,int maxY,int MODE){
        //画封闭曲线
         GL11.glBegin(MODE);
        //左下角
         GL11.glVertex3i(minX,minY,0);
        //右下角
         GL11.glVertex3i(minX,maxY,0);
        //右上角
         GL11.glVertex3i(maxX,maxY,0);
        //左上角
         GL11.glVertex3i(maxX,minY,0);
        //结束画线
         GL11.glEnd();

    }
    public static GL_Vector getLookAtDirection(float cursorX, float cursorY) {
		IntBuffer viewport = ByteBuffer.allocateDirect((Integer.SIZE / 8) * 16)
				.order(ByteOrder.nativeOrder()).asIntBuffer();
		FloatBuffer modelview = ByteBuffer
				.allocateDirect((Float.SIZE / 8) * 16)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		FloatBuffer projection = ByteBuffer
				.allocateDirect((Float.SIZE / 8) * 16)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		FloatBuffer pickingRayBuffer = ByteBuffer
				.allocateDirect((Float.SIZE / 8) * 3)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		FloatBuffer zBuffer = ByteBuffer.allocateDirect((Float.SIZE / 8) * 1)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		float winX = (float) cursorX;
        LogUtil.println("viewport3 x:"+viewport.get(0)+"y:"+viewport.get(1)+"y:"+viewport.get(2));

		float winY = viewport.get(3)-(float) cursorY;
     //   GL11.glReadPixels( (int)(cursorX), (int)(winY), 1, 1, GL11.GL_DEPTH_COMPONENT,GL11. GL_FLOAT,pickingRayBuffer );
       //float  winZ= pickingRayBuffer.get(0);
       // pickingRayBuffer.rewind();
        GLU.gluUnProject(winX, winY, 0, modelview, projection, viewport,
				pickingRayBuffer);
		GL_Vector nearVector = new GL_Vector(pickingRayBuffer.get(0),
				pickingRayBuffer.get(1), pickingRayBuffer.get(2));

		//pickingRayBuffer.rewind();

		GLU.gluUnProject(winX, winY, 1.0f, modelview, projection, viewport,
				pickingRayBuffer);
		GL_Vector farVector = new GL_Vector(pickingRayBuffer.get(0),
				pickingRayBuffer.get(1), pickingRayBuffer.get(2));

 		return farVector.sub(nearVector).normalize();



	}
    public static void initShader(ShaderConfig shaderConfig){

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
    }
    public static void initProModelView(ShaderConfig config){
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
        Util.checkGLError();
        int modelLoc= glGetUniformLocation(config.getProgramId(), "model");
        config.setModelLoc(modelLoc);
        Util.checkGLError();
        int viewLoc= glGetUniformLocation(config.getProgramId(), "view");
        config.setViewLoc(viewLoc);
        Util.checkGLError();
        glUniformMatrix4(modelLoc, false, model.toFloatBuffer());

        // config.setModel(viewLoc);
        Util.checkGLError();
        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
        Util.checkGLError();
        glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );



    }
    public static void initObjectColor(ShaderConfig config ){
        glUseProgram(config.getProgramId());
        int objectColorLoc= glGetUniformLocation(config.getProgramId(), "objectColor");
        config.setObejctColorLoc(objectColorLoc);
        //float[] objectColorAry = new float[]{1,0.5f,0.31f,};
        // FloatBuffer objectColorBbuffer = BufferUtils.createFloatBuffer(3);
        // objectColorBbuffer.put(objectColorAry);objectColorBbuffer.rewind();
        glUniform3f(objectColorLoc,1.0f,0.5f,0.31f);
        // glUniformMatrix4(objectColorLoc, false, objectColorBbuffer);
        Util.checkGLError();
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
        Util.checkGLError();
        config.setLightColorLoc(lightColorLoc);


    }
    public static void createCubeVao(Vao vao){
        //生成vaoid
        if(vao.getVaoId()>0){
            LogUtil.err("vao have been initialized");
        }
        vao.setVaoId( glGenVertexArrays());


        Util.checkGLError();
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        Util.checkGLError();
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

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        vao.setPoints(VerticesArray.length/5);


        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

        //glUseProgram(ProgramId);



    }
    public static int CreateProgram(String vertexPath,String fragPath)throws Exception {
        int vertShaderId =CreateVertShaders(vertexPath);
        int fragShaderId = CreateFragShaders(fragPath);
        int programId = CreateProgram(vertShaderId,fragShaderId);
        return programId;

    }
    public static void shaderBindTexture(ShaderConfig config,int texHandle){

    }

    public static int CreateProgram(int vertexShaderId, int fragmentShaderId){
        int newProgramId = glCreateProgram();
        Util.checkGLError();

        // Attach vertex shader
        glAttachShader(newProgramId, vertexShaderId);
        Util.checkGLError();

        // Attach fragment shader
        glAttachShader(newProgramId, fragmentShaderId);
        Util.checkGLError();

        // We tell the program how the vertex attribute indices will map
        // to named "in" variables in the vertex shader. This must be done
        // before compiling.
        // glBindAttribLocation(ProgramId, POSITION_INDEX, "in_Position");
        // glBindAttribLocation(ProgramId, COLOR_INDEX, "in_Color");

        glLinkProgram(newProgramId);
        Util.checkGLError();

        // Print possible compile errors
        System.out.println("Program linking:");
        printProgramLog(newProgramId);


        //System.out.println("transformLoc:"+transformLoc);
        Util.checkGLError();
        //glUseProgram(ProgramId);
        // Util.checkGLError();
        return newProgramId;


    }

    public static int  CreateVertShaders(String path) throws IOException {

        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+path).toString());
        //创建着色器
        int  newShaderId = glCreateShader(GL_VERTEX_SHADER);
        Util.checkGLError();
        //源码
        glShaderSource(newShaderId, VertexShader);
        Util.checkGLError();
        //编译
        glCompileShader(newShaderId);
        Util.checkGLError();
        //打印日志
        printShaderLog(newShaderId);
        Util.checkGLError();
        return newShaderId;
    }
    public static int CreateFragShaders(String path) throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+path).toString());

        int newFragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        Util.checkGLError();

        glShaderSource(newFragmentShaderId, FragmentShader);
        Util.checkGLError();

        glCompileShader(newFragmentShaderId);
        Util.checkGLError();

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
        Util.checkGLError();

        System.out.println("  Log (length " + logLength + " chars)");
        String log = glGetShaderInfoLog(id, logLength);
        Util.checkGLError();
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
        Util.checkGLError();

        System.out.println("  Log (length " + logLength + " chars)");
        String log = glGetProgramInfoLog(programId, logLength);
        Util.checkGLError();
        for (String line : log.split("\n")) {
            System.out.println("  " + line);
        }
        System.out.println("");
    }

    public static int createVAO(FloatBuffer floatBuffer){floatBuffer.flip();
        int lightVaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(lightVaoId);
        Util.checkGLError();

        CreateLightVBO(floatBuffer);
        glBindVertexArray(0);
        Util.checkGLError();
        return lightVaoId;
    }
    public static void CreateLightVBO(FloatBuffer Vertices){
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL15.GL_STATIC_DRAW);//put data

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8* 4, 0);
        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();
    }

    public static void drawRect(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, float minx, float miny , float maxx, float maxy){

    }

    public static void drawRectWithShader(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, float minx, float miny , float maxx, float maxy){


    }

    public static void drawCubeWithShader(ShaderConfig config,Vao vao){

        glUseProgram(config.getProgramId());
        Util.checkGLError();

        glBindVertexArray(vao.getVaoId());
        Util.checkGLError();
        glDrawArrays(GL_TRIANGLES,0,vao.getPoints());
        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();



    }

    public static void glVertex3fv4rect(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4,  TextureInfo ti,int position){

        switch (position){
            case Constants.TOP:
                GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
                break;
            case Constants.BOTTOM:
                GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
                break;
            case Constants.LEFT:
                GL11.glNormal3f( -1f, 0.0f, 0.0f);
                break;
            case Constants.RIGHT:
                GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
                break;
            case Constants.FRONT:
                GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
                break;
            case Constants.BACK:
                GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
                break;

        }

        GL11.glTexCoord2f(ti.minX, ti.minY);
        glVertex3fv(p1);    // Bottom Left ǰ����
        GL11.glTexCoord2f(ti.maxX, ti.minY);
        glVertex3fv(p2);    // Bottom Right ǰ����
        GL11.glTexCoord2f(ti.maxX, ti.maxY);
        glVertex3fv(p3);    // Top Right ǰ����
        GL11.glTexCoord2f(ti.minX, ti.maxY);
        glVertex3fv(p4);    // Top Left	ǰ����
    }
    public static void glVertex3fv(GL_Vector p){
        GL11.glVertex3f(p.x,p.y,p.z);
    }


    public static void glVertex3fv4rect(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, TextureInfo ti, FloatBuffer floatBuffer){
        p1 =GL_Matrix.multiply(matrix,p1);

        p2 =GL_Matrix.multiply(matrix,p2);
        p3 =GL_Matrix.multiply(matrix,p3);
        p4 =GL_Matrix.multiply(matrix,p4);
        normal=GL_Matrix.multiply(matrix,normal);
        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY);
        floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY);
        floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY);
    }
    public static void glVertex3fv4triangle(GL_Vector p1, GL_Vector p2, GL_Vector p3, GL_Vector p4, GL_Matrix matrix, GL_Vector normal, TextureInfo ti, FloatBuffer floatBuffer){
        p1 =GL_Matrix.multiply(matrix,p1);

        p2 =GL_Matrix.multiply(matrix,p2);
        p3 =GL_Matrix.multiply(matrix,p3);
        p4 =GL_Matrix.multiply(matrix,p4);
        normal=GL_Matrix.multiply(matrix,normal);
        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY);
        floatBuffer.put(p2.x).put(p2.y).put(p2.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.minY);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY);
        floatBuffer.put(p4.x).put(p4.y).put(p4.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.maxY);
        floatBuffer.put(p1.x).put(p1.y).put(p1.z).put(normal.x).put(normal.y).put(normal.z).put(ti.minX).put(ti.minY);
        floatBuffer.put(p3.x).put(p3.y).put(p3.z).put(normal.x).put(normal.y).put(normal.z).put(ti.maxX).put(ti.maxY);
    }

    public static void checkGLError() {
        try{
            Util.checkGLError();
        }catch (Exception e ){
            //e.printStackTrace();
            //LogUtil.println(e.getMessage());
            //throw e;
        }
    }

    public static void setPerspective()
    {
        // select projection matrix (controls perspective)
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(40f, 1, 1f, 1000f);
        // return to modelview matrix
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    static int rotation =0;

    public static void setGlobalLight(){
        GL11.glEnable(GL11.GL_LIGHTING);
        GLApp.setLight(GL11.GL_LIGHT1,
                new float[]{1.0f, 1.0f, 1.0f, 1.0f},   // diffuse color
                new float[]{1.0f, 1.0f, 1.0f, 1.0f},   // ambient
                new float[]{1.0f, 1.0f, 1.0f, 1.0f},   // specular
                new float[]{0, 5, 0, 1});
    }
    public static void renderCubeTest(){

        rotation += 5;
        // gui.update();
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // select model view for subsequent transforms
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // set viewpoint 10 units from origin, looking at origin
        GLU.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);

        // rotate, scale and draw cube
        GL11.glPushMatrix();
        {
            GL11.glRotatef(rotation, 0, 1, 0);
            GL11.glColor4f(0f, .5f, 1f, 1f);
            GLApp.renderCube();
        }
        GL11.glPopMatrix();

        // draw another overlapping cube
        GL11.glPushMatrix();
        {
            GL11.glRotatef(rotation, 1, 1, 1);
            GL11.glColor4f(.7f, .5f, 0f, 1f);
            GLApp.renderCube();
        }
        GL11.glPopMatrix();
    }
    public static ShaderConfig shaderImageConfig ;
    public static int image2DShaderProgram;

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

    public static GLImage makeTexture(String textureImagePath){
        GLImage textureImg=null;
        try {
            textureImg = GLApp.loadImage(PathManager.getInstance().getInstallPath().resolve(textureImagePath).toUri());//
            //Image image=        ImageIO.read(new File(installPath.resolve(textureImagePath).toUri()));
            if (textureImg != null) {
                textureImg.textureHandle = GLApp.makeTexture(textureImg);Util.checkGLError();
                GLApp.makeTextureMipMap(textureImg.textureHandle, textureImg);
                Util.checkGLError();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        return textureImg;
    }

    public static ShaderConfig get2DImageShaderConfig(){
         if(shaderImageConfig==null ){
            shaderImageConfig =new ShaderConfig();
            shaderImageConfig.setVertPath("chapt7/chapt7.vert");
            shaderImageConfig.setFragPath("chapt7/chapt7.frag");
            initShader(shaderImageConfig);

        }
        return shaderImageConfig;
    }
    /*
     * use shader to draw image
     */
    public static void draw2DImageWithShader(ShaderConfig config,Vao vao) {
        /*if(shaderImageConfig==null ){
            shaderImageConfig =new ShaderConfig();
            shaderImageConfig.setVertPath("chapt7/chapt7.vert");
            shaderImageConfig.setFragPath("chapt7/chapt7.frag");
            initShader(shaderImageConfig);

        }*/
       // renderImageShader(shaderImageConfig);
        /*if(image2DShaderProgram==0 ){
            createImage2DShaderProgram();
        }*/


        glUseProgram(config.getProgramId());
        // glUniform4f(0, 0.0f, greenValue, 0.0f, 1.0f);
        //glBindTexture(GL_TEXTURE_2D, config.getTextureHanle());
        glBindVertexArray(vao.getVaoId());
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

        Util.checkGLError();

    }

    public static void createImage2DShaderProgram(){
        try {
            image2DShaderProgram = OpenglUtils.CreateProgram("twodimg/twodimg.vert", "twodimg/twodimg.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
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

    public static void create2dimageVao(Vao vao,float minX,float minY,float maxX,float maxY){
        //生成vaoid
        //create vao
        if(vao.getVaoId()>0){
            LogUtil.err("vao have been initialized");
        }
        vao.setVaoId( glGenVertexArrays());
        Util.checkGLError();
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        Util.checkGLError();

        //create vbo

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        minX= minX/800;
        minY=minY/600;
        maxX=maxX/800;
        maxY=maxY/600;
        float VerticesArray[]= {minX, maxY, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top Right
                minX,minY, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // Bottom Right
                maxX, minY, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom Left
                maxX, maxY, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top Left
        };


       FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty

        vao.setVertices(Vertices);
        int VboId=glGenBuffers();//create vbo
        vao.setVboId(VboId);
        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        //create ebo


        float width = 1;


        int[] indices={
                0,1,3,
                1,2,3
        };
        IntBuffer Indices = BufferUtils.createIntBuffer(indices.length);
        Indices.put(indices);
        Indices.rewind();

        int eboId = glGenBuffers();
        vao.setEboId(eboId);
        Util.checkGLError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Indices,GL_STATIC_DRAW);

        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);

        //颜色
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3 * 4);

        Util.checkGLError();
        //
        glEnableVertexAttribArray(1);
        //纹理位置
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8* 4, 6*4);

        Util.checkGLError();
        glEnableVertexAttribArray(2);

        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

    }

    public static void bind(int textureHandle){

        try {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    textureHandle);

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
