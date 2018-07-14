package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.block.BlockParseUtil;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.model.ImageBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.RemoteChunkProvider;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.ItemType;
import com.dozenx.game.engine.edit.view.GroupBlock;
import com.dozenx.game.engine.item.BlockUtil;
import com.dozenx.game.engine.item.parser.ItemDoorParser;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.MapUtil;
import com.dozenx.util.MathUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * item 模板存放在 只要block的engine 是door 类型的 就用这个definition去定义
 */
public class ComplexBlockDefinition extends BlockDefinition {
    private static final Logger logger = LoggerFactory.getLogger(ComplexBlockDefinition.class);
    public HashMap<Integer, BaseBlock> idShapeMap = new HashMap<>();

    public ComplexBlockDefinition() {
        this.id = ItemType.wood_door.id;
        this.getItemModel();
        this.setShape(TextureManager.getShape("wood_door_up"));
    }

    /**
     * 如果有一个配置文件被解析城了map 可以通过map再转成此类bean
     *
     * @param map
     */
    public void receive(Map map) {

        super.receive(map);
        ItemDoorParser.parse(this, map);
        String content = MapUtil.getStringValue(map, "script");
        int index = 0;
        boolean whenStart = false;
        StringBuffer sb = new StringBuffer();
        List<CodeLine> codeLines = new ArrayList<CodeLine>();
        CodeLine exPression = null;
        String lastWord = null;
        while (index < content.length()) {
            // Map result = getNextWord(content,index);//实现index 自加 并返回获得的 单词
            char c = content.charAt(index);
            if (exPression != null) {

                exPression.push(c);
                if (exPression.closed) {
                    exPression = null;
                }
            } else if (c == ';') {
                lastWord = sb.toString();
                if (exPression != null) {
                    exPression.end(lastWord);
                    exPression = null;
                }

            } else if (c == '(') {//空格的出现常用于 函数或者 if判断 或者优先级

                lastWord = sb.toString();
                sb.setLength(0);
                if (lastWord.equals("if")) {//if判断

                } else {//函数
                    exPression = new FunctionCodeLine(lastWord);
                }


                index++;
            } else if (c == '=') {//空格
                exPression = new AssignCodeLine(lastWord);
                // exPression.push();
                sb.setLength(0);

            } else {
                sb.append(c);
            }
            index++;


        }
        init();
        // this.setShape(TextureManager.getShape("wood_door"));
    }

    public static int getNextPos(String content, int startIndex, String c) {
        return content.indexOf(c, startIndex);
    }

    public static int getCh(String condition, int index, int length, String target) {
        if (condition.length() < index + length - 1) {
            return -1;
        }
        if (condition.substring(index, length).equals(target)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @param value
     * @return
     */
    public DoorBlockBean parse(int value) {
        int face = BlockParseUtil.getDirection(value); //朝向 0~7位是id 8~9 两位是朝向 00东 南 西 北10位是open 状态位 0 是关 1是开 11 是上下(门用不到)
        int open = BlockParseUtil.isOpen(value);//是否是打开
        int isTop = BlockParseUtil.isTop(value); //是否是上面部分
        DoorBlockBean doorBlockBean = new DoorBlockBean(face, open, isTop);
        return doorBlockBean;

    }

    /**
     * state id value 2 baseBlock
     *
     * @param value
     * @return
     */
    public DoorBlockBean int2Bean(int value) {
        int face = BlockParseUtil.getDirection(value); //朝向 0~7位是id 8~9 两位是朝向 00东 南 西 北10位是open 状态位 0 是关 1是开 11 是上下(门用不到)
        int open = BlockParseUtil.isOpen(value);//是否是打开
        int isTop = BlockParseUtil.isTop(value); //是否是上面部分
        DoorBlockBean doorBlockBean = new DoorBlockBean(face, open, isTop);
        return doorBlockBean;
    }

    /**
     * bean 2 id value
     *
     * @param bean
     * @return
     */
    public DoorBlockBean bean2Int(DoorBlockBean bean) {
        int face = bean.face;
        int open = bean.open;
        int isTop = bean.top;

        int id = ItemType.wood_door.id;
        return new DoorBlockBean(face, open, isTop);
    }

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
        BaseBlock block = new ImageBlock();
        // block[0] = TextureManager.getShape("stars_down");
        // block[1] = TextureManager.getShape("stars_up");
        if (block == null) {
            logger.error("wood_door_up block is null");//可能是server的启动
            return;
        }
        for (top = 0; top < 2; top++) {
            if (top == 1) {
                block = TextureManager.getShape("wood_door_up");
            }
            for (face = 0; face < 4; face++) {
                for (open = 0; open < 2; open++) {
                    BaseBlock blockTemp = block.copy();
                    blockTemp.reComputePoints();
                    if (open == 1) {

                        blockTemp.setPenetrate(true);
                        //
                        BlockUtil.rotateYWithCenter(blockTemp, 0f, 0f, 0f, Constants.PI90);
                    }
                    BlockUtil.rotateYWithCenter(blockTemp, 0.5f, 0.5f, 0.5f, Constants.PI90 * face);
                    int stateId = BlockParseUtil.getValue(face, ItemType.wood_door.id, top, open);
                    blockTemp.id = ItemType.wood_door.id;
                    blockTemp.stateId = stateId;

                    idShapeMap.put(stateId, blockTemp);//id shape Map 进行映射
                    TextureManager.putIdShapeMap(stateId, blockTemp);

                }

            }
        }

    }

    public boolean beUsed(BaseBlock block) {
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


        //left down block  left upblock  right down block right up block
        //x- position


        ChunkProvider localChunkProvider = CoreRegistry
                .get(ChunkProvider.class);

        Chunk chunk = localChunkProvider.getChunk(chunkX, 0, chunkZ);

        //x+ position
        IBlock x00Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x - 1), MathUtil.floor(placePoint.y), MathUtil.floor(placePoint.z));
        IBlock x01Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x - 1), MathUtil.floor(placePoint.y + 1), MathUtil.floor(placePoint.z));
        IBlock x10Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x + 1), MathUtil.floor(placePoint.y), MathUtil.floor(placePoint.z));
        IBlock x11Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x + 1), MathUtil.floor(placePoint.y + 1), MathUtil.floor(placePoint.z));


        IBlock z00Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x), MathUtil.floor(placePoint.y), MathUtil.floor(placePoint.z - 1));
        IBlock z01Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x), MathUtil.floor(placePoint.y + 1), MathUtil.floor(placePoint.z - 1));
        IBlock z10Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x), MathUtil.floor(placePoint.y), MathUtil.floor(placePoint.z + 1));
        IBlock z11Block = localChunkProvider.getBlockAt(MathUtil.floor(placePoint.x), MathUtil.floor(placePoint.y + 1), MathUtil.floor(placePoint.z + 1));
        boolean xPosible = false;
        boolean zPosible = false;
        if (x00Block == null || x01Block == null || x10Block == null || x11Block == null || x00Block.getId() == 0 || x01Block.getId() == 0
                || x10Block.getId() == 0 || x11Block.getId() == 0) {//如果x侧的左右两边没有满足两格高的枪毙 block为空的化 就不能在这一侧建立门了
            xPosible = false;
        } else {
            //检查y侧 是否方通的

//            if(z00Block.getId()!=0 && z01Block.getId()!=0 || ){
//
//            }
            xPosible = true;
        }

        if (z00Block == null || z01Block == null || z10Block == null || z11Block == null || z00Block.getId() == 0 || z01Block.getId() == 0
                || z10Block.getId() == 0 || z11Block.getId() == 0) {//如果x侧的block为空的化 就不能在这一侧建立门了
            zPosible = false;
        } else {
            zPosible = true;
        }
        //z- position

        //z+ postion
        if (cmd.cy < 0) {
            LogUtil.err("y can't be <0 ");
        }

        //blockType 应该和IteType类型联系起来

        if (cmd.blockType == ItemType.wood_door.id) {
            int faceDir = BlockUtil.getFaceDir(placePoint, viewDir);//获取当前的方向

            if (faceDir == Constants.BACK) {
                faceDir = 0;
                if (!xPosible) {//如果x方向两边没有两格高的墙壁的话
                    return;
                }
            } else if (faceDir == Constants.FRONT) {
                faceDir = 2;
                if (!xPosible) {//如果z方向是堵塞
                    return;
                }
            } else if (faceDir == Constants.LEFT) {
                faceDir = 1;
                if (!zPosible) {
                    return;
                }
            } else if (faceDir == Constants.RIGHT) {
                faceDir = 3;
                if (!zPosible) {
                    return;
                }
            }
            LogUtil.println("faceDir:" + faceDir);
            int newId = BlockParseUtil.getValue(faceDir, ItemType.wood_door.id, 0, 0);
            if (cmd.cy == 0) {
                LogUtil.println(newId + "");
            }
            cmd.blockType = newId;//获取加工后的方块状态id
                    /*if(pianyiX<0.1 ){//把一个方块分为 12345678 8个格子 算出它再哪个格子
                        //说明是向左的方向
                        if(block==1 ||  block==4||block==5 ||  block==8){
                            condition=Constants.LEFT;
                        }else{
                            condition=Constants.RIGHT;
                        }
                    }else if(pianyiX>0.9){
                        if(block==1 ||  block==4||block==5 ||  block==8){
                            condition=Constants.LEFT;
                        }else{
                            condition=Constants.RIGHT;
                        }
                        //说明是向右的方向
                    }else if(pianyiY<0.1 ){
                        //说明是向上的方向
                    }else if(pianyiY>0.9){
                        //说明是向下的方向
                    }else if(pianyiZ<0.1 ){
                        //说明是向前的方向
                    }else if(pianyiZ>0.9){
                        //说明是向后的方向
                    }*/
            CoreRegistry.get(Client.class).send(cmd);
            cmd.cy += 1;
            cmd.blockType = BlockParseUtil.getValue(faceDir, ItemType.wood_door.id, 1, 0);
            ;//拷贝下方的
            CoreRegistry.get(Client.class).send(cmd);


        }
    }

    public void beDestroyed(BaseBlock block, int chunkX, int chunkY, int chunkZ, int x, int y, int z) {
        //发现这个id
        int top = BlockParseUtil.isTop(block.stateId);
        ChunkProvider remoteChunkProvider = CoreRegistry
                .get(RemoteChunkProvider.class);
        if (top == 0) {//it's bottom block
            //删除上面的 delete up one

            remoteChunkProvider.setBlock(chunkX, chunkY, chunkZ, MathUtil.getOffesetChunk(x), y + 1, MathUtil.getOffesetChunk(z), 0);
        } else {

            remoteChunkProvider.setBlock(chunkX, chunkY, chunkZ, MathUtil.getOffesetChunk(x), y - 1, MathUtil.getOffesetChunk(z), 0);
            //remoteChunkProvider.setBlock();
        }
    }

    public static void main(String args[]){
        String content="baseModel=model(\"stairDown\",\"stairUp\");";
        int index = 0;
        boolean whenStart = false;
        StringBuffer sb = new StringBuffer();
        List<CodeLine> codeLines = new ArrayList<CodeLine>();
        CodeLine exPression = null;
        String lastWord = null;
        while (index < content.length()) {
            // Map result = getNextWord(content,index);//实现index 自加 并返回获得的 单词
            char c = content.charAt(index);
            if (exPression != null) {

                exPression.push(c);
                if (exPression.closed) {
                    codeLines.add(exPression);
                    exPression = null;

                }
            } else if (c == ';') {
                lastWord = sb.toString();
                if (exPression != null) {
                    exPression.end(lastWord);
                    exPression = null;
                }

            } else if (c == '(') {//空格的出现常用于 函数或者 if判断 或者优先级

                lastWord = sb.toString();
                sb.setLength(0);
                if (lastWord.equals("if")) {//if判断

                } else {//函数
                    exPression = new FunctionCodeLine(lastWord);
                }


                index++;
            } else if (c == '=') {//空格
                lastWord = sb.toString();

                exPression = new AssignCodeLine(lastWord);
                // exPression.push();
                lastWord=null;
                sb.setLength(0);

            } else {
                sb.append(c);
            }
            index++;


        }
        System.out.println(codeLines);
        BaseBlock block=null;
        for(CodeLine codeLine : codeLines){
            if(codeLine instanceof  AssignCodeLine){
                AssignCodeLine assignCodeLine =(AssignCodeLine)codeLine;
                if(assignCodeLine.leftVariable.equals("baseModel")){
                    if(assignCodeLine.rightCodeLine instanceof FunctionCodeLine) {
                        FunctionCodeLine functionCodeLine =(FunctionCodeLine)assignCodeLine.rightCodeLine ;
                        //特殊处理=======================
                        if(functionCodeLine.name .equals("model")){
                                if(functionCodeLine.args.size()>1){
                                    GroupBlock groupBlock =new GroupBlock();
                                    for(int i=0;i<functionCodeLine.args.size();i++){
                                        groupBlock.addChild(TextureManager.getShape(functionCodeLine.args.get(i)));
                                    }
                                    block=groupBlock;

                                }else{
                                     block =TextureManager.getShape(functionCodeLine.args.get(0));
                                }

                            }
                        //特殊处理结束=======================


                    }
                    //BaseBlock block =  assignCodeLine.rightCodeLine
                    //  this.setShape(  );
                }
            }
        }
    }

}
