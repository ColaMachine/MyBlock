package cola.machine.game.myblocks.physic;

import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import javax.vecmath.Vector3f;

public class BulletPhysics {

    //public BlockRepository blockRepository=null;

    public BulletPhysics(/*BlockRepository blockRepository*/) {
        //	this.blockRepository=blockRepository;

    }


    /**
     * 点击选择探测器
     *
     * @param from
     * @param direction
     * @param distance
     * @param blockname
     * @param delete
     * @return 3个Gl_vector 第一个是触碰到的block 位置 第二个是触碰路径上的最后一个空block位置 第三个第一个x分量是block的类型
     */
    //名字要改一下 改成选择方块
    public BulletResultDTO rayTrace(GL_Vector from, GL_Vector direction, float distance, String blockname, boolean delete) {
        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);
        GL_Vector to = new GL_Vector(direction);

        //������յ�λ��

        //ÿ���ƶ�0.1 init  variable
        int preBlockIndex = -1;
        int chunkIndex = -1;
        Integer preChunkIndex = null;  //if preChunkIndex != chunkIndex bianlichunk will to find it's new chunk and also use to find the place block
        int blockIndex = 0;
        Chunk bianliChunk = null;//need to be initialize
        int chunkX, preChunkX, chunkZ, preChunkZ;
        //int chunkY;
        float nowX, nowY, nowZ, preX, preY, preZ;
        nowX = nowY = nowZ = preX = preY = preZ = 0;
        int worldX, worldY, worldZ, preWorldX, preWorldY, preWorldZ;
        int offsetX, offsetZ, preOffsetX, preOffsetZ;
        // int preBlockIndex =0; every time push a little
        preX = from.x;
        preY = from.y;
        preZ = from.z;
        for (float x = 0.1f; x < distance; x += 0.1) {//一点一点推进 看撞到了哪个物体 every time add a little
            nowX = from.x + x * to.x;
            nowY = from.y + x * to.y;
            nowZ = from.z + x * to.z;


            worldX = MathUtil.floor(nowX);//x 代表推进的举例 推进后的x
            worldY = MathUtil.floor(nowY);//推进后的y
            if (worldY < 0) {
                BulletResultDTO arr = new BulletResultDTO();
                // arr.targetBlock= targetBlock;

                arr.absolutePlacePoint = new GL_Vector(preX,
                        preY,
                        preZ);//����ײ��ǰ�ķ���λ��
                return arr;//说明已经穿透地板了
            }
            worldZ = MathUtil.floor(nowZ);//推进后的z

            offsetX = MathUtil.getOffesetChunk(worldX);
            offsetZ = MathUtil.getOffesetChunk(worldZ);
            blockIndex = offsetX * 10000 + offsetZ * 100 + worldY;//推进后落入的blockINdex
            if (preBlockIndex == -1) {
                preBlockIndex = blockIndex;
            }
            chunkX = MathUtil.getBelongChunkInt(nowX);//换算出新的chunkX
            chunkZ = MathUtil.getBelongChunkInt(nowZ);//换算出新的chunkZ
            chunkIndex = chunkX * 10000 + chunkZ;
            if (blockIndex == preBlockIndex) {
                continue;
            }
            if (blockIndex != preBlockIndex)//如果和之前判断的还是同一个的话 略过
            {
                //pre
             /*   preBlockIndex = blockIndex;
                preChunkIndex = chunkIndex;
                preChunkX = chunkX;
                preChunkZ = chunkZ;
                preX=nowX;
                preY=nowY;
                preZ = nowZ;
                preOffsetX = offsetX;
                preOffsetZ = offsetZ;*/
                //int chunkY;


            }
            //ȥ����Ƿ���һ������֮��
            //ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(MathUtil.floor((float)_x/16),0,MathUtil.floor((float)_z/16)));
            if (preChunkIndex == null) {
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX, 0, chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发
                preChunkIndex = chunkIndex;
            }

            //once enther a new chunk block then make sure if crash a realblock
            if (chunkIndex != preChunkIndex) {//如果进入到新的chunk方格子空间
                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX, 0, chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发

            }
            // 得到当前chunk 改变的次数有限
            //

            //int chunk_pos_x =  MathUtil.floor(((float)_x)/16);
            //int chunk_pos_z =  MathUtil.floor(((float)_z)/16);
            //System.out.printf(" %d %d %d",_x,_y,_z);
            //先获取chunk 在获取block
            //blockRepository.haveObject(_x,_y,_z)

            //detect if there is block
            if (bianliChunk != null) {
                //Block targetBlock = bianliChunk.getBlock(offsetX,worldY,offsetZ);
                int blockType = bianliChunk.getBlockData(offsetX, worldY, offsetZ);
                if (blockType > 0) {
                    BulletResultDTO arr = new BulletResultDTO();
                    if (delete) {//如果是删除 就删除对应的方块
                        //Block block=new BaseBlock("water",0,false);
                        // bianliChunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z),block);
                        //((ChunkImpl)bianliChunk).build();
                        // ((ChunkImpl)bianliChunk).buildAlpha();
                        //bianliChunk.build();
                        //return null;
                        if (nowY < 0) {
                            LogUtil.err("y can't be <0 ");
                        }
                   /* arr[0] = new GL_Vector(MathUtil.floor(from.x+x*to.x),
                            MathUtil.floor(from.y+x*to.y),
                            MathUtil.floor(from.z+x*to.z));*/
                        //return ;
                    }
                    IBlock targetBlock = bianliChunk.getBlock(offsetX, worldY, offsetZ);
                    arr.targetChunX = chunkX;
                    arr.targetChunZ = chunkZ;
                    arr.relativeTargetPoint = new GL_Vector(offsetX,
                            MathUtil.floor(nowY),
                            offsetZ);
                    arr.targetBlock = (BaseBlock) targetBlock;
                    //退回来 如果是增加放置一个block的话

                    //BlockManager blockManager = CoreRegistry.get(BlockManager.class);//创建新的方块
                    //Block block=new BaseBlock("water",blockManager.getBlock(blockname).getId(),false);

                    //Chunk _chunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));
                    //_chunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor(_y), MathUtil.getOffesetChunk(_z), block);
               /* _chunk.setBlock(MathUtil.floor(from.x+x*to.x)%16,
                        MathUtil.floor(from.y+x*to.y),
    					MathUtil.floor(from.z+x*to.z)%16, block);*/
                    //_chunk.disposeMesh();
                    //_chunk.update();
                    //重新更新
                    //_chunk.build();
                    //_chunk.buildAlpha();
                    if (from.y + x * to.y < 0) {
                        LogUtil.err("y can't be <0 ");
                    }

                    //  LogUtil.println("x:"+(from.x+x*to.x)%1 + "y:"+(from.y+x*to.y)%1+"z:"+(from.z+x*to.z)%1);

                    arr.absolutePlacePoint= new GL_Vector(preX,
                            preY,
                            preZ);//����ײ��ǰ�ķ���λ��
                    //  arr[2] =new GL_Vector(blockType,0,0);
                    return arr;

                }// if has no crash a real block then prex prey prez use to fallback
                preBlockIndex = blockIndex;
                preChunkIndex = chunkIndex;
                preChunkX = chunkX;
                preChunkZ = chunkZ;
                preX = nowX;
                preY = nowY;
                preZ = nowZ;
                preOffsetX = offsetX;
                preOffsetZ = offsetZ;
            }
        }
        return null;

    }

    public static int[] getZhengShuSecList(float from, float to, int dir) {
//        int start = dir>0 ?(int) Math.ceil(from):(int)Math.floor(from);
//        int dest = dir>0 ?(int) Math.floor(to):(int)Math.ceil(to);
        int start = (int) Math.ceil(Math.min(from, to));
        int dest = (int) Math.floor(Math.max(from, to));
        if (start <= dest) {
            return new int[]{start, dest};
        } else {
            return null;
        }

    }

    public static IBlock process(float nowX, float nowY, float nowZ, ChunkProvider chunkProvider) {
        int chunkX, chunkZ, offsetX, offsetZ, worldX, worldY, worldZ;
        if(nowY<0){
            return null;
        }

        Chunk chunk;

        worldX = (int) Math.floor(nowX);
        worldY = (int) Math.floor(nowY);
        worldZ = (int) Math.floor(nowZ);

        chunkX = MathUtil.getBelongChunkInt(nowX);//换算出新的chunkX
        chunkZ = MathUtil.getBelongChunkInt(nowZ);//换算出新的chunkZ
        chunk = chunkProvider.getChunk(new Vector3i(chunkX, 0, chunkZ));
        if(chunk==null)
            return null;
        offsetX = MathUtil.getOffesetChunk(worldX);
        offsetZ = MathUtil.getOffesetChunk(worldZ);

        IBlock block = chunk.getBlock(offsetX, worldY, offsetZ);
        if(block!=null){
            block=block.copy();
            if(block==null){
                LogUtil.println("this doesn't work");
            }
            block.set(worldX,worldY,worldZ);
        }

        return block;
    }

    public static BulletResultDTO rayTrace2(GL_Vector from, GL_Vector direction, float distance, String blockname, boolean delete) {
        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);
        GL_Vector to = from.copyClone().add(direction.copyClone().normalize().mult(distance));
        //获取各个方向
        int xDir = to.x - from.x > 0 ? 1 : -1;
        int yDir = to.y - from.y > 0 ? 1 : -1;
        int zDir = to.z - from.z > 0 ? 1 : -1;
        //如果x的方向是负数的话就要x>to.x否则是x<to.x
        int xMinMax[] = getZhengShuSecList(from.x, to.x, xDir);
        int yMinMax[] = getZhengShuSecList(from.y, Math.max(0,to.y), xDir);
        int zMinMax[] = getZhengShuSecList(from.z, to.z, xDir);
        Float minDistance = 999f;
        Vector3f touchPoint = new Vector3f(0, 0, 0);
        int chunkX, chunkZ, offsetX, offsetZ, worldX, worldY, worldZ;

        float nowX, nowY, nowZ;
        Chunk chunk;
        IBlock targetBlock = null;
        GL_Vector placePoint  =new GL_Vector();
        String  touchFace = "";
        if (xMinMax != null) {
            for (int x = xMinMax[0]; x <= xMinMax[1]; x++) {
                float thisDistance = (x - from.x) / direction.x;
                if (thisDistance < minDistance) {
                    //判断这里是否有方块
                    //判断有没有和方块交接

                    nowX = x;
                    nowY = thisDistance * direction.y + from.y;
                    nowZ = thisDistance * direction.z + from.z;
                 if(nowY<0){
                     continue;
                 }
                    IBlock block = process(nowX, nowY, nowZ, localChunkProvider);

                    if (block != null) {
                        targetBlock = block;
                        touchFace = "xZuoMina";
                        minDistance = thisDistance;
                        touchPoint.x = nowX+0.001f;
                        touchPoint.y = nowY;
                        touchPoint.z = nowZ;//在x的正面

                        placePoint.x=nowX-0.001f;
                        placePoint.y=nowY;
                        placePoint.z=nowZ;//放置点在x的反面

                    }else {

                        block = process(nowX - 1, nowY, nowZ, localChunkProvider);
                        if (block != null) {
                            touchFace = "xYouMina";

                            targetBlock = block;


                            minDistance = thisDistance;
                            touchPoint.x = nowX-0.001f;
                            touchPoint.y = nowY;
                            touchPoint.z = nowZ;//在x的负面

                            placePoint.x=nowX+0.001f;
                            placePoint.y=nowY;
                            placePoint.z=nowZ;//放置点在x的正面
                        }
                    }
                }
            }
        }

        if (yMinMax != null) {
            for (int y = yMinMax[0]; y <= yMinMax[1]; y++) {
                float thisDistance = (y - from.y) / direction.y;
                if (thisDistance < minDistance) {
                    nowX = thisDistance * direction.x + from.x;
                    nowY = y;
                    nowZ = thisDistance * direction.z + from.z;
                    if(nowY<0){
                        continue;
                    }
                    IBlock block = process(nowX, nowY, nowZ, localChunkProvider);

                    if (block != null) {
                        targetBlock=block;
                        touchFace = "yxiamian";
                        minDistance = thisDistance;
                        touchPoint.x = nowX;
                        touchPoint.y = nowY+0.001f;
                        touchPoint.z = nowZ;//在x的正面

                        placePoint.x=nowX;
                        placePoint.y=nowY-0.001f;
                        placePoint.z=nowZ;//放置点在x的反面

                    }else
                    if(nowY>=1){
                        block = process(nowX, nowY-1, nowZ, localChunkProvider);

                        if (block != null) {
                            targetBlock=block;
                            touchFace = "yShangMian";
                            minDistance = thisDistance;
                            touchPoint.x = nowX;
                            touchPoint.y = nowY-0.001f;
                            touchPoint.z = nowZ;//在x的正面

                            placePoint.x=nowX;
                            placePoint.y=nowY+0.001f;
                            placePoint.z=nowZ;//放置点在x的反面

                        }
                    }


                }


            }
        }
        if (zMinMax != null) {
            for (int z = zMinMax[0]; z <= zMinMax[1]; z++) {
                float thisDistance = (z - from.z) / direction.z;
                if (thisDistance < minDistance) {
                    nowX = thisDistance * direction.x + from.x;
                    nowY = thisDistance * direction.y + from.y;
                    nowZ = z;
                    if(nowY<0){
                        continue;
                    }
                    IBlock block = process(nowX, nowY, nowZ, localChunkProvider);

                    if (block != null) {
                        targetBlock=block;
                        touchFace = "zQianMian";
                        minDistance = thisDistance;
                        touchPoint.x = nowX;
                        touchPoint.y = nowY;
                        touchPoint.z = nowZ+0.001f;//在x的正面

                        placePoint.x=nowX;
                        placePoint.y=nowY;
                        placePoint.z=nowZ-0.001f;//放置点在x的反面

                    }else{
                         block = process(nowX, nowY, nowZ-1, localChunkProvider);

                        if (block != null) {
                            targetBlock=block;
                            touchFace = "zHouMian";
                            minDistance = thisDistance;
                            touchPoint.x = nowX;
                            touchPoint.y = nowY;
                            touchPoint.z = nowZ-0.001f;//在x的正面

                            placePoint.x=nowX;
                            placePoint.y=nowY;
                            placePoint.z=nowZ+0.001f;//放置点在x的反面
                        }
                    }
                }
            }
        }
        if (minDistance != 999) {
            BulletResultDTO arr = new BulletResultDTO();

            arr.init(touchPoint.x,touchPoint.y,touchPoint.z,placePoint.x,placePoint.y,placePoint.z,(BaseBlock) targetBlock);


            //判断这个点在这个物体的哪个面上面

         //   arr.placePoint = placePoint;//����ײ
            return arr;
        }else{
            //判断是否和地板接触到了
            nowY=0;
            float length = (nowY-from.y)/direction.y;
            if(length<distance){
                nowX=(nowY-from.y)/direction.y*direction.x + from .x;
                nowZ=(nowY-from.y)/direction.y*direction.z + from .z;
                BulletResultDTO arr = new BulletResultDTO();
                arr.absolutePlacePoint=new GL_Vector(nowX,nowY,nowZ);
                return arr;
            }
        }
        return null;
    }
    /**
     * 点击选择探测器
     * @param from
     * @param direction
     * @param distance
     * @param blockname
     * @param delete
     * @return 3个Gl_vector 第一个是触碰到的block 位置 第二个是触碰路径上的最后一个空block位置 第三个第一个x分量是block的类型
     */
    //名字要改一下 改成选择方块
//    public BulletResultDTO rayTrace2(GL_Vector from, GL_Vector direction, float distance,String blockname,boolean delete) {
//        ChunkProvider localChunkProvider = CoreRegistry.get(ChunkProvider.class);
//        GL_Vector to = new GL_Vector(direction);
//
//
//        //获取经过的方块
//
//        List<Vector3i> list = new ArrayList<>();
//        //首先是更新到整数位 方向上最近的 整数位  后面是
//        int negative = direction.x < 0 ? -1 : 1;
//
//        //初始化为正帧数 如1.3 那么 下一个正整数是2  如果方向是负的那么下一个 是1
//        //如果是-1.3 正方向 则为-1 负方向是-2
//        //其实就是 向上取整数 和向下取整数
//        double xCell = Math.ceil(from.x);
//        double xFloor = Math.floor(from.x);
//        if(negative>0){
//
//            for(int xi=(int)xFloor;xi<xFloor+4;xi++){
//                //
//                float length = (xi-from.x)/direction.x;
//                if(length>4){
//                    //超过举例了
//                    break;
//                }
//                float bili = (xi-from.x)/direction.x;
//                float yi=bili*direction.y +from.y;
//                float zi = bili * direction.z+from.z;
//
//
//            }
//            for(float xi=(float)(xFloor-from.x);Math.abs(xi)<3;xi+=negative){
//                float bili = xi/direction.x;
//                float yi=bili*direction.y;
//                float zi =direction.z*bili;
//
//                if(Vector3fUtil.length(xi,yi,zi)>4){//超出4长度的认为太长了 够不着的
//                    break;
//                }
//
//                list.add(new Vector3i(xi,yi,zi));
////            if(Vector3fUtil.distance(from.x+xi,yd,zd,from.x,from.y,from.z)<4){
////
////            }
//
//
//            }
//        }
//
//        for(float xi=negative;Math.abs(xi)<3;xi+=negative){
//            float bili = xi/direction.x;
//            float yi=bili*direction.y;
//            float zi =direction.z*bili;
//
//            if(Vector3fUtil.length(xi,yi,zi)>4){//超出4长度的认为太长了 够不着的
//                break;
//            }
//
//            list.add(new Vector3i(xi,yi,zi));
////            if(Vector3fUtil.distance(from.x+xi,yd,zd,from.x,from.y,from.z)<4){
////
////            }
//
//
//        }
//
//        for(int yi=0;yi<3;yi++){
//            float bili = yi/direction.y;
//            float yi=bili*direction.y;
//            float zi =direction.z*bili;
//
////            if(Vector3fUtil.length(xi,yi,zi)>4){//超出4长度的认为太长了 够不着的
////                break;
////            }
//
//         //   list.add(new Vector3i(xi,yi,zi));
//
//        }
//        //������յ�λ��
//
//        //ÿ���ƶ�0.1 init  variable
//        int preBlockIndex=-1;
//        int chunkIndex=-1;
//        Integer preChunkIndex=null;  //if preChunkIndex != chunkIndex bianlichunk will to find it's new chunk and also use to find the place block
//        int blockIndex=0;
//        Chunk bianliChunk=null;//need to be initialize
//        int chunkX,preChunkX,chunkZ,preChunkZ;
//        //int chunkY;
//        float nowX,nowY,nowZ,preX,preY,preZ;
//        nowX=nowY=nowZ=preX=preY=preZ=0;
//        int worldX,worldY,worldZ ,preWorldX,preWorldY,preWorldZ;
//        int offsetX ,offsetZ ,preOffsetX,preOffsetZ;
//        // int preBlockIndex =0; every time push a little
//        preX = from.x;
//        preY = from.y;
//        preZ = from.z;
//
//        int mainDir =1;
//        if(direction.y>direction.x){
//            mainDir=2;
//        }
//
//        if(direction.z>direction.x){
//            mainDir=3;
//        }
//
//        if(mainDir>1){
//            if(direction.y>direction.z){
//                mainDir=2;
//            }else{
//                mainDir=3;
//            }
//        }
//        for(float x=0.1f;x<distance;x+=0.1){//一点一点推进 看撞到了哪个物体 every time add a little
//            nowX = from.x+x*to.x;
//            nowY = from.y+x*to.y;
//            nowZ = from.z+x*to.z;
//
//
//            worldX = MathUtil.floor(nowX);//x 代表推进的举例 推进后的x
//            worldY = MathUtil.floor(nowY);//推进后的y
//            if(worldY<0){
//                BulletResultDTO arr = new BulletResultDTO();
//                // arr.targetBlock= targetBlock;
//
//                arr.placePoint =  new GL_Vector(preX,
//                        preY,
//                        preZ);//����ײ��ǰ�ķ���λ��
//                return arr;//说明已经穿透地板了
//            }
//            worldZ = MathUtil.floor(nowZ);//推进后的z
//
//            offsetX = MathUtil.getOffesetChunk(worldX);
//            offsetZ = MathUtil.getOffesetChunk(worldZ);
//            blockIndex=offsetX*10000+offsetZ*100+worldY;//推进后落入的blockINdex
//            if(preBlockIndex==-1){
//                preBlockIndex = blockIndex;
//            }
//            chunkX=MathUtil.getBelongChunkInt(nowX);//换算出新的chunkX
//            chunkZ=MathUtil.getBelongChunkInt(nowZ);//换算出新的chunkZ
//            chunkIndex =  chunkX*10000+chunkZ;
//            if(blockIndex == preBlockIndex){
//                continue;
//            }
//            if(blockIndex!=preBlockIndex)//如果和之前判断的还是同一个的话 略过
//            {
//                //pre
//             /*   preBlockIndex = blockIndex;
//                preChunkIndex = chunkIndex;
//                preChunkX = chunkX;
//                preChunkZ = chunkZ;
//                preX=nowX;
//                preY=nowY;
//                preZ = nowZ;
//                preOffsetX = offsetX;
//                preOffsetZ = offsetZ;*/
//                //int chunkY;
//
//
//            }
//            //ȥ����Ƿ���һ������֮��
//            //ChunkImpl chunk = localChunkProvider.getChunk(new Vector3i(MathUtil.floor((float)_x/16),0,MathUtil.floor((float)_z/16)));
//            if(preChunkIndex==null){
//                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发
//                preChunkIndex = chunkIndex;
//            }
//
//            //once enther a new chunk block then make sure if crash a realblock
//            if( chunkIndex!=preChunkIndex){//如果进入到新的chunk方格子空间
//                bianliChunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));//因为拉远距离了之后 会导致相机的位置其实是在很远的位置 改为只其实还没有chunk加载 所以最好是从任务的头顶开始出发
//
//            }
//            // 得到当前chunk 改变的次数有限
//            //
//
//            //int chunk_pos_x =  MathUtil.floor(((float)_x)/16);
//            //int chunk_pos_z =  MathUtil.floor(((float)_z)/16);
//            //System.out.printf(" %d %d %d",_x,_y,_z);
//            //先获取chunk 在获取block
//            //blockRepository.haveObject(_x,_y,_z)
//
//            //detect if there is block
//            if(bianliChunk !=null){
//                //Block targetBlock = bianliChunk.getBlock(offsetX,worldY,offsetZ);
//                int blockType = bianliChunk.getBlockData(offsetX,worldY,offsetZ);
//                if(blockType>0){
//                    BulletResultDTO arr = new BulletResultDTO();
//                    if(delete){//如果是删除 就删除对应的方块
//                        //Block block=new BaseBlock("water",0,false);
//                        // bianliChunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor( _y), MathUtil.getOffesetChunk(_z),block);
//                        //((ChunkImpl)bianliChunk).build();
//                        // ((ChunkImpl)bianliChunk).buildAlpha();
//                        //bianliChunk.build();
//                        //return null;
//                        if(nowY<0){
//                            LogUtil.err("y can't be <0 ");
//                        }
//                   /* arr[0] = new GL_Vector(MathUtil.floor(from.x+x*to.x),
//                            MathUtil.floor(from.y+x*to.y),
//                            MathUtil.floor(from.z+x*to.z));*/
//                        //return ;
//                    }
//                    IBlock targetBlock = bianliChunk.getBlock(offsetX,worldY,offsetZ);
//                    arr.targetChunX= chunkX;
//                    arr.targetChunZ= chunkZ;
//                    arr.targetPoint = new GL_Vector(offsetX,
//                            MathUtil.floor(nowY),
//                            offsetZ);
//                    arr.targetBlock= (BaseBlock)targetBlock;
//                    //退回来 如果是增加放置一个block的话
//
//                    //BlockManager blockManager = CoreRegistry.get(BlockManager.class);//创建新的方块
//                    //Block block=new BaseBlock("water",blockManager.getBlock(blockname).getId(),false);
//
//                    //Chunk _chunk = localChunkProvider.getChunk(new Vector3i(chunkX,0,chunkZ));
//                    //_chunk.setBlock(MathUtil.getOffesetChunk(_x),MathUtil.floor(_y), MathUtil.getOffesetChunk(_z), block);
//               /* _chunk.setBlock(MathUtil.floor(from.x+x*to.x)%16,
//    					MathUtil.floor(from.y+x*to.y),
//    					MathUtil.floor(from.z+x*to.z)%16, block);*/
//                    //_chunk.disposeMesh();
//                    //_chunk.update();
//                    //重新更新
//                    //_chunk.build();
//                    //_chunk.buildAlpha();
//                    if(from.y+x*to.y<0){
//                        LogUtil.err("y can't be <0 ");
//                    }
//
//                    //  LogUtil.println("x:"+(from.x+x*to.x)%1 + "y:"+(from.y+x*to.y)%1+"z:"+(from.z+x*to.z)%1);
//
//                    arr.placePoint =  new GL_Vector(preX,
//                            preY,
//                            preZ);//����ײ��ǰ�ķ���λ��
//                    //  arr[2] =new GL_Vector(blockType,0,0);
//                    return arr;
//
//                }// if has no crash a real block then prex prey prez use to fallback
//                preBlockIndex = blockIndex;
//                preChunkIndex = chunkIndex;
//                preChunkX = chunkX;
//                preChunkZ = chunkZ;
//                preX=nowX;
//                preY=nowY;
//                preZ = nowZ;
//                preOffsetX = offsetX;
//                preOffsetZ = offsetZ;
//            }
//        }
//        return null;
//
//    }


}
