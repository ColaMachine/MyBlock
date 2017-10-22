package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class CopyBlock extends BaseBlock{
    int dir=0;
    
    int open=0;
    ItemDefinition itemDefinition;
    BaseBlock targetBlock ;
    public CopyBlock(BaseBlock targetBlock){
        this.id = ItemType.CopyBlock.id;
        this.targetBlock =  targetBlock;
    }



    @Override
    public void setValue(int value) {


    }
    //画出四个面
    public void render(Vao vao,ShapeFace shapeFace,TextureInfo ti ){

    }
    public boolean use(GL_Vector placePoint){
        return false;
    }
    public boolean beuse(){
       return targetBlock.beuse();
    }
    @Override
    public IBlock clone(){
        return this;
    }
    @Override
    public boolean isPenetrate() {
        return targetBlock.penetration;
    }
    public void beAttack(){
        targetBlock.beAttack();

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
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {

        ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(),1,1,1,1,1,1);
    }
    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {
        ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(),1,1,1,1,1,1);
    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX,float parentY,float parentZ, float childX,float childY,float childZ,float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {
        ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(),1,1,1,1,1,1);
    }

//    @Override
//    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {
//        ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(),1,1,1,1,1,1);
//    }
}
