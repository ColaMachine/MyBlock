package com.dozenx.game.network.server;

/**
 * Created by dozen.zhang on 2017/2/16.
 */
public class PlayerStatus {
    private float x;
    private String name;
    private String pwd;

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

    private float y;

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

    public int getBodyAngle() {
        return bodyAngle;
    }

    public void setBodyAngle(int bodyAngle) {
        this.bodyAngle = bodyAngle;
    }

    public int getHeadAngle() {
        return headAngle;
    }

    public void setHeadAngle(int headAngle) {
        this.headAngle = headAngle;
    }

    public int getHeadAngle2() {
        return headAngle2;
    }

    public void setHeadAngle2(int headAngle2) {
        this.headAngle2 = headAngle2;
    }

    public byte getHeadEquip() {
        return headEquip;
    }

    public void setHeadEquip(byte headEquip) {
        this.headEquip = headEquip;
    }

    public byte getBodyEquip() {
        return bodyEquip;
    }

    public void setBodyEquip(byte bodyEquip) {
        this.bodyEquip = bodyEquip;
    }

    public byte getHandEquip() {
        return handEquip;
    }

    public void setHandEquip(byte handEquip) {
        this.handEquip = handEquip;
    }

    public byte getShoeEquip() {
        return shoeEquip;
    }

    public void setShoeEquip(byte shoeEquip) {
        this.shoeEquip = shoeEquip;
    }

    public byte getLegEquip() {
        return legEquip;
    }

    public void setLegEquip(byte legEquip) {
        this.legEquip = legEquip;
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

    private float z;
    private int bodyAngle;
    private int headAngle;
    private int headAngle2;
    private byte headEquip;
    private byte bodyEquip;//省体装备
    private byte handEquip;//手部装备
    private byte shoeEquip;
    private byte legEquip;//腿部装备
    private int id;//id
    private long loginTime;//下线时间
    private long logoffTime;//登录时间
    private int targetId;//目标
    private boolean isplayer;//是否是玩家
    private byte type;//生物种类

}
