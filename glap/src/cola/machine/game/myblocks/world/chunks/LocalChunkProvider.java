/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cola.machine.game.myblocks.world.chunks;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cola.machine.game.myblocks.persistence.ChunkStore;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerator;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author Immortius
 */
public class LocalChunkProvider implements ChunkProvider,GeneratingChunkProvider {

    private static final Logger logger = LoggerFactory.getLogger(LocalChunkProvider.class);

    private ConcurrentMap<Vector3i, ChunkImpl> nearCache = Maps.newConcurrentMap();//旁边缓存

    private StorageManager storageManager;//保存系统

    private final Set<Vector3i> preparingChunks = Sets.newSetFromMap(Maps.<Vector3i, Boolean>newConcurrentMap());
    private WorldGenerator generator;//世界生成机制
    private BlockManager blockManager;
    public LocalChunkProvider(StorageManager storageManager,WorldGenerator generator){
    	blockManager=CoreRegistry.get(BlockManager.class);
    	this.storageManager=storageManager;
    	this.generator=generator;
    }
    
    public LocalChunkProvider(){
    	
    }
    public void createOrLoadChunk(Vector3i chunkPos){
		ChunkImpl chunk=nearCache.get(chunkPos);
		if(chunk == null){
				/*if(storageManager.containsChunkStoreFor(chunkPos)){
					 ChunkStore chunkStore = storageManager.loadChunkStore(chunkPos);
					 chunk= chunkStore.getChunk();
					 
					 if(nearCache.putIfAbsent(chunkPos, chunkStore.getChunk())!=null){
						 logger.warn("Chunk {} is already in the near cache", chunkPos);
					 }
				}else*/{
					 chunk = new ChunkImpl(chunkPos);
                    generator.createChunk(chunk);
                    chunk.build();
                    if (nearCache.putIfAbsent(chunkPos, chunk) != null) {
                        logger.warn("Chunk {} is already in the near cache", chunkPos);
                    }
                   // chunk.build();
				}
			}
		
		
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
	        if(chunk==null){
	        	createOrLoadChunk(pos);
	        	 chunk = nearCache.get(pos);
	        }
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
		  ChunkImpl chunk = nearCache.get(new Vector3i(x,y,z));
	        if(chunk==null){
	        	createOrLoadChunk(new Vector3i(x,y,z));
	        	 chunk = nearCache.get(new Vector3i(x,y,z));
	        }
	            return chunk;
	     
	}
    

}
