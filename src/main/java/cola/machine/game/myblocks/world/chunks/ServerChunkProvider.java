

package cola.machine.game.myblocks.world.chunks;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.math.Vector2i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;
import cola.machine.game.myblocks.world.generator.WorldGenerator;
import com.dozenx.util.FileUtil;
import com.dozenx.util.MathUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * 本地的方块提供者
 * @author Immortius
 */
public class ServerChunkProvider extends LocalChunkProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServerChunkProvider.class);

    private ConcurrentMap<Vector2i, ChunkImpl> nearCache = Maps.newConcurrentMap();//旁边缓存

   // private StorageManager storageManager;//保存系统

   // private final Set<Vector3i> preparingChunks = Sets.newSetFromMap(Maps.<Vector3i, Boolean>newConcurrentMap());
   // private WorldGenerator generator;//世界生成机制
   // private BlockManager blockManager;


    public ServerChunkProvider(StorageManager storageManager, WorldGenerator generator){
        super(storageManager,generator);
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
                        PathManager.getInstance().getInstallPath().resolve("saves/block").resolve(fileName);
				if(Files.isRegularFile(chunkPath)){
					
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

                            String additionalFileName =""+chunkPos.x +"_"+chunkPos.y+"_"+chunkPos.z+".map";
                            Path additionalPath =
                                    PathManager.getInstance().getInstallPath().resolve("saves").resolve(additionalFileName);

                            File additionalFile = additionalPath.toFile();
                            if(additionalFile.exists()){
                                List<String> lineList = FileUtil.readFile2List(additionalFile);

                    for(String line : lineList){
                        String[] ary = line.split(",");
        int x = Integer.valueOf(ary[0]);
                        int y=Integer.valueOf(ary[1]);
                        int z = Integer.valueOf(ary[2]);
                        int dir = Integer.valueOf(ary[3]);


                        chunkImpl.setBlockStatus(x,y,z,dir);
                    }
                            }


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



	  public ChunkImpl getChunk(Vector3i pos) {
	        ChunkImpl chunk =(ChunkImpl) nearCache.get(pos);
	        /*if(chunk==null){
	        	createOrLoadChunk(pos);
	        	 chunk = nearCache.get(pos);
	        }*/
	            return chunk;
	     
	    }





	@Override
	public ChunkImpl getChunk(int x, int y, int z) {
		 // ChunkImpl chunk = nearCache.get(new Vector3i(x,y,z));
		ChunkImpl chunk = (ChunkImpl)nearCache.get(new Vector2i(x,z));
	        if(chunk==null){
	        	createOrLoadChunk(new Vector3i(x,y,z));
	        	 chunk = (ChunkImpl)nearCache.get(new Vector2i(x,z));
	        }
		  if(chunk!=null&& chunk.getBlockData()==null){
			  System.out.println("found the null blockdata chunk in nearCache 。solve it！！！");
		  }
	            return chunk;
	     
	}


	@Override
	public IBlock getBlockAt(int worldX, int worldY, int worldZ){
		int chunkX = MathUtil.getBelongChunkInt(worldX);
		int chunxZ = MathUtil.getBelongChunkInt(worldZ);
		Chunk chunk =  nearCache.get(new Vector2i(chunkX,chunxZ));
		//if(chunk!=null)
		return chunk.getBlock(MathUtil.getOffesetChunk(worldX),worldY,MathUtil.getOffesetChunk(worldZ));
	}

    @Override
    public void setBlock(int chunkX, int chunkY, int chunkZ, int blockX, int blockY, int blockZ, int stateId) {

    }

    @Override
    public void save() {
        Iterator<Map.Entry<Vector2i,ChunkImpl >> it = nearCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Vector2i,ChunkImpl > entry = it.next();
            entry.getValue().save();
        }
    }



}
