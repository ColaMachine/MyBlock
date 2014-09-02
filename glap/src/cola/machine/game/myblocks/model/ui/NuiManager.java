package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.tool.ToolBarContainer;

public class NuiManager {
    ToolBarContainer toolbarcontainer;
    Cross cross;
    Bag bag;
    public  NuiManager(){
       
        cross=new Cross();

        bag=new Bag();
        
        toolbarcontainer = new ToolBarContainer();
    }

    public void render(){
       
        cross.render();
        bag.render();
        
        toolbarcontainer.render();
    }
}
