package com.dozenx.game.opengl.util;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import core.log.LogUtil;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.GLApp;
import glapp.GLImage;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Point4f;
import javax.vecmath.Vector2f;

import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
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
        glRect(leftX, leftY, leftX + width, leftY + height, GL11.GL_LINE_LOOP);
       // GL11.glColor3ub(color[0],color[1],color[2]);
       // GL11.glRectf(-25.0f, 25.0f, 25.0f, -25.0f);
        //GL11.glRecti(leftX+lineWidth/2,leftY-lineWidth/2,leftX+width-lineWidth/2,leftY+height+lineWidth/2);
       glRect(leftX + lineWidth / 2, leftY + lineWidth / 2, leftX + width - lineWidth / 2, leftY + height - lineWidth / 2, GL11.GL_POLYGON);
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
         GL11.glVertex3i(minX, maxY, 0);
        //右上角
         GL11.glVertex3i(maxX, maxY, 0);
        //左上角
         GL11.glVertex3i(maxX, minY, 0);
        //结束画线
         GL11.glEnd();

    }
    public static GL_Vector getLookAtDirection2(GL_Vector viewDir ,float cursorX, float cursorY) {
        GL_Vector newV = new GL_Vector();
        newV.copy(viewDir);

        int windowWidth =Constants.WINDOW_WIDTH;
        int windowHeight =Constants.WINDOW_HEIGHT;
        float newX= cursorX-windowWidth/2;
        float newY=cursorY-windowHeight/2;
        //()/x = Math.tan(45/2);

        //(windowWidth/2)/x = Math.tan(0y);
        //newY/x= Math.tan(?);
        //newX/x= Math.tan(?);
        double x = (windowHeight/2)/Math.tan(3.14/4/2);

        //double x = (windowHeight/2)/Math.tan(3.14/4/2);

        //float newXF= newX/(windowWidth/2);
       // float newYF= newY/(windowHeight/2);
        double yDegree = Math.atan(newY/x );
        double xDegree = Math.atan(newX/x);


        GL_Matrix M = GL_Matrix.rotateMatrix(/*(float) Math.toRadians(updownDegree)/5,*/0, (float)- xDegree,
                0);




        //ViewDir.y+=updownDegree/100;
        GL_Vector vd = M.transform(newV);


        //计算俯角
        double xy= Math.sqrt(vd.x*vd.x + vd.z*vd.z);
        double jiaojiao = Math.atan(vd.y/xy);
        jiaojiao+=yDegree;
        vd.y =(float)(Math.tan(jiaojiao)*xy);
        vd.normalize(); //<vector x=-0.5508821 y=0.0 z=-0.83458304> 0.66006864937011
        //-576  869.6  0.6623735050598  // 33.428°
       return vd.normalize();
//        -0.5476552  -0.83405346

	}

    public static GL_Vector getLookAtDirection3(GL_Vector viewDir ,float cursorX, float cursorY) {
        GL_Vector newV = new GL_Vector();
        newV.copy(viewDir);

        int windowWidth =Constants.WINDOW_WIDTH;
        int windowHeight =Constants.WINDOW_HEIGHT;
        float newX= cursorX-windowWidth/2;//换算成中心坐标为0 , 0 的新坐标
        float newY=cursorY-windowHeight/2;
        //()/x = Math.tan(45/2);

        //(windowWidth/2)/x = Math.tan(0y);
        //newY/x= Math.tan(?);
        //newX/x= Math.tan(?);
        double x = (windowHeight/2)/Math.tan(3.14/4/2);//fov /2 时候对象的x轴的距离  也就是 视角的位置到 近端界面的距离

        //double x = (windowHeight/2)/Math.tan(3.14/4/2);

        //float newXF= newX/(windowWidth/2);
        // float newYF= newY/(windowHeight/2);
        double yDegree = Math.atan(newY/x );
        double xDegree = Math.atan(newX/x);
        double ydu = yDegree/3.1415*180;
        double xdu = xDegree/3.1415*180;
        LogUtil.println("角度x"+xdu+"角度y"+ydu);

        //GL_Matrix M = GL_Matrix.rotateMatrix(/*(float) Math.toRadians(updownDegree)/5,*/0, (float)- xDegree/2,
          //      0);

        //计算俯角
        double xy= Math.sqrt(newV.x*newV.x + newV.z*newV.z);
        double jiaojiao = Math.atan(newV.y/xy);
        jiaojiao+=yDegree;

        double xzDegree =Math.atan(newV.z/newV.x);
        newV.z= (float) Math.tan( xzDegree+xDegree)*newV.x;
        xy= Math.sqrt(newV.x*newV.x + newV.z*newV.z);
        newV.y =(float)(Math.tan(jiaojiao)*xy);
        //ViewDir.y+=updownDegree/100;
        // GL_Vector vd = M.transform(newV);
//        vd.normalize();
        return newV.normalize();


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


    public static void main(String args[]){
        GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
        Vector2f dd =wordPositionToXY(projection,new GL_Vector(5,5,-14),new GL_Vector(0.3f,0,0),new GL_Vector(0,0,-1));
            System.out.println(dd.x);
        System.out.println(dd.y);
    }
    public static Vector2f wordPositionToXY(GL_Matrix projection ,GL_Vector position,GL_Vector cameraPosition,GL_Vector viewDir ){

        GL_Matrix view=
                GL_Matrix.LookAt(cameraPosition,viewDir);

        GL_Matrix modal = GL_Matrix.rotateMatrix(0,0,0);
        GL_Matrix step1 = GL_Matrix.multiply(projection,view);
        GL_Matrix step2 = GL_Matrix.multiply(step1,modal);
        Point4f final4f = GL_Matrix.multiply(step2,

                new javax.vecmath.Point4f(position.x,position.y,position.z,1f)
        );

        return new Vector2f(final4f.x/final4f.w/2+0.5f,1-final4f.y/final4f.w/2f-0.5f);
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
