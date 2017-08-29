package cola.machine.game.myblocks.block;

/**
 * Created by luying on 14-10-17.
 */
public class Block {


   // private short cid;//child id
   private short id;
    private String name;
   // private BlockApperance blockApperance;//包括了顶点信息 对应的纹理 法线

    private String displayName;
    private boolean targetable;
    private float hardness;
    private boolean liquid;
    private boolean passable;
    private boolean transparent;
    private boolean replaceable;


/*

    String backTexture="";

    String topTexture="";
    String bottomTexture="";
    String leftTexture ="";
    String rightTexture="";
    String frontTexture="" ;

    public String getTopTexture() {
        return topTexture;
    }

    public void setTopTexture(String topTexture) {
        this.topTexture = topTexture;
    }

    public void setAllSideTexture(String text){
        this.frontTexture=this.backTexture=this.topTexture=this.bottomTexture=
                this.leftTexture=this.rightTexture=text;
    }



    public String getBottomTexture() {
        return bottomTexture;
    }

    public void setBottomTexture(String bottomTexture) {
        this.bottomTexture = bottomTexture;
    }





    public void setBlockApperance(BlockApperance blockApperance) {
        this.blockApperance = blockApperance;
    }
    public BlockApperance getBlockApperance() {
        return blockApperance;
    }


    //the collisionbox

    //some block has it's own rotation
    //难道所有的

    public short getCid() {
        return cid;
    }

    public void setCid(short cid) {
        this.cid = cid;
    }

    */

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public boolean isTargetable() {
        return targetable;
    }

    public void setTargetable(boolean targetable) {
        this.targetable = targetable;
    }


    public void setId(short id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public void setLiquid(boolean liquid) {
        this.liquid = liquid;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public void setReplaceable(boolean replaceable) {
        this.replaceable = replaceable;
    }


    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public float getHardness() {
        return hardness;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isReplaceable() {
        return replaceable;
    }


}
