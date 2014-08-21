import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class SqlBeautify {
	public void readFile02() throws IOException {
        FileInputStream fis=new FileInputStream("G:\\zhangzhiwei\\wuye\\查找基站基本信息.txt");
        InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        FileOutputStream fos=new FileOutputStream(new File("G:\\zhangzhiwei\\wuye\\查找基站基本信息1.txt"));
        OutputStreamWriter osw=new OutputStreamWriter(fos);
        BufferedWriter  bw=new BufferedWriter(osw);
        //简写如下
        //BufferedReader br = new BufferedReader(new InputStreamReader(
        //        new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
        String line="";
        String[] arrs=null;
        while ((line=br.readLine())!=null) {
           
            bw.write("sql.append(\"      "+line+"      \");\t\n");
        }
        br.close();
        isr.close();
        fis.close();
        bw.close();
        osw.close();
        fos.close();
    }
	  /**
     * 一行一行写入文件，解决写入中文字符时出现乱码
     * 
     * 流的关闭顺序：先打开的后关，后打开的先关，
     *       否则有可能出现java.io.IOException: Stream closed异常
     * 
     * @throws IOException 
     */
    public void writeFile02() throws IOException {
        String[] arrs={
                "zhangsan,23,福建",
                "lisi,30,上海",
                "wangwu,43,北京",
                "laolin,21,重庆",
                "ximenqing,67,贵州"
        };
        //写入中文字符时解决中文乱码问题
        FileOutputStream fos=new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt"));
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter  bw=new BufferedWriter(osw);
        //简写如下：
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
        //        new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));

        for(String arr:arrs){
            bw.write(arr+"\t\n");
        }
        
        //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
    }
	public static void main(String args[]){
		try{new SqlBeautify().
			readFile02();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
