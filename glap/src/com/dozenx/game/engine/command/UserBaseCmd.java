package com.dozenx.game.engine.command;

import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 17/2/7.
 */
public abstract class UserBaseCmd extends BaseGameCmd{
    protected int userId;


    public UserBaseCmd(){



    }
    public UserBaseCmd(int userId){
        this.userId = userId;


    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



}
