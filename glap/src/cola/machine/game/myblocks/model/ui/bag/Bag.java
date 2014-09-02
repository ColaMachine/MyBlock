package cola.machine.game.myblocks.model.ui.bag;

import cola.machine.game.myblocks.container.Slot;
import cola.machine.game.myblocks.engine.MyBlockEngine;
import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.tool.ToolBarSlop;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;
import org.terasology.registry.CoreRegistry;

/**
 * Created by luying on 14-8-29.
 */
public class Bag extends RegionArea  {
    public Human human;
    public Slot[] slots;//bag slot
    public TextureInfo textureInfo;
    public ToolBarSlop[] toolBarSlots = new ToolBarSlop[10];//toobar slot
    RegionArea slotsRegion=new RegionArea();
    public int humanTextureHandle;
    public float slotsWidth=458;
    public float slotsHeight=130;
    public float slotWidth;
    public float slotHeight;

    public int rowNum=3;
    public int colNum=9;
    public float left=121;
    public float bottom=174;
    public float mouseX;
    public float mouseY;
    public boolean show=false;
//    public float height=36;
//    public float width=36;

    public Bag() {

        this.withWH(100,100,500,400);
        this.textureInfo= TextureManager.getIcon("bag");
        this.humanTextureHandle= TextureManager.getTextureHandle("human").textureHandle;
        this.human= CoreRegistry.get(MyBlockEngine.class).human;
        //slotsRegion=new RegionArea(left,bottom,slotsWidth,slotsHeight);
        slotsRegion.withWH(left,bottom,slotsWidth,slotsHeight);
        slotWidth= slotsRegion.getWidth()/9;
        slotHeight=slotsRegion.getHeight()/3;
         /* 鍒濆鍖�*/
        slots=new Slot[3*9+9];
        for(int rowIndex=0;rowIndex<3;rowIndex++) {
            for (int colIndex = 0; colIndex < 9; colIndex++) {
                Slot slot = new Slot(left+slotWidth * colIndex+2, bottom+slotHeight * rowIndex+2, slotWidth -5, slotHeight-5);
                slots[rowIndex * 9 + colIndex] = slot;
            }
        }
        
        for(int i=0;i<9;i++) {
         
                Slot slot = new Slot(left+slotWidth * i+2, bottom-55+2, slotWidth -5, slotHeight-5);
                slots[3*9+  i] = slot;
            }
        slotsRegion.withWH(left,bottom-55,slotsWidth,slotsHeight+55);
        CoreRegistry.put(Bag.class,this);

        this.putItem(0,new Item("apple_golden",10));
        this.putItem(1,new Item("soil",10));
    }

    public void putItem(int slotIndex , Item item){
        slots[slotIndex].putItem(item);
    }
    public void render() {
if(!show)return;
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
        GL11.glVertex3f(minX, minY, (float)-10);
        GL11.glTexCoord2f(textureInfo.maxX, textureInfo.minY);
        GL11.glVertex3f(maxX, minY, (float)-10);
        GL11.glTexCoord2f(textureInfo.maxX, textureInfo.maxY);
        GL11.glVertex3f(maxX, maxY, (float)-10);
        GL11.glTexCoord2f(textureInfo.minX, textureInfo.maxY);
        GL11.glVertex3f(minX, maxY, (float)-10);
        GL11.glEnd();







        //鐢讳竴涓皬浜哄湪妗嗕綋閲�
        GL11.glPushMatrix();
        {

            GL11.glTranslated( 240, 315, -21); // rotate around Y axis
            //GL11.glTranslated( -human.Position.x,  -human.Position.y, -human.Position.z -21); // rotate around Y axis
            //GL11.glRotatef(rotation, 0, 1, 0); // rotate around Y axis
             GL11.glScalef(40f, 40f, 40f); // scale up
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, humanTextureHandle);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
                    GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            human.renderInMirror();

        }
        GL11.glPopMatrix();


        //renderSlot();
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK,GL11.GL_LINE);



        GL11.glDisable(GL11.GL_TEXTURE_2D);

        renderSlot();


       /* GL11.glLineWidth(2f);
        GL11.glColor3f(1f, 1f, 1f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(mouseX-10,mouseY-10);
        GL11.glVertex2f(mouseX+10,mouseY-10);
        GL11.glVertex2f(mouseX+10,mouseY+10);
        GL11.glVertex2f(mouseX-10,mouseY+10);
        GL11.glEnd();*/




        if(this.item!=null) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.item.textureInfo.textureHandle);
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glNormal3f(0.0f, 0.0f, 1.0f); // normal faces positive Z

                GL11.glTexCoord2f( this.item.textureInfo.minX, this.item.textureInfo.minY);
                GL11.glVertex2f(mouseX-slotWidth/2,mouseY-slotHeight/2);


                GL11.glTexCoord2f( this.item.textureInfo.maxX,this.item.textureInfo.minY);
                GL11.glVertex2f(mouseX + slotWidth / 2, mouseY - slotHeight / 2);

                GL11.glTexCoord2f( this.item.textureInfo.maxX,this.item.textureInfo.maxY);
                GL11.glVertex2f(mouseX + slotWidth / 2, mouseY + slotHeight / 2);

                GL11.glTexCoord2f( this.item.textureInfo.minX,this.item.textureInfo.maxY);
                GL11.glVertex2f(mouseX - slotWidth / 2, mouseY + slotHeight / 2);

            }
            GL11.glEnd();

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, GLApp.fontTextureHandle);
            //GLApp.print((int)minX,(int) minY,"11");
            GL11.glDisable(GL11.GL_LIGHTING);
            // enable alpha blending, so character background is transparent
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glTranslatef(mouseX + slotWidth / 2-20, mouseY - slotHeight / 2 , 0);        // Position The Text (in pixel coords)
            for(int i=0; i<String.valueOf(this.item.count).length(); i++) {
                GL11.glCallList(GLApp.fontListBase - 32 + String.valueOf(this.item.count).charAt(i));
            }
            // GL11.glTranslatef(-maxX,-minY , 0);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
       /* GL11.glBegin(GL11.GL_QUADS);
      //  GL11.glRectf(200,200,300,300);
        GL11.glVertex3f(200, 200, (float)-10);
        GL11.glVertex3f(300, 200, (float)-10);
        GL11.glVertex3f(300, 300, (float)-10);
        GL11.glVertex3f(200, 300, (float)-10);
        // GL11.glRectf(200,200,400,400);
        GL11.glEnd();*/



        GLApp.setOrthoOff();
        // return to previous settings
        GLApp. popAttrib();
    }

    public void renderSlot(){
        for(int i=0,length=slots.length;i<length;i++){
            slots[i].render();
        }
    }

    public void click(int x,int y){
        anySlotClicked(x, y);
        mouseX=x;mouseY=y;


    }
    public void move(int x,int y){
        mouseX=x;mouseY=y;
    }
    public Item item;
    public void anySlotClicked(float x,float y){
        if(slotsRegion.contain(x,y)){
            //count the num of slot
           // int rownum = (x-slotsRegion.minX)%sl
            for(int i=0;i<slots.length;i++){
                if(slots[i].contain(x,y)){
                    if(slots[i].item ==null &&  this.item!=null){
                        slots[i].item=this.item;
                        this.item=null;

                    }else {
                        this.item = slots[i].choose();
                        slots[i].clear();

                    } break;
                }
            }


        }
    }
    public void changeShow(){
    	this.show=!this.show;
    }
   
}
