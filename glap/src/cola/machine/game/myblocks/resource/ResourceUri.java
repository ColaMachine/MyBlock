package cola.machine.game.myblocks.resource;

/**
 * Created by luying on 14/10/21.
 */
public class ResourceUri {
    ResourceType type;

    String moduleName;

    String resourceName;

    public ResourceUri(ResourceType type,String moduleName,String resourceName){
        this.type=type;
        this.moduleName=moduleName;
        this.resourceName=resourceName;
    }

	public ResourceType getResourceType() {
		return type;
	}

	public ResourceType getType() {
		return type;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getResourceName() {
		return resourceName;
	}
}
