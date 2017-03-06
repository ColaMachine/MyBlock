package cola.machine.game.myblocks.model.textture;

import core.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import glapp.GLImage;

import org.lwjgl.opengl.GL11;

public class TextureInfo extends RegionArea{
	
	/*public float minX=0;
	public float minY=0;
	public float maxX=0;
	public float maxY=0;*/
    public  GLImage img;
    public int textureHandle= 0;
	public TextureInfo(float minX,float minY,float maxX,float maxY){
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
	}
public float x1;
    public float y1;
    public float owidth;
    public float oheight;
    public float x2;
    public float y2;

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
    public String imageName;
    public String name;
    int imgWidth ;
    int imgHeight;
    public TextureInfo(String imgName,float minX,float minY,float width,float height,String name){
        this.x1=minX;
        this.name=name;
        this.y1=minY;
        this.imageName=imgName;
        this.owidth=width;
        this.oheight=height;
        this.x2=x1+owidth;
        this.y2=y2+oheight;
        img=TextureManager.getImage(imgName);
        imgWidth=img.tmpi.getWidth();//.w;
        imgHeight=img.tmpi.getHeight();//img.h;
       // texture = TextureManager.getTexture(imgName);
        try {
            if(imgName.equals("terrain") || imgName.equals("iron_layer_1")|| imgName.equals("items")){
                withWH(minX /imgWidth,/*(img.h - minY -height)*/minY / imgHeight, width / imgWidth, height / imgHeight);//(img.h - minY -
            }else {
                withWHReverse(minX / imgWidth,/*(img.h - minY -height)*/minY / imgHeight, width / imgWidth, height / imgHeight);//(img.h - minY -
            }

        }catch(Exception e){
            LogUtil.println("error");
            System.exit(1);
        }
        // withWH(minX / img.w, minY / img.h, width / img.w, height / img.h);//(img.h - minY -
        textureHandle= img.textureHandle;

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
}
