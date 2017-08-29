package cola.machine.game.myblocks.model.textture;

import glapp.GLImage;

/**
 * Created by luying on 16/8/31.
 */
public class ImageInfo {
    String name;
    String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public GLImage getImg() {
        return img;
    }

    public void setImg(GLImage img) {
        this.img = img;
    }

    GLImage img;
}
