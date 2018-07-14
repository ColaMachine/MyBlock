package cola.machine.game.myblocks.skill;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.physic.BulletResultDTO;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.bean.Role;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.BeAttackCmd;
import com.dozenx.game.engine.command.ChunkRequestCmd;
import com.dozenx.game.engine.command.DropCmd;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.ui.died.view.DiedPanel;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.util.MathUtil;
import com.dozenx.util.RandomUtil;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import java.util.*;

/**
 * Created by luying on 16/9/25.
 */
public class AttackManager {
    public BulletPhysics bulletPhysics;
    public AttackManager(BulletPhysics bulletPhysics,LivingThingManager livingThingManager){
        this.livingThingManager =livingThingManager;
        this.bulletPhysics = bulletPhysics;
        CoreRegistry.put(AttackManager.class, this);
    }
    private LivingThingManager livingThingManager;
    public static List<DropBall> dropList =new ArrayList<>();
    public static List<AttackBall> attackList =new ArrayList<>();
    public static List<Ball> drawThings =new ArrayList<>();
    public static BaseBlock selectThing =null;

    public static List<Ball> diedList =new ArrayList<>();
    Vector4f color =new Vector4f(1,0,0,1);
    public static Queue<TimeString> texts= new LinkedList<TimeString>();
    public static void addDrop(DropBall ball){
        dropList.add(ball);
    }

    public static void addAttack(AttackBall ball){
        attackList.add(ball);
    }
    public static void addText(TimeString timeString){
        texts.offer(timeString);
    }
    GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
    public  void update(){

        //
        ShaderManager.anotherShaderConfig.getVao().getVertices().rewind();
        if(selectThing!=null){
          //  ShaderUtils.draw3dColorBox(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),selectThing.x,selectThing.y,selectThing.z,new GL_Vector(0,0,0),selectThing.width,selectThing.height,selectThing.thick,0.5f);
            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(),selectThing.x,selectThing.y,selectThing.z,selectThing.width,selectThing.height,selectThing.thick);
            GamingState.editEngine.lineNeedUpdate=true;
        }else{
            ShaderUtils.draw3dColorBoxLine(ShaderManager.lineShaderConfig.getVao(),0,0,0,0,0,0);
            GamingState.editEngine.lineNeedUpdate=true;
        }
        //attackball 先在空中飞行 碰到物体后 转入dropthing当中 比如箭飞行过去 然后碰到人就表示射中了 会粘在人的身体上 射到了方块上 会粘在方块上
         for(int i=diedList.size()-1;i>=0;i--) {
            Ball ball = diedList.get(i);
            if(ball.died) {
                //atta.remove(ball);
                diedList.remove(i);
            }else{
                ball.update(ShaderManager.anotherShaderConfig);
            }

        }
        for(int i=attackList.size()-1;i>=0;i--){
            Ball ball = attackList.get(i);
            GL_Vector vector = ball.position;

            if(ball.species!= livingThingManager.player.species && ball.from!= livingThingManager.player&& GL_Vector.length(new GL_Vector(vector,livingThingManager.player.getPosition()))<1){
                livingThingManager.player.beAttack(1);
                livingThingManager.player.receive(new BeAttackCmd(ball.from.getId(),ball.itemDefinition,livingThingManager.player.getId(),ball.direction));
                ball.readyDied=true;

            }else
            for(int j=livingThingManager.livingThings.size()-1;j>=0;j--){
                LivingThing livingThing = livingThingManager.livingThings.get(j);
                //LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");
                if(ball.species!= livingThing.species && ball.from!= livingThing&& GL_Vector.length(new GL_Vector(vector,livingThing.getPosition()))<1){
                    livingThingManager.livingThings.get(j).beAttack(10);
                    livingThingManager.livingThings.get(j).receive(new BeAttackCmd(ball.from.getId(),ball.itemDefinition,livingThing.getId(),ball.direction));
                    ball.readyDied=true;
                    //stexts.offer(new TimeString(5+""));
                    //Vector2f xy = OpenglUtils.wordPositionToXY(projection,livingThing.getPosition(),GamingState.instance.camera.Position,GamingState.instance.camera.getViewDir());
                    //texts.offer(new TimeString(5+"",xy.x*Constants.WINDOW_WIDTH,xy.y*Constants.WINDOW_HEIGHT));
                    //livingThingManager.livingThings.get(j).beAttack(5);
                }
            }
           /** for(int j=0;j<livingThingManager.livingThings.size();j++){
                if(livingThingManager.livingThings.get(j).getPosition().sub(ball.getPosition()).length()<1){
                    ball.died=true;
                    livingThingManager.livingThings.get(j).beAttack(10);
                }
            }**/
            ball.update(ShaderManager.anotherShaderConfig);


            if(ball.readyDied){
                diedList.add(attackList.remove(i));
            }
        }
        ShaderManager.uifloatShaderConfig.getVao().getVertices().rewind();
      // Iterator<TimeString> shanghais = texts.iterator();
        int index=0;
        Long now= TimeUtil.getNowMills();
        while (texts.peek()!=null && (now -texts.peek().getStartTime()) >5000) {


                texts.poll();

        }
        Iterator<TimeString> shanghais = texts.iterator();
        //伤害文字
        while (shanghais.hasNext()){
            index++;
            TimeString shanghai = shanghais.next();
                //GL11.glColor4f(1, 1, 1, 1);
            color.w= 1-(now- shanghai.getStartTime())/5000;
                ShaderUtils.printText(shanghai.getText(),(int)shanghai.x,(int)shanghai.y -(int)(now- shanghai.getStartTime())*100/5000,0,24,color,ShaderManager.uifloatShaderConfig );
                OpenglUtils.checkGLError();
                //GLApp.print((int)minX,(int)minY,this.innerText);
        }

        //ShaderUtils.createVao(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao(),anotherShaderConfig);


    }
    int anotherShaderConfig[] = new int[]{3,3,3,1};
    int uiFloatShaderConfig[] = new int[]{3,2,1,4};

   /* public static void update2(){
        for(int i=list.size()-1;i>=0;i--){
            Ball ball = list.get(i);
            GL_Vector vector = ball.position;
            for(int j=LivingThingManager.livingThings.size()-1;j>=0;j--){
                //LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");

//                LogUtil.println(LivingThingManager.livingThings.get(j).position.length(vector)+"");

                if(GL_Vector.length(new GL_Vector(vector,LivingThingManager.livingThings.get(j).getPosition()))<0.5){
                    ball.died=true;
                    LivingThingManager.livingThings.get(j).beAttack(5);
                }
            }
            ball.update();
            if(ball.died){
                list.remove(i);
            }
        }

    }*/
    public  void render(){
        for(int i=attackList.size()-1;i>=0;i--){
            Ball ball = attackList.get(i);
            ball.render();
        }
        ShaderUtils.freshVao(ShaderManager.uifloatShaderConfig,ShaderManager.uifloatShaderConfig.getVao());

        ShaderUtils.freshVao(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao());
        ShaderUtils.finalDraw(ShaderManager.anotherShaderConfig,ShaderManager.anotherShaderConfig.getVao());
//        ShaderUtils.tempfinalDraw(ShaderManager.terrainShaderConfig,ShaderManager.anotherShaderConfig.getVao());
        ShaderUtils.finalDraw(ShaderManager.uifloatShaderConfig,ShaderManager.uifloatShaderConfig.getVao());

    }
    public long lastAttackTime;
    long now;
    public void attack(Player player){
         now = TimeUtil.getNowMills();
        if(now-lastAttackTime>500){
            lastAttackTime= now;
        }else{
            return;
        }
        //选中一个colorblock 作为当前的block
        ChunkProvider localChunkProvider = CoreRegistry
                .get(ChunkProvider.class);
        boolean delete = true;
        //获取当前的block item
        //
        BulletResultDTO arr  = bulletPhysics.rayTrace2(player.viewPosition , player.getViewDir(),4, "soil", delete);//从当前的头上面开始沿着方向选取一个目标

        if(arr!=null && arr.targetBlock!=null){//如果选到了目标
            //  if(arr.targetBlock instanceof ColorBlock){
            //  ((ColorBlock) arr.targetBlock).green =1 ;




            int blockWorldX  = (int)Math.floor( arr.absoluteTargetPoint.x);
            int blockWorldY  = (int) Math.floor(arr.absoluteTargetPoint.y);
            int blockWorldZ  =  (int)Math.floor( arr.absoluteTargetPoint.z);
            if(selectThing!=null &&blockWorldX  == selectThing.x && blockWorldY == selectThing.y && blockWorldZ ==selectThing.z){//还是老的目标了
                selectThing.blood--;
                if(selectThing.blood<=0){

                    ItemDefinition definition = ItemManager.getItemDefinition(selectThing.id);
                    definition.beDestroyed(selectThing,arr.targetChunX,0,arr.targetChunZ,blockWorldX,blockWorldY,blockWorldZ);
                    //先删除方块再扔到地上

                    ChunkRequestCmd cmd = new ChunkRequestCmd(new Vector3i(arr.targetChunX, 0, arr.targetChunZ));
                    cmd.cx = MathUtil.getOffesetChunk( selectThing.x);
                    cmd.cy = (int) selectThing.y;
                    cmd.cz = MathUtil.getOffesetChunk( selectThing.z);

                    if (cmd.cy < 0) {
                        LogUtil.err("y can't be <0 ");
                    }
                    cmd.type = 2;
                    //blockType 应该和IteType类型联系起来
                    cmd.blockType = 0;

                    CoreRegistry.get(Client.class).send(cmd);

                    //删除方块
                    DropCmd dropCmd = new DropCmd(0, RandomUtil.getRandom(5),selectThing.id,now);
                    dropCmd.setX(selectThing.x);
                    dropCmd.setY(selectThing.y);
                    dropCmd.setZ(selectThing.z);
                    CoreRegistry.get(Client.class).send(dropCmd );
                }
            }else {//选择到了新的目标
                selectThing = arr.targetBlock;//new ColorBlock(arr.targetChunX*16+(int)arr.targetPoint.x,(int)arr.targetPoint.y,arr.targetChunZ*16+(int)arr.targetPoint.z);
                selectThing.x = blockWorldX;
                selectThing.y = blockWorldY;
                selectThing.z =  blockWorldZ;
            }
            //LogUtil.println(arr.targetPoint+"");
            //  }
        }
    }

    public void currentChooseObj(Player player){
        now = TimeUtil.getNowMills();
        if(now-lastAttackTime>500){
            lastAttackTime= now;
        }else{
            return;
        }
        //选中一个colorblock 作为当前的block
        ChunkProvider localChunkProvider = CoreRegistry
                .get(ChunkProvider.class);
        boolean delete = true;
        //获取当前的block item
        //
        BulletResultDTO arr  = bulletPhysics.rayTrace2(player.viewPosition , player.getViewDir(),4, "soil", delete);//从当前的头上面开始沿着方向选取一个目标

        if(arr!=null && arr.targetBlock!=null) {//如果选到了目标
            selectThing = arr.targetBlock;//new ColorBlock(arr.targetChunX*16+(int)arr.targetPoint.x,(int)arr.targetPoint.y,arr.targetChunZ*16+(int)arr.targetPoint.z);
            selectThing.x =  (int)Math.floor(arr.absoluteTargetPoint.x);
            selectThing.y =  (int)Math.floor( arr.absoluteTargetPoint.y);
            selectThing.z = (int) Math.floor( arr.absoluteTargetPoint.z);
        }else{
            selectThing=null;
        }
    }
    public static int computeDamage(Role source,Role target){
        int gongji = source.getTotalPower();
        int fangyu = target.getTotalAgility();
        double percent = fangyu*0.06/(fangyu*0.06+1);
        int damage = (int)(gongji*(1-percent));
        return damage;
    }

    public static void addAttackEvent(LivingThingBean source,LivingThingBean target){
        int damage =  AttackManager.computeDamage(source,target);

        Vector2f screenXY= OpenglUtils.wordPositionToXY(ShaderManager.projection,target.getPosition().copyClone().add(new GL_Vector(0,target.getExecutor().getModel().getRootComponent().height,0)), GamingState.getInstance().camera.Position,GamingState.getInstance().camera.ViewDir);
        screenXY.x *= Constants.WINDOW_WIDTH;
        screenXY.y *= Constants.WINDOW_HEIGHT;

        addText(new TimeString("-"+damage,screenXY.x,screenXY.y));
        target.beAttack(damage);
        if(target.isDied() && target.getId() == GamingState.player.getId()){
            CoreRegistry.get(DiedPanel.class).setVisible(true);
        }
        source.changeAnimationState("attack");
        //AttackManager.addAttack(ball);//这里发现一个问题是 宠物发起的攻击会砸死自己
        source.setLastAttackTime(TimeUtil.getNowMills());

        target.changeAnimationState("beattack");
        //AttackManager.addAttack(ball);//这里发现一个问题是 宠物发起的攻击会砸死自己



    }

}
