package cola.machine.game.myblocks.persistence;


import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

public interface ChunkStore {
	public     ChunkImpl getChunk();
}
