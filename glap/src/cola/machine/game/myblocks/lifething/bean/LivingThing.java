package cola.machine.game.myblocks.lifething.bean;

import cola.machine.game.myblocks.lifething.manager.LivingThingManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.Connector;
import cola.machine.game.myblocks.switcher.Switcher;
import glmodel.GL_Vector;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import sun.plugin.viewer.LifeCycleManager;
import cola.machine.game.myblocks.math.AABB;
import javax.vecmath.Point3f;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThing extends cola.machine.game.myblocks.model.AABB.AABB{
    public boolean stable = true;
    public void setStable(boolean flag) {
        this.stable = flag;
    }

    public long lastTime = 0;
    public long lastMoveTime = 0;
    public float v = 6.2f;
    public float g = 19.6f;
    public float s = 0;
    public float nextZ = 0;
    public int limit = 0;
    public int mark = 0;
    public
    int preY = 0;

    public void drop() {

        // ��¼��ǰ��ʱ��
        this.stable=false;
        this.v=0f;
        preY = (int) this.position.y;
        lastTime = Sys.getTime();

    }
    public void flip(int y) {
        mark = y;
        limit = 0;
    }

    public void reset() {
        mark = limit = 0;
    }
   // public AABB aabb;
   protected  float HAND_HEIGHT=1.5f;
    protected float HAND_WIDTH=0.5f;
    protected float HAND_THICK=0.5f;

    protected  float BODY_HEIGHT=1.5f;
    protected float BODY_WIDTH=1f;
    protected  float BODY_THICK=0.5f;


    protected float LEG_HEIGHT=1.5f;
    protected float LEG_WIDTH=0.5f;
    protected  float LEG_THICK=0.5f;

    protected float HEAD_HEIGHT=1f;
    protected float HEAD_WIDTH=1f;
    protected  float HEAD_THICK=1f;

    public float RotatedX, RotatedY, RotatedZ;

    public Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);

    public void setPosition(float posx, float posy, float posz) {
        this.minX=posx-0.5f;
        this.minY=posy;
        this.minZ=posz-0.5f;

        this.maxX=posx+0.5f;
        this.maxY=posy+4;
        this.maxZ=posz+0.5f;
     //




    }
    public void dropControl() {
        if(!Switcher.IS_GOD)
            if (!this.stable) {
                long t = Sys.getTime() - this.lastTime;//�˶���ʱ��

                s = this.v * t / 1000 - 0.5f * (this.g) * t * t / 1000000;//�˶��ľ���
                // this.position.y+=s;
                // System.out.println("time:"+t+" weiyi:"+s);
                // GL11.glTranslated(0, s, 0);
                this.position.y = preY + s;//��Ӧy��䶯
                //System.out.println("��ǰ�˵�y���:"+this.position.y);
                if (this.position.y <= mark) {
                    //
                    //System.out.println("��ǰ��y" + mark);
                    this.position.y = mark;
                    this.stable = true;
                    mark = 0;
                    preY = 0;
                }

            }
    }
    public void render(){
        GL11.glPushMatrix();
        this.dropControl();
        GL11.glTranslatef(position.x, position.y + 0.75f, position.z);
        float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
        GL11.glRotatef(angle, 0, 1, 0);
        GL11.glScalef(0.5f,0.5f,0.5f);
        bodyComponent.render();
        GL11.glScalef(2,2,2);
        GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-position.x,-position.y,-position.z);
        GL11.glPopMatrix();
    }
    public void adjust(float posx, float posy, float posz) {
        this.minX=posx-0.5f;
        this.minY=posy;
        this.minZ=posz-0.5f;

        this.maxX=posx+0.5f;
        this.maxY=posy+4;
        this.maxZ=posz+0.5f;
        position = new GL_Vector(posx, posy, posz);
    }
    public LivingThing(){

        this.WalkDir=new  GL_Vector(0,0,-1);
        bodyComponent.id="human_body";
        bodyComponent.setEightFace("human_body");


        bodyComponent.id="human_body";
        bodyComponent.setEightFace("human_body");
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rHandComponent.setEightFace("human_hand");
        rHandComponent.id="rHumanHand";
        rHandComponent.setOffset(new Point3f(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(0,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(rHandComponent);

        //小手

        //rhand
        Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lHandComponent.setEightFace("human_hand");
        lHandComponent.id="lHumanHand";
        lHandComponent.setOffset(new Point3f(0,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(lHandComponent);

        //lleg
        Component human_l_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_b_leg.setEightFace("human_leg");
        human_l_b_leg.id="human_l_b_leg";

        human_l_b_leg.setOffset(new Point3f(LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addChild(human_l_b_leg);




        //rleg
        Component human_r_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_b_leg.setEightFace("human_leg");
        human_r_b_leg.id="human_r_b_leg";
        human_r_b_leg.setOffset(new Point3f(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addChild(human_r_b_leg);

        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("human_head");

        head.setOffset(new Point3f(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new Point3f(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addChild(head);






    }

    Component main;
    int blood;  //  血量
    int energy; //  能量
    int sight;  //  视力


    int basePower;      //  基础力量
    int baseIntell;     //  基础智力
    int baseAgility;    //  基础敏捷
    int baseSpirit;     //  基础精神

    int level;          //  现在的等级

    int power;          //  现在的力量值
    int Intell;         //  智力值
    int agility;        //  敏捷值
    int spirit;         //  精神值



    public GL_Vector ViewDir;   //  观察方向
    public GL_Vector WalkDir;   //  行走方向

    public GL_Vector position;    //  位置
    public GL_Vector oldPosition=new GL_Vector();   //  旧位置

}
