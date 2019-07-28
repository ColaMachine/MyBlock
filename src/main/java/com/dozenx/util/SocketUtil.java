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
        int headLen=0;
        try {
//            int index = inputSteram.read(bytes,0,4);
//            LogUtil.println("第"+index+"个包");
            headLen = inputSteram.read(bytes,0,4);//决定了长度
            if(headLen<4){
                LogUtil.err("头部数据少于4:"+headLen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = ByteUtil.getInt(bytes);
        //ByteUtil.clear(bytes);
        if (length <= 0) {
           LogUtil.err("读取的数据为:"+length);
            return null;
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

                //要求对面重发  返回错误 要求重发


                System.exit(0);
              //  beginRepair(inputSteram);
            }else{
                //对的告诉对方可以继续了
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newBytes;
    }

}
