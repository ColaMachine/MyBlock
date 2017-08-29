package com.dozenx.game.opengl.util;

/**
 * Created by luying on 16/11/28.
 */
public class Fbo {
    int fboId;
    int textureId;

    public int getFboId() {
        return fboId;
    }

    public void setFboId(int fboId) {
        this.fboId = fboId;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
}
