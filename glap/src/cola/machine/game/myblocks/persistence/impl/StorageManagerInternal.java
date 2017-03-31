package cola.machine.game.myblocks.persistence.impl;


import cola.machine.game.myblocks.persistence.ChunkStore;
import cola.machine.game.myblocks.persistence.StorageManager;

import cola.machine.game.myblocks.math.Region3i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ChunkResponseCmd;
import com.dozenx.game.engine.command.ResultCmd;
import com.dozenx.game.network.client.Client;

public class StorageManagerInternal implements StorageManager{
	@Override
	public ChunkStore loadChunkStore(Vector3i chunkPos) {


        //CoreRegistry.get(Client.class).send();
        ResultCmd resultCmd =  CoreRegistry.get(Client.class).syncSend(new ChunkRequestCmd(chunkPos));
		ChunkResponseCmd responseCmd = new ChunkResponseCmd(resultCmd.getMsg());
	}

	@Override
	public boolean containsChunkStoreFor(Vector3i chunkPos) {
		// VIP Auto-generated method stub
		return false;
	}

}
