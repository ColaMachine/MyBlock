/*
 * Copyright (c) 2008-2011, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.dozenx.game.engine.ui.inventory.view;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import core.log.LogUtil;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;

import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;

import javax.vecmath.Vector4f;
//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;
//

/**
 *
 * @author Matthias Mann
 */
public class ItemSlotView extends HtmlObject {
//    private Logger logger = Logger.getLogger(ItemSlotView.class);
    public static final StateKey STATE_DRAG_ACTIVE = StateKey.get("dragActive");
    public static final StateKey STATE_DROP_OK = StateKey.get("dropOk");
    public static final StateKey STATE_DROP_BLOCKED = StateKey.get("dropBlocked");
    //Font font;
    private DragListener listener;
    private boolean dragActive;
    private ParameterMap icons;
    private int itemType ;//Constants.SLOT_TYPE_HAND  对应任务身上的位置
    public int index;
    private IconView iconView;
    public ItemSlotView(int type,int index ) {
        this.index =index;
        this.itemType=type;
        this.setBorderColor(new Vector4f(1,1,1,1));
        this.setBorderWidth(1);
        this.setWidth(40);
        this.setHeight(40);
        this.display=HtmlObject.INLINE;
        //this.setTheme("itemslot");
        //   label =new Label();
        // label.setText("");
        // add(label);
   /*     font=new LWJGLFont();
        font=.getgetTheme().dragetFont("black");*/

    /*  KeyStroke ks = KeyStroke.parse("ctrl c", "copy");

        InputMap inputMap = InputMap.empty();
        inputMap.addKeyStroke(ks);
        //setCanAcceptKeyboardFocus(true);
        this.setInputMap(inputMap);*/
    }
    public IconView getIconView() {
        return iconView;
    }
    public interface DragListener {
        public void dragStarted(ItemSlotView slot, Event evt);
        public void dragging(ItemSlotView slot, Event evt);
        public void dragStopped(ItemSlotView slot, Event evt);
    }


    public void setIconView(IconView iconView) {
       // this.allChildrenRemoved();
       /* if(itemWrap == null ){
            this.allChildrenRemoved();
        }*/
        if(this.iconView==iconView)return;
        BagController bagController = CoreRegistry.get(BagController.class);
        if(iconView!=null){

            bagController.changePosition(iconView.getItemBean(),this.index);
        }

        this.iconView = iconView;
        this.removeChild();
        this.appendChild(iconView);
        if(this.getItemType()>0){
            bagController.loadEquip(this.getItemType(),iconView==null?null:iconView.getItemBean());
        }

       /* if(iconView !=null ) {//穿上装备
            *//*if(IconView.getIcon2()!=null) {
                this.setBackgroundImage(new Image(IconView.getIcon2()));
            }*//*

            if (this.getItemType() == Constants.SLOT_TYPE_HEAD) {
                Human human = CoreRegistry.get(Human.class);
                human.addHeadEquip(iconView.getItemCfg());
            }
            if (this.getItemType() == Constants.SLOT_TYPE_LEG) {
                Human human = CoreRegistry.get(Human.class);
                human.addLegEquip(iconView.getItemCfg());
            }
            if (this.getItemType() == Constants.SLOT_TYPE_BODY) {
                Human human = CoreRegistry.get(Human.class);
                human.addBodyEquip(iconView.getItemCfg());
            }
            if (this.getItemType() == Constants.SLOT_TYPE_HAND) {
                Human human = CoreRegistry.get(Human.class);
                human.addHandEquip(iconView.getItemCfg());
            }
        }else{//卸下装备
            if (this.getItemType() == Constants.SLOT_TYPE_HEAD) {
                Human human = CoreRegistry.get(Human.class);
                human.addHeadEquip(null);
            }
            if (this.getItemType() == Constants.SLOT_TYPE_LEG) {
                Human human = CoreRegistry.get(Human.class);
                human.addLegEquip(null);

            }
            if (this.getItemType() == Constants.SLOT_TYPE_BODY) {
                Human human = CoreRegistry.get(Human.class);
                human.addBodyEquip(null);

            }
            if (this.getItemType() == Constants.SLOT_TYPE_HAND) {
                Human human = CoreRegistry.get(Human.class);

                human.addHandEquip(null);
            }
        }*/
      /*  if(itemWrap!=null && itemWrap.getParent()!=null){
            itemWrap.getParent().removeChild(itemWrap);

        }
        if(itemWrap!=null){
            add(itemWrap);
          //  itemWrap.adjustSize();
        }*/

    }

    //private String item;
   // private Image icon;
   // private int num;
    //private Label label;


    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }


    /*public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num=num;
    }*/

    /*public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
        findIcon();//根据name 查找
    }

    public void setItem(String item,int num) {
        this.item = item;
        this.num=num;
   //     label.setText(""+num);
        findIcon();//根据name 查找
    }*/

   /* public boolean canDrop() {
        return true;//itemWrap == null;
    }*/

    public boolean canDrop(ItemDefinition itemDefinition) {
        if(this.itemType==0|| this.itemType ==Constants.SLOT_TYPE_HAND){
            return true;
        }
        try {
            if (this.itemType != 0 && itemDefinition.getType() !=null && (this.itemType & itemDefinition.getPosition()) != 0)
                return true;//itemWrap == null;
            else
                return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
   /* public Image getIcon() {
        return icon;
    }*/
    
    public DragListener getListener() {
        return listener;
    }

    public void setListener(DragListener listener) {
        this.listener = listener;
    }
    
    public void setDropState(boolean drop, boolean ok) {
        AnimationState as = getAnimationState();
        as.setAnimationState(STATE_DROP_OK, drop && ok);
        as.setAnimationState(STATE_DROP_BLOCKED, drop && !ok);
    }
    

    protected boolean handleEvent(Event evt) {
        if(evt.isMouseEventNoWheel()) {
          if(dragActive) {

                if(evt.isMouseDragEnd()) {
                    if(listener != null) {
                        listener.dragStopped(this, evt);
                    }
                   /* if((evt.getModifiers() & 2) == 2) {
                        logger.info("ctrl ");
                    }*/

                    dragActive = false;
                    getAnimationState().setAnimationState(STATE_DRAG_ACTIVE, false);
                } else if(listener != null) {
                    listener.dragging(this, evt);
                }
            } else if(evt.isMouseDragEvent()) {
                dragActive = true;
                getAnimationState().setAnimationState(STATE_DRAG_ACTIVE, true);
                if(listener != null) {
                    LogUtil.println("dragStarted");
                    listener.dragStarted(this, evt);
                }
            }/*
            if(dragActive) {
                if (evt.getType()==Event.Type.MOUSE_CLICKED) {
                    if (listener != null) {
                        listener.dragStopped(this, evt);
                    }
                    logger.info("放下");
                    System.out.println("itemSlog 放下");
                    dragActive = false;
                    getAnimationState().setAnimationState(STATE_DRAG_ACTIVE, false);
                }else{
                    listener.dragging(this, evt);
                }
            }else if(evt.getType()==Event.Type.MOUSE_CLICKED){
                logger.info("拿起");
                System.out.println("itemSlog 拿起");
                dragActive = true;
                getzAnimationState().setAnimationState(STATE_DRAG_ACTIVE, true);
                if(listener != null) {
                    listener.dragStarted(this, evt);
                }
            }*/
            return true;
        }/*else if(evt.isKeyEvent()){
            logger.info(evt.getKeyChar());
            String name =this.getInputMap().mapEvent(evt);
            if(name!=null ) {
                 name =this.getInputMap().mapEvent(evt);
                logger.info("paset");
            }
        }*/
        
        
        return super.handleEvent(evt);
    }

/*    @Override//静态绘制
    protected void paintWidget(Document gui) {
        if(!dragActive && IconView != null) {
            if(IconView.getIcon2()==null){
                LogUtil.println("icon2 is null");
            }
            IconView.getIcon2().draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            //font.drawText(null,getInnerX(), getInnerY(),"10");
            //this.paintChild(gui,label);
            //this.paintChild(gui,itemWrap);
           // itemWrap.getIcon2().draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            //font.drawText(getAnimationState(),getInnerX()+15,getInnerY()+15, IconView.getNum()+"");
        }

    }*/

   // @Override//绘制拖动过程
 /*   protected void paintDragOverlay(GUI gui, int mouseX, int mouseY, int modifier) {
        if(IconView != null) {
            final int innerWidth = getInnerWidth();
            final int innerHeight = getInnerHeight();

            *//*itemWrap.getIcon().draw(getAnimationState(),
                    mouseX - innerWidth/2,
                    mouseY - innerHeight/2,
                    innerWidth, innerHeight);*//*
            IconView.getIcon2().draw(getAnimationState(),
                    mouseX - innerWidth/2,
                    mouseY - innerHeight/2,
                    innerWidth, innerHeight);
           // itemWrap.setPosition(mouseX - innerWidth/2, mouseY - innerHeight/2);
            //this.paintChild(gui,itemWrap);
           // label.setOffscreenExtra(mouseX,mouseY,label.getWidth(),label.getHeight());
            //font.drawText(getAnimationState(),mouseX+5,mouseY+5, IconView.getNum()+"");
        }
    }
*/
    //@Override
  /*  protected void applyTheme(ThemeInfo themeInfo) {
        //super.applyTheme(themeInfo);
       // icons = themeInfo.getParameterMap("icons");
       // font = themeInfo.getFont("black");
        //findIcon();
    }*/
 //   private TextureInfo icon2;
   /* private void findIcon() {
        if(IconView == null || icons == null) {
            //icon = null;
        } else {
            //icon = icons.getImage(item)
            //icon2= TextureManager.getTextureInfo(itemWrap.getItem());
           //itemWidget.setIcon(icons.getImage(itemWidget.getItem())) ;
        }
    }*/
}
