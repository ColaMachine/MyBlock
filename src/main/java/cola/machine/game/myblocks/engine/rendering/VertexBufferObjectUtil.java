package cola.machine.game.myblocks.engine.rendering;

import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBufferObjectUtil {
	private VertexBufferObjectUtil(){
		
	}
	public static void bufferVboData(int id,FloatBuffer buffer ,int drawMode){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,id);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawMode);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
	}
	
	public static void bufferVboData(int id,IntBuffer buffer ,int drawMode){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,id);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, drawMode);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
	}
}
