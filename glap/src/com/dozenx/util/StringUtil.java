package com.dozenx.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String EMPTY = "";
	public static boolean  isEmpty(String str){
		if(str ==null ||  str.length()==0){
			return true;
		}
		return false;
	}
	public static boolean  isBlank(String str){
		if(str ==null ||  str.length()==0){
			return true;
		}
		return false;
	}

	public static boolean  isNull(Object obj){
		if(obj==null)
			return true;

		if(obj.getClass()==Integer.class||obj.getClass()==double.class||obj.getClass()==Float.class){

			return  (int)obj==0;
		}
		return false;
	}
	public static boolean  isNotEmpty(String str){
		
		return !isEmpty(str);
	}
	
	public static boolean checkEmailValid(String email){
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}
	public static boolean isNumeric(String str){
		if (str == null|| str.equals("")) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	public static boolean checkNumeric(String str){
        if (str == null|| str.equals("")) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean checkDateStr(String str,String format){
	    format=format.replaceAll("[yMdhHms]", "\\\\d");
	   return str.matches(format);
	}

	public static boolean checkUserNameValid(String username){
		String regex = "/^[0-9A-Za-z]*[a-zA-Z]+[0-9A-Za-z]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		return matcher.find();
	}
	public static boolean checkFloat(String value,int integer,int fraction){
	    String regex= String.format("^[+|-]?\\d{1,%d}(\\.\\d{1,%d})?$", integer,fraction);
	    if(fraction==0){
	        regex= String.format("^[+|-]?\\d{1,%d}$", integer);
	    }
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}
	public static String getAbc(String abc){
	    return abc.substring(0, 1).toUpperCase()+abc.substring(1);
	}
	public static String getabc(String abc){
        return abc.substring(0, 1).toLowerCase()+abc.substring(1);
    }
	
	public  static String join(String join,Object [] strAry){
		StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
             if(i==(strAry.length-1)){
                 sb.append(strAry[i]);
             }else{
                 sb.append(strAry[i]).append(join);
             }
        }
        return new String(sb);
	}
	
	public static String getContentBetween(String content,String a,String b){
		int index = content.indexOf(a)+a.length();
		int last = content.lastIndexOf(b);
		return content.substring(index, last);
	}
    public static boolean isPhone(String value) {
        String regex = "^[1][3578][0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    public static boolean isEmail(String value) {
        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    public static boolean isID(String value) {
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
    public static boolean splitStrContains(String longStr,String bean){
        if(longStr.indexOf(","+bean+",")>=0 || longStr.startsWith(bean+",") || longStr.endsWith(","+bean) || longStr.equals(bean)){
            return true;
        }
        return false;
    }
    /*public static void main(String[] args) {
        System.out.println(StringUtil.isID("330104198601292711"));;
    }*/

	/**  随机数字池**/
	public static String randDigitString = "0123456789";//随机产生的字符串
	
	/**随机字母池  **/
	public static String randAlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
	public static String randAlphaDigitString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
	public static String getRandomString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=(randDigitString+randAlphaString).charAt((int)(Math.random()*10));
		}
		return code;
	}
	/**
	 * 说明:得到随机字母字符串
	 * @param len
	 * @return
	 * @return String
	 * @author dozen.zhang
	 * @date 2016年3月18日下午9:07:32
	 */
	public static String getRandomAlphaString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=randAlphaString.charAt((int)(Math.random()*randAlphaString.length()));
		}
		return code;
	}
	/**
	 * 说明:得到随机字母字符串
	 * @param len
	 * @return
	 * @return String
	 * @author dozen.zhang
	 * @date 2016年3月18日下午9:07:32
	 */
	public static String getRandomAlphaDigitString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=randAlphaString.charAt((int)(Math.random()*randAlphaDigitString.length()));
		}
		return code;
	}
	/**
	 * 说明:获得随机数字字符串
	 * @param len
	 * @return String
	 * @author dozen.zhang
	 * @date 2016年3月18日下午9:07:00
	 */
	public static String getRandomDigitString(int len){
		String code ="";
		for(int i=0;i<len;i++){
			code+=randDigitString.charAt((int)(Math.random()*randDigitString.length()));
		}
		return code;
	}
	/**
	 * 说明:检查是否只有字母和数字
	 * @param str
	 * @return
	 * @return boolean
	 * @author dozen.zhang
	 * @date 2016年3月18日下午9:06:40
	 */
	public static boolean checkAlphaNumeric(String str){
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isAlphabetic(str.charAt(i))==false&&Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	   /**
	 * 说明:检查是否是只有字母
	 * @param str
	 * @return boolean
	 * @author dozen.zhang
	 * @date 2016年3月18日下午9:06:01
	 */
	public static boolean checkAlpha(String str){
	        if (str == null) {
	            return false;
	        }
	        int sz = str.length();
	        for (int i = 0; i < sz; i++) {
	            if (Character.isAlphabetic(str.charAt(i))==false) {
	                return false;
	            }
	        }
	        return true;
	    }
	/**
	 * 说明:检验年月日格式
	 * @param type
	 * @return String
	 * @author dozen.zhang
	 * @date 2016年3月18日下午9:06:19
	 */
	public static String getYMDStr(String type){
        String ymd ="";
        if(type.startsWith("date")){
            ymd="yyyy-MM-dd";
        }else{
            ymd="yyyy-MM-dd HH:mm:ss";
        }
return ymd;
    }




	/**
	 * 获取异常堆栈信息
	 * @param e
	 * @return
	 */
	public static String getExceptionStackTrace(Exception e){
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer,true));
		return writer.toString();
	}

	/**
	 * 转义get方式提交的中文字符
	 * @param str
	 * @return
	 */
	public static String fromGetRequest(String str) {
		String result="";
		try {
			if(str==null || str.equals(""))return "";
			result = new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * byte数组转换成16进制字符串
	 * @param b
	 * @return
	 */
	public static String bytesToHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 16进制字符串转成byte数组
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString){
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		int hexLength = length;
		while(hexLength % 8 != 0){
			hexLength++;
		}
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[hexLength];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 字符串转16进制字符串
	 * @param str
	 * @return
	 */
	public static String stringToHexString(String str) {
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
	 * 16进制字符串转字符串
	 * @param str
	 * @return
	 */
	public static String hexStringToString(String str) {
		byte[] baKeyword = new byte[str.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			str = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return str;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}


	/**
	 * 加密算法
	 * @param data	加密数据
	 * @param key	秘钥
	 * @return 加密结果
	 */
	public static byte[] desEnCryt(byte[] data, byte[] key) {
		byte[] result = null;
		try {
			SecureRandom sr = new SecureRandom();
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			// 创建Cipher对象
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			// 初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
			// 加解密
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解密算法
	 * @param data	解密数据
	 * @param key	秘钥
	 * @return 解密结果
	 */
	public static byte[] desDeCryt(byte[] data, byte[] key) {
		byte[] result = null;
		try {
			SecureRandom sr = new SecureRandom();
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * DES加密方法
	 * @param message	加密数据
	 * @param keyString	密钥
	 * @return 加密结果
	 */
	public static String encryptByDes(String message, String keyString) {
		String dataHexString = stringToHexString(message);
		String keyHexString = stringToHexString(keyString);
		byte[] data = hexStringToBytes(dataHexString);
		byte[] key = hexStringToBytes(keyHexString);
		byte[] result = null;
		try {
			SecureRandom sr = new SecureRandom();
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, secretkey, sr);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesToHexString(result);
	}

	/**
	 * DES解密方法
	 * @param dataHexString	解密数据
	 * @param keyString	密钥
	 * @return
	 */
	public static String decryptByDes(String dataHexString, String keyString) {
		String keyHexString = stringToHexString(keyString);
		byte[] data = hexStringToBytes(dataHexString);
		byte[] key = hexStringToBytes(keyHexString);
		byte[] result = null;
		try {
			SecureRandom sr = new SecureRandom();
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, secretkey, sr);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexStringToString(bytesToHexString(result)).trim();
	}
	/**
	 * AES解密方法
	 * @param dataHexString	解密数据
	 * @param decryptKey	密钥
	 * @return
	 */
	public static String aesDecryptByBytes(String dataHexString, String decryptKey){
		if (StringUtil.isEmpty(dataHexString)) {
			return "";
		}
		byte[] decryptBytes = null;
		try {
			byte[] encryptBytes = StringUtil.base64Decode(dataHexString);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(decryptKey.getBytes());
			kgen.init(128, secureRandom);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
			decryptBytes = cipher.doFinal(encryptBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexStringToString(bytesToHexString(decryptBytes)).trim();
	}

	public static byte[] base64Decode(String base64Code) throws Exception{
		return new BASE64Decoder().decodeBuffer(base64Code);
	}
static HashMap<Character,Character> gbkMap =null;

    //http://www.cnblogs.com/human/p/3517607.html
    public static String byte2Str(byte[] bytes,String charset) throws Exception {
        StringBuffer  returnStr= new StringBuffer();
        int unicode =0;
       // byte[] bytes =new byte[4];
        String unicodeBiteStr="";
        if(charset.equals("gbk")){
           if(gbkMap==null) {
               gbkMap=new HashMap<Character,Character>();
               List<String> lists =new ArrayList<String>();
               lists= FileUtil.readFile2List("/Users/luying/Documents/workspace/calendar/gbk2unicode");
                for(String line:lists){
                    if(!StringUtil.isBlank(line)){
                        String[] ary = line.split(" ");

                       // byte gbkByte =Byte.parseByte(ary[0],16);
                       // byte unicodeByte  =Byte.parseByte(ary[1],16);
                       byte[] gbkBytes =ByteUtil.hexStr2Bytes(ary[0]);
                        char gbkByte =ByteUtil.getChar(gbkBytes);//char)(gbkBytes[0]<<4+gbkBytes[1]);

                        byte[] unicodeBytes =ByteUtil.hexStr2Bytes(ary[2]);
                        char unicodeByte =ByteUtil.getChar(unicodeBytes);//(char)(unicodeBytes[0]<<4+unicodeBytes[1]);


                        gbkMap.put(gbkByte,unicodeByte);
                    }
                }
            }
            //Byte.parseByte("")
             //newByte= Byte.parseByte("B8F6",16);
            //byte[] newBytes= ByteUtil.hexStr2Bytes("B8F6");
            //char newChar = ByteUtil.getChar(newBytes);
            //gbkMap.get(newChar);*/
            //char unicodeChar1 = gbkMap.get(newChar);
            //System.out.println(unicodeChar1);


            for(int i=0;i<bytes.length;i++){
                if(bytes[i]>0&&bytes[i]<=107){
                    returnStr.append((char)bytes[i]);
                    //s+=;
                }else{
                    byte[] byteAry =new byte[2];
                    byteAry[0]= bytes[i];
                    byteAry[1]= bytes[i+1];
                    char ch = ByteUtil.getChar(byteAry);
                    //int ch = (bytes[i]<<4+bytes[i+1]);
                    i++;
                    char unicodeChar = gbkMap.get(ch);
                    //s+=unicodeChar;
                    returnStr.append((char)bytes[i]);
                }
            }

        }else {//什么是utf-8 utf-8 有明显的特征他的第一位有几个1说明她有几位 后面的几位都是01开头的s
            //boolean start=false;
            int weishu=0;
            for(int i=0;i<bytes.length;i++){
                int nowIndex =i;
                 weishu =0;
                int[] biteAry = ByteUtil.to8BinaryInt(bytes[i]);
                //开始计算有几个头部1
                String newUnicodeBiteStr="";
                for(int j=0;j<biteAry.length;j++){
                    if(biteAry[j]==1){
                        weishu++;
                    }else{
                        break;
                    }

                }
                //得到剩余的比特
                for(int j=weishu+1;j<8;j++){
                    newUnicodeBiteStr+=biteAry[j];

                }
                //将剩余的数值转换成unicode值
                for(int biteLeft =2;biteLeft<=weishu;biteLeft++){
                    i++;
                    int[] nextBiteAry = ByteUtil.to8BinaryInt(bytes[i]);
                    if(nextBiteAry[0]!=1 || nextBiteAry[1]!=0){
                        throw new Exception("it's not utf-8 charset encode utf-8 must begin with 10");
                    }
                    for(int k=2;k<8;k++){
                        newUnicodeBiteStr+=nextBiteAry[k];
                    }

                }

                /*if(ByteUtil.to8BinaryInt(bytes[i])[0]==0){
                    //说明是什么呢 说明是单字节字符
                    char chars=(char) bytes[i];
                    s+=String.valueOf(chars);
                    start=false;
                }else{
                    if(!start){
                        String binaryStr = ByteUtil.to8BinaryStr(bytes[i]);
                        int oneNum =1;
                        int zeroIndex=1;
                        while(binaryStr.charAt(zeroIndex)=='1'){
                            zeroIndex++;
                        }
                        weishu=zeroIndex+1;
                        if(weishu>4){
                            System.out.println("该位数超过了4为");
                        }

                    }
                }*/

                //把那个转成unicode代表的字符

               // System.out.println((char)ByteUtil.binaryStr2int(newUnicodeBiteStr));
                returnStr.append((char)ByteUtil.binaryStr2int(newUnicodeBiteStr));
            }
        }
        return returnStr.toString();

    }

    public static void main(String args[]){
        try {
           System.out.println( StringUtil.byte2Str(new String("1234567890ab个你好啊我知道你是谁").getBytes("gbk"), "gbk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
