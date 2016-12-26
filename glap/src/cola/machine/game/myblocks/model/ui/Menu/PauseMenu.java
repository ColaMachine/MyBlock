package cola.machine.game.myblocks.model.ui.Menu;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.input.MouseEventReceiver;
import cola.machine.game.myblocks.input.PauseMouseEventReceiver;
import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.Table;
import cola.machine.game.myblocks.model.ui.html.Td;
import cola.machine.game.myblocks.model.ui.html.Tr;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import glapp.GLApp;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 * Created by luying on 14-10-3.
 */
public class PauseMenu {
    public boolean show=false;
    int buttonWidth=200;
    int buttonHeight=50;
    int cellSpacing=20;
    Div mask;
    public PauseMenu(){
        MouseEventReceiver mouseEventReceiver = new PauseMouseEventReceiver();
         mask =new Div();
        CoreRegistry.put(PauseMenu.class, this);
        mask.setLeft(0);mask.setVisible(false);
        mask.setTop(0);
        mask.setHeight(Constants.WINDOW_HEIGHT);
        mask.setWidth(Constants.WINDOW_WIDTH);
        mask.setBackgroundColor(new Vector4f(0.1f,0.1f,0.1f,0.8f));

        Table table =new Table();
        table.setLeft(mask.getWidth()/2-buttonWidth/2);
        table.setTop(mask.getHeight()/2-cellSpacing-buttonHeight*2);
        table.setHeight(buttonHeight*2+cellSpacing);
        table.setWidth(buttonWidth);
        table.cellpadding=5;
        table.cellspacing=cellSpacing;
        table.setBorderWidth(1);
        table.setBorderColor(new Vector4f(0,0,0,0));
        mask.appendChild(table);
        Tr tr1=new Tr();
        table.addRow(tr1);
        Td td1 =new Td();td1.setBorderColor(new Vector4f(0,0,0,0));td1.setBorderWidth(2);
        td1.innerText="SAVE GAME";td1.mouseEventReceiver=mouseEventReceiver;
        tr1.addCell(td1);
        td1.setBorderColor(new Vector4f(0,0,0,1));
        //td1.mouseEventReceiver=
        Tr tr2=new Tr();
        table.addRow(tr2);
        Td td2= new Td();td2.setBorderColor(new Vector4f(0,0,0,0));td2.setBorderWidth(2);  td2.innerText="EXIT GAME";
        tr2.addCell(td2);td2.mouseEventReceiver=mouseEventReceiver;
        td2.setBorderColor(new Vector4f(0,0,0,1));
        mask.refresh();

    }
    public void render(){
        if(mask.isVisible()){
            mask.render();
        }

    }

    public void click(int x,int y){


     /*   InputEvent event =new InputEvent();
        event.x=x;
        event.y=y;


        mask.onClick(event);
*/


    }
    public void move(int x,int y){

    }
    public void show(){
        this.show=!show;
        if(show) {
            mask.setVisible(true);
            Switcher.MOUSE_AUTO_CENTER=false;
        }else {
            mask.setVisible(false);
            Switcher.MOUSE_AUTO_CENTER=true;
        }
    }

}
