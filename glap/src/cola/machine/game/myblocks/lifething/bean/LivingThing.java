package cola.machine.game.myblocks.lifething.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.Role.excutor.Executor;
import com.dozenx.game.engine.Role.model.PlayerModel;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import core.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Component;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.network.client.Client;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.graphics.shader.ShaderManager;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThing extends LivingThingBean {
   /*public  float bodyAngle =0  ;
    public float headAngle =0  ;
    public float headAngle2 =0  ;*/
    public LivingThing(int id){
        super(id);

        this.getExecutor().setModel( new PlayerModel(this));
    }

   /* public long updateTime;
    public int id;
    public String name;
    public float distance;

    public int blood;  //  血量
    public int energy; //  能量
    public int sight=5;  //  视力

    public int physicAttack ;//物攻
    public int mgicAttack ;//魔攻

    public int fangyu;

    public int nowBlood;
    public int nowEnergy;



    public int basePower=100;      //  基础力量
    public int baseIntell=100;     //  基础智力
    public int baseAgility=100;    //  基础敏捷
    public int baseSpirit=100;     //  基础精神*/


   /* public int totalPower;
    public int totalIntell;
    public int totalAgility;
    public int totalSpirit;*/

  /*  public int level;          //  现在的等级

    public int totalPower;          //  现在的力量值
    public int totalIntell;         //  智力值
    public int totalAgility;        //  敏捷值
    public int totalSpirit;         //  精神值

    public float speed=1;*/

   // public AABB aabb;




    //private WeakReference<LivingThing> target;
    /*public LivingThing getTarget(){
        if(target==null)return null;
        return target.get()!=null ? target.get() :null;
    }
    public void   finalize(){
        LogUtil.println("回收了");
    }
    public void setTarget(LivingThing target){
        if(target==null){
            this.target=null;
        }else
        this.target=new WeakReference<LivingThing>(target);
    }*/

    /*public void disapper(){
        this.exist=false;
    }
    public String getState(){
        return "力量:"+basePower+"/"+totalPower+"\n"
                +"智力:"+baseIntell+"/"+totalIntell+"\n"
                +"敏捷:"+baseAgility+"/"+totalAgility+"\n"
                +"精神:"+baseSpirit+"/"+totalSpirit+"\n"
                +"血量:"+nowHP+"/"+HP+"\n"
                +"魔法:"+nowMP+"/"+MP+"\n"
                +"防御:"+defense+"";
    }*/
   /* public void drop() {

        // ��¼��ǰ��ʱ��
        this.stable=false;
        this.v=0f;
        preY = (int) this.position.y;
        lastTime = Sys.getTime();

    }*/
/*
    public void changeProperty( ){
   // totalPower = basePower+

        acculateProperty(this.getExecutor().getModel().bodyComponent);

        this.totalPower+=this.basePower;

        totalAgility+=this.baseAgility;
        totalIntell+=this.baseIntell;

        totalSpirit+=this.baseSpirit;

        this.blood=this.totalPower;
        this.energy=this.totalIntell;



    }*/

    /*public void acculateProperty(Component component){
        //try{
        if(component!=null && component.children!=null)
        for(int i=component.children.size()-1;i>=0;i--){
            Component child = component.children.get(i);
            if(child.itemDefinition !=null){
                totalPower +=child.itemDefinition.getStrenth();
                totalAgility+=child.itemDefinition.getAgile();
                totalIntell+=child.itemDefinition.getIntelli();
                totalSpirit+=child.itemDefinition.getSpirit();
            }
            if(child.children!=null){
                acculateProperty(child);
            }
        }*//*}catch(StackOverflowError e ){
            e.printStackTrace();
        }*//*
    }*/
   /* public void flip(int y) {
        mark = y;
        limit = 0;
    }

    public void reset() {
        mark = limit = 0;
    }


    public float RotatedX, RotatedY, RotatedZ;*/

    /*public Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);
*/
/*
    public void dropControl() {
        if(!Switcher.IS_GOD)
            if (!this.stable) {
                long t = Sys.getTime() - this.lastTime;//�˶���ʱ��
                GamingState.livingThingChanged=true;
                GamingState.cameraChanged=true;
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
    }*/

   // int vaoId;
    //int trianglesCount =0;
    /*private void build(){//当发生改变变的时候触发这里
        if(Switcher.SHADER_ENABLE){
            GL_Matrix translateMatrix=GL_Matrix.translateMatrix(position.x, position.y + 0.75f, position.z);
            //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,-bodyAngle+3.14f/2*//**3.14f/180*//*,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
            //.getVao().getVertices()
          //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
            bodyComponent.build(ShaderManager.livingThingShaderConfig,rotateMatrix);


        }else{

        }

         *//*trianglesCount= floatBuffer.position()/8;
        if(trianglesCount<=0){
            LogUtil.println("trianglesCount can't be 0");
            System.exit(1);
        }*//*
       // ShaderManager.livingThingShaderConfig.getVao().setVertices();
         // ShaderUtils.updateLivingVao(ShaderManager.livingThingShaderConfig.getVao());//createVAO(floatBuffer);
        *//*if(ShaderManager.livingThingShaderConfig.getVao().getVaoId()<=0){
            LogUtil.println("vaoId can't be 0");
            System.exit(1);
        }*//*
    }*/
    //FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(10240);
    public void select(){



            Shape shape = TextureManager.getShape("iron_pants");

            Component component= new Component(shape.getWidth(),shape.getHeight(),shape.getThick());
            component.setShape(shape);
            component.name="select";

        component.id=getId()*100+99;
            component.setOffset(new Point3f(0,4,0),new Point3f(0,0,0));
            //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
        getModel().bodyComponent.addChild(component);	//changeProperty()

    }
    public void unSelect(){
        if("select".equals(getModel().bodyComponent.children.get(getModel().bodyComponent.children.size()-1).id)){
            getModel().bodyComponent.children.remove(getModel().bodyComponent.children.size()-1);
        }





    }

    public void update(){
        super.update();

        this.getModel().build();
       // this.currentState.update();
    }


/*


    public void adjust(float posx, float posy, float posz) {
        this.minX=posx-0.5f;
        this.minY=posy;
        this.minZ=posz-0.5f;

        this.maxX=posx+0.5f;
        this.maxY=posy+4;
        this.maxZ=posz+0.5f;
        position = new GL_Vector(posx, posy, posz);
    }

*/



    public void beAttack(int damage){
        super.beAttack(damage);
        /*if(this.nowHP<=0){
            AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            manager.clear(getModel().bodyComponent);
            manager.apply(getModel().bodyComponent,"died");

        }*/
    }





    public void attack(){


       /* CoreRegistry.get(AnimationManager.class)
                .apply(getModel().bodyComponent,"kan");*/
    }

    /*public void beAttack(){
        super.beAttack();



    }*/




    public void setInfo( PlayerStatus info ){
        super.setInfo(info);


        //if(info.getBodyEquip()>0){
            getModel().addBodyEquip(ItemManager.getItemDefinition(ItemType.values()[info.getBodyEquip()]));
            //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //}else {
            getModel().addBodyEquip(null);

        //}if(info.getHeadEquip()>0){
                getModel().addHeadEquip(ItemManager.getItemDefinition(ItemType.values()[info.getHeadEquip()]));
                //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
         //   }
        //}if(info.getHandEquip()>0){
            getModel().addHandEquip(ItemManager.getItemDefinition(ItemType.values()[info.getHandEquip()]));
            //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //}if(info.getLegEquip()>0){
            getModel().addLegEquip(ItemManager.getItemDefinition(ItemType.values()[info.getLegEquip()]));
            //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
       //// }if(info.getFootEquip()>0){
            getModel().addShoeEquip(ItemManager.getItemDefinition(ItemType.values()[info.getFootEquip()]));
            //livingThing.addBodyEquip(TextureManager.getItemDefinition(cmd.getItemType()));
        //}
       this.changeProperty();
    }


    public void getInfo(PlayerStatus info ){
        //   PlayerStatus info =new PlayerStatus();
        super.getInfo(info);
        //  return info;
    }

    public PlayerStatus getInfo(){
        PlayerStatus info =new PlayerStatus();
        super.getInfo(info);
        return info;
    }

}
