package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.AABB.AABB;
import cola.machine.game.myblocks.model.textture.TextureInfo;

/**
 * 方块实体 如果你想找的是方块说明 那么应该找 blockDefinition
 */
public abstract class BaseBlock extends AABB implements IBlock {
    //是否是透明
    boolean alpha =false;
    //是否可以通过
    public boolean penetration=false;
    //
    public int id=0;

    ItemDefinition itemDefinition;

	public void renderShader(ShaderConfig config , GL_Matrix matrix){

	}
    public BaseBlock(int x,int y,int z,float width,float height,float thick){
            this.x =x;
        this.y=y;
        this.z =z ;
        this.width =width;
        this.height = height;
        this.thick=thick;
    }

    public ChunkImpl chunk;


	public int x=0;//0~16
	public int y=0;//0~128
	public int z=0;//0~16
    public float width;
    public float height;
    public float thick;
    public int chunkX=0;
    public int chunkY=0;

	/*public int red=0;
	public int blue=0;
	public int green=0;*/




    public void setName(String name){
        this.name = name;
    }

	public String getName(){
		return name;
	}

    @Override
    public void setValue(int value) {

    }

    @Override
    public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z) {
       
    }

    public String name;
	public BaseBlock(String name,int x,int y,int z){
		this.name=name;
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x-1;
		this.minY=y-1;
		this.minZ=z-1;
		this.maxX=x+1;
		this.maxY=y+1;
		this.maxZ=z+1;
	}
	/*public BaseBlock(String name,int x,int y,int z,Color color){
		this.name=name;
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x-1;
		this.minY=y-1;
		this.minZ=z-1;
		this.maxX=x+1;
		this.maxY=y+1;
		this.maxZ=z+1;
		this.red=color.r();
		this.blue=color.b();
		this.green=color.g();
	}*/
	public BaseBlock(String name,int id,boolean alpha){
		this.name=name;
		this.id=id;
        this.alpha=alpha;
	}
    public boolean getAlpha(){
        return this.alpha;
    }

	public BaseBlock(){
		
	}
		
	
	public void renderCube(){
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
        // Back Face
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
        GL11.glEnd();
	}
	
	public void render(){
		 // Front Face
		TextureInfo ti=TextureManager.getTextureInfo(getName());
	    GL11.glBegin(GL11.GL_QUADS);
	        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	        // Back Face
	        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	       // Top Face
	        
	        
	     // Top Face
	        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       // Bottom Face
	        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        // Right face
	        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        // Left Face
	        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
	        GL11.glTexCoord2f(ti.minX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	        GL11.glTexCoord2f(ti.maxX,ti.minY); GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glTexCoord2f(ti.maxX,ti.maxY); GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	        GL11.glTexCoord2f(ti.minX,ti.maxY); GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glEnd();
	}
	
	public void renderColor(){
		 // Front Face
		TextureInfo ti=TextureManager.getTextureInfo(getName());
	    GL11.glBegin(GL11.GL_QUADS);
	        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
	        GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	       GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	        GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	        // Back Face
	        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
	       GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	       // Top Face
	        
	        
	     // Top Face
	        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
	        GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Left
	         GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Bottom Right
	        GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	       // Bottom Face
	        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
	       GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Top Right
	        GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Top Left
	        GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	     GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	        // Right face
	        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
	       GL11.glVertex3f( 1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Right
	        GL11.glVertex3f( 1.0f+x,  1.0f+y, -1.0f+z);	// Top Right
	        GL11.glVertex3f( 1.0f+x,  1.0f+y,  1.0f+z);	// Top Left
	         GL11.glVertex3f( 1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Left
	        // Left Face
	        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
	       GL11.glVertex3f(-1.0f+x, -1.0f+y, -1.0f+z);	// Bottom Left
	       GL11.glVertex3f(-1.0f+x, -1.0f+y,  1.0f+z);	// Bottom Right
	       GL11.glVertex3f(-1.0f+x,  1.0f+y,  1.0f+z);	// Top Right
	         GL11.glVertex3f(-1.0f+x,  1.0f+y, -1.0f+z);	// Top Left
	        GL11.glEnd();
	}
    //设置中心点
	public void setCenter(int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x;
		this.minY=y;
		this.maxX=x+1;
		this.maxY=y+1;
	}
	@Override
	public int getX() {
		// VIP Auto-generated method stub
		return x;
	}
	@Override
	public int getY() {
		// VIP Auto-generated method stub
		return y;
	}
	@Override
	public int getZ() {
		// VIP Auto-generated method stub
		return z;
	}

/*
	@Override
	public int b() {
		return blue;
	}
	@Override
	public int g() {
		return green;
	}
	@Override



    public int r() {
        return red;
    }
    public float rf(){
        return this.r()*1f;
    }
    public float bf(){
        return this.r()*1f;
    }
    public float gf(){
        return this.r()*1f;
    }
*/

	public int getId() {
		// VIP Auto-generated method stub
		return id;
	}

    public boolean use(){
        return false;
    }
    @Override
    public boolean beuse(){
        return false;
    }

 @Override
    public IBlock clone(){
        return null;
    }


    @Override
    public void setChunk(ChunkImpl chunk){
        this.chunk= chunk;
    }

    @Override
    public boolean isPenetrate() {
        return penetration;
    }

    @Override
    public void setPenetrate(boolean penetration) {
        this.penetration =penetration;
    }

    @Override
    public ChunkImpl getChunk(){
        return chunk;
    }

    public void beAttack(){
        int chunkX = chunk.chunkPos.x;
        int chunkZ = chunk.chunkPos.z;

        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = x;//this.getX();
        cmd.cy =y;//this.getX();
        cmd.cz =z;// this.getZ();

        if(cmd.cy<0){
            LogUtil.err("y can't be <0 ");
        }
        cmd.type = 2;
        //blockType 应该和IteType类型联系起来
        cmd.blockType = 0;

        CoreRegistry.get(Client.class).send(cmd);
    }




    public boolean zh=true;
    public boolean zl=true;
    public boolean yl=true;
    public boolean yh=true;
    public boolean xl=true;
    public boolean xh=true;

    public boolean isZh() {
        return zh;
    }

    public void setZh(boolean zh) {
        this.zh = zh;
    }

    public boolean isZl() {
        return zl;
    }

    public void setZl(boolean zl) {
        this.zl = zl;
    }

    public boolean isYl() {
        return yl;
    }

    public void setYl(boolean yl) {
        this.yl = yl;
    }

    public boolean isYh() {
        return yh;
    }

    public void setYh(boolean yh) {
        this.yh = yh;
    }

    public boolean isXl() {
        return xl;
    }

    public void setXl(boolean xl) {
        this.xl = xl;
    }

    public boolean isXh() {
        return xh;
    }

    public void setXh(boolean xh) {
        this.xh = xh;
    }


    public abstract void update(float x, float y, float z, float width, float height, float thick);
    public abstract void update();

    public abstract  BaseBlock copy();
    public void addWidth(int num){
        this.width+=num;
    }
    public void addX(int num){
        this.x+=num;
    }

    public void addHeight(int num){
        this.height+=num;
    }
    public void addY(int num){
        this.y+=num;
    }

    public void addThick(int num){
        this.thick+=num;
    }
    public void addZ(int num){
        this.z+=num;
    }
}
