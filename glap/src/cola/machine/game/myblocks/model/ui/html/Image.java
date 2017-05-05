package cola.machine.game.myblocks.model.ui.html;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;

/**
 * Created by luying on 16/12/21.
 */
public class Image {
    private String name;
    private TextureInfo texture;
    public String getName() {
        return name;
    }
    public Image(TextureInfo texture){
        this.texture=texture;

    }
    public void setName(String name) {
        this.name = name;
    }
    public TextureInfo getTexture(){
        return texture;
    }


}
