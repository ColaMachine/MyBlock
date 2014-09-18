package cola.machine.game.myblocks.model.ui.html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luying on 14-9-16.
 */
public class Table extends HtmlObject{
    public float cellspacing;
    public float cellpadding;
    //public List<Tr> rows=new ArrayList<Tr>();
    public void addRow(Tr tr){
        tr.rowIndex=childNodes.size();
        childNodes.add(tr);
    }
    public void deleteRow(int i){
        if(childNodes.size()>i){
            childNodes.remove(i);
        }
    }

    public void mouseDown(int x,int y){

    }
}
