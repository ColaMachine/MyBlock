package cola.machine.game.myblocks.texture;

import cola.machine.game.myblocks.asset.AssetLoader;
import org.terasology.module.Module;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by luying on 14/10/21.
 */
public class TextureLoader implements AssetLoader<TextureData> {

    @Override
    public TextureData load(Module module, InputStream stream, List<URL> urls, List<URL> deltas) throws IOException {
        return null;
    }
}
