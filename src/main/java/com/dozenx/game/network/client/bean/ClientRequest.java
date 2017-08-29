package com.dozenx.game.network.client.bean;

import com.dozenx.game.engine.command.GameCmd;

/**
 * Created by luying on 17/2/19.
 */
public class ClientRequest {
    private int id;//唯一id
    private Runnable callBack;//回调函数
    private GameCmd cmd;//方法 参数

    
}
