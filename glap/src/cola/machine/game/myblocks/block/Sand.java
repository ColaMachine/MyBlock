package cola.machine.game.myblocks.block;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;

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
