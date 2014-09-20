package cola.machine.game.myblocks.model.ui.html;

/**
 * Created by luying on 14-9-16.
 */
public class Tr extends HtmlObject{
    public int rowIndex;
    public Td cells;
    public void addCell(Td td){
        td.columnIndex=this.childNodes.size();
        this.childNodes.add(td);
        td.parentNode=this;
    }
    public float getHeight(){

        /*return (this.parentNode.maxY-this.parentNode.minY-
                ((Table)this.parentNode).cellpadding*2)
                /this.parentNode.childNodes.size();*/
        if(this.parentNode.childNodes.size()==1){
            return this.parentNode.maxY-this.parentNode.minY -
                    ((Table)this.parentNode).cellpadding*2;

        }else{
            return (parentNode.maxY-this.parentNode.minY -
                    ((Table)this.parentNode).cellpadding*2-

                    ((Table)this.parentNode).cellspacing*
                            (this.parentNode.childNodes.size()-1)
            )
                            /this.parentNode.childNodes.size();
        }
 /*      return  (this.parentNode.maxY-this.parentNode.minY -
               ((Table)this.parentNode).cellpadding*2-
               this.parentNode.childNodes.size()==1?0:
               (((Table)this.parentNode).cellspacing*
                       (this.parentNode.childNodes.size()-1))
               /this.parentNode.childNodes.size());*/
    }
    public float getWidth(){
       return  this.parentNode.getWidth()-2*((Table)this.parentNode).cellpadding;
    }
    public void refresh(){
        this.minX=//this.parentNode.minX+((Table)this.parentNode).cellpadding;
        this.parentNode.minX+
                ((Table)this.parentNode).cellpadding;
               // rowIndex*(((Table)this.parentNode.parentNode).cellspacing+  this.getWidth());
        this.minY=this.parentNode.minY+
                ((Table)this.parentNode).cellpadding+
                (getHeight()+((Table)parentNode).cellspacing)*rowIndex;
        //table的minY ＋cellpadding ＋rowindex*（高度＋cellspaccing）
        this.maxY=this.minY+this.getHeight();
        this.maxX=this.minX+this.getWidth();

        for(int i=0;i<this.childNodes.size();i++){
            this.childNodes.get(i).refresh();
        }
    }
}
