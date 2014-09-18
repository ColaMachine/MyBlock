package cola.machine.game.myblocks.model.ui.html;

/**
 * Created by luying on 14-9-16.
 */
public class Td extends HtmlObject {
    public int columnIndex;
    public float getLeft(){
       Table table = (Table)this.parentNode.parentNode;
        return table.getLeft()+table.cellpadding+table.cellspacing*columnIndex+getWidth()*columnIndex;
    }
    public float getWidth(){
        return (this.parentNode.parentNode.getWidth()- ((Table)this.parentNode.parentNode).cellpadding*2)/(this.parentNode.childNodes.size()-1);
    }
}
