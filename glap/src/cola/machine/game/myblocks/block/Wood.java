package cola.machine.game.myblocks.block;


/**
 * Created by luying on 14-8-30.
 */
public class Wood extends Block {
    public Wood(){
        super();
        setId((short)1);
        setName("wood");
        setDisplayName("木头");
        setTargetable(true);
        this.setHardness(3);

        setLiquid(false);
        setPassable(false);
        setTransparent(false);
        setReplaceable(false);
    }
        
}
