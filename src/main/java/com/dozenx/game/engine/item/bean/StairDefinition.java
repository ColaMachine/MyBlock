package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.block.BlockParseUtil;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.item.BlockUtil;
import com.dozenx.game.engine.item.parser.ItemDoorParser;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * item 模板存放在 只要block的engine 是door 类型的 就用这个definition去定义
 */
public class StairDefinition extends BlockDefinition {
    private static final Logger logger = LoggerFactory.getLogger(StairDefinition.class);
    public HashMap<Integer, BaseBlock> idShapeMap = new HashMap<>();

    public StairDefinition() {
        this.itemTypeId = ItemType.wood_door.id;
        this.getItemModel();

    }

    /**
     * 如果有一个配置文件被解析城了map 可以通过map再转成此类bean
     *
     * @param map
     */
    public void receive(Map map) {
        super.receive(map);
        ItemDoorParser.parse(this, map);
        if(GamingState.player!=null) {
            init();
        }
        // this.setShape(TextureManager.getShape("wood_door"));
    }


//    /**
//     * @param value
//     * @return
//     */
//    public DoorBlockBean parse(int value) {
//        int face = BlockParseUtil.getDirection(value); //朝向 0~7位是id 8~9 两位是朝向 00东 南 西 北10位是open 状态位 0 是关 1是开 11 是上下(门用不到)
//        //int open = BlockParseUtil.isOpen(value);//是否是打开
//        int isTop = BlockParseUtil.isTop(value); //是否是上面部分
//        DoorBlockBean doorBlockBean = new DoorBlockBean(face, open, isTop);
//        return doorBlockBean;
//
//    }

    /**
     * state id value 2 baseBlock
     *
     * @param value
     * @return
     */
//    public DoorBlockBean int2Bean(int value) {
//        int face = BlockParseUtil.getDirection(value); //朝向 0~7位是id 8~9 两位是朝向 00东 南 西 北10位是open 状态位 0 是关 1是开 11 是上下(门用不到)
//        int open = BlockParseUtil.isOpen(value);//是否是打开
//        int isTop = BlockParseUtil.isTop(value); //是否是上面部分
//        DoorBlockBean doorBlockBean = new DoorBlockBean(face, open, isTop);
//        return doorBlockBean;
//    }

    /**
     * bean 2 id value
     *
     * @param bean
     * @return
     */
//    public DoorBlockBean bean2Int(DoorBlockBean bean) {
//        int face = bean.face;
//        int open = bean.open;
//        int isTop = bean.top;
//
//        int id = ItemType.wood_door.id;
//        return new DoorBlockBean(face, open, isTop);
//    }
    public void init() {

        GroupBlock groupBlock = new GroupBlock();


        BaseBlock top = TextureManager.getShape("wood_stair_top").copy();
        BaseBlock bottom = TextureManager.getShape("wood_stair_bottom").copy();

        groupBlock.addChild(bottom);
        top.addY(0.5f);
        groupBlock.addChild(top);


        topBlock = new GroupBlock();

        BaseBlock top2 = TextureManager.getShape("wood_stair_top").copy();
        BaseBlock bottom2 = TextureManager.getShape("wood_stair_bottom").copy();
        bottom2.addY(0.5f);
        topBlock.addChild(top2);
        topBlock.addChild(bottom2);
        this.setShape(groupBlock);
        groupBlock.special=true;
        topBlock.special=true;
        this.stateBlock();
    }

    GroupBlock topBlock = null;

    /**
     * 缓存住所有的状态对应的模型 数字id 对应  baseblock 会存两份  一份在这个的dorrDefinition 一份再这个的 TextureManager的 idShapeMap中  shape 这个词汇感觉已经不适合了,会混淆一个概念 其实就是block
     */
    public void stateBlock() {
        int rot = 0;
        int face = 0;
        int top = 0;
        int open = 0;
        BaseBlock block = this.getShape();

        for (top = 0; top < 2; top++) {

            for (face = 0; face < 4; face++) {

                BaseBlock blockTemp = block.copy();
                if (top == 1) {
                    blockTemp=topBlock.copy();
                }
                blockTemp.reComputePoints();

                BlockUtil.rotateYWithCenter(blockTemp, 0.5f, 0.5f, 0.5f, Constants.PI90 * face);
                int stateId = BlockParseUtil.getValue(face, ItemType.wood_stair.id, top, open);
                blockTemp.id = ItemType.wood_stair.id;
                blockTemp.stateId = stateId;

                idShapeMap.put(stateId, blockTemp);//id shape Map 进行映射
                TextureManager.putIdShapeMap(stateId, blockTemp);


            }
        }

    }

   /* public boolean beUsed(BaseBlock block) {
        try {
            //通过一个通用的方式获得点击的面在哪里
            //  int chunkX = MathUtil.getBelongChunkInt(targetPoint.x);
            // int chunkZ = MathUtil.getBelongChunkInt(targetPoint.z);
            //   TreeBlock treeBlock =new TreeBlock(hitPoint);
            //treeBlock.startPosition=hitPoint;
            //  treeBlock.generator();
            //  int blockX = MathUtil.floor(targetPoint.x) - chunkX * 16;
            //  int blockY = MathUtil.floor(targetPoint.y);
            //  int blockZ = MathUtil.floor(targetPoint.z) - chunkZ * 16;

            int chunkX = block.chunkX;//.getChunk().getChunkWorldPosX();//.chunkX;
            int chunkZ = block.chunkZ;//getChunk().getChunkWorldPosZ();
            ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
            cmd.cx = (int) block.getX();
            cmd.cz = (int) block.getZ();
            cmd.cy = (int) block.getY();
            cmd.type = 1;
            int open = BlockParseUtil.isOpen(block.stateId);//.abs( this.open -1)  ;
            int face = BlockParseUtil.getDirection(block.stateId);//.abs( this.open -1)  ;
            int top = BlockParseUtil.isTop(block.stateId);

            if (open == 0) {
                open = 1;
                LogUtil.println("开了");
            } else {
                LogUtil.println("关了");
                open = 0;
            }

            int newStateId = BlockParseUtil.getValue(face, ItemType.wood_door.id, top, open);
            cmd.blockType = newStateId;
       *//* int realBlockType = ByteUtil.get8_0Value(blockType);

        if(realBlockType==ItemType.wood_door.ordinal()){
            //判断当前是开还是关
            int state = ByteUtil.get16_12Value(blockType);
            if(state == 0 ){
                //是关
                blockType = 1<<12| blockType;
            }else{
                blockType = ByteUtil.HEX_0_1_1_1 & blockType;
            }*//*
            // cmd.blockType= blockType;
            CoreRegistry.get(Client.class).send(cmd);
            if (top == 1 && cmd.cy == 0) {
                LogUtil.err("it's imposible");
            }
            //打印出 id 对应的朝向 开关 top  还有 她的 二进制

            LogUtil.println("top:" + top + "dir:" + face + "open:" + open);
            if (top == 0) {
                LogUtil.println("是下面");
                newStateId = BlockParseUtil.getValue(face, ItemType.wood_door.id, 1, open);
                cmd.blockType = newStateId;
                cmd.cy++;//把上面的方块的状态也改了

            } else if (top == 1) {
                LogUtil.println("是上面");
                newStateId = BlockParseUtil.getValue(face, ItemType.wood_door.id, 0, open);
                cmd.blockType = newStateId;
                cmd.cy--;///把下面的状态也改了
                if (cmd.cy < 0) {
                    cmd.cy = 0;
                }

            }
            CoreRegistry.get(Client.class).send(cmd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /**
     * @param placePoint
     * @param itemType
     * @param viewDir
     */
    public void use(GL_Vector placePoint, Integer itemType, GL_Vector viewDir) {
        //检查上方是否有物体
        int chunkX = MathUtil.getBelongChunkInt(placePoint.x);
        int chunkZ = MathUtil.getBelongChunkInt(placePoint.z);
        LogUtil.println(placePoint.y + "");
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        int blockX = MathUtil.floor(placePoint.x) - chunkX * 16;
        int blockY = MathUtil.floor(placePoint.y);
        int blockZ = MathUtil.floor(placePoint.z) - chunkZ * 16;
        int top = 0;
        cmd.cx = blockX;
        cmd.cz = blockZ;
        cmd.cy = blockY;
        float xiaoShuDian = placePoint.y % 1;

        if (xiaoShuDian > 0.5) {
            top = 1;
        }
        cmd.type = 1;
        cmd.blockType = itemType;

        //z+ postion
        if (cmd.cy < 0) {
            LogUtil.err("y can't be <0 ");
        }

        //blockType 应该和IteType类型联系起来


        int faceDir = BlockUtil.getFaceDir(placePoint, viewDir);//获取当前的方向

        if (faceDir == Constants.BACK) {
            faceDir = 0;

        } else if (faceDir == Constants.FRONT) {
            faceDir = 2;

        } else if (faceDir == Constants.LEFT) {
            faceDir = 1;

        } else if (faceDir == Constants.RIGHT) {
            faceDir = 3;

        }
        LogUtil.println("faceDir:" + faceDir);
        int newId = BlockParseUtil.getValue(faceDir, ItemType.wood_stair.id, top, 0);
        if (cmd.cy == 0) {
            LogUtil.println(newId + "");
        }

        cmd.blockType = newId;//获取加工后的方块状态id
        CoreRegistry.get(Client.class).send(cmd);


    }


}
