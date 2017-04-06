package cola.machine.game.myblocks.world.block.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.world.block.BlockManager;
import com.dozenx.game.engine.command.ItemType;

public class BlockManagerImpl extends BlockManager {
	private HashMap<String,Block> blockInfoNameMap=new HashMap();
    private HashMap<Integer,Block> blockInfoIdMap=new HashMap();
	public BlockManagerImpl(){
        Block air=new BaseBlock("air",0,false);
		Block stone=new BaseBlock("stone", ItemType.stone_block.ordinal(),false);
		Block soil =new BaseBlock("soil",ItemType.soil_block.ordinal(),false);
		Block glass=new BaseBlock("glass",ItemType.glass_block.ordinal(),true);
		Block sand=new BaseBlock("sand",ItemType.sand_block.ordinal(),false);
		Block mantle=new BaseBlock("mantle",ItemType.mantle_block.ordinal(),false);
		Block water=new BaseBlock("water",ItemType.water_block.ordinal(),true);
		Block wood=new BaseBlock("wood",ItemType.wood_block.ordinal(),false);
        blockInfoNameMap.put("air", air);
        blockInfoNameMap.put("water", water);
        blockInfoNameMap.put("stone", stone);
        blockInfoNameMap.put("soil", soil);
        blockInfoNameMap.put("glass", glass);
        blockInfoNameMap.put("sand", sand);
        blockInfoNameMap.put("mantle", mantle);
        blockInfoNameMap.put("wood", wood);

        Iterator<Map.Entry<String, Block>> it = blockInfoNameMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Block> entry =it.next();

            blockInfoIdMap.put(entry.getValue().getId(),entry.getValue());
        }


	}
	
	public Block getBlock(String  name){
		return blockInfoNameMap.get(name);
	}
    public Block getBlock(int  id){
        return blockInfoIdMap.get(id);
    }
	/*@Override
	public Block getBlock(int oldValue) {
		
		// VIP Auto-generated method stub
		return null;
	}*/
	
	
}
