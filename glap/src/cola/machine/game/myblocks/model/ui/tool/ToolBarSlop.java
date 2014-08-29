package cola.machine.game.myblocks.model.ui.tool;

import cola.machine.game.myblocks.model.region.RegionArea;
import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.model.textture.TextureInfo;

public class ToolBarSlop extends RegionArea{




	int keyCode;
	public TextureInfo textureInfo;
	/*float minX = 100;
	float minY = 0;
	float maxX = 600;
	float maxY = 50;*/

	public ToolBarSlop( float minX, float minY, float maxX, float maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
    public void bind(int keyCode){
        this.keyCode= keyCode;
    }
	public void render() {
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
			GL11.glTexCoord2f(textureInfo.minX, textureInfo.minY);
			GL11.glVertex3f(minX, minY, (float) 0);
			GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
			GL11.glVertex3f(maxX, minY, (float) 0);
			GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
			GL11.glVertex3f(maxX, maxY, (float) 0);
			GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
			GL11.glVertex3f(minX, maxY, (float) 0);
	
	}
}
