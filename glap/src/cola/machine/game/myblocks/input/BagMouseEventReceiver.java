package cola.machine.game.myblocks.input;

import org.lwjgl.input.Keyboard;

import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import  cola.machine.game.myblocks.model.ui.html.*;

/**
 * Created by luying on 14-9-19.
 */
public class BagMouseEventReceiver implements MouseEventReceiver{
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
        if(true){//Keyboard.isKeyDown(0)
        	//if this has item  and mouse hasn't then current choose this item
        	
        	if(Document.var("currentchoose")==null ){
        		//fetch item from slot
        		if(htmlObject.childNodes!=null && htmlObject.childNodes.size()>0){
                    HtmlObject temp = htmlObject.childNodes.get(0);
                    int columnId=((Td)htmlObject).columnIndex;
                    int rowIndex=((Tr)htmlObject.parentNode).rowIndex;
                    int soltIndex=rowIndex*9+columnId;
                    
                    bag.item=bag.slots[soltIndex].item;
                    bag.slots[soltIndex].item=null;
                    Document.getElementById("bag").appendChild(temp);
            		Document.var("currentchoose", htmlObject.childNodes.get(0));
                    htmlObject.childNodes.clear();
                    /*temp.setWidth().width=temp.maxX-temp.minX;
                    temp.height=temp.maxY-temp.minY;
                    temp.left=temp.minX;
                    temp.top=temp.minY;*/
                    temp.update();
            	}
        	}else 
        	{
        		if(htmlObject.childNodes!=null && htmlObject.childNodes.size()>0){
        			//swap item
        			HtmlObject temp= htmlObject.childNodes.get(0);
        			htmlObject.childNodes.remove(0);
        			htmlObject.childNodes.add((HtmlObject)Document.var("currentchoose"));
            		Document.var("currentchoose",temp);
            		
            		int columnId=((Td)htmlObject).columnIndex;
                    int rowIndex=((Tr)htmlObject.parentNode).rowIndex;
                    int soltIndex=rowIndex*9+columnId;
                    Item item =bag.item;
                    
                    bag.item=bag.slots[soltIndex].item;
                    bag.slots[soltIndex].item=item;
            	}else{
            		//put item in slot
            	    //parent remove this one this
                    HtmlObject temp = (HtmlObject)Document.var("currentchoose");
                    temp.parentNode.removeChild(temp);
        			htmlObject.appendChild(temp);
            		Document.var("currentchoose",null);
                    /*temp.width=-1;
                    temp.height=-1;
                    temp.left=-1;
                    temp.top=-1;*/
                    
                	int columnId=((Td)htmlObject).columnIndex;
                    int rowIndex=((Tr)htmlObject.parentNode).rowIndex;
                    int soltIndex=rowIndex*9+columnId;
                   
                    temp.update();
                   
                    bag.slots[soltIndex].item=bag.item;bag.item=null;
            	}
                Document.getElementById("bag").update();
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
