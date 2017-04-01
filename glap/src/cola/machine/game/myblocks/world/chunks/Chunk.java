package cola.machine.game.myblocks.world.chunks;


import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.protobuf.ChunksProtobuf;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;


public interface Chunk {
    Vector3i getPos();

    Block getBlock(Vector3i pos);

    Block getBlock(int x, int y, int z);

    Block setBlock(int x, int y, int z, Block block);

    Block setBlock(Vector3i pos, Block block);

/*    void setLiquid(Vector3i pos, LiquidData state);

    void setLiquid(int x, int y, int z, LiquidData newState);

    LiquidData getLiquid(Vector3i pos);

    LiquidData getLiquid(int x, int y, int z);*/

    Vector3i getChunkWorldPos();

    int getChunkWorldPosX();

    int getChunkWorldPosY();

    int getChunkWorldPosZ();

    Vector3i getBlockWorldPos(Vector3i blockPos);

    Vector3i getBlockWorldPos(int x, int y, int z);

    int getBlockWorldPosX(int x);

    int getBlockWorldPosY(int y);

    int getBlockWorldPosZ(int z);

    int getChunkSizeX();

    int getChunkSizeY();
    void setBlockData(TeraArray blockData);
    int getChunkSizeZ();

   TeraArray getBlockData();

    int  getBlockData(int x,int y,int z);
}
