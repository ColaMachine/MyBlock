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
    private Constants(){

    }


    /** item type **/
    public static final int  ITEM_TYPE_WEAR =1;
    public static final int ITEM_TYPE_FOOD =2;


    public static final int WEAR_POSI_HEAD =1;
    public static final int WEAR_POSI_BODY =2;
    public static final int WEAR_POSI_LEG =3;
    public static final int WEAR_POSI_FOOT =4;

}
