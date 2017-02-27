package com.dozenx.game.network.server.bean;

import com.dozenx.game.engine.command.ItemType;

/**
 * Created by dozen.zhang on 2017/2/16.
 */
public class PlayerStatus {
    private float x;
    private float y;
    private float z;

    private String name;
    private String pwd;
    private float bodyAngle;
    private float headAngle;
    private float headAngle2;
    private int headEquip;
    private int bodyEquip;//省体装备
    private int handEquip;//手部装备
    private int shoeEquip;
    private int legEquip;//腿部装备
    private int id;//id
    private long loginTime;//下线时间
    private long logoffTime;//登录时间
    private int targetId;//目标
    private boolean isplayer;//是否是玩家
    private byte type;//生物种类


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


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getHeadEquip() {
        return headEquip;
    }

    public void setHeadEquip(int headEquip) {
        this.headEquip = headEquip;
    }

    public int getBodyEquip() {
        return bodyEquip;
    }

    public void setBodyEquip(int bodyEquip) {
        this.bodyEquip = bodyEquip;
    }

    public int getHandEquip() {
        return handEquip;
    }

    public void setHandEquip(int handEquip) {
        this.handEquip = handEquip;
    }


    public int getShoeEquip() {
        return shoeEquip;
    }

    public void setShoeEquip(int shoeEquip) {
        this.shoeEquip = shoeEquip;
    }

    public int getLegEquip() {
        return legEquip;
    }

    public float getBodyAngle() {
        return bodyAngle;
    }

    public void setBodyAngle(float bodyAngle) {
        this.bodyAngle = bodyAngle;
    }

    public float getHeadAngle() {
        return headAngle;
    }

    public void setHeadAngle(float headAngle) {
        this.headAngle = headAngle;
    }

    public float getHeadAngle2() {
        return headAngle2;
    }

    public void setHeadAngle2(float headAngle2) {
        this.headAngle2 = headAngle2;
    }

    public void setLegEquip(int legEquip) {
        this.legEquip = legEquip;
    }

    public boolean isplayer() {
        return isplayer;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getLogoffTime() {
        return logoffTime;
    }

    public void setLogoffTime(long logoffTime) {
        this.logoffTime = logoffTime;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public boolean isIsplayer() {
        return isplayer;
    }

    public void setIsplayer(boolean isplayer) {
        this.isplayer = isplayer;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }



}
