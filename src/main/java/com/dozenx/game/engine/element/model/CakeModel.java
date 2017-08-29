package com.dozenx.game.engine.element.model;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.Role.model.Model;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.ImageUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 物品模型-饼状模型
 * Created by dozen.zhang on 2017/3/7.
 */
public class CakeModel implements Model {
    public TextureInfo getIcon() {
        return icon;
    }

    public CakeModel(TextureInfo ti){
        if(ti==null){
            LogUtil.err("ti can't be null" );
        }
        this.icon = ti;
        this.init();
    }
    IBlock[] blocks;
    public static HashMap<String,IBlock[]> map =new HashMap<>();
    TextureInfo icon;

    private void init() {
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
        List<IBlock> list = new ArrayList<IBlock>();
        /*if(this.name.equals("wood_sword")){
            LogUtil.println("hello");
        }*/
        HashMap<Integer ,IBlock> blockMap =new HashMap<Integer,IBlock>();
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

                        IBlock soil = new ColorBlock(0, i, _height-j, color);
                        list.add(soil);

                        blockMap.put(i * _height + _height-j, soil);

                    }
                }

            for (int i = 0; i < _width; i++)
                for (int j = 0; j < _height; j++) {
                    IBlock block = blockMap.get(i * _height + j);
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
            blocks = list.toArray(new IBlock[list.size()]);
            map.put(this.icon.imageName,blocks);
        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Component getRootComponent() {
        return null;
    }

    public void build(ShaderConfig config , GL_Matrix rotateMatrix){
        //rotateMatrix= GL_Matrix.rotateMatrix( 0, 0, rotateZ);
        /*rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( -90*3.14f/180, 0, 0));
            rotateMatrix=GL_Matrix.multiply(rotateMatrix,GL_Matrix.rotateMatrix( 0, 0, -45*3.14f/180));
*/
        if(blocks==null){
            this.init();
        }

        for (IBlock block : blocks) {
            //  GL11.glColor3f(block.rf(), block.bf() , block.gf());
            ColorBlock colorBlock = (ColorBlock) block;
            colorBlock.renderShader(config,config.getVao(),rotateMatrix);
        }
    }
    public void build(ShaderConfig config, Vao vao , int x, int y, int z){

    }
}
