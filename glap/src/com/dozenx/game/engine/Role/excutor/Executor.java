package com.dozenx.game.engine.Role.excutor;

import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.GameCmd;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.engine.live.state.State;

/**
 * Created by luying on 17/3/5.
 */
public class Executor {

    Model model ;
    public Executor(Role role ){
        model = new PlayerModel(role);
    }
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    State currentState ;
    public void receive(GameCmd cmd ){
        currentState.receive(cmd);
    }

}
