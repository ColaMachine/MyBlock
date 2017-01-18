package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.log.LogUtil;
import de.matthiasmann.twl.Event;

import javax.vecmath.Vector4f;

/**
 * Created by luying on 14-9-16.
 */
public class Div extends HtmlObject implements Cloneable{

    public void handle(Event event ){
        System.out.println("进来了");
    }
    protected boolean handleEvent(Event evt) {
       //ss System.out.println("进来了");
        if(evt.getType()==Event.Type.MOUSE_ENTERED){
            this.borderColor=new Vector4f(1,1,1,1);
            LogUtil.println(this.id+"进入了");
            Document.needUpdate=true;
            return true;
        }else if(evt.getType()==Event.Type.MOUSE_EXITED){
            this.borderColor=new Vector4f(0,0,0,1);
            LogUtil.println(this.id+"离开了了");
            document.needUpdate=true;return true;
        }
         //this.borderColor=new Vector4f(1,1,1,1);
        return false;
    }
    public void refresh(){
    	//System.out.println("DIV id:"+id);
        if(this.getWidth()==0){
           /* this.minX=this.parentNode.minX;
            this.minY=this.parentNode.minY;
            this.maxX=this.parentNode.maxX;
            this.maxY=this.parentNode.maxY;*/
            //this.reSize();

        }else{
            super.update();
        }

        for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).update();
        }
    }
}
