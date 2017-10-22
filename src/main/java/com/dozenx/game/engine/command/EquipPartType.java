package com.dozenx.game.engine.command;

/**
 * Created by dozen.zhang on 2017/2/13.
 */
public enum EquipPartType {

    HEAD("human_head"),
    BODY("human_body"),
    ARM("arm"),
    LARM("human_left_hand"),
    RARM("human_right_hand"),


    HAND("hand"),
    RHAND("human_right_hand_equip"),
    LHAND("lhand"),


    LEG("human_leg"),

    LLEG("human_left_leg"),
    RLEG("human_right_leg"),
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
