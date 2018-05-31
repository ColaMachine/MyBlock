package cola.machine.game.myblocks.block;

import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.ByteUtil;

/**
 * Created by luying on 18/4/9.
 */
public class BlockParseUtil {

    public static int getId( int value){
        return value & ByteUtil.HEX_0_0_1_1;
    }
    /**
     * //朝向 0~7位是id 8~9 两位是朝向 00东 01南 10西 11北
     * @param value
     * @return
     */
    public static int getDirection(int value){
        return ByteUtil.get9_8Value(value);
    }

    /**
     * 10 是上下 0是下 1是上(门用不到)
     * @param value
     * @return
     */
    public static int isTop(int value){
        return  value>>10 &1; //是否是上面部分
    }

    /**
     * 11位是open 状态位 0 是关 1是开
     * @param value
     * @return
     */
    public static int isOpen(int value){
        return  value>>11 &1; //是否是上面部分
    }


    public static int getValue(int direction,int id,int top,int open){
         return  id | (direction<<8)  | (top <<10) | (open <<11)  ;
    }

    public static void main(String args[]){
        int open =0 ;
        int top=1;
        int direction =2 ;
        int id = ItemType.wood_door.id;
        int value = new BlockParseUtil().getValue(direction,id,top,open );
        System.out.println(ByteUtil.toBinaryStr(id));
        System.out.println(ByteUtil.toBinaryStr(value));
        System.out.println(ByteUtil.toBinaryStr(2065>>10));
        System.out.println("value :"+value );
        System.out.println("id:"+getId(value ));
        System.out.println("direction:"+getDirection(value ));
        System.out.println("open:"+isOpen(value ));
        System.out.println("top:"+isTop(value ));


    }
}
