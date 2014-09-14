package cola.machine.game.myblocks.rendering.world;

import glapp.GLApp;
import glapp.GLCamera;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.vecmath.Vector3f;

import math.Rect2i;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.cameras.Camera;
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkBlockIterator;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Vector3i;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

import com.google.common.collect.Lists;

public class WorldRendererLwjgl implements WorldRenderer {

	private static final Logger logger = LoggerFactory
			.getLogger(WorldRendererLwjgl.class);
	private final Skysphere skysphere;
	private ChunkProvider chunkProvider;
	private WorldProvider worldProvider;
	private LocalPlayerSystem localPlayerSystem;
	private Human player;
	private int chunkPosX = -1;// 当前的方块
	private int chunkPosZ = -1;
	private static final int MAX_CHUNKS = ViewDistance.MEGA.getChunkDistance()
			* ViewDistance.MEGA.getChunkDistance();
	private final List<ChunkImpl> chunksInProximity = Lists
			.newArrayListWithCapacity(MAX_CHUNKS);
	private final PriorityQueue<ChunkImpl> renderQueueChunksOpaque = new PriorityQueue<>(
			256, new ChunkFrontToBackComparator());

	public WorldRendererLwjgl(WorldProvider worldProvider,
			ChunkProvider chunkProvider, LocalPlayerSystem localPlayerSystem,
			GLCamera camera1, Human player) {
		this.activeCamera = camera1;
		this.chunkProvider = chunkProvider;
		this.worldProvider = worldProvider;
		this.localPlayerSystem = localPlayerSystem;
		skysphere = new Skysphere(this);
		this.player = player;
		this.setup();
	}

	public void updateVisibleQuads() {

		/*
		 * for(int i=0;i<chunksInProximity.size();i++){ ChunkImpl chunk =
		 * chunksInProximity.get(i); ChunkBlockIterator it =
		 * chunk.getBlockIterator(); if(it.next()){ Block block =it.getBlock();
		 * it.getBlockPos() } }
		 */
		// Chunk chunk =
		// chunkProvider.getChunk(this.getActiveCamera().getPosition());
		// 创建纹理数组

		// 创建定点数组
		// 创建法相数组

		// 跟新自己
	}

	public void render() {
		this.updateChunksInProximity(false);
		for (ChunkImpl chunk : chunksInProximity) {
			
			chunk.preRender();
			
		}
		GL11.glPushMatrix();
		{

			GL11.glBindTexture(GL11.GL_TEXTURE_2D,
					TextureManager.getIcon("soil").textureHandle);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			
		
			for (ChunkImpl chunk : chunksInProximity) {
				GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
						chunk.getChunkWorldPosZ());
				GL11.glBegin(GL11.GL_QUADS);
				chunk.render();
				GL11.glEnd();
				GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
						-chunk.getChunkWorldPosZ());
			}
			
		}
		GL11.glPopMatrix();
		/*
		 * GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		 * 
		 * 
		 * // Bind to the VAO that has all the information about the vertices
		 * GL30.glBindVertexArray(vaoId); GL20.glEnableVertexAttribArray(0);
		 * GL20.glEnableVertexAttribArray(1);
		 * 
		 * // Bind to the index VBO that has all the information about the order
		 * of the vertices GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,
		 * vboiId);
		 * 
		 * // Draw the vertices GL11.glDrawElements(GL11.GL_TRIANGLES,
		 * indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
		 * 
		 * // Put everything back to default (deselect)
		 * GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		 * GL20.glDisableVertexAttribArray(0);
		 * GL20.glDisableVertexAttribArray(1); GL30.glBindVertexArray(0);
		 */

	}

	int vaoId, vbocId, vboId;
	int displayId;

	public void setup() {
		this.updateChunksInProximity(true);

		/*
		 * ChunkImpl chunk=chunkProvider.getChunk(new Vector3i(1,1,1));
		 * displayId = GLApp.beginDisplayList();
		 * 
		 * Block block =new BaseBlock("soil",1,1,1); //block.render();
		 * GL11.glBegin(GL11.GL_QUADS); chunk.build(); GL11.glEnd();
		 * System.out.println(chunk.count); GLApp.endDisplayList();
		 */
	}

	public boolean updateChunksInProximity(boolean force) {
		int newChunkPosX = calcPlayerChunkOffsetX();
		int newChunkPosZ = calcPlayerChunkOffsetZ();
		int viewingDistance = 3;// config.getRendering().getViewDistance().getChunkDistance();

		boolean chunksCurrentlyPending = false;

		if (chunkPosX != newChunkPosX || chunkPosZ != newChunkPosZ || force) {

			System.out.printf(" newChunkPosX:%d newChunkPosZ %d \n",
					newChunkPosX, newChunkPosZ);
			System.out.printf(" oldChunkPosX:%d oldChunkPosZ %d \n", chunkPosX,
					chunkPosZ);
			if (chunksInProximity.size() == 0 || force) {
				chunksInProximity.clear();
				for (int x = -(viewingDistance / 2); x < viewingDistance / 2; x++) {
					for (int z = -(viewingDistance / 2); z < viewingDistance / 2; z++) {
						ChunkImpl c = chunkProvider.getChunk(newChunkPosX + x,
								0, newChunkPosZ + z);
						if (c != null) {
							chunksInProximity.add(c);
						} else {
							logger.warn("chunk is null");
							// chunkProvider.
							chunkProvider.createOrLoadChunk(new Vector3i(x, 0,
									newChunkPosZ + z));
							chunksCurrentlyPending = true;
							c = chunkProvider.getChunk(x, 0, newChunkPosZ + z);
							chunksInProximity.add(c);
						}
					}
				}
			} else {

				int vd2 = viewingDistance / 2;
				Rect2i oldView = Rect2i.createFromMinAndSize(chunkPosX - vd2,
						chunkPosZ - vd2, viewingDistance, viewingDistance);
				Rect2i newView = Rect2i.createFromMinAndSize(
						newChunkPosX - vd2, newChunkPosZ - vd2,
						viewingDistance, viewingDistance);
				List<Rect2i> removeRects = Rect2i.difference(oldView, newView);
				for (Rect2i r : removeRects) {
					for (int x = r.minX(); x <= r.maxX(); ++x) {
						for (int y = r.minY(); y <= r.maxY(); ++y) {
							System.out.printf(
									"delete chunk  x:%d y:%d z:%d \n", x, 0, y);
							ChunkImpl c = chunkProvider.getChunk(x, 0, y);
							// System.out.println("delete chunk x: %d y: %d ",x,y);
							if (c != null) {
								chunksInProximity.remove(c);
								c.disposeMesh();
								chunkProvider.removeChunk(c);

							}
						}
					}
				}

				List<Rect2i> addRects = Rect2i.difference(newView, oldView);
				for (Rect2i r : addRects) {
					for (int x = r.minX(); x <= r.maxX(); ++x) {
						for (int y = r.minY(); y <= r.maxY(); ++y) {
							System.out.printf("add chunk  x:%d y:%d z:%d \n",
									x, 0, y);
							ChunkImpl c = chunkProvider.getChunk(x, 0, y);
							// System.out.println("delete chunk x: %d y: %d ",x,y);
							if (c != null) {
								chunksInProximity.add(c);

							} else {
								chunkProvider.createOrLoadChunk(new Vector3i(x,
										0, y));
								chunksCurrentlyPending = true;
								c = chunkProvider.getChunk(x, 0, y);
								chunksInProximity.add(c);
							}
						}
					}
				}

				/*
				 * for (int x = newView.minX(); x <= newView.maxX(); ++x) { for
				 * (int y = newView.minY(); y <= newView.maxY(); ++y) {
				 * System.out.printf("add chunk  x:%d y:%d z:%d \n",x,0,y);
				 * ChunkImpl c = chunkProvider.getChunk(x, 0, y); if (c != null
				 * ) { chunksInProximity.add(c); } else {
				 * chunkProvider.createOrLoadChunk(new Vector3i(x, 0, y));
				 * chunksCurrentlyPending = true; c = chunkProvider.getChunk(x,
				 * 0, y); chunksInProximity.add(c); } } }
				 */

			}

			chunkPosX = newChunkPosX;
			chunkPosZ = newChunkPosZ;
			// pendingChunks = chunksCurrentlyPending;
			// TODO chunksInProximity 排序 按照由近及远
			return true;
		}
		return false;
	}

	private int calcCamChunkOffsetX() {
		return (int) (getActiveCamera().getPosition().x / ChunkConstants.SIZE_X);
	}

	private int calcPlayerChunkOffsetX() {
		return (int) ((player.Position.x < 0 ? (player.Position.x - ChunkConstants.SIZE_X)
				: player.Position.x) / ChunkConstants.SIZE_X);
	}

	public static void main(String args[]) {
		System.out.println((-31) / 16);
	}

	/*
	 * public Camera getActiveCamera() { return activeCamera; }
	 */

	private int calcCamChunkOffsetZ() {
		return (int) (getActiveCamera().getPosition().z / ChunkConstants.SIZE_Z);
	}

	private int calcPlayerChunkOffsetZ() {
		return (int) ((player.Position.z < 0 ? (player.Position.z - ChunkConstants.SIZE_Z)
				: player.Position.z) / ChunkConstants.SIZE_Z);
	}

	public void renderWorld(Camera camera) {
		// 所有非透明方块
		while (renderQueueChunksOpaque.size() > 0) {
			// renderChunk(renderQueueChunksOpaque.poll(),
			// ChunkMesh.RenderPhase.OPAQUE, camera, ChunkRenderMode.DEFAULT);
		}
		// 所有透明方块

	}

	private static float distanceToCamera(ChunkImpl chunk) {
		Vector3f result = new Vector3f((chunk.getPos().x + 0.5f)
				* ChunkConstants.SIZE_X, 0, (chunk.getPos().z + 0.5f)
				* ChunkConstants.SIZE_Z);

		Vector3f cameraPos = CoreRegistry.get(WorldRenderer.class)
				.getActiveCamera().getPosition();
		result.x -= cameraPos.x;
		result.z -= cameraPos.z;

		return result.length();
	}

	public Camera getActiveCamera() {
		return activeCamera;
	}

	private Camera activeCamera;

	private static class ChunkFrontToBackComparator implements
			Comparator<ChunkImpl> {
		public int compare(ChunkImpl o1, ChunkImpl o2) {
			double distance = distanceToCamera(o1);
			double distance2 = distanceToCamera(o2);
			/*
			 * if(01==null){ return -1;
			 * 
			 * }else if(o2 ==null){ return 1; }if(distance =distance2){ return
			 * 0; } return distatnce2 > distance ?-1:1;
			 */
			return 0;
		}

	}
}
