package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.model.base.BaseBlock;
import cola.machine.game.myblocks.model.textture.BoneBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FloatBufferWrap;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

/**
 * 物品模型-盒子模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class BoxModel implements Model {
    public TextureInfo front; //如果是盒状的话有四面 其实应该纳入什么模型具体对象中的
    public TextureInfo back;
    public TextureInfo top;
    public TextureInfo left;
    public TextureInfo right;
    public TextureInfo bottom;
    public GL_Vector P1;
    public GL_Vector P2;
    public  GL_Vector P3;
    public  GL_Vector P4;
    public GL_Vector P5;
    public GL_Vector P6;
    public GL_Vector P7;
    public GL_Vector P8;


    public static float[][] getFrontVertices(float minX,float minY,float minZ,float width,float height,float thick){
        return new float[][]{{minX,minY,minZ+thick},{minX+width,minY,minZ+thick},{minX+width,minY+height,minZ+thick},{minX,minY+height,minZ+thick}};
    }

    public static float[][] getBackVertices(float minX,float minY,float minZ,float width,float height,float thick){
        return new float[][]{{minX+width,minY,minZ},{minX,minY,minZ},{minX,minY+height,minZ},{minX+width,minY+height,minZ}};
    }

    public static float[][] getLeftVertices(float minX,float minY,float minZ,float width,float height,float thick){
        return new float[][]{{minX,minY,minZ},{minX,minY,minZ+thick},{minX,minY+height,minZ+thick},{minX,minY+height,minZ}};
    }
    public static float[][] getRightVertices(float minX,float minY,float minZ,float width,float height,float thick){
        return new float[][]{{minX+width,minY,minZ+thick},{minX+width,minY,minZ},{minX+width,minY+height,minZ},{minX+width,minY+height,minZ+thick}};
    }

    public static float[][] getTopVertices(float minX,float minY,float minZ,float width,float height,float thick){
        return new float[][]{{minX,minY+height,minZ+thick},{minX+width,minY+height,minZ+thick},{minX+width,minY+height,minZ},{minX,minY+height,minZ}};
    }
    public static float[][] getBottomVertices(float minX,float minY,float minZ,float width,float height,float thick){
        return new float[][]{{minX,minY,minZ},{minX+width,minY,minZ},{minX+width,minY,minZ+thick},{minX,minY,minZ+thick}};
    }

    public static float[][] frontVertices = new float[][]{{0,0,1f},{1,0,1f},{1,1,1f},{0,1,1f}};

    public static float[][] backVertices = new float[][]{{1,0,0f},{0,0,0f},{0,1,0f},{1,1,0f}};

    public static float[][] leftVertices = new float[][]{{0,0,0f},{0,0,1f},{0,1,1f},{0,1,0f}};


    public static float[][] rightVertices = new float[][]{{1,0,1f},{1,0,0f},{1,1,0f},{1,1,1f}};

    public static float[][] topVertices = new float[][]{{0,1,1f},{1,1,1f},{1,1,0f},{0,1,0f}};

    public static float[][] bottomVertices = new float[][]{{0,0,0f},{1,0,0f},{1,0,1f},{0,0,1f}};

    public static GL_Vector FRONT_DIR = new GL_Vector(0, 0, 1f);
    public static GL_Vector BACK_DIR = new GL_Vector(0, 0, -1f);
    public static GL_Vector TOP_DIR = new GL_Vector(0, 1, 0f);
    public static GL_Vector DOWN_DIR = new GL_Vector(0, -1, 0f);
    public static GL_Vector LEFT_DIR = new GL_Vector(-1, 0, 0f);
    public static GL_Vector RIGHT_DIR = new GL_Vector(1, 0, 0f);
    public static GL_Vector[] dirAry = new GL_Vector[]{TOP_DIR,DOWN_DIR,FRONT_DIR,BACK_DIR,LEFT_DIR,RIGHT_DIR};


    //7   6
    //4    5
      //3   2
    //0    1
    public static int[] frontFace=new int[]{0,1,5,4};
    public static int[] backFace=new int[]{2,3,7,6};
    public static int[] leftFace=new int[]{3,0,4,7};
    public static int[] rightFace=new int[]{1,2,6,5};
    public static int[] topFace=new int[]{4,5,6,7};
    public static int[] bottomFace=new int[]{3,2,1,0};

    public static int[][] facesAry = new int[][]{topFace,bottomFace,frontFace,backFace,leftFace,rightFace,};

    public static GL_Vector[] getPoint(int x,int y,int z){


        GL_Vector p1= new GL_Vector(x,y,z+1);//左前下
        GL_Vector p2= new GL_Vector(x+1,y,z+1);//右前下
        GL_Vector p3= new GL_Vector(x+1,y,z);
        GL_Vector p4= new GL_Vector(x,y,z);

        GL_Vector p5= new GL_Vector(x,y+1,z+1);
        GL_Vector p6= new GL_Vector(x+1,y+1,z+1);
        GL_Vector p7= new GL_Vector(x+1,y+1,z);
        GL_Vector p8= new GL_Vector(x,y+1,z);

        return new GL_Vector[]{p1,p2,p3,p4,p5,p6,p7,p8};
    }

    public static GL_Vector[] getPoint(float x,float y,float z){


        GL_Vector p1= new GL_Vector(x,y,z+1);
        GL_Vector p2= new GL_Vector(x+1,y,z+1);
        GL_Vector p3= new GL_Vector(x+1,y,z);
        GL_Vector p4= new GL_Vector(x,y,z);

        GL_Vector p5= new GL_Vector(x,y+1,z+1);
        GL_Vector p6= new GL_Vector(x+1,y+1,z+1);
        GL_Vector p7= new GL_Vector(x+1,y+1,z);
        GL_Vector p8= new GL_Vector(x,y+1,z);

        return new GL_Vector[]{p1,p2,p3,p4,p5,p6,p7,p8};
    }





    public static GL_Vector[] getSmallPoint(float x,float y,float z,float size){


        GL_Vector p1= new GL_Vector(x-size,y-size,z+size);
        GL_Vector p2= new GL_Vector(x+size,y-size,z+size);
        GL_Vector p3= new GL_Vector(x+size,y-size,z-size);
        GL_Vector p4= new GL_Vector(x-size,y-size,z-size);

        GL_Vector p5= new GL_Vector(x-size,y+size,z+size);
        GL_Vector p6= new GL_Vector(x+size,y+size,z+size);
        GL_Vector p7= new GL_Vector(x+size,y+size,z-size);
        GL_Vector p8= new GL_Vector(x-size,y+size,z-size);

        return new GL_Vector[]{p1,p2,p3,p4,p5,p6,p7,p8};
    }
    public static GL_Vector[] getSmallPoint(float x,float y,float z,float width,float height,float thick){


        GL_Vector p1= new GL_Vector(x,y,z+thick);
        GL_Vector p2= new GL_Vector(x+width,y,z+thick);
        GL_Vector p3= new GL_Vector(x+width,y,z);
        GL_Vector p4= new GL_Vector(x,y,z);

        GL_Vector p5= new GL_Vector(x,y+height,z+thick);
        GL_Vector p6= new GL_Vector(x+width,y+height,z+thick);
        GL_Vector p7= new GL_Vector(x+width,y+height,z);
        GL_Vector p8= new GL_Vector(x,y+height,z);

        return new GL_Vector[]{p1,p2,p3,p4,p5,p6,p7,p8};
    }

    /*public void setEightFace(String name,TextureManager textureManager){

        // this.name =name;
        this.front= textureManager.getTextureInfo(name+"_front");
        this.back= textureManager.getTextureInfo(name+"_back");
        this.left= textureManager.getTextureInfo(name+"_left");
        this.right= textureManager.getTextureInfo(name+"_right");
        this.top= textureManager.getTextureInfo(name+"_top");
        this.bottom= textureManager.getTextureInfo(name+"_bottom");
    }*/

    @Override
    public Component getRootComponent() {
        return null;
    }

    /**
     * 主要是人物渲染的时候要用到
     * @param config
     * @param rotateMatrix
     */
    public void build(ShaderConfig config , GL_Matrix rotateMatrix){
        FloatBufferWrap floatBuffer = config.getVao().getVertices();
        if(front!=null) {
            // ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P1,P2,P6,P5,new GL_Vector(0,0,1f),front);
            ShaderUtils.draw3dImage(config,config.getVao(),rotateMatrix,P1, P2, P6, P5,  FRONT_DIR, front );
        }
        if(back!=null) {
            //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
            ShaderUtils.draw3dImage(config,config.getVao(),rotateMatrix,P3,P4,P8,P7,BACK_DIR,back);
        }
        if(top!=null) {
            //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P5,P6,P7,P8,new GL_Vector(0,1,0f),front);
            ShaderUtils.draw3dImage(config,config.getVao(),rotateMatrix,P5,P6,P7,P8,TOP_DIR,top);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(config,config.getVao(),rotateMatrix,P4,P3,P2,P1,BACK_DIR,bottom);
        }
        if(left!=null) {
            ShaderUtils.draw3dImage(config,config.getVao(),rotateMatrix,P2,P3,P7,P6,LEFT_DIR,left);
        }
        if(right!=null) {
            ShaderUtils.draw3dImage(config,config.getVao(),rotateMatrix,P4,P1,P5,P8,RIGHT_DIR,right);
        }
    }

    public  BoxModel(BaseBlock shape){//这个逻辑是错误的 shape 是不应该有itemDefinition属性的
        if(shape == null){
            LogUtil.err(" shape can't be null");
        }
        float thick =shape.getThick();
        float width = shape.getWidth();
        float height= shape.getHeight();

        if(shape instanceof  BoneBlock) {
            BoneBlock boneBlock = (BoneBlock) shape;
            this.front = boneBlock.getFront();
            this.back = boneBlock.getBack();
            this.left = boneBlock.getLeft();
            this.right = boneBlock.getRight();
            this.top = boneBlock.getTop();
            this.bottom = boneBlock.getBottom();

        }
        this.P1= new GL_Vector(0,0,thick);
        this.P2= new GL_Vector(width,0,thick);
        this.P3= new GL_Vector(width,0,0);
        this.P4= new GL_Vector(0,0,0);
        this.P5= new GL_Vector(0,height,thick);
        this.P6= new GL_Vector(width,height,thick);
        this.P7= new GL_Vector(width,height,0);
        this.P8= new GL_Vector(0,height,0);
    }  public void build(ShaderConfig config, Vao vao , int x, int y, int z){

    }
}
