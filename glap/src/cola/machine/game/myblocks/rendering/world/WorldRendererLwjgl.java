package cola.machine.game.myblocks.rendering.world;

import glapp.GLCamera;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.vecmath.Vector3f;

import math.Rect2i;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.cameras.Camera;
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkBlockIterator;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

import com.google.common.collect.Lists;

public class WorldRendererLwjgl implements WorldRenderer {

    private static final Logger logger = LoggerFactory.getLogger(WorldRendererLwjgl.class);
	private final Skysphere skysphere;
	private ChunkProvider chunkProvider;
	private WorldProvider worldProvider;
	private LocalPlayerSystem localPlayerSystem;
	
	   private int chunkPosX=-1;//当前的方块
    private int chunkPosZ=-1;
	  private static final int MAX_CHUNKS = ViewDistance.MEGA.getChunkDistance() * ViewDistance.MEGA.getChunkDistance();
	  private final List<ChunkImpl> chunksInProximity = Lists.newArrayListWithCapacity(MAX_CHUNKS);
	   private final PriorityQueue<ChunkImpl> renderQueueChunksOpaque = new PriorityQueue<>(256, new ChunkFrontToBackComparator());
	public WorldRendererLwjgl(WorldProvider worldProvider,
			ChunkProvider chunkProvider, LocalPlayerSystem localPlayerSystem, GLCamera camera1) {
		this.activeCamera= camera1;
		this.chunkProvider = chunkProvider;
		this.worldProvider = worldProvider;
		this.localPlayerSystem = localPlayerSystem;
		skysphere = new Skysphere(this);
	}
	public void updateVisibleQuads(){
		
		for(int i=0;i<chunksInProximity.size();i++){
			ChunkImpl chunk = chunksInProximity.get(i);
			ChunkBlockIterator it = chunk.getBlockIterator();
			if(it.next()){
				Block block =it.getBlock();
				
			}
		}
		//Chunk chunk = chunkProvider.getChunk(this.getActiveCamera().getPosition());
		//创建纹理数组
		
		//创建定点数组
		//创建法相数组
		
		//跟新自己
	}
	public boolean updateChunksInProximity(boolean force) {
		int newChunkPosX = calcCamChunkOffsetX();
		int newChunkPosZ = calcCamChunkOffsetZ();
		int viewingDistance = 3;// config.getRendering().getViewDistance().getChunkDistance();
		
		  boolean chunksCurrentlyPending = false;
		  
		  if (chunkPosX != newChunkPosX || chunkPosZ != newChunkPosZ || force ) {
			  if (chunksInProximity.size() == 0 || force ) {
				  chunksInProximity.clear();
				  for (int x = -(viewingDistance / 2); x < viewingDistance / 2; x++) {
					  for (int z = -(viewingDistance / 2); z < viewingDistance / 2; z++) {
						  ChunkImpl c = chunkProvider.getChunk(newChunkPosX + x, 0, newChunkPosZ + z);
						  if (c != null ) {
	                            chunksInProximity.add(c);
	                        } else {
	                        	logger.warn("chunk is null");
	                          // chunkProvider.
	                        }
					  }
				  }
			  }else{

				  int vd2 = viewingDistance / 2;
				  Rect2i oldView = Rect2i.createFromMinAndSize(chunkPosX - vd2, chunkPosZ - vd2, viewingDistance, viewingDistance);
	              Rect2i newView = Rect2i.createFromMinAndSize(newChunkPosX - vd2, newChunkPosZ - vd2, viewingDistance, viewingDistance);
	              List<Rect2i> removeRects = Rect2i.difference(oldView, newView);
	              for (Rect2i r : removeRects) {
	                  for (int x = r.minX(); x <= r.maxX(); ++x) {
	                      for (int y = r.minY(); y <= r.maxY(); ++y) {
	                          ChunkImpl c = chunkProvider.getChunk(x, 0, y);
	                          if (c != null) {
	                              chunksInProximity.remove(c);
	                             // c.disposeMesh();
	                          }
	                      }
	                  }
	              }
	              
	              
	              for (int x = newView.minX(); x <= newView.maxX(); ++x) {
	                  for (int y = newView.minY(); y <= newView.maxY(); ++y) {
	                      ChunkImpl c = chunkProvider.getChunk(x, 0, y);
	                      if (c != null ) {
	                          chunksInProximity.add(c);
	                      } else {
	                          chunksCurrentlyPending = true;
	                      }
	                  }
	              }
			  
			  }
			  
			  chunkPosX = newChunkPosX;
	            chunkPosZ = newChunkPosZ;
	            //pendingChunks = chunksCurrentlyPending;
	            //TODO chunksInProximity 排序 按照由近及远
	            return true;
		  }
		return false;
	}

	private int calcCamChunkOffsetX() {
		return (int) (getActiveCamera().getPosition().x / ChunkConstants.SIZE_X);
	}

	/*public Camera getActiveCamera() {
		return activeCamera;
	}*/

	private int calcCamChunkOffsetZ() {
		return (int) (getActiveCamera().getPosition().z / ChunkConstants.SIZE_Z);
	}
	
	public void renderWorld(Camera camera) {
		//所有非透明方块
		 while (renderQueueChunksOpaque.size() > 0) {
	           // renderChunk(renderQueueChunksOpaque.poll(), ChunkMesh.RenderPhase.OPAQUE, camera, ChunkRenderMode.DEFAULT);
	        }
		//所有透明方块
		
	}
	
  
	 private static float distanceToCamera(ChunkImpl chunk) {
	        Vector3f result = new Vector3f((chunk.getPos().x + 0.5f) * ChunkConstants.SIZE_X, 0, (chunk.getPos().z + 0.5f) * ChunkConstants.SIZE_Z);

	        Vector3f cameraPos = CoreRegistry.get(WorldRenderer.class).getActiveCamera().getPosition();
	        result.x -= cameraPos.x;
	        result.z -= cameraPos.z;

	        return result.length();
	    }
	 public Camera getActiveCamera() {
	        return activeCamera;
	    }
	 
	  private Camera activeCamera;
    private static class ChunkFrontToBackComparator implements Comparator<ChunkImpl>{
    	public int compare(ChunkImpl o1,ChunkImpl o2){
    		double distance =distanceToCamera(o1);
    		double distance2=distanceToCamera(o2);
    		/*if(01==null){
    			return -1;
    			
    		}else if(o2 ==null){
    			return 1;
    		}if(distance =distance2){
    			return 0;
    		}
    		return distatnce2 > distance ?-1:1;*/
			return 0;
    	}

		
    }
}
