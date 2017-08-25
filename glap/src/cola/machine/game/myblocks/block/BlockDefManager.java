package cola.machine.game.myblocks.block;

/**
 * Created by luying on 14/10/24.
 */
public class BlockDefManager {
    BlockDefRegistry  blockDefRegistry=new BlockDefRegistry();
    public void registerAllBlock(){
//        blockDefRegistry.registry(new Air());
//        blockDefRegistry.registry(new Stone());
//        blockDefRegistry.registry(new Water());
//        blockDefRegistry.registry(new Bedrock());

    }

    public Block getBlockById(short id){
        return blockDefRegistry.getBlockById(id);
    }

    public Block getBlockByName(String name){
        return blockDefRegistry.getBlockByName(name);
    }
}
