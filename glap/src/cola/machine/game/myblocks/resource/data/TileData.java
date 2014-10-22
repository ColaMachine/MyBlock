package cola.machine.game.myblocks.resource.data;

import java.awt.image.BufferedImage;

import cola.machine.game.myblocks.resource.ResourceData;

public class TileData implements ResourceData{

    private BufferedImage image;

    public TileData(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

}
