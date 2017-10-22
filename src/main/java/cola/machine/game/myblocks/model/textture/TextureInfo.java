package cola.machine.game.myblocks.model.textture;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import core.log.LogUtil;
import glapp.GLImage;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

public class TextureInfo extends RegionArea{
	public GL_Vector color ;//颜色 与纹理句柄互斥
	/*public float minX=0;
	public float minY=0;
	public float maxX=0;
	public float maxY=0;*/
    public  GLImage img;
    public int textureHandle= 0;//纹理句柄
    public float x1;//左下角纹理坐标
    public float y1;//左下角纹理坐标
    public float owidth;//原始纹理宽度
    public float oheight;//原始纹理高度
    public float x2;//右上角纹理坐标
    public float y2;//右上角纹理坐标
    int[] splitx ;//是否是网格纹理
    int[] splity;//是否是网格纹理
    public String imageName;//图片的名称
    public String name;//
    int imgWidth ;//整张图片的宽度
    int imgHeight;//整张图片的宽度
    public TextureInfo(float minX,float minY,float maxX,float maxY){

		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
	}


    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    /*public TextureInfo(String imgName,float minX,float minY,float width,float height){
            this.x1=minX;
            this.y1=minY;
            this.owidth=width;
            this.oheight=height;
            this.x2=x1+owidth;
            this.y2=y2+oheight;
             img=TextureManager.getImage(imgName);
            withWH(minX/img.w, minY/img.h, width/img.w, height/img.h);
            textureHandle= img.textureHandle;

        }*/
   // public Texture texture;

    public int[] getSplitx() {
        return splitx;
    }

    public void setSplitx(int[] splitx) {
        this.splitx=new int[splitx.length+1];
        int centerX = (int)this.owidth -splitx[0]-splitx[1];
        this.splitx[0]=splitx[0];
        this.splitx[1]=centerX;
        this.splitx[2]=splitx[1];



        //this.splitx = splitx;
    }

    public int[] getSplity() {

        return splity;
    }

    public void setSplity(int[] splity) {

        this.splity=new int[splity.length+1];
        int centerY = (int)this.oheight -splity[0]-splity[1];
        this.splity[0]=splitx[0];
        this.splity[1]=centerY;
        this.splity[2]=splity[1];

        //原始纹理 拆分后还要计算出一个0~1的minX minY的值

        //纹理的原始坐标是左下角为0,0
        //绘制的顺序是从左下角顺序往上么
        //但是文档的坐标是左上角
        //要不从左上角开始好了
        //绘制的过程应该是这样的
        //先计算出左上角的纹理的minX minY maxX maxY
//        float t11[] =new float[2];
//        t11[0]=this.minX;
//        TextureInfo t11=new TextureInfo();
//        t11.minX = this.minX;
//        t11.minY = this.minY;
         childTis = new TextureInfo[this.splitx.length*this.splity.length];
        for(int y=0;y<this.splity.length;y++){
        for(int x=0;x<this.splitx.length;x++){

                TextureInfo ti =new TextureInfo();
                childTis[y*this.splity.length+x]=ti;
                ti.x1=this.x1+getSumValue(this.splitx,x);
                ti.y1=this.y1+getSumValue(this.splity,y);
                ti.owidth = this.splitx[x];
                ti.oheight = this.splity[y];
                ti.imgWidth = this.imgWidth;
                ti.imgHeight = this.imgHeight;
                ti.minX = ti.x1/this.imgWidth;
                ti.maxX = (ti.x1+ ti.owidth)/ti.imgWidth;

                ti.minY = ti.y1/this.imgHeight;
                ti.maxY = (ti.y1+ ti.oheight)/ti.imgHeight;
            }
        }

        //这里要开始绘制了
        //给定一个窗口坐标minX minY 给定一个宽度 给定一个高度
        //先假定是3*3网格的



      //  this.splity = splity;
    }
    float screenMinX;
    float screenMinY;
    float screenWidth;
    float screenHeight;

    TextureInfo[] childTis=null;
    public void apply( int posX, int posY,  int width, int height){

       for(int y=0;y<3;y++){


          // childTis[i*3+j].screenHeight=childTis[0*3+0].oheight;//等于原始的纹理高度
          // childTis[i*3+j].screenWidth=childTis[0*3+0].oheight;//等于原始的纹理高度
            for(int x=0;x<3;x++){
                if(y==0){
                    childTis[y*3+x].screenMinY = posY;
                }else{
                    childTis[y*3+x].screenMinY =  childTis[(y-1)*3+x].screenMinY+childTis[(y-1)*3+x].screenHeight ;
                }
                if(x==0){
                    childTis[y*3+x].screenMinX = posX;
                }else{
                    childTis[y*3+x].screenMinX =  childTis[y*3+(x-1)].screenMinX+childTis[y*3+(x-1)].screenWidth ;
                }
              //+上前面的x累计
              //  childTis[y*3+x].screenMinY = posY;
                //计算宽度
                if(y==0||y==2){//第一行和最后一行
                    //高度都为原始高度
                    childTis[y*3+x].screenHeight=childTis[y*3+x].oheight;//等于原始的纹理高度
                }else{
                    childTis[y*3+x].screenHeight=height-this.splity[0]-this.splity[this.splity.length-1];//等于剩余的宽度
                }
                //计算高度
                if(x==0||x==2){
                    childTis[y*3+x].screenWidth=childTis[y*3+x].owidth;//等于原始的纹理高度
                }else{
                    childTis[y*3+x].screenWidth=width-this.splitx[0]-this.splitx[this.splitx.length-1];//等于剩余的宽度
                }
            }

       }
    }
    public  void draw2dImg( int posX, int posY, float z, int width, int height) {

        //被绘制的对象也要被拆分成对应的网格 九宫格

        apply(posX,posY,width,height);
        Vao vao = ShaderManager.uiShaderConfig.getVao();
        int index = ShaderUtils.bindAndGetTextureIndex(ShaderManager.uiShaderConfig,this.textureHandle);


        for(int i=0;i<childTis.length;i++){
            TextureInfo ti =childTis[i] ;
            float left =( (float)childTis[i].screenMinX)/ Constants.WINDOW_WIDTH*2-1f;
            float top=(Constants.WINDOW_HEIGHT- ( (float)childTis[i].screenMinY))/Constants.WINDOW_HEIGHT*2-1f;
            float _height = ( (float)childTis[i].screenHeight)/Constants.WINDOW_HEIGHT*2;
            float _width =( (float)childTis[i].screenWidth)/Constants.WINDOW_WIDTH*2;
            GL_Vector p1 = new GL_Vector(left,top-_height,z);
            GL_Vector p2 = new GL_Vector(left+_width,top-_height,z);
            GL_Vector p3 = new GL_Vector(left+_width,top,z);
            GL_Vector p4 = new GL_Vector(left,top,z);

            vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(0).put(0).put(0).put(0);
            vao.getVertices().put(p2.x).put(p2.y).put(p2.z).put(ti.maxX).put(ti.minY).put(index).put(0).put(0).put(0).put(0);
            vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(0).put(0).put(0).put(0);
            vao.getVertices().put(p4.x).put(p4.y).put(p4.z).put(ti.minX).put(ti.maxY).put(index).put(0).put(0).put(0).put(0);
            vao.getVertices().put(p1.x).put(p1.y).put(p1.z).put(ti.minX).put(ti.minY).put(index).put(0).put(0).put(0).put(0);
            vao.getVertices().put(p3.x).put(p3.y).put(p3.z).put(ti.maxX).put(ti.maxY).put(index).put(0).put(0).put(0).put(0);
        }
    }
    public int  getSumValue(int ary[],int index){
        int sum =0 ;
        for(int i=0;i<index;i++){
            sum+=ary[i];
        }
        return sum;
    }

    public TextureInfo(String imgName,float leftDownX,float leftDownY,float width,float height,String name){
        this.x1=leftDownX;
        this.name=name;
        this.y1=leftDownY;
        this.imageName=imgName;
        this.owidth=width;
        this.oheight=height;
        this.x2=x1+owidth;
        this.y2=y2+oheight;
        img=TextureManager.getImage(imgName);
        if(img == null || img.tmpi ==null){
            LogUtil.println("err");
        }
        imgWidth=img.tmpi.getWidth();//.w;
        imgHeight=img.tmpi.getHeight();//img.h;
       // texture = TextureManager.getTexture(imgName);
        try {
           // if(imgName.equals("terrain") || imgName.equals("iron_layer_1")|| imgName.equals("items")){
                withWH(leftDownX /imgWidth,/*(img.h - minY -height)*/leftDownY / imgHeight, width / imgWidth, height / imgHeight);//(img.h - minY -
          //  }else {
             //   withWHReverse(minX / imgWidth,/*(img.h - minY -height)*/minY / imgHeight, width / imgWidth, height / imgHeight);//(img.h - minY -
           // }

        }catch(Exception e){
            LogUtil.println("error");
            System.exit(1);
        }
        // withWH(minX / img.w, minY / img.h, width / img.w, height / img.h);//(img.h - minY -
        textureHandle= img.textureHandle;

    }
    public TextureInfo(){

    }
    public TextureInfo(String imgName){
        GLImage img=TextureManager.getImage(imgName);
        withWH(0, 0, 1, 1);
        textureHandle= img.textureHandle;
        //texture = TextureManager.getTexture(imgName);
    }

    public TextureInfo(String imgName,float minX,float minY,float width,float height,boolean flag){
        GLImage img=TextureManager.getImage(imgName);
        withWH(minX, minY, width, height);
        textureHandle= img.textureHandle;
        //texture = TextureManager.getTexture(imgName);

    }

    /*public TextureInfo(String imgName, int x, int y, int width, int height, int w, int h) {
    	 GLImage img=TextureManager.getImage(imgName);
         withWH((float)x/w, (float)y/h, (float)width/w, (float)height/h);
         textureHandle= img.textureHandle;
	}*/
    public TextureInfo(String imgName, float x, float y, float width, float height, float totalWidth, float totalHeight) {
        GLImage img=TextureManager.getImage(imgName);
        withWH((float)x/totalWidth, (float)y/totalHeight, (float)width/totalWidth, (float)height/totalHeight);
        textureHandle= img.textureHandle;
        //texture = TextureManager.getTexture(imgName);
    }
    public TextureInfo(String imgName, float x, float y, float width, float height, float w, float h,float offset) {
        GLImage img=TextureManager.getImage(imgName);
        withWH((x+offset)/w, (y+offset)/h, (width-offset)/w, (height-offset)/h);
        textureHandle= img.textureHandle;
        //texture = TextureManager.getTexture(imgName);
    }
	public void render(){


    }

    public void draw(de.matthiasmann.twl.renderer.AnimationState animationState, int x, int y, int w, int h){

           // if(  ( (LWJGLTexture) texture ).bind()) {

        if(bind()){
               /* GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                        textureHandle);*/
                //System.out.println(0.003921568859368563f*-1f);
                /*GL11.glColor4f(
                        0.003921568859368563f*-1f,
                        0.003921568859368563f*-1f,
                        0.003921568859368563f*-1f,
                        0.003921568859368563f*-1f);*/
                GL11.glBegin(GL11.GL_QUADS);
                drawQuad(x, y, w, h);
                GL11.glEnd();
            }

    }
    public boolean bind(){
        try {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                    textureHandle);
            return true;
           // ((LWJGLTexture) texture).bind();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    /*void drawQuad(int x, int y, int w, int h) {

        GL11.glTexCoord2f(minX, minY); GL11.glVertex2i(x   , y    );
        GL11.glTexCoord2f(minX, maxY); GL11.glVertex2i(x    , y + h);
        GL11.glTexCoord2f(maxX, maxY); GL11.glVertex2i(x + w, y + h);
        GL11.glTexCoord2f(maxX, minY); GL11.glVertex2i(x + w, y    );
    }*/

    void drawQuad(int x, int y, int w, int h) {


        GL11.glTexCoord2f(minX, minY); GL11.glVertex2i(x    , y + h);
        GL11.glTexCoord2f(maxX, minY); GL11.glVertex2i(x + w, y + h);
        GL11.glTexCoord2f(maxX, maxY); GL11.glVertex2i(x + w, y    );
        GL11.glTexCoord2f(minX, maxY); GL11.glVertex2i(x   , y    );
    }
    private int width;
    private int height;


    public void setWidth(int width) {
        this.width = width;
    }


    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets a parameter of the texture.
     *
     * @param name  Name of the parameter
     * @param value Value to set
     */
    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }
    /**
     * Uploads image data with specified width and height.
     *
     * @param width  Width of the image
     * @param height Height of the image
     * @param data   Pixel data of the image
     */
    public void uploadData(int width, int height, ByteBuffer data) {
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }

    /**
     * Uploads image data with specified internal format, width, height and
     * image format.
     *
     * @param internalFormat Internal format of the image data
     * @param width          Width of the image
     * @param height         Height of the image
     * @param format         Format of the image data
     * @param data           Pixel data of the image
     */
    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

    /**
     * Creates a texture with specified width, height and data.
     *
     * @param width  Width of the texture
     * @param height Height of the texture
     * @param data   Picture Data in RGBA format
     *
     * @return Texture from the specified data
     */
    public static TextureInfo createTexture(int width, int height, ByteBuffer data) {
        TextureInfo texture = new TextureInfo();
        texture. textureHandle = glGenTextures();
        texture.setWidth(width);
        texture.setHeight(height);

        texture.bind();

        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return texture;
    }
}
