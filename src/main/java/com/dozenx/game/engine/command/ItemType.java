package com.dozenx.game.engine.command;

import java.util.HashMap;

/**
 * Created by luying on 17/2/13.
 * 存储物品的定义和名称之间的关系 由于后面都走.item文件 所以不方便维护
 * 所有在itemType里定义的都是内置的物品,
 */
public enum ItemType {
    NULL,/*fur_shoulder,*/
    fur_helmet(19,"fur_helmet","皮头盔"),/*chain_helmet,*/
    fur_armor(20),
    fur_pants(21),
    fur_shoe(22),

    iron_helmet(23),
    iron_armor(24),
    iron_pants(25),
    iron_shoe(26),

    gold_helmet(27),
    gold_armor(28),
    gold_pants(29),
    gold_shoe(30),
    chain_helmet,


    diamond_helmet(31),
    diamond_armor(32),
    diamond_pants(33),
    diamond_shoe(34),

    flint,firestone,
    coal,rope,/*seed,*/apple,gold_apple,egg,sugar,ice_ball,
    head_slot,chain_armor,
    arch(36,"arch","弓")

    ,stone_bar,iron_bar,feather,wheat,photo_frame,
    sugarcane,bone,cacke,rubber_ball,body_slot,chain_pants,
    arrow(37),dorlach,gold_bar,clay,
    bread,billboard,iron_door,bed,pant_slot,
    stone_shoe,wood_stick,compass,
    diamond,red_stone,paper,book,map,shoe_slot,wood_sword(31),stone_sword,
    diamond_sword,gold_sword(32),fish_pole,buket,buket_water,buket_magma,
    buket_milk,wood_shovel,stone_shovel,iron_shovel,diamond_shovel,
    gold_shovel,scissor,wood_pick(33),stone_pick,iron_pick,diamond_pick,
    gold_pick,arch1,raw_beef,cooked_beef,iron_stick,wood_axe(34),stone_axe,
    iron_axe,diamond_axe,gold_axe,arch2,wood_hoe(35),stone_hoe,iron_hoe,
    diamond_hoe,gold_hoe,arch3,
    grit,soil(12),stone(13),wood_stair(40),wood_half(41)


    ,wood(3),glass(4),sand(11),mantle(1),water(10),tree_wood(14,"tree_wood","木头"),tree_leaf(15,"tree_leaf","树叶"),
    tree_seed(16),wood_door(17,"wood_door","木头门"),copy_down(),box(18),red(2),StoneBrick(7),MossyStoneBrick(8),CrackedStoneBrick(6),grass(9),OakWood(5),CopyBlock(40);
    /**测试的时候打印itemtype**/
    public static void main(String args[]){
        System.out.println(ItemType.red.ordinal());
        System.out.println(ItemType.values()[95]);
    }
    static HashMap<Integer,ItemType> id2TypeMap =new HashMap<Integer,ItemType>();
    static{

        for(ItemType  e:ItemType.values()) {
            id2TypeMap.put(e.id,e);
        }
    }
    public int getType(){
        return this.ordinal();
    }

    public static ItemType getItemTypeById(Integer id){
        return id2TypeMap.get(id);
    }


    public int id;
    public String name;
    public String remark;

    ItemType(){

    }
    ItemType(int id){
        this.id =id ;

    }
     ItemType(int id,String name,String chname){
        this.id =id ;
        this.name =name;
        this.remark = chname;
    }


//    public static int getTypeVal(ItemType itemType){
//        if(itemType==null || itemType==ItemType.NULL){
//            return 0;
//        }else{
//            return itemType.ordinal();
//        }
//    }
}
