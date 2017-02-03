package com.dozenx.game.opengl.util;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by luying on 16/11/28.
 */
public class Vao {
    public Vao(){
       // this.name=name;
    }
    public Vao(String name ){
        this.name=name;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int vaoId;
    private int eboId;
    private int vboId;
    private int points;
    private FloatBuffer Vertices = /*FloatBuffer.allocate(10240);*/BufferUtils.createFloatBuffer(102400);
    public int getVaoId() {
        return vaoId;
    }

    public void setVaoId(int vaoId) {
        this.vaoId = vaoId;
    }

    public int getEboId() {
        return eboId;
    }

    public void setEboId(int eboId) {
        this.eboId = eboId;
    }

    public int getVboId() {
        return vboId;
    }

    public void setVboId(int vboId) {
        this.vboId = vboId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public FloatBuffer getVertices() {
        return Vertices;
    }

    public void setVertices(FloatBuffer vertices) {
        Vertices = vertices;
    }



}
