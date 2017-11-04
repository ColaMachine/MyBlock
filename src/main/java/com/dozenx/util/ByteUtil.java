package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import com.dozenx.game.engine.command.ItemType;
import core.log.LogUtil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByteUtil
{
    public static void clear(byte[] bytes){
        for(int i=0;i<bytes.length;i++){
            bytes[i]=0;
        }
    }
    public static ByteBufferWrap createBuffer(){
        return new ByteBufferWrap();
    }
    public static SimpleByteBufferWrap createSimpleBuffer(int capacity){
        return  new SimpleByteBufferWrap(capacity,false);
    }

    public static SimpleByteBufferWrap createSimpleBuffer(byte[] bytes){
        return  new SimpleByteBufferWrap(bytes);
    }
    public static ByteBufferWrap createBuffer(byte[] bytes){
        return new ByteBufferWrap(bytes);
    }
    public static byte[] copy(byte[] bytes,int start,int length){
        byte[] newBytes =new byte[length];
        for(int i=0;i<length;i++){
            newBytes[i]=bytes[start+i];
        }
        return newBytes;
    }

    public static byte[] slice(byte[] src,int start,int length){
        try {
            byte[] bytes = new byte[length];
            System.arraycopy(src, start, bytes, 0, length);
            return bytes;
        }catch (Exception e){
            LogUtil.err(e);
        }
        return null;
    }

   /* public static ByteBuffer createBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate();
    }*/
    public static byte[] getBytes(byte data)
    {
        byte[] bytes = new byte[1];
        bytes[0]= data;
        return bytes;
    }

    public static byte[] getBytes(short data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(byte[]... byteArrys)
    {
        int sum=0;
       for(int i=0;i<byteArrys.length;i++){
           sum += byteArrys[i].length;
       }
        byte[] bytes= new byte[sum];
        int index=0;
        for(int i=0;i<byteArrys.length;i++){
            for(int j=0;j<byteArrys[i].length;j++){
                bytes[index]=byteArrys[i][j];
                index++;
            }
        }
        return bytes;
    }

    public static byte[] getBytes(byte[]byteArry,int startIndex,int length)
    {

        byte[] bytes= new byte[length];
        if(startIndex+length >=byteArry.length){
            return null;
        }
        for(int i=0;i<length;i++){

                bytes[i]=byteArry[i+startIndex];


        }
        return bytes;
    }

    public static byte[] getBytes(char data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }
    public static byte[] getBytes(int data)
    {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (data & 0xff);
        bytes[2] = (byte) ((data & 0xff00) >> 8);
        bytes[1] = (byte) ((data & 0xff0000) >> 16);
        bytes[0] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }
    public static byte[] getBytes123(int data)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] getBytes123(long data)
    {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }
    public static byte[] getBytes(long data)
    {
        byte[] bytes = new byte[8];
        bytes[7] = (byte) (data & 0xff);
        bytes[6] = (byte) ((data >> 8) & 0xff);
        bytes[5] = (byte) ((data >> 16) & 0xff);
        bytes[4] = (byte) ((data >> 24) & 0xff);
        bytes[3] = (byte) ((data >> 32) & 0xff);
        bytes[2] = (byte) ((data >> 40) & 0xff);
        bytes[1] = (byte) ((data >> 48) & 0xff);
        bytes[0] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

   public static byte[] getBytes(float data)
    {
        int intBits = Float.floatToIntBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(double data)
    {
        long intBits = Double.doubleToLongBits(data);
        return getBytes(intBits);
    }

    public static byte[] getBytes(String data, String charsetName)
    {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static byte[] getBytes(String data)
    {
        return getBytes(data, "GBK");
    }

/*
    public static short getShort(byte[] bytes)
    {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }*/

    public static short getShort(byte[] bytes)
    {
        return (short) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
    }

   /* public static char getChar(byte[] bytes)
    {
        return (char) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
    }*/

    public static char getChar(byte[] bytes)
    {
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

  /*  public static int getInt(byte[] bytes)
    {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }
*/

    public static int getInt(byte[] bytes)
    {
        return (0xff & bytes[3]) | (0xff00 & (bytes[2] << 8)) | (0xff0000 & (bytes[1] << 16)) | (0xff000000 & (bytes[0] << 24));
    }


    public static long getLong(byte[] bytes)
    {
        return(0xffL & (long)bytes[0]) | (0xff00L & ((long)bytes[1] << 8)) | (0xff0000L & ((long)bytes[2] << 16)) | (0xff000000L & ((long)bytes[3] << 24))
                | (0xff00000000L & ((long)bytes[4] << 32)) | (0xff0000000000L & ((long)bytes[5] << 40)) | (0xff000000000000L & ((long)bytes[6] << 48)) | (0xff00000000000000L & ((long)bytes[7] << 56));
    }

    public static float getFloat(byte[] bytes)
    {
        return Float.intBitsToFloat(getInt(bytes));
    }

    public static double getDouble(byte[] bytes)
    {
        long l = getLong(bytes);
        System.out.println(l);
        return Double.longBitsToDouble(l);
    }

    public static String getString(byte[] bytes, String charsetName)
    {
        return new String(bytes, Charset.forName(charsetName));
    }

    public static String getString(byte[] bytes)
    {
        return getString(bytes, "GBK");
    }


    public static void main(String[] args)
    {
        System.out.println(ByteUtil.toBinaryStr(5419));
        System.out.println(ByteUtil.toBinaryStr(102));
        System.out.println(ByteUtil.toBinaryStr(43));
        System.out.println(ByteUtil.toBinaryStr(ByteUtil.HEX_1_1_0_0));
        System.out.println(ItemType.wood_door.id);
System.out.println(ByteUtil.get8_0Value(555));

        System.out.println((byte)254);
       short s = 122;
        int i = 122;
        long l = 1222222;

        char c = 'a';

        float f = 122.22f;
        double d = 122.22;

        String string = "我是好孩子";
        System.out.println(s);
        System.out.println(i);
        System.out.println(l);
        System.out.println(c);
        System.out.println(f);
        System.out.println(d);
        System.out.println(string);

        System.out.println("**************");

        System.out.println(getShort(getBytes(s)));
        System.out.println(getInt(getBytes(i)));
        System.out.println(getLong(getBytes(l)));
        System.out.println(getChar(getBytes(c)));
        System.out.println(getFloat(getBytes(f)));
        System.out.println(getDouble(getBytes(d)));
        System.out.println(getString(getBytes(string)));

        System.out.println("toFullBinaryString"+toBinaryStr(-13602928));
        System.out.println("toHex"+toHex(-13602928));
        System.out.println("toHex"+toHex(-13537135));
        System.out.println("00000001 转int:"+binaryStr2int("00000001"));

        byte[] bytes = hexStr2Bytes("B8F6");
        //10830
        Integer a= Integer.parseInt("4e2a",16);
        System.out.println("00000001 转int:"+binaryStr2int("00000001"));
        try {
            byte[] byteary = "个".getBytes("gbk");//-72 -10
            System.out.println("个的gbk 16进制表示:"+Integer.toHexString(byteary[0]) +""+Integer.toHexString(byteary[1]) );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int[] arr = getValueSplit4Slot(unionBinary(1,2,3,4));
        System.out.println(""+arr[0]+arr[1]+arr[2]+arr[3]);
        System.out.println(toBinaryStr(15));
        System.out.println(toBinaryStr(15&HEX_0_0_0_1));
        System.out.println(unionBinary(1,2,3,4));
    }

    public  static int unionBinary(int a,int b,int c,int d){
        return (((a&HEX_0_0_0_1 ) <<12 )|
        ((b&HEX_0_0_0_1 ) <<8 )|
        ((c&HEX_0_0_0_1 ) <<4 )|
        ((d&HEX_0_0_0_1 ) <<0 ));

    }

    /**
     * 将3个小于16的数 和一个 最大24bit的数据结合再一起
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public  static int unionBinary4_4_4_24(int a,int b,int c,int d){
        return (((a&HEX_0_0_0_1 ) <<28 )| //4
                ((b&HEX_0_0_0_1 ) <<24 )|   //
                ((c&HEX_0_0_0_1 ) << 20)|
                ((d<<12 >>12 )  ));

    }

    public  static int unionBinary4_4_8_16(int a,int b,int c,int d){
        return (((a&HEX_0_0_0_1 ) <<28 )| //4
                ((b&HEX_0_0_0_1 ) <<24 )|   //
                ((c&HEX_0_0_1_1 ) << 16)|
                ((d<<16 >>16 )  ));

    }
    public  static int unionBinary4_8_4_16(int a,int b,int c,int d){
        return (((a&HEX_0_0_0_1 ) <<28 )| //4
                ((b&HEX_0_0_0_1 ) <<20 )|   //
                ((c&HEX_0_0_1_1 ) << 16)|
                ((d<<16 >>16 )  ));

    }

    public static int [] getValueSplit4Slot(int value ){

        return new int[]{get16_12Value(value),get12_8Value(value),get8_4Value(value),get4_0Value(value)};

    }
    public static int [] getValueSplit8Slot(int value ){

        return new int[]{get32_28Value(value),get28_24Value(value),get24_16Value(value),get16_0Value(value)};

    }
    public static int get32_28Value(int value ){
        return (value>>16 & HEX_1_0_0_0 )>>12 &HEX_0_0_0_1;
    }
    public static int get28_24Value(int value ){
        return (value>>16 & HEX_0_1_0_0 )>>8&HEX_0_0_0_1;
    }
    public static int get24_20Value(int value ){
        return (value>>16 & HEX_0_0_1_0 )>>4&HEX_0_0_0_1;
    }
    public static int get24_16Value(int value ){
        return (value>>16 & HEX_0_0_1_1 );
    }
    public static int get20_16Value(int value ){
        return (value>>16 & HEX_0_0_0_1)>>0&HEX_0_0_0_1;
    }

    public static int get16_12Value(int value ){
        return (value & HEX_1_0_0_0 )>>12&HEX_0_0_0_1;
    }
    public static int get16_0Value(int value ){
        return (value <<16 )>>16;
    }
    public static int get12_8Value(int value ){
        return (value & HEX_0_1_0_0 )>>8&HEX_0_0_0_1;
    }
    public static int get8_4Value(int value ){
        return (value & HEX_0_0_1_0 )>>4&HEX_0_0_0_1;
    }
    public static int get4_0Value(int value ){
        return (value & HEX_0_0_0_1)>>0;
    }
    public static int get8_0Value(int value ){
        return (value & HEX_0_0_1_1);
    }
    public static int get16_8Value(int value ){
        return (value & HEX_1_1_0_0)>>8;
    }

    public static String toBinaryStr(int num) {
        char[] chs = new char[Integer.SIZE];
        for(int i = 0; i < Integer.SIZE; i++) {
            chs[Integer.SIZE - 1 - i] = (char)(((num >> i) & 1) + '0');
        }
        return new String(chs);
    }
    public static String toHex(int i){
        return Integer.toHexString(i);
    }
    public static void printBinary(String value){

    }
    public static void toBinary(int value){

    }
    /*public static void toBinaryStr(int value){

    }*/

    public static int[] to8BinaryInt(byte b){
        int[] bites = new int[8];
        for(int i=0;i<8;i++){
            bites[i]= (b >> (8-i-1))& 1;
            // System.out.print(bites[i]);
        }
        return bites;
    }
    public static String to8BinaryStr(byte b){
        int[] bites = new int[8];
        char[] chars =new char[8];
        for(int i=0;i<8;i++){
            chars[i]=(char)(((b >> (8-i-1))& 1)+48);
            //System.out.print(bites[i]);
        }

        return String.valueOf(chars);
    }
    public static int binaryStr2int(String binaryStr){
        return Integer.parseInt(binaryStr,2);
}
   /* public static void main(String[] args){
        System.out.println(ByteUtil.to8BinaryStr((byte)255));
    }*/


    /**
     * 字符串转换成十六进制字符串
     */

    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     *
     * 十六进制转换字符串
     */

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    /**
     *String的字符串转换成unicode的String
     */
    public static String str2Unicode(String strText) throws Exception {
        char c;
        String strRet = "";
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                strRet += "//u" + strHex;
            } else {
                // 低位在前面补00
                strRet += "//u00" + strHex;
            }
        }
        return strRet;
    }


    /**
     *unicode的String转换成String的字符串
     */
    public static String unicode2Str(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }
    //1111 0000 0000 0000
    // 0000 1111 0000 0000
    // 0000 0000 1111 0000
    //0000 0000 0000 1111
    public final static short HEX_1_0_0_0 =(short) (15<<12);
    public final static short HEX_0_1_0_0 = 15<<8;
    public final static short HEX_0_0_1_0 = 15<<4;
    public final static short HEX_0_0_0_1 = 15<<0;
    public final static short HEX_0_0_1_1 = 255;
    public final static short HEX_0_1_1_1 = HEX_0_0_1_1 | HEX_0_1_0_0;
    public final static short HEX_1_1_0_0 = (short)(255<<8);
}
