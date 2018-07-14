package cola.machine.game.myblocks.model;


import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;

public interface IBlock {
	public String getName();
    public void setChunk(ChunkImpl chunk);

    public boolean isPenetrate();
    public boolean isSpecial();
    public void setPenetrate(boolean penetrate);
    public ChunkImpl getChunk();

    public void setValue(int value);


    /**color block de xuanran fangshi 的渲染方式*/
   // public void renderShader(ShaderConfig config , GL_Matrix matrix);
    /**其他特殊方块的渲染方式**/
 //   public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z);
    public void render();
	public void renderCube();
	public void renderColor();
	public void set(int x,int y,int z);
	public int getX();
	public int getY();
	public int getZ();

    //如果是颜色方块会用到
	/*public int r();
    public float rf();
	public int b();
    public float bf();
	public int g();
    public float gf();*/


	public int getId();
    public boolean getAlpha();

    public boolean use();
    public boolean beuse();

    public IBlock copy();

    public void beAttack();

    //应该是判断 隔壁的是否有方块

    public boolean isZh();

    public void setZh(boolean zh);

    public boolean isZl();

    public void setZl(boolean zl) ;

    public boolean isYl();
    public void setYl(boolean yl) ;

    public boolean isYh() ;

    public void setYh(boolean yh) ;

    public boolean isXl();
    public void setXl(boolean xl) ;

    public boolean isXh() ;

    public void setXh(boolean xh);
}
