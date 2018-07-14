package cola.machine.game.myblocks.model;

/**
 * 方块实体 如果你想找的是方块说明 那么应该找 blockDefinition
 */
public abstract class BlockStatus{
    //是否是透明
    boolean alpha =false;
    //是否可以通过
    public boolean penetration=false;

    public boolean live =false;
    
  
    //
    public int dir;
    public int id=0;
  


}
