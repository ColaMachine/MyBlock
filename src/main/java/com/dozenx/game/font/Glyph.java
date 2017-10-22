package com.dozenx.game.font;

/**
 * Created by luying on 16/12/25.
 */
public class Glyph {
    public final int width;
    public final int height;
    public final int x;
    public final int y;
    public final float advance;
    public Glyph( int x, int y,int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.advance = 1;
    }


    /**
     * Creates a font Glyph.
     *
     * @param width   Width of the Glyph
     * @param height  Height of the Glyph
     * @param x       X coordinate on the font texture
     * @param y       Y coordinate on the font texture
     * @param advance Advance width
     */
    public Glyph(int width, int height, int x, int y, float advance) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.advance = advance;
    }
}
