package cola.machine.game.myblocks.world.block.internal;

import java.util.HashMap;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.world.block.BlockManager;

public class BlockManagerImpl extends BlockManager {
	private HashMap<String,Block> registerBlockInfo=new HashMap();
	public BlockManagerImpl(){
		Block water=new BaseBlock("water",6);
		Block stone=new BaseBlock("stone",1);
		Block soil =new BaseBlock("soil",2);
		Block glass=new BaseBlock("glass",3);
		Block sand=new BaseBlock("sand",4);
		Block mantle=new BaseBlock("mantle",5);
		registerBlockInfo.put("water", water);
		registerBlockInfo.put("stone", stone);
		registerBlockInfo.put("soil", soil);
		registerBlockInfo.put("glass", glass);
		registerBlockInfo.put("sand", sand);
		registerBlockInfo.put("mantle", mantle);
	}
	
	public Block getBlock(String  name){
		return registerBlockInfo.get(name);
	}

	@Override
	public Block getBlock(short oldValue) {
		
		// VIP Auto-generated method stub
		return null;
	}
	
	
}
