package cola.machine.game.myblocks.input;

import cola.machine.game.myblocks.engine.SimpleUri;
import cola.machine.game.myblocks.input.events.ButtonEvent;


public class BindButtonEvent  extends ButtonEvent{//

    private SimpleUri id;//identity
    private ButtonState state;//是 按下去 释放 还是down up repeat

    public BindButtonEvent() {
        super(0);
    }

    public void prepare(SimpleUri buttonId, ButtonState newState, float delta) {
        reset(delta);
        this.id = buttonId;
        this.state = newState;
    }

    public SimpleUri getId() {
        return id;
    }

    public ButtonState getState() {
        return state;
    }


}
