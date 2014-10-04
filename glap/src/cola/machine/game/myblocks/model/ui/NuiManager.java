package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.Menu.PauseMenu;
import glapp.GLApp;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.tool.ToolBar;

public class NuiManager {
    ToolBar toolbarcontainer;
    Cross cross;
    Bag bag;
    PauseMenu PauseMenu;
    public  NuiManager(){
       
        cross=new Cross();

        bag=new Bag();
         PauseMenu =new PauseMenu();
        toolbarcontainer = new ToolBar();
    }

    public void render(){
    	   GLApp.pushAttribOrtho();
           // switch to 2D projection
           GLApp. setOrthoOn();
        cross.render();
        toolbarcontainer.render();
        bag.render();

        PauseMenu.render();
        
          GLApp.setOrthoOff();
        // return to previous settings
       GLApp. popAttrib();
    }
}
