package cola.machine.game.myblocks.model;

import com.dozenx.util.ByteUtil;

/**
 * directionblock has four direction 0 east 1 south 2 west 3 North
 */
public class DirectionSixBlock extends StateBlock{
    public DirectionSixBlock(String name, int id, boolean isAlpha){
        super(name,id,isAlpha);
    }
    public int dir=0;//0 east 1 south 2 west 3 North
    public int updown =0;
    

    @Override
    public void setValue(int value) {
        super.setValue(value);
        dir= ByteUtil.get9_8Value(value);
        updown = ByteUtil.get10Value(value);
    }

    public int getValue(){

        return (((updown>0?ByteUtil.BIT_0100 : 0 )& dir)<<8 )& id;
    }
}
