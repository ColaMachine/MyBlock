package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/13.
 */
public enum ItemType {
    NULL,fur_shoulder,fur_helmet,chain_helmet,stone_helmet,iron_helmet,gold_helmet,flint,firestone,coal,rope,seed,apple,gold_apple,egg,sugar,ice_ball,head_slot,fur_armor,chain_armor,iron_armor,diamond_armoriron_cloth,gold_armor,arch,stone_bar,iron_bar,feather,wheat,photo_frame,sugarcane,bone,cacke,rubber_ball,body_slot,fur_pants,chain_pants,diamond_pants,iron_pants,gold_pants,arrow,dorlach,gold_bar,clay,bread,billboard,wood_door,iron_door,bed,pant_slot,fur_shoe,diamond_shoe,stone_shoe,iron_shoe,gold_shoe,wood_stick,compass,diamond,red_stone,paper,book,map,shoe_slot,wood_sword,stone_sword,diamond_sword,gold_sword,fish_pole,buket,buket_water,buket_magma,buket_milk,wood_shovel,stone_shovel,iron_shovel,diamond_shovel,gold_shovel,scissor,wood_pick,stone_pick,iron_pick,diamond_pick,gold_pick,arch1,raw_beef,cooked_beef,iron_stick,wood_axe,stone_axe,iron_axe,diamond_axe,gold_axe,arch2,wood_hoe,stone_hoe,iron_hoe,diamond_hoe,gold_hoe,arch3;
    public int getType(){
        return this.ordinal();
    }
}