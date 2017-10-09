package cola.machine.game.myblocks.model;

/**
 * Created by dozen.zhang on 2017/9/25.
 */
public interface RotateBlock {

    public float rotateX(float value);
    public float rotateY(float value);
    public float rotateZ(float value);

    public void setRotateX(float value);
    public void setRotateY(float value);
    public void setRotateZ(float value);

    public  float getRotateX();

    public float getRotateY();
    public float getRotateZ();

    public float getCenterX();
    public float getCenterY();
    public float getCenterZ();

    public void setCenterX(float value);
    public void setCenterY(float value);
    public void setCenterZ(float value);
//
//    public float getX();
//    public float getY();
//    public float  getZ();
}
