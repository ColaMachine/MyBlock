package cola.machine.game.myblocks.model.ui.bag;

import cola.machine.game.myblocks.input.Event;
import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.model.ui.html.Table;
import cola.machine.game.myblocks.model.ui.html.Td;
import cola.machine.game.myblocks.model.ui.html.Tr;
import glapp.GLApp;

import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.container.Slot;
import cola.machine.game.myblocks.engine.MyBlockEngine;
import cola.machine.game.myblocks.input.BagMouseEventReceiver;
import cola.machine.game.myblocks.input.MouseEventReceiver;
import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.region.RegionArea;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.model.ui.tool.ToolBarSlop;
import cola.machine.game.myblocks.registry.CoreRegistry;

import javax.vecmath.Vector3f;

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
    public int earthHandle;
    public float slotsWidth=458;
    public float slotsHeight=130;
    public float slotWidth;
    public float slotHeight;
   // public Block block;
    public int rowNum=3;
    public int colNum=9;
    public float left=121;
    public float bottom=174;
    public float mouseX;
    public float mouseY;
    public boolean show=false;
//    public float height=36;
//    public float width=36;
Div div;
    public Bag() {
        MouseEventReceiver mouseEventReceiver=new BagMouseEventReceiver();
        div=new Div();
        div.id="bag";
        Document.appendChild(div);
        div.margin="0 auto";
        div.width=400;
        div.height=300;
        div.border_width=1;
        div.background_image="bag";
        TextureInfo batTexture= TextureManager.getIcon("bag");
        div.width=batTexture.owidth;
        div.height=batTexture.oheight;

        div.border_color=new Vector3f(0,0,0);
        //toolbar
        {
            Table table =new Table();
            table.border_color=new Vector3f(0,0,0);
            table.border_width=1;
            table.left=14;
            table.bottom=14;table.width=325;
            table.height=39;
            div.appendChild(table);
            table.cellspacing=2;
            table.cellpadding=1;
            for(int i=0;i<1;i++){
                Tr tr =new Tr();
                table.addRow(tr);
                for(int j=0;j<9;j++){
                    Td td=new Td();

                    td.border_width=1;
                    td.border_color=new Vector3f(0,0,0);
                    td.mouseEventReceiver=mouseEventReceiver;
                    tr.addCell(td);
                    //Div container=new Div();
                    // td.a
                }
            }
        }
        //bag

        {
            Table table =new Table();
            table.border_color=new Vector3f(0,0,0);
            table.border_width=1;
            table.left=14;
            table.bottom=60;table.width=325;
            table.height=111;
            div.appendChild(table);
            table.cellspacing=2;
            table.cellpadding=1;
            for(int i=0;i<3;i++){
                Tr tr =new Tr();
                table.addRow(tr);
                for(int j=0;j<9;j++){
                    Td td=new Td();
                    td.mouseEventReceiver=mouseEventReceiver;
                    td.border_width=1;
                    td.border_color=new Vector3f(0,0,0);
                    tr.addCell(td);
                    td.id="bag_"+tr.rowIndex+"_"+td.columnIndex;
                    System.out.println(td.id);
                    //Div container=new Div();
                    // td.a
                }
            }
        }
        //workspace
        {
            Table table =new Table();
            table.border_color=new Vector3f(0,0,0);
            table.border_width=1;
            table.left=175;
            table.bottom=213;table.width=72;
            table.height=72;
            div.appendChild(table);
            table.cellspacing=2;
            table.cellpadding=1;
            for(int i=0;i<2;i++){
                Tr tr =new Tr();
                table.addRow(tr);
                for(int j=0;j<2;j++){
                    Td td=new Td();

                    td.border_width=1;
                    td.border_color=new Vector3f(0,0,0);
                    tr.addCell(td);
                    td.mouseEventReceiver=mouseEventReceiver;
                    //Div container=new Div();
                    // td.a
                }
            }
        }
        ////workspace2
        {
            Table table =new Table();
            table.border_color=new Vector3f(0,0,0);
            table.border_width=1;
            table.left=287;
            table.bottom=227;table.width=38;
            table.height=38;
            div.appendChild(table);
            table.cellspacing=1;
            table.cellpadding=1;
            for(int i=0;i<1;i++){
                Tr tr =new Tr();
                table.addRow(tr);
                for(int j=0;j<1;j++){
                    Td td=new Td();

                    td.border_width=1;
                    td.border_color=new Vector3f(0,0,0);
                    td.mouseEventReceiver=mouseEventReceiver;
                    tr.addCell(td);
                    //Div container=new Div();
                    // td.a
                }
            }
        }
//person view
        {
            Div personDiv=new Div();
            personDiv.left=50;
            personDiv.bottom=176;
            personDiv.width=108;
            personDiv.height=144;
            personDiv.border_color=new Vector3f(0,0,0);
            personDiv.border_width=1;
            div.appendChild(personDiv);

        }
        /*Table table =new Table();
        div.appendChild(table);
        table.cellspacing=5;
        table.cellpadding=5;
        for(int i=0;i<5;i++){
            Tr tr =new Tr();
            table.addRow(tr);
            for(int j=0;j<5;j++){
                Td td=new Td();

                td.border_width=1;
                td.border_color=new Vector3f(0,0,0);
                tr.addCell(td);
                //Div container=new Div();
               // td.a
            }
        }*/

//weapon
        Div weaponDiv =new Div();
        weaponDiv.border_width=1;
        weaponDiv.border_color=new Vector3f(0,0,0);;
        div.appendChild(weaponDiv);
        weaponDiv.left=13;
        weaponDiv.bottom=173;
        weaponDiv.width=38;
        weaponDiv.height=148;
        Table weaponTable =new Table();
        weaponDiv.appendChild(weaponTable);
        weaponTable.cellpadding=2;
        weaponTable.cellspacing=2;
        for(int i=0;i<4;i++){

            Tr w_tr=new Tr();
            w_tr.rowIndex=i;
            Td w_td =new Td();
            w_td.columnIndex=0;
            w_td.border_color=new Vector3f(0,0,0);
            w_td.border_width=1;
            w_tr.addCell(w_td);
            w_td.mouseEventReceiver=mouseEventReceiver;
            weaponTable.addRow(w_tr);

        }

        this.withWH(100,100,500,400);
        this.textureInfo= TextureManager.getIcon("bag");
        this.humanTextureHandle= TextureManager.getTextureHandle("human").textureHandle;
        this.earthHandle= TextureManager.getTextureHandle("background").textureHandle;
        this.human= CoreRegistry.get(MyBlockEngine.class).human;
        //this.block= new BaseBlock(0,0,0);
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
        this.putItem(2,new Item("glass",10));
        this.putItem(3,new Item("wood",10));
        this.putItem(4,new Item("sand",10));
        this.putItem(5,new Item("water",10)); div.refresh();
    }

    public void putItem(int slotIndex , Item item){
    	int rowIndex=slotIndex/9;
    	int columnIndex=slotIndex%9;
    	
    	//find the html element and put the 
    	//create element div and set the 
    	Div div =new Div();
    	div.background_image=item.name;
        div.border_width=1;
        div.border_color=new Vector3f(0,0,0);
    	HtmlObject object=Document.getElementById("bag_"+rowIndex+"_"+columnIndex);
        object.appendChild(div);
        slots[slotIndex].putItem(item);
    }
    public void render() {
if(!show)return;


       // GLApp.pushAttribOrtho();
        // switch to 2D projection
       // GLApp. setOrthoOn();
        // tweak settings
        GL11.glEnable(GL11.GL_TEXTURE_2D);   // be sure textures are on
        GL11.glColor4f(1,1,1,1);             // no color
        GL11.glDisable(GL11.GL_LIGHTING);    // no lighting
        GL11.glDisable(GL11.GL_DEPTH_TEST);  // no depth test
        GL11.glEnable(GL11.GL_BLEND);        // enable transparency
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
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
            
           // GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.earthHandle);
           // block.renderCube();
        }
        GL11.glPopMatrix();
        //renderBlockTest();

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



      //  GLApp.setOrthoOff();
        // return to previous settings
       // GLApp. popAttrib();

        div.render();
       
    }
    public void renderBlockTest(){
    	GL11.glPushMatrix();
		{
			 GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureManager.getTextureHandle("background").textureHandle);
		
    	GL11.glRotated(45, 0, 1, 0);
    	GL11.glRotated(30, 1, 0, 0);
    	GL11.glScalef(30, 30, 30);
    	GL11.glTranslated(200, 200, -40);
    	
    	
           GL11.glEnd();
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
		}GL11.glPopMatrix();
    	
    	
    }
    public void renderSlot(){
        for(int i=0,length=slots.length;i<length;i++){
            slots[i].render();
        }
    }

    public void click(int x,int y){
        Event event =new Event();
        event.x=x;
        event.y=y;

        anySlotClicked(event);
        mouseX=x;mouseY=y;
        anyHtmlClicked();

    }
    public void move(int x,int y){
        mouseX=x;mouseY=y;
        HtmlObject currentChoose=(HtmlObject)Document.var("currentchoose");
        if(currentChoose!=null){
          // float width= currentChoose.maxX-currentChoose.minX;
            //float height= currentChoose.maxY-currentChoose.minY;
            currentChoose.minX=x-currentChoose.width/2;
            currentChoose.maxX=x+currentChoose.width/2;

            currentChoose.minY=y-currentChoose.height/2;
            currentChoose.maxY=y+currentChoose.height/2;
//            currentChoose.minX=x-40/2;
//            currentChoose.maxX=x+40/2;
            currentChoose.left=x-40/2;
//            currentChoose.minY=y-40/2;
//            currentChoose.maxY=y+40/2;
           currentChoose.bottom=y-40/2;
        }
    }
    public static Item item;
    public void anyHtmlClicked(){
    	
    }
    public void anySlotClicked(Event event){
    	div.onClick(event);
       /* if(slotsRegion.contain(x,y)){
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


        }*/
    }
    public void changeShow(){
    	this.show=!this.show;
    }
   
}
