

package cola.machine.game.myblocks.world.chunks;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.generator.WorldGenerator;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.network.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地的方块提供者
 * @author Immortius
 */
public class RemoteChunkProvider extends  LocalChunkProvider {

    private static final Logger logger = LoggerFactory.getLogger(RemoteChunkProvider.class);

    public RemoteChunkProvider(){

    }
    public RemoteChunkProvider(StorageManager storageManager, WorldGenerator generator){
        super(storageManager,generator);
    }
    public void setBlock(int chunkX,int chunkY,int chunkZ,int blockX,int blockY,int blockZ,int stateId){
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = blockX;
        cmd.cz = blockZ;
        cmd.cy = blockY;
        cmd.type = 1;
        cmd.blockType = stateId;
        CoreRegistry.get(Client.class).send(cmd);

    }
}
