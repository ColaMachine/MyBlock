package com.dozenx.game.engine.ui.toolbar.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import com.dozenx.game.engine.ui.inventory.view.SlotPanel;

/**
 * Created by luying on 17/2/5.
 */
public class ToolbarView extends SlotPanel {
    public ToolbarView(int numSlotsX, int numSlotsY) {
        super( numSlotsX,  numSlotsY);
        this.setPosition(HtmlObject.POSITION_ABSOLUTE);
        this.setTop(Constants.WINDOW_HEIGHT-40);
        this.setLeft(0);
    }
}
