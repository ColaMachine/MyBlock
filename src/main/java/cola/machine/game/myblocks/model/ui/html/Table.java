package cola.machine.game.myblocks.model.ui.html;

import de.matthiasmann.twl.AnimationState;

/**
 * Created by luying on 14-9-16.
 */
public class Table extends HtmlObject{

    public Table(){

    }
    public Table(AnimationState animationState,boolean flag){

    }
    public float cellspacing=0;
    public float cellpadding=0;
    //public List<Tr> rows=new ArrayList<Tr>();
    public void addRow(Tr tr){
        tr.rowIndex=childNodes.size();
        this.appendChild(tr);
    }
    public void deleteRow(int i){
        if(childNodes.size()>i){
            this.removeChild(i);
           // childNodes.remove(i);
        }
        //TODO the left tr need to be re index
    }



}
