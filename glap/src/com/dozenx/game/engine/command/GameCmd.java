package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/7.
 */
public interface GameCmd {
    public byte[] toBytes();
    public void delete();

    public int val();
}
