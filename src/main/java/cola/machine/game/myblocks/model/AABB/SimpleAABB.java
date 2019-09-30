package cola.machine.game.myblocks.model.AABB;

import com.dozenx.util.MathUtil;

public class SimpleAABB {
    public void setAABB(float minX, float minY, float minZ,
                        float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    float posMinX,posMinY,posMinZ,posMaxX,posMaxY,posMaxZ;

   public void  setAABBSPosition(float posX,float posY,float posZ){
        this.posMinX = posX+minX;
       this.posMinY = posY+minY;
       this.posMinZ = posZ+minZ;


       this.posMaxX = posX+maxX;
       this.posMaxY = posY+maxY;
       this.posMaxZ = posZ+maxZ;
    }

    public float minX, minY, minZ, maxX, maxY, maxZ;
    //碰撞检测
    public boolean overlaps(SimpleAABB aabb2) {
//        return !(maxX < aabb2.minX || minX > aabb2.maxX)
//                && !(maxY < aabb2.minY || minY > aabb2.maxY)
//                && !(maxZ < aabb2.minZ || minZ > aabb2.maxZ);

        return MathUtil.testCubeXiangjiao(aabb2.minX, aabb2.minY, aabb2.minZ, aabb2.maxX - aabb2.minX, aabb2.maxY - aabb2.minY, aabb2.maxZ - aabb2.minZ,
                this.minX, this.minY
                , this.minZ, this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ
        );
    }

    public void reCaculate() {

    }





}
