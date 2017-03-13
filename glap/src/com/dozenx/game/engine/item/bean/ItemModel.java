package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.util.ImageUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import org.lwjgl.opengl.GL11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 物品模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class ItemModel {
    public TextureInfo getIcon() {
        return icon;
    }

    public void setIcon(TextureInfo icon) {
        this.icon = icon;
    }

    Block[] blocks;
    public static HashMap<String,Block[]> map =new HashMap<>();
    TextureInfo icon;

    public void init() {
        if(blocks!=null){
            return;
        }
        this.blocks =  map.get(this.icon.name);
        if(blocks!=null){
            return;
        }
        map.put(this.icon.name,blocks);
        int height=  this.icon.img
                .h;
        int width=  this.icon.img
                .w;
        int minX = (int)(height*this.icon.minX);

        int maxX = (int)(height*this.icon.maxX);

        int minY=height-(int)(height*this.icon.maxY);
        int maxY= height-(int)(height*this.icon.minY);
        List<Block> list = new ArrayList<Block>();
        /*if(this.name.equals("wood_sword")){
            LogUtil.println("hello");
        }*/
        HashMap<Integer ,Block> blockMap =new HashMap<Integer,Block>();
        try {
            Color[][] colors = ImageUtil.getGrayPicture(icon.img.tmpi, minX,
                    minY, maxX, maxY);
            int _width = maxX-minX;
            int _height=maxY-minY;
            for (int i = 0; i < _width; i++)
                for (int j = 0; j < _height; j++) {
                    Color color = colors[i][j];
                    if (color != null)
                    {

                        Block soil = new ColorBlock(0, i, _height-j, color);
                        list.add(soil);

                        blockMap.put(i * _height + _height-j, soil);

                    }
                }

            for (int i = 0; i < _width; i++)
                for (int j = 0; j < _height; j++) {
                    Block block = blockMap.get(i * _height + j);
                    if(block!=null){
                        if(j!=_height && blockMap.get(i * _height + j+1)!=null){
                            block.setZh(false);
                        }
                        if(j!=0&& blockMap.get(i * _height + j-1)!=null){
                            block.setZl(false);
                        }
                        if(i!=_width && blockMap.get((i +1)* _height + j)!=null){
                            block.setYh(false);
                        }
                        if(i!=0 && blockMap.get((i -1)* _height + j)!=null){
                            block.setYl(false);
                        }
                        // block.setXh(false);
                        // block.setXl(false);
                    }

                }
            blocks = list.toArray(new Block[list.size()]);
            map.put(this.icon.imageName,blocks);
        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void renderShader(ShaderConfig config , GL_Matrix rotateMatrix){
        //rotateMatrix= GL_Matrix.rotateMatrix( 0, 0, rotateZ);
        /*rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( -90*3.14f/180, 0, 0));
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, 0, -45*3.14f/180));
*/
        if(blocks==null){
            this.init();
        }

        for (Block block : blocks) {
            //  GL11.glColor3f(block.rf(), block.bf() , block.gf());
            block.renderShader(config,rotateMatrix);
        }
    }
    public void render(){


        // 先缩小
        //GL11.glRotated(180, 0, 1, 0);

        //GL11.glTranslatef(x-2,y, z);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);//GL11.glTranslatef(2,0f,-4f);
        // GL11.glRotated(135, 1,0 , 0);
        //  GL11.glRotated(90, 0, 0, 1);

        GL11.glScalef(0.1f, 0.1f, 0.1f);
        //GL11.glTranslatef(-3.5f,-0.5f, -11f);
        for (Block block : blocks) {
            GL11.glColor3f(block.rf(), block.bf(), block.gf());
            block.render();
        }
        // GL11.glTranslatef(3.5f,0.5f,11f);
        GL11.glScalef(10f, 10f, 10f);

        // GL11.glRotated(-90, 0, 0, 1);
        //GL11.glRotated(-135, 1, 0, 0);//GL11.glTranslatef(-2f,0f,4f);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //GL11.glTranslatef(-x+2,-y, -z);
        //GL11.glRotated(-180, 0, 1, 0);
    }
}
