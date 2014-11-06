package cola.machine.game.myblocks.resource.data;

import java.awt.image.BufferedImage;

import cola.machine.game.myblocks.asset.AssetData;
import cola.machine.game.myblocks.resource.ResourceData;

public class TileData implements AssetData {

    private BufferedImage image;

    public TileData(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

}
