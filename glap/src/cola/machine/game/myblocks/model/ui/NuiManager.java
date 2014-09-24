package cola.machine.game.myblocks.model.ui;

import glapp.GLApp;
import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.tool.ToolBar;

public class NuiManager {
    ToolBar toolbarcontainer;
    Cross cross;
    Bag bag;
    public  NuiManager(){
       
        cross=new Cross();

        bag=new Bag();
        
        toolbarcontainer = new ToolBar();
    }

    public void render(){
    	   GLApp.pushAttribOrtho();
           // switch to 2D projection
           GLApp. setOrthoOn();
        cross.render();
        toolbarcontainer.render();
        bag.render();
        
   
        
          GLApp.setOrthoOff();
        // return to previous settings
       GLApp. popAttrib();
    }
}
