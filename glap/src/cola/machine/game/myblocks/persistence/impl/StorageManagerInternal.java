package cola.machine.game.myblocks.persistence.impl;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.GZIPInputStream;


import com.google.common.collect.Maps;

import cola.machine.game.myblocks.persistence.ChunkStore;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.world.chunks.Vector3i;

public class StorageManagerInternal implements StorageManager{
	@Override
	public ChunkStore loadChunkStore(Vector3i chunkPos) {
		
		return null;
	}

	@Override
	public boolean containsChunkStoreFor(Vector3i chunkPos) {
		// VIP Auto-generated method stub
		return false;
	}

}
