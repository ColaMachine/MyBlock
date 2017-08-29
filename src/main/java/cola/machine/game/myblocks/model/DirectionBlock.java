package cola.machine.game.myblocks.model;

public class DirectionBlock extends StateBlock{
    public DirectionBlock(String name, int id, boolean isAlpha){
        super(name,id,isAlpha);
    }
    public int dir=0;


    @Override
    public void setValue(int value) {
        super.setValue(value);

        //获取condition
        dir = value12_8;
    }

}
