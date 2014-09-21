package cola.machine.game.myblocks.input;

import org.lwjgl.input.Keyboard;

import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;

/**
 * Created by luying on 14-9-19.
 */
public interface MouseEventReceiver {
    public boolean mouseMoveListen=false;
    public boolean mouseRClickListen=false;
    public boolean mouseLClickListen=false;
    
    /*public void handle(Event event,HtmlObject htmlObject){
    	
    }*/
    public void mouseMove(int x,int y );
    public void mouseClick(float x,float y,HtmlObject htmlObject);
    public void mouseMoveHandle(int x,int y);
    public void mouseRCLickHandle(int x,int y);
    public void mouseLCLickHandle(int x,int y);
}
