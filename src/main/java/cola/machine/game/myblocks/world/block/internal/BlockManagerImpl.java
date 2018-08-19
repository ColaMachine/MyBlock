package cola.machine.game.myblocks.world.block.internal;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.DoorBlock;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.world.block.BlockManager;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.action.ItemManager;
import core.log.LogUtil;

import java.util.HashMap;

public class BlockManagerImpl extends BlockManager {
    /**name to block **/
	//private HashMap<String,IBlock> blockInfoNameMap=new HashMap();
    /**id to block **/
    private HashMap<Integer ,Float[]> blockAryMap =new HashMap<>();//所有的block的id 和对应的 渲染数据
    private HashMap<Integer,BaseBlock> blockInfoIdMap=new HashMap();

    /**
     * 加载所有的block
     */
    public void  loadAllBlock(){

    }

    /**
     * 这里需要改进 不同的block 应该对应不同的类 colorblock  textureblock  DirStateTextureBLock
     */
	public BlockManagerImpl(){
       /* IBlock air=new ImageBlock("air",0,false);
		IBlock stone=new ImageBlock("stone", ItemType.stone.id,false);
		IBlock soil =new ImageBlock("soil",ItemType.soil.id,false);
		IBlock glass=new ImageBlock("glass",ItemType.glass.id,true);
		IBlock sand=new ImageBlock("sand",ItemType.sand.id,false);
		IBlock mantle=new ImageBlock("mantle",ItemType.mantle.id,false);
		IBlock water=new ImageBlock("water",ItemType.water.id,true);
		IBlock wood=new ImageBlock("wood",ItemType.wood.id,false);


        IBlock grass=new ImageBlock("grass",ItemType.grass.id,false);
        blockInfoNameMap.put("grass", grass);
        IBlock CrackedStoneBrick=new ImageBlock("CrackedStoneBrick",ItemType.CrackedStoneBrick.id,false);
        blockInfoNameMap.put("CrackedStoneBrick", CrackedStoneBrick);

        IBlock StoneBrick=new ImageBlock("StoneBrick",ItemType.StoneBrick.id,false);
        blockInfoNameMap.put("StoneBrick", StoneBrick);

        IBlock MossyStoneBrick=new ImageBlock("MossyStoneBrick",ItemType.MossyStoneBrick.id,false);
        blockInfoNameMap.put("MossyStoneBrick", MossyStoneBrick);

        IBlock OakWood=new ImageBlock("OakWood",ItemType.OakWood.id,false);
        blockInfoNameMap.put("OakWood", OakWood);


        IBlock treeWood=new ImageBlock("tree_wood",ItemType.tree_wood.id,false);
        IBlock treeLeaf=new ImageBlock("tree_seed",ItemType.tree_seed.id,true);
        IBlock treeSeed=new ImageBlock("tree_leaf",ItemType.tree_leaf.id,false);
        IBlock wood_door=new DoorBlock("wood_door",ItemType.wood_door.id,true);
        IBlock copy_down=new CopyDownBlock("copy_down",ItemType.copy_down.id,true);
        IBlock red=new ColorBlock("red",ItemType.red.id,false);
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
        IBlock box=new BoxBlock("box",ItemType.box.id,true);
        blockInfoNameMap.put("box", box);
        blockInfoNameMap.put("copy_down", copy_down);
        blockInfoNameMap.put("red", red);
       Iterator<Map.Entry<String, IBlock>> it = blockInfoNameMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, IBlock> entry =it.next();

            blockInfoIdMap.put(entry.getValue().getId(),entry.getValue());
        }
*/

        blockInfoIdMap.put(ItemType.wood_door.id,new DoorBlock("doorblock",ItemType.wood_door.id,false));
        //遍历所有的方块落入到

	}

	public IBlock getBlock(String  name){
        return ItemManager.getItemDefinition(name).getShape();
		//return blockInfoNameMap.get(name);
	}
    @Override
    public IBlock getBlock(int  id) {
       /* if(id<0 || id>255 || id==ItemType.wood_door.id|| id==ItemType.box.id){
           int realId =  ByteUtil.get8_0Value(id);
            IBlock block =blockInfoIdMap.get(realId);
           // block.setValue(id);
            return block;
        }else*/
        if(id==2065){
            LogUtil.println(123+"");
        }
        BaseBlock block = TextureManager.stateIdShapeMap.get(id);
        return block;
        //ItemDefinition itemDefinition = ItemManager.getItemDefinition(id);
       // return itemDefinition==null?null:itemDefinition.getShape();
    }
	/*@Override
	public Block getBlock(int oldValue) {
		
		// VIP Auto-generated method stub
		return null;
	}*/
	
	
}
