package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.manager.TextureManager;
import org.lwjgl.input.Keyboard;

import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import  cola.machine.game.myblocks.model.ui.html.*;

/**
 * Created by luying on 14-9-19.
 */
public class ToolbarMouseEventReceiver implements MouseEventReceiver{
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
                    Document.getInstance().getElementById("bag").appendChild(temp);
            		Document.var("currentchoose", htmlObject.childNodes.get(0));
                    htmlObject.childNodes.clear();
                    temp.setWidth(temp.getWidth());
                    temp.setHeight(temp.getHeight());
                    /*temp.setPosX();
                    temp.top=temp.minY;*/
                    Document.getInstance().getElementById("toolbar_"+rowIndex+"_"+columnId).setBackgroundImage(null);
                    
            	}
        	}else 
        	{
        		if(htmlObject.childNodes!=null && htmlObject.childNodes.size()>0){
        			//swap item
        			HtmlObject temp= htmlObject.childNodes.get(0);
        			htmlObject.childNodes.remove(0);
        			htmlObject.childNodes.add((HtmlObject)Document.var("currentchoose"));
            		Document.getInstance().var("currentchoose",temp);
            		
            		int columnId=((Td)htmlObject).columnIndex;
                    int rowIndex=((Tr)htmlObject.parentNode).rowIndex;
                    int soltIndex=rowIndex*9+columnId;
                    Item item =bag.item;
                    
                    bag.item=bag.slots[soltIndex].item;
                    bag.slots[soltIndex].item=item;
                    Document.getInstance().getElementById("toolbar_"+rowIndex+"_"+columnId).setBackgroundImage(new Image(TextureManager.getTextureInfo(item.name)));
                    
            	}else{
            		//put item in slot
            	    //parent remove this one this
                    HtmlObject temp = (HtmlObject)Document.var("currentchoose");
                    temp.parentNode.removeChild(temp);
        			htmlObject.appendChild(temp);
            		Document.var("currentchoose",null);
                    temp.setWidth(0);
                    temp.setHeight(0);
                    temp.setLeft(0);
                    temp.setTop(0);
                    
                	int columnId=((Td)htmlObject).columnIndex;
                    int rowIndex=((Tr)htmlObject.parentNode).rowIndex;
                    int soltIndex=rowIndex*9+columnId;
                   
                    
                   
                    bag.slots[soltIndex].item=bag.item;
                    Document.getInstance().getElementById("toolbar_"+rowIndex+"_"+columnId).setBackgroundImage(new Image(TextureManager.getTextureInfo(bag.item.name)));
                    
                    bag.item=null;
                    
            	}
                Document.getInstance().getElementById("bag").update();
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
