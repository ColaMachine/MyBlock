package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class StateBlock extends BaseBlock{
  int state;
    int value16_12 ;
    int value12_8;
    int value8_4;
    int value4_0;

    public StateBlock(String name , int id, boolean isAlpha){
        super(name,id,isAlpha);
    }

    public void setValue(int value) {
        int hightByte = ByteUtil.get16_8Value(value);
        value16_12 =ByteUtil.get16_12Value(value);
        value12_8 = ByteUtil.get12_8Value(value);;


    }

    @Override
    public Block clone(){
        BoxBlock block =  new BoxBlock(this.getName(),this.getId(),this.getAlpha());
        block.itemDefinition =itemDefinition;
        return block;
    }
}
