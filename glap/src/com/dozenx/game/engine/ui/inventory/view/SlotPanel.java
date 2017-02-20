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

import core.log.LogUtil;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import de.matthiasmann.twl.Event;

import javax.vecmath.Vector4f;

/**
 *
 * @author Matthias Mann
 */
public class SlotPanel extends HtmlObject {

    private int numSlotsX;
    private int numSlotsY;
    protected final ItemSlotView[] slot;

    private int slotSpacing;

    private ItemSlotView dragSlot;
    private ItemSlotView dropSlot;
    boolean dragActive=false;
    com.dozenx.game.engine.ui.inventory.view.IconView IconView = null;
    int mouseX = 0;
    int mouseY =0;
    //Font font;
    ItemSlotView.DragListener listener;
    public SlotPanel(int numSlotsX, int numSlotsY) {
        this.setBorderColor(new Vector4f(1,1,1,1));
        this.setBorderWidth(1);

        this.setWidth(numSlotsX*40+1);
        this.numSlotsX = numSlotsX;
        this.numSlotsY = numSlotsY;
        this.slot = new ItemSlotView[numSlotsX * numSlotsY];//数组
        //创建listener
         listener = new ItemSlotView.DragListener() {//创建listener
            public void dragStarted(ItemSlotView slot, Event evt) {
                SlotPanel.this.dragStarted(slot, evt);
            }
            public void dragging(ItemSlotView slot, Event evt) {
                SlotPanel.this.dragging(slot, evt);
            }
            public void dragStopped(ItemSlotView slot, Event evt) {
                SlotPanel.this.dragStopped(slot, evt);
            }
        };

        for(int i=0 ; i<slot.length ; i++) {
            slot[i] = new ItemSlotView(0);
            slot[i].setListener(listener);//所有的slot都绑定了一个listener
            appendChild(slot[i]);
        }


    }

   /* @Override
    public int getPreferredInnerWidth() {
        return (slot[0].getPreferredWidth() + slotSpacing)*numSlotsX - slotSpacing;
    }

    @Override
    public int getPreferredInnerHeight() {
        return (slot[0].getPreferredHeight() + slotSpacing)*numSlotsY - slotSpacing;
    }
*/
    /*@Override
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
    }*/

   /* @Override
    protected void applyTheme(ThemeInfo themeInfo) {
        super.applyTheme(themeInfo);
        slotSpacing = themeInfo.getParameter("slotSpacing", 5);
        font = themeInfo.getFont("black");
    }*/

    void dragStarted(ItemSlotView slot, Event evt) {
        if(slot.getIconView() != null) {
            dragSlot = slot;
            dragging(slot, evt);
        }
    }

    void dragging(ItemSlotView slot, Event evt) {
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
    void drop(ItemSlotView slot, Event evt) {
        if(dragSlot != null) {
            HtmlObject w = this.getParent().getParent().getWidgetAt(evt.getMouseX(), evt.getMouseY());
           // if(w.getParent() instanceof PersonPanel) {
                //LogUtil.println("拖到了person上");
           // }
            if(w instanceof ItemSlotView) {
                //System.out.println(1);
                setDropSlot((ItemSlotView)w);
            } else {
                setDropSlot(null);
            }
        }
    }

    void dragStopped(ItemSlotView slot, Event evt) {
        if(dragSlot != null) {
            drop(slot, evt);
            if(dropSlot != null && dropSlot.canDrop(dragSlot.getIconView().getItemDefinition()) && dropSlot != dragSlot) {
              IconView dropItem = dropSlot.getIconView();
                IconView dragItem = dragSlot.getIconView();
                //如果是相同的元素 允许堆叠
                if(dropItem==null){
                    dropSlot.setIconView(dragItem);
                    dragSlot.setIconView(null);

                }else
                if(dropItem.getItem().equals(dragItem.getItem())){
                    dropItem.setNum(dropItem.getNum()+dragItem.getNum());
                    dragSlot.setIconView(null);
                }else{
                    dropSlot.setIconView(dragItem);
                    dragSlot.setIconView(dropItem);
                }

            /*    slot.setItemWrap(null);
                dropSlot.setItemWrap(dragSlot.getItemWrap());
                dragSlot.setItemWrap(null);*/
              // dropSlot.setNum(dragSlot.getNum());
            }
            Document.needUpdate=true;
            setDropSlot(null);
            dragSlot = null;
        }
    }

    private void setDropSlot(ItemSlotView slot) {
        if(slot != dropSlot) {
            if(dropSlot != null) {
                dropSlot.setDropState(false, false);
            }
            dropSlot = slot;
            if(dropSlot != null) {
                if(dragSlot.getIconView().getItemDefinition()==null){
                    LogUtil.println("itemcfg 不能为null");
                    assert dragSlot.getIconView().getItemDefinition()!=null;
                }
                dropSlot.setDropState(true, dropSlot == dragSlot || dropSlot.canDrop(dragSlot.getIconView().getItemDefinition()));
            }
        }
    }


    protected boolean handleEvent(Event evt) {

            if(IconView !=null) {//如果正在拖动

                if (evt.getType()==Event.Type.MOUSE_CLICKED ) {//如果是鼠标单击事件

                    HtmlObject w = getWidgetAt(evt.getMouseX(), evt.getMouseY());//判断有没有点击到slot上
                    if(w instanceof ItemSlotView) {
                        ItemSlotView slot = (ItemSlotView)w;
                       // ItemWrap oldItemWrap = slot.getItemWrap();
                        IconView oldIconView = slot.getIconView();
                        slot.setIconView(IconView);
                        this.IconView = oldIconView;

                        setDropSlot((ItemSlotView)w);
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
                HtmlObject w = getWidgetAt(evt.getMouseX(), evt.getMouseY());
                if(w instanceof ItemSlotView) {
                    ItemSlotView slot = (ItemSlotView)w;

                       IconView = slot.getIconView();

                    if(IconView !=null) {
                        slot.setIconView(null);
                        System.out.println("拿起");
                        mouseX=evt.getMouseX();
                        mouseY=evt.getMouseY();
                    }

                }

            }else if(evt.getType() ==evt.type.MOUSE_BTNDOWN){
                LogUtil.println("buton down");
            }
            return true;




    }
    @Override//静态绘制
    protected void paintWidget(Document gui) {
        if(IconView != null) {
            final int innerWidth = 40;
            final int innerHeight = 40;

            /*IconView.getIcon().draw(getAnimationState(),
                    mouseX - innerWidth/2,
                    mouseY - innerHeight/2,
                    innerWidth, innerHeight);*/

            // font.drawText(getAnimationState(),mouseX+5,mouseY+5, IconView.getNum()+"");
        }

    }
}
