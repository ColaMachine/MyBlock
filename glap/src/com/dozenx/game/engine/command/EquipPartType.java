package com.dozenx.game.engine.command;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum EquipPartType {

    HEAD("human_head"),
    BODY("human_body"),
    ARM("arm"),
    LARM("larm"),
    RARM("rarm"),


    HAND("hand"),
    RHAND("rhand"),
    LHAND("lhand"),


    LEG("human_leg"),

    LLEG("lleg"),
    RLEG("rleg"),
    FOOT("foot"),



    LFOOT("lfoot"),
    RFOOT("rfoot"),

    ARMOR("armor"),
    PANTS("pants"),
    SHOOE("shooe"),

    ;
    String name ;
     EquipPartType(String name){
        this.name =name;
    }
    public String getName(){
        return name;
    }
    public  byte getType(){
        return (byte)this.ordinal();
    }

}
