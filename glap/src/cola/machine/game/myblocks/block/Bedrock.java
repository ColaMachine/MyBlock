package cola.machine.game.myblocks.block;

/**
 * Created by luying on 14-10-18.
 */
public class Bedrock extends Block{
    public Bedrock(){
        super();
        setId((short)7);
        setName("mantle");
        setDisplayName("基岩");
        setTargetable(true);
        this.setHardness(5);
        //setAllSideTexture("mantle");
        setLiquid(false);
        setPassable(false);
        setTransparent(true);
        setReplaceable(true);
    }
}
