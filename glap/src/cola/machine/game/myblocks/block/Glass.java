package cola.machine.game.myblocks.block;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;

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
    public void render(ShaderConfig config, Vao vao, int x, int y, int z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

    @Override
    public IBlock clone() {
        return null;
    }
}
