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

    public int textureHandle= 0;
	public TextureInfo(float minX,float minY,float maxX,float maxY){
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
	}

    public TextureInfo(String imgName,float minX,float minY,float width,float height){
        GLImage img=TextureManager.getTextureHandle(imgName);
        withWH(minX/img.w, minY/img.h, width/img.w, height/img.h);
        textureHandle= img.textureHandle;

    }

    public TextureInfo(String imgName){
        GLImage img=TextureManager.getTextureHandle(imgName);
        withWH(0, 0, 1, 1);
        textureHandle= img.textureHandle;

    }

    public TextureInfo(String imgName,float minX,float minY,float width,float height,boolean flag){
        GLImage img=TextureManager.getTextureHandle(imgName);
        withWH(minX, minY, width, height);
        textureHandle= img.textureHandle;

    }

    public TextureInfo(String imgName, int x, int y, int width, int height, int w, int h) {
    	 GLImage img=TextureManager.getTextureHandle(imgName);
         withWH((float)x/w, (float)y/h, (float)width/w, (float)height/h);
         textureHandle= img.textureHandle;
	}

	public void render(){


    }
}
