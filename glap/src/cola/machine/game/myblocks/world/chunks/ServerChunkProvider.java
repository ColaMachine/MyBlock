

package cola.machine.game.myblocks.world.chunks;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.math.Vector2i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;
import cola.machine.game.myblocks.world.generator.WorldGenerator;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 本地的方块提供者
 * @author Immortius
 */
public class ServerChunkProvider implements ChunkProvider,GeneratingChunkProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServerChunkProvider.class);

    private ConcurrentMap<Vector2i, ChunkImpl> nearCache = Maps.newConcurrentMap();//旁边缓存

    private StorageManager storageManager;//保存系统

    private final Set<Vector3i> preparingChunks = Sets.newSetFromMap(Maps.<Vector3i, Boolean>newConcurrentMap());
    private WorldGenerator generator;//世界生成机制
    private BlockManager blockManager;
    public ServerChunkProvider(StorageManager storageManager, WorldGenerator generator){
    	blockManager=CoreRegistry.get(BlockManager.class);
    	this.storageManager=storageManager;
    	this.generator=generator;
    }

    public ServerChunkProvider(){
    	
    }
    public void createOrLoadChunk(Vector3i chunkPos){
    	//System.out.printf("加载或创建地图  x:%d y:%d z:%d \n",chunkPos.x,chunkPos.y,chunkPos.z);
		ChunkImpl chunk=nearCache.get(new Vector2i(chunkPos.x,chunkPos.z));
		if(chunk == null){
			String fileName =""+chunkPos.x +"_"+chunkPos.y+"_"+chunkPos.z+".chunk";
			if(chunkPos.x==-2 && chunkPos.z==-5){
				LogUtil.err("block data may be null");
			}
			Path chunkPath =
                        PathManager.getInstance().getInstallPath().resolve("saves").resolve(fileName);
				if(!Switcher.test&&Files.isRegularFile(chunkPath)){
					
					ChunkImpl chunkImpl=new ChunkImpl(chunkPos);
						try {
							  ObjectInputStream in=new ObjectInputStream(new FileInputStream(chunkPath.toFile()));
							  
					            //读取UserInfo对象并调用它的toString()方法   
					           // TeraArray user=(TeraArray)(in.readObject());  
							  TeraArray user= new TeraDenseArray16Bit(); 
					            user.readExternal(in);
							chunkImpl.setBlockData(user) ;
							if(chunkImpl.getBlockData()==null){
								LogUtil.err("block data  is null");
							}
							in.close();
							//byte[] chunkData =Files.readAllBytes(chunkPath);
						} catch (Exception e) {
							// VIP Auto-generated catch block
							e.printStackTrace();
						}
					// ChunkStore chunkStore = storageManager.loadChunkStore(chunkPos);
					// chunk= chunkStore.getChunk();
					 
					 if(nearCache.putIfAbsent(new Vector2i(chunkPos.x,chunkPos.z), chunkImpl)!=null){
						 logger.warn("Chunk {} is already in the near cache", chunkPos);
					 }
				}else{
					 chunk = new ChunkImpl(chunkPos);
                    generator.createChunk(chunk);
                 //   chunk.build();
                    if (nearCache.putIfAbsent(new Vector2i(chunkPos.x,chunkPos.z), chunk) != null) {
                        logger.warn("Chunk {} is already in the near cache", chunkPos);
                    }
                   // chunk.build();
				}
			}
		 chunk=nearCache.get(new Vector2i(chunkPos.x,chunkPos.z));

		
	}
	public Vector3i getPosition() {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public ChunkViewCore getLocalView(Vector3i centerChunkPos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public ChunkViewCore getSubviewAroundBlock(Vector3i blockPos, int extent) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public ChunkViewCore getSubviewAroundChunk(Vector3i chunkPos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// VIP Auto-generated method stub
		
	}

	@Override
	public boolean isChunkReady(Vector3i pos) {
		// VIP Auto-generated method stub
		return false;
	}

	  public ChunkImpl getChunk(Vector3i pos) {
	        ChunkImpl chunk = nearCache.get(pos);
	        /*if(chunk==null){
	        	createOrLoadChunk(pos);
	        	 chunk = nearCache.get(pos);
	        }*/
	            return chunk;
	     
	    }



	@Override
	public void dispose() {
		// VIP Auto-generated method stub
		
	}

	@Override
	public void purgeChunks() {
		// VIP Auto-generated method stub
		
	}

	@Override
	public ChunkImpl getChunkForProcessing(Vector3i pos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public ChunkViewCore getViewAround(Vector3i chunkPos) {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public void onChunkIsReady(Vector3i position) {
		// VIP Auto-generated method stub
		
	}

	@Override
	public WorldGenerator getWorldGenerator() {
		// VIP Auto-generated method stub
		return null;
	}

	@Override
	public ChunkImpl getChunk(int x, int y, int z) {
		 // ChunkImpl chunk = nearCache.get(new Vector3i(x,y,z));
		ChunkImpl chunk = nearCache.get(new Vector2i(x,z));
	        if(chunk==null){
	        	createOrLoadChunk(new Vector3i(x,y,z));
	        	 chunk = nearCache.get(new Vector2i(x,z));
	        }
		  if(chunk!=null&& chunk.getBlockData()==null){
			  System.out.println("found the null blockdata chunk in nearCache 。solve it！！！");
		  }
	            return chunk;
	     
	}

	@Override
	public void createOrLoadChunk(int x, int i, int y) {
		// VIP Auto-generated method stub
		
	}

	@Override
	public void removeChunk(Chunk c) {
		this.nearCache.remove(new Vector2i(c.getPos().x,c.getPos().z));
		if(nearCache.get(new Vector2i(c.getPos().x,c.getPos().z))!=null){
			LogUtil.err("unload chunk failed");
			System.out.println("删除失败!");
		}
	}

	@Override
	public void save() {
		Iterator<Map.Entry<Vector2i,ChunkImpl >> it = nearCache.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Vector2i,ChunkImpl > entry = it.next();
			entry.getValue().save();
		}
	}


	public void setBlock(){

	}
}
