package cola.machine.game.myblocks.container;

import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.item.ItemStack;
import cola.machine.game.myblocks.model.region.RegionArea;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 14-8-29.
 */
public class Slot extends RegionArea{
    public Item item;
    public Slot(float minX,float minY,float width,float height){
        this.withWH(minX,minY,width,height);
    }
    public boolean chooseFlag=false;
    public void choose(){
        this.chooseFlag=true;
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

        }
    }

    public void put(Item item){
        this.item=item;
    }

}
