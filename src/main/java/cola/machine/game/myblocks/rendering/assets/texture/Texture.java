package cola.machine.game.myblocks.rendering.assets.texture;

import cola.machine.game.myblocks.math.Rect2f;
import cola.machine.game.myblocks.texture.TextureData;

/**
 * Created by luying on 14/11/5.
 */
public interface Texture extends TextureRegionAsset<TextureData> {


    Rect2f FULL_TEXTURE_REGION = Rect2f.createFromMinAndSize(0, 0, 1, 1);

    public enum WrapMode {
        CLAMP,
        REPEAT
    }

    public enum FilterMode {
        NEAREST,
        LINEAR
    }

    WrapMode getWrapMode();

    FilterMode getFilterMode();

    // TODO: Remove when no longer needed
    TextureData getData();

    // TODO: This shouldn't be on texture
    int getId();

    public enum Type {
        TEXTURE2D,
        TEXTURE3D
    }
}
