package cola.machine.game.myblocks.input;

import org.lwjgl.input.Keyboard;

import cola.machine.game.myblocks.model.ui.html.HtmlObject;

/**
 * Created by luying on 14-9-19.
 */
public class BagMouseEventReceiver implements MouseEventReceiver{
    public boolean mouseMoveListen=false;
    public boolean mouseRClickListen=false;
    public boolean mouseLClickListen=false;
    public void mouseMove(int x,int y ){
        //TODO
        if(mouseMoveListen){
            mouseMoveHandle(x,y);


        }

    }
    public void mouseClick(int x,int y,HtmlObject htmlObject){
        if(Keyboard.isKeyDown(0)){
        	//if this has item  and mouse hasn't then current choose this item
        	if(htmlObject.background_image!=null){
        		
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
