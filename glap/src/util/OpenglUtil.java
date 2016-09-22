package util;

import cola.machine.game.myblocks.log.LogUtil;
import glmodel.GL_Vector;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class OpenglUtil {

    public static void glFillRect(int leftX,int leftY,int width,int height,int lineWidth,byte[] borderColor,byte color[]){
        //GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
       // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(lineWidth);

        GL11.glColor3ub(borderColor[0],borderColor[1],borderColor[2]);

       GL11. glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);

        //GL11.glColor3ub(borderColor[0],borderColor[1],borderColor[2]);
        glRect(leftX,leftY,leftX+width,leftY+height,GL11.GL_LINE_LOOP);
        GL11.glColor3ub(color[0],color[1],color[2]);
       // GL11.glRectf(-25.0f, 25.0f, 25.0f, -25.0f);
        //GL11.glRecti(leftX+lineWidth/2,leftY-lineWidth/2,leftX+width-lineWidth/2,leftY+height+lineWidth/2);
       glRect(leftX+lineWidth/2,leftY+lineWidth/2,leftX+width-lineWidth/2,leftY+height-lineWidth/2,GL11.GL_POLYGON);
        GL11.glFlush ();
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
        LogUtil.println("viewport3"+viewport.get(3));
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
}
