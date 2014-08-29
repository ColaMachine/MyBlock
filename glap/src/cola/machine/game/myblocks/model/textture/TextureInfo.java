package cola.machine.game.myblocks.model.textture;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import glapp.GLImage;

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
}
