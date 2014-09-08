package cola.machine.game.myblocks.world.block;

import cola.machine.game.myblocks.model.Block;

public abstract class BlockManager {

	 public abstract Block getBlock(String uri);

	public abstract Block getBlock(short oldValue);

}
