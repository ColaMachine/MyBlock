package cola.machine.game.myblocks.item;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;

/**
 * Created by luying on 14-8-29.
 */
public class Item {
    public String name;
    String id;
    String type;
    String icon;
    String render;
    public  int count;
    public ItemInfo itemInfo;
    public TextureInfo textureInfo;
    int iconTextureHandle=0;
    int entityTextureHandle=0;
    int upsideTextureHandle=0;
    int sideTextureHandle=0;

    public Item(String name ,int count){
        this.name=name;
        this.textureInfo= TextureManager.getIcon(name);
        this.count=count;
    }
}
