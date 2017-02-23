package core.log;

/**
 * Created by luying on 16/7/24.
 */
public class LogUtil {
    public static void println(String s){
        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(System.currentTimeMillis()+""+eles[1]+":"+s);
    }

    public static void println(String s, Object ... objectes){
        String b= String.format(s,objectes);
        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(System.currentTimeMillis()+""+eles[1]+":"+b);
    }
    public static void err(String s){
        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(System.currentTimeMillis()+""+eles[1]+":"+s);
       // System.exit(0);
    }
    public static void err(String s,Object[] objectes){
        String b= String.format(s,objectes);
        println(b);
        System.exit(0);
    }


    public static void err(Exception  e){
       // println(s);
        e.printStackTrace();
        System.exit(0);
    }

}
