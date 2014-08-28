package cola.machine.game.myblocks.model.ui.tool;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.model.textture.TextTureInfo;

public class ToolBarSlop {
	int key;
	public TextTureInfo textTureInfo;
	float minX = 100;
	float minY = 0;
	float maxX = 600;
	float maxY = 50;

	public ToolBarSlop(int key, float minX, float minY, float maxX, float maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.key = key;
	}

	public void render() {
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
			GL11.glTexCoord2f(textTureInfo.minX, textTureInfo.minY);
			GL11.glVertex3f(minX, minY, (float) 0);
			GL11.glTexCoord2f(textTureInfo.maxX, textTureInfo.minY);
			GL11.glVertex3f(maxX, minY, (float) 0);
			GL11.glTexCoord2f(textTureInfo.maxX, textTureInfo.maxY);
			GL11.glVertex3f(maxX, maxY, (float) 0);
			GL11.glTexCoord2f(textTureInfo.minX, textTureInfo.maxY);
			GL11.glVertex3f(minX, maxY, (float) 0);
	
	}
}
