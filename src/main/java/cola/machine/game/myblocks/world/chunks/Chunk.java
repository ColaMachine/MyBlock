package cola.machine.game.myblocks.world.chunks;


import java.util.Map;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;


public interface Chunk {
    /**
     * 得到chunk的 大块区域坐标 类似时区
     * @return
     */
    Vector3i getPos();

    IBlock getBlock(Vector3i pos);

    IBlock getBlock(int x, int y, int z);

    public void setBlock(int x, int y, int z, int block) ;
    //public void setBlock(int x, int y, int z, Integer block) ;
    IBlock setBlock(int x, int y, int z, IBlock block);

    IBlock setBlock(Vector3i pos, IBlock block);

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

    void setNeedUpdate(boolean b);
     boolean isNeedUpdate();

    Map<Integer, IBlock> getBlockMap();

    void setBlockStatus(int x, int y, int z, int dir);
}
