package cola.machine.game.myblocks.world.generator;


import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

public interface WorldGenerator {


    void createChunk(Chunk chunk);
}
