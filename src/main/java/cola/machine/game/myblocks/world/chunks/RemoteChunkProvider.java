

package cola.machine.game.myblocks.world.chunks;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.math.Vector2i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraArray;
import cola.machine.game.myblocks.world.chunks.blockdata.TeraDenseArray16Bit;
import cola.machine.game.myblocks.world.generator.WorldGenerator;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.FileUtil;
import com.dozenx.util.MathUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

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
