package cola.machine.game.myblocks.control;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import glapp.GLApp;
import glapp.GLCamera;
import glmodel.GL_Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import cola.machine.game.myblocks.engine.MyBlockEngine;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.physic.BulletPhysics;
import util.MathUtil;
import util.OpenglUtil;

public class MouseControlCenter{
	public Human human;
	public GLCamera camera;
	public MyBlockEngine engine;
public Robot robot;
	public float centerX=0;
	public float centerY=0;
	
	float prevMouseX = 0;
	float prevMouseY = 0;
	
	public  MouseControlCenter(Human human ,GLCamera camera,MyBlockEngine engine){
		this.engine=engine;
		this.human =human;
		this.camera=camera;
		centerX=Display.getX()+GLApp.displayWidth/2;
		
		centerY=Display.getY()+GLApp.displayWidth/2;
		//System.out.println("the center position : x"+centerX+" y "+centerY);
		try {
			robot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	public void handleNavKeys(float seconds) {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			human.RotateX(human.camSpeedXZ * seconds);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			human.RotateV(-human.camSpeedXZ * seconds);
		}
		// Turn right
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			human.RotateV(-human.camSpeedR * seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			human.StrafeRight(-human.camSpeedXZ * seconds);
		}
		// Pan right
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			human.StrafeRight(human.camSpeedXZ * seconds);
		}
		// tilt down
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			human.MoveForward(-human.camSpeedXZ * seconds);
		}
		// tilt up
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			human.MoveForward(human.camSpeedXZ * seconds);
		}
		// tilt up
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				human.jumpHigh();
			//	System.out.println("同时按下了w键");
				human.jump();
			// System.out.println("jump");
		}
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
	
	
	public void mouseLClick(int x, int y){
		/*GL_Vector from =camera.Position;*/
		GL_Vector viewdir =OpenglUtil.getLookAtDirection(x, y);
	/*
		GL_Vector to = GL_Vector.add(camera.Position,
					GL_Vector.multiply(viewdir,10));
		this.engine.lineStart=from;
		this.engine.mouseEnd=to;*/
		
		GL_Vector hitPoint=bulletPhysics.rayTrace(camera.Position,viewdir,20);
		if(hitPoint!=null){
			Block block =new Block((int)hitPoint.x,(int)hitPoint.y,(int)hitPoint.z);
			bulletPhysics.blockRepository.put(block);
			this.engine.blockRepository.reBuild();
		}
		
	}
	public void mouseRClick(int x, int y){
		
		
	}
	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	
	public void mouseDown(int x, int y) {/*
		// add mouse motion to line if left button is down, and mouse has moved
		// more than 10 pixels
		// System.out.println("1");
		lineStart.x = cam.camera.Position.x;
		lineStart.y = cam.camera.Position.y;
		lineStart.z = cam.camera.Position.z;
//		
//		lineStart.x =human.ViewDir.x;
//		lineStart.y =human.ViewDir.y;
//		lineStart.z = human.ViewDir.z;
		
		prevMouseX = x;
		prevMouseY = y;
		
		mouseEnd = GL_Vector.add(lineStart,
				GL_Vector.multiply(getLookAtDirection(x, y), 35));

//		System.out.printf("%f %f %f \r\n" + "", mouseDir.x, mouseDir.y,
//				mouseDir.z);
		

	*/}
	
	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	Point mousepoint;
	public boolean canDetectMove=true;
	public BulletPhysics bulletPhysics;
	public void mouseMove(int x, int y) {
		// add mouse motion to line if left button is down, and mouse has moved
		// more than 10 pixels
		
		//转动身体 头  摄像机跟随头部
		
			// add a segment to the line
			// /System.out.println("身体转动");
			// System.out.println(x-prevMouseX);
//		if(!canDetectMove){
//			if (MathUtil. distance(400, 300, x, y)< 10f){
//				canDetectMove=true;
//			}
//		}else
		mousepoint = MouseInfo.getPointerInfo()
		.getLocation();
		if (MathUtil. distance(centerX, centerY, mousepoint.x, mousepoint.y) > 10f
				) {	
			//System.out.println("now mouse position x:"+x+" y :"+y);
			
			human.RotateV((float)(-(mousepoint.x - centerX)  *4*GLApp.getSecondsPerFrame()));
			// System.out.printf("y distance: %d \r\n",(y-prevMouseY));
			human.RotateX((float)(-(mousepoint.y - centerY) *4*GLApp.getSecondsPerFrame()));
			
			//robot.mouseMove(mousepoint.x-(x-400), mousepoint.y-(y-300));
			//robot.mouseMove(Display.getX()+400, Display.getY()+300);
			robot.mouseMove((int)centerX,(int)centerY);
			//System.out.println("move to position x :"+(mousepoint.x-(x-400))+" y :"+(mousepoint.y-(y-300)));
			
			canDetectMove=false;
	//	System.out.println("move x distance:"+(-x + centerX)+" y distance:"+(y - centerY));
		}

		
	}
	/**
	 * Add last mouse motion to the line, only if left mouse button is down.
	 */
	public void mouseRightDrag(int x, int y) {
		// add mouse motion to line if left button is down, and mouse has moved
		// more than 10 pixels
		if (MathUtil. distance(prevMouseX, prevMouseY, x, y) > 10f
				&& MathUtil. distance(prevMouseX, prevMouseY, x, y) < 20f) {
			// add a segment to the line
			// /System.out.println("身体转动");
			// System.out.println(x-prevMouseX);
			human.RotateV(-(x - prevMouseX) / 5);
			// System.out.printf("y distance: %d \r\n",(y-prevMouseY));
			human.RotateX((y - prevMouseY) / 5);

			// regenerate the line
			// save mouse position
			prevMouseX = x;
			prevMouseY = y;

			// 移动镜头
		} 
	}
	
	public void mouseLeftDrag(int x, int y) {
		if ( MathUtil.distance(prevMouseX, prevMouseY, x, y) > 10f
				&& MathUtil.distance(prevMouseX, prevMouseY, x, y) < 20) {
			// add a segment to the line
			// System.out.println("头部转动");
			// System.out.println(x-prevMouseX);
			human.ViewRotateV(-(x - prevMouseX) / 5);
			// System.out.printf("y distance: %d \r\n",(y-prevMouseY));
			human.ViewRotateX((y - prevMouseY) / 5);

			// regenerate the line
			// save mouse position
			prevMouseX = x;
			prevMouseY = y;

			// 移动镜头
		}
	}
	public void keyDown(int keycode) {
		/*
		 * if (Keyboard.KEY_SPACE == keycode) { cam.setCamera((cam.camera ==
		 * camera1)? camera2 : camera1); }
		 */
	}

	public void keyUp(int keycode) {
	}

}
