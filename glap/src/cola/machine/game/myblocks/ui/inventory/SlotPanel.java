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

import cola.machine.game.myblocks.log.LogUtil;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.Font;

/**
 *
 * @author Matthias Mann
 */
public class SlotPanel extends Widget {

    private int numSlotsX;
    private int numSlotsY;
    protected final ItemSlot[] slot;

    private int slotSpacing;

    private ItemSlot dragSlot;
    private ItemSlot dropSlot;
    boolean dragActive=false;
    IconWidget iconWidget = null;
    int mouseX = 0;
    int mouseY =0;
    Font font;
    ItemSlot.DragListener listener;
    public SlotPanel(int numSlotsX, int numSlotsY) {

        this.numSlotsX = numSlotsX;
        this.numSlotsY = numSlotsY;
        this.slot = new ItemSlot[numSlotsX * numSlotsY];//数组
        //创建listener
         listener = new ItemSlot.DragListener() {//创建listener
            public void dragStarted(ItemSlot slot, Event evt) {
                SlotPanel.this.dragStarted(slot, evt);
            }
            public void dragging(ItemSlot slot, Event evt) {
                SlotPanel.this.dragging(slot, evt);
            }
            public void dragStopped(ItemSlot slot, Event evt) {
                SlotPanel.this.dragStopped(slot, evt);
            }
        };

        for(int i=0 ; i<slot.length ; i++) {
            slot[i] = new ItemSlot(0);
            slot[i].setListener(listener);//所有的slot都绑定了一个listener
            add(slot[i]);
        }


    }

    @Override
    public int getPreferredInnerWidth() {
        return (slot[0].getPreferredWidth() + slotSpacing)*numSlotsX - slotSpacing;
    }

    @Override
    public int getPreferredInnerHeight() {
        return (slot[0].getPreferredHeight() + slotSpacing)*numSlotsY - slotSpacing;
    }

    @Override
    protected void layout() {
        int slotWidth  = slot[0].getPreferredWidth();
        int slotHeight = slot[0].getPreferredHeight();
        
        for(int row=0,y=getInnerY(),i=0 ; row<numSlotsY ; row++) {
            for(int col=0,x=getInnerX() ; col<numSlotsX ; col++,i++) {
                slot[i].adjustSize();
                slot[i].setPosition(x, y);
                x += slotWidth + slotSpacing;
            }
            y += slotHeight + slotSpacing;
        }
    }

    @Override
    protected void applyTheme(ThemeInfo themeInfo) {
        super.applyTheme(themeInfo);
        slotSpacing = themeInfo.getParameter("slotSpacing", 5);
        font = themeInfo.getFont("black");
    }
    
    void dragStarted(ItemSlot slot, Event evt) {
        if(slot.getIconWidget() != null) {
            dragSlot = slot;
            dragging(slot, evt);
        }
    }
    
    void dragging(ItemSlot slot, Event evt) {
        /*if(dragSlot != null) {
            Widget w = this.getParent().getParent().getWidgetAt(evt.getMouseX(), evt.getMouseY());
            if(w.getParent() instanceof PersonPanel) {
                //LogUtil.println("拖到了person上");
            }
            if(w instanceof ItemSlotView) {
               // System.out.println(1);
                setDropSlot((ItemSlotView)w);
            } else {
                setDropSlot(null);
            }
        }*/
    }
    void drop(ItemSlot slot, Event evt) {
        if(dragSlot != null) {
            Widget w = this.getParent().getParent().getWidgetAt(evt.getMouseX(), evt.getMouseY());
           // if(w.getParent() instanceof PersonPanel) {
                //LogUtil.println("拖到了person上");
           // }
            if(w instanceof ItemSlot) {
                //System.out.println(1);
                setDropSlot((ItemSlot)w);
            } else {
                setDropSlot(null);
            }
        }
    }

    void dragStopped(ItemSlot slot, Event evt) {
        if(dragSlot != null) {
            drop(slot, evt);
            if(dropSlot != null && dropSlot.canDrop(dragSlot.getIconWidget().getItemCfg()) && dropSlot != dragSlot) {
              IconWidget dropItem = dropSlot.getIconWidget();
                IconWidget dragItem = dragSlot.getIconWidget();
                //如果是相同的元素 允许堆叠
                if(dropItem==null){
                    dropSlot.setIconWidget(dragItem);
                    dragSlot.setIconWidget(null);

                }else
                if(dropItem.getItem().equals(dragItem.getItem())){
                    dropItem.setNum(dropItem.getNum()+dragItem.getNum());
                    dragSlot.setIconWidget(null);
                }else{
                    dropSlot.setIconWidget(dragItem);
                    dragSlot.setIconWidget(dropItem);
                }

            /*    slot.setItemWrap(null);
                dropSlot.setItemWrap(dragSlot.getItemWrap());
                dragSlot.setItemWrap(null);*/
              // dropSlot.setNum(dragSlot.getNum());
            }
            setDropSlot(null);
            dragSlot = null;
        }
    }

    private void setDropSlot(ItemSlot slot) {
        if(slot != dropSlot) {
            if(dropSlot != null) {
                dropSlot.setDropState(false, false);
            }
            dropSlot = slot;
            if(dropSlot != null) {
                if(dragSlot.getIconWidget().getItemCfg()==null){
                    LogUtil.println("itemcfg 不能为null");
                    assert dragSlot.getIconWidget().getItemCfg()!=null;
                }
                dropSlot.setDropState(true, dropSlot == dragSlot || dropSlot.canDrop(dragSlot.getIconWidget().getItemCfg()));
            }
        }
    }

    @Override//静态绘制
    protected void paintWidget(GUI gui) {
        if(iconWidget != null) {
            final int innerWidth = 40;
            final int innerHeight = 40;

            iconWidget.getIcon().draw(getAnimationState(),
                    mouseX - innerWidth/2,
                    mouseY - innerHeight/2,
                    innerWidth, innerHeight);

            font.drawText(getAnimationState(),mouseX+5,mouseY+5, iconWidget.getNum()+"");
        }

    }
    protected boolean handleEvent(Event evt) {

            if(iconWidget !=null) {//如果正在拖动

                if (evt.getType()==Event.Type.MOUSE_CLICKED ) {//如果是鼠标单击事件

                    Widget w = getWidgetAt(evt.getMouseX(), evt.getMouseY());//判断有没有点击到slot上
                    if(w instanceof ItemSlot ) {
                        ItemSlot slot = (ItemSlot)w;
                       // ItemWrap oldItemWrap = slot.getItemWrap();
                        IconWidget oldIconWidget = slot.getIconWidget();
                        slot.setIconWidget(iconWidget);
                        this.iconWidget = oldIconWidget;

                        setDropSlot((ItemSlot)w);
                        System.out.println("InventoryPanel 放下");
                        int eventModifiers= evt.getModifiers();
                        if((eventModifiers & Event.MODIFIER_CTRL) != 0) {
                            System.out.println("ctrl DOWN");
                        }

                    } else {
                        setDropSlot(null);
                    }





                }else{
                    mouseX=evt.getMouseX();
                    mouseY=evt.getMouseY();

//                    listener.dragging(this, evt);
                }
            }else if(evt.getType()==Event.Type.MOUSE_CLICKED){
                Widget w = getWidgetAt(evt.getMouseX(), evt.getMouseY());
                if(w instanceof ItemSlot) {
                    ItemSlot slot = (ItemSlot)w;

                       iconWidget = slot.getIconWidget();

                    if(iconWidget !=null) {
                        slot.setIconWidget(null);
                        System.out.println("拿起");
                        mouseX=evt.getMouseX();
                        mouseY=evt.getMouseY();
                    }

                }

            }
            return true;




    }
    
}
