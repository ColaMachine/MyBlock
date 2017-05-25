package com.dozenx.game.engine.item;

import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.ByteUtil;

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
            if(blockId== ItemType.wood_door.ordinal()){
                return true;
            }
        }


        return false;
    }


}
