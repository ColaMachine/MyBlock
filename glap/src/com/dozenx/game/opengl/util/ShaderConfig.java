package com.dozenx.game.opengl.util;

import glmodel.GL_Matrix;

import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;
import java.util.HashMap;

/**
 * Created by luying on 16/11/26.
 */
public class ShaderConfig {

    public int textureIndex;

    public int getDepthMapLoc() {
        return depthMapLoc;
    }

    public void setDepthMapLoc(int depthMapLoc) {
        this.depthMapLoc = depthMapLoc;
    }

    private int texture0Loc;
    private int texture1Loc;
    private int texture2Loc;
    private int texture3Loc;
    private int texture4Loc;

    private int texture5Loc;
    private int texture6Loc;
    private int texture7Loc;
    private int texture8Loc;
    private int depthMapLoc;

    public int[] getParamLenAry() {
        return paramLenAry;
    }

    public void setParamLenAry(int[] paramLenAry) {
        this.paramLenAry = paramLenAry;
    }

    private int[] paramLenAry;

    private int paramTotalLen;

    public int getParamTotalLen() {
        return paramTotalLen;
    }

    public void setParamTotalLen(int paramTotalLen) {
        this.paramTotalLen = paramTotalLen;
    }

    public int getTexture0Loc() {
        return texture0Loc;
    }

    public void setTexture0Loc(int texture0Loc) {
        this.texture0Loc = texture0Loc;
    }



    public int getTexture1Loc() {
        return texture1Loc;
    }

    public void setTexture1Loc(int texture1Loc) {
        this.texture1Loc = texture1Loc;
    }

    public int getTexture2Loc() {
        return texture2Loc;
    }

    public void setTexture2Loc(int texture2Loc) {
        this.texture2Loc = texture2Loc;
    }

    public int getTexture3Loc() {
        return texture3Loc;
    }

    public void setTexture3Loc(int texture3Loc) {
        this.texture3Loc = texture3Loc;
    }

    public int getTexture4Loc() {
        return texture4Loc;
    }

    public void setTexture4Loc(int texture4Loc) {
        this.texture4Loc = texture4Loc;
    }

    public int getTexture5Loc() {
        return texture5Loc;
    }

    public void setTexture5Loc(int texture5Loc) {
        this.texture5Loc = texture5Loc;
    }

    public int getTexture6Loc() {
        return texture6Loc;
    }

    public void setTexture6Loc(int texture6Loc) {
        this.texture6Loc = texture6Loc;
    }

    public int getTexture7Loc() {
        return texture7Loc;
    }

    public void setTexture7Loc(int texture7Loc) {
        this.texture7Loc = texture7Loc;
    }



    public int getTexture8Loc() {
        return texture8Loc;
    }

    public void setTexture8Loc(int texture8Loc) {
        this.texture8Loc = texture8Loc;
    }

    public ShaderConfig(){

    }
    public ShaderConfig(String name,String fragPath,String vertPath){
        this.name =name;
        this.fragPath=fragPath;
        this.vertPath = vertPath;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    HashMap<String,Vao> vaoMaps =new HashMap<String,Vao>();
    public void addVao(Vao vao ){
        vaoMaps.put(vao.getName(),vao);
    }
    public Vao getVao(String name){
       return  vaoMaps.get(name);
    }
    public Vao getVao() {
        return vao;
    }

    public void setVao(Vao vao) {
        this.vao = vao;
    }

    private Vao vao=new Vao();
private int viewPosLoc;

    public int getViewPosLoc() {
        return viewPosLoc;
    }

    public void setViewPosLoc(int viewPosLoc) {
        this.viewPosLoc = viewPosLoc;
    }
//    private float minX;
//    private float minY;
//    private float maxX;
//    private float maxY;

    private String vertPath;
    private int programId;

    public int getShadowLightViewLoc() {
        return shadowLightViewLoc;
    }

    public void setShadowLightViewLoc(int shadowLightViewLoc) {
        this.shadowLightViewLoc = shadowLightViewLoc;
    }

    private int lightPosLoc;

     private int shadowLightViewLoc;
    public int getLightPosLoc() {
        return lightPosLoc;
    }

    public void setLightPosLoc(int lightPosLoc) {
        this.lightPosLoc = lightPosLoc;
    }

    //    private int vboId;
//    private int vaoId;
//private int eboId ;
    //private FloatBuffer Vertices;
    private int projLoc;
    private int modelLoc;

    private int viewLoc;


    private int textureHanle;
    private GL_Matrix model;
    private GL_Matrix model2;

//
//    public float getMinX() {
//        return minX;
//    }
//
//    public void setMinX(float minX) {
//        this.minX = minX;
//    }
//
//    public float getMinY() {
//        return minY;
//    }
//
//    public void setMinY(float minY) {
//        this.minY = minY;
//    }
//
//    public float getMaxX() {
//        return maxX;
//    }
//
//    public void setMaxX(float maxX) {
//        this.maxX = maxX;
//    }
//
//    public float getMaxY() {
//        return maxY;
//    }
//
//    public void setMaxY(float maxY) {
//        this.maxY = maxY;
//    }

    public int getTextureHanle() {
        return textureHanle;
    }

   /* public void setTextureHanle(int textureHanle) {
        this.textureHanle = textureHanle;
    }*/


//
//    public int getEboId() {
//        return eboId;
//    }
//
//    public void setEboId(int eboId) {
//        this.eboId = eboId;
//    }

    public GL_Matrix getModel2() {
        return model2;
    }

    public void setModel2(GL_Matrix model2) {
        this.model2 = model2;
    }

    public GL_Matrix getModel() {
        return model;
    }

    public void setModel(GL_Matrix model) {
        this.model = model;
    }

    public GL_Matrix getProjection() {
        return projection;
    }

    public void setProjection(GL_Matrix projection) {
        this.projection = projection;
    }

    public GL_Matrix getView() {
        return view;
    }

    public void setView(GL_Matrix view) {
        this.view = view;
    }

    private GL_Matrix projection;
    private GL_Matrix view;
    private Vector3f position=new Vector3f(0,0,0);
//    private int points;

//    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }

    private  int obejctColorLoc;

    public int getObejctColorLoc() {
        return obejctColorLoc;
    }

    public void setObejctColorLoc(int obejctColorLoc) {
        this.obejctColorLoc = obejctColorLoc;
    }

    public int getLightColorLoc() {
        return lightColorLoc;
    }

    public void setLightColorLoc(int lightColorLoc) {
        this.lightColorLoc = lightColorLoc;
    }

    private  int lightColorLoc;
    public int getVertShaderId() {
        return vertShaderId;
    }

    public void setVertShaderId(int vertShaderId) {
        this.vertShaderId = vertShaderId;
    }

    public int getFragShaderId() {
        return fragShaderId;
    }

    public void setFragShaderId(int fragShaderId) {
        this.fragShaderId = fragShaderId;
    }

    private String fragPath;
    private int vertShaderId;
    private int fragShaderId;
    public String getFragPath() {
        return fragPath;
    }

    public void setFragPath(String fragPath) {
        this.fragPath = fragPath;
    }

    public String getVertPath() {
        return vertPath;
    }

    public void setVertPath(String vertPath) {
        this.vertPath = vertPath;
    }




    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

//    public int getVboId() {
//        return vboId;
//    }
//
//    public void setVboId(int vboId) {
//        this.vboId = vboId;
//    }
//
//    public int getVaoId() {
//        return vaoId;
//    }
//
//    public void setVaoId(int vaoId) {
//        this.vaoId = vaoId;
//    }
//
//    public FloatBuffer getVertices() {
//        return Vertices;
//    }
//
//    public void setVertices(FloatBuffer vertices) {
//        Vertices = vertices;
//    }

    public int getProjLoc() {
        return projLoc;
    }

    public void setProjLoc(int projLoc) {
        this.projLoc = projLoc;
    }

    public int getModelLoc() {
        return modelLoc;
    }

    public void setModelLoc(int modelLoc) {
        this.modelLoc = modelLoc;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public int getViewLoc() {
        return viewLoc;
    }

    public void setViewLoc(int viewLoc) {
        this.viewLoc = viewLoc;
    }

    public  HashMap<Integer, Integer> getSampleLocMap() {
        return sampleLocMap;
    }

    public  void setTextureIndexMap(HashMap<Integer, Integer> textureIndexMap) {
        textureIndexMap = textureIndexMap;
    }


    //glTexLoc === > uniform smaple loc
    public  HashMap<Integer,Integer> sampleLocMap=new HashMap();

}
