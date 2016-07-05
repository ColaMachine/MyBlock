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

import cola.machine.game.myblocks.action.BagController;
import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.bean.ItemEntity;
import cola.machine.game.myblocks.registry.CoreRegistry;

import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.ThemeInfo;
import de.matthiasmann.twl.Widget;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Matthias Mann
 */
public class InventoryPanel extends Widget {
    private BagEntity bagEntity;
    private BagController bagController;
    private int numSlotsX;
    private int numSlotsY;
    private final ItemSlot[] slot;
    
    private int slotSpacing;

    private ItemSlot dragSlot;
    private ItemSlot dropSlot;
    
    public InventoryPanel(int numSlotsX, int numSlotsY) {
        this.bagController = CoreRegistry.get(BagController.class);
        assert bagController!=null:"bagController miss in CoreRegistry";
        //Assert.checkNonNull(bagController,"bagController miss in CoreRegistry");

       // this.bagEntity = CoreRegistry.get(BagEntity.class);
        //Assert.checkNonNull(bagEntity,"bagEntity miss in CoreRegistry");
        this.numSlotsX = numSlotsX;
        this.numSlotsY = numSlotsY;
        this.slot = new ItemSlot[numSlotsX * numSlotsY];//数组
        //创建listener
        ItemSlot.DragListener listener = new ItemSlot.DragListener() {//创建listener
            public void dragStarted(ItemSlot slot, Event evt) {
                InventoryPanel.this.dragStarted(slot, evt);
            }
            public void dragging(ItemSlot slot, Event evt) {
                InventoryPanel.this.dragging(slot, evt);
            }
            public void dragStopped(ItemSlot slot, Event evt) {
                InventoryPanel.this.dragStopped(slot, evt);
            }
        };

        for(int i=0 ; i<slot.length ; i++) {
            slot[i] = new ItemSlot();
            slot[i].setListener(listener);//所有的slot都绑定了一个listener
            add(slot[i]);
        }
        Map<Integer,ItemEntity> itemEntityMap=bagController.getAllItemEntity();
        Set<Integer> set = itemEntityMap.keySet();
        for(int key:set){
            slot[key].setItem(itemEntityMap.get(key).getName(),itemEntityMap.get(key).getNum());
        }
       /* for(int i=0;i<itemEntitys.length;i++){
            slot[0].setItem(itemEntitys[i].getName(),itemEntitys[i].getNum());
        }*/
        slot[10].setItem("red");
        slot[11].setItem("green");
        slot[12].setItem("blue");
        slot[13].setItem("yellow");
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
    }
    
    void dragStarted(ItemSlot slot, Event evt) {
        if(slot.getItem() != null) {
            dragSlot = slot;
            dragging(slot, evt);
        }
    }
    
    void dragging(ItemSlot slot, Event evt) {
        if(dragSlot != null) {
            Widget w = getWidgetAt(evt.getMouseX(), evt.getMouseY());
            if(w instanceof ItemSlot) {
                setDropSlot((ItemSlot)w);
            } else {
                setDropSlot(null);
            }
        }
    }
    
    void dragStopped(ItemSlot slot, Event evt) {
        if(dragSlot != null) {
            dragging(slot, evt);
            if(dropSlot != null && dropSlot.canDrop() && dropSlot != dragSlot) {
                dropSlot.setItem(dragSlot.getItem());
                dragSlot.setItem(null);
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
                dropSlot.setDropState(true, dropSlot == dragSlot || dropSlot.canDrop());
            }
        }
    }
    
}
