package cola.machine.game.myblocks.lifething.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.network.server.bean.PlayerStatus;
import core.log.LogUtil;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.textture.ItemDefinition;
import cola.machine.game.myblocks.model.textture.Shape;
import cola.machine.game.myblocks.model.ui.html.Document;
import com.dozenx.game.network.client.Client;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.graphics.shader.ShaderManager;
import glapp.GLApp;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Point3f;
import java.lang.ref.WeakReference;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThing extends LivingThingBean {
   /*public  float bodyAngle =0  ;
    public float headAngle =0  ;
    public float headAngle2 =0  ;*/
    public LivingThing(int id){
        this.currentState = new IdleState(this);

        this.id=id;//(int)(Math.random()*1000000);
        this.WalkDir=new  GL_Vector(1,0,0);
        bodyComponent.id=id*10+EquipPartType.BODY.ordinal();
        bodyComponent.name = "human_body";
        bodyComponent.setEightFace("human_body");

        //rhand
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rHandComponent.id=id*10+EquipPartType.RHAND.ordinal();
        rHandComponent.name="rHumanHand";
        rHandComponent.setEightFace("human_hand");
        rHandComponent.setOffset(new Point3f(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(0,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(rHandComponent);

        //小手

        //lhand
        Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lHandComponent.id=id*10+EquipPartType.LHAND.ordinal();
        lHandComponent.setEightFace("human_hand");
        lHandComponent.setOffset(new Point3f(0,BODY_HEIGHT*3/4,BODY_THICK/2),new Point3f(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addChild(lHandComponent);

        //lleg
        Component human_l_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_b_leg.setEightFace("human_leg");
        human_l_b_leg.id=id*10+EquipPartType.LLEG.ordinal();

        human_l_b_leg.setOffset(new Point3f(LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addChild(human_l_b_leg);




        //rleg
        Component human_r_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_b_leg.setEightFace("human_leg");
        human_r_b_leg.id=id*10 +EquipPartType.RLEG.ordinal();
        human_r_b_leg.setOffset(new Point3f(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new Point3f(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addChild(human_r_b_leg);

        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("human_head");
        head.id=id*10+EquipPartType.HEAD.ordinal();
        head.setOffset(new Point3f(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new Point3f(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addChild(head);


        changeProperty();

        this.nowBlood=this.blood;
        this.nowEnergy=this.energy;
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
/*

    public GL_Vector RightVector=new GL_Vector(1,0,0); ;
    public GL_Vector UpVector =new GL_Vector(0,1,0);

    public GL_Vector ViewDir = new GL_Vector(0,0,-1);  //  观察方向
    public GL_Vector WalkDir = new GL_Vector(0,0,-1);    //  行走方向
    public float attackDistance=1;
    public GL_Vector position= new GL_Vector(0,0,0);    //  位置
    public GL_Vector nextPosition= new GL_Vector(0,0,0);    //  位置
    public GL_Vector oldPosition=new GL_Vector();   //  旧位置*/

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
    public boolean exist=true;
    private WeakReference<LivingThing> target;
    public LivingThing getTarget(){
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
    }
    public int mark = 0;
    public
    int preY = 0;
    public void disapper(){
        this.exist=false;
    }
    public String getState(){
        return "力量:"+basePower+"/"+totalPower+"\n"
                +"智力:"+baseIntell+"/"+totalIntell+"\n"
                +"敏捷:"+baseAgility+"/"+totalAgility+"\n"
                +"精神:"+baseSpirit+"/"+totalSpirit+"\n"
                +"血量:"+nowBlood+"/"+blood+"\n"
                +"魔法:"+nowEnergy+"/"+energy+"\n"
                +"防御:"+fangyu+"";
    }
    public void drop() {

        // ��¼��ǰ��ʱ��
        this.stable=false;
        this.v=0f;
        preY = (int) this.position.y;
        lastTime = Sys.getTime();

    }
    public boolean died=false;
    public void died(){
        this.nowBlood=0;
        died=true;
        
    }
    public void changeProperty( ){
   // totalPower = basePower+

        acculateProperty(this.bodyComponent);

        this.totalPower+=this.basePower;

        totalAgility+=this.baseAgility;
        totalIntell+=this.baseIntell;

        totalSpirit+=this.baseSpirit;

        this.blood=this.totalPower;
        this.energy=this.totalIntell;



    }

    public void acculateProperty(Component component){
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
        }/*}catch(StackOverflowError e ){
            e.printStackTrace();
        }*/
    }
    public void flip(int y) {
        mark = y;
        limit = 0;
    }

    public void reset() {
        mark = limit = 0;
    }


    public float RotatedX, RotatedY, RotatedZ;

    public Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);


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
    }

    public void render2(){
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



        //GLApp.project(this.position.x, this.position.y+2, this.position.z, vector);

        //vector[1]=600-vector[1]-45;


    }
   // int vaoId;
    //int trianglesCount =0;
    private void build(){//当发生改变变的时候触发这里
        if(Switcher.SHADER_ENABLE){
            GL_Matrix translateMatrix=GL_Matrix.translateMatrix(position.x, position.y + 0.75f, position.z);
            //float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
            GL_Matrix rotateMatrix = GL_Matrix.rotateMatrix(0,-bodyAngle+3.14f/2/**3.14f/180*/,0);

            rotateMatrix=GL_Matrix.multiply(translateMatrix,rotateMatrix);
            //.getVao().getVertices()
          //  ShaderManager.livingThingShaderConfig.getVao().getVertices().rewind();
            bodyComponent.build(ShaderManager.livingThingShaderConfig,rotateMatrix);


        }else{

        }

         /*trianglesCount= floatBuffer.position()/8;
        if(trianglesCount<=0){
            LogUtil.println("trianglesCount can't be 0");
            System.exit(1);
        }*/
       // ShaderManager.livingThingShaderConfig.getVao().setVertices();
         // ShaderUtils.updateLivingVao(ShaderManager.livingThingShaderConfig.getVao());//createVAO(floatBuffer);
        /*if(ShaderManager.livingThingShaderConfig.getVao().getVaoId()<=0){
            LogUtil.println("vaoId can't be 0");
            System.exit(1);
        }*/
    }
    //FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(10240);
    public void select(){



            Shape shape = TextureManager.getShape("iron_pants");

            Component component= new Component(shape.getWidth(),shape.getHeight(),shape.getThick());
            component.setShape(shape);
            component.name="select";

        component.id=id*100+99;
            component.setOffset(new Point3f(0,4,0),new Point3f(0,0,0));
            //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
        bodyComponent.addChild(component);	//changeProperty()

    }
    public void unSelect(){
        if("select".equals(bodyComponent.children.get(bodyComponent.children.size()-1).id)){
            bodyComponent.children.remove(bodyComponent.children.size()-1);
        }





    }
    public void render(){
        /*GL11.glPushMatrix();try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/

        GL11.glTranslatef(position.x, position.y + 0.75f, position.z);
        float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
        GL11.glRotatef(angle, 0, 1, 0);/*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/
        GL11.glScalef(0.5f,0.5f,0.5f);/*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }
*/
        bodyComponent.render();
        GL11.glScalef(2,2,2);
        GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-position.x,-position.y-0.75f,-position.z);
       // GL11.glPopMatrix();



        //GLApp.project(this.position.x, this.position.y+2, this.position.z, vector);

        //vector[1]=600-vector[1]-45;

        //TextureManager.getTextureInfo("human_head_front").draw(null,(int)result[0],(int)result[1],headWidth,headHeight);
       /* GLApp.pushAttrib();
       GLApp.setOrthoOn();

        TextureManager.getTextureInfo("human_head_front").draw(null,(int)vector[0],(int)vector[1],40,40);

        GLApp.glFillRect((int)vector[0],(int)vector[1], 150, 20, 4, borderColor, whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+4,150*nowBlood/blood,20,lineWdith,borderColor,redColor);

        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150,20,lineWdith,borderColor,whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150*nowEnergy/energy,20,lineWdith,borderColor,blue);

        GLApp.print((int)vector[0],(int)vector[1]+30,"hello");
        GLApp.setOrthoOff();
        GLApp.popAttrib();*/
       /*try{
            Util.checkGLError();}catch (Exception e ){
            e.printStackTrace();
            LogUtil.println(e.getMessage());
            throw e;
        }*/
    }
    public void update(){
         this.dropControl();
        build();
        this.currentState.update();
    }
    public void renderShader(){
        /*if(ShaderManager.livingThingShaderConfig.getVao().getVaoId()==0){
            preRenderShader();

            //LogUtil.err("vao id 不能为空");
        }*/
       // assert ShaderManager.livingThingShaderConfig.getVao().getVaoId()!=0;
      // TextureManager.getTextureInfo("human_head_right").bind();

        //ShaderUtils.finalDraw(GamingState.instance.shaderManager.livingThingShaderConfig);
        /*glUseProgram(GamingState.instance.shaderManager.livingThingShaderConfig.getProgramId());
        Util.checkGLError();

       *//* int transformLoc= glGetUniformLocation(ProgramId,"transform");
        glUniformMatrix4(0,  false,matrixBuffer );
        matrixBuffer.rewind();*//*
        // glBindTexture(GL_TEXTURE_2D, this.textureHandle);
        glBindVertexArray(GamingState.instance.shaderManager.livingThingShaderConfig.getVao().getVaoId());

//        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        //3position 3normal 2texcoord 1 textureindex
        glDrawArrays(GL11.GL_TRIANGLES,0,GamingState.instance.shaderManager.livingThingShaderConfig.getVao().getPoints()/9);

        Util.checkGLError();
        glBindVertexArray(0);

        glUseProgram(0);*/
        // GL11.glPushMatrix();

       // GL11.glTranslatef(position.x, position.y + 0.75f, position.z);
       // float angle=GL_Vector.angleXZ(this.WalkDir , new GL_Vector(0,0,-1));
        //GL11.glRotatef(angle, 0, 1, 0);
       // GL11.glScalef(0.5f,0.5f,0.5f);

       // bodyComponent.renderShader().render();
        /*GL11.glScalef(2,2,2);
        GL11.glRotatef(-angle, 0, 1, 0);
        GL11.glTranslatef(-position.x,-position.y,-position.z);
        GL11.glPopMatrix();*/



        //GLApp.project(this.position.x, this.position.y+2, this.position.z, vector);

       // vector[1]=600-vector[1]-45;

        //TextureManager.getTextureInfo("human_head_front").draw(null,(int)result[0],(int)result[1],headWidth,headHeight);
       /* GLApp.pushAttrib();
       GLApp.setOrthoOn();

        TextureManager.getTextureInfo("human_head_front").draw(null,(int)vector[0],(int)vector[1],40,40);

        GLApp.glFillRect((int)vector[0],(int)vector[1], 150, 20, 4, borderColor, whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+4,150*nowBlood/blood,20,lineWdith,borderColor,redColor);

        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150,20,lineWdith,borderColor,whiteColor);
        GLApp.glFillRect((int)vector[0],(int)vector[1]+30,150*nowEnergy/energy,20,lineWdith,borderColor,blue);

        GLApp.print((int)vector[0],(int)vector[1]+30,"hello");
        GLApp.setOrthoOff();
        GLApp.popAttrib();*/
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




    public void beAttack(int damage){
        this.nowBlood-=damage;
        Document.needUpdate=true;
        Client.messages.push(new SayCmd(this.id,this.name,"被攻击 损失"+damage+"点血"));
        if(this.nowBlood<=0){
            this.nowBlood=0;
            AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            manager.clear(bodyComponent);
            manager.apply(bodyComponent,"died");

        }
    }

     IdleState currentState ;
    public void receive(GameCmd cmd ){
        currentState.receive(cmd);
    }

    public ItemDefinition getMainWeapon(){
        Component component = bodyComponent.findChild("rHumanHand");
        if(component.children.size()>0){
            Component weapon = component.children.get(0);
          return weapon.itemDefinition;
        }
        return null;
    }

    public void addBodyEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.BODY, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
    public void addLegEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.LEG, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addHandEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.HAND, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addShoeEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.FOOT, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }
    public void addHeadEquipStart(ItemDefinition itemCfg) {
        EquipCmd equipMentCmd = new EquipCmd(this, EquipPartType.HEAD, itemCfg);
        CoreRegistry.get(Client.class).send(equipMentCmd);
        //NetWorkManager.push(equipMentCmd);
    }

    public void addHeadEquip(ItemDefinition itemCfg)  {

        Component parent = 	bodyComponent.findChild("human_head");
        addChild(parent, "cap", itemCfg);
    }


    public void addChild(Component parent,String name,ItemDefinition itemCfg) {
        if(parent==null){
            LogUtil.err("parent node is null");
        }
        Component shoe = parent.findChild(name);
        if (shoe == null) {
            if (itemCfg == null) {
                return;
            } else {
                Shape shape = itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }
                Component component = new Component(shape.getWidth(), shape.getHeight(), shape.getThick());
                component.setShape(itemCfg.getShape());
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                changeProperty();
            }
        } else {

            if (itemCfg == null) {
                //删除shoe节点
                parent.removeChild(shoe);
            } else {
                parent.removeChild(shoe);
                Shape shape = itemCfg.getShape();
                if (shape == null) {
                    LogUtil.err("load shape from itemDefinition:" + itemCfg.getName() + "failed");

                }
                Component component = new Component(shape.getWidth(), shape.getHeight(), shape.getThick());
                component.setShape(itemCfg.getShape());
                component.name = name;

                component.setOffset(new Point3f(shape.getP_posi_x(), shape.getP_posi_y(), shape.getP_posi_z()), new Point3f(shape.getC_posi_x(), shape.getC_posi_y(), shape.getC_posi_z()));
                //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
                parent.addChild(component);
                changeProperty();
            }
        }
    }
    public void addShoeEquip(boolean leftFlag ,ItemDefinition itemCfg)  {
        Component parent ;
        if(leftFlag){
            parent = bodyComponent.findChild("human_l_b_leg");
        }else{
            parent = bodyComponent.findChild("human_r_b_leg");
        }
        //Component shoe =   bodyComponent.findChild("shoe");
        addChild(parent,"shoe",itemCfg);
    }
    public void addShoeEquip(ItemDefinition itemCfg)  {
        addShoeEquip(true, itemCfg);
        addShoeEquip(false, itemCfg);
    }
    public void addLegEquip(ItemDefinition itemCfg)  {
        Component parent = 	bodyComponent.findChild("human_l_b_leg");
        addChild(parent, "pants", itemCfg);
    }
    public void addBodyEquip(ItemDefinition itemCfg)  {
        Component parent = 	bodyComponent.findChild("human_body");
        addChild(parent, "armor", itemCfg);
    }

    public void addHandEquip(ItemDefinition itemCfg)  {
        //Shape shape = itemCfg.getShape();
        Component parent = 	bodyComponent.findChild("rHumanHand");
        addChild(parent, "weapon", itemCfg);
    }

    public void setPlayerStatus(PlayerStatus status){
        super.setPlayerStatus(status);
        this.addHeadEquip(TextureManager.getItemDefinition(ItemType.values()[status.getHeadEquip()]));
        this.addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[status.getBodyEquip()]));
        this.addHandEquip(TextureManager.getItemDefinition(ItemType.values()[status.getHandEquip()]));
        this.addLegEquip(TextureManager.getItemDefinition(ItemType.values()[status.getLegEquip()]));
        this.addShoeEquip(TextureManager.getItemDefinition(ItemType.values()[status.getShoeEquip()]));


    }

    public void changeAnimationState(String animationName){
        CoreRegistry.get(AnimationManager.class).clear(bodyComponent);
        CoreRegistry.get(AnimationManager.class).apply(bodyComponent,animationName);
    }
}
