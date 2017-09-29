package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import glmodel.GL_Vector;

/**
 * Created by dozen.zhang on 2017/9/23.
 */
public class Triangle {
    public ColorBlock block1;
    public ColorBlock block2;
    public ColorBlock block3;
    public String textureName;
    public TextureInfo ti;
    public float rf;
    public float gf;
    public float bf;

    public void renderShader(ShaderConfig terrainShaderConfig, Vao vao) {

        ShaderUtils.draw3dColorTriangle(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),
             block1.x, block1.y, block1.z,
                block2.x, block2.y, block2.z,
                block3.x, block3.y, block3.z,
                BoxModel.FRONT_DIR, block1.rf,block1.gf,block1.bf);

        ShaderUtils.drawLine( block1.x, block1.y, block1.z,  block2.x, block2.y, block2.z);
        ShaderUtils.drawLine( block2.x, block2.y, block2.z,  block3.x, block3.y, block3.z);
        ShaderUtils.drawLine( block1.x, block1.y, block1.z,  block3.x, block3.y, block3.z);
    }
}
