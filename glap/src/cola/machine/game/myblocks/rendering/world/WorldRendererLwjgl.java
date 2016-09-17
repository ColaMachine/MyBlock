package cola.machine.game.myblocks.rendering.world;

import check.CrashCheck;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.rendering.cameras.OrthographicCamera;
import glapp.GLApp;
import glapp.GLCamera;

import java.nio.*;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.vecmath.Vector3f;

import glmodel.GL_Vector;
import math.Rect2i;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.engine.subsystem.lwjgl.GLBufferPool;
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
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

import cola.machine.game.myblocks.math.Region3i;
import cola.machine.game.myblocks.math.Vector3i;
import com.google.common.collect.Lists;

import cola.machine.game.myblocks.config.Config;
import util.MathUtil;
import static org.lwjgl.opengl.GL11.*;

public class WorldRendererLwjgl implements WorldRenderer {
    private final Config config;
    //DoubleBuffer eqr ;
	private static final Logger logger = LoggerFactory
			.getLogger(WorldRendererLwjgl.class);
	private final Skysphere skysphere;
    private static final int SHADOW_FRUSTUM_BOUNDS = 500;
	private ChunkProvider chunkProvider;
    int groundTextureHandle = 0;
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
    /* SHADOW MAPPING */
    private Camera lightCamera = new OrthographicCamera(-SHADOW_FRUSTUM_BOUNDS, SHADOW_FRUSTUM_BOUNDS, SHADOW_FRUSTUM_BOUNDS, -SHADOW_FRUSTUM_BOUNDS);
	public WorldRendererLwjgl(WorldProvider worldProvider,
							  ChunkProvider chunkProvider, LocalPlayerSystem localPlayerSystem,
							  GLBufferPool bufferPool) {
		 this( worldProvider,
				 chunkProvider,  localPlayerSystem,
				 bufferPool, null);
	}
	public WorldRendererLwjgl(WorldProvider worldProvider,
			ChunkProvider chunkProvider, LocalPlayerSystem localPlayerSystem,
			GLBufferPool bufferPool,Human player) {

       // eqr=
            //    ByteBuffer.allocateDirect(4 * GLApp.SIZE_DOUBLE).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        //eqr.put(0.0f).put(35.0f).put(0.0f).put(0.0f);
        this.config=CoreRegistry.get(Config.class);
        if(config ==null){
            LogUtil.println("config 不能为Null ");
           System.exit(0);
        }
        //this.activeCamera = camera1;
		this.chunkProvider = chunkProvider;
		this.worldProvider = worldProvider;
		this.localPlayerSystem = localPlayerSystem;
		
		skysphere = new Skysphere(this);
        CoreRegistry.put(CrashCheck.class,new CrashCheck(player,chunksInProximity));
        CoreRegistry.put(WorldRendererLwjgl.class,this);
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
    private IntBuffer TextureIDBuffer = BufferUtils.createIntBuffer(1);

	public void render() {
       // if(true)return;
        this.updateChunksInProximity(false);
        for (ChunkImpl chunk : chunksInProximity) {

            chunk.preRender();

        }

                TextureManager.getTextureInfo("mantle").bind();

        GL11.glEnable(GL11.GL_BLEND);
        for (ChunkImpl chunk : chunksInProximity) {
            GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                    chunk.getChunkWorldPosZ());
            GL11.glBegin(GL11.GL_QUADS);
            chunk.render();
            GL11.glEnd();
            GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                    -chunk.getChunkWorldPosZ());
        }

       /* for (ChunkImpl chunk : chunksInProximity) {
            GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                    chunk.getChunkWorldPosZ());
            GL11.glBegin(GL11.GL_QUADS);
            chunk.renderAlpha();
            GL11.glEnd();
            GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                    -chunk.getChunkWorldPosZ());
        }*/
        GL11.glDisable(GL11.GL_BLEND);
/*
        GL11.glLoadIdentity();

        GL11.glColorMask(false, false, false, false);// forbit the specify color be written to frame buffer

        GL11. glEnable(GL11.GL_STENCIL_TEST);

        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 1);			// 设置蒙板测试总是通过，参考值设为1，掩码值也设为1

        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);		// 设置当深度测试不通过时，保留蒙板中的值不变。如果通过则使用参考值替换蒙板值

        GL11.glDisable(GL11.GL_DEPTH_TEST);				// 禁用深度测试
        for (ChunkImpl chunk : chunksInProximity) {
            GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                    chunk.getChunkWorldPosZ());
            GL11.glBegin(GL11.GL_QUADS);
            chunk.renderAlpha();
            GL11.glEnd();
            GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                    -chunk.getChunkWorldPosZ());
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);						//启用深度测试

        GL11.glColorMask(true, true, true, true);						// 可以绘制颜色

        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 1);					//下面的设置指定当我们绘制时，不改变蒙板缓存区的值

        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
        eqr.position(0);
        GL11.glEnable(GL11.GL_CLIP_PLANE0);						// 使用剪切平面
        GL11.glClipPlane(GL11.GL_CLIP_PLANE0, eqr);					// 设置剪切平面为地面，并设置它的法线为向下

        GL11. glPushMatrix();							// 保存当前的矩阵

        GL11.glScalef(1.0f, -1.0f, 1.0f);


        for (ChunkImpl chunk : chunksInProximity) {
            GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                    chunk.getChunkWorldPosZ());
            GL11.glBegin(GL11.GL_QUADS);
            chunk.render();
            GL11.glEnd();
            GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                    -chunk.getChunkWorldPosZ());
        }
        GL11.glPopMatrix();

        // Pop The Matrix Off The Stack
        GL11.glDisable( GL11.GL_CLIP_PLANE0);							// Disable Clip Plane For Drawing The Floor
        GL11. glDisable( GL11.GL_STENCIL_TEST);							// We Don't Need The Stencil Buffer Any More (Disable)
        GL11. glEnable( GL11.GL_BLEND);									// Enable Blending (Otherwise The Reflected Object Wont Show)
        GL11. glDisable( GL11.GL_LIGHTING);								// Since We Use Blending, We Disable Lighting
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);					// Set Color To White With 80% Alpha
        GL11.glBlendFunc( GL11.GL_SRC_ALPHA,  GL11.GL_ONE_MINUS_SRC_ALPHA);	// Blending Based On Source Alpha And 1 Minus Dest Alpha
        for (ChunkImpl chunk : chunksInProximity) {
            GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                    chunk.getChunkWorldPosZ());
            GL11.glBegin(GL11.GL_QUADS);
            chunk.renderAlpha();
            GL11.glEnd();
            GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                    -chunk.getChunkWorldPosZ());
        }
        								// Draw The Floor To The Screen
        GL11.glEnable( GL11.GL_LIGHTING);								// Enable Lighting
        GL11.glDisable( GL11.GL_BLEND);								// Disable Blending

        for (ChunkImpl chunk : chunksInProximity) {
            GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                    chunk.getChunkWorldPosZ());
            GL11.glBegin(GL11.GL_QUADS);
            chunk.render();
            GL11.glEnd();
            GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                    -chunk.getChunkWorldPosZ());
        }										// Draw The Ball
        GL11. glFlush();*/

		//skysphere.render();
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
		 * System.out.println(chunk.count); worldre
		 */
	}

	public boolean updateChunksInProximity(boolean force) {
		int newChunkPosX = calcPlayerChunkOffsetX();
		int newChunkPosZ = calcPlayerChunkOffsetZ();
		int viewingDistance = config.getRendering().getViewDistance().getChunkDistance();

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

				int vd2 = viewingDistance/2 ;
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
    public void save(){
        for(int i=0;i<chunksInProximity.size();i++){
            ChunkImpl chunk =chunksInProximity.get(i);
            if(chunk!=null){
                chunk.save();
            }
        }
    }


   /* private void renderShadowMap(Camera camera) {

        glDisable(GL_CULL_FACE);

        camera.lookThrough();

        while (renderQueueChunksOpaqueShadow.size() > 0) {
            renderChunk(renderQueueChunksOpaqueShadow.poll(), ChunkMesh.RenderPhase.OPAQUE, camera, ChunkRenderMode.SHADOW_MAP);
        }

        for (RenderSystem renderer : systemManager.iterateRenderSubscribers()) {
            renderer.renderShadows();
        }

        glEnable(GL_CULL_FACE);

    }*/
   /* public void lookThrough() {
        loadProjectionMatrix();
        glMatrixMode(GL_PROJECTION);
        GL11.glLoadMatrix(MatrixUtils.matrixToFloatBuffer(getProjectionMatrix()));
        glMatrixMode(GL11.GL_MODELVIEW);
        loadModelViewMatrix();
        glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadMatrix(MatrixUtils.matrixToFloatBuffer(   viewMatrixReflectedLeftEye;));
    }*/

public void setPlayer(Human human){
	this.player=human;
}
}
