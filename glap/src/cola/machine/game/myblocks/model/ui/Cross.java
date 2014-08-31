package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.tool.ToolBarSlop;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 14-8-29.
 */
public class Cross extends RegionArea {
    public TextureInfo textureInfo;

    public Cross() {
        this.withWH(GLApp.getWidth()/2-25, GLApp.getHeight()/2-25,50f,50f);
        this.textureInfo= TextureManager.getIcon("cross");


    }

    public void render() {


        GLApp.pushAttribOrtho();
        // switch to 2D projection
        GLApp. setOrthoOn();
        // tweak settings
        GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
        GL11.glColor4f(1,1,1,1);             // no color
        GL11.glDisable(GL11.GL_LIGHTING);    // no lighting
        GL11.glDisable(GL11.GL_DEPTH_TEST);  // no depth test
        GL11.glEnable(GL11.GL_BLEND);        // enable transparency
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // activate the image texture

        // draw a textured quad

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureInfo.textureHandle);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
        GL11.glTexCoord2f(textureInfo.minX, textureInfo.minY);
        GL11.glVertex3f(minX, minY, (float) 0);
        GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
        GL11.glVertex3f(maxX, minY, (float) 0);
        GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
        GL11.glVertex3f(maxX, maxY, (float) 0);
        GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
        GL11.glVertex3f(minX, maxY, (float) 0);
        GL11.glEnd();
        GLApp.setOrthoOff();
        // return to previous settings
        GLApp. popAttrib();
    }
}
