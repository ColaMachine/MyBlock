package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.FloatBufferWrap;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.model.AABB.AABB;

import java.nio.FloatBuffer;

public class ColorBlock extends BaseBlock{
    public ColorBlock(){

    }

    public ColorBlock(int x,int y,int z,float width,float height,float thick){
        this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;

    }
    public ColorBlock(int x,int y,int z,float width,float height,float thick,float rf,float gf,float bf,float opacity){
        this.x =x;
        this.y=y;
        this.z=z;
        this.opacity = opacity;
        this.width =width;
        this.height =height;
        this.thick =thick;
        this.rf =rf;
        this.gf =gf;
        this.bf =bf;
    }
    /*public boolean selected =false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }*/

    public int x = 0;
	public int y = 0;
	public int z = 0;
    int beishu =5;//缩小的尺寸
	public int red = 0;
	public int blue = 0;
	public int green = 0;
    public float rf =0;
    public float bf=0;
    public float gf=0;
    public float opacity =1;
    public float width=1;
    public float height=1;
    public float thick=1;
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
    public float rf(){
        return this.rf;
    }
    public float bf(){
        return this.bf;
    }
    public float gf(){
        return this.gf;
    }
    public ColorBlock(String name,int id,boolean alpha) {
        super( name, id, alpha);
    }
	public ColorBlock(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.minX = x;
		this.minY = y;
		this.minZ = z;
		this.maxX = x + 1;
		this.maxY = y + 1;
		this.maxZ = z + 1;
	}

	public ColorBlock(int x, int y, int z, Color color) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.minX = x;
		this.minY = y;
		this.minZ = z;
		this.maxX = x + 1;
		this.maxY = y + 1;
		this.maxZ = z + 1;

		this.red = color.r();
		this.blue = color.b();
		this.green = color.g();
        this.rf=this.red*1f/256;
        this.gf=this.green*1f/256;
        this.bf=this.blue*1f/256;

         P1 =new GL_Vector(minX/beishu, minY/beishu, maxZ/beishu);
         P2 =new GL_Vector(maxX/beishu, minY/beishu, maxZ/beishu);
         P3 =new GL_Vector(maxX/beishu, minY/beishu, minZ/beishu);
         P4 =new GL_Vector(minX/beishu, minY/beishu, minZ/beishu);

         P5 =new GL_Vector(minX/beishu, maxY/beishu, maxZ/beishu);
         P6 =new GL_Vector(maxX/beishu, maxY/beishu, maxZ/beishu);
         P7 =new GL_Vector(maxX/beishu, maxY/beishu, minZ/beishu);
         P8 =new GL_Vector(minX/beishu, maxY/beishu, minZ/beishu);
	}
    GL_Vector P1 =new GL_Vector(minX/beishu, minY/beishu, maxZ/beishu);
    GL_Vector P2 =new GL_Vector(maxX/beishu, minY/beishu, maxZ/beishu);
    GL_Vector P3 =new GL_Vector(maxX/beishu, minY/beishu, minZ/beishu);
    GL_Vector P4 =new GL_Vector(minX/beishu, minY/beishu, minZ/beishu);

    GL_Vector P5 =new GL_Vector(minX/beishu, maxY/beishu, maxZ/beishu);
    GL_Vector P6 =new GL_Vector(maxX/beishu, maxY/beishu, maxZ/beishu);
    GL_Vector P7 =new GL_Vector(maxX/beishu, maxY/beishu, minZ/beishu);
    GL_Vector P8 =new GL_Vector(minX/beishu, maxY/beishu, minZ/beishu);


    public void renderShader(ShaderConfig config , GL_Matrix rotateMatrix) {
        // Front Face
        FloatBufferWrap floatBuffer = config.getVao().getVertices();
        GL_Vector color = new GL_Vector(this.rf(),this.gf(),this.bf());

            if(zh) {
                // ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P1,P2,P6,P5,new GL_Vector(0,0,1f),front);
                ShaderUtils.draw3dColor(P1,P2,P6,P5,rotateMatrix,new GL_Vector(0,0,1f),color,floatBuffer, config);
            }
            if(zl) {
                //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
                ShaderUtils.draw3dColor(P3,P4,P8,P7,rotateMatrix,new GL_Vector(0,0,-1),color,floatBuffer, config);
            }
            if(yh) {
                //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P5,P6,P7,P8,new GL_Vector(0,1,0f),front);
                ShaderUtils.draw3dColor(P5,P6,P7,P8,rotateMatrix,new GL_Vector(0,1,0),color,floatBuffer, config);
            }

            if(yl) {
                ShaderUtils.draw3dColor(P4,P3,P2,P1,rotateMatrix,new GL_Vector(0,-1,0),color,floatBuffer, config);
            }
            if(xl) {
                ShaderUtils.draw3dColor(P2,P3,P7,P6,rotateMatrix,new GL_Vector(-1,0,0f),color,floatBuffer, config);
            }
            if(xh) {
                ShaderUtils.draw3dColor(P4,P1,P5,P8,rotateMatrix,new GL_Vector(1,0,0),color,floatBuffer, config);
            }

    }
	public void render() {
		// Front Face



		GL11.glBegin(GL11.GL_QUADS);
        if(zh){
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glVertex3f(minX, minY, maxZ); // Bottom Left
		GL11.glVertex3f(maxX, minY, maxZ); // Bottom Right
		GL11.glVertex3f(maxX, maxY, maxZ); // Top Right
		GL11.glVertex3f(minX, maxY, maxZ); // Top Left
        }
		// Back Face
        if(zl) {
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            GL11.glVertex3f(minX, minY, minZ); // Bottom Right
            GL11.glVertex3f(minX, maxY, minZ); // Top Right
            GL11.glVertex3f(maxX, maxY, minZ); // Top Left
            GL11.glVertex3f(maxX, minY, minZ); // Bottom Left
        }
		// Top Face
		// Top Face
        if(yh) {
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glVertex3f(minX, maxY, minZ); // Top Left
            GL11.glVertex3f(minX, maxY, maxZ); // Bottom Left
            GL11.glVertex3f(maxX, maxY, maxZ); // Bottom Right
            GL11.glVertex3f(maxX, maxY, minZ); // Top Right
        }
        if(yl) {
            // Bottom Face
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
            GL11.glVertex3f(minX, minY, minZ); // Top Right
            GL11.glVertex3f(maxX, minY, minZ); // Top Left
            GL11.glVertex3f(maxX, minY, maxZ); // Bottom Left
            GL11.glVertex3f(minX, minY, maxZ); // Bottom Right
        }
		// Right face
        if(xh) {
            GL11.glNormal3f(1.0f, 0.0f, 0.0f);
            GL11.glVertex3f(maxX, minY, minZ); // Bottom Right
            GL11.glVertex3f(maxX, maxY, minZ); // Top Right
            GL11.glVertex3f(maxX, maxY, maxZ); // Top Left
            GL11.glVertex3f(maxX, minY, maxZ); // Bottom Left
        }
		// Left Face
        if(xl) {
            GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
            GL11.glVertex3f(minX, minY, minZ); // Bottom Left
            GL11.glVertex3f(minX, minY, maxZ); // Bottom Right
            GL11.glVertex3f(minX, maxY, maxZ); // Top Right
            GL11.glVertex3f(minX, maxY, minZ); // Top Left
        }
		GL11.glEnd();
	}

	public void setCenter(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.minX = x - 1;
		this.minY = y - 1;
		this.maxX = x + 1;
		this.maxY = y + 1;
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

	public int r() {
		return red;
	}

	//@Override
	public int b() {
		return blue;
	}

	//@Override
	public int g() {
		return green;
	}

	//@Override
	public String getName() {
		// VIP Auto-generated method stub
		return null;
	}

    @Override
    public void setChunk(ChunkImpl chunk) {

    }

    @Override
    public boolean isPenetrate() {
        return false;
    }

    @Override
    public void setPenetrate(boolean penetrate) {

    }



    @Override
    public ChunkImpl getChunk() {
        return null;
    }

    @Override
    public void setValue(int value) {

    }



    @Override
	public void renderCube() {
		// VIP Auto-generated method stub
		
	}

	@Override
	public void renderColor() {
		// VIP Auto-generated method stub
		
	}

	/*@Override
	public int getId() {
		// VIP Auto-generated method stub
		return 0;
	}*/
    public boolean getAlpha(){
        return false;
    }

    @Override
    public boolean use() {
        return false;
    }

    @Override
    public boolean beuse() {
        return false;
    }


    @Override
    public Block clone(){
        return new ColorBlock(x,y,z);
    }

    @Override
    public void beAttack() {

    }


    @Override
    public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z) {
        ShaderUtils.draw3dColorBox(ShaderManager.terrainShaderConfig,vao,x,y,z,ti.color,1,1);
    }

    public void update(){
        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x, y, z, new GL_Vector(rf, gf, bf), width, height, thick, /*selectBlockList.size()>0?0.5f:*/this.opacity);

    }
    public void update(float x,float y,float z,float width,float height,float thick){
        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x, y, z, new GL_Vector(rf, gf, bf), width, height, thick, /*selectBlockList.size()>0?0.5f:*/this.opacity);

    }

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

    public ColorBlock copy(){
        ColorBlock colorBlock  =new ColorBlock(this.x,this.y,this.z,this.width,this.height,this.thick,this.rf,this.gf,this.bf,this.opacity);

        return colorBlock;
    }
}
