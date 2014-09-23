package cola.machine.game.myblocks.model.ui.html;

/**
 * Created by luying on 14-9-16.
 */
public class Div extends HtmlObject{

    public void refresh(){
    	//System.out.println("DIV id:"+id);
        if(this.width==0){
            this.minX=this.parentNode.minX;
            this.minY=this.parentNode.minY;
            this.maxX=this.parentNode.maxX;
            this.maxY=this.parentNode.maxY;
        }else{
            super.refresh();
        }

        for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).refresh();
        }
    }
}
