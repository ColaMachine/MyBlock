package com.dozenx.game.engine.Role.bean;

import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.
 */
public class AttackProperties extends BaseProperties{
    public void getInfo(PlayerStatus info) {
        super.getInfo(info);

    }

    public void setInfo(PlayerStatus info ){
        super.setInfo(info);

    }

    private long lastHurtTime = 0;

    public int blood;  //  血量
    public int energy; //  能量


    public int physicAttack ;//物攻
    public int mgicAttack ;//魔攻

    public int fangyu;

    public int nowBlood;        //血量
    public int nowEnergy;       //蓝量



    public int basePower=100;      //  基础力量
    public int baseIntell=100;     //  基础智力
    public int baseAgility=100;    //  基础敏捷
    public int baseSpirit=100;     //  基础精神



    public int totalPower;          //  现在的力量值
    public int totalIntell;         //  智力值
    public int totalAgility;        //  敏捷值
    public int totalSpirit;         //  精神值


    public long getLastHurtTime() {
        return lastHurtTime;
    }
    public void setLastHurtTime(long lastHurtTime) {
        this.lastHurtTime = lastHurtTime;
    }

    public String getAttackPropertiesStr(){
        return "力量:"+basePower+"/"+totalPower+"\n"
                +"智力:"+baseIntell+"/"+totalIntell+"\n"
                +"敏捷:"+baseAgility+"/"+totalAgility+"\n"
                +"精神:"+baseSpirit+"/"+totalSpirit+"\n"
                +"血量:"+nowBlood+"/"+blood+"\n"
                +"魔法:"+nowEnergy+"/"+energy+"\n"
                +"防御:"+fangyu+"";
    }

    public boolean died=false;
    public void died(){
        this.nowBlood=0;
        died=true;

    }
}
