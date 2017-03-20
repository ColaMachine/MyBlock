package com.dozenx.game.engine.Role.bean;

import com.dozenx.game.network.server.bean.PlayerStatus;

/**
 * Created by luying on 17/3/5.

 */
public class AttackProperties extends BaseProperties{
    Long lastAttackTime=0l;
    public void getInfo(PlayerStatus info) {
        super.getInfo(info);
        info.HP=this.getHP();
        info.nowHP =this.getNowHP();
        info.nowMP =this.getNowMP();
        info.MP =this.getMP();
        /*if(info.HP>0){
            this.died=false;;

        }*/
    }

    public void setInfo(PlayerStatus info ){
        super.setInfo(info);
       this.setHP(info.HP);
       this.setNowHP(info.nowHP);
       this.setNowMP( info.nowMP );
       this.setMP( info.MP);
        /*if(info.HP>0){
            this.died=false;;
        }*/
    }

    private long lastHurtTime = 0;

    public int HP;  //  血量



    public int getBasePower() {
        return basePower;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    public int getBaseIntell() {
        return baseIntell;
    }

    public void setBaseIntell(int baseIntell) {
        this.baseIntell = baseIntell;
    }

    public int getBaseAgility() {
        return baseAgility;
    }

    public void setBaseAgility(int baseAgility) {
        this.baseAgility = baseAgility;
    }

    public int getBaseSpirit() {
        return baseSpirit;
    }

    public void setBaseSpirit(int baseSpirit) {
        this.baseSpirit = baseSpirit;
    }

    public int getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(int totalPower) {
        this.totalPower = totalPower;
    }

    public int getTotalIntell() {
        return totalIntell;
    }

    public void setTotalIntell(int totalIntell) {
        this.totalIntell = totalIntell;
    }

    public int getTotalAgility() {
        return totalAgility;
    }

    public void setTotalAgility(int totalAgility) {
        this.totalAgility = totalAgility;
    }

    public int getTotalSpirit() {
        return totalSpirit;
    }

    public void setTotalSpirit(int totalSpirit) {
        this.totalSpirit = totalSpirit;
    }

    public boolean isDied() {
        return nowHP<=0;
    }

    /*public void setDied(boolean died) {
        this.died = died;
    }*/

    public int MP; //  能量


    public int pattack ;//物攻
    public int mattack ;//魔攻

    public int defense;

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getPattack() {
        return pattack;
    }

    public void setPattack(int pattack) {
        this.pattack = pattack;
    }

    public int getMattack() {
        return mattack;
    }

    public void setMattack(int mattack) {
        this.mattack = mattack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getNowHP() {
        return nowHP;
    }

    public void setNowHP(int nowHP) {
        this.nowHP = nowHP;
    }

    public int getNowMP() {
        return nowMP;
    }

    public void setNowMP(int nowMP) {
        this.nowMP = nowMP;
    }

    public int nowHP;        //血量
    public int nowMP;       //蓝量



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
                +"血量:"+nowHP+"/"+HP+"\n"
                +"魔法:"+nowMP+"/"+MP+"\n"
                +"防御:"+defense+"";
    }

   // private boolean died=false;
    public void died(){
        this.nowHP=0;
        //died=true;

    }

    public Long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(Long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }


    public void changeProperty( ){
        // totalPower = basePower+

        // acculateProperty(this.getExecutor().getModel().bodyComponent);

        this.totalPower=this.basePower;

        totalAgility=this.baseAgility;
        totalIntell=this.baseIntell;

        totalSpirit=this.baseSpirit;

        this.HP=this.totalPower;
        this.MP=this.totalIntell;




    }
}
