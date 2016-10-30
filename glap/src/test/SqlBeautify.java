package test;

import java.io.*;


public class SqlBeautify {
	public void readFile02() throws IOException {
        FileInputStream fis=new FileInputStream("G:\\zhangzhiwei\\wuye\\���һ�վ����Ϣ.txt");
        InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        FileOutputStream fos=new FileOutputStream(new File("G:\\zhangzhiwei\\wuye\\���һ�վ����Ϣ1.txt"));
        OutputStreamWriter osw=new OutputStreamWriter(fos);
        BufferedWriter  bw=new BufferedWriter(osw);
        //��д����
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
     * һ��һ��д���ļ������д�������ַ�ʱ��������
     * 
     * ���Ĺر�˳���ȴ򿪵ĺ�أ���򿪵��ȹأ�
     *       �����п��ܳ���java.io.IOException: Stream closed�쳣
     * 
     * @throws java.io.IOException
     */
    public void writeFile02() throws IOException {
        String[] arrs={
                "zhangsan,23,����",
                "lisi,30,�Ϻ�",
                "wangwu,43,����",
                "laolin,21,����",
                "ximenqing,67,����"
        };
        //д�������ַ�ʱ���������������
        FileOutputStream fos=new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt"));
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter  bw=new BufferedWriter(osw);
        //��д���£�
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
        //        new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));

        for(String arr:arrs){
            bw.write(arr+"\t\n");
        }
        
        //ע��رյ��Ⱥ�˳���ȴ򿪵ĺ�رգ���򿪵��ȹر�
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
