package cola.machine.game.myblocks.model.textture;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import glapp.GLImage;

public class TextureCfgBean{
String name;
    String xywh;
    String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXywh() {
        return xywh;
    }

    public void setXywh(String xywh) {
        this.xywh = xywh;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
