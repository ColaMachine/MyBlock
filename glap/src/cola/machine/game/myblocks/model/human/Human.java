package cola.machine.game.myblocks.model.human;

import check.CrashCheck;
import cola.machine.game.myblocks.logic.characters.MovementMode;
import cola.machine.game.myblocks.registry.CoreRegistry;
import glapp.GLApp;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import util.MathUtil;
import cola.machine.game.myblocks.item.Item;
import cola.machine.game.myblocks.item.weapons.Sword;
import cola.machine.game.myblocks.model.Block;
import cola.machine.game.myblocks.model.AABB.AABB;
import cola.machine.game.myblocks.repository.BlockRepository;
import cola.machine.game.myblocks.switcher.Switcher;

import javax.vecmath.Vector3f;

public class Human extends AABB{
	static final float PIdiv180 = 0.0174532925f;
	public GL_Vector ViewDir;
	public GL_Vector RightVector;
	public GL_Vector UpVector;
	public GL_Vector WalkDir;
	public GL_Vector Position;
    public GL_Vector oldPosition=new GL_Vector();
	public float RotatedX, RotatedY, RotatedZ;
	public float camSpeedR = 50; // degrees per second
	public float camSpeedXZ = 5; // units per second
	public float camSpeedY = 10; // units per second

    public MovementMode movementMode=MovementMode.NONE;

    public byte armor_head=0;
    public byte armor_leg=0;
    public byte armor_foot=0;
    public byte armor_body=0;
    public byte armor_hand=0;

	int height = 2;
	public HumanHead head = new HumanHead();
	public HumanHand LHand = new HumanHand(true);
	public HumanHand RHand = new HumanHand(false);

	public HumanLeg LLeg = new HumanLeg();
	public HumanLeg RLeg = new HumanLeg();
	public HumanBody body = new HumanBody();
	public Item item;
	BlockRepository blockRepository;
	public boolean stable = true;
	public Human(	BlockRepository blockRepository){
		this.blockRepository=blockRepository;

        RHand.human= this;
        LHand.human= this;
		//sword=new Sword(0,0,0);
	}//Sword sword;
	public void setStable(boolean flag) {
		this.stable = flag;
	}

	long lastTime = 0;
	long lastMoveTime = 0;
	float v = 6.2f;
	float g = 19.6f;
	float s = 0;
	float nextZ = 0;
	int limit = 0;
	public int mark = 0;

	public void flip(int y) {
		mark = y;
		limit = 0;
	}

	public void reset() {
		mark = limit = 0;
	}

	public void nextMotion() {
		if (!this.stable) {
			this.Position.y -= 0.1;
		}
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
		Position = new GL_Vector(posx, posy, posz);
		ViewDir = new GL_Vector(dirx, diry, dirz);
		WalkDir = new GL_Vector(dirx, diry, dirz);
		UpVector = new GL_Vector(upx, upy, upz);
		// System.out.printf("UpVector : %f %f %f
		// \r\n",UpVector.x,UpVector.y,UpVector.z);

		RightVector = GL_Vector.crossProduct(ViewDir, UpVector);
		RotatedX = RotatedY = RotatedZ = 0.0f; // TO DO: should set these to
												// correct values
		
		head.setHead(0,  3, 0, dirx, diry, dirz, upx, upy, upz);
		LLeg.setHead(0 - 0.25f, 0 + 1.5f, 0, dirx, diry, dirz, upx, upy,
				upz);
		RLeg.setHead(0 + 0.25f, 0 + 1.5f, 0, dirx, diry, dirz, upx, upy,
				upz);
		LHand.setHead(0 - 0.75f, 0 + 2.75f, 0, dirx, diry, dirz, upx,
				upy, upz);
		RHand.setHead(0 + 0.75f, 0 + 2.75f, 0, dirx, diry, dirz, upx,
				upy, upz);
		body.setHead(0, 0 + 1.5f, 0, dirx, diry, dirz, upx, upy, upz);

	}

	public void adjust(float posx, float posy, float posz) {
		this.minX=posx-0.5f;
		this.minY=posy;
		this.minZ=posz-0.5f;
		
		this.maxX=posx+0.5f;
		this.maxY=posy+4;
		this.maxZ=posz+0.5f;
		
		
		Position = new GL_Vector(posx, posy, posz);
		//RightVector = GL_Vector.crossProduct(ViewDir, UpVector);

		/*head.adjust(posx, posy + 3, posz);
		LLeg.adjust(posx - 0.25f, posy + 1.5f, posz);
		RLeg.adjust(posx + 0.25f, posy + 1.5f, posz);
		LHand.adjust(posx - 0.75f, posy + 2.75f, posz);
		RHand.adjust(posx + 0.75f, posy + 2.75f, posz);
		body.adjust(posx, posy + 1.5f, posz);*/
	}

	int preY = 0;

    public static void main(String args[]){

        System.out.println(16>>4);
    }

    //走路的判断过程  先判断 当前状态 然后再走动

    public boolean judge;
    public MovementMode mode = MovementMode.WALKING;

    public void update(){

    }
	public void dropControl() {
		if(!Switcher.IS_GOD)
		if (!this.stable) {
			long t = Sys.getTime() - this.lastTime;//�˶���ʱ��

			s = this.v * t / 1000 - 0.5f * (this.g) * t * t / 1000000;//�˶��ľ���
			// this.Position.y+=s;
			// System.out.println("time:"+t+" weiyi:"+s);
			// GL11.glTranslated(0, s, 0);
			this.Position.y = preY + s;//��Ӧy��䶯
			//System.out.println("��ǰ�˵�y���:"+this.Position.y);
			if (this.Position.y <= mark) {
				//
		//System.out.println("��ǰ��y" + mark);
				this.Position.y = mark;
				this.stable = true;
				mark = 0;
				preY = 0;
			}

		}
	}

//	public static void main(String args[]) {
//		long t = 1024;
//		float v = 10;
//		float s = v * t / 1000 - 0.5f * (9.8f) * t * t / 1000000;
//		// System.out.println("time: weiyi:"+(s));
//	}

	public void render() {
		adjust(this.Position.x, this.Position.y, this.Position.z);
		//GL11.glTranslatef(this.Position.x, this.Position.y, this.Position.z);
		float angle=GL_Vector.angleXZ(this.ViewDir, new GL_Vector(0,0,-1));
		//System.out.println("glRotatef angle :"+angle);
		//System.out.printf("%f %f %f \r\n",this.ViewDir.x,this.ViewDir.y,this.ViewDir.z);
		
	//	GL11.glTranslatef(-this.Position.x, -this.Position.y, -this.Position.z);
		this.walk();
		this.dropControl();
        GL11.glTranslatef(Position.x,Position.y,Position.z);
        
        GL11.glRotatef(angle, 0, 1, 0);
        GL11.glScalef(0.5f,0.5f,0.5f);
		LLeg.render();

		RLeg.render();
	

		
		body.render();
		head.render();


        RHand.render();

        LHand.render();
        GL11.glScalef(2,2,2);
		GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-Position.x,-Position.y,-Position.z);
	}


    public void renderPart() {
        adjust(this.Position.x, this.Position.y, this.Position.z);
        //GL11.glTranslatef(this.Position.x, this.Position.y, this.Position.z);
        float angle=GL_Vector.angleXZ(this.ViewDir, new GL_Vector(0,0,-1));
        //System.out.println("glRotatef angle :"+angle);
        //System.out.printf("%f %f %f \r\n",this.ViewDir.x,this.ViewDir.y,this.ViewDir.z);

        //	GL11.glTranslatef(-this.Position.x, -this.Position.y, -this.Position.z);
        this.walk();
        this.dropControl();
        GL11.glTranslatef(Position.x,Position.y,Position.z);

        GL11.glRotatef(angle, 0, 1, 0);

//        LLeg.render();
//
//        RLeg.render();
//
         LHand.render();
//
        //RHand.render();
//
//        body.render();
//        head.render();
        GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-Position.x,-Position.y,-Position.z);
    }

    public void renderInMirror() {
       // adjust(this.Position.x, this.Position.y, this.Position.z);
      //  GL11.glTranslatef(this.Position.x, this.Position.y, this.Position.z);
        //float angle=GL_Vector.angleXZ(this.ViewDir, new GL_Vector(0,0,-1));
        //System.out.println("glRotatef angle :"+angle);
        //System.out.printf("%f %f %f \r\n",this.ViewDir.x,this.ViewDir.y,this.ViewDir.z);
       // GL11.glRotatef(angle, 0, 1, 0);
        //GL11.glTranslatef(-this.Position.x, -this.Position.y, -this.Position.z);
        //this.walk();
       // this.dropControl();

        LLeg.render();
        RLeg.render();

        body.render();
        head.render();


        RHand.render();

        LHand.render();
    }

	public void move(float x, float y, float z) {
        if(GL_Vector.length(GL_Vector.sub(oldPosition,Position))>0.1){
            this.oldPosition.copy(this.Position);
        }


		this.Position.set(x,y,z);
		if(!Switcher.IS_GOD)
       if(CoreRegistry.get(CrashCheck.class).check()){
           this.Position.copy(oldPosition);
       }
        //this.stable=false;
	}
	public boolean needJudgeCrash=false;
	public void move(GL_Vector vector) {
		float x =vector.x;
		float y =vector.y;
		float z =vector.z;
		this.move(x, y, z);
		
	}

    public void moveOld(){
        this.Position=oldPosition;
        //make some adjust for float not Precision
    }
	/**
	 * Rotate the camera around the absolute vertical axis (0,1,0), NOT around
	 * the cameras Y axis. This simulates a person looking up or down and
	 * rotating in place. You will rotate your body around the vertical axis,
	 * while your head remains tilted at the same angle.
	 * 
	 * @param Angle
	 *            the angle to rotate around the vertical axis in degrees
	 */
	public void RotateV(float Angle) {
		// Make a matrix to rotate the given number of degrees around Y axis
		GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(Angle),
				0);
		// rotate the view vector
		GL_Vector vd = M.transform(ViewDir);
		// the up vector is perpendicular to the old view and the new view
		// UpVector = (Angle > 0)? GL_Vector.crossProduct(ViewDir,vd) :
		// GL_Vector.crossProduct(vd,ViewDir);
		// the right vector is perpendicular to the new view and Up vectors
		RightVector = GL_Vector.crossProduct(vd, UpVector);
		// set the view direction
		ViewDir = vd;
		//System.out.println(ViewDir);
		//ViewDir = WalkDir;
		//RotatedY += Angle;
		// System.out.println(RotatedY);
	}
	
	public void ViewRotateV(float Angle) {
		// Make a matrix to rotate the given number of degrees around Y axis
		GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(Angle),
				0);
		//System.out.println(Angle);
		// rotate the view vector
		
		GL_Vector vd = M.transform(ViewDir);
		// the up vector is perpendicular to the old view and the new view
		// UpVector = (Angle > 0)? GL_Vector.crossProduct(ViewDir,vd) :
		// GL_Vector.crossProduct(vd,ViewDir);
		// the right vector is perpendicular to the new view and Up vectors
		//RightVector = GL_Vector.crossProduct(vd, UpVector);
		ViewDir = vd;
		/*System.out.printf("ViewDir : %f %f %f \r\n", ViewDir.x, ViewDir.y,
				ViewDir.z);*/
		// set the view direction
		// System.out.println(RotatedY);
	}

	public void RotateX(float Angle) {
		//System.out.println("angle" + Angle);
		//RotatedX += Angle;
		// Rotate viewdir around the right vector:
		ViewDir = GL_Vector.normalize(GL_Vector.add(GL_Vector.multiply(ViewDir,
				(float) Math.cos(Angle * PIdiv180)), GL_Vector.multiply(
				UpVector, (float) Math.sin(Angle * PIdiv180))));
		/*System.out.printf("ViewDir : %f %f %f \r\n", ViewDir.x, ViewDir.y,
				ViewDir.z);

		System.out.printf("UpVector : %f %f %f \r\n", UpVector.x, UpVector.y,
				UpVector.z);*/

		//now compute the new UpVector (by cross product)
		/*UpVector = GL_Vector.multiply(GL_Vector.crossProduct(ViewDir,
				RightVector), -1);*/
	}
	public void ViewRotateX(float Angle) {
	//	System.out.println("angle" + Angle);
		RotatedX += Angle;
		// Rotate viewdir around the right vector:
		ViewDir = GL_Vector.normalize(GL_Vector.add(GL_Vector.multiply(ViewDir,
				(float) Math.cos(Angle * PIdiv180)), GL_Vector.multiply(
				UpVector, (float) Math.sin(Angle * PIdiv180))));
		/*System.out.printf("ViewDir : %f %f %f \r\n", ViewDir.x, ViewDir.y,
				ViewDir.z);

		System.out.printf("UpVector : %f %f %f \r\n", UpVector.x, UpVector.y,
				UpVector.z);*/

		//now compute the new UpVector (by cross product)
		/*UpVector = GL_Vector.multiply(GL_Vector.crossProduct(ViewDir,
				RightVector), -1);*/
	}
	/*public void handleNavKeys(float seconds) {
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			this.RotateV(camSpeedR * seconds);
		}
		// Turn right
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			this.RotateV(-camSpeedR * seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.StrafeRight(-camSpeedXZ * seconds);
		}
		// Pan right
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.StrafeRight(camSpeedXZ * seconds);
		}
		// tilt down
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.MoveForward(-camSpeedXZ * seconds);
		}
		// tilt up
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.MoveForward(camSpeedXZ * seconds);
		}
		// tilt up
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				this.jumpHigh();
			//	System.out.println("ͬʱ������w��");
				this.jump();
			// System.out.println("jump");
		}
	}*/

	public void StrafeRight(float Distance) {
		//if (this.stable) {
			lastMoveTime = Sys.getTime();
			this.move( GL_Vector.add(Position, GL_Vector.multiply(RightVector,
					Distance)));
		//}
	}

	public void MoveForward(float Distance) {// System.out.printf("%f %f %f
												// \r\n",ViewDir.x,ViewDir.y,ViewDir.z);
		//if (this.stable) {
		this.move( GL_Vector.add(Position, GL_Vector.multiplyWithoutY(ViewDir,
					Distance)));
			lastMoveTime = Sys.getTime();
			// System.out.printf("position: %f %f %f viewdir: %f %f %f
			// \r\n",Position.x,Position.y,Position.z,ViewDir.x,ViewDir.y,ViewDir.z);
		//}
	}

	public void startWalk() {
		LLeg.angle = 0;
		RLeg.angle = -0;
	}

	int du = 30;

	public void walk() {
		if (Sys.getTime() - this.lastMoveTime > 1000) {
			this.stop();
			return;
		}
		if (LLeg.angle > 30 || LLeg.angle < -30) {
			du = -du;

		}// System.out.println(du);
		LLeg.angle += du * GLApp.getSecondsPerFrame();
		RLeg.angle -= du * GLApp.getSecondsPerFrame();

		RHand.angle += du * GLApp.getSecondsPerFrame();
		LHand.angle -= du * GLApp.getSecondsPerFrame();

	}

	public void stop() {
		LLeg.angle = 0;
		RLeg.angle = -0;
	}


	public void jumpHigh() {
		
		// ��¼��ǰ��ʱ��
		if (this.stable) {
			this.v=12.6f;
			preY = (int) this.Position.y;
			lastTime = Sys.getTime();
			this.stable = false;
		}
	}
	public void jump() {
		//this.Position.y+=1;
		// ��¼��ǰ��ʱ��
		if(Switcher.IS_GOD){
			this.Position.y+=2;
		}else
		if (this.stable) {
			this.v=10.2f;
			preY = (int) this.Position.y;
			lastTime = Sys.getTime();
			this.stable = false;
		}
	}
	public void drop() {
		
		// ��¼��ǰ��ʱ��
this.stable=false;
		this.v=0f;
			preY = (int) this.Position.y;
			lastTime = Sys.getTime();
			
	}

    public  Vector3f  velocity=new Vector3f();
    public void update(){
        //计算当前的速度

        if(){

        }
    }

}
