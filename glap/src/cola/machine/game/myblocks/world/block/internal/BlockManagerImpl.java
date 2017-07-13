package cola.machine.game.myblocks.world.block.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.CopyDownBlock;
import cola.machine.game.myblocks.model.DoorBlock;
import cola.machine.game.myblocks.world.block.BlockManager;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.ByteUtil;

public class BlockManagerImpl extends BlockManager {
	private HashMap<String,Block> blockInfoNameMap=new HashMap();
   private HashMap<Integer,Block> blockInfoIdMap=new HashMap();

    /**
     * 加载所有的block
     */
    public void  loadAllBlock(){

    }

	public BlockManagerImpl(){
        Block air=new BaseBlock("air",0,false);
		Block stone=new BaseBlock("stone", ItemType.stone.ordinal(),false);
		Block soil =new BaseBlock("soil",ItemType.soil.ordinal(),false);
		Block glass=new BaseBlock("glass",ItemType.glass.ordinal(),true);
		Block sand=new BaseBlock("sand",ItemType.sand.ordinal(),false);
		Block mantle=new BaseBlock("mantle",ItemType.mantle.ordinal(),false);
		Block water=new BaseBlock("water",ItemType.water.ordinal(),true);
		Block wood=new BaseBlock("wood",ItemType.wood.ordinal(),false);
        Block treeWood=new BaseBlock("tree_wood",ItemType.tree_wood.ordinal(),false);
        Block treeLeaf=new BaseBlock("tree_seed",ItemType.tree_seed.ordinal(),true);
        Block treeSeed=new BaseBlock("tree_leaf",ItemType.tree_leaf.ordinal(),false);
        Block wood_door=new DoorBlock("wood_door",ItemType.wood_door.ordinal(),true);
        Block copy_down=new CopyDownBlock("copy_down",ItemType.copy_down.ordinal(),true);
        blockInfoNameMap.put("air", air);
        blockInfoNameMap.put("water", water);
        blockInfoNameMap.put("stone", stone);
        blockInfoNameMap.put("soil", soil);
        blockInfoNameMap.put("glass", glass);
        blockInfoNameMap.put("sand", sand);
        blockInfoNameMap.put("mantle", mantle);
        blockInfoNameMap.put("wood", wood);
       blockInfoNameMap.put("tree_wood", treeWood);
        blockInfoNameMap.put("tree_leaf", treeLeaf);
        blockInfoNameMap.put("tree_seed", treeSeed);
        blockInfoNameMap.put("wood_door", wood_door);
        Block box=new DoorBlock("box",ItemType.box.ordinal(),true);
        blockInfoNameMap.put("box", box);
        blockInfoNameMap.put("copy_down", copy_down);
       Iterator<Map.Entry<String, Block>> it = blockInfoNameMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Block> entry =it.next();

            blockInfoIdMap.put(entry.getValue().getId(),entry.getValue());
        }


	}

	public Block getBlock(String  name){
		return blockInfoNameMap.get(name);
	}
    public Block getBlock(int  id) {
        if(id<0 || id>ItemType.wood_door.ordinal()){
           int realId =  ByteUtil.get8_0Value(id);
            Block block =blockInfoIdMap.get(realId);
            block.setValue(id);
            return block;
        }else
        return blockInfoIdMap.get(id);
    }
	/*@Override
	public Block getBlock(int oldValue) {
		
		// VIP Auto-generated method stub
		return null;
	}*/
	
	
}
