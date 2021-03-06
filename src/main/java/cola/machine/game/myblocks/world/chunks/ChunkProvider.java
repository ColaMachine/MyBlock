

package cola.machine.game.myblocks.world.chunks;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.IBlock;


public interface ChunkProvider {
	


    /**
     * A local view provides a
     *
     * @param centerChunkPos
     * @return A chunk view centered on the given chunk, with all of the surrounding chunks included.
     */
    ChunkViewCore getLocalView(Vector3i centerChunkPos);

    /**
     * @param blockPos
     * @param extent
     * @return A chunk view of the chunks around the given blockPos.
     */
    ChunkViewCore getSubviewAroundBlock(Vector3i blockPos, int extent);

    /**
     * @param chunkPos
     * @return A chunk view including the chunks around the given chunk
     */
    ChunkViewCore getSubviewAroundChunk(Vector3i chunkPos);

    /**
     * Sets the world entity, for the purpose of receiving chunk events.
     *
     * @param entity
     */
  //  void setWorldEntity(EntityRef entity);

    /**
     * Requests that a region around the given entity be maintained in near cache
     *
     * @param entity
     * @param distance The region (in chunks) around the entity that should be near cached
     */
    //void addRelevanceEntity(EntityRef entity, int distance);

    /**
     * Requests that a region around the given entity be maintained in near cache
     *
     * @param entity
     * @param distance The region (in chunks) around the entity that should be near cached
     * @param listener A listener to chunk region events
     */
  //  void addRelevanceEntity(EntityRef entity, int distance, ChunkRegionListener listener);

    /**
     * Retrieves the ChunkRelevanceRegion object for the given entity
     *
     * @param entity
     * @return The chunk relevance region, or null
     */
   // void updateRelevanceEntity(EntityRef entity, int distance);

    /**
     * Removes an entity from producing a caching region
     *
     * @param entity
     */
  //  void removeRelevanceEntity(EntityRef entity);

    /**
     * Updates the near cache based on the movement of the caching entities
     */
    void update();

    /**
     * @param pos
     * @return Whether this chunk is available and ready for use
     */
    boolean isChunkReady(Vector3i pos);

    /**
     * Returns the chunk at the given position if possible.
     *
     * @param x The chunk position on the x-axis
     * @param y The chunk position on the y-axis
     * @param z The chunk position on the z-axis
     * @return The chunk, or null if the chunk is not ready
     */
    Chunk getChunk(int x, int y, int z);

    /**
     * Returns the chunk at the given position if possible.
     *
     * @param chunkPos The position of the chunk to obtain
     * @return The chunk, or null if the chunk is not ready
     */
    Chunk getChunk(Vector3i chunkPos);

    /**
     * Disposes the chunk provider, cleaning up all chunks and other assets it is using
     */
    void dispose();

    /**
     * Destroys all chunks and triggers their regeneration
     */
    void purgeChunks();

	void createOrLoadChunk(int x,int  y, int z);
	 public void createOrLoadChunk(Vector3i chunkPos);

	void removeChunk(Chunk c);
    void save();
    public void reload(Chunk chunk);
    public IBlock getBlockAt(int worldX, int worldY, int worldZ);

    public void setBlock(int chunkX,int chunkY,int chunkZ,int blockX,int blockY,int blockZ,int stateId);
}
