package com.dozenx.util;

/**
 * Created by luying on 17/2/27.
 */
public class TimeUtil {
    static long[] slot=new long[42];
    static int index=0;
    public static void update(){
        slot[index]=System.currentTimeMillis();
        index++;
        if(index>=slot.length){
            index=0;
        }
    }
    public static long getNowMills(){

        return slot[index];
    }

}
