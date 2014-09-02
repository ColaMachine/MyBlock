package cola.machine.game.myblocks.model.ui;

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
       
        cross.render();
        bag.render();
        
        toolbarcontainer.render();
    }
}
