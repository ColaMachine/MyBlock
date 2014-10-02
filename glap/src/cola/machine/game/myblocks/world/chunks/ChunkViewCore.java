

package cola.machine.game.myblocks.world.chunks;


public interface ChunkViewCore extends ChunkView {

    /**
     * Sets the light level at the given position
     *
     * @param pos
     * @param light
     */
    void setLight(Vector3i pos, byte light);

    /**
     * Sets the light level at the given coordinates
     *
     * @param blockX
     * @param blockY
     * @param blockZ
     * @param light
     */
    void setLight(int blockX, int blockY, int blockZ, byte light);

    /**
     * Sets the sunlight level at the given position
     *
     * @param pos
     * @param light
     */
    void setSunlight(Vector3i pos, byte light);

    /**
     * Sets the sunlight level at the given coordinates
     *
     * @param blockX
     * @param blockY
     * @param blockZ
     * @param light
     */
    void setSunlight(int blockX, int blockY, int blockZ, byte light);

}
