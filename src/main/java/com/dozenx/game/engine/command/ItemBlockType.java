package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/13.
 */
public enum ItemBlockType {
    NULL,SAND,SOIL,STONE,WOOD,MANTLE,WATER;

    public int getType(){
        return this.ordinal();
    }

    public static int getTypeVal(ItemBlockType itemType){
        if(itemType==null || itemType== ItemBlockType.NULL){
            return 0;
        }else{
            return itemType.ordinal();
        }
    }
}
