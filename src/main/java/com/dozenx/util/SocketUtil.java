package com.dozenx.util;

import cola.machine.game.myblocks.engine.Constants;
import core.log.LogUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dozen.zhang on 2017/4/24.
 */
public class SocketUtil {

    public static byte[] read(InputStream inputSteram){
        byte[] bytes=new byte[4];
        try {
            inputSteram.read(bytes,0,4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = ByteUtil.getInt(bytes);
        //ByteUtil.clear(bytes);
        if (length <= 0) {
           LogUtil.err("读取的数据为:"+length);
        //Thread.sleep(1000);
       // continue;
        }

        //int n=0;

        byte[] newBytes = new byte[length];
        try {
            int remaining=length;
            while(remaining>0) {
                int  n=inputSteram.read(newBytes, length-remaining, remaining);
                remaining -= n;

            }
            int end = inputSteram.read();
            if ( end != Constants.end) {
                LogUtil.err(" read error ");
              //  beginRepair(inputSteram);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newBytes;
    }

}
