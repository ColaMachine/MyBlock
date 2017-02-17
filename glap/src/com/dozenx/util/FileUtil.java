/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2016年3月13日
 * 文件说明: 
 */
package com.dozenx.util;



import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.log.LogUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<File> readAllFileInFold(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if(!file.exists()){
            LogUtil.println("not exsits "+path);
            System.exit(0);
        }
        if(!file.isDirectory()){
            LogUtil.err("it's not folder "+path);
        }
        return listFile(file);


    }

    public static List<File> listFile(File file ){
        List<File > fileList =new ArrayList<>();
        File[] fileAry = file.listFiles();
        for(File childFile : fileAry){
            if(childFile.isDirectory()){
                fileList.addAll(listFile(childFile));
            }else{
                fileList.add(childFile);
            }
        }
        return fileList;
    }
    public static String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if(!file.exists()){
            LogUtil.println("not exsits "+path);
            System.exit(0);
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        if(templateStr==null || templateStr.toString().length()==0){
            LogUtil.println("file is empty: "+path);
            System.exit(0);
        }
//        LogUtil.println(templateStr.toString());
        return templateStr.toString();
    }
    public static String readFile2Str(File file) throws IOException {

        if(!file.exists()){
            LogUtil.println("not exsits "+file);
            System.exit(0);
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        if(templateStr==null || templateStr.toString().length()==0){
            LogUtil.println("file is empty: "+file);
            System.exit(0);
        }
//        LogUtil.println(templateStr.toString());
        return templateStr.toString();
    }
    public static void writeFile(File file ,String content) throws IOException {
        try {

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file, false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void main(String args[]){
try {
    FileReader fr = new FileReader("G://V2.2.7.txt");
    BufferedReader br = new BufferedReader(fr);
    String s;
    String name="";
    StringBuffer result =new StringBuffer();
    while ((s = br.readLine()) != null) {
       // System.out.println(s);
        //System.out.println(s.split("\\s+").length);
        String arr[] = s.split("\\s+");
        System.out.println(arr[0]);
        if(name.equals("")){
             name=arr[arr.length-1];
        }else if(arr[arr.length-1].trim().equals(name.trim())){

            result.append("'").append(arr[0]).append("',");

        }else{

           // System.out.println(name+"select distinct  IPAddress from wii_device_ssid where deviceid in(select id from wii_device where DevId in ("+result.toString()+"))");
            result=new StringBuffer();
            name=arr[arr.length-1];
        }
        if(arr.length==3){

        }

    }
   // System.out.println(name+"select distinct  IPAddress from wii_device_ssid where id in(select id from wii_device where DevId in ("+result.toString()+")");
    fr.close();
}catch(Exception e){
    e.printStackTrace();
}
    }

    public static List<String > readFile2List(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();
        if(!file.exists()){
            LogUtil.println("read file failed path:"+path);
        }
        LogUtil.println("read file from1 path:"+path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
      //  BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines =new ArrayList();
        String s ;
        // StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            lines.add(s);
            // templateStr.append(s + "\r\n");
        }
        return lines;
    }
}
