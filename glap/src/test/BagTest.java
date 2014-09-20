package test;

import cola.machine.game.myblocks.model.ui.html.*;

import javax.vecmath.Vector3f;

/**
 * Created by luying on 14-9-19.
 */
public class BagTest {
    public static void main(String args[]){

      //  HtmlObject dd =new Td();
      //  dd.getWidth();
        Div
        div=new Div();
        div.margin="0 auto";
        div.width=400;
        div.height=300;
        div.border_width=1;
        div.border_color=new Vector3f(0,0,0);
        Table table =new Table();
        div.appendChild(table);
        table.cellspacing=1;
        for(int i=0;i<5;i++){
            Tr tr =new Tr();
            table.addRow(tr);
            for(int j=0;j<5;j++){
                Td td=new Td();

                td.border_width=1;
                tr.border_color=new Vector3f(0,0,0);
                tr.addCell(td);
                //Div container=new Div();
                // td.a
            }
        }
        div.refresh();

    }
}
