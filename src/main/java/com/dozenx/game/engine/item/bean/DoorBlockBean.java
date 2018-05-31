package com.dozenx.game.engine.item.bean;

import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.DoorBlock;

/**
 * Created by luying on 17/12/12.
 */
public class DoorBlockBean {

    BaseBlock model = null;

    int  open;
    int face;
    int top;//是否是顶端
public DoorBlockBean(int face,int open,int isTop){
    this.top =isTop;
    this.open=open;
    this.face = face;
}

}
