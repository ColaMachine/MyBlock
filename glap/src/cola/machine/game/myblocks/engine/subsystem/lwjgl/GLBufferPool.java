package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import cola.machine.game.myblocks.engine.rendering.VertexBufferObjectUtil;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class GLBufferPool {
	
	private static final int BUFFER_FETCH_SIZE=16;
	private boolean traceBufferUsage;
	private int totalPoolSize;
	
	
	private TIntList pool =new TIntArrayList();
	
	private TIntObjectMap<String > usageTracker;
	
	public GLBufferPool(boolean traceBufferUsage){
		this.traceBufferUsage = traceBufferUsage;
		if(traceBufferUsage){
			usageTracker = new TIntObjectHashMap<>();
		}
	}
	
	public int get(String forUseBy){
		if(pool.isEmpty()){
			IntBuffer buffer =BufferUtils.createIntBuffer(BUFFER_FETCH_SIZE);
			GL15.glGenBuffers(buffer);
			for(int i=0;i<BUFFER_FETCH_SIZE; ++i){
				pool.add(buffer.get(i));
			}
			totalPoolSize += BUFFER_FETCH_SIZE;//means totalPoolSize= BUFFER_FETCH_SIZE
		}
		int result =pool.removeAt(pool.size()-1);
		if(traceBufferUsage){
			usageTracker.put(result,forUseBy);
		}
		return result;
	}
	public void dispose(int buffer){
		if(buffer!=0){
			pool.add(buffer);
			IntBuffer dataBuffer =BufferUtils.createIntBuffer(1);
			dataBuffer.put(0);
			dataBuffer.flip();
			VertexBufferObjectUtil.bufferVboData(buffer,dataBuffer,GL15.GL_STATIC_DRAW);
			dataBuffer.flip();
			if(traceBufferUsage){
				usageTracker.remove(buffer);
			}
		}
	}
	
	public int getActivePoolSize(){
		return totalPoolSize - pool.size();
	}
	
	public String getUsageMap(){
		if(traceBufferUsage){
			return usageTracker.toString();
		}
		return "Tracing disabled ";
	}
}
