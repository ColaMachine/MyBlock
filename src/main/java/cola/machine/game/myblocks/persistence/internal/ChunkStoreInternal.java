package cola.machine.game.myblocks.persistence.internal;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.persistence.ChunkStore;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ChunkStoreInternal implements ChunkStore {

	Map<Vector3i, String> ma= Maps.newConcurrentMap();
    private Map<Vector3i, ChunkStoreInternal> pendingProcessingChunkStore = Maps.newConcurrentMap();
    private Map<Vector3i, byte[]> compressedChunkStore = Maps.newConcurrentMap();
    private static final String WORLDS_PATH = "worlds";
    private static final Logger logger = LoggerFactory.getLogger(StorageManagerInternal.class);
    private static final int CHUNK_ZIP_DIM = 32;
    private boolean storeChunksInZips = true;
    
	 public boolean containsChunkStoreFor(Vector3i chunkPos) {
		 if (pendingProcessingChunkStore.containsKey(chunkPos) || compressedChunkStore.containsKey(chunkPos)) {
	            return true;
	        }
		 if (storeChunksInZips) {
	            Path chunkZipPath = getWorldPath().resolve(getChunkZipFilename(getChunkZipPosition(chunkPos)));
	            if (Files.isRegularFile(chunkZipPath)) {
	                try (FileSystem zip = FileSystems.newFileSystem(chunkZipPath, null)) {
	                    return Files.isRegularFile(zip.getPath(getChunkFilename(chunkPos)));
	                } catch (IOException e) {
	                    logger.error("Failed to access chunk zip {}", chunkZipPath, e);
	                }
	            }
	            return false;
	        } else {
	            return Files.isRegularFile(getWorldPath().resolve(getChunkFilename(chunkPos)));
	        }
	 }
	 
	 private Path getWorldPath() {
	        return PathManager.getInstance().getCurrentSavePath().resolve(WORLDS_PATH).resolve("mian");
	    }
	 private String getChunkFilename(Vector3i pos) {
	        return String.format("%d.%d.%d.chunk", pos.x, pos.y, pos.z);
	    }
	 private String getChunkZipFilename(Vector3i pos) {
	        return String.format("%d.%d.%d.chunks.zip", pos.x, pos.y, pos.z);
	    }

	 
	    private Vector3i getChunkZipPosition(Vector3i chunkPos) {
	        Vector3i result = new Vector3i(chunkPos);
	        result.divide(CHUNK_ZIP_DIM);
	        if (chunkPos.x < 0) {
	            result.x -= 1;
	        }
	        if (chunkPos.y < 0) {
	            result.y -= 1;
	        }
	        if (chunkPos.z < 0) {
	            result.z -= 1;
	        }
	        return result;
	    }
	    
	    ChunkStore loadChunkStore(Vector3i chunkPos){
	    	ChunkStore store = pendingProcessingChunkStore.get(chunkPos);
	    	if(store ==null){
	    		byte[] chunkData=compressedChunkStore.get(chunkPos);
	    	}
	    	return null;
	    }

		@Override
		public ChunkImpl getChunk() {
			// VIP Auto-generated method stub
			return null;
		}


}
