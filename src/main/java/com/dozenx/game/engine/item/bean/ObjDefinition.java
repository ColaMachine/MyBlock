package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.block.BlockParseUtil;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.model.base.BaseBlock;
import cola.machine.game.myblocks.model.base.ObjBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.RemoteChunkProvider;
import com.alibaba.fastjson.JSONObject;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.edit.EditEngine;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.element.model.IconModel;
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
public class ObjDefinition extends BlockDefinition {
    private static final Logger logger = LoggerFactory.getLogger(ObjDefinition.class);
    public HashMap<Integer, BaseBlock> idShapeMap = new HashMap<>();

    public ObjDefinition() {
        this.itemTypeId = itemTypeId;
        this.getItemModel();
        BaseBlock block = TextureManager.getShape("wood_door_up");
        if(block!=null){//服务器端是null
            block.special=true;
        }
        this.setShape(block);
    }

    /**
     * 如果有一个配置文件被解析城了map 可以通过map再转成此类bean
     *
     * @param map
     */
    public void receive(Map map) {
        super.receive(map);
        parse( map);
        init();
        // this.setShape(TextureManager.getShape("wood_door"));
    }
    public  void parse(Map map){
     //   ItemBlockProperties blockProperties = (ItemBlockProperties)item.itemTypeProperties;




        if (GamingState.player != null) {//区分服务器版本和客户端版本
           // String shapeName = (String) map.get("shape");
            BaseBlock shape = (ObjBlock) EditEngine .parse((JSONObject) map.get("shape"));
                        /*if(icon.equals("fur_helmet")){
                            LogUtil.println("123");
                        }*/
            this.getItemModel().setIcon(this.itemModel.getIcon());

           // BaseBlock shape = TextureManager.getShape(shapeName);
            if (shape == null) {//如果没有shape 说明还没有用到该物体 还没有定义shape
                this.getItemModel().init();
            } else {
                this.setShape(shape);
                this.itemModel.wearModel = null;
                this.itemModel.handModel = new IconModel(this.itemModel.getIcon());
                this.itemModel.outdoorModel = new IconModel(this.itemModel.getIcon());

                this.itemModel.placeModel = new BoxModel(this.getShape());
            }
            this.setType(ItemMainType.BLOCK);
        }
        // return null;
    }

    /**
     * @param value
     * @return
     */
//    public DoorBlockBean parse(int value) {
//        int face = BlockParseUtil.getDirection(value); //朝向 0~7位是id 8~9 两位是朝向 00东 南 西 北10位是open 状态位 0 是关 1是开 11 是上下(门用不到)
//        int open = BlockParseUtil.isOpen(value);//是否是打开
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
   /* public DoorBlockBean int2Bean(int value) {
        int face = BlockParseUtil.getDirection(value); //朝向 0~7位是id 8~9 两位是朝向 00东 南 西 北10位是open 状态位 0 是关 1是开 11 是上下(门用不到)
        int open = BlockParseUtil.isOpen(value);//是否是打开
        int isTop = BlockParseUtil.isTop(value); //是否是上面部分
        DoorBlockBean doorBlockBean = new DoorBlockBean(face, open, isTop);
        return doorBlockBean;
    }

    *//**
     * bean 2 id value
     *
     * @param bean
     * @return
     *//*
    public DoorBlockBean bean2Int(DoorBlockBean bean) {
        int face = bean.face;
        int open = bean.open;
        int isTop = bean.top;

        int id = itemTypeId;
        return new DoorBlockBean(face, open, isTop);
    }*/

    public void init() {

        this.stateBlock();
    }

    /**
     * 缓存住所有的状态对应的模型 数字id 对应  baseblock 会存两份  一份在这个的dorrDefinition 一份再这个的 TextureManager的 idShapeMap中  shape 这个词汇感觉已经不适合了,会混淆一个概念 其实就是block
     */
    public void stateBlock() {
        int rot = 0;
        int face = 0;
        int top = 0;
        int open = 0;
        BaseBlock block = this.getShape();
        if (block == null) {
            logger.error("wood_door_up block is null");//可能是server的启动
            return;
        }

            for (face = 0; face < 4; face++) {

                    BaseBlock blockTemp = block.copy();
                    blockTemp.reComputePoints();
                    if (open == 1) {

                        blockTemp.setPenetrate(true);
                        //
                        BlockUtil.rotateYWithCenter(blockTemp, 0f, 0f, 0f, Constants.PI90);

                    }
                    BlockUtil.rotateYWithCenter(blockTemp, 0.5f, 0.5f, 0.5f,Constants.PI90 * face);
                    int stateId = BlockParseUtil.getValue(face, itemTypeId, top, open);
                    blockTemp.id = itemTypeId;
                    blockTemp.stateId = stateId;


                    GL_Vector[] minMaxPoints= BlockUtil.getMinMaxPoint(blockTemp.points);
                    blockTemp.x= minMaxPoints[0].x;
                    blockTemp.y= minMaxPoints[0].y;
                    blockTemp.z= minMaxPoints[0].z;

                    blockTemp.special=true;
                    blockTemp.height= minMaxPoints[1].x-blockTemp.x;
                    blockTemp.width= minMaxPoints[1].y-blockTemp.y;
                    blockTemp.thick= minMaxPoints[1].z-blockTemp.z;

                    blockTemp.minX= minMaxPoints[0].x;
                    blockTemp.minY= minMaxPoints[0].y;
                    blockTemp.minZ= minMaxPoints[0].z;

                    blockTemp.maxX= minMaxPoints[1].x;
                    blockTemp.maxY= minMaxPoints[1].y;
                    blockTemp.maxZ= minMaxPoints[1].z;


                    idShapeMap.put(stateId, blockTemp);//id shape Map 进行映射
                    TextureManager.putIdShapeMap(stateId, blockTemp);

                }

        //TODO test
//        BaseBlock block1 = TextureManager.stateIdShapeMap.get(2065);
//        if(block1.points[0].x==0){
//            LogUtil.println("errr");
//        }else{
//            LogUtil.println("right");
//        }
    }

    public  boolean beUsed(BaseBlock block) {
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
            } else {  LogUtil.println("关了");
                open = 0;
            }

            int newStateId = BlockParseUtil.getValue(face, itemTypeId, top, open);
            cmd.blockType = newStateId;
       /* int realBlockType = ByteUtil.get8_0Value(blockType);

        if(realBlockType==ItemType.wood_door.ordinal()){
            //判断当前是开还是关
            int state = ByteUtil.get16_12Value(blockType);
            if(state == 0 ){
                //是关
                blockType = 1<<12| blockType;
            }else{
                blockType = ByteUtil.HEX_0_1_1_1 & blockType;
            }*/
            // cmd.blockType= blockType;
            CoreRegistry.get(Client.class).send(cmd);
            if (top == 1 && cmd.cy == 0) {
                LogUtil.err("it's imposible");
            }
            //打印出 id 对应的朝向 开关 top  还有 她的 二进制

            LogUtil.println("top:"+top+"dir:"+face+"open:"+open);
            if (top == 0) {
                LogUtil.println("是下面");
                newStateId = BlockParseUtil.getValue(face, itemTypeId, 1, open);
                cmd.blockType = newStateId;
                cmd.cy++;//把上面的方块的状态也改了

            } else if (top == 1){
                LogUtil.println("是上面");
                newStateId = BlockParseUtil.getValue(face, itemTypeId, 0, open);
                cmd.blockType = newStateId;
                cmd.cy--;///把下面的状态也改了
                if(cmd.cy<0){
                    cmd.cy=0;
                }

            }
            CoreRegistry.get(Client.class).send(cmd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param placePoint
     * @param itemType
     * @param viewDir
     */
    public void use(GL_Vector placePoint, Integer itemType, GL_Vector viewDir) {
        //检查上方是否有物体
        int chunkX = MathUtil.getBelongChunkInt(placePoint.x);
        int chunkZ = MathUtil.getBelongChunkInt(placePoint.z);
        //   TreeBlock treeBlock =new TreeBlock(hitPoint);
        //treeBlock.startPosition=hitPoint;

        //  treeBlock.generator();
        int blockX = MathUtil.floor(placePoint.x) - chunkX * 16;
        int blockY = MathUtil.floor(placePoint.y);
        int blockZ = MathUtil.floor(placePoint.z) - chunkZ * 16;
        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
        cmd.cx = blockX;
        cmd.cz = blockZ;
        cmd.cy = blockY;
        cmd.type = 1;
        cmd.blockType = itemType;

        if (cmd.cy < 0) {
            LogUtil.err("y can't be <0 ");
        }

        //blockType 应该和IteType类型联系起来
//
//        if (cmd.blockType == ItemType.id) {
            int faceDir = BlockUtil.getFaceDirSimple(placePoint, viewDir);//获取当前的方向

            if(faceDir== Constants.BACK){
                faceDir=0;

            }else  if(faceDir== Constants.FRONT){
                faceDir=2;

            }else if(faceDir== Constants.LEFT){
                faceDir=1;

            }else if(faceDir== Constants.RIGHT){
                faceDir=3;

             }
            LogUtil.println("faceDir:"+faceDir);
            int newId = BlockParseUtil.getValue(faceDir, this.itemTypeId, 0, 0);
            if (cmd.cy == 0) {
                LogUtil.println(newId + "");
            }
            cmd.blockType = newId;//获取加工后的方块状态id

            CoreRegistry.get(Client.class).send(cmd);
//            cmd.cy += 1;
//            cmd.blockType = BlockParseUtil.getValue(faceDir, itemTypeId, 1, 0);
//            ;//拷贝下方的
//            CoreRegistry.get(Client.class).send(cmd);


        //}
    }

    public void beDestroyed(BaseBlock block,int chunkX,int chunkY,int chunkZ,int x,int y,int z){
        //发现这个id
        int top = BlockParseUtil.isTop(block.stateId);
        ChunkProvider remoteChunkProvider = CoreRegistry
                .get(RemoteChunkProvider.class);
        if(top==0){//it's bottom block
            //删除上面的 delete up one

            remoteChunkProvider.setBlock(chunkX,chunkY,chunkZ,MathUtil.getOffesetChunk(x),y+1,MathUtil.getOffesetChunk(z),0);
        }else{

            remoteChunkProvider.setBlock(chunkX,chunkY,chunkZ,MathUtil.getOffesetChunk(x),y-1,MathUtil.getOffesetChunk(z),0);
            //remoteChunkProvider.setBlock();
        }
    }

}
