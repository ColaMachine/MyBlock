package cola.machine.game.myblocks.resource;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class ResourceSourceCollection implements ResourceSource{
	private String sourceId;
	private Iterable<ResourceSource> resourceSources;
	public ResourceSourceCollection(String sourceId,Iterable<ResourceSource> resourceSources){
		this.sourceId=sourceId;
		this.resourceSources=resourceSources;
		
	}
	@Override
	public String getSourceId() {
		// TODO Auto-generated method stub
		return sourceId;
	}

	@Override
	public List<URL> get(ResourceUri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ResourceUri> list() {
		// TODO Auto-generated method stub
		List<ResourceUri> combinedList= Lists.newArrayList();
		for(ResourceSource resourceSource : resourceSources){
			for(ResourceUri resourceUri: resourceSource.list()){
				combinedList.add(resourceUri);
			}
			
		}
		return combinedList;
	}

	@Override
	public Iterable<ResourceUri> list(ResourceType type) {
		// TODO Auto-generated method stub
		return null;
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
	

}
