

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Configuration {
	
	private static String xPath="E:\\¿¼ÇÚ²âÊÔ\\conf.txt";
	private static ArrayList<String> Dos;
	private static boolean terminated=true;
	public static void init(){
		Dos=new ArrayList<String>();
		try{
			String input;
			BufferedReader in =new BufferedReader(new FileReader(xPath));
			while((input=in.readLine())!=null){
				Dos.add(input);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String getXPath() {
		return xPath;
	}
	public static void setXPath(String path) {
		xPath = path;
	}
	public static ArrayList<String> getDos() {
		return Dos;
	}
	public static void setDos(ArrayList<String> dos) {
		Dos = dos;
	}
	public static boolean isTerminated() {
		return terminated;
	}
	public static void setTerminated(boolean terminated) {
		Configuration.terminated = terminated;
	}
}
