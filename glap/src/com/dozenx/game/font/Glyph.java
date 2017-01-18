package com.dozenx.game.font;

/**
 * Created by luying on 16/12/25.
 */
public class Glyph {
    public final int width;
    public final int height;
    public final int x;
    public final int y;

    public Glyph( int x, int y,int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
}
