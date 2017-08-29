package com.dozenx.util;

/**
 * Created by dozen.zhang on 2017/6/3.
 */
public class RandomUtil {

    public static int getRandom(int num){
        return (int)(Math.random()*Math.pow(10,num));
    }

    public static int getRandom(int min,int max){
        return min+(int)(Math.random()*(max-min));
    }

}
