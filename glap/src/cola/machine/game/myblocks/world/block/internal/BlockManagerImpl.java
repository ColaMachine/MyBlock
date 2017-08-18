package cola.machine.game.myblocks.world.block.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cola.machine.game.myblocks.model.*;
import cola.machine.game.myblocks.world.block.BlockManager;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.ByteUtil;

public class BlockManagerImpl extends BlockManager {
    /**name to block **/
	private HashMap<String,IBlock> blockInfoNameMap=new HashMap();
    /**id to block **/
   private HashMap<Integer,IBlock> blockInfoIdMap=new HashMap();

    /**
     * 加载所有的block
     */
    public void  loadAllBlock(){

    }

    /**
     * 这里需要改进 不同的block 应该对应不同的类 colorblock  textureblock  DirStateTextureBLock
     */
	public BlockManagerImpl(){
        IBlock air=new ImageBlock("air",0,false);
		IBlock stone=new ImageBlock("stone", ItemType.stone.ordinal(),false);
		IBlock soil =new ImageBlock("soil",ItemType.soil.ordinal(),false);
		IBlock glass=new ImageBlock("glass",ItemType.glass.ordinal(),true);
		IBlock sand=new ImageBlock("sand",ItemType.sand.ordinal(),false);
		IBlock mantle=new ImageBlock("mantle",ItemType.mantle.ordinal(),false);
		IBlock water=new ImageBlock("water",ItemType.water.ordinal(),true);
		IBlock wood=new ImageBlock("wood",ItemType.wood.ordinal(),false);


        IBlock grass=new ImageBlock("grass",ItemType.grass.ordinal(),false);
        blockInfoNameMap.put("grass", grass);
        IBlock CrackedStoneBrick=new ImageBlock("CrackedStoneBrick",ItemType.CrackedStoneBrick.ordinal(),false);
        blockInfoNameMap.put("CrackedStoneBrick", CrackedStoneBrick);

        IBlock StoneBrick=new ImageBlock("StoneBrick",ItemType.StoneBrick.ordinal(),false);
        blockInfoNameMap.put("StoneBrick", StoneBrick);

        IBlock MossyStoneBrick=new ImageBlock("MossyStoneBrick",ItemType.MossyStoneBrick.ordinal(),false);
        blockInfoNameMap.put("MossyStoneBrick", MossyStoneBrick);

        IBlock OakWood=new ImageBlock("OakWood",ItemType.OakWood.ordinal(),false);
        blockInfoNameMap.put("OakWood", OakWood);


        IBlock treeWood=new ImageBlock("tree_wood",ItemType.tree_wood.ordinal(),false);
        IBlock treeLeaf=new ImageBlock("tree_seed",ItemType.tree_seed.ordinal(),true);
        IBlock treeSeed=new ImageBlock("tree_leaf",ItemType.tree_leaf.ordinal(),false);
        IBlock wood_door=new DoorBlock("wood_door",ItemType.wood_door.ordinal(),true);
        IBlock copy_down=new CopyDownBlock("copy_down",ItemType.copy_down.ordinal(),true);
        IBlock red=new ColorBlock("red",ItemType.red.ordinal(),false);
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
        IBlock box=new BoxBlock("box",ItemType.box.ordinal(),true);
        blockInfoNameMap.put("box", box);
        blockInfoNameMap.put("copy_down", copy_down);
        blockInfoNameMap.put("red", red);
       Iterator<Map.Entry<String, IBlock>> it = blockInfoNameMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, IBlock> entry =it.next();

            blockInfoIdMap.put(entry.getValue().getId(),entry.getValue());
        }


	}

	public IBlock getBlock(String  name){
		return blockInfoNameMap.get(name);
	}
    @Override
    public IBlock getBlock(int  id) {
        if(id<0 || id>255 || id==ItemType.wood_door.ordinal()|| id==ItemType.box.ordinal()){
           int realId =  ByteUtil.get8_0Value(id);
            IBlock block =blockInfoIdMap.get(realId);
           // block.setValue(id);
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
