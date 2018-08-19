package com.dozenx.game.engine.item;

import cola.machine.game.myblocks.block.BlockParseUtil;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.BaseBlock;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.util.ByteUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

/**
 * Created by dozen.zhang on 2017/5/23.
 */
public class BlockUtil {
    public static boolean isDoorOpen(int blockId) {
        int state = ByteUtil.get16_8Value(blockId);
        int switcher = ByteUtil.get8_4Value(state);
        int dir = ByteUtil.get4_0Value(state);
        if (switcher == 1) {
            return true;
        }
        return false;
    }

    public static boolean isDoor(int blockId) {
        if (blockId > 256) {
            blockId = ByteUtil.get8_0Value(blockId);
            if (blockId == ItemType.wood_door.id) {
                return true;
            }
        }


        return false;
    }

    public static int getFaceDir(GL_Vector placePoint, GL_Vector viewDir) {
        float pianyiX = placePoint.x % 1;
        float pianyiY = placePoint.y % 1;
        float pianyiZ = placePoint.z % 1;
        int block = 0;
        if (pianyiX < 0.5) {
            if (pianyiY < 0.5) {
                if (pianyiZ < 0.5) {
                    block = 4;
                } else {
                    block = 1;
                }
            } else {
                if (pianyiZ < 0.5) {
                    block = 5;
                } else {
                    block = 8;
                }
            }
        } else {
            if (pianyiY < 0.5) {
                if (pianyiZ < 0.5) {
                    block = 3;
                } else {
                    block = 2;
                }
            } else {
                if (pianyiZ < 0.5) {
                    block = 7;
                } else {
                    block = 6;
                }
            }
        }
        int condition = 0;
        if (Math.abs(viewDir.x) > Math.abs(viewDir.z)) {
            if (block == 1 || block == 4 || block == 5 || block == 8) {
                condition = Constants.LEFT;
            } else {
                condition = Constants.RIGHT;
            }
        } else {
            if (block == 1 || block == 2 || block == 5 || block == 6) {
                condition = Constants.FRONT;
            } else {
                condition = Constants.BACK;
            }
        }
        return condition;
        // cmd.blockType  = condition<<8|cmd.blockType;
    }

    public int getBlockId(String name) {
        return 0;
    }

    /**
     * 方块的id 是0~256中间的 多余的高位都是方块的状态
     *
     * @param blockValue
     * @return
     */
    public static int getRealBlockId(int blockValue) {
        if (blockValue > 255) {
            return blockValue & ByteUtil.HEX_0_0_1_1;
        }
        return blockValue;
    }

    public static void rotateYWithCenter(BaseBlock block, float centerX, float centerY, float centerZ, float degree) {
        // GL_Vector[] points=block.points;
        //要走到 不加x y z 会出现中心点无效的问题
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(centerX, centerY, centerZ);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0, degree, 0);

        rotateMatrix = GL_Matrix.multiply(translateMatrix, rotateMatrix);
        rotateMatrix = GL_Matrix.multiply(rotateMatrix, GL_Matrix.translateMatrix(-centerX, -centerY, -centerZ));
//        for(int i=0;i<points.length;i++){
//            points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
//        }

        block.reComputePoints(rotateMatrix);
    }

    public static void rotateZWithCenter(BaseBlock block, float centerX, float centerY, float centerZ, float degree) {
        //  GL_Vector[] points=block.points;
        //要走到 不加x y z 会出现中心点无效的问题
        GL_Matrix translateMatrix = GL_Matrix.translateMatrix(centerX, centerY, centerZ);

        GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0, 0, degree);

        rotateMatrix = GL_Matrix.multiply(translateMatrix, rotateMatrix);
        rotateMatrix = GL_Matrix.multiply(rotateMatrix, GL_Matrix.translateMatrix(-centerX, -centerY, -centerZ));
        block.reComputePoints(rotateMatrix);
        // for(int i=0;i<points.length;i++){
        // points[i]=rotateMatrix.multiply(rotateMatrix ,points[i]);
        // }
    }

    //获得立方体顶点中的最小的和最大点 用于碰撞试验
    public static GL_Vector[] getMinMaxPoint(GL_Vector[] points) {
        GL_Vector minPoint = points[0].copyClone();
        GL_Vector maxPoint = points[0].copyClone();
        for (int i = 1; i < points.length; i++) {

            GL_Vector nowPoint = points[i];
            if (nowPoint.x < minPoint.x){
                minPoint.x=nowPoint.x;
            }
            if (nowPoint.x > maxPoint.x){
                maxPoint.x=nowPoint.x;
            }

            if (nowPoint.y < minPoint.y){
                minPoint.y=nowPoint.y;
            }
            if (nowPoint.y > maxPoint.y){
                maxPoint.y=nowPoint.y;
            }


            if (nowPoint.z < minPoint.z){
                minPoint.z=nowPoint.z;
            }
            if (nowPoint.z > maxPoint.z){
                maxPoint.z=nowPoint.z;
            }

//            if (nowPoint.x <= minPoint.x+0.0001 && nowPoint.y <= minPoint.y+0.0001 && nowPoint.z <= minPoint.z+0.0001) {
//                minPoint = nowPoint;
//            } else if (nowPoint.x+0.0001 >= minPoint.x && nowPoint.y+0.0001 >= minPoint.y && nowPoint.z+0.0001 >= minPoint.z) {
//                maxPoint = nowPoint;
//            }


        }
        return new GL_Vector[]{minPoint, maxPoint};
    }

    public static void main(String args[]){
        int id =1042;
        System.out.println(BlockUtil.getRealBlockId(id));

        System.out.println(BlockParseUtil.isOpen(id));
        System.out.println(BlockParseUtil.getDirection(id));
        System.out.println(BlockParseUtil.isTop(id));



        int face = 0;
        int top = 0;//决定是了是右边还是左边 也有可能是单个的 取决于
        int open = 0;
        BaseBlock block = null;//this.getShape();
//        if (block == null) {
//            //logger.error("wood_door_up block is null");//可能是server的启动
//            return;
//        }
//        for (top = 0; top < 2; top++) {
//            if (top == 1) {
//                block = TextureManager.getShape("wood_door_up");
//            }
        for (face = 0; face < 4; face++) {
            for (open = 0; open < 2; open++) {

                int stateId = BlockParseUtil.getValue(face, ItemType.box.id, top, open);

                System.out.println(stateId);
            }
            //}
        }

        BaseBlock block1 = TextureManager.stateIdShapeMap.get(2065);
        if(block1.points[0].x==0){
            LogUtil.println("errr");
        }else{
            LogUtil.println("right");
        }
    }
}
