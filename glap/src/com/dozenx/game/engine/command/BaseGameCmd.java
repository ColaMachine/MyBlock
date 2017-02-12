package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/7.
 */
public class BaseGameCmd  implements  GameCmd{
    private boolean deleted;
    @Override
    public void delete() {
        this.deleted=true;
    }

    public int val;

    @Override
    public int val(){
        return val;

    }

    public void setVal(int val){
        this.val=val;
    }
}
