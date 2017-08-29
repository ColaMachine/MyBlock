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
package cola.machine.game.myblocks.ui.inventory;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import core.log.LogUtil;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;
import de.matthiasmann.twl.renderer.Font;
//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;
//
/**
 *
 * @author Matthias Mann
 */
public class ItemSlot extends Widget {
//    private Logger logger = Logger.getLogger(ItemSlotView.class);
    public static final StateKey STATE_DRAG_ACTIVE = StateKey.get("dragActive");
    public static final StateKey STATE_DROP_OK = StateKey.get("dropOk");
    public static final StateKey STATE_DROP_BLOCKED = StateKey.get("dropBlocked");
    Font font;
    public interface DragListener {
        public void dragStarted(ItemSlot slot, Event evt);
        public void dragging(ItemSlot slot, Event evt);
        public void dragStopped(ItemSlot slot, Event evt);
    }
    private IconWidget iconWidget;

    public IconWidget getIconWidget() {
        return iconWidget;
    }

    public void setIconWidget(IconWidget iconWidget) {
       // this.allChildrenRemoved();
       /* if(itemWrap == null ){
            this.allChildrenRemoved();
        }*/

        this.iconWidget = iconWidget;
        if(iconWidget !=null ) {
            if (this.getItemType() == Constants.SLOT_TYPE_HEAD) {
                Player player = CoreRegistry.get(Player.class);
                player.getModel().addHeadEquip(new ItemBean(iconWidget.getItemCfg(),1));
            }
            if (this.getItemType() == Constants.SLOT_TYPE_LEG) {
                Player player = CoreRegistry.get(Player.class);
                player.addLegEquip(new ItemBean(iconWidget.getItemCfg(),1));
            }
            if (this.getItemType() == Constants.SLOT_TYPE_BODY) {
                Player player = CoreRegistry.get(Player.class);
                player.addBodyEquip(new ItemBean(iconWidget.getItemCfg(),1));
            }
            if (this.getItemType() == Constants.SLOT_TYPE_HAND) {
                Player player = CoreRegistry.get(Player.class);
                player.addHandEquip(new ItemBean(iconWidget.getItemCfg(),1));
            }
        }else{
            if (this.getItemType() == Constants.SLOT_TYPE_HEAD) {
                Player player = CoreRegistry.get(Player.class);
                player.clearHeadEquip();
            }
            if (this.getItemType() == Constants.SLOT_TYPE_LEG) {
                Player player = CoreRegistry.get(Player.class);
                player.clearLegEquip();

            }
            if (this.getItemType() == Constants.SLOT_TYPE_BODY) {
                Player player = CoreRegistry.get(Player.class);
                player.clearBodyEquip();

            }
            if (this.getItemType() == Constants.SLOT_TYPE_HAND) {
                Player player = CoreRegistry.get(Player.class);

                player.clearHandEquip();
            }
        }
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
    private DragListener listener;
    private boolean dragActive;
    private ParameterMap icons;
    private int itemType ;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public ItemSlot(int type) {
        this.itemType=type;
        this.setTheme("itemslot");
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

    @Override//静态绘制
    protected void paintWidget(GUI gui) {
        if(!dragActive && iconWidget != null) {
            if(iconWidget.getIcon2()==null){
                LogUtil.println("icon2 is null");
            }
            iconWidget.getIcon2().draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            //font.drawText(null,getInnerX(), getInnerY(),"10");
            //this.paintChild(gui,label);
            //this.paintChild(gui,itemWrap);
           // itemWrap.getIcon2().draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            font.drawText(getAnimationState(),getInnerX()+15,getInnerY()+15, iconWidget.getNum()+"");
        }

    }

    @Override//绘制拖动过程
    protected void paintDragOverlay(GUI gui, int mouseX, int mouseY, int modifier) {
        if(iconWidget != null) {
            final int innerWidth = getInnerWidth();
            final int innerHeight = getInnerHeight();

            /*itemWrap.getIcon().draw(getAnimationState(),
                    mouseX - innerWidth/2,
                    mouseY - innerHeight/2,
                    innerWidth, innerHeight);*/
            iconWidget.getIcon2().draw(getAnimationState(),
                    mouseX - innerWidth/2,
                    mouseY - innerHeight/2,
                    innerWidth, innerHeight);
           // itemWrap.setPosition(mouseX - innerWidth/2, mouseY - innerHeight/2);
            //this.paintChild(gui,itemWrap);
           // label.setOffscreenExtra(mouseX,mouseY,label.getWidth(),label.getHeight());
            font.drawText(getAnimationState(),mouseX+5,mouseY+5, iconWidget.getNum()+"");
        }
    }

    @Override
    protected void applyTheme(ThemeInfo themeInfo) {
        super.applyTheme(themeInfo);
        icons = themeInfo.getParameterMap("icons");
        font = themeInfo.getFont("black");
        //findIcon();
    }
    private TextureInfo icon2;
    private void findIcon() {
        if(iconWidget == null || icons == null) {
            //icon = null;
        } else {
            //icon = icons.getImage(item)
            //icon2= TextureManager.getTextureInfo(itemWrap.getItem());
           //itemWidget.setIcon(icons.getImage(itemWidget.getItem())) ;
        }
    }
}
