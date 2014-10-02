package cola.machine.game.myblocks.container;

import glapp.GLApp;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;

/**
 * Created by luying on 14-8-29.
 */
public class Slot extends RegionArea{
    public Item item;
    public Slot(float minX,float minY,float width,float height){
        this.withWH(minX,minY,width,height);
    }
    public boolean chooseFlag=false;
    public Item choose(){
        this.chooseFlag=true;
        return item;
    }
    public void clear(){
        this.item=null;
    }
    public void render(){
        if(chooseFlag){
           GL11.glColor3f(1,0,0);
            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(minX, minY, 0);
            GL11.glVertex3f(maxX, minY, 0);
            GL11.glVertex3f(maxX, maxY, 0);
            GL11.glVertex3f(minX ,maxY   , 0);
            GL11.glEnd();
        }else{
          GL11.glColor3f(1,1,1);
            super.render();
        }

        if(item!=null){

        	GL11.glPushMatrix();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            TextureInfo ti = TextureManager.getTextureInfo(item.name);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,ti.textureHandle);

            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z

                GL11.glTexCoord2f(ti.minX, ti.minY);
                GL11.glVertex3f( minX, minY, 0);

                GL11.glTexCoord2f(ti.maxX,ti. minY);
                GL11.glVertex3f( maxX, minY, 0);

                GL11.glTexCoord2f(ti.maxX, ti.maxY);
                GL11.glVertex3f( maxX, maxY,0);

                GL11.glTexCoord2f(ti.minX, ti.maxY);
                GL11.glVertex3f(minX,maxY, 0);

            }
                GL11.glEnd();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, GLApp.fontTextureHandle);
            //GLApp.print((int)minX,(int) minY,"11");
            GL11.glDisable(GL11.GL_LIGHTING);
            // enable alpha blending, so character background is transparent
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glTranslatef(maxX-20,minY , 0);        // Position The Text (in pixel coords)
            for(int i=0; i<String.valueOf(this.item.count).length(); i++) {
                GL11.glCallList(GLApp.fontListBase - 32 + String.valueOf(this.item.count).charAt(i));
            }
           // GL11.glTranslatef(-maxX,-minY , 0);

            GL11.glDisable(GL11.GL_TEXTURE_2D);

            GL11.glPopMatrix();
        }
    }
    
    

    public void putItem(Item item){
        this.item=item;

    }

}
