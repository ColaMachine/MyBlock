package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.AABB.AABB;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.MapUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * 方块实体 如果你想找的是方块说明 那么应该找 blockDefinition
 */
public abstract class BaseBlock extends AABB implements IBlock {
    public int blood =5;
    boolean delete =false;
    //是否是透明
    boolean alpha =false;
    //是否可以通过
    public boolean penetration=false;

    public boolean live =false;
    
    public float zuni =0;
    //
    public int dir;
    public int id=0;
    public ChunkImpl chunk;

    public int light = 0 ;
    public float x=0;//0~16
    public float y=0;//0~128
    public float z=0;//0~16
    public float width=1;
    public float height=1;
    public float thick=1;
    public int chunkX=0;
    public int chunkY=0;


    public String name;
    public boolean zh=true;
    public boolean zl=true;
    public boolean yl=true;
    public boolean yh=true;
    public boolean xl=true;
    public boolean xh=true;

    public GL_Vector[] points =BoxModel.getPoint(0,0,0);//容易发生堆溢出
    ItemDefinition itemDefinition;
    
    public BaseBlock(){
        
    }
  
    public BaseBlock(float x,float y,float z,float width,float height,float thick){
            this.x =x;
        this.y=y;
        this.z =z ;
        this.width =width;
        this.height = height;
        this.thick=thick;
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getThick() {
        return thick;
    }

    public void setThick(float thick) {
        this.thick = thick;
    }



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

    /*@Override
    public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z) {
       
    }*/


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
    public void set(int x ,int y ,int z ){
        this.x = x;
        this.y = y ;
         this .z = z;
    }
    //设置中心点
	/*public void setCenter(int x,int y,int z){
		this.x=x;
		this.y=y;
		this.z=z;
		
		this.minX=x;
		this.minY=y;
		this.maxX=x+1;
		this.maxY=y+1;
	}*/
	@Override
	public int getX() {
		// VIP Auto-generated method stub
		return (int)x;
	}
	@Override
	public int getY() {
		// VIP Auto-generated method stub
		return (int)y;
	}
	@Override
	public int getZ() {
		// VIP Auto-generated method stub
		return (int)z;
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

        if(this.itemDefinition == null){
            this.itemDefinition =   ItemManager.getItemDefinition(this.id);
        }
        if(this.itemDefinition!=null ){

            return itemDefinition .beUsed(this);

        }
        
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
        cmd.cx = (int)x;//this.getX();
        cmd.cy =(int)y;//this.getX();
        cmd.cz =(int)z;// this.getZ();

        if(cmd.cy<0){
            LogUtil.err("y can't be <0 ");
        }
        cmd.type = 2;
        //blockType 应该和IteType类型联系起来
        cmd.blockType = 0;

        CoreRegistry.get(Client.class).send(cmd);
    }


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


 /*   public abstract void update(float x, float y, float z, float width, float height, float thick);
    public abstract void update();*/

    public abstract  BaseBlock copy();
    public void copyBaseBlock(BaseBlock block){
        block.x=this.x;
        block.y=this.y;
        block.z=z;
        block.width=width;
        block.id=id;
        block.penetration=penetration;
        block.height=height;
        block.thick =thick;
        block.live = live;
        block.name =name;
    }
    public  void reComputePoints(){
        this.points = BoxModel.getSmallPoint(0,0,0,width,height,thick);
       // this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
        GL_Matrix rotateMatrix = GL_Matrix.multiply(GL_Matrix.multiply(GL_Matrix.translateMatrix(width/2,0,thick/2),GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0)),GL_Matrix.translateMatrix(-width/2,0,-thick/2) );
      //  GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0);
        for(int i=0;i<points.length;i++){
            points[i] = rotateMatrix.multiply(rotateMatrix,points[i]);

        }
    }
    public  void reComputePointsInGroup(){
        this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
        // this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);
        GL_Matrix rotateMatrix = GL_Matrix.multiply(GL_Matrix.multiply(GL_Matrix.translateMatrix(width/2,0,thick/2),GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0)),GL_Matrix.translateMatrix(-width/2,0,-thick/2) );
        //  GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0);
        for(int i=0;i<points.length;i++){
            points[i] = rotateMatrix.multiply(rotateMatrix,points[i]);

        }
    }


    public  void reComputePoints(GL_Matrix rotateMatrix){
        this.points = BoxModel.getSmallPoint(x,y,z,width,height,thick);

        GL_Matrix rotateMatrix1 = GL_Matrix.rotateMatrix(0,this.dir*3.14f/2,0);
        for(int i=0;i<points.length;i++){
            points[i] = rotateMatrix.multiply(rotateMatrix,points[i]);

        }
    }
    public static void main(String args[]){
        GL_Matrix rotate = GL_Matrix.translateMatrix(0.5f,0,0.5f);

         rotate = GL_Matrix.multiply(rotate,GL_Matrix.rotateMatrix(0,0*3.14f/2,0));
        rotate =GL_Matrix.multiply(rotate,GL_Matrix.translateMatrix(-0.5f, 0, -0.5f));
        GL_Vector point = GL_Matrix.multiply(rotate,new GL_Vector(0,0,0.4f));
        System.out.println(point);
    }

    public float addWidth(float num){
        this.width+=num;
        reComputePoints();
        return this.width;
    }
    public float addX(float num){
        this.x+=num;
        reComputePoints();
        return this.x;
    }

    public float addHeight(float num){
        this.height+=num; reComputePoints();
        return this.height;
    }
    public float addY(float num){
        this.y+=num; reComputePoints();
        return this.y;
    }

    public float addThick(float num){
        this.thick+=num; reComputePoints();
        return this.thick;
    }
    public float addZ(float num){
        this.z+=num; reComputePoints();
        return this.z;
    }

    /**
     * 最简单的渲染 因为不能支持旋转面临被替换的风险
     * @param config
     * @param vao
     * @param x
     * @param y
     * @param z
     * @param top
     * @param bottom
     * @param left
     * @param right
     * @param front
     * @param back
     * @author 张智威  
     * @date 2017年8月31日 下午6:05:56
     */
    public abstract  void render(ShaderConfig config, Vao vao, float x,float y,float z ,boolean top,
                                 boolean bottom,boolean left,boolean right,boolean front ,boolean back);
/**
 * 现在使用在骨骼系统中的渲染 用于实时计算 性能有点差 其他都很好
 * @param config
 * @param vao
 * @param matrix
 * @author 张智威  
 * @date 2017年8月31日 下午6:07:07
 */
    public  abstract void renderShader(ShaderConfig config , Vao vao , GL_Matrix matrix);
/**
 * 在colorgroup中渲染时候用这种方式 但是参数太多 而且不支持旋转
 * @param config
 * @param vao
 * @param parentX
 * @param parentY
 * @param parentZ
 * @param childX
 * @param childY
 * @param childZ
 * @param width
 * @param height
 * @param thick
 * @param top
 * @param bottom
 * @param left
 * @param right
 * @param front
 * @param back
 * @author 张智威  
 * @date 2017年8月31日 下午6:07:32
 */
    public abstract void renderShaderInGivexyzwht(ShaderConfig config, Vao vao,float parentX,float parentY,float parentZ, float childX,float childY,float childZ,float width,float height,float thick,boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back);
    /**
     * 一种支持旋转的折中方案 但是需要先计算几个点的位置 再editEngine 中渲染 childPoints参数几乎不用了
     * @param config
     * @param vao
     * @param matrix是父亲的旋转逻辑
     * @param childPoints 都是事先计算好的
     * @author 张智威  
     * @date 2017年8月31日 下午6:08:34
     */
    //public abstract void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints);

   public  String toBaseBlockString(){
       StringBuffer buffer = new StringBuffer();
       buffer.append("name:\"").append(this.name).append("\",")
               .append("width:").append(this.width).append(",").append("height:").append(this.height).append(",")
               .append("thick:").append(this.thick).append(",").append("x:").append(this.x).append(",").append("y:")
               .append(this.y).append(",").append("z:").append(this.z).append(",")
               .append("dir:").append(this.dir).append(",")
               .append("penetration:").append(this.penetration).append(",")
               .append("zuni:").append(this.zuni).append(",")
               .append("live:")

               .append(live).append(",").append("points:").append(JSON.toJSON(this.points)).append(",");






       return buffer.toString();
   }
    public static void parse( BaseBlock  block , JSONObject map){
        float x = MapUtil.getFloatValue(map,"x",0f);
        float y = MapUtil.getFloatValue(map,"y",0f);
        float z = MapUtil.getFloatValue(map,"z",0f);
        int dir = MapUtil.getIntValue(map, "dir",0);
        float zuni = MapUtil.getFloatValue(map,"zuni",0f);
        String name = MapUtil.getStringValue(map,"name");

        boolean penetration = MapUtil.getBooleanValue(map,"penetration",false);
        String pointsStr = MapUtil.getStringValue(map,"points");
        if(StringUtil.isNotEmpty(pointsStr)){
           /* JSONArray jsonArray = JSON.parseArray(pointsStr,GL_Vector.class);
            for(int i=0;i<jsonArray.size();i++){
                block.points = new GL_Vector[]
            }*/
            List list = JSON.parseArray(pointsStr,GL_Vector.class);
            GL_Vector[] pointAry = new GL_Vector[list.size()];
            block.points  =(GL_Vector[] )list.toArray(pointAry) ;
        }
        block.zuni= zuni;
        block.name =name;
        block.penetration = penetration;
        float width = MapUtil.getFloatValue(map,"width");
        float height = MapUtil.getFloatValue(map,"height");
        float thick = MapUtil.getFloatValue(map,"thick");
        block.x=x;
        block.y=y;
        block.z=z;
        block.width=width;
        block.height=height;
        block.thick =thick;

    }

    public float rotateY(float value){
        this.dir++;
        if(this.dir>=4){
            this.dir=0;
        }
        reComputePoints();
        return this.dir;
    }
    public void scale(float xzoom,float yzoom,float zzoom){
        this.x=this.x*xzoom;
        this.y= this.y*yzoom;
        this.z=this.z*zzoom;
        this.width = this.width * xzoom;
        this.height =this.height*yzoom;
        this.thick =this.thick*zzoom;
    }

}
