package cola.machine.game.myblocks.log;

/**
 * Created by luying on 16/7/24.
 */
public class LogUtil {
    public static void println(String s){
        Throwable e =new Throwable();
        StackTraceElement[] eles = e.getStackTrace();
        System.out.println(eles[1]+":"+s);
    }

    public static void err(String s){
        println(s);
        System.exit(0);
    }

}
