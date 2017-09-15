package cola.machine.game.myblocks.engine;

import cola.machine.game.myblocks.naming.Name;
import com.google.common.base.Charsets;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.terasology.naming.Name;

/**
 * Created by luying on 14-10-5.
 */
public class Constants {

    public final static byte end=(byte)112;
    public static  int USER_ID=30;
    public static final int FPS=30;
    public static final int WINDOW_WIDTH=600;
    public static final int WINDOW_HEIGHT=600;
    public static final String ENTITY_DATA_FILE="entity.dat";
    public static final int DEFAULT_PORT=25777;//server port
    public static final String WORLD_DATA_FILE="world.dat";
    public static final String MAIN_WORLD="main";
    public static final int BAG_NUM_SLOTS_X=10;
    public static final int BAG_NUM_SLOTS_Y=5;
    public static final int BAG_CAPACITY=BAG_NUM_SLOTS_X*BAG_NUM_SLOTS_Y;
    public static final Charset CHARSET= Charsets.UTF_8;
    public static final Name ENGINE_MODULE=new Name("engine");
    public static final Name CORE_MODULE= new Name("core");
    public static final String ASSETS_SUBDIRECTORY="assets";
    public static final String OVERRIDES_SUBDIRECTORY="overrides";
    public static final String DELTAS_SUBDIRECTORY="overrides";
    public static final Path MODULE_INFO_FILENAME= Paths.get("module.txt");
    //public static final String CHARSET ="UTF-8";
    public static final int TOP =0;
    public static final int BOTTOM=1;
    public static final int FRONT =2;
    public static final int BACK=3;
    public static final int LEFT =4;
    public static final int RIGHT=5;
    
    public static final int CHUNK_HEIGHT=128;

    public static final int CHUNK_WIDTH=16;
    public static final int CHUNK_THICK=16;
    private Constants(){

    }


    /** item type **/
    public static final int  ICON_TYPE_WEAR =1<<0;
    public static final int ICON_TYPE_FOOD =1<<1;

    public static final int ICON_TYPE_SKILL =1<<2;
    public static final int SLOT_TYPE_ALL=0;

    public static final int SLOT_TYPE_COMMON=0;
    public static final int SLOT_TYPE_HEAD=1<<0;
    public static final int SLOT_TYPE_BODY=1<<1;
    public static final int SLOT_TYPE_LEG=1<<2;
    public static final int SLOT_TYPE_FOOT=1<<3;
    public static final int SLOT_TYPE_HAND=1<<4;
    public static final int SLOT_TYPE_SKILL=1<<5;

    public static final int WEAR_POSI_HEAD =1<<0;
    public static final int WEAR_POSI_BODY =1<<1;
    public static final int WEAR_POSI_LEG =1<<2;
    public static final int WEAR_POSI_FOOT =1<<3;
    public static final int WEAR_POSI_HAND =1<<4;



    public static byte[] borderColor=new byte[]{0,0,0};
    public static byte[] redColor=new byte[]{(byte)245,(byte)0,(byte)0};
    public static byte[] yellow=new byte[]{(byte)255,(byte)255,(byte)0};
    public static byte[] whiteColor=new byte[]{(byte)245,(byte)245,(byte)245};
    public static byte[] blue=new byte[]{(byte)0,(byte)0,(byte)250};

    public static final int SKILL_TYPE_DAMAGE=1;
    public static final int SKILL_TYPE_HEAL=2;
    public static final int SKILL_DAMAGE_TYPE_PHYSIC=1;
    public static final int SKILL_DAMAGE_TYPE_MAGIC=2;

    public static final int serverPort =6060;

    public static String userName;

    public static final int INVENTORY_SLOT_X_NUM=6;
    public static final int INVENTORY_SLOT_Y_NUM=6;

    public static float camSpeedR = 15; // degrees per second
    public static float camSpeedXZ = 4.4f; // units per second
    public static float camSpeedY = 0.1f; // units per second

    static final float PIdiv180 = (float)Math.PI/2;

    public static boolean SHADOW_ENABLE=false;
    public static boolean TRIABLE_ENABLE=false;
    public static boolean HDR_ENABLE=false;
    public static boolean GAOSI_ENABLE=false;
    
    public static boolean DELAY_ENABLE=true;

    public static boolean SSAO_ENABLE =true;
}
