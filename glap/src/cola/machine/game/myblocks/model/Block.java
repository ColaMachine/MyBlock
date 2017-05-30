package cola.machine.game.myblocks.model;


import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.dozenx.game.opengl.util.ShaderConfig;
import glmodel.GL_Matrix;

public interface  Block {
	public String getName();
    public void setChunk(ChunkImpl chunk);

    public boolean isPenetrate();
    public void setPenetrate(boolean penetrate);
    public ChunkImpl getChunk();

    public void setValue(int value);
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


    public void renderShader(ShaderConfig config , GL_Matrix matrix);
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

    public boolean use();
    public boolean beuse();

    public Block clone();

    public void beAttack();
}
