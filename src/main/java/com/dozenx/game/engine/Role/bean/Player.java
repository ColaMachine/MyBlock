package com.dozenx.game.engine.Role.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.model.BoneRotateImageBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.server.bean.PlayerStatus;
import glmodel.GL_Vector;
import org.lwjgl.Sys;

public class Player extends LivingThing {

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
}
