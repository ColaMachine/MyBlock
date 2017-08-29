package cola.machine.game.myblocks.world.block;

import cola.machine.game.myblocks.model.IBlock;

public abstract class BlockManager {

	 public abstract IBlock getBlock(String uri);

	public abstract IBlock getBlock(int oldValue);


}
