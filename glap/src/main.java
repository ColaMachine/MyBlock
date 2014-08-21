import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class main {
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
//		System.out.println(c.get(Calendar.MINUTE));
		
		String minute = ((Math.random()*15)+"").substring(0,2).replace(".", "");
		int _minute = Integer.parseInt(minute);
		System.out.println(_minute);
	}
	
}
