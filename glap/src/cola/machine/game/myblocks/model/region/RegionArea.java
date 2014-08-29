package cola.machine.game.myblocks.model.region;

/**
 * Created by luying on 14-8-28.
 */
public class RegionArea {
   public float minX = 0;
    public float minY = 0;
    public float maxX = 0;
    public float maxY = 0;
    public float width=0;
    public float height=0;
    public RegionArea(){

    }

    public float getWidth(){
        return maxX-minX;
        //return width;
    }
    public float getHeight(){
        return maxY-minY;
        //return height;
    }
    public RegionArea(float minX,float minY,float maxX,float maxY){
        this.minX=minX;
        this.minY=minY;
        this.maxX = maxX;
        this.maxY = maxY;

    }
    public void withWH(float minX,float minY,float width,float height){
        this.minX=minX;
        this.minY=minY;
        this.maxX = minX+ width;
        this.maxY = minY+height;
    }
}
