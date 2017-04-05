package com.dozenx.game.engine.command;

/**
 * Created by luying on 17/2/13.
 */
public enum ItemMainType {
    FOOD,WEAR,WEAPON,BLOCK;
   public int getType(){
        return this.ordinal();
    }


    
    public static int getTypeVal(ItemMainType itemType){

        return itemType.ordinal();
    }
    //352034061552413
}
