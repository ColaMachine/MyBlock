package cola.machine.game.myblocks.block;

import java.util.HashMap;

/**
 * Created by luying on 14/10/24.
 */
public class BlockDefRegistry {

    public HashMap<Short,Block> blockDefById=new HashMap();

    public HashMap<String,Block> blockDefByName=new HashMap();

    public void registry(Block block){

        blockDefById.put((short)(block.getId()*10+block.getCid()),block);
        blockDefByName.put(block.getName(),block);
    }

    public Block getBlockById(short id){
        return blockDefById.get(id);
    }

    public Block getBlockByName(String name){
        return blockDefByName.get(name);
    }
}
