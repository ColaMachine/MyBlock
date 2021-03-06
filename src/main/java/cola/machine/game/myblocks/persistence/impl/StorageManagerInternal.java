package cola.machine.game.myblocks.persistence.impl;


import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Chunk;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ChunkResponseCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.client.Client;
import core.log.LogUtil;

public class StorageManagerInternal implements StorageManager{
	@Override
	public Chunk loadChunkStore(Vector3i chunkPos) {
        BlockManager blockManager =CoreRegistry.get(BlockManager.class);

        //CoreRegistry.get(Client.class).send();

        //一旦涉及到网络的都有必要改成异步的
        LogUtil.println("客户端加载chunk:" + chunkPos.x + "," + chunkPos.z + "");
        ResultCmd resultCmd =  CoreRegistry.get(Client.class).syncSend(new ChunkRequestCmd(chunkPos));
        if(resultCmd!=null){
        LogUtil.println("接收到");
		ChunkResponseCmd responseCmd = new ChunkResponseCmd(resultCmd.getMsg());


       /* Chunk chunk =new ChunkImpl(responseCmd.x,responseCmd.y,responseCmd.z);
        TeraArray blockData = new TeraDenseArray16Bit();

        //blockData.copy()
        byte[] data = responseCmd.data;
        for(int i=0;i< chunk.getChunkSizeX();i++){
            for(int j=0;i< chunk.getChunkSizeY();j++){
                for(int k=0;i< chunk.getChunkSizeZ();k++){
                    blockData.set(i,j,k,data[i*16*16+j+k]);
                    chunk.setBlock(i,j,k,blockManager.getBlock(data[i*16*16+j+k]));
                }
            }
        }
        chunk.setBlockData(blockData);*/
        return responseCmd.chunk;

        }
        else{
            return null;
        }
	}

	@Override
	public boolean containsChunkStoreFor(Vector3i chunkPos) {
		// VIP Auto-generated method stub
		return false;
	}

}
