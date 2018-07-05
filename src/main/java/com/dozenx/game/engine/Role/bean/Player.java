package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.BoneRotateImageBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.physic.BulletResultDTO;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackBall;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.ItemMainType;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.server.bean.PlayerStatus;
import core.log.LogUtil;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

public class Player extends LivingThing {
    public  GL_Vector viewPosition =new GL_Vector();
    public Player(int id) {
        super(id);
        this.idleAnimation=new String[]{"wag_tail","sniffer"};
       // this.speed=50;
        this.getExecutor().setModel( new PlayerModel(this));
    }

    //判断人的当前状态 是在固体block上还是在液体block上 还是在液体中 还是在空气中
    public void setHuman(float posx, float posy, float posz, float dirx,
                         float diry, float dirz, float upx, float upy, float upz) {
        if (upx == 0 && upy == 0 && upz == 0) {
            System.out
                    .println("GLCamera.setCamera(): 1Up vector needs to be defined");
            upx = 0;
            upy = 1;
            upz = 0;
        }
        if (dirx == 0 && diry == 0 && dirz == 0) {
            System.out
                    .println("GLCamera.setCamera(): 1ViewDirection vector needs to be defined");
            dirx = 0;
            diry = 0;
            dirz = -1;
        }
        position = new GL_Vector(posx, posy, posz);
        viewDir = new GL_Vector(dirx, diry, dirz);
        walkDir = new GL_Vector(dirx, diry, dirz);
        upVector = new GL_Vector(upx, upy, upz);
        // System.out.printf("UpVector : %f %f %f
        // \r\n",UpVector.x,UpVector.y,UpVector.z);

        setRightVector( GL_Vector.crossProduct(viewDir, upVector));
        //RotatedX = RotatedY = RotatedZ = 0.0f; // TO DO: should set these to
        // correct values

		/*head.setHead(0,  3, 0, dirx, diry, dirz, upx, upy, upz);
		LLeg.setHead(0 - 0.25f, 0 + 1.5f, 0, dirx, diry, dirz, upx, upy,
				upz);
		RLeg.setHead(0 + 0.25f, 0 + 1.5f, 0, dirx, diry, dirz, upx, upy,
				upz);
		LHand.setHead(0 - 0.75f, 0 + 2.75f, 0, dirx, diry, dirz, upx,
				upy, upz);
		RHand.setHead(0 + 0.75f, 0 + 2.75f, 0, dirx, diry, dirz, upx,
				upy, upz);
		body.setHead(0, 0 + 1.5f, 0, dirx, diry, dirz, upx, upy, upz);*/

    }


    public void preRender() {
        adjust(this.position.x, this.position.y, this.position.z);

        //this.walk();

    }

  /*  public void render() {
        super.getrender();
    }*/


    public void move(float x, float y, float z) {
		/*float distance = GL_Vector.length(GL_Vector.sub(oldPosition,position));
        if(distance>0.02){*/
        super.move(x,y,z);


        //this.oldPosition.copy(this.position);

      // if(TimeUtil.getNowMills()-lastMoveTime>200) {
          // GamingState.cameraChanged = true;
        GamingState.setCameraChanged(true);

       //}
        ShaderManager.humanPosChangeListener();


       // this.position.set(x, y, z);
        //this.updateTime =  TimeUtil.getNowMills();

        //client.send("move:");
        //this.stable=false;
        //client.send("move:"+this.id+","+this.position.x+","+this.position.y+","+this.position.z+"");

        // }


	/*	String message = "move:"+ id+","+position.x
				+","+position.y
				+","+position.z+","+WalkDir.x+","+WalkDir.y+","+WalkDir.z;
		client.send(message);*/

    }

    //public boolean needJudgeCrash=false;
    public void move(GL_Vector vector) {
        float x = vector.x;
        float y = vector.y;
        float z = vector.z;
        this.move(x, y, z);


    }

    public void moveOld() {
        this.position = oldPosition;
        //make some adjust for float not Precision
    }


    public void moveRight(float Distance) { if(isDied())return;
        if (Math.abs(Distance) > 0.02) {

            this.changeAnimationState("walkerLeft");
            //if (this.stable) {
            lastMoveTime = Sys.getTime();
            this.move(GL_Vector.add(position, GL_Vector.multiply(getRightVector(),
                    Distance)));
        }
        //}
    }

    public void moveForward(float Distance) {
        if(isDied())return;
        // System.out.printf("%f %f %f
//        LogUtil.println("MoveForward");							// \r\n",ViewDir.x,ViewDir.y,ViewDir.z);
        //if (this.stable) {
        if (Math.abs(Distance) > 0.02) {
            //Player player= CoreRegistry.get(Player.class);
            AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            manager.apply(getModel().rootComponent, "walkerFoward");
            this.move(GL_Vector.add(position, GL_Vector.multiplyWithoutY(walkDir,
                    Distance)));

//        LogUtil.println(position+"");
            lastMoveTime = Sys.getTime();
            // System.out.printf("position: %f %f %f viewdir: %f %f %f
            // \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
            //}
        }
    }


    int du = 30;


    public void jumpHigh() {
       super.jump();
    }

    public void jump() { if(isDied())return;
        //this.position.y+=1;
        // ��¼��ǰ��ʱ��
        //水平方向
        //水平方向速度
        //起点位置

        //垂直起跳后的速度




        if (Switcher.IS_GOD) {
            this.position.y += 2;
            GamingState.setCameraChanged(true);
         } else if (this.isStable()) {
           super.jump();
            //GL_Vector from, GL_Vector dir, int userId,float speed
            //this.changeState(new JumpState(this,new JumpCmd(this.getPosition(),this.getWalkDir(),this.getId(),1)));
        }
    }


    public void addShoeEquip(boolean leftFlag ,ItemBean itemCfg)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = getModel().rootComponent.findChild("human_l_b_leg");
        }else{
            parent = getModel().rootComponent.findChild("human_r_b_leg");
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        getModel().addChild(parent,Component.body,"shoe",itemCfg);
    }
    public void addHeadEquip(ItemBean itemCfg)  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_head");
        getModel().addChild(parent,Component.body, "helmet",itemCfg);
    }

    public void clearHeadEquip(){
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_head");
        parent.children.remove(0);
    }
    public void addShoeEquip(ItemBean itemCfg)  {
        addShoeEquip(true, itemCfg);
        addShoeEquip(false, itemCfg);
    }
    public void clearShoeEquip(){
        clearShoeEquip(true);
        clearShoeEquip(false);
    }
    public void addLegEquip(ItemBean itemCfg)  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_l_b_leg");
        getModel().addChild(parent,Component.body, "pants", itemCfg);
    }

    public void clearLegEquip()  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_l_b_leg");
        getModel().removeChild(parent, "pants");
    }
    public void addBodyEquip(ItemBean itemCfg)  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_body");
        getModel().addChild(parent,Component.body, "armor", itemCfg);
    }
    public void clearBodyEquip()  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_body");
        getModel().removeChild(parent, "armor");
    }

    public void addHandEquip(ItemBean itemCfg)  {
        //Shape shape = itemCfg.getShape();
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("rhuman_hand");
        getModel().addChild(parent,Component.hand, "weapon", itemCfg);
    }
    public void clearHandEquip()  {
        //Shape shape = itemCfg.getShape();
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("rhuman_hand");
        getModel().removeChild(parent, "weapon");
    }


    public void addShoeEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = getModel().rootComponent.findChild("human_l_b_leg");
        }else{
            parent = getModel().rootComponent.findChild("human_r_b_leg");
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        getModel().addChild(parent,Component.body,"shoe",itemCfg);
    }
    public PlayerModel getModel(){
        return (PlayerModel)this.getExecutor().getModel();
    }
    public void clearShoeEquip(boolean leftFlag)  {
        BoneRotateImageBlock parent ;
        if(leftFlag){
            parent = getModel().rootComponent.findChild("human_l_b_leg");
        }else{
            parent = getModel().rootComponent.findChild("human_r_b_leg");
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        getModel().removeChild(parent,"shoe");
    }
    public void addHeadEquip(ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_head");
        getModel().addChild(parent,Component.body, "helmet", itemCfg);
    }
    public void addShoeEquip(ItemDefinition itemCfg)  {
        addShoeEquip(true, itemCfg);
        addShoeEquip(false, itemCfg);
    }
    public void addLegEquip(ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_l_b_leg");
        getModel().addChild(parent,Component.body, "pants", itemCfg);
    }
    public void addBodyEquip(ItemDefinition itemCfg)  {
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("human_body");
        getModel().addChild(parent,Component.body, "armor", itemCfg);
    }

    public void addHandEquip(ItemDefinition itemCfg)  {
        //Shape shape = itemCfg.getShape();
        BoneRotateImageBlock parent = 	getModel().rootComponent.findChild("rhuman_hand");
        getModel().addChild(parent,Component.hand, "weapon", itemCfg);
    }


    public void setInfo( PlayerStatus info ){
        super.setInfo(info);
        PlayerModel model = (PlayerModel)getModel();
        //if(info.getBodyEquip()>0){
        model.addBodyEquip(new ItemBean(ItemManager.getItemDefinition(info.getBodyEquip()), 1));
        //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //}else {
        //getModel().addBodyEquip(null);

        //}if(info.getHeadEquip()>0){
        model.addHeadEquip(new ItemBean(ItemManager.getItemDefinition(info.getHeadEquip()), 1));
        //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //   }
        //}if(info.getHandEquip()>0){
        model.addHandEquip(new ItemBean(ItemManager.getItemDefinition(info.getHandEquip()), 1));
        //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //}if(info.getLegEquip()>0){
        model.addLegEquip(new ItemBean(ItemManager.getItemDefinition(info.getLegEquip()), 1));
        //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //// }if(info.getFootEquip()>0){
        model.addShoeEquip(new ItemBean(ItemManager.getItemDefinition(info.getFootEquip()), 1));
        //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));

    }

    public void attack(){
        //AttackManager.attack();
        super.attack();
//       int damage =  AttackManager.computeDamage(this,this.getTarget());
//        LogUtil.println("player 攻击");
//        this.getTarget().beAttack(damage);
//        AttackManager.addAttackEvent(this,this.getTarget(),damage);
//        AttackBall ball =new AttackBall(5,this.getPosition().copyClone(),this.getViewDir().copyClone(),5,2,this,0);
//
//        AttackManager.addAttack(ball);
    }

    public void use(BulletPhysics bulletPhysics){
        //这逻辑是什么鬼?===============================================



        //获取客户端的方块管理器
        ChunkProvider localChunkProvider = CoreRegistry
                .get(ChunkProvider.class);
        boolean delete = false;
        //获取当前的block item
        ItemDefinition  handItemDefintion = ItemManager.getItemDefinition(getHandEquip());
        //如果当前手上有拿block 就是放置的动作 如果没有 就是拆方块的节奏
        //准备命令
        if(handItemDefintion!=null && handItemDefintion.getType() == ItemMainType.BLOCK  ){
            delete=false;
        }else {
            delete=true;

        }


        //获取选中的方块
        BulletResultDTO arr
                = bulletPhysics.rayTrace(new GL_Vector(viewPosition.x, viewPosition.y, viewPosition.z), viewDir,
                20, "soil", delete);


        //如果选到了方块
        if(arr!=null){
            GL_Vector targetPoint =arr.targetPoint;
            GL_Vector  placePoint= arr.placePoint;

            //打印点
            //获得朝向
            //判断选择的方块是不是门之类的
            //Integer blockType = ((Block)arr[2]).getId();
            IBlock targetBlock = arr.targetBlock;
            if(arr.targetBlock!=null ) {
                Integer blockType = targetBlock.getId();
                //获得靠近还是靠远
                //LogUtil.println("x:"+targetPoint.x%1 + "y:"+targetPoint.y%1+"z:"+targetPoint.z%1);
                //这却的途径是什么

                //如果物体是可以被使用的
                //Block targetBlock =
                //===========先判断对象方块能不能被使用==========================
                if (targetBlock.beuse()) {//如果是有状态的block
                   /* //通过一个通用的方式获得点击的面在哪里
                    int chunkX = MathUtil.getBelongChunkInt(targetPoint.x);
                    int chunkZ = MathUtil.getBelongChunkInt(targetPoint.z);
                    //   TreeBlock treeBlock =new TreeBlock(hitPoint);
                    //treeBlock.startPosition=hitPoint;
                    //  treeBlock.generator();
                    int blockX = MathUtil.floor(targetPoint.x) - chunkX * 16;
                    int blockY = MathUtil.floor(targetPoint.y);
                    int blockZ = MathUtil.floor(targetPoint.z) - chunkZ * 16;
                    ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
                    cmd.cx = blockX;
                    cmd.cz = blockZ;
                    cmd.cy = blockY;
                    cmd.type = 1;


                    int realBlockType = ByteUtil.get8_0Value(blockType);

                    if(realBlockType==ItemType.wood_door.ordinal()){
                        //判断当前是开还是关
                        int state = ByteUtil.get16_12Value(blockType);
                        if(state == 0 ){
                            //是关
                            blockType = 1<<12| blockType;
                        }else{
                            blockType = ByteUtil.HEX_0_1_1_1 & blockType;
                        }
                        cmd.blockType= blockType;
                        CoreRegistry.get(Client.class).send(cmd);
                        return;
                    }*/
                    return;
                }
            }

            //=================开始使用手上物体 说明对象物体不能被使用============

            if(handItemDefintion==null){
                return;
            }

            // int condition = BlockUtil.getIndex(placePoint, camera.getViewDir());
            handItemDefintion.use(placePoint,handItemDefintion.getItemType(),viewDir);

            //有各种itemDefintion的定义
            //开始放置物品
            //其实我就是想知道点击的是哪一个面上 点击的面上
            //得出当前人手上拿的是不是方块
               /* int chunkX = MathUtil.getBelongChunkInt(placePoint.x);
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
                cmd.blockType = handItem.getItemType().ordinal();



                if(cmd.cy<0){
                    LogUtil.err("y can't be <0 ");
                }

                //blockType 应该和IteType类型联系起来

                if(cmd.blockType==ItemType.wood_door.ordinal()){
                    int condition = BlockUtil.getIndex(placePoint,camera.getViewDir());
                    cmd.blockType  = condition<<8|cmd.blockType;
                    *//*if(pianyiX<0.1 ){//把一个方块分为 12345678 8个格子 算出它再哪个格子
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
                    }*//*
                }
                CoreRegistry.get(Client.class).send(cmd);*/
        }
    }
}
