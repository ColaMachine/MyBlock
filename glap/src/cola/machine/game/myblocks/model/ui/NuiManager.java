package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.bag.Bag;
import cola.machine.game.myblocks.model.ui.tool.ToolBarContainer;

public class NuiManager {
    ToolBarContainer toolbarcontainer;
    Cross cross;
    Bag bag;
    public  NuiManager(){
         toolbarcontainer = new ToolBarContainer();
        cross=new Cross();

        bag=new Bag();
    }

    public void render(){
        toolbarcontainer.render();
        cross.render();
        bag.render();
    }
}
