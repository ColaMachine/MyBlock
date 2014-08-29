package cola.machine.game.myblocks.model.ui;

import cola.machine.game.myblocks.model.ui.tool.ToolBarContainer;

public class NuiManager {
    ToolBarContainer toolbarcontainer;
    public  NuiManager(){
         toolbarcontainer = new ToolBarContainer();
    }

    public void render(){
        toolbarcontainer.render();
    }
}
