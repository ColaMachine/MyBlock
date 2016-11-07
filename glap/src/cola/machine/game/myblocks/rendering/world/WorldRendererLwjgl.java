package cola.machine.game.myblocks.rendering.world;

import check.CrashCheck;
import cola.machine.game.myblocks.engine.modes.StartMenuState;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.rendering.cameras.OrthographicCamera;
import cola.machine.game.myblocks.switcher.Switcher;
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
import org.lwjgl.opengl.*;
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
import util.OpenglUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

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
        CoreRegistry.put(CrashCheck.class,new CrashCheck(/*player,*/chunksInProximity));
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
            if(Switcher.SHADER_ENABLE){
                chunk.preRenderShader();
            }else{
                chunk.preRender();
            }


        }
        try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }

		TextureManager.getTextureInfo("mantle").bind();
        if(Switcher.SHADER_ENABLE){
            for (ChunkImpl chunk : chunksInProximity) {
                glUseProgram(StartMenuState.terrainProgramId);
                Util.checkGLError();
                chunk.renderShader();
                glBindVertexArray(0);
                Util.checkGLError();
            }
        }else{
            for (ChunkImpl chunk : chunksInProximity) {
                GL11.glTranslated(chunk.getChunkWorldPosX(), 0,
                chunk.getChunkWorldPosZ());
                //GL11.glBegin(GL11.GL_QUADS);
                 chunk.render();


                 // GL11.glEnd();
                try{
                    Util.checkGLError();}catch (Exception e ){
                    e.printStackTrace();
                    LogUtil.println(e.getMessage());
                    throw e;
                }

                 GL11.glTranslated(-chunk.getChunkWorldPosX(), 0,
                        -chunk.getChunkWorldPosZ());/*try{
                    Util.checkGLError();}catch (Exception e ){
                    e.printStackTrace();
                    LogUtil.println(e.getMessage());
                    throw e;
                }*/
            }
        }
       /* try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/
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
		int viewingDistance =config.getRendering().getViewDistance().getChunkDistance();

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
		return (int) ((player.position.x < 0 ? (player.position.x - ChunkConstants.SIZE_X)
				: player.position.x) / ChunkConstants.SIZE_X);
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
		return (int) ((player.position.z < 0 ? (player.position.z - ChunkConstants.SIZE_Z)
				: player.position.z) / ChunkConstants.SIZE_Z);
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
