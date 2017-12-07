package cola.machine.game.myblocks.model;

import com.dozenx.util.ByteUtil;

/**
 * directionblock has four direction 0 east 1 south 2 west 3 North
 */
public class DirectionFourBlock extends StateBlock{
    public DirectionFourBlock(String name, int id, boolean isAlpha){
        super(name,id,isAlpha);
    }
    public int dir=0;//0 east 1 south 2 west 3 North


    @Override
    public void setValue(int value) {
        super.setValue(value);
        dir= ByteUtil.get9_8Value(value);



    }

    public int getValue(){

        return ( dir<<8 )& id;
    }

}
