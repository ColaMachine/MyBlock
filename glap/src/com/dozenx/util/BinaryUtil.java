package com.dozenx.util;

/**
 * Created by luying on 16/9/15.
 */
public class BinaryUtil {

    public static  String toString(int a){
        StringBuffer sb =new StringBuffer(32);
    for(int i=31;i>=0;i--){
       // System.out.println(a +"&"+(i<<i)+ "="+(a & (1<<i) ));
        System.out.print((a & (1<<i) )!=0?"1":"0");

        sb.append((a & (1<<i) )>=0?"1":"0");
    } System.out.println();
    return sb.toString();
    }


    public static void main(String args[]){
        BinaryUtil.toString(1);
        //System.out.println(1 &(1<<1));

    }
}
