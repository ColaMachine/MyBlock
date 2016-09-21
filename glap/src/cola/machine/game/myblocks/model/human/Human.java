package cola.machine.game.myblocks.model.human;

import check.CrashCheck;
import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.logic.characters.MovementMode;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.Connector;
import cola.machine.game.myblocks.model.textture.ItemCfgBean;
import cola.machine.game.myblocks.model.textture.Shape;
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

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class Human extends LivingThing {





    //Player player ;
	static final float PIdiv180 = 0.0174532925f;
	//public GL_Vector ViewDir;
   // public GL_Vector WalkDir;
	public GL_Vector RightVector;
	public GL_Vector UpVector;
	//public GL_Vector position;
   // public GL_Vector oldposition=new GL_Vector();


	public float camSpeedR = 5; // degrees per second
	public float camSpeedXZ = 2.4f; // units per second
	public float camSpeedY = 0.1f; // units per second


 /*   public MovementMode movementMode=MovementMode.NONE;*/

/*
    public byte armor_head=0;
    public byte armor_leg=0;
    public byte armor_foot=0;
    public byte armor_body=0;
    public byte armor_hand=0;
*/

/*	int height = 2;*/
    //Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);
	/*public HumanHead head = new HumanHead();
	public HumanHand LHand = new HumanHand(true);
	public HumanHand RHand = new HumanHand(false);

	public HumanLeg LLeg = new HumanLeg();
	public HumanLeg RLeg = new HumanLeg();
	public HumanBody body = new HumanBody();*/
/*	public Item item;*/
	/*BlockRepository blockRepository;*/


	public Human(/*	BlockRepository blockRepository*/){
/*

       Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        Connector body_lhand = new Connector(lHandComponent,new GL_Vector(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(0,HAND_HEIGHT*3/4,HAND_THICK/2));

        bodyComponent.addConnector(body_lhand);
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        Connector body_rhand = new Connector(lHandComponent,new GL_Vector(0,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addConnector(body_rhand);*/

        /*this.blockRepository=blockRepository;*/



    /*    RHand.human= this;
        LHand.human= this;*/
		//sword=new Sword(0,0,0);
	}//Sword sword;


/*	public void nextMotion() {
		if (!this.stable) {
			this.position.y -= 0.1;
		}
	}*/

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
		ViewDir = new GL_Vector(dirx, diry, dirz);
		WalkDir = new GL_Vector(dirx, diry, dirz);
		UpVector = new GL_Vector(upx, upy, upz);
		// System.out.printf("UpVector : %f %f %f
		// \r\n",UpVector.x,UpVector.y,UpVector.z);

		RightVector = GL_Vector.crossProduct(ViewDir, UpVector);
		RotatedX = RotatedY = RotatedZ = 0.0f; // TO DO: should set these to
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


/*
    public static void main(String args[]){

        System.out.println(16>>4);
    }*/

    //走路的判断过程  先判断 当前状态 然后再走动

   // public boolean judge;
 //   public MovementMode mode = MovementMode.WALKING;

    public void update(){

    }


//	public static void main(String args[]) {
//		long t = 1024;
//		float v = 10;
//		float s = v * t / 1000 - 0.5f * (9.8f) * t * t / 1000000;
//		// System.out.println("time: weiyi:"+(s));
//	}
	public void preRender(){
		adjust(this.position.x, this.position.y, this.position.z);

		//this.walk();

	}

	public void render(){
		super.render();
	}
    public void renderInMirror() {
       // adjust(this.position.x, this.position.y, this.position.z);
      //  GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
        //float angle=GL_Vector.angleXZ(this.ViewDir, new GL_Vector(0,0,-1));
        //System.out.println("glRotatef angle :"+angle);
        //System.out.printf("%f %f %f \r\n",this.ViewDir.x,this.ViewDir.y,this.ViewDir.z);
       // GL11.glRotatef(angle, 0, 1, 0);
        //GL11.glTranslatef(-this.position.x, -this.position.y, -this.position.z);
        //this.walk();
       // this.dropControl();

        /*LLeg.render();
        RLeg.render();

        body.render();
        head.render();


        RHand.render();

        LHand.render();*/
    }

	public void move(float x, float y, float z) {
        if(GL_Vector.length(GL_Vector.sub(oldPosition,position))>0.1){
            this.oldPosition.copy(this.position);
        }


		this.position.set(x,y,z);
		if(!Switcher.IS_GOD)
       if(CoreRegistry.get(CrashCheck.class).check(this)){
           this.position.copy(oldPosition);
       }
        //this.stable=false;
	}
	//public boolean needJudgeCrash=false;
	public void move(GL_Vector vector) {
		float x =vector.x;
		float y =vector.y;
		float z =vector.z;
		this.move(x, y, z);

	}

    public void moveOld(){
        this.position=oldPosition;
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
	public void RotateV1(float Angle) {
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

    /**
     * x 是俯仰角度
     * y 是左右角度

     */

	public void headRotate(float leftRightDegree,float updownDegree){
        GL_Matrix M = GL_Matrix.rotateMatrix(/*(float) Math.toRadians(updownDegree)/5,*/0, (float) Math.toRadians(leftRightDegree)/5,
                0);
		ViewDir.y-=updownDegree/100;
        GL_Vector vd = M.transform(ViewDir);
        ViewDir = vd;

        if(ViewDir.y<=Switcher.FUJIAO  )
            ViewDir.y=Switcher.FUJIAO ;
        if(ViewDir.y>=Switcher.YANGJIAO  )
            ViewDir.y=Switcher.YANGJIAO;
      //  System.out.println(vd);
    }
    public void bodyRotate(float updownDegree,float leftRightDegree){
        GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(leftRightDegree)/5,
                0);
        GL_Vector vd = M.transform(WalkDir);
        RightVector = GL_Vector.crossProduct(vd, UpVector);
        WalkDir = vd;
        ViewDir.x= vd.x;
        ViewDir.z = vd.z;
        headRotate(0,-updownDegree);
    }
	public void ViewRotateV1(float Angle) {
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

	public void RotateX1(float Angle) {
		//System.out.println("angle" + Angle);
		//RotatedX += Angle;
		// Rotate viewdir around the right vector:
        RotatedX += Angle;
		ViewDir = GL_Vector.normalize(GL_Vector.add(GL_Vector.multiply(ViewDir,
				(float) Math.cos(Angle * PIdiv180)), GL_Vector.multiply(
				UpVector, (float) Math.sin(Angle * PIdiv180))));

//        WalkDir .x= ViewDir.x;
//        WalkDir .z= ViewDir.z;
		/*System.out.printf("ViewDir : %f %f %f \r\n", ViewDir.x, ViewDir.y,
				ViewDir.z);

		System.out.printf("UpVector : %f %f %f \r\n", UpVector.x, UpVector.y,
				UpVector.z);*/

		//now compute the new UpVector (by cross product)
		/*UpVector = GL_Vector.multiply(GL_Vector.crossProduct(ViewDir,
				RightVector), -1);*/
	}
	public void ViewRotateX111(float Angle) {
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

        AnimationManager manager = CoreRegistry.get(AnimationManager.class);
        manager.apply(bodyComponent,"walkerLeft");
		//if (this.stable) {
			lastMoveTime = Sys.getTime();
			this.move( GL_Vector.add(position, GL_Vector.multiply(RightVector,
					Distance)));
		//}
	}

	public void MoveForward(float Distance) {// System.out.printf("%f %f %f
//        LogUtil.println("MoveForward");							// \r\n",ViewDir.x,ViewDir.y,ViewDir.z);
		//if (this.stable) {

        //Player player= CoreRegistry.get(Player.class);
        AnimationManager manager = CoreRegistry.get(AnimationManager.class);
        manager.apply(bodyComponent,"walkerFoward");
		this.move( GL_Vector.add(position, GL_Vector.multiplyWithoutY(WalkDir,
					Distance)));
			lastMoveTime = Sys.getTime();
			// System.out.printf("position: %f %f %f viewdir: %f %f %f
			// \r\n",position.x,position.y,position.z,ViewDir.x,ViewDir.y,ViewDir.z);
		//}
	}

	/*public void startWalk() {
		LLeg.angle = 0;
		RLeg.angle = -0;
	}*/

	int du = 30;

	/*public void walk() {
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

	}*/

	/*public void stop() {
		LLeg.angle = 0;
		RLeg.angle = -0;
	}*/


	public void jumpHigh() {

		// ��¼��ǰ��ʱ��
		if (this.stable) {
			this.v=12.6f;
			preY = (int) this.position.y;
			lastTime = Sys.getTime();
			this.stable = false;
		}
	}
	public void jump() {
		//this.position.y+=1;
		// ��¼��ǰ��ʱ��
		if(Switcher.IS_GOD){
			this.position.y+=2;
		}else
		if (this.stable) {
			this.v=10.2f;
			preY = (int) this.position.y;
			lastTime = Sys.getTime();
			this.stable = false;
		}
	}

public void addHeadEquip(ItemCfgBean itemCfg)  {
	Component parent = 	bodyComponent.findChild("human_head");
	if(itemCfg==null){
		parent.children.remove(parent.children.size()-1);
		return;
	}
	Shape shape = itemCfg.getShape();

	Component component= new Component(shape.getWidth(),shape.getHeight(),shape.getThick());
	component.setShape(itemCfg.getShape());
	component.id=itemCfg.getName();

    if(parent==null){
        LogUtil.println("未找到子component");
        System.exit(0);
    }
	component.setOffset(new Point3f(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new Point3f(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
	//Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
	parent.addChild(component);
}
    public void addLegEquip(ItemCfgBean itemCfg)  {
		Component parent = 	bodyComponent.findChild("human_l_b_leg");
		if(itemCfg==null){
			parent.children.remove(parent.children.size()-1);
			return;
		}
        Shape shape = itemCfg.getShape();

        Component component= new Component(shape.getWidth(),shape.getHeight(),shape.getThick());
        component.setShape(itemCfg.getShape());
        component.id=itemCfg.getName();

		component.setOffset(new Point3f(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new Point3f(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));

		//Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
        //parent.addConnector(connector);
		parent.addChild(component);
    }
    public void addBodyEquip(ItemCfgBean itemCfg)  {
		Component parent = 	bodyComponent.findChild("human_body");
		if(itemCfg==null){
			parent.children.remove(parent.children.size()-1);
			return;
		}

        Shape shape = itemCfg.getShape();

        Component component= new Component(shape.getWidth(),shape.getHeight(),shape.getThick());
        component.setShape(itemCfg.getShape());
        component.id=itemCfg.getName();

        if(parent==null){
            LogUtil.println("未找到子component");
            System.exit(0);
        }
		component.setOffset(new Point3f(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new Point3f(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
		parent.addChild(component);
    }

    public void addHandEquip(ItemCfgBean itemCfg)  {
        //Shape shape = itemCfg.getShape();
		Component parent = 	bodyComponent.findChild("rHumanHand");
		if(itemCfg==null){
			parent.children.clear();
			return;
		}


        Component component= new Component(0.01f,1,1);
        itemCfg.init();
        component.setItem(itemCfg);
        component.id=itemCfg.getName();

        if(parent==null){
            LogUtil.println("未找到子component");
            System.exit(0);
        }
		component.setOffset(new Point3f(this.HAND_WIDTH/2,0,this.HAND_THICK/2),new Point3f(0,0,0));

		// Connector connector = new Connector(component,new GL_Vector(this.player.HAND_WIDTH/2,0,this.player.HAND_THICK/2),new GL_Vector(0,0,0));
        parent.addChild(component);
    }
    //public  Vector3f  velocity=new Vector3f();


}
