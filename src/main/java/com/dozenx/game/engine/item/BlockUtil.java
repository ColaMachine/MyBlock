package com.dozenx.game.engine.item;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.ByteUtil;
import glmodel.GL_Vector;

/**
 * Created by dozen.zhang on 2017/5/23.
 */
public class BlockUtil {
    public static boolean isDoorOpen(int blockId){
        int state = ByteUtil.get16_8Value(blockId);
        int switcher = ByteUtil.get8_4Value(state);
        int dir = ByteUtil.get4_0Value(state);
        if(switcher==1){
            return true;
        }
        return false;
    }
    public static boolean isDoor(int blockId){
        if(blockId>256){
            blockId = ByteUtil.get8_0Value(blockId);
            if(blockId== ItemType.wood_door.id){
                return true;
            }
        }


        return false;
    }
    public static int getFaceDir(GL_Vector placePoint, GL_Vector viewDir){
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

    public  int getBlockId(String name){
        return 0;
    }

    /**
     * 方块的id 是0~256中间的 多余的高位都是方块的状态
     * @param blockValue
     * @return
     */
    public static int getRealBlockId(int blockValue){
        if(blockValue>255){
            return blockValue& ByteUtil.HEX_0_0_1_1;
        }
        return blockValue;
    }


}
