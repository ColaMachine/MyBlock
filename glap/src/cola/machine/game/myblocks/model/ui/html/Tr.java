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
    }
}
