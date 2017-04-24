package cola.machine.game.myblocks.world.chunks;


import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.protobuf.ChunksProtobuf;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import com.dozenx.game.engine.command.ItemBlockType;


public interface Chunk {
    /**
     * 得到chunk的 大块区域坐标 类似时区
     * @return
     */
    Vector3i getPos();

    Block getBlock(Vector3i pos);

    Block getBlock(int x, int y, int z);

    public void setBlock(int x, int y, int z, int block) ;
    public void setBlock(int x, int y, int z, ItemBlockType block) ;
    Block setBlock(int x, int y, int z, Block block);

    Block setBlock(Vector3i pos, Block block);

/*    void setLiquid(Vector3i pos, LiquidData state);

    void setLiquid(int x, int y, int z, LiquidData newState);

    LiquidData getLiquid(Vector3i pos);

    LiquidData getLiquid(int x, int y, int z);*/

    Vector3i getChunkWorldPos();

    /**
     * 得到精准的世界坐标 类似经纬度
     * @return
     */
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
    Integer[] zip();

    public void disposeMesh();
    public void build() ;
}
