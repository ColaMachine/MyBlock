package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.block.BlockParseUtil;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.BoxBlock;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.item.BlockUtil;
import com.dozenx.game.engine.item.parser.ItemBoxParser;
import com.dozenx.game.engine.ui.inventory.control.BoxController;
import com.dozenx.game.engine.ui.inventory.view.BoxPanel;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import java.util.Map;

/**
 * item 模板存放在
 */
public class BoxDefinition extends  BlockDefinition {
    public BaseBlock shapeOpen;
    public void receive(Map map) {
        super.receive(map);
        ItemBoxParser.parse(this, map);
        if(GamingState.instance.player != null ){
            this.stateBlock();
        }

    }

    /**
     * 缓存住所有的状态对应的模型 数字id 对应  baseblock 会存两份  一份在这个的dorrDefinition 一份再这个的 TextureManager的 idShapeMap中  shape 这个词汇感觉已经不适合了,会混淆一个概念 其实就是block
     */
    public void stateBlock() {

        int face = 0;
        int top = 0;//决定是了是右边还是左边 也有可能是单个的 取决于
        int open = 0;
        BaseBlock block = this.getShape();
        if (block == null) {
            //logger.error("wood_door_up block is null");//可能是server的启动
            return;
        }
//        for (top = 0; top < 2; top++) {
//            if (top == 1) {
//                block = TextureManager.getShape("wood_door_up");
//            }
            for (face = 0; face < 4; face++) {//0 1 2 3
                for (open = 0; open < 2; open++) {
                    if(face == 2){
                        LogUtil.println("box +face 2");
                    }

                    if (open == 1) {
                        block=shapeOpen;//TextureManager.getShape("box_open");//由于这个是box_open 而在配置里这个是一个objblock
                        if(shapeOpen == null){
                            block=shape;
                        }

                    }else{
                        block=shape;//TextureManager.getShape("box_close");

                    }
                    if(block == null ){
                        LogUtil.println("block is null");
                    }
                    BaseBlock blockTemp = block.copy();
                    blockTemp.reComputePoints();
                    BlockUtil.rotateYWithCenter(blockTemp, 0.5f, 0.5f, 0.5f,Constants.PI90 * face);
                    int stateId = BlockParseUtil.getValue(face, this.itemTypeId, top, open);
                    blockTemp.id = itemTypeId;
                    blockTemp.stateId = stateId;
                    if(stateId == 530){
                        LogUtil.println("box +stateId"+stateId);
                    }
                    if(stateId == 1024){
                        LogUtil.println("box +stateId"+stateId);
                    }

//                    GL_Vec.maxZ= minMaxPoints[1].z;


                    //idShapeMap.put(stateId, blockTemp);//id shape Map 进行映射
                    TextureManager.putIdShapeMap(stateId, blockTemp);

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
    public void use(GL_Vector placePoint, Integer itemType, GL_Vector viewDir) {

      //  stateBlock();
        //检查上方是否有物体
        int chunkX = MathUtil.getBelongChunkInt(placePoint.x);
        int chunkZ = MathUtil.getBelongChunkInt(placePoint.z);
        //   TreeBlock treeBlock =new TreeBlock(hitPoint);
        //treeBlock.startPosition=hitPoint;wood

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

        //if(cmd.blockType== ItemType.wood_door.ordinal()){
        int condition = BlockUtil.getFaceDir(placePoint, viewDir);
        //cmd.blockType = condition << 8 | cmd.blockType;
        LogUtil.println("放置新的元素 place new item stateId:"+cmd.blockType);
        cmd.blockType = BlockParseUtil.getValue(condition, itemTypeId, 0, 0);
        if(cmd.blockType==1042){
            LogUtil.println("hello");
        }
        CoreRegistry.get(Client.class).send(cmd);


        //   }
    }

    public boolean beUsed(BaseBlock block) {
        //stateBlock();
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

            CoreRegistry.get(BoxPanel.class).setVisible(true);
            CoreRegistry.get(BoxPanel.class).requestKeyboardFocus();
            Document.needUpdate = true;
            Switcher.isChat = true;
            //block.open = 1;

            //修改方块的状态为开并拿会物品列表
            ItemBean[] list = CoreRegistry.get(BoxController.class).openBox((BoxBlock)block);

            CoreRegistry.get(BoxPanel.class).reload(list);
            Document.needUpdate = true;

//            if (top == 1 && cmd.cy == 0) {
//                LogUtil.err("it's imposible");
//            }
//            //打印出 id 对应的朝向 开关 top  还有 她的 二进制
//
//            LogUtil.println("top:"+top+"dir:"+face+"open:"+open);
//            if (top == 0) {
//                LogUtil.println("是下面");
//                newStateId = BlockParseUtil.getValue(face, ItemType.wood_door.id, 1, open);
//                cmd.blockType = newStateId;
//                cmd.cy++;//把上面的方块的状态也改了
//
//            } else if (top == 1){
//                LogUtil.println("是上面");
//                newStateId = BlockParseUtil.getValue(face, ItemType.wood_door.id, 0, open);
//                cmd.blockType = newStateId;
//                cmd.cy--;///把下面的状态也改了
//                if(cmd.cy<0){
//                    cmd.cy=0;
//                }
//
//            }
//            CoreRegistry.get(Client.class).send(cmd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;


        //更新到boxpanel
        //  Document.getInstance().setFocusKeyWidget(CoreRegistry.get(BoxPanel.class));


        //打开状态
//        int chunkX =chunk.chunkPos.x;
//        int chunkZ=chunk.chunkPos.z;
//        ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
//        cmd.cx = this.getX();
//        cmd.cz = this.getZ();
//        cmd.cy = this.getY();
//        cmd.type = 1;
//
//
//
//
//        int newId= (open<<12| this.dir<<8 )| this.id;
//        cmd.blockType= newId;
//
//        CoreRegistry.get(Client.class).send(cmd);
//        return true;
    }


}
