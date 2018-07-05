package cola.machine.game.myblocks.animation;

/**
 * Created by luying on 16/7/28.
 */
public class Transform {

    public Integer rotateX=0;
    public Integer rotateY=0;
    public Integer rotateZ=0;
    public Float translateX=0f;
    public Float translateY=0f;
    public Float translateZ=0f;

    public Float scaleX=1f;
    public Float scaleY=1f;
    public Float scaleZ=1f;

    public void clear(){
          rotateX=0;
          rotateY=0;
          rotateZ=0;
          translateX=0f;
          translateY=0f;
          translateZ=0f;

          scaleX=1f;
          scaleY=1f;
          scaleZ=1f;
    }

}
