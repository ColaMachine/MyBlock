package cola.machine.game.myblocks.resource;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;

public class AbstractSource implements ResourceSource {
	
	private static final Logger logger =LoggerFactory.getLogger(AbstractSource.class);
	
	private String sourceId;
	private SetMultimap<ResourceUri,URL> resources =HashMultimap.create();
	private SetMultimap<ResourceType, ResourceUri> resourcesByType = HashMultimap.create();
   // private SetMultimap<ResourceUri, URL> overrides = HashMultimap.create();
    //private SetMultimap<ResourceUri, URL> deltas = HashMultimap.create();
    
    public AbstractSource(String id){
    	sourceId =id;
    }
    
    
	@Override
	public String getSourceId() {
		return sourceId;
	}

	@Override
	public List<URL> get(ResourceUri uri) {
		
		return Lists.newArrayList(resources.get(uri));
	}

	@Override
	public Iterable<ResourceUri> list() {
		
		return resources.keySet();
	}

	@Override
	public Iterable<ResourceUri> list(ResourceType type) {
		return resourcesByType.get(type);
	}

	@Override
	public List<URL> getOverride(ResourceUri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ResourceUri> listOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<URL> getDelta(ResourceUri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	protected void clear(){
		resources.clear();
		resourcesByType.clear();
	}
	protected ResourceUri getUri(Path relativePath){
		return getUri(sourceId,relativePath);
	}
	protected ResourceUri getUri(String  moduleId,Path relativePath){
		if(relativePath.getNameCount()>1){
			Path resourcePath =relativePath.subpath(0,1);
			Path filename =relativePath.getFileName();
			String extension = com.google.common.io.Files.getFileExtension(filename.toString());
			String nameOnly = filename.toString().substring(0, filename.toString().length()-extension.length()-1);
			ResourceType resourceType=ResourceType.getTypeFor(resourcePath.toString(),extension);
			if(resourceType!=null){
				return new ResourceUri(resourceType,moduleId,nameOnly);
			}
		}
		return null;
	}
	
	protected void addItem(ResourceUri uri,URL url){
		resources.put(uri, url);
		resourcesByType.put(uri.getResourceType(), uri);
	}
}
