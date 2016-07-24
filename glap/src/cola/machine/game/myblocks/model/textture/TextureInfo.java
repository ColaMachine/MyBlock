package cola.machine.game.myblocks.model.textture;

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
    public TextureInfo(String imgName,float minX,float minY,float width,float height){
        this.x1=minX;
        this.y1=minY;
        this.owidth=width;
        this.oheight=height;
        this.x2=x1+owidth;
        this.y2=y2+oheight;
         img=TextureManager.getImage(imgName);
        withWH(minX/img.w, minY/img.h, width/img.w, height/img.h);
        textureHandle= img.textureHandle;

    }

    public TextureInfo(String imgName){
        GLImage img=TextureManager.getImage(imgName);
        withWH(0, 0, 1, 1);
        textureHandle= img.textureHandle;

    }

    public TextureInfo(String imgName,float minX,float minY,float width,float height,boolean flag){
        GLImage img=TextureManager.getImage(imgName);
        withWH(minX, minY, width, height);
        textureHandle= img.textureHandle;

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
    }
    public TextureInfo(String imgName, float x, float y, float width, float height, float w, float h,float offset) {
        GLImage img=TextureManager.getImage(imgName);
        withWH((x+offset)/w, (y+offset)/h, (width-offset)/w, (height-offset)/h);
        textureHandle= img.textureHandle;
    }
	public void render(){


    }
}
