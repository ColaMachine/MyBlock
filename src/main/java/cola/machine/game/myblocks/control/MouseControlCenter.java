package cola.machine.game.myblocks.control;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.IBlock;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.physic.BulletResultDTO;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.chunks.Chunk;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.ui.head.view.HeadPanel;
import com.dozenx.game.engine.ui.inventory.view.BoxPanel;
import com.dozenx.game.engine.ui.toolbar.view.ToolBarView;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.util.MathUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glapp.GLApp;
import glapp.GLCamera;
import glmodel.GL_Vector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MouseControlCenter {
    public Player player;//当前玩家对象
    public GLCamera camera;//当前相机
    //public MyBlockEngine engine;
    public double preKeyTime = 0;
    public Robot robot;//用于自动镜头移动 鼠标剧终
    public float centerX = 0;//屏幕中心
    public float centerY = 0;//屏幕中心
    public LivingThingManager livingThingManager;//生物管理器

    public float prevMouseX = 0;//之前鼠标点击的位置 或者启动的位置
    public float prevMouseY = 0;//之前鼠标点击的位置
    public boolean canDetectMove = true;
    public BulletPhysics bulletPhysics; //物理引擎
    int DRAG_DIST=0;
    public GameState gameState;
    final Client client ;
    boolean mouseRightPressed=false;//用来判断是否按着
    boolean mouseLeftPressed=false;

    private long lastkeyPressTime =0;
    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */
    Point mousepoint;//用于镜头对准
    public MouseControlCenter(Player player, GLCamera camera,GameState gameState,Client client) {
        this(player,  camera,client) ;

        this.gameState=gameState;
    }

    public MouseControlCenter(Player player, GLCamera camera,Client client) {
        this.client = client;
        //this.engine = engine;
        this.player = player;
        this.camera = camera;
        this.livingThingManager = CoreRegistry.get(LivingThingManager.class);
        centerX = Display.getX() + GLApp.displayWidth / 2;

        centerY = Display.getY() + GLApp.displayWidth / 2;
        // System.out.println("the center position : x"+centerX+" y "+centerY);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
    public int lastKey = 0;
    int key;
    public void handleNavKeys(float seconds) {
        //dosn't need cooling
        // w a s d space
        //i think it's something  could be repeat something only receive down up as a signal;
        //这个方法里处理需要重复按键的实情
        //let put the key in a group that need cooling
        //LogUtil.println(seconds+"");
        if( Switcher.isChat){
            return;
        }

       /* if (Keyboard.isKeyDown(Keyboard.KEY_G)) {

        }*/

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            //livingThingManager.chooseObject(null);
            // human.headRotate(-human.camSpeedXZ * seconds*100,0 );
        }   if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            if(Switcher.edit){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.adjustWidth(1,true);
                }else{
                    GamingState.editEngine.adjustWidth(1,false);
                }

            }else
            if(AttackManager.selectThing!=null){
                AttackManager.selectThing.width-=1;
            }
            // human.headRotate(-human.camSpeedXZ * seconds*100,0 );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            if(Switcher.edit){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.adjustWidth(-1,true);
                }else{
                    GamingState.editEngine.adjustWidth(-1,false);
                }

            }else
            if(AttackManager.selectThing!=null){
                AttackManager.selectThing.width+=1;
            }
            //human.headRotate(human.camSpeedXZ * seconds*100,0 );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            // human.headRotate(0, -human.camSpeedXZ * seconds*100);
            if(Switcher.edit){

                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.adjustThick(1,true);
                }else{
                    GamingState.editEngine.adjustThick(1,false);
                }

            }else
            if(AttackManager.selectThing!=null){
                AttackManager.selectThing.thick+=1;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            if(Switcher.edit){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.adjustThick(-1,true);
                }else{
                    GamingState.editEngine.adjustThick(-1,false);
                }

            }else
            if(AttackManager.selectThing!=null){
                AttackManager.selectThing.thick-=1;
            }
            //human.headRotate(0, human.camSpeedXZ * seconds*100);
        }  else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            if(Switcher.edit){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.adjustHeight(-1,true);
                }else{
                    GamingState.editEngine.adjustHeight(-1,false);
                }

            }else
            if(AttackManager.selectThing!=null){
                AttackManager.selectThing.height-=1;return;
            }else {
                Switcher.CAMERA_2_PLAYER++;
                if (Switcher.CAMERA_2_PLAYER > 200) {
                    Switcher.CAMERA_2_PLAYER = 200;
                }
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            if(Switcher.edit){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.adjustHeight(1,true);
                }else{
                    GamingState.editEngine.adjustHeight(1,false);
                }


            }else
            if(AttackManager.selectThing!=null){
                AttackManager.selectThing.height+=1;return;
            }else {
                Switcher.CAMERA_2_PLAYER--;
                if (Switcher.CAMERA_2_PLAYER < 0) {
                    Switcher.CAMERA_2_PLAYER = 0;
                }
            }
        } if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            player.bodyRotate(Constants.camSpeedR * seconds,0);
        }   if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            // Turn right

            player.bodyRotate( -Constants.camSpeedR * seconds,0);
        }



        if(TimeUtil.getNowMills() - lastkeyPressTime >200){
             key = -1;
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                key= WalkCmd.FORWARD_LEFT;
            }else
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                key= WalkCmd.FORWARD_RIGHT;
            }else{
                key= WalkCmd.FORWARD;
            }
        }else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                key= WalkCmd.BACK_LEFT;
            }else
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                key= WalkCmd.BACK_RIGHT;
            }else{
                key= WalkCmd.BACK;
            }
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            key= WalkCmd.LEFT;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            key= WalkCmd.RIGHT;
        }else{
            key= WalkCmd.STOP;
        }

        if((key!=0 && TimeUtil.getNowMills() - lastkeyPressTime>500 )|| key!=lastKey  || player.isDirChanged()){
            lastKey= key;
            player.setDirChanged(false);
            //walkCmd.dir = key;
            //player.receive(walkCmd);

            GL_Vector from = player.position;
            GL_Vector walkDir = player.walkDir.normalize().copyClone();
            GL_Vector right = player.getRightVector().copyClone();
            GL_Vector to = null;
            if(key==WalkCmd.FORWARD){

                to=from.copyClone().add(walkDir.mult(player.speed));
            }else if(key==WalkCmd.BACK){
                to=from.copyClone().add(walkDir.mult(-player.speed));
            }else if(key==WalkCmd.LEFT){
                to=from.copyClone().add(right.mult(-player.speed));
            }else if(key==WalkCmd.RIGHT){
                to=from.copyClone().add(right.mult(player.speed));
            }else if(key==WalkCmd.FORWARD_LEFT){
                to=from.copyClone().add(walkDir.mult(7.5f).add(right.mult(-7.5f)));
            }else if(key==WalkCmd.FORWARD_RIGHT){
                to=from.copyClone().add(walkDir.mult(7.5f).add(right.mult(7.5f)));
            }else if(key==WalkCmd.BACK_LEFT){
                to=from.copyClone().add(walkDir.mult(-7.5f).add(right.mult(-7.5f)));
            }else if(key==WalkCmd.BACK_RIGHT){
                to=from.copyClone().add(walkDir.mult(-7.5f).add(right.mult(7.5f)));
            }else if(key == WalkCmd.STOP){
                to=from;
            }
           // LogUtil.println("from:"+from);
            //LogUtil.println("to:"+to);
            WalkCmd2 walkCmd =new WalkCmd2(from,to,player.getId());
            walkCmd.bodyAngle = player.getBodyAngle();
            if(key == WalkCmd.STOP){
                //return ;
                walkCmd.stop =true;
            }
            //client.send(walkCmd);
            player.receive(walkCmd);
            lastkeyPressTime=TimeUtil.getNowMills();

        }

        }





       if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            if(Switcher.edit){
                player.position.y-=1;  GamingState.setCameraChanged(true);
            }else {
                player.position.y = player.position.y - 3 * seconds;
                player.move(player.position);
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            player.position.y = player.position.y + 3 * seconds;
            player.move(player.position);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if(Switcher.edit){
                player.position.y+=1;
                GamingState.setCameraChanged(true);
            }else {
                double timenow = GLApp.getTimeInSeconds();

                if ((timenow - preKeyTime) < 1) {
                    return;
                }
                preKeyTime = timenow;
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                    player.jumpHigh();
                // System.out.println("ͬʱ������w��");
                player.jump();
            }
            // System.out.println("jump");
        }
                                                        /*else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(1);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(2);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
                                                            double timenow = GLApp.getTimeInSeconds();

                                                            if ((timenow - preKeyTime) < 500) {
                                                                return;
                                                            }
                                                            preKeyTime = timenow;


                                                            Switcher.PRINT_SWITCH = !Switcher.PRINT_SWITCH;
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
                                                            double timenow = GLApp.getTimeInSeconds();

                                                            if ((timenow - preKeyTime) < 1) {
                                                                return;
                                                            }
                                                            System.out.println("pretime:"
                                                                    + preKeyTime + " time:" + timenow + " seconds:" + seconds);
                                                            preKeyTime = timenow;
                                                            Switcher.IS_GOD = !Switcher.IS_GOD;
                                                            System.out.println("god mode:" + Switcher.IS_GOD);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {

                                                            double timenow = GLApp.getTimeInSeconds();
                                                            if ((timenow - preKeyTime) < 1) {
                                                                return;
                                                            }
                                                            preKeyTime = timenow;
                                                            CoreRegistry.get(Bag.class).changeShow();
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(3);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(4);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(5);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(6);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(7);
                                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
                                                            CoreRegistry.get(ToolBar.class).keyDown(8);
                                                        }
                                        if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
                                            CoreRegistry.get(ToolBar.class).keyDown(9);
                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
                                            double timenow = GLApp.getTimeInSeconds();
                                            if ((timenow - preKeyTime) < 1) {
                                                return;
                                            }
                                            preKeyTime = timenow;
                                            Switcher.CAMERA_2_PLAYER++;
                                            if (Switcher.CAMERA_2_PLAYER > 0) {
                                                Switcher.CAMERA_2_PLAYER = 0;
                                            }
                                        } else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {

                                            if (!Keyboard.getEventKeyState()) {
                                                Switcher.CAMERA_2_PLAYER--;
                                                if (Switcher.CAMERA_2_PLAYER < -10) {
                                                    Switcher.CAMERA_2_PLAYER = -10;
                                                }
                                            }
                                            double timenow = GLApp.getTimeInSeconds();
                                            if ((timenow - preKeyTime) < 1) {
                                                return;
                                            }
                                            preKeyTime = timenow;


                                        } */
        // tilt up
         /*else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                                                {
                                                    double timenow = GLApp.getTimeInSeconds();

                                                    if ((timenow - preKeyTime) < 1) {
                                                        return;
                                                    }
                                                    preKeyTime = timenow;
                                                    CoreRegistry.get(PauseMenu.class).show();
                                                }
                                            }*/
    }

    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */
    //基本上不用了
//    public void mouseUp(int x, int y) {
//        // add mouse motion to line if left button is down, and mouse has moved
//        // more than 10 pixels
//        // System.out.println("1");
//        prevMouseX = 0;
//        prevMouseY = 0;
//
//    }

    private void mouseLClick(int x, int y) {


        long now =TimeUtil.getNowMills();
        if(now-player.getLastAttackTime()>1000){
            LivingThing livingThing = null;
            //如果当前目标距离人物小于多少的距离
            if(player.getTarget()!=null){
                if(player.getPosition().copyClone().sub(player.getTarget().getPosition()).length()<2){
                    livingThing = (LivingThing)player.getTarget();
                }
            }else{
                livingThing = livingThingManager.chooseObject(player.getPosition(), player.getWalkDir());
            }
            if(livingThing!=null) {
                player.setTarget(livingThing);
                CoreRegistry.get(Client.class).send(new AttackCmd(player.getId(),player.getMainWeapon()== ItemType.arch.id ?AttackType.ARROW:AttackType.KAN, livingThing.getId()));

                //后退
                //CoreRegistry.get(Client.class).send(new JumpCmd(livingThing.getPosition(),player.walkDir,livingThing.getId(),1f));

            }
            try {
                CoreRegistry.get(Client.class).send(new AttackCmd(player.getId(), player.getMainWeapon() == ItemType.arch.id ? AttackType.ARROW : AttackType.KAN, 0));
            }catch(Exception e){
               e.printStackTrace();
            }

        }
        startX = x;
        startY =Constants.WINDOW_HEIGHT- y ;
        if(Switcher.edit){
            GamingState.editEngine.prevX = x;
            GamingState.editEngine.prevY = y;
            GamingState.editEngine.mouseClick(x,y);

            if(Switcher.mouseState ==Switcher.boxSelectMode){


            GamingState.selectDiv.setVisible(true);
            GamingState.selectDiv.setLeft(x);
            GamingState.selectDiv.setTop((int)startY);

            }
        }
        Switcher.MOUSE_CANCELBUBLE = false;
    /*    AnimationManager manager = CoreRegistry.get(AnimationManager.class);
        manager.apply(human.bodyComponent,"walkerFoward");*/

//        CoreRegistry.get(Bag.class).click(x, y);

        if (Switcher.MOUSE_CANCELBUBLE)
            return;
        //  CoreRegistry.get(PauseMenu.class).click(x, y);


//       System.out.println("x:" + x + "y:" + y);
//                                    /* GL_Vector from =camera.Position; */
//        System.out.printf("mouse clikc at  %d %d \r\n ", x, y);


        //GL_Vector viewDir = OpenglUtils.getLookAtDirection2(camera.getViewDir(),x, y);
        //viewDir.y=-viewDir.y;
//        // this.human.viewDir;//OpenglUtil.getLookAtDirection(x, y);
//
//        // System.out.printf("OpenglUtil getLookAtDirection %f %f %f \r\n ",
//        // viewDir.x,viewDir.y,viewDir.z);
//


//        GL_Vector to = GL_Vector.add(camera.Position,
//                GL_Vector.multiply(viewDir, 100));
//
//       /* GamingState.instance.lightPos.x= to.x;
//        GamingState.instance.lightPos.y= to.y;
//        GamingState.instance.lightPos.z= to.z;
//        GamingState.lightPosChanged=true;*/
//        //this.engine.lineStart = camera.Position;
//        //this.engine.mouseEnd = to;
//        //获取客户端的方块管理器
//        ChunkProvider localChunkProvider = CoreRegistry
//                .get(ChunkProvider.class);
//        GL_Vector hitPoint = bulletPhysics.rayTrace(new GL_Vector(player.getPosition().x,player.getPosition().y+2,player.getPosition().z), viewDir,
//                20, "soil", true);
        // camera.getviewDir().add();
        LivingThing livingThing = livingThingManager.chooseObject(x, Constants.WINDOW_HEIGHT-y);
        
        if(livingThing !=null ){
            player.setTarget(livingThing);
            Document.needUpdate=true;
            LogUtil.println(livingThing.getId()+"");
            CoreRegistry.get(HeadPanel.class).bind(livingThing).show();
        }
      //  livingThingManager.chooseObject(camera.Position, camera.getViewDir());
        
    

        //选中一个colorblock 作为当前的block
       /* ChunkProvider localChunkProvider = CoreRegistry
                .get(ChunkProvider.class);
        boolean delete = true;
        //获取当前的block item
        //
        BulletResultDTO arr  = bulletPhysics.rayTrace(camera.Position.copyClone(), camera.getViewDir().copyClone(),
                104, "soil", delete);
        if(arr!=null && arr.targetBlock!=null){
            if(arr.targetBlock instanceof ColorBlock){
                ((ColorBlock) arr.targetBlock).green =1 ;
                AttackManager.selectThing = new ColorBlock(arr.targetChunX*16+(int)arr.targetPoint.x,(int)arr.targetPoint.y,arr.targetChunZ*16+(int)arr.targetPoint.z);
                LogUtil.println(arr.targetPoint+"");
            }
        }*/
        //livingThingManager.attack();
       /*Ball ball =new Ball(this.camera.Position,viewDir,17.3f, TextureManager.getShape("arrow"));

        AttackManager.add(ball);*/
        //  20);
        /*if (hitPoint != null) {
            Block block=new
                    BaseBlock("soil",(int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z);
                                          if("wood".equals(engine.currentObject)){ block =new
                                          Wood((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
                                          if("glass".equals(engine.currentObject)){ block =new
                                          Glass((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
                                          if("soil".equals(engine.currentObject)){ block =new
                                          Soil((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
                                          if("water".equals(engine.currentObject)){ block =new
                                          Water((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }
                                          if(block!=null){ bulletPhysics.blockRepository.put(block);
                                          this.engine.blockRepository.reBuild(engine.currentObject); }

        }*/
        //CoreRegistry.get(Bag.class).click(x, y);
        // CoreRegistry.get(PauseMenu.class).click(x, y);
    }

//    public void mouseRClick(int x, int y) {
//
//        LogUtil.println("x:" + x + "y:" + y);
//                                    /* GL_Vector from =camera.Position; */
//        LogUtil.println("mouse clikc at  %d %d \r\n ", x, y);
//       // System.out.printf("mouse clikc at  %d %d \r\n ", x, y);
//        GL_Vector viewDir = OpenglUtils.getLookAtDirection(x, y);
//        // this.human.viewDir;//OpenglUtil.getLookAtDirection(x, y);
//
//        // System.out.printf("OpenglUtil getLookAtDirection %f %f %f \r\n ",
//        // viewDir.x,viewDir.y,viewDir.z);
//
//        GL_Vector to = GL_Vector.add(camera.Position,
//                GL_Vector.multiply(viewDir, 10));
//       // this.engine.lineStart = camera.Position;
//       // this.engine.mouseEnd = to;
//        ChunkProvider localChunkProvider = CoreRegistry
//                .get(ChunkProvider.class);
//        GL_Vector hitPoint = bulletPhysics.rayTrace(camera.Position, viewDir,
//                20, StartMenuState.catchThing, true);
//        if (hitPoint != null) {
//            // Block block=new
//            // BaseBlock(engine.currentObject,(int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z);
//                                        /*
//                                         * if("wood".equals(engine.currentObject)){ block =new
//                                         * Wood((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
//                                         * if("glass".equals(engine.currentObject)){ block =new
//                                         * Glass((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
//                                         * if("soil".equals(engine.currentObject)){ block =new
//                                         * Soil((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
//                                         * if("water".equals(engine.currentObject)){ block =new
//                                         * Water((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }
//                                         */
//                                        /*
//                                         * if(block!=null){ bulletPhysics.blockRepository.put(block);
//                                         * this.engine.blockRepository.reBuild(engine.currentObject); }
//                                         */
//
//        }
////        CoreRegistry.get(Bag.class).click(x, y);
//
//
//    }

    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */

//    public void mouseDown(int x, int y) {/*
//                                                                     * // add mouse motion to line if left
//                                                                     * button is down, and mouse has moved
//                                                                     * // more than 10 pixels //
//                                                                     * System.out.println("1"); lineStart.x
//                                                                     * = cam.camera.Position.x; lineStart.y
//                                                                     * = cam.camera.Position.y; lineStart.z
//                                                                     * = cam.camera.Position.z; // //
//                                                                     * lineStart.x =human.viewDir.x; //
//                                                                     * lineStart.y =human.viewDir.y; //
//                                                                     * lineStart.z = human.viewDir.z;
//                                                                     *
//                                                                     * prevMouseX = x; prevMouseY = y;
//                                                                     *
//                                                                     * mouseEnd = GL_Vector.add(lineStart,
//                                                                     * GL_Vector
//                                                                     * .multiply(getLookAtDirection(x, y),
//                                                                     * 35));
//                                                                     *
//                                                                     * // System.out.printf("%f %f %f \r\n"
//                                                                     * + "", mouseDir.x, mouseDir.y, //
//                                                                     * mouseDir.z);
//                                                                     */
//        mouseLeftPressed=true;
//    }
    public void handleMouseWheel(int times){
        Switcher.CAMERA_2_PLAYER+=times;
        if (Switcher.CAMERA_2_PLAYER < 0) {
            Switcher.CAMERA_2_PLAYER = 0;
        }else
        if (Switcher.CAMERA_2_PLAYER > 500) {
            Switcher.CAMERA_2_PLAYER = 500;
        }
        GamingState.setCameraChanged(true);

    }

    /**
     * gaming state 调用
     * @param x
     * @param y
     */
    public void mouseLeftDown(int x, int y) {
        prevMouseX=x;
        prevMouseY=y;
        mouseLClick(x, y);
        /*if(Switcher.edit){
            GamingState.editEngine.mouseClick(x, y);
        }*/
        mouseLeftPressed=true;
//        LogUtil.println("mouseLeftDown");
    }

    /**
     * gaming state 调用
     * @param x
     * @param y
     */
    public void mouseLeftUp(int x, int y) {
        mouseLeftPressed=false;
        if(Switcher.edit){
           // GamingState.selectDiv.setVisible(false);
         /*   GamingState.editEngine.selectObject(GamingState.selectDiv.getLeft(),GamingState.selectDiv.getTop(),
                    GamingState.selectDiv.getLeft()+ GamingState.selectDiv.getWidth(),GamingState.selectDiv.getTop()+GamingState.selectDiv.getHeight());
*/


                GamingState.editEngine.mouseUp(x,y);


            if(Switcher.mouseState == Switcher.boxSelectMode){
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    GamingState.editEngine.selectMany(   prevMouseX,
                            Constants. WINDOW_HEIGHT-prevMouseY,
                            x,Constants.WINDOW_HEIGHT-y,false);
                }else
                GamingState.editEngine.selectMany(   prevMouseX,
                        Constants. WINDOW_HEIGHT-prevMouseY,
                        x,Constants.WINDOW_HEIGHT-y,true);

            }else if(Switcher.mouseState  == Switcher.shootMode){
             /*   GamingState.editEngine.shootBlock(prevMouseX,
                        Constants. WINDOW_HEIGHT-prevMouseY);*/
            }else if(Switcher.mouseState  == Switcher.brushMode){
                    GamingState.editEngine.brushBlock(prevMouseX,
                    Constants. WINDOW_HEIGHT-prevMouseY);
            }
            else if(Switcher.mouseState  == Switcher.textureMode){
                GamingState.editEngine.brushImageOnBlock(prevMouseX,
                        Constants. WINDOW_HEIGHT-prevMouseY);
            }

            //如果都没选中 开启单选模式
            /*if(GamingState.editEngine.selectBlockList.size()==0){
                GamingState.editEngine.chooseObject(GamingState.instance.camera.Position,GamingState.instance.camera.getViewDir());
            }*/
        }
    }
    /**
     * gaming state 调用
     * @param x
     * @param y
     */
    public void mouseRightDown(int x, int y) {
        mouseRightPressed=true;
        prevMouseX=x;
        prevMouseY=y;
        
        //选中xz平面
        
        GL_Vector from = GamingState.instance.camera.Position;
        GL_Vector viewDir =  OpenglUtils.getLookAtDirectionInvert(GamingState.instance.camera.getViewDir().copyClone(),x,y);

        float weizhi = -from.y/viewDir.y;
       int  curentX=(int)(from.x+weizhi*viewDir.x);
        int curentZ= (int)(from.z+weizhi*viewDir.z);
        if(!Switcher.edit){
            GamingState.instance.player.setDest(new GL_Vector(curentX,0.1f,curentZ));
        }

        
    }
    /**
     * gaming state 调用
     * @param x
     * @param y
     */
    public void mouseRightUp(int x, int y) {
        mouseRightPressed=false;
    }

    /**
     * gaming state 先调用 每次都更新
     * @param x
     * @param y
     */
    public void mouseMove(int x, int y) {
        if(Switcher.edit){
           GamingState.editEngine.mouseMove(x,y);
        }
        if(mouseRightPressed){
            this.mouseRightDrag(x,y);
           /* camera.viewDir.x= human.viewDir.x;
            camera.viewDir.y= human.viewDir.y;
            camera.viewDir.z= human.viewDir.z;*/
        }
        if(mouseLeftPressed){
            this.mouseLeftDrag(x,y);
        }

        // add mouse motion to line if left button is down, and mouse has moved
        // more than 10 pixels
        // add a segment to the line
        // /System.out.println("����ת��");
        // System.out.println(x-prevMouseX);
        // if(!canDetectMove){
        // if (MathUtil. distance(400, 300, x, y)< 10f){
        // canDetectMove=true;
        // }
        // }else
       /* mousepoint = MouseInfo.getPointerInfo().getLocation();
        if (MathUtil.distance(centerX, centerY, mousepoint.x, mousepoint.y) > 10f) {
            // System.out.println("now mouse position x:"+x+" y :"+y);


            if (Switcher.MOUSE_AUTO_CENTER) {
                human.headRotate((float) (-(mousepoint.x - centerX)
                        * 4 * GLApp.getSecondsPerFrame()), (float) (-(mousepoint.y - centerY)
                        * 4 * GLApp.getSecondsPerFrame()));
                // System.out.printf("y distance: %d \r\n",(y-prevMouseY));
                // human.RotateX();

                //robot.mouseMove(mousepoint.x-(x-400), mousepoint.y-(y-300));
                // robot.mouseMove(Display.getX()+400, Display.getY()+300);
                robot.mouseMove((int) centerX, (int) centerY);
                // System.out.println("move to position x :"+(mousepoint.x-(x-400))+" y :"+(mousepoint.y-(y-300)));

            }

            canDetectMove = false;
            // System.out.println("move x distance:"+(-x +
            // centerX)+" y distance:"+(y - centerY));
        }
        CoreRegistry.get(Bag.class).move(x, y);*/

    }

//    public void mouseMove(int x, int y,StartMenuState state) {
//        if(mouseRightPressed){
//            this.mouseRightDrag(x,y);
//
//        }
//        if(mouseLeftPressed){
//            this.mouseLeftDrag(x,y);
//
//
//        }
//
//        // add mouse motion to line if left button is down, and mouse has moved
//        // more than 10 pixels
//        // add a segment to the line
//        // /System.out.println("����ת��");
//        // System.out.println(x-prevMouseX);
//        // if(!canDetectMove){
//        // if (MathUtil. distance(400, 300, x, y)< 10f){
//        // canDetectMove=true;
//        // }
//        // }else
//       /* mousepoint = MouseInfo.getPointerInfo().getLocation();
//        if (MathUtil.distance(centerX, centerY, mousepoint.x, mousepoint.y) > 10f) {
//            // System.out.println("now mouse position x:"+x+" y :"+y);
//
//
//            if (Switcher.MOUSE_AUTO_CENTER) {
//                human.headRotate((float) (-(mousepoint.x - centerX)
//                        * 4 * GLApp.getSecondsPerFrame()), (float) (-(mousepoint.y - centerY)
//                        * 4 * GLApp.getSecondsPerFrame()));
//                // System.out.printf("y distance: %d \r\n",(y-prevMouseY));
//                // human.RotateX();
//
//                //robot.mouseMove(mousepoint.x-(x-400), mousepoint.y-(y-300));
//                // robot.mouseMove(Display.getX()+400, Display.getY()+300);
//                robot.mouseMove((int) centerX, (int) centerY);
//                // System.out.println("move to position x :"+(mousepoint.x-(x-400))+" y :"+(mousepoint.y-(y-300)));
//
//            }
//
//            canDetectMove = false;
//            // System.out.println("move x distance:"+(-x +
//            // centerX)+" y distance:"+(y - centerY));
//        }
//        CoreRegistry.get(Bag.class).move(x, y);*/
//
//    }

    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */
    private void mouseRightDrag(int x, int y) {

        //视角移动
        // add mouse motion to line if left button is down, and mouse has moved
        // more than 10 pixels
        if (MathUtil.distance(prevMouseX, prevMouseY, x, y) > DRAG_DIST
                && MathUtil.distance(prevMouseX, prevMouseY, x, y) < 200000f) {
            // add a segment to the line
            // /System.out.println("����ת��");
            // System.out.println(x-prevMouseX);
            player.bodyRotate( -(x - prevMouseX)/2,(y - prevMouseY)/2);
            // System.out.printf("y distance: %d \r\n",(y-prevMouseY));
            //human.RotateX(-(y - prevMouseY) / 5);
            camera.fenli = false;
            // regenerate the line
            // save mouse positionf
            prevMouseX = x;
            prevMouseY = y;
            camera.ViewDir.x= player.viewDir.x;
            camera.ViewDir.y= player.viewDir.y;
            camera.ViewDir.z= player.viewDir.z;
            GamingState.setCameraChanged(true);
           // camera.changeCallBack();
            // �ƶ���ͷ
        }
    }

    private void mouseLeftDrag(int x, int y) {
        if(!Switcher.edit) {

            if (Math.abs(x - prevMouseX) > DRAG_DIST ||
                    Math.abs(y - prevMouseY) > DRAG_DIST) {
      /*  if (MathUtil.distance(prevMouseX, prevMouseY, x, y) > 0.1f
                && MathUtil.distance(prevMouseX, prevMouseY, x, y) < 2000) {*/
                // add a segment to the line
                // System.out.println("ͷ��ת��");
                // System.out.println(x-prevMouseX);
                camera.fenli = true;

                player.headRotate(-(x - prevMouseX), (y - prevMouseY));
                // camera.RotateX(-(x - prevMouseX) / 5);
                // System.out.printf("y distance: %d \r\n",(y-prevMouseY));
                //camera.RotateY((y - prevMouseY) / 5);

                // regenerate the line
                // save mouse position
                prevMouseX = x;
                prevMouseY = y;
                camera.ViewDir.x = player.viewDir.x;
                camera.ViewDir.y = player.viewDir.y;
                camera.ViewDir.z = player.viewDir.z;
                GamingState.setCameraChanged(true);
                //camera.changeCallBack();
                // �ƶ���ͷ
            }
        }else{
            GamingState.editEngine.mouseDrag(x,Constants.WINDOW_HEIGHT-y);
            //拖出一个矩形
            if(Switcher.mouseState == Switcher.boxSelectMode) {
                y = Constants.WINDOW_HEIGHT - y;
                GamingState.selectDiv.setWidth(x - (int) startX);
                GamingState.selectDiv.setHeight(y - (int) startY);
                Document.needUpdate =true;
            }else if(Switcher.mouseState == Switcher.faceSelectMode){
              //  GamingState.editEngine.faceSelect(x,y);
                GamingState.editEngine.mouseMove(x,y);
            }
          //  LogUtil.println("nowX:"+x+"startX"+startX);
           // LogUtil.println("nowY:"+y+"starty"+startY);



        }
    }
    float startX;
    float startY;
    /*
     不响应连续按键
     gaming state
     */
    public void keyDown(int keycode) {
        long now =TimeUtil.getNowMills();
        if (Keyboard.isKeyDown( Keyboard.KEY_G)) {
           /* if(player.getTarget()!=null){
                CoreRegistry.get(Client.class).send(new AttackCmd(player.getId(),player.getMainWeapon()== ItemType.arch ?AttackType.ARROW:AttackType.KAN, player.getTarget().getId()));
            }else*/{
                //选取当前人面前的目标
                if(now-player.getLastAttackTime()>1000){
                    LivingThing livingThing = null;
                    //如果当前目标距离人物小于多少的距离
                    if(player.getTarget()!=null){
                        if(player.getPosition().copyClone().sub(player.getTarget().getPosition()).length()<2){
                            livingThing = (LivingThing)player.getTarget();
                        }
                    }else{
                        livingThing = livingThingManager.chooseObject(player.getPosition(), player.getWalkDir());
                    }
                    if(livingThing!=null) {
                        player.setTarget(livingThing);
                        CoreRegistry.get(Client.class).send(new AttackCmd(player.getId(),player.getMainWeapon()== ItemType.arch.id ?AttackType.ARROW:AttackType.KAN, livingThing.getId()));

                        //后退
                        //CoreRegistry.get(Client.class).send(new JumpCmd(livingThing.getPosition(),player.walkDir,livingThing.getId(),1f));

                    }
                    try {
                        CoreRegistry.get(Client.class).send(new AttackCmd(player.getId(), player.getMainWeapon() == ItemType.arch.id ? AttackType.ARROW : AttackType.KAN, 0));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
               }

            if(player.getMainWeapon()==null ) {
                //TODO 如果使用了武器 比如射箭 就不用再消除方块了
                GL_Vector to = GL_Vector.add(camera.Position,
                        GL_Vector.multiply(camera.getViewDir(), 100));

       /* GamingState.instance.lightPos.x= to.x;
        GamingState.instance.lightPos.y= to.y;
        GamingState.instance.lightPos.z= to.z;
        GamingState.lightPosChanged=true;*/
                //this.engine.lineStart = camera.Position;
                //this.engine.mouseEnd = to;
                //获取客户端的方块管理器
                //TODO 消除方块 的距离太远了 改成了4
                ChunkProvider localChunkProvider = CoreRegistry
                        .get(ChunkProvider.class);
                boolean delete = true;
                //获取当前的block item
                //
                BulletResultDTO arr = bulletPhysics.rayTrace(new GL_Vector(player.getPosition().x, player.getPosition().y + 2, player.getPosition().z), camera.getViewDir(),
                        4, "soil", delete);


                if (arr != null) {

                    GL_Vector hitPoint = arr.targetPoint;
                    //打印点
                    //获得朝向

                    //获得靠近还是靠远
                    if (hitPoint == null) {
                        return;
                    }
                    LogUtil.println("x:" + hitPoint.x % 1 + "y:" + hitPoint.y % 1 + "z:" + hitPoint.z % 1);

           /*     if(arr.targetBlock.getId()>ItemType.wood_door.ordinal()){
                    arr.targetBlock.beAttack();
                    return ;
                }*/
                    //获得上一层还是下一层

                    //其实我就是想知道点击的是哪一个面上 点击的面上
                    //得出当前人手上拿的是不是方块
                    int chunkX = arr.targetChunX;
                    int chunkZ = arr.targetChunZ;
                    //   TreeBlock treeBlock =new TreeBlock(hitPoint);
                    //treeBlock.startPosition=hitPoint;
                    //  treeBlock.generator();

                    // int blockX = MathUtil.floor(hitPoint.x) - chunkX * 16;
                    // int blockY = MathUtil.floor(hitPoint.y);
                    // int blockZ = MathUtil.floor(hitPoint.z) - chunkZ * 16;
                    ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(chunkX, 0, chunkZ));
                    cmd.cx = (int) hitPoint.x;
                    cmd.cy = (int) hitPoint.y;
                    cmd.cz = (int) hitPoint.z;

                    if (cmd.cy < 0) {
                        LogUtil.err("y can't be <0 ");
                    }
                    cmd.type = delete ? 2 : 1;
                    //blockType 应该和IteType类型联系起来
                    cmd.blockType = 0;

                    CoreRegistry.get(Client.class).send(cmd);
                }
            }

           // Client.messages.push(new AttackCmd(AttackType.ARROW));
            //player.receive(new AttackCmd(AttackType.ARROW));
        }
        if(Keyboard.isKeyDown( Keyboard.KEY_H)){
//            camera.setPosition(player.getPosition());
//            camera.setViewDir(player.getViewDir());
            Switcher.CAMERA_2_PLAYER=0;
            GamingState.setCameraChanged(true);
        }

        if (Keyboard.isKeyDown( Keyboard.KEY_V)) {

            if(AttackManager.selectThing!=null){
                 ChunkProvider chunkProvider = CoreRegistry.get(ChunkProvider.class);
                //Chunk chunk = chunkProvider.getChunk(AttackManager.selectThing.chunkX, 0 ,AttackManager.selectThing.chun );
                List<ColorBlock> list =new ArrayList<>();
                ItemDefinition  handItem = ItemManager.getItemDefinition(player.getHandEquip());

                int chunkX = MathUtil.getBelongChunkInt(AttackManager.selectThing.getX());
                int chunkZ = MathUtil.getBelongChunkInt(AttackManager.selectThing.getZ());
                Chunk chunk  =  chunkProvider.getChunk(chunkX, 0, chunkZ);
                for(int x =AttackManager.selectThing.getX() ;x<AttackManager.selectThing.getX()+AttackManager.selectThing.width;x++){
                    for(int y =AttackManager.selectThing.getY() ;y<AttackManager.selectThing.getY()+AttackManager.selectThing.height;y++){
                        for(int z =AttackManager.selectThing.getZ() ;z<AttackManager.selectThing.getZ()+AttackManager.selectThing.thick;z++){
                            //list.add()
                            int _chunkX = MathUtil.getBelongChunkInt(x);
                            int _chunkZ = MathUtil.getBelongChunkInt(z);
                            if(_chunkX!= chunkX || _chunkZ != chunkZ){
                                chunkX = _chunkX;
                                chunkZ=_chunkZ;
                                chunk  =  chunkProvider.getChunk(chunkX, 0, chunkZ);
                            }
                            //Chunk chunk  =  chunkProvider.getChunk(MathUtil.getBelongChunkInt(x), 0, MathUtil.getBelongChunkInt(z));



                            chunk.setBlock(MathUtil.getOffesetChunk(x), y, MathUtil.getOffesetChunk(z),handItem.getItemType());
                        }
                    }
                }
                chunk.build();
                AttackManager.selectThing=null;
                return;
            }
            GL_Vector to = GL_Vector.add(camera.Position,
                    GL_Vector.multiply(camera.getViewDir(), 100));

       /* GamingState.instance.lightPos.x= to.x;
        GamingState.instance.lightPos.y= to.y;
        GamingState.instance.lightPos.z= to.z;
        GamingState.lightPosChanged=true;*/
            //this.engine.lineStart = camera.Position;
            //this.engine.mouseEnd = to;
            //获取客户端的方块管理器
            ChunkProvider localChunkProvider = CoreRegistry
                    .get(ChunkProvider.class);
            boolean delete = false;
            //获取当前的block item
            ItemDefinition  handItem = ItemManager.getItemDefinition(player.getHandEquip());
            //如果当前手上有拿block 就是放置的动作 如果没有 就是拆方块的节奏
            if(handItem!=null && handItem.getType() == ItemMainType.BLOCK  ){
                delete=false;
            }else {
                delete=true;

            }

            BulletResultDTO arr
             = bulletPhysics.rayTrace(new GL_Vector(player.getPosition().x, player.getPosition().y + 2, player.getPosition().z), camera.getViewDir(),
                    20, "soil", delete);



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
                //获得上一层还是下一层
                if(handItem==null){
                    return;
                }

               // int condition = BlockUtil.getIndex(placePoint, camera.getViewDir());
                handItem.use(placePoint,handItem.getItemType(),camera.getViewDir());
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
        }else
        if (Keyboard.isKeyDown( Keyboard.KEY_T)) {
            if(player.getItemBeans()[24]!=null){
                CoreRegistry.get(Client.class).send(new DropCmd(player.getId(),player.getItemBeans()[24].getId()));
            }


            // Client.messages.push(new AttackCmd(AttackType.ARROW));
            //player.receive(new AttackCmd(AttackType.ARROW));
        }
                                       /* if(Keyboard.isRepeatEvent()){
                                            LogUtil.println("重复按键"+Keyboard.getKeyName(keycode));
                                        }
                                          */  //dosn't need cooling
        // w a s d space
        //i think it's something  could be repeat something only receive down up as a signal;

        //let put the key in a group that need cooling

                                            /*if (keycode == Keyboard.KEY_1) {
                                                CoreRegistry.get(ToolBar.class).keyDown(1);
                                            } else if (keycode == Keyboard.KEY_2) {
                                                CoreRegistry.get(ToolBar.class).keyDown(2);
                                            } else if (keycode == Keyboard.KEY_3) {
                                                CoreRegistry.get(ToolBar.class).keyDown(3);
                                            } else if (keycode == Keyboard.KEY_4) {
                                                CoreRegistry.get(ToolBar.class).keyDown(4);
                                            } else if (keycode == Keyboard.KEY_5) {
                                                CoreRegistry.get(ToolBar.class).keyDown(5);
                                            } else if (keycode == Keyboard.KEY_6) {
                                                CoreRegistry.get(ToolBar.class).keyDown(6);
                                            } else if (keycode == Keyboard.KEY_7) {
                                                CoreRegistry.get(ToolBar.class).keyDown(7);
                                            } else if (keycode == Keyboard.KEY_8) {
                                                CoreRegistry.get(ToolBar.class).keyDown(8);
                                            } else if (keycode == Keyboard.KEY_9) {
                                                CoreRegistry.get(ToolBar.class).keyDown(9);
                                            }*/

        if (Keyboard.isKeyDown( Keyboard.KEY_F3)) {
            Switcher.PRINT_SWITCH = !Switcher.PRINT_SWITCH;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
            // CoreRegistry.get(Bag.class).changeShow();
        } else if (Keyboard.isKeyDown( Keyboard.KEY_P)) {
          /*  Switcher.CAMERA_2_PLAYER++;
            if (Switcher.CAMERA_2_PLAYER > 10) {
                Switcher.CAMERA_2_PLAYER = 10;
            }*/
        } else if (Keyboard.isKeyDown( Keyboard.KEY_O)) {
           /* Switcher.CAMERA_2_PLAYER--;
            if (Switcher.CAMERA_2_PLAYER < 0) {
                Switcher.CAMERA_2_PLAYER = 0;
            }*/
        } else if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
            Switcher.IS_GOD = !Switcher.IS_GOD;
            System.out.println("god mode:" + Switcher.IS_GOD);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            // CoreRegistry.get(PauseMenu.class).show();
            livingThingManager.chooseObject(null);
            CoreRegistry.get(BoxPanel.class).setVisible(false);
            Switcher.isChat=false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_F5)) {
            Switcher.CAMERA_MODEL++;
            if (Switcher.CAMERA_MODEL > 2) {
                Switcher.CAMERA_MODEL = 0;
            }
            if (Switcher.CAMERA_MODEL == 0) {
                Switcher.CAMERA_2_PLAYER = 0;
            } else {
                Switcher.CAMERA_2_PLAYER = 10;
            }
        }
        int seconds = 100;

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
           // human.headRotate(0, -human.camSpeedXZ * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
           // human.headRotate(human.camSpeedXZ * seconds, 0);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
           // human.bodyRotate(0, human.camSpeedR * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {// Turn right
          //  System.out.println("key_e");
         //   human.bodyRotate(0, -human.camSpeedR * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            //human.StrafeRight(-human.camSpeedXZ * seconds);
            //LogUtil.println("walk left");
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // Pan right
            //human.StrafeRight(human.camSpeedXZ * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // tilt down
           // human.MoveForward(-human.camSpeedXZ * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // tilt up

            //human.MoveForward(human.camSpeedXZ * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
         /*   player.position.y = player.position.y - 3 * seconds;
            player.move(player.position);*/
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            player.position.y = player.position.y + 3 * seconds;
            player.move(player.position);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
            // CoreRegistry.get(ToolBar.class).keyDown(1);
            LogUtil.println(player.position.toString());
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            CoreRegistry.get(ToolBarView.class).keyDown(1);
        }   else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
        CoreRegistry.get(ToolBarView.class).keyDown(2);
    }
    else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
        CoreRegistry.get(ToolBarView.class).keyDown(3);
    } else if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
        CoreRegistry.get(ToolBarView.class).keyDown(4);
    } else if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
        CoreRegistry.get(ToolBarView.class).keyDown(5);
    } else if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
        CoreRegistry.get(ToolBarView.class).keyDown(6);
    } else if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
        CoreRegistry.get(ToolBarView.class).keyDown(7);
    } else if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
        CoreRegistry.get(ToolBarView.class).keyDown(8);
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
        CoreRegistry.get(ToolBarView.class).keyDown(9);
    }
        /*else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            CoreRegistry.get(ToolBar.class).keyDown(1);
      else if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
            double timenow = GLApp.getTimeInSeconds();

            if ((timenow - preKeyTime) < 500) {
                return;
            }
            preKeyTime = timenow;


            Switcher.PRINT_SWITCH = !Switcher.PRINT_SWITCH;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
            double timenow = GLApp.getTimeInSeconds();

            if ((timenow - preKeyTime) < 1) {
                return;
            }
            System.out.println("pretime:"
                    + preKeyTime + " time:" + timenow + " seconds:" + seconds);
            preKeyTime = timenow;
            Switcher.IS_GOD = !Switcher.IS_GOD;
            System.out.println("god mode:" + Switcher.IS_GOD);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_B)) {

            double timenow = GLApp.getTimeInSeconds();
            if ((timenow - preKeyTime) < 1) {
                return;
            }
            preKeyTime = timenow;
            CoreRegistry.get(Bag.class).changeShow();
       else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
double timenow = GLApp.getTimeInSeconds();
if ((timenow - preKeyTime) < 1) {
return;
}
preKeyTime = timenow;
Switcher.CAMERA_2_PLAYER++;
if (Switcher.CAMERA_2_PLAYER > 0) {
Switcher.CAMERA_2_PLAYER = 0;
}
} else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {

if (!Keyboard.getEventKeyState()) {
Switcher.CAMERA_2_PLAYER--;
if (Switcher.CAMERA_2_PLAYER < -10) {
    Switcher.CAMERA_2_PLAYER = -10;
}
}
double timenow = GLApp.getTimeInSeconds();
if ((timenow - preKeyTime) < 1) {
return;
}
preKeyTime = timenow;


} */
        // tilt up

        //this code will cause when input word in chat panel then the human will jump
       /* if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            double timenow = GLApp.getTimeInSeconds();

            if ((timenow - preKeyTime) < 1) {
                return;
            }
            preKeyTime = timenow;
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                player.jumpHigh();
            // System.out.println("ͬʱ������w��");
            player.jump();
            // System.out.println("jump");
        }*/

                                        /*
                                         * if (Keyboard.KEY_SPACE == keycode) { cam.setCamera((cam.camera ==
                                         * camera1)? camera2 : camera1); }
                                         */
    }

    public void keyUp(int keycode) {


    }


}
