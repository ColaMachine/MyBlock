package com.dozenx.game.network.server.bean;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import com.dozenx.game.engine.element.bean.Component;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.Role.excutor.Executor;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.live.state.IdleState;
import com.dozenx.game.engine.live.state.State;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.ByteUtil;
import com.dozenx.util.TimeUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 16/9/16.
 */
public class LivingThingBean extends Role {

    public LivingThingBean(int id){



       /* this.getExecutor().setCurrentState(new IdleState(this));*/
        this.setId(id);//(int)(Math.random()*1000000);

        this.setExecutor(new Executor(this));
        this.getExecutor().setCurrentState(new IdleState(this));


        this.walkDir=new  GL_Vector(1,0,0);
        changeProperty();


        /*this.nowBlood=this.blood;
        this.nowEnergy=this.energy;*/
    }


   /* public LivingThingBean(){

    }*/


    /*public GL_Vector getPosition(){
        return this.getPosition());
    }*/

   // private BagBean bag =new BagBean();
    //private PlayerStatus status ;
    //protected IdleState currentState  = new WalkState(this);
    /*public LivingThingBean(PlayerStatus playerStatus){

        setPlayerStatus(playerStatus);





    }*/






    /*public void setPosition(GL_Vector position) {

        setPosition(position.x,position.y,position.z);
        //




    }*/
    /*public PlayerStatus getStatus(){
        return this.status;
    }*/
  /*  public void setPosition(float posx, float posy, float posz) {
        this.minX=posx-0.5f;
        this.minY=posy;
        this.minZ=posz-0.5f;

        this.maxX=posx+0.5f;
        this.maxY=posy+4;
        this.maxZ=posz+0.5f;
     //
        position.x= posx;
        position.y = posy;
        position.z = posz;
        this.setX(posx);
        this.setY(posy);
        this.setZ(posz);
       *//* this.position.x=posx;
        this.position.y=posy;
        this.position.z=posz;*//*



    }*/





   /* public void setPlayerStatus(PlayerStatus status){



        setName(status.getName());

        setPwd(status.getPwd());
        setBodyAngle(status.getBodyAngle());
        setHeadAngle(status.getHeadAngle());
        setHeadAngle2(status.getHeadAngle2());
        setHeadEquip( this.getItemBean(status.getHeadEquip()));
        setBodyEquip(status.getBodyEquip());
        setHandEquip(status.getHandEquip());
        setShoeEquip(status.getShoeEquip());
        setLegEquip(status.getLegEquip());
        setTargetId(status.getTargetId());
        setIsplayer(status.isplayer);

        this.id = status.getId();
        setPosition(status.x,status.y,status.z);

        this.headAngle =status.getHeadAngle();
        this.bodyAngle =status.getBodyAngle();
        this.headAngle2 = status.getHeadAngle2();




    }*/
    public static void main(String args[]){
        PlayerStatus playerStatus =new PlayerStatus();
        playerStatus.setId(4);
       byte[] byteAry= new PlayerSynCmd(playerStatus).toBytes();
        byte[] newAry =  ByteUtil.slice(byteAry,4,byteAry.length-4);


        ResultCmd result = new ResultCmd(0,newAry,1);

        byte[] resultAry = result.toBytes();
        ResultCmd receiveResultCmd =  new ResultCmd( resultAry);

        PlayerSynCmd cmd = new PlayerSynCmd(receiveResultCmd.getMsg());
        PlayerStatus newPlayerStatus= cmd.getPlayerStatus();
        System.out.println(newPlayerStatus.getId());

    }

    public void doSomeThing(ServerContext serverContext){
        this.getExecutor().getCurrentState().update();
            /*if (this.getTargetId() >0) {
                //检查举例 如果举例过远 放弃追逐
                LivingThingBean player= serverContext.getOnlinePlayerById(enemy.getTargetId());
                if(player==null){
                    enemy.setTargetId(0);
                }else
                if (GL_Vector.length(GL_Vector.sub(player.getPosition(), player.getPosition())) >15 ) {
                    if(TimeUtil.getNowMills()-enemy.getLastHurtTime()<10*1000) {//如果上次伤害还没超过多少时间

                        enemy.setTargetId(0);
                    }
                    //enemy.setTarget(player);
                }
            }

            for(LivingThingBean player: serverContext.getAllOnlinePlayer()){


                if (GL_Vector.length(GL_Vector.sub(enemy.getPosition(), player.getPosition())) < 5) {
                    enemy.setTargetId(player.getId());
                    //enemy.setTarget(player);
                }

            }*/

    }

    public void changeState(State humanState){
        if(this.getExecutor().getCurrentState()!=null &&getExecutor().getCurrentState() != humanState ){
            getExecutor().getCurrentState().dispose();

        }
        getExecutor().setCurrentState(humanState) ;
    }







    /*public  float bodyAngle =0  ;
    public float headAngle =0  ;
    public float headAngle2 =0  ;*/


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
    //public boolean exist=true;
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
    public int mark = 0;
    public
    int preY = 0;
    /*public void disapper(){
        this.exist=false;
    }*/
    public String getState(){
        return "力量:"+basePower+"/"+totalPower+"\n"
                +"智力:"+baseIntell+"/"+totalIntell+"\n"
                +"敏捷:"+baseAgility+"/"+totalAgility+"\n"
                +"精神:"+baseSpirit+"/"+totalSpirit+"\n"
                +"血量:"+nowHP+"/"+HP+"\n"
                +"魔法:"+nowMP+"/"+MP+"\n"
                +"防御:"+defense+"\n"
                +"攻击:"+pattack+"\n";

    }
    public void drop() {

        // ��¼��ǰ��ʱ��
        this.stable=false;
        this.v=0f;
        preY = (int) this.position.y;
        lastTime = TimeUtil.getNowMills();

    }
    //public boolean died=false;
    public void died(){
        this.nowHP=0;
        //died=true;

    }

    public void acculateProperty(Component component){
        //try{
        if(component!=null && component.children!=null)
            for(int i=component.children.size()-1;i>=0;i--){
                Component child = component.children.get(i);
                if(child.itemBean !=null){
                    totalPower +=child.itemBean.itemDefinition.getStrenth();
                    totalAgility+=child.itemBean.itemDefinition.getAgile();
                    totalIntell+=child.itemBean.itemDefinition.getIntelli();
                    totalSpirit+=child.itemBean.itemDefinition.getSpirit();
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


   // public float RotatedX, RotatedY, RotatedZ;

    /*public Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);
*/

    public void dropControl() {
        if(!Switcher.IS_GOD)
            if (!this.stable) {
                long t = TimeUtil.getNowMills() - this.lastTime;//�˶���ʱ��
                if(t<=0)return;
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

/*

        Shape shape = TextureManager.getShape("iron_pants");

        Component component= new Component(shape.getWidth(),shape.getHeight(),shape.getThick());
        component.setShape(shape);
        component.name="select";

        component.id=getId()*100+99;
        component.setOffset(new Point3f(0,4,0),new Point3f(0,0,0));
        //Connector connector = new Connector(component,new GL_Vector(shape.getP_posi_x(),shape.getP_posi_y(),shape.getP_posi_z()),new GL_Vector(shape.getC_posi_x(),shape.getC_posi_y(),shape.getC_posi_z()));
        getModel().bodyComponent.addChild(component);	//changeProperty()*/

    }
    public void unSelect(){
      /*  if("select".equals(getModel().bodyComponent.children.get(getModel().bodyComponent.children.size()-1).id)){
            getModel().bodyComponent.children.remove(getModel().bodyComponent.children.size()-1);
        }*/





    }

    public void update(){
        this.dropControl();
       // this.getModel().build();
        this.getExecutor().getCurrentState().update();
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
        this.nowHP-=damage;
        this.setLastHurtTime(TimeUtil.getNowMills());
        Document.needUpdate=true;
        Client.messages.push(new SayCmd(this.getId(),this.name,"被攻击 损失"+damage+"点血"));
        if(this.nowHP<=0){
            this.nowHP=0;
         /*   AnimationManager manager = CoreRegistry.get(AnimationManager.class);
            manager.clear(getModel().bodyComponent);
            manager.apply(getModel().bodyComponent,"died");*/

        }
    }

    //IdleState currentState ;
    public void receive(GameCmd cmd ){
        this.getExecutor().getCurrentState().receive(cmd);
    }

    public ItemType getMainWeapon(){
        /*Component component = bodyComponent.findChild("rHumanHand");
        if(component.children.size()>0){
            Component weapon = component.children.get(0);
          return weapon.itemDefinition;
        }*/
        return getHandEquip();
    }



    /*public void setPlayerStatus(PlayerStatus status){
        super.setPlayerStatus(status);
        this.addHeadEquip(TextureManager.getItemDefinition(ItemType.values()[status.getHeadEquip()]));
        this.addBodyEquip(TextureManager.getItemDefinition(ItemType.values()[status.getBodyEquip()]));
        this.addHandEquip(TextureManager.getItemDefinition(ItemType.values()[status.getHandEquip()]));
        this.addLegEquip(TextureManager.getItemDefinition(ItemType.values()[status.getLegEquip()]));
        this.addShoeEquip(TextureManager.getItemDefinition(ItemType.values()[status.getShoeEquip()]));


    }*/

    public void changeAnimationState(String animationName){
    if(GamingState.player!=null) {
        //CoreRegistry.get(AnimationManager.class).clear(getModel().bodyComponent);
        if (animationName != null) {
            CoreRegistry.get(AnimationManager.class).apply(getModel().rootComponent, animationName);
        }
    }
    }

    public void attack(){

/*
        CoreRegistry.get(AnimationManager.class)
                .apply(getModel().bodyComponent,"attack");*/
    }

    /*public void beAttack(int harm){
        super.beAttack(harm);
*//*
        CoreRegistry.get(AnimationManager.class)
                .apply(getModel().bodyComponent,"beattack");*//*

    }*/




    public void setInfo( PlayerStatus info ){
        super.setInfo(info);
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
