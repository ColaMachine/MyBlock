package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.model.ui.html.Td;
import cola.machine.game.myblocks.model.ui.html.Tr;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import glapp.GLApp;

/**
 * Created by luying on 14-9-19.
 */
public class PauseMouseEventReceiver implements MouseEventReceiver{
	public Bag bag;
    public boolean mouseMoveListen=false;
    public boolean mouseRClickListen=false;
    public boolean mouseLClickListen=false;
    public void mouseMove(int x,int y ){
        //TODO
        if(mouseMoveListen){
            mouseMoveHandle(x,y);


        }

    }
    public void mouseClick(float x,float y,HtmlObject htmlObject){
        if(!"none".equals(htmlObject.display)){//Keyboard.isKeyDown(0)
        	//if this has item  and mouse hasn't then current choose this item
            Tr tr = (Tr)htmlObject.parentNode;
            if(tr.rowIndex==1){
                //means exit
                GLApp.finished=true;
            }else if(tr.rowIndex==0){
                //means save
                CoreRegistry.get(WorldRendererLwjgl.class).save();
            }

        }
    }
    public void mouseMoveHandle(int x,int y){

    }
    public void mouseRCLickHandle(int x,int y){//receive the mouse key up

    }

    public void mouseLCLickHandle(int x,int y){

    }
}
