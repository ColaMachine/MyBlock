package cola.machine.game.myblocks.model.ui.html;

/**
 * Created by luying on 14-9-16.
 */
public class Td extends HtmlObject {
    public int columnIndex;
    /*public float getLeft(){
       Table table = (Table)this.parentNode.parentNode;
        return table.getLeft()+table.cellpadding+table.cellspacing*columnIndex+getWidth()*columnIndex;
    }*/
    public float getWidth(){
        return (parentNode.maxX-parentNode.minX-
                ((Table)parentNode.parentNode).cellspacing*
                        (parentNode.childNodes.size()-1))/parentNode.childNodes.size();
    }
    public float getHeight(){
        return this.parentNode.maxY-this.parentNode.minY;
    }
    public void refresh(){
	/*	float the_width = (parentNode.maxX - parentNode.minX - ((Table) parentNode.parentNode).cellspacing
				* (parentNode.childNodes.size() - 1))
				/ parentNode.childNodes.size();
		this.minX = this.parentNode.minX + columnIndex
				* (((Table) parentNode.parentNode).cellspacing + the_width);
		this.minY = this.parentNode.minY;
		// this.maxX=minX+getWidth();
		this.maxX = minX + the_width;
		this.maxY = this.parentNode.maxY;
		*/
        this.minX=this.parentNode.minX+columnIndex*(((Table)parentNode.parentNode).cellspacing+getWidth());
        this.minY=this.parentNode.minY;
        this.maxX=minX+getWidth();
        this.maxY=this.parentNode.maxY;
        for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).refresh();
        }
    }

    public float getLeft(){
        return this.minX;
    }

    public float getBottom(){
       return this.minY;
    }
}
