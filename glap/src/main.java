import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.Block;

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

        double x;
        double y;
        double z;
        double accu=0.01;
        for(x=0;x<=3;x+=accu){
            for(y=0;y<=3;y+=accu){
                for (z=0;z<=3;z+=accu){
                    if( Math.pow ((x*x+9/4*y*y-1),3)-x*x*z*z*z-9/80*y*y*z*z*z==0){

                    }


                }
            }
        }
	}
	
}
