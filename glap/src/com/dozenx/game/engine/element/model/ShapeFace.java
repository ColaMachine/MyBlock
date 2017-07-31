package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.math.Vector3fUtil;

import javax.vecmath.Vector3f;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/5/18.
 */
public class ShapeFace {

    /*public List<Vector3f> vertices;
    public List<Vector3f> normals;
    public List<Vector3f> texcoords;
    public List<Integer> faces;*/
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[][] vertices;

    public float[][] getVertices() {
        return vertices;
    }

    public void setVertices(float[][] vertices) {
        this.vertices = vertices;
    }

    public float[][] getNormals() {
        return normals;
    }

    public void setNormals(float[][] normals) {
        this.normals = normals;
    }

    public float[][] getTexcoords() {
        return texcoords;
    }

    public void setTexcoords(float[][] texcoords) {
        this.texcoords = texcoords;
    }

    public int[][] getFaces() {
        return faces;
    }

    public void setFaces(int[][] faces) {
        this.faces = faces;
    }

    public boolean isFullSide() {
        return fullSide;
    }

    public void setFullSide(boolean fullSide) {
        this.fullSide = fullSide;
    }

    public float[][] normals;
    public float[][] texcoords;
    public  int[][] faces;
    public  boolean fullSide;
   // List<Integer> faces;
}
