package cola.machine.game.myblocks.world.chunks.Internal;


import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.ChunkViewCore;
import cola.machine.game.myblocks.world.generator.WorldGenerator;

public interface GeneratingChunkProvider extends ChunkProvider{


    /**
     * @param pos
     * @return Whether this chunk is available and ready for use
     */
    boolean isChunkReady(Vector3i pos);

    /**
     * Obtains a chunk for pipeline processing. This should happen regardless of the state of the chunk.
     *
     * @param pos
     * @return The requested chunk, or null if it isn't currently loaded.
     */
    ChunkImpl getChunkForProcessing(Vector3i pos);

    /**
     * Obtains a local chunk view of the chunk at the given position and the immediately surrounding chunks.
     * Block positions are offset so that the origin is at minimum coords of the target chunk.
     *
     * @param chunkPos
     * @return A local chunk view, or null if some of the chunks are unavailable.
     */
    ChunkViewCore getViewAround(Vector3i chunkPos);

    /**
     * Causes the creation or loading of a chunk.
     *
     * @param position
     */
    void createOrLoadChunk(Vector3i position);

    /**
     * Notifies the chunk provider that a chunk is ready.
     *
     * @param position
     */
    void onChunkIsReady(Vector3i position);

    WorldGenerator getWorldGenerator();

	
	
}
