package cola.machine.game.myblocks.persistence;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.world.chunks.Chunk;
public  interface  StorageManager{
    Chunk loadChunkStore(Vector3i chunkPos);
    boolean containsChunkStoreFor(Vector3i chunkPos);
}
