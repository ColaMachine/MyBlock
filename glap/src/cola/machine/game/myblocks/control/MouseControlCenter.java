package cola.machine.game.myblocks.control;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.modes.StartMenuState;
import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import core.log.LogUtil;
import cola.machine.game.myblocks.model.ui.tool.ToolBar;
import com.dozenx.game.engine.command.AttackCmd;
import com.dozenx.game.engine.command.AttackType;
import com.dozenx.game.opengl.util.OpenglUtils;
import glapp.GLApp;
import glapp.GLCamera;
import glmodel.GL_Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;

import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.util.MathUtil;

public class MouseControlCenter {
    public Human human;
    public GLCamera camera;
    //public MyBlockEngine engine;
    public double preKeyTime = 0;
    public Robot robot;
    public float centerX = 0;
    public float centerY = 0;
    public LivingThingManager livingThingManager;

    public float prevMouseX = 0;
    public float prevMouseY = 0;
    public boolean canDetectMove = true;
    public BulletPhysics bulletPhysics;
    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */
    Point mousepoint;
    public MouseControlCenter(Human human, GLCamera camera,GameState gameState) {
        this( human,  camera) ;
        this.gameState=gameState;
    }

    public MouseControlCenter(Human human, GLCamera camera) {
        //this.engine = engine;
        this.human = human;
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

    public void handleNavKeys(float seconds) {
        //dosn't need cooling
        // w a s d space
        //i think it's something  could be repeat something only receive down up as a signal;

        //let put the key in a group that need cooling
        //LogUtil.println(seconds+"");
        if( Switcher.isChat){
            return;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
           // human.headRotate(0, -human.camSpeedXZ * seconds*100);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
           // human.headRotate(-human.camSpeedXZ * seconds*100,0 );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            livingThingManager.chooseObject(null);
            // human.headRotate(-human.camSpeedXZ * seconds*100,0 );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            //human.headRotate(human.camSpeedXZ * seconds*100,0 );
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            //human.headRotate(0, human.camSpeedXZ * seconds*100);
        }  else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            Switcher.CAMERA_2_PLAYER++;
            if (Switcher.CAMERA_2_PLAYER > 200) {
                Switcher.CAMERA_2_PLAYER = 200;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            Switcher.CAMERA_2_PLAYER--;
            if (Switcher.CAMERA_2_PLAYER < 0) {
                Switcher.CAMERA_2_PLAYER = 0;
            }
        } if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            human.bodyRotate(human.camSpeedR * seconds,0);
        }   if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            // Turn right

            human.bodyRotate( -human.camSpeedR * seconds,0);
        }  if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            human.StrafeRight(-human.camSpeedXZ * seconds);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // Pan right
            human.StrafeRight(human.camSpeedXZ * seconds);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // tilt down
            human.MoveForward(-human.camSpeedXZ * seconds);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) { // tilt down
            //human.MoveForward(-human.camSpeedXZ * seconds);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {   // tilt up
            human.MoveForward(human.camSpeedXZ * seconds);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            human.position.y = human.position.y - 3 * seconds;
            human.move(human.position);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            human.position.y = human.position.y + 3 * seconds;
            human.move(human.position);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            double timenow = GLApp.getTimeInSeconds();

            if ((timenow - preKeyTime) < 1) {
                return;
            }
            preKeyTime = timenow;
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                human.jumpHigh();
            // System.out.println("ͬʱ������w��");
            human.jump();
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
    public void mouseUp(int x, int y) {
        // add mouse motion to line if left button is down, and mouse has moved
        // more than 10 pixels
        // System.out.println("1");
        prevMouseX = 0;
        prevMouseY = 0;

    }

    public void mouseLClick(int x, int y) {
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


        GL_Vector viewdir = OpenglUtils.getLookAtDirection2(camera.getViewDir(),x, y);
        //viewdir.y=-viewdir.y;
//        // this.human.ViewDir;//OpenglUtil.getLookAtDirection(x, y);
//
//        // System.out.printf("OpenglUtil getLookAtDirection %f %f %f \r\n ",
//        // viewdir.x,viewdir.y,viewdir.z);
//


        GL_Vector to = GL_Vector.add(camera.Position,
                GL_Vector.multiply(viewdir, 100));

       /* GamingState.instance.lightPos.x= to.x;
        GamingState.instance.lightPos.y= to.y;
        GamingState.instance.lightPos.z= to.z;
        GamingState.lightPosChanged=true;*/
        //this.engine.lineStart = camera.Position;
        //this.engine.mouseEnd = to;
//        ChunkProvider localChunkProvider = CoreRegistry
//                .get(ChunkProvider.class);
//        GL_Vector hitPoint = bulletPhysics.rayTrace(camera.Position, viewdir,
//                20, engine.currentObject, false);
        // camera.getViewDir().add();
        livingThingManager.chooseObject(camera.Position, viewdir);
        //livingThingManager.attack();
       /*Ball ball =new Ball(this.camera.Position,viewdir,17.3f, TextureManager.getShape("arrow"));

        AttackManager.add(ball);*/
        //  20);
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
        //CoreRegistry.get(Bag.class).click(x, y);
        // CoreRegistry.get(PauseMenu.class).click(x, y);
    }

    public void mouseRClick(int x, int y) {

        LogUtil.println("x:" + x + "y:" + y);
                                    /* GL_Vector from =camera.Position; */
        LogUtil.println("mouse clikc at  %d %d \r\n ", x, y);
       // System.out.printf("mouse clikc at  %d %d \r\n ", x, y);
        GL_Vector viewdir = OpenglUtils.getLookAtDirection(x, y);
        // this.human.ViewDir;//OpenglUtil.getLookAtDirection(x, y);

        // System.out.printf("OpenglUtil getLookAtDirection %f %f %f \r\n ",
        // viewdir.x,viewdir.y,viewdir.z);

        GL_Vector to = GL_Vector.add(camera.Position,
                GL_Vector.multiply(viewdir, 10));
       // this.engine.lineStart = camera.Position;
       // this.engine.mouseEnd = to;
        ChunkProvider localChunkProvider = CoreRegistry
                .get(ChunkProvider.class);
        GL_Vector hitPoint = bulletPhysics.rayTrace(camera.Position, viewdir,
                20, StartMenuState.catchThing, true);
        if (hitPoint != null) {
            // Block block=new
            // BaseBlock(engine.currentObject,(int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z);
                                        /*
                                         * if("wood".equals(engine.currentObject)){ block =new
                                         * Wood((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
                                         * if("glass".equals(engine.currentObject)){ block =new
                                         * Glass((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
                                         * if("soil".equals(engine.currentObject)){ block =new
                                         * Soil((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }else
                                         * if("water".equals(engine.currentObject)){ block =new
                                         * Water((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z); }
                                         */
                                        /*
                                         * if(block!=null){ bulletPhysics.blockRepository.put(block);
                                         * this.engine.blockRepository.reBuild(engine.currentObject); }
                                         */

        }
//        CoreRegistry.get(Bag.class).click(x, y);


    }

    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */

    public void mouseDown(int x, int y) {/*
                                                                     * // add mouse motion to line if left
                                                                     * button is down, and mouse has moved
                                                                     * // more than 10 pixels //
                                                                     * System.out.println("1"); lineStart.x
                                                                     * = cam.camera.Position.x; lineStart.y
                                                                     * = cam.camera.Position.y; lineStart.z
                                                                     * = cam.camera.Position.z; // //
                                                                     * lineStart.x =human.ViewDir.x; //
                                                                     * lineStart.y =human.ViewDir.y; //
                                                                     * lineStart.z = human.ViewDir.z;
                                                                     *
                                                                     * prevMouseX = x; prevMouseY = y;
                                                                     *
                                                                     * mouseEnd = GL_Vector.add(lineStart,
                                                                     * GL_Vector
                                                                     * .multiply(getLookAtDirection(x, y),
                                                                     * 35));
                                                                     *
                                                                     * // System.out.printf("%f %f %f \r\n"
                                                                     * + "", mouseDir.x, mouseDir.y, //
                                                                     * mouseDir.z);
                                                                     */
        mouseLeftPressed=true;
    }
    public void handleMouseWheel(int times){
        Switcher.CAMERA_2_PLAYER+=times;
        if (Switcher.CAMERA_2_PLAYER < 0) {
            Switcher.CAMERA_2_PLAYER = 0;
        }else
        if (Switcher.CAMERA_2_PLAYER > 100) {
            Switcher.CAMERA_2_PLAYER = 100;
        }
        GamingState.cameraChanged=true;

    }
    public void mouseLeftDown(int x, int y) {
        prevMouseX=x;
        prevMouseY=y;
        mouseLClick(x, y);
        mouseLeftPressed=true;
    }
    public void mouseLeftUp(int x, int y) {
        mouseLeftPressed=false;
    }
    public void mouseRightDown(int x, int y) {
        mouseRightPressed=true;
        prevMouseX=x;
        prevMouseY=y;
    }
    public void mouseRightUp(int x, int y) {
        mouseRightPressed=false;
    }
    boolean mouseRightPressed=false;
    boolean mouseLeftPressed=false;

    public void mouseMove(int x, int y) {
        if(mouseRightPressed){
            this.mouseRightDrag(x,y);
           /* camera.ViewDir.x= human.ViewDir.x;
            camera.ViewDir.y= human.ViewDir.y;
            camera.ViewDir.z= human.ViewDir.z;*/
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

    public void mouseMove(int x, int y,StartMenuState state) {
        if(mouseRightPressed){
            this.mouseRightDrag(x,y);

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

    /**
     * Add last mouse motion to the line, only if left mouse button is down.
     */
    public void mouseRightDrag(int x, int y) {


        // add mouse motion to line if left button is down, and mouse has moved
        // more than 10 pixels
        if (MathUtil.distance(prevMouseX, prevMouseY, x, y) > 1f
                && MathUtil.distance(prevMouseX, prevMouseY, x, y) < 200000f) {
            // add a segment to the line
            // /System.out.println("����ת��");
            // System.out.println(x-prevMouseX);
            human.bodyRotate( -(x - prevMouseX),(y - prevMouseY));
            // System.out.printf("y distance: %d \r\n",(y-prevMouseY));
            //human.RotateX(-(y - prevMouseY) / 5);
            camera.fenli = false;
            // regenerate the line
            // save mouse position
            prevMouseX = x;
            prevMouseY = y;
            camera.ViewDir.x= human.ViewDir.x;
            camera.ViewDir.y= human.ViewDir.y;
            camera.ViewDir.z= human.ViewDir.z;
            GamingState.cameraChanged=true;
           // camera.changeCallBack();
            // �ƶ���ͷ
        }
    }
    int DRAG_DIST=3;
    public GameState gameState;
    public void mouseLeftDrag(int x, int y) {

        if ( Math.abs(x - prevMouseX) > DRAG_DIST ||
                Math.abs(y - prevMouseY) > DRAG_DIST) {
      /*  if (MathUtil.distance(prevMouseX, prevMouseY, x, y) > 0.1f
                && MathUtil.distance(prevMouseX, prevMouseY, x, y) < 2000) {*/
            // add a segment to the line
            // System.out.println("ͷ��ת��");
            // System.out.println(x-prevMouseX);
            camera.fenli = true;

            human.headRotate(-(x - prevMouseX), (y - prevMouseY));
            // camera.RotateX(-(x - prevMouseX) / 5);
            // System.out.printf("y distance: %d \r\n",(y-prevMouseY));
            //camera.RotateY((y - prevMouseY) / 5);

            // regenerate the line
            // save mouse position
            prevMouseX = x;
            prevMouseY = y;
            camera.ViewDir.x= human.ViewDir.x;
            camera.ViewDir.y= human.ViewDir.y;
            camera.ViewDir.z= human.ViewDir.z;
            GamingState.cameraChanged=true;
            //camera.changeCallBack();
            // �ƶ���ͷ
        }
    }

    public void keyDown(int keycode) {
        if (keycode == Keyboard.KEY_G) {
            human.receive(new AttackCmd(AttackType.ARROW));
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

        if (keycode == Keyboard.KEY_F3) {
            Switcher.PRINT_SWITCH = !Switcher.PRINT_SWITCH;
        } else if (keycode == Keyboard.KEY_B) {
            // CoreRegistry.get(Bag.class).changeShow();
        } else if (keycode == Keyboard.KEY_P) {
            Switcher.CAMERA_2_PLAYER++;
            if (Switcher.CAMERA_2_PLAYER > 10) {
                Switcher.CAMERA_2_PLAYER = 10;
            }
        } else if (keycode == Keyboard.KEY_O) {
            Switcher.CAMERA_2_PLAYER--;
            if (Switcher.CAMERA_2_PLAYER < 0) {
                Switcher.CAMERA_2_PLAYER = 0;
            }
        } else if (keycode == Keyboard.KEY_F4) {
            Switcher.IS_GOD = !Switcher.IS_GOD;
            System.out.println("god mode:" + Switcher.IS_GOD);
        } else if (keycode == Keyboard.KEY_ESCAPE) {
            // CoreRegistry.get(PauseMenu.class).show();
        } else if (keycode == Keyboard.KEY_F5) {
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
            System.out.println("key_e");
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
            human.position.y = human.position.y - 3 * seconds;
            human.move(human.position);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            human.position.y = human.position.y + 3 * seconds;
            human.move(human.position);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
           // CoreRegistry.get(ToolBar.class).keyDown(1);
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
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            double timenow = GLApp.getTimeInSeconds();

            if ((timenow - preKeyTime) < 1) {
                return;
            }
            preKeyTime = timenow;
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                human.jumpHigh();
            // System.out.println("ͬʱ������w��");
            human.jump();
            // System.out.println("jump");
        }

                                        /*
                                         * if (Keyboard.KEY_SPACE == keycode) { cam.setCamera((cam.camera ==
                                         * camera1)? camera2 : camera1); }
                                         */
    }

    public void keyUp(int keycode) {


    }


}
