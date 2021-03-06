package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.model.base.BaseBlock;
import cola.machine.game.myblocks.model.base.ColorBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;

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

    public boolean hashBlock(BaseBlock tb1, BaseBlock tb2, BaseBlock tb3){
        if(block1 ==tb1 || block1==tb2 || block1==tb3){
            if(block2 ==tb1 || block2==tb2 || block2==tb3){
                if(block3 ==tb1 || block3==tb2 || block3==tb3){
                    return true;
                }
            }
        }
        return false;
    }
}
