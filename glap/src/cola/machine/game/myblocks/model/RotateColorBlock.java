package cola.machine.game.myblocks.model;

import cola.machine.game.myblocks.Color;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.world.chunks.Internal.ChunkImpl;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.ShapeFace;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FloatBufferWrap;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

public class RotateColorBlock extends ColorBlock{
    public RotateColorBlock(){

    }



    public   float rotateX;
    public float rotateY;
    public float rotateZ;


    GL_Vector[] points = BoxModel.getPoint(0,0,0);
    public RotateColorBlock(int x, int y, int z){
        this.x =x;
        this.y=y;
        this.z=z;


    }
    public RotateColorBlock(int x, int y, int z, float width, float height, float thick){
        this.x =x;
        this.y=y;
        this.z=z;
        this.width =width;
        this.height =height;
        this.thick =thick;

    }
    public RotateColorBlock(int x, int y, int z, float width, float height, float thick, float rf, float gf, float bf, float opacity){
        this.x =x;
        this.y=y;
        this.z=z;
        this.opacity = opacity;
        this.width =width;
        this.height =height;
        this.thick =thick;
        this.rf =rf;
        this.gf =gf;
        this.bf =bf;
    }



    public void update(){


        GL_Vector[] dirAry = BoxModel.dirAry;




        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(),x,y,z, this.points,dirAry,  rf, gf, bf,this.opacity);

    }

    public RotateColorBlock copy(){
        RotateColorBlock colorBlock  =new RotateColorBlock(this.x,this.y,this.z,this.width,this.height,this.thick,this.rf,this.gf,this.bf,this.opacity);

        return colorBlock;
    }
    public void rotateX(float value){
        this.rotateX+=value*0.1;reCompute();
    }
    public void rotateY(float value){
        this.rotateY+=value*0.1;
        reCompute();
    }
    public void rotateZ(float value){
        this.rotateZ+=value*0.1;
        reCompute();
    }

    public void reCompute(){
        points= BoxModel.getSmallPoint(0,0,0,width,height,thick);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(0,0,0);
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX,rotateY,rotateZ);
        for(int i=0;i<points.length;i++){
            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        }

    }
}
