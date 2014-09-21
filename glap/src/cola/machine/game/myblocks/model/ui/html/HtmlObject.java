package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.input.KeyEventReceiver;
import cola.machine.game.myblocks.input.MouseEventReceiver;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 14-9-17.
 */
public class HtmlObject extends RegionArea{
    private static final Logger logger = LoggerFactory.getLogger(HtmlObject.class);
    public MouseEventReceiver mouseEventReceiver;
    public KeyEventReceiver keyEventReceiver;
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
        childNodes.add(htmlObject);htmlObject.parentNode=this;
    }
    public void removeChild(){
        if(this.childNodes!=null && this.childNodes.size()>0)
        this.childNodes.remove(this.childNodes.size()-1);

    }

    public void removeChild(int i){
        if(this.childNodes!=null && this.childNodes.size()>i-1)
            this.childNodes.remove(i);

    }
    public float getWidth (){
        if(width>0){
            return width;
        }else{
            width=parentNode.getWidth();///this.parentNode.childNodes.size() because of tr's width  may be the whole with of table
            return width;
        }
    }
    public float getHeight(){
        if(this.height>0){
            return  height;
        }else{
            return this.parentNode. getHeight();
        }


    }
    public float getLeft(){
        /*if(this.left>0)
        return left;
        else
        {
            if(this.parentNode!=null)
                return this.parentNode.getLeft();
            else
                return 0;
        }*/
        if(this.parentNode!=null)
            return this.parentNode.getLeft()+this.left;
        else{
            return this.left;
        }
    }
    public float getBottom(){
        if(this.parentNode!=null)
        return this.parentNode.getBottom()+this.bottom;
        else{
            return this.bottom;
        }
    }

    public void reSize(){
        this.width=this.getWidth();

    }
    public void refresh(){
        this.minX=this.getLeft();
        this.minY=this.getBottom();
        this.maxX=this.minX+this.getWidth();
        this.maxY=this.minY+this.getHeight();
        if(this.maxX==0){
            logger.error("maxX can't not be 0");
            System.exit(0);
        }
        for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).refresh();
        }
    }
    public void render(){
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
        if(this.border_width>0){
            GL11.glLineWidth(this.border_width);
            GL11.glColor3f(this.border_color.x,this.border_color.y,this.border_color.z);
            GLApp.drawRect((int)this.minX,(int)this.minY,this.maxX-this.minX,this.maxY-this.minY);

        }
        for(HtmlObject htmlObject:this.childNodes){
            htmlObject.render();
        }

      /*  for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).render();
        }*/


    }
    public void onClick(float x,float y){
    	if(this.contain(x, y)&& this.mouseEventReceiver!=null){
    		this.mouseEventReceiver.mouseClick(x, y, this);
    		for(HtmlObject htmlObject:this.childNodes){
    			htmlObject.onClick(x, y);
    		}
    	}
    }
}
