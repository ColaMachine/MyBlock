package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.AttackBall;
import cola.machine.game.myblocks.skill.AttackManager;
import cola.machine.game.myblocks.skill.Ball;
import cola.machine.game.myblocks.skill.TimeString;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

import javax.vecmath.Vector2f;

/**
 * Created by luying on 17/3/5.
 */
public class State {
    boolean disposed = false;

    public void dispose() {
        this.disposed = true;
    }

    long lastTime = 0;
    // long beginTime = 0;
    //GL_Vector walkDir = null;
    float distance = 0;
    long nowTime = 0;

    public void update() {
        //LogUtil.println("no state ");
      /*  try {
            Thread.sleep(200);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public GL_Vector nowPosition(long nowTime, long lastTime, float speed, GL_Vector from, GL_Vector to) {
        GL_Vector walkDir = GL_Vector.sub(to, from).normalize();
        GL_Vector newPosition = GL_Vector.add(from, GL_Vector.multiplyWithoutY(walkDir,
                speed / 10 * (nowTime - lastTime) / 1000));
        return newPosition;
    }

    public State() {


    }

    protected LivingThingBean livingThing;
    int intervalTime = 500;

    public State(LivingThingBean livingThing) {
        this.livingThing = livingThing;
    }

    public void receive(GameCmd gameCmd) {

        this.livingThing.changeState(this.getStateByCmd(gameCmd));//当接受了新的命令 就从当前的状态切换成制定的状态
    }

    /**
     * 向在这里做1个集中管理 控制所有状态的转换
     * @param gameCmd
     * @return
     */
    public State getStateByCmd(GameCmd gameCmd){//,GameCmd gameCmd
        CmdType type =gameCmd.getCmdType();
        if(type== CmdType.DIED){
            return new DiedState(this.livingThing,gameCmd);
        }else if( type== CmdType.JUMP){
            return new JumpState(this.livingThing,gameCmd);
        }else if( type== CmdType.ATTACK){
            AttackCmd attackCmd = (AttackCmd) gameCmd;
            if(attackCmd.getTargetId()==livingThing.getId()){
                return new BeAttackState(this.livingThing,attackCmd);
            }else{
                return new AttackState(this.livingThing,attackCmd);
            }


        }else if(type==CmdType.CHASE){
            return new ChaseState(this.livingThing,gameCmd);
        }else{
            return new IdleState(this.livingThing,gameCmd);
        }

    }
    public void preCheck() {
        if (this.disposed) {
            LogUtil.err("已经结束了");
        }
    }

    public void diedProcessor() {
        if (livingThing.isDied()) {
            this.livingThing.changeState(new DiedState(this.livingThing,new DiedCmd(livingThing.getId())));
            LogUtil.println(livingThing.getId() + "已经死去");
        }
    }

    public void noTargetProcessor() {
        if (livingThing.getTarget() == null && livingThing.getTargetId() == 0) {
            this.livingThing.changeState(new IdleState(livingThing,null));
            LogUtil.println(livingThing.getId() + "正变得平静");
        }
    }

    public void commonCmdReceive(GameCmd gameCmd) {
        if (gameCmd.getCmdType() == CmdType.DIED) {

            this.livingThing.changeState(new DiedState(this.livingThing,null));

        } else if (gameCmd.getCmdType() == CmdType.ATTACK) {

            Long notTime = TimeUtil.getNowMills();
            if (notTime - livingThing.getLastAttackTime() > intervalTime) {
                livingThing.setLastAttackTime(notTime);
            } else {
                return;
            }
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            AttackCmd cmd = (AttackCmd) gameCmd;
            if (cmd.getAttackType() == AttackType.KAN) {
                livingThing.changeAnimationState("kan");

                livingThing.getTarget().beAttack(cmd.getAttackValue());
                Document.needUpdate = true;
                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            } else if (cmd.getAttackType() == AttackType.ARROW) {
                this.livingThing.changeState(new ShootState(this.livingThing));
                livingThing.getTarget().beAttack(cmd.getAttackValue());
            }

        }
    }

    public void commonUpdate() {

    }


}
