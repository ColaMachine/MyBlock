package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/13.
 */
public enum ItemType {
    NULL,/*fur_shoulder,*/
    fur_helmet(19,"fur_helmet","皮头盔"),/*chain_helmet,*/
    chain_helmet,
    iron_helmet(26),
    gold_helmet(22),flint,firestone,
    coal,rope,/*seed,*/apple,gold_apple,egg,sugar,ice_ball,
    head_slot,fur_armor(20),chain_armor,iron_armor(27),diamond_armoriron_cloth,
    gold_armor,arch(36,"arch","弓")

    ,stone_bar,iron_bar,feather,wheat,photo_frame,
    sugarcane,bone,cacke,rubber_ball,body_slot,fur_pants(21),chain_pants,
    diamond_pants,iron_panmts(28),gold_pants,arrow(37),dorlach,gold_bar,clay,
    bread,billboard,iron_door,bed,pant_slot,fur_shoe(22),
    diamond_shoe,stone_shoe,iron_shoe(29),gold_shoe,wood_stick,compass,
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
    public int getType(){
        return this.ordinal();
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
    public static int getTypeVal(ItemType itemType){
        if(itemType==null || itemType==ItemType.NULL){
            return 0;
        }else{
            return itemType.ordinal();
        }
    }
}
