package cola.machine.game.myblocks.block;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;

/**
 * Created by luying on 14-8-30.
 */
public class Soil extends cola.machine.game.myblocks.block.Block{

    public Soil(){
        this.setId((short)0);
        this.setName("soil");
        this.setDisplayName("土");
        this.setHardness(3);
      
        BlockApperance blockApperance=new BlockApperance();
       // blockApperance.setAllSide(TextureManager.getTextureInfo("soil"));

    }

}
