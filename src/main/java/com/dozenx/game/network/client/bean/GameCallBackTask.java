package com.dozenx.game.network.client.bean;

import com.dozenx.game.engine.command.ResultCmd;

/**
 * Created by dozen.zhang on 2017/2/21.
 */
public class GameCallBackTask implements Runnable {
    private ResultCmd result;

    public ResultCmd getResult() {
        return result;
    }

    public void setResult(ResultCmd result) {
        this.result = result;
    }

    @Override
    public void run() {

    }
}
