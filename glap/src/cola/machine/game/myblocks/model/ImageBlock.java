package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

public class ImageBlock extends BaseBlock{

   public TextureInfo front;
    public   TextureInfo back;
    public TextureInfo left;
    public TextureInfo right;
    public TextureInfo top;
    public TextureInfo bottom;
    public ImageBlock(){

    }




    GL_Vector[] points = BoxModel.getPoint(0,0,0);

    public ImageBlock(String name, int i, boolean b) {
        super(name,i,b);
    }

    public void update(float x,float y,float z,float width,float height,float thick){


        GL_Vector[] dirAry = BoxModel.dirAry;

        if(top!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[1], points[2], points[6], points[5], dirAry[5], right);
        }


    }
    public void update(){


        GL_Vector[] dirAry = BoxModel.dirAry;

        if(top!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, points[4], points[5], points[6], points[7], dirAry[0], top);
        }

        if(bottom!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[3], points[2], points[1], points[0], dirAry[1], bottom);
        }

        if(front!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[0], points[1], points[5], points[4], dirAry[2], front);
        }

        if(back!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[2], points[3], points[7], points[6], dirAry[3], back);
        }

        if(left!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z,  points[3], points[0], points[4], points[7], dirAry[4], left);
        }
        if(right!=null) {
            ShaderUtils.draw3dImage(ShaderManager.terrainShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x,y,z, points[1], points[2], points[6], points[5], dirAry[5], right);
        }


    }

    public ImageBlock copy(){
        ImageBlock colorBlock  =new ImageBlock();
        colorBlock.width=width;
        colorBlock.height=height;
        colorBlock.thick =thick;
        colorBlock.top=top;
        colorBlock.bottom=bottom;
        colorBlock.front=front;
        colorBlock.back=back;
        colorBlock.left=left;
        colorBlock.right=right;

        return colorBlock;
    }

    public String toString(){


        StringBuffer buffer =new StringBuffer();
        buffer.append("{").append("name:'").append(this.name).append("',")
                .append("blocktype:'imageblock',")
                .append("width:").append(this.width).append(",")
                .append("height:").append(this.height).append(",")
                .append("thick:").append(this.thick).append(",")
                .append("x:").append(this.x).append(",")
                .append("y:").append(this.y).append(",")
                .append("z:").append(this.z).append(",")
                .append("front:'").append(this.front.name).append("',")
                .append("back:'").append(this.back.name).append("',")
                .append("bottom:'").append(this.bottom.name).append("',")
                .append("top:'").append(this.top.name).append("',")
                .append("left:'").append(this.left.name).append("',")
                .append("right:'").append(this.right.name).append("',")
                .append("}");

        StringBuffer sb =new StringBuffer();

        sb.append(x).append(",").append(y).append(",").append(z).append(",")
                .append(width).append(",").append(height).append(",").append(thick).append(",");


        sb.append(this.x).append(",").append(this.y).append(",").append(this.z).append(",")
                .append(this.width).append(",").append(this.height).append(",").append(this.thick).append(",")
                .append(0).append(",").append(0).append(",").append(0)
                .append(",").append(0)
                .append(",") .append(this.top==null?" ":this.top.name)
                .append(",") .append(this.bottom==null?" ":this.bottom.name)
                .append(",") .append(this.front==null?" ":this.front.name)
                .append(",") .append(this.back==null?" ":this.back.name)
                .append(",") .append(this.left==null?" ":this.left.name)
                .append(",").append(this.right==null?" ":this.right.name);

        return sb.toString();


    }

    @Override
    public IBlock clone() {
        return null;
    }
}
