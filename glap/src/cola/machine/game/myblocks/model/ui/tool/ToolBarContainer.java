package cola.machine.game.myblocks.model.ui.tool;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

public class ToolBarContainer {
	public int textureHandle=0;
	public int minX = 100;
	public int minY = 0;
	public int maxX = 600;
	public int maxY = 50;
	public ToolBarSlop[] toolBarSlops = new ToolBarSlop[10];

	public void init() {
		float step = (maxX - minX) / 10f;
		for (int i = 0; i < 10; i++) {
			ToolBarSlop slop = new ToolBarSlop(i, minX + i * step, 0, minX
					+ (i + 1) * step, maxY);

			toolBarSlops[i] = slop;

		}

	}

	public void render() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureHandle);
		GL11.glBegin(GL11.GL_QUADS);
		{
			for (int i = 0; i < 10; i++) {
				toolBarSlops[i].render();
			}
		}
		GL11.glEnd();

	}
}
