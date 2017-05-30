package com.dozenx.game.engine.command;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/2/18.
 */
public class BlockUtil {

    public static int getIndex(GL_Vector placePoint,GL_Vector viewDir){
        float pianyiX = placePoint.x%1;
        float pianyiY = placePoint.y%1;
        float pianyiZ= placePoint.z%1;
        int block=0;
        if(pianyiX<0.5){
            if(pianyiY<0.5){
                if(pianyiZ<0.5){
                    block=4;
                }else{
                    block=1;
                }
            }else{
                if(pianyiZ<0.5){
                    block=5;
                }else{
                    block=8;
                }
            }
        }else{
            if(pianyiY<0.5){
                if(pianyiZ<0.5){
                    block=3;
                }else{
                    block=2;
                }
            }else{
                if(pianyiZ<0.5){
                    block=7;
                }else{
                    block=6;
                }
            }
        }
        int condition=0;
        if(Math.abs(viewDir.x)>Math.abs(viewDir.z)){
            if(block==1 ||  block==4||block==5 ||  block==8){
                condition= Constants.LEFT;
            }else{
                condition=Constants.RIGHT;
            }
        }else{
            if(block==1 ||  block==2||block==5 ||  block==6){
                condition=Constants.FRONT;
            }else{
                condition=Constants.BACK;
            }
        }
        return condition;
       // cmd.blockType  = condition<<8|cmd.blockType;
    }
}
