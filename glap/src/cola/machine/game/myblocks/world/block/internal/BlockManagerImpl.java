package cola.machine.game.myblocks.world.block.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.world.block.BlockManager;

public class BlockManagerImpl extends BlockManager {
	private HashMap<String,Block> blockInfoNameMap=new HashMap();
    private HashMap<Integer,Block> blockInfoIdMap=new HashMap();
	public BlockManagerImpl(){
        Block air=new BaseBlock("air",0,false);
		Block stone=new BaseBlock("stone",1,false);
		Block soil =new BaseBlock("soil",3,false);
		Block glass=new BaseBlock("glass",20,true);
		Block sand=new BaseBlock("sand",12,false);
		Block mantle=new BaseBlock("mantle",7,false);
		Block water=new BaseBlock("water",8,true);
		Block wood=new BaseBlock("wood",5,false);
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
