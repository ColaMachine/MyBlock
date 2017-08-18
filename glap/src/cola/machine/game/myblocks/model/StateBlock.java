package cola.machine.game.myblocks.model;

import com.dozenx.util.ByteUtil;

public class StateBlock extends BaseBlock{
  int state;
    int value16_12 ;
    int value12_8;
    int value8_4;
    int value4_0;

    public StateBlock(String name , int id, boolean isAlpha){
        super(name,id,isAlpha);
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
