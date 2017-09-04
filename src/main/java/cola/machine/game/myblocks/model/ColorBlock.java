package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FloatBufferWrap;
import com.dozenx.util.MapUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

public class ColorBlock extends BaseBlock{
    public ColorBlock(){

    }

    public ColorBlock(float x,float y,float z,float width,float height,float thick){
        this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;

    }
    public ColorBlock(float x,float y,float z,float width,float height,float thick,float rf,float gf,float bf,float opacity){
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

/*    public int x = 0;
	public int y = 0;
	public int z = 0;*/
    int beishu =5;//缩小的尺寸
	public int red = 0;
	public int blue = 0;
	public int green = 0;
    public float rf =0;
    public float bf=0;
    public float gf=0;
    public float opacity =1;
   /* public float width=1;
    public float height=1;
    public float thick=1;*/
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
	public ColorBlock(float x, float y, float z) {
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

	public ColorBlock(float x, float y, float z, Color color) {
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

@Override//这个方法应该被废弃 p1p2p3p4p5p6p7都应该被废弃 转而使用 points
    public void renderShader(ShaderConfig config , Vao vao ,GL_Matrix rotateMatrix) {
        // Front Face
        FloatBufferWrap floatBuffer = vao.getVertices();
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

    /**
     * 带旋转的使用
     * @param config
     * @param rotateMatrix
     */

  /*  public void renderShader(ShaderConfig config ,Vao vao , GL_Matrix rotateMatrix) {
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
*/

   /* @Override
    public void renderShader(Vao vao,ShapeFace shapeFace,TextureInfo ti,int x,int y,int z) {
        ShaderUtils.draw3dColorBox(ShaderManager.terrainShaderConfig,vao,x,y,z,ti.color,1,1);
    }*/

    /**
     * 长长使用再在group中
     */
    @Override //静态方块的绘制
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao,float parentX,float parentY,float parentZ, float childX,float childY,float childZ,float childWidth,float childHeight,float childThick,boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back){
       //ShaderUtils.draw3dColorBox(config, vao, childX, childY, childZ, new GL_Vector(rf, gf, bf), childWidth, childHeight, childThick, /*selectBlockList.size()>0?0.5f:*/this.opacity);
        ShaderUtils.draw3dColorBox(config, vao, parentX,parentY,parentZ,points, BoxModel.dirAry,rf, gf, bf,this.opacity  );

    }

    @Override  //生物的动画
    public void renderShaderInGivexyzwht(ShaderConfig config, Vao vao, GL_Matrix matrix, GL_Vector[] childPoints) {
        
        
        ShaderUtils.draw3dColorBox(config, vao, matrix ,childPoints,new GL_Vector(rf, gf, bf),this.opacity);
    }

    /**
     * 在chunk当中直接使用
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
     */
    @Override
    public void render(ShaderConfig config, Vao vao, float x, float y, float z, boolean top, boolean bottom, boolean left, boolean right, boolean front, boolean back) {
        ShaderUtils.draw3dColorBox(config, vao, x, y, z,points, BoxModel.dirAry,rf, gf, bf,this.opacity  );
     /*   ShaderUtils.draw3dColorBox(config, vao, x, y, z, new GL_Vector(rf, gf, bf), 1, 1, 1, *//*selectBlockList.size()>0?0.5f:*//*this.opacity,
                top,bottom,left,right,front,right);*/
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
        super.beuse();
        return false;
    }


    @Override
    public IBlock clone(){
        return new ColorBlock(x,y,z);
    }

    @Override
    public void beAttack() {

    }




    public ColorBlock copy(){
        ColorBlock colorBlock  =new ColorBlock(this.x,this.y,this.z,this.width,this.height,this.thick,this.rf,this.gf,this.bf,this.opacity);
        colorBlock.id= id;
        return colorBlock;
    }


  /*  public void update(){
        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x, y, z, new GL_Vector(rf, gf, bf), width, height, thick, *//*selectBlockList.size()>0?0.5f:*//*this.opacity);

    }*/
    public String toColorBlockString(){
        StringBuffer buffer =new StringBuffer();

        buffer.append("r:").append(this.rf).append(",")
                .append("g:").append(this.gf).append(",")
                .append("b:").append(this.bf).append(",")

                .append("a:").append(this.opacity).append(",");


        return buffer.toString();
    }

    public String toString(){
       StringBuffer buffer =new StringBuffer();
        buffer.append("{")
                .append("blocktype:'colorblock',")
               .append(toBaseBlockString())
                .append(toColorBlockString())

                .append("}");
        /*StringBuffer sb = new StringBuffer();
        sb.append(this.x).append(",").append(this.y).append(",").append(this.z).append(",")
                .append(this.width).append(",").append(this.height).append(",").append(this.thick).append(",")
                .append(this.rf).append(",").append(this.gf).append(",").append(this.bf);
        return sb.toString();*/
        return buffer.toString();
    }


    public static ColorBlock parse(JSONObject map){

        ColorBlock block =new ColorBlock();

        block. parseColor(block,map);




        return block;


    }
    public  void parseColor(ColorBlock block ,JSONObject map){
        parse(block,map);

        float red = MapUtil.getFloatValue(map,"r");
        float green = MapUtil.getFloatValue(map,"g");
        float blue = MapUtil.getFloatValue(map,"b");
        float alpha = MapUtil.getFloatValue(map,"a");

        block.rf= red;
        block.gf= green;
        block.bf= blue;

        block.opacity = alpha;


    }
}
