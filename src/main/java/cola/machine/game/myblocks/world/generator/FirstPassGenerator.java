package cola.machine.game.myblocks.world.generator;

import cola.machine.game.myblocks.world.chunks.Chunk;


public interface FirstPassGenerator extends  BaseChunkGenerator  {
 void generateChunk(Chunk chunk);
}
