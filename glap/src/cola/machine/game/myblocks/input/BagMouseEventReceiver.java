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
        if(true){//Keyboard.isKeyDown(0)
        	//if this has item  and mouse hasn't then current choose this item
        	
        	if(Document.var("currentchoose")==null ){
        		if(htmlObject.childNodes!=null && htmlObject.childNodes.size()>0){
                    HtmlObject temp = htmlObject.childNodes.get(0);
                    Document.getElementById("bag").appendChild(temp);
            		Document.var("currentchoose", htmlObject.childNodes.get(0));
                    htmlObject.childNodes.clear();
                    temp.width=temp.maxX-temp.minX;
                    temp.height=temp.maxY-temp.minY;
                    temp.left=temp.minX;
                    temp.bottom=temp.minY;
            	}
        	}else 
        	{
        		if(htmlObject.childNodes!=null && htmlObject.childNodes.size()>0){
        			HtmlObject temp= htmlObject.childNodes.get(0);
        			htmlObject.childNodes.remove(0);
        			htmlObject.childNodes.add((HtmlObject)Document.var("currentchoose"));
            		Document.var("currentchoose",temp);
            	}else{
            	    //parent remove this one this
                    HtmlObject temp = (HtmlObject)Document.var("currentchoose");
                    temp.parentNode.removeChild(temp);
        			htmlObject.appendChild(temp);
            		Document.var("currentchoose",null);
                    temp.width=-1;
                    temp.height=-1;
                    temp.left=-1;
                    temp.bottom=-1;
            	}
                Document.getElementById("bag").refresh();
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
