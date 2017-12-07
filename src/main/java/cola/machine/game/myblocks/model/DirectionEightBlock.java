package cola.machine.game.myblocks.model;

/**
 * directionblock has four direction 0 east 1 south 2 west 3 North
 */
public class DirectionEightBlock extends StateBlock{
    public DirectionEightBlock(String name, int id, boolean isAlpha){
        super(name,id,isAlpha);
    }
    public int dir=0;//0 east 1 south 2 west 3 North
    

    @Override
    public void setValue(int value) {
        super.setValue(value);

        //获取condition
        dir = value12_8;
    }

}
