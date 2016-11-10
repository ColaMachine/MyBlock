package com.dozenx.game.opengl.util;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.util.FileUtil;
import glapp.GLApp;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;
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

    public static int CreateProgram(String vertexPath,String fragPath)throws Exception {
        int vertShaderId =CreateVertShaders(vertexPath);
        int fragShaderId = CreateFragShaders(fragPath);
        int programId = CreateProgram(vertShaderId,fragShaderId);
        return programId;

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
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
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
}
