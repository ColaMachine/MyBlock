package cola.machine.game.myblocks.resource;

import cola.machine.game.myblocks.texture.TextureLoader;

/**
 * Created by luying on 14/10/21.
 */
public enum ResourceType {
    //BLOCK_TILE("blocktile", "blockTiles", "png", new TileLoader()),
    TEXTURE("texture",new String[]{"textures","fonts"},new String[]{"png"},new TextureLoader());

    private String id;
    private String[] subDir;
    private String[] extensions;
    private ResourceType(String id,String[] subDir,String[] extensions,ResourceLoader resourceLoader){
        this.id=id;
        this.subDir=subDir;
        this.extensions=extensions;

    }

}
