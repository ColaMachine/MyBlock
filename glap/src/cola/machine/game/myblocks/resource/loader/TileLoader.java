package cola.machine.game.myblocks.resource.loader;

import cola.machine.game.myblocks.asset.AssetLoader;
import cola.machine.game.myblocks.asset.AssetType;
import glapp.GLApp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.math.IntMath;

import cola.machine.game.myblocks.resource.ResourceLoader;
import cola.machine.game.myblocks.resource.ResourceType;
import cola.machine.game.myblocks.resource.data.TileData;
import org.terasology.module.Module;

public class TileLoader implements AssetLoader<TileData> {
	private static final Logger logger =LoggerFactory.getLogger(TileLoader.class);

    public TileLoader() {
    }
    @Override
    public TileData load(Module module, InputStream stream, List<URL> urls, List<URL> deltas) throws IOException {
        BufferedImage image = ImageIO.read(stream);
        if (!IntMath.isPowerOfTwo(image.getHeight()) || !(image.getWidth() == image.getHeight())) {
            throw new IOException("Invalid tile - must be square with power-of-two sides");
        }
        return new TileData(image);
    }

}
