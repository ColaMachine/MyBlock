package cola.machine.game.myblocks.resource;

import java.util.Map;





import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import cola.machine.game.myblocks.resource.loader.TileLoader;
import cola.machine.game.myblocks.texture.TextureLoader;

/**
 * Created by luying on 14/10/21.
 */
public enum ResourceType {
    //BLOCK_TILE("blocktile", "blockTiles", "png", new TileLoader()),
	BLOCK_DEFINITION("blockdef",  new String[]{"blocks"},  new String[]{"block"}, null),
	BLOCK_TILE("blocktile", new String[]{"blockTiles"}, new String[]{"png"}, new TileLoader()),
	TEXTURE("texture",new String[]{"textures","fonts"},new String[]{"png"},new TextureLoader());
    private static Map<String, ResourceType> typeIdLookup;
    private static Table<String, String, ResourceType> subDirLookup;
	static {
		typeIdLookup=Maps.newHashMap();
		subDirLookup=HashBasedTable.create();
		for(ResourceType type : ResourceType.values()){
			typeIdLookup.put(type.getTypeId(), type);
			for(String dir : type.getSubDir()){
				for(String extension: type.getExtensions()){
					subDirLookup.put(dir,extension,type);
				}
			}
		}
	}
    
	public String getTypeId() {
	return typeId;
}
	public String[] getSubDir() {
		return subDir;
	}
	public String[] getExtensions() {
		return extensions;
	}
	private String typeId;
    private String[] subDir;
    private String[] extensions;
    private ResourceType(String typeId,String[] subDir,String[] extensions,ResourceLoader resourceLoader){
        this.typeId=typeId;
        this.subDir=subDir;
        this.extensions=extensions;

    }
    
    public static  ResourceType getTypeFor(String dir,String extension){
    	return subDirLookup.get(dir, extension);
    	
    }

}
