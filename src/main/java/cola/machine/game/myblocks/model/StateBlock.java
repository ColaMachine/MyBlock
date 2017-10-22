package cola.machine.game.myblocks.model;

import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ByteUtil;
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


    public void update(float x, float y, float z, float width, float height, float thick) {

    }


    public void update() {

    }

    @Override
    public BaseBlock copy() {
        return null;
    }

    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

    @Override
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, float parentX,float parentY,float parentZ, float childX,float childY,float childZ,float width, float height, float thick, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {

    }

//    @Override
//    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {
//
//    }

    @Override
    public void renderShader(ShaderConfig config, Vao vao, GL_Matrix matrix) {

    }

    public void setValue(int value) {
        int hightByte = ByteUtil.get16_8Value(value);
        value16_12 =ByteUtil.get16_12Value(value);
        value12_8 = ByteUtil.get12_8Value(value);;


    }

    @Override
    public IBlock clone(){
        BoxBlock block =  new BoxBlock(this.getName(),this.getId(),this.getAlpha());
        block.itemDefinition =itemDefinition;
        return block;
    }
}
