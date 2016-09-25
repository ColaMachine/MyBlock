package cola.machine.game.myblocks.model.textture;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.ColorBlock;
import com.dozenx.util.BinaryUtil;
import org.lwjgl.opengl.GL11;
import util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemDefinition {


    String name;
    TextureInfo icon;
    int type;
    String remark;
    int spirit;
    int agile;
    int intelli;
    int strenth;
    Shape shape;

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public TextureInfo getIcon() {
        return icon;
    }

    public void setIcon(TextureInfo icon) {
        this.icon = icon;
    }

    int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getAgile() {
        return agile;
    }

    public void setAgile(int agile) {
        this.agile = agile;
    }

    public int getIntelli() {
        return intelli;
    }

    public void setIntelli(int intelli) {
        this.intelli = intelli;
    }

    public int getStrenth() {
        return strenth;
    }

    public void setStrenth(int strenth) {
        this.strenth = strenth;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void init1(){
        List<Block> list = new ArrayList<Block>();
        try {

          int height=  this.icon.img
                  .h;
            int width=  this.icon.img
                    .w;
            int minX = (int)(height*this.icon.minX);

            int maxX = (int)(height*this.icon.maxX);
            int minY=height-(int)(height*this.icon.maxY);
            int maxY= height-(int)(height*this.icon.minY);
           /* int minY=height-(int)(height*this.icon.maxY);
            int maxY= height-(int)(height*this.icon.minY);*/
            int[] pix = this.icon.img.getPixelInts();
            int length = pix.length;
            for (int i = minX; i < maxX; i++)
                for (int j = minY; j < maxY; j++) {
                    int value = pix[i*height+width];
                    int a = value>>24&255;

                    int r = value<<8>>24;
                    int g = value<<16>>24;
                    int b = value&255;
                    if(value!=-14606047&& value!=0){
                        Color color = new Color(r,g,b);
                        Block soil = new ColorBlock(i-minX, 0, j-minY, color);
                        list.add(soil);
                    }


                }
            blocks = list.toArray(new Block[list.size()]);

        } catch (Exception e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void init() {
        int height=  this.icon.img
                .h;
        int width=  this.icon.img
                .w;
        int minX = (int)(height*this.icon.minX);

        int maxX = (int)(height*this.icon.maxX);

        int minY=height-(int)(height*this.icon.maxY);
        int maxY= height-(int)(height*this.icon.minY);
        List<Block> list = new ArrayList<Block>();
        if(this.name.equals("wood_sword")){
            LogUtil.println("hello");
        }
        HashMap<Integer ,Block> blockMap =new HashMap<Integer,Block>();
        try {
            Color[][] colors = ImageUtil.getGrayPicture(this.getIcon().img.tmpi, minX,
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

        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void init3 () {
        int height=  this.icon.img
                .h;
        int width=  this.icon.img
                .w;
        int minX = (int)(height*this.icon.minX);

        int maxX = (int)(height*this.icon.maxX);

        int minY=height-(int)(height*this.icon.maxY);
        int maxY= height-(int)(height*this.icon.minY);
        List<Block> list = new ArrayList<Block>();
        if(this.name.equals("wood_sword")){
            LogUtil.println("hello");
        }
        try {
            Color[][] colors = ImageUtil.getGrayPicture(this.getIcon().img.uri.getPath().toString(), minX,
                    minY, maxX, maxY);
            for (int i = 0; i < 16; i++)
                for (int j = 0; j < 16; j++) {
                    Color color = colors[i][j];
                    if (color != null)
                    {

                        Block soil = new ColorBlock(i, 0, j, color);
                        list.add(soil);
                    }
                }
            blocks = list.toArray(new Block[list.size()]);

        } catch (FileNotFoundException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // VIP Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        int value = 111111111;
        //BinaryUtil.toString(1);
        //BinaryUtil.toString(1<<1);
        //BinaryUtil.toString(1<<30);
        //BinaryUtil.toString(1<<30>>30);

        BinaryUtil.toString(value);
        int a = value>>24;
        BinaryUtil.toString(a);
        int r = (value<<8)>>24&255;
        System.out.println(r);
        BinaryUtil.toString(r);
        int g = (value<<16)>>24&255;
        System.out.println(g);
        BinaryUtil.toString(g);
        int b = (value<<24)>>24&255;
        System.out.println(b);
        BinaryUtil.toString(b);

    }
    Block[] blocks;
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
            GL11.glColor3f(block.rf(), block.bf() , block.gf());
            block.render();
        }
       // GL11.glTranslatef(3.5f,0.5f,11f);
        GL11.glScalef(10f,10f, 10f);

      // GL11.glRotated(-90, 0, 0, 1);
        //GL11.glRotated(-135, 1, 0, 0);//GL11.glTranslatef(-2f,0f,4f);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //GL11.glTranslatef(-x+2,-y, -z);
        //GL11.glRotated(-180, 0, 1, 0);
    }
}
