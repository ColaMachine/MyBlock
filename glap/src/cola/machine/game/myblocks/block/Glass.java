package cola.machine.game.myblocks.block;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;

/**
 * Created by luying on 14-8-30.
 */
public class Glass extends BaseBlock {
        String name ="glass";
        public Glass(int x, int y, int z) {
        	super("glass",x,y,z);
	}

    @Override
    public void update(float x, float y, float z, float width, float height, float thick) {

    }

    @Override
    public void update() {

    }

    @Override
    public BaseBlock copy() {
        return null;
    }

    @Override
    public IBlock clone() {
        return null;
    }
}
