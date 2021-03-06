package cola.machine.game.myblocks.rendering.world;

import check.CrashCheck;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.engine.subsystem.lwjgl.GLBufferPool;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.math.Vector2i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.cameras.Camera;
//import cola.machine.game.myblocks.rendering.cameras.OrthographicCamera;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkConstants;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ChunkssCmd;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.MathUtil;
import com.dozenx.util.math.Rect2i;
import com.google.common.collect.Lists;
import core.log.LogUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Vector3f;
import java.nio.IntBuffer;
import java.util.*;

public class WorldRendererLwjgl implements WorldRenderer {
    private final Config config;
    // DoubleBuffer eqr ;
    private static final Logger logger = LoggerFactory.getLogger(WorldRendererLwjgl.class);
    private final Skysphere skysphere;
    private static final int SHADOW_FRUSTUM_BOUNDS = 500;
    private ChunkProvider chunkProvider;
    int groundTextureHandle = 0;
    private WorldProvider worldProvider;
    private LocalPlayerSystem localPlayerSystem;
    private Player player;
    private int chunkPosX = -1;// 当前的方块
    private int chunkPosZ = -1;
    private static final int MAX_CHUNKS = ViewDistance.MEGA.getChunkDistance() * ViewDistance.MEGA.getChunkDistance();
    private final List<Chunk> chunksInProximity = Lists.newArrayListWithCapacity(MAX_CHUNKS);
    private final PriorityQueue<ChunkImpl> renderQueueChunksOpaque = new PriorityQueue<>(256,
            new ChunkFrontToBackComparator());
    /* SHADOW MAPPING */
//    private Camera lightCamera = new OrthographicCamera(-SHADOW_FRUSTUM_BOUNDS, SHADOW_FRUSTUM_BOUNDS,
//            SHADOW_FRUSTUM_BOUNDS, -SHADOW_FRUSTUM_BOUNDS);

    public WorldRendererLwjgl(WorldProvider worldProvider, ChunkProvider chunkProvider,
            LocalPlayerSystem localPlayerSystem, GLBufferPool bufferPool) {
        this(worldProvider, chunkProvider, localPlayerSystem, bufferPool, null);
    }

    public WorldRendererLwjgl(WorldProvider worldProvider, ChunkProvider chunkProvider,
            LocalPlayerSystem localPlayerSystem, GLBufferPool bufferPool, Player player) {

        // eqr=
        // ByteBuffer.allocateDirect(4 *
        // GLApp.SIZE_DOUBLE).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        // eqr.put(0.0f).put(35.0f).put(0.0f).put(0.0f);
        this.config = CoreRegistry.get(Config.class);
        if (config == null) {
            LogUtil.println("config 不能为Null ");
            System.exit(0);
        }
        // this.activeCamera = camera1;
        this.chunkProvider = chunkProvider;
        this.worldProvider = worldProvider;
        this.localPlayerSystem = localPlayerSystem;

        skysphere = new Skysphere(this);
        CoreRegistry.put(CrashCheck.class, new CrashCheck(/* player, */chunksInProximity));
        CoreRegistry.put(WorldRendererLwjgl.class, this);
        this.player = player;
        this.setup();
        this.client = CoreRegistry.get(Client.class);
    }

    Client client;

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

    public void render(ShaderConfig config) {
        this.updateChunksInProximity(false);
        // ShaderManager.terrainShaderConfig.getVao().getVertices().clear();
        for (Chunk chunk : chunksInProximity) {
            if (chunk != null)
                ((ChunkImpl) chunk).update();// 重新构建build
        }
        this.update();
        for (Chunk chunk : chunksInProximity) {

            if (chunk != null/* && chunk.getPos().x==0 &&chunk.getPos().z==0*/ )
                ((ChunkImpl) chunk).render(config);// 调用draw方法

        }
    }

    public void update(){
        while (client.chunks.size() > 0 && client.chunks.peek() != null) {

            ChunkRequestCmd cmd = (ChunkRequestCmd) client.chunks.poll();
            Chunk chunk = chunkProvider.getChunk(new Vector3i(cmd.x, 0, cmd.z));// 因为拉远距离了之后
            // 会导致相机的位置其实是在很远的位置
            // 改为只其实还没有chunk加载
            // 所以最好是从任务的头顶开始出发
            if (chunk == null) {
                continue;
            }
            // 如果这是一个大于1*1*1体积的block
            //LogUtil.println("worldRenderLwjgl"+cmd.cx+":"+cmd.cy+":"+cmd.cz);
            chunk.setBlock(cmd.cx, cmd.cy, cmd.cz, cmd.blockType);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            chunk.setBlockStatus(cmd.cx, cmd.cy, cmd.cz, cmd.dir);
          /*  BaseBlock block = (BaseBlock) chunk.getBlock(cmd.cx, cmd.cy, cmd.cz);
            if (block != null && block instanceof ColorGroup) {
                block.dir = cmd.dir;
            }*/
            chunk.setNeedUpdate(true);
            if (cmd.cy > 20) {
                LogUtil.println("" + cmd.cy);
            }
            // chunkList.add(chunk);

        }
        // 这里的工作较给chunk的needupdate标志位来完成
        /*
         * for(int i=chunkList.size()-1;i>=0;i--){ chunkList.get(i).build();
         * chunkList.remove(i); }
         */

        while (client.chunkAlls.size() > 0 && client.chunkAlls.peek() != null) {
            ChunkssCmd cmd = (ChunkssCmd) client.chunkAlls.poll();
            List<Integer> list = cmd.list;
            Chunk chunk;
            Map<Vector2i, Chunk> map = new HashMap<>();
            for (int i = 0; i < list.size() / 4; i++) {

                int x = list.get(i * 4);
                int y = list.get(i * 4 + 1);
                int z = list.get(i * 4 + 2);
                int blockValue = list.get(i * 4 + 3);

                int chunkX = MathUtil.getBelongChunkInt(x);
                int chunkZ = MathUtil.getBelongChunkInt(z);
                int blockX = MathUtil.floor(x) - chunkX * 16;
                int blockY = MathUtil.floor(y);
                int blockZ = MathUtil.floor(z) - chunkZ * 16;
                chunk = map.get(new Vector2i(chunkX, chunkZ));
                if (chunk == null) {
                    chunk = chunkProvider.getChunk(new Vector3i(chunkX, 0, chunkZ));// 因为拉远距离了之后
                    // 会导致相机的位置其实是在很远的位置
                    // 改为只其实还没有chunk加载
                    // 所以最好是从任务的头顶开始出发
                    map.put(new Vector2i(chunkX, chunkZ), chunk);
                }
                chunk.setBlock(blockX, blockY, blockZ, blockValue);

            }

            for (Vector2i vector2i : map.keySet()) {
                Chunk chunNow = map.get(vector2i);
                chunNow.build();
            }

        }
    }
    // List<Chunk> chunkList =new ArrayList<>();
    public void render() {
        OpenglUtils.checkGLError();
        this.update();
        // if(true)return;
        this.updateChunksInProximity(false);
        // ShaderManager.terrainShaderConfig.getVao().getVertices().clear();
        for (Chunk chunk : chunksInProximity) {
            if (chunk != null)
                ((ChunkImpl) chunk).update();// 如果vaoid是空的会重新构建build
                                             // 这里需要给每个chunk一个标志位 如果更新了 就重新构建
        }
        OpenglUtils.checkGLError();
        // 偶尔发生 或者当该用户登录 或者被创建的时候
        // CoreRegistry.get(Client.class);

        // skysphere.update();
        /*
         * try{ Util.checkGLError();}catch (Exception e ){ e.printStackTrace();
         * LogUtil.println(e.getMessage()); throw e; }
         */

        // TextureManager.getTextureInfo("mantle").bind();
        // shadow

        for (Chunk chunk : chunksInProximity) {
            if (!Switcher.SHADER_ENABLE) {
                GL11.glTranslated(chunk.getChunkWorldPosX(), 0, chunk.getChunkWorldPosZ());
            }
            OpenglUtils.checkGLError();
            if (chunk != null)
                ((ChunkImpl) chunk).render();// 调用draw方法

            if (!Switcher.SHADER_ENABLE) {
                GL11.glTranslated(-chunk.getChunkWorldPosX(), 0, -chunk.getChunkWorldPosZ());
            }
        }

        for (Chunk chunk : chunksInProximity) {
            if (!Switcher.SHADER_ENABLE) {
                GL11.glTranslated(chunk.getChunkWorldPosX(), 0, chunk.getChunkWorldPosZ());
            }
            OpenglUtils.checkGLError();
            if (chunk != null)
                ((ChunkImpl) chunk).renderAlpha();

            if (!Switcher.SHADER_ENABLE) {
                GL11.glTranslated(-chunk.getChunkWorldPosX(), 0, -chunk.getChunkWorldPosZ());
            }
        }
        // 编辑器里的方块
      //

        // skysphere.render();
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

            System.out.printf(" newChunkPosX:%d newChunkPosZ %d \n", newChunkPosX, newChunkPosZ);
            System.out.printf(" oldChunkPosX:%d oldChunkPosZ %d \n", chunkPosX, chunkPosZ);
            if (chunksInProximity.size() == 0 || force) {
                chunksInProximity.clear();
                for (int x = -(viewingDistance / 2); x <= viewingDistance / 2; x++) {
                    for (int z = -(viewingDistance / 2); z <= viewingDistance / 2; z++) {
                        Chunk c = chunkProvider.getChunk(newChunkPosX + x, 0, newChunkPosZ + z);
                        if (c != null) {
                            chunksInProximity.add(c);
                        } else {
                            logger.warn("chunk is null");
                            // chunkProvider.
                            chunkProvider.createOrLoadChunk(new Vector3i(newChunkPosX + x, 0, newChunkPosZ + z));
                            chunksCurrentlyPending = true;
                            c = chunkProvider.getChunk(newChunkPosX + x, 0, newChunkPosZ + z);
                            if (c != null)
                                chunksInProximity.add(c);
                        }
                    }
                }
            } else {

                int vd2 = viewingDistance / 2;
                Rect2i oldView = Rect2i.createFromMinAndSize(chunkPosX - vd2, chunkPosZ - vd2, viewingDistance,
                        viewingDistance);
                Rect2i newView = Rect2i.createFromMinAndSize(newChunkPosX - vd2, newChunkPosZ - vd2, viewingDistance,
                        viewingDistance);
                List<Rect2i> removeRects = Rect2i.difference(oldView, newView);
                for (Rect2i r : removeRects) {
                    for (int x = r.minX(); x <= r.maxX(); ++x) {
                        for (int y = r.minY(); y <= r.maxY(); ++y) {
                            System.out.printf("delete chunk  x:%d y:%d z:%d \n", x, 0, y);
                            Chunk c = chunkProvider.getChunk(x, 0, y);
                            // System.out.println("delete chunk x: %d y: %d
                            // ",x,y);
                            if (c != null) {
                                chunksInProximity.remove(c);
                                ((ChunkImpl) c).disposeMesh();
                                chunkProvider.removeChunk(c);

                            }
                        }
                    }
                }

                List<Rect2i> addRects = Rect2i.difference(newView, oldView);
                for (Rect2i r : addRects) {
                    for (int x = r.minX(); x <= r.maxX(); ++x) {
                        for (int y = r.minY(); y <= r.maxY(); ++y) {
                            System.out.printf("add chunk  x:%d y:%d z:%d \n", x, 0, y);
                            Chunk c = chunkProvider.getChunk(x, 0, y);
                            // System.out.println("delete chunk x: %d y: %d
                            // ",x,y);
                            if (c != null) {
                                chunksInProximity.add(c);

                            } else {
                                chunkProvider.createOrLoadChunk(new Vector3i(x, 0, y));
                                chunksCurrentlyPending = true;
                                c = chunkProvider.getChunk(x, 0, y);

                                if (c != null)
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
        return (int) ((player.position.x < 0 ? (player.position.x - ChunkConstants.SIZE_X) : player.position.x)
                / ChunkConstants.SIZE_X);
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
        return (int) ((player.position.z < 0 ? (player.position.z - ChunkConstants.SIZE_Z) : player.position.z)
                / ChunkConstants.SIZE_Z);
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
        Vector3f result = new Vector3f((chunk.getPos().x + 0.5f) * ChunkConstants.SIZE_X, 0,
                (chunk.getPos().z + 0.5f) * ChunkConstants.SIZE_Z);

        Vector3f cameraPos = CoreRegistry.get(WorldRenderer.class).getActiveCamera().getPosition();
        result.x -= cameraPos.x;
        result.z -= cameraPos.z;

        return result.length();
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    private Camera activeCamera;

    private static class ChunkFrontToBackComparator implements Comparator<ChunkImpl> {
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

    public void save() {
        for (int i = 0; i < chunksInProximity.size(); i++) {
            Chunk chunk = chunksInProximity.get(i);
            if (chunk != null) {
                ((ChunkImpl) chunk).save();
            }
        }
    }

    /*
     * private void renderShadowMap(Camera camera) {
     * 
     * glDisable(GL_CULL_FACE);
     * 
     * camera.lookThrough();
     * 
     * while (renderQueueChunksOpaqueShadow.size() > 0) {
     * renderChunk(renderQueueChunksOpaqueShadow.poll(),
     * ChunkMesh.RenderPhase.OPAQUE, camera, ChunkRenderMode.SHADOW_MAP); }
     * 
     * for (RenderSystem renderer : systemManager.iterateRenderSubscribers()) {
     * renderer.renderShadows(); }
     * 
     * glEnable(GL_CULL_FACE);
     * 
     * }
     */
    /*
     * public void lookThrough() { loadProjectionMatrix();
     * glMatrixMode(GL_PROJECTION);
     * GL11.glLoadMatrix(MatrixUtils.matrixToFloatBuffer(getProjectionMatrix()))
     * ; glMatrixMode(GL11.GL_MODELVIEW); loadModelViewMatrix();
     * glMatrixMode(GL11.GL_MODELVIEW);
     * GL11.glLoadMatrix(MatrixUtils.matrixToFloatBuffer(
     * viewMatrixReflectedLeftEye;)); }
     */

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBlock(int x, int y, int z) {

    }
}
