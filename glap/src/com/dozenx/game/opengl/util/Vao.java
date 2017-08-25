package com.dozenx.game.opengl.util;

import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.util.ByteBufferWrap;
import com.dozenx.util.FloatBufferWrap;
import core.log.LogUtil;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by luying on 16/11/28.
 */
public class Vao {
    ShaderConfig config ;
    public Vao( ShaderConfig config){
        this.config = config;
     /*   if(config == null || config.getParamLenAry() ==null){
            LogUtil.err("dangerous");
        }*/
        // 为什么一开始就要渲染vao呢 chunkimpl是不是在加载完毕后要主动渲染一次vao
        if(config!=null)
            ShaderUtils.initVao(config,this);
       // this.name=name;
         Vertices =new FloatBufferWrap();// /*FloatBuffer.allocate(10240);*/BufferUtils.createFloatBuffer(102400);
    }
    public Vao(int bufferLength,ShaderConfig config ){
        // this.name=name;
        this(config);

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
    //  private List<Integer> vaoIdAry =new ArrayList<Integer>();
    private int eboId;
    private int vboId;
    private int points;
    private FloatBufferWrap Vertices =new FloatBufferWrap() ;
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

    public FloatBufferWrap getVertices() {
        return Vertices;
    }

    public void setVertices(FloatBufferWrap vertices) {
        Vertices = vertices;
    }

  /*  public void shrink(){
        int capacity = (int)(Vertices.capacity()*0.75);
        this.Vertices =BufferUtils.createFloatBuffer(capacity);
    }*/
   /* public  void expand(){
        int capacity = (int)(Vertices.capacity()*1.5);
         FloatBuffer floatBuffer =BufferUtils.createFloatBuffer(capacity);
        Vertices.flip();
        floatBuffer.put(Vertices);
        this.Vertices = floatBuffer;
    }*/
}
