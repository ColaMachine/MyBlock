package cola.machine.game.myblocks.physic;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import com.dozenx.util.MathUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/5/29.
 */
public class BulletResultDTO {

    //碰到的点 如果在最大表面需要减0.001
    public GL_Vector absoluteTargetPoint;//相对于chunk的位置
    //摆放的位置
    public GL_Vector absolutePlacePoint;//相对于chunk的位置
    //碰到的点 如果在最大表面需要减0.001
    public GL_Vector relativeTargetPoint;//相对于chunk的位置
    //摆放的位置
    private GL_Vector relativePlacePoint;//相对于chunk的位置
    //碰到的物体
    public BaseBlock targetBlock;//相对于chunk的位置 里面的xyz 都是相对位置
    //所在chunkX
    public int targetChunX;//所在chunk的大块位置
    //所在chunkZ
    public int targetChunZ;//所在chunk的大块位置

    private int placeChunkX;

    public void init(float touchPointX,float touchPointY,float touchPointZ,
                     float placePointX,float placePointY,float placePointZ,BaseBlock baseBlock){
        this.absoluteTargetPoint = new GL_Vector(touchPointX,touchPointY,touchPointZ);
        this.absolutePlacePoint = new GL_Vector(placePointX,placePointY,placePointZ);

        targetChunX = MathUtil.getBelongChunkInt(absoluteTargetPoint.x);
        targetChunZ=MathUtil.getBelongChunkInt(absoluteTargetPoint.z);
        relativeTargetPoint = new GL_Vector(MathUtil.getBelongChunkInt(absoluteTargetPoint.x),absoluteTargetPoint.y,
                MathUtil.getBelongChunkInt(absoluteTargetPoint.z) );
        relativePlacePoint = new GL_Vector(MathUtil.getBelongChunkInt(absolutePlacePoint.x),absolutePlacePoint.y,
                MathUtil.getBelongChunkInt(absolutePlacePoint.z) );

        placeChunkX= MathUtil.getBelongChunkInt(absolutePlacePoint.x);
        placeChunkZ= MathUtil.getBelongChunkInt(absolutePlacePoint.z);
        targetBlock=baseBlock.copy();
        targetBlock.chunkX=targetChunX;
        targetBlock.chunkZ=targetChunZ;
        targetBlock.x=MathUtil.getOffesetChunk(absoluteTargetPoint.x);
        targetBlock.z=MathUtil.getOffesetChunk(absoluteTargetPoint.z);
    }

    public int placeChunkZ;
}
