package com.dozenx.game.input;

import cola.machine.game.myblocks.lifething.bean.GameActor;

/**
 * Created by luying on 16/11/13.
 */
public interface  Command {

    public void execute(GameActor actor);
}
