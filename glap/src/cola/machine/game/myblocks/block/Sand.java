package cola.machine.game.myblocks.block;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import glmodel.GL_Matrix;

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


    public void update(float x, float y, float z, float width, float height, float thick) {

    }


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
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float x, float y, float z, float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

    @Override
    public IBlock clone() {
        return null;
    }
}
