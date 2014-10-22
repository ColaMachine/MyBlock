package cola.machine.game.myblocks.resource;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luying on 14/10/21.
 */
public class ResourceManager {
	
    public static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    private Map<String,ResourceSource> resourceSources=new HashMap();
    
    
    private HashMap <ResourceType,List<ResourceUri> > typeResrouceMap=new HashMap<ResourceType,List<ResourceUri>>();
    private HashMap<ResourceType,HashBasedTable<String,String,ResourceUri>> uriLookup=new HashMap<ResourceType,HashBasedTable<String,String,ResourceUri>>();

    public ResourceManager(){
        for(ResourceType type: ResourceType.values()){
            uriLookup.put(type, HashBasedTable.<String,String,ResourceUri>create());
        }
        //first search the main path
        setEnvironment();
    }

    
    public void setEnvironment(){
    	
       Path path= PathManager.getInstance().getHomePath();
       resourceSources.clear();
       List<ResourceSource> sources =new ArrayList();
       sources.add(createAssetSource("core", path));//soures means directory or archive sou
      
      ResourceSource source =new ResourceSourceCollection("core",sources);
      resourceSources.put(source.getSourceId(), source);
      for (ResourceUri asset : source.list()) {
    	  uriLookup.get(asset.getResourceType()).put(asset.getResourceName(), asset.getModuleName(), asset);
	  }
       
    }
    
    
    public ResourceSource createAssetSource(String id,Path path){
    	if(Files.isRegularFile(path)){
    		return null;
    	}else{
    		return new DirectorySource(id,path.resolve(Constants.ASSETS_SUBDIRECTORY));
    	}
    }
}
