package com.dozenx.game.server.user.bean;

import cola.machine.game.myblocks.server.Worker;
import com.dozenx.game.engine.command.GameCmd;

/**
 * Created by luying on 17/2/18.
 */
public class GameRequest {
    private GameCmd cmd ;

    public GameCmd getCmd() {
        return cmd;
    }

    public void setCmd(GameCmd cmd) {
        this.cmd = cmd;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    private Worker worker;
    public GameRequest(GameCmd cmd ,Worker worker){
        this.cmd =cmd ;
        this. worker = worker;
    }


}
