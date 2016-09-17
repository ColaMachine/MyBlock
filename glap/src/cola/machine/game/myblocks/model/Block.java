package cola.machine.game.myblocks.model;


public interface  Block {
	public String getName();



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



    public void render();
	public void renderCube();
	public void renderColor();
	public void setCenter(int x,int y,int z);
	public int getX();
	public int getY();
	public int getZ();
	public int r();
    public float rf();
	public int b();
    public float bf();
	public int g();
    public float gf();
	public int getId();
    public boolean getAlpha();
}
