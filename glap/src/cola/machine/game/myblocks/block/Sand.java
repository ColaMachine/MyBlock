package cola.machine.game.myblocks.block;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;

/**
 * Created by luying on 14-8-30.
 */
public class Sand extends BaseBlock {
    String basedOn= "core:soil";
       int     hardness= 2;
        int mass=32;
        String name ="sand";
        public Sand(int x, int y, int z) {
        	super("sand",x,y,z);
	}
}
