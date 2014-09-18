package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 14-9-17.
 */
public class HtmlObject extends RegionArea{
   public HtmlObject parentNode;
    public List<HtmlObject> childNodes =new ArrayList<HtmlObject>();
    public String id;
    public String name;
    public float left;
    public float bottom;
    public float width;
    public float height;
    public String position;
    public String display;
    public  float offsetleft;
    public  float OffsetRight;
    public String margin;
    public int  border_width;
    public String background_image;

    public Vector3f border_color;
    public void onClick(){

    }
    public void appendChild(HtmlObject htmlObject){
        childNodes.add(htmlObject);
    }
    public float getWidth (){
        if(width!=-1){
            return width;
        }else{
            width=parentNode.getWidth();
            return width;
        }
    }
    public float getHeight(){
        return  height;

    }
    public float getLeft(){
        return left;
    }
    public float getBottom(){
        return this.bottom;
    }

    public void reSize(){
        this.width=this.getWidth();

    }
    public void render(){
        if(this.border_width>0){
            GL11.glColor3f(this.border_color.x,this.border_color.y,this.border_color.z);
            GLApp.drawRect((int)this.getBottom(),(int)this.getLeft(),this.getWidth(),this.getHeight());
        }
        if(this.background_image!=null){
            TextureInfo textureInfo = TextureManager.getIcon(this.background_image);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureInfo.textureHandle);

            GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
            GL11.glBegin(GL11.GL_QUADS);

            GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z
            GL11.glTexCoord2f(textureInfo.minX, textureInfo.minY);
            GL11.glVertex3f(this.getLeft(), this.getBottom(), (float)-10);
            GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
            GL11.glVertex3f(this.getLeft()+this.getWidth(), this.getBottom(), (float)-10);
            GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
            GL11.glVertex3f(maxX, maxY, (float)-10);
            GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
            GL11.glVertex3f(minX, maxY, (float)-10);
            GL11.glEnd();
        }
        for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).render();
        }
    }
}
