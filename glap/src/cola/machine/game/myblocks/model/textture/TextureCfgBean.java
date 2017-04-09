package cola.machine.game.myblocks.model.textture;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.region.RegionArea;
import glapp.GLImage;

public class TextureCfgBean{
String name;
    String xywh;
    String image;
    String splitx;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    String color;

    public String getSplitx() {
        return splitx;
    }

    public void setSplitx(String splitx) {
        this.splitx = splitx;
    }

    public String getSplity() {
        return splity;
    }

    public void setSplity(String splity) {
        this.splity = splity;
    }

    String splity;

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
