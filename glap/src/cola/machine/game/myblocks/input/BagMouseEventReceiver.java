package cola.machine.game.myblocks.input;

import org.lwjgl.input.Keyboard;

import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.html.Document;
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
    public void mouseClick(float x,float y,HtmlObject htmlObject){
        if(Keyboard.isKeyDown(0)){
        	//if this has item  and mouse hasn't then current choose this item
        	
        	if(Document.var("currentchoose")==null ){
        		if(htmlObject.childNodes!=null ){
            		Document.var("currentchoose", htmlObject.childNodes.get(0));
            	}
        	}else 
        	{
        		if(htmlObject.childNodes!=null ){
        			HtmlObject temp= htmlObject.childNodes.get(0);
        			htmlObject.childNodes.remove(0);
        			htmlObject.childNodes.add((HtmlObject)Document.var("currentchoose"));
            		Document.var("currentchoose",temp);
            	}else{
            	
        			htmlObject.childNodes.add((HtmlObject)Document.var("currentchoose"));
            		Document.var("currentchoose",null);
            	}
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
