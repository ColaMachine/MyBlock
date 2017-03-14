package com.dozenx.game.engine.live.state;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.skill.Ball;
import com.dozenx.game.engine.command.*;
import com.dozenx.game.engine.item.ItemUtil;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import com.dozenx.game.network.server.bean.LivingThingBean;
import com.dozenx.util.TimeUtil;
import core.log.LogUtil;
import glmodel.GL_Vector;

/**
 * Created by luying on 17/3/5.
 */
public class State {
    public void dispose(){

    }
    public void update(){

    }
    public State(){

    }
    protected LivingThingBean livingThing;
    int intervalTime=500;
    public State(LivingThingBean livingThing){
        this.livingThing = livingThing;
    }
    public void receive(GameCmd gameCmd){
        if(gameCmd instanceof DropCmd){
            DropCmd cmd = (DropCmd ) gameCmd;

           // 遍历所有的物品
            if(livingThing== GamingState.player && cmd.getUserId() == livingThing.getId()){//是主角掉落的
                /* ItemBean[] itemBeens = livingThing.getItemBeans();
               for(int i=0;i<itemBeens.length;i++){
                ItemBean nowBean = itemBeens[i];
                if(nowBean!=null && nowBean.getId()== cmd.getItemId()){
                    itemBeens[i]=null;
                    if(livingThing== GamingState.player){
                        CoreRegistry.get(BagController.class).refreshBag();
                        break;
                    }


                }
            }*/
            }else{//就是其他人掉落的 或者是数据同步到你机器上的 早就掉落的

            }
            ItemManager.add(new Ball(cmd.getItemId(),new GL_Vector(cmd.getX(),cmd.getY(),cmd.getZ()),livingThing.getWalkDir().getClone(),0, cmd.getItemType(),livingThing));
            //livingThing.getItemBeans()[24]=null;




        }else if(gameCmd instanceof PickCmd){
            PickCmd cmd = (PickCmd ) gameCmd;

            /*// 遍历所有的物品
            if(livingThing== GamingState.player && cmd.getUserId() == livingThing.getId()){//是主角掉落的
                ItemBean[] itemBeens = livingThing.getItemBeans();
                for(int i=0;i<itemBeens.length;i++){
                    ItemBean nowBean = itemBeens[i];
                    if(nowBean==null && nowBean.getId()== cmd.getItemId()){
                        itemBeens[i]=null;
                        if(livingThing== GamingState.player){
                            CoreRegistry.get(BagController.class).refreshBag();
                            break;
                        }


                    }
                }
            }else{//就是其他人掉落的 或者是数据同步到你机器上的 早就掉落的

            }*/
            ItemManager.removeWorldItem(cmd.getItemId());
            //livingThing.getItemBeans()[24]=null;




        }else

        if(gameCmd instanceof AttackCmd){

            Long notTime = TimeUtil.getNowMills();
            if(notTime-livingThing.getLastAttackTime() >intervalTime){
                livingThing.setLastAttackTime(notTime);
            }else{
                return;
            }
            /*Wep
            SkillDefinition skill = livingThing.getNowSkill();
            if(livingThing.getNowSkill()){

            }*/
            AttackCmd cmd =(AttackCmd) gameCmd;
            if(cmd.getAttackType()== AttackType.KAN){
                CoreRegistry.get(AnimationManager.class).apply(livingThing.getModel().bodyComponent,"kan");
                livingThing.getTarget().beAttack(cmd.getAttackValue());
                Document.needUpdate=true;
                //getExecutor().getCurrentState().dispose();
                //livingThing.getExecutor().getModel().
            }
            /*if(this.livingThing!=null && this.livingThing.getTarget()!=null ) {
                GL_Vector direction =  GL_Vector.sub(this.livingThing.getTarget().position,
                        this.livingThing.position);
                if(GL_Vector.length(direction)>220){
                    LogUtil.println("超出距离");
                    return;
                }
                if(ItemUtil.isFarWeapon(livingThing.getHandEquip()*//*.getItemDefinition().getItemType()*//*.ordinal())){
                    this.livingThing.changeState( new ShootState(this.livingThing));

                }
               *//* if(livingThing.getMainWeapon()!=null && livingThing.getMainWeapon().getName().equals("arch")){
                   this.livingThing.changeState( new IdleShootState(this.livingThing));

                }*//*

            }*/

            }
        }




}
