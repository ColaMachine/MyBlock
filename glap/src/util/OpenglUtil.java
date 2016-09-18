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
