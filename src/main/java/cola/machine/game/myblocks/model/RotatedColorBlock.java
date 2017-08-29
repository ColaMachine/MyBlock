package cola.machine.game.myblocks.model;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
public class RotatedColorBlock extends ColorBlock {
    public float rotateX;
    public float rotateY;
    public float rotateZ;
    GL_Vector[] points = BoxModel.getPoint(0, 0, 0);


    public RotatedColorBlock() {
    }

    public RotatedColorBlock(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RotatedColorBlock(float x, float y, float z, float width, float height, float thick) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.thick = thick;
    }

    public RotatedColorBlock(float x, float y, float z, float width, float height, float thick, float rf, float gf, float bf, float opacity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.opacity = opacity;
        this.width = width;
        this.height = height;
        this.thick = thick;
        this.rf = rf;
        this.gf = gf;
        this.bf = bf;
    }


    public void update() {
        GL_Vector[] dirAry = BoxModel.dirAry;
        ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig, ShaderManager.anotherShaderConfig.getVao(), x, y, z, this.points, dirAry, rf, gf, bf, this.opacity);
    }

    public RotatedColorBlock copy() {
        RotatedColorBlock colorBlock = new RotatedColorBlock(this.x, this.y, this.z, this.width, this.height, this.thick, this.rf, this.gf, this.bf, this.opacity);
        return colorBlock;
    }

    public void rotateX(float value) {
        this.rotateX += value * 0.1;
        reCompute();
    }

    public void rotateY(float value) {
        this.rotateY += value * 0.1;
        reCompute();
    }

    public void rotateZ(float value) {
        this.rotateZ += value * 0.1;
        reCompute();
    }

    public void reCompute() {
        points = BoxModel.getSmallPoint(0, 0, 0, width, height, thick);
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(0, 0, 0);
        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(rotateX, rotateY, rotateZ);
        for (int i = 0; i < points.length; i++) {
            points[i] = rotateMatrix.multiply(rotateMatrix, points[i]);
        }

    }
}
