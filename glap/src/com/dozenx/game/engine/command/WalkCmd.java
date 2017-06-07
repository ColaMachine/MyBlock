package com.dozenx.game.engine.command;

import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/7.
 */
public class WalkCmd extends BaseGameCmd{
    public GL_Vector from  ;
    public GL_Vector to;

    float fromX;
    float fromY;
    float fromZ;
    float toX;
    float toY;
    float toZ;

    public static int FORWARD=1;
    public static int LEFT=2;
    public static int RIGHT=3;
    public static int BACK=4;
    public WalkCmd(){

    }
    public WalkCmd(boolean forward,boolean left ,boolean right,boolean back){

    }
    private CmdType cmdType =CmdType.WALK;
    @Override
    public CmdType getCmdType() {
        return cmdType;
    }
}
