package cola.machine.game.myblocks.resource;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luying on 14/10/21.
 */
public class ResourceManager {

    public static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    private Map<String,ResourceSource> resourceSource=new HashMap();

    private HashMap <ResourceType,List<ResourceUri> > typeResrouceMap=new HashMap<ResourceType,List<ResourceUri>>();
    private HashMap<ResourceType,HashBasedTable<String,String,ResourceUri>> uriLookup=new HashMap<ResourceType,HashBasedTable<String,String,ResourceUri>>();

    public ResourceManager(){
        for(ResourceType type: ResourceType.values()){
            uriLookup.put(type, HashBasedTable.<String,String,ResourceUri>create());
        }
        //first search the main path
        setEnviroment();
    }

    public void setEnviroment(){
       Path path= PathManager.getInstance().getHomePath().resolve("assets");
        try(){

        }catch(Exception e){
            Lo

        }

    }
}
