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

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.inventory.control.BagController;

import javax.vecmath.Vector4f;

/**
 *
 * @author Matthias Mann
 */
public class PersonPanel extends SlotPanel {
    private BagEntity bagEntity;
    private BagController bagController;
    private int numSlotsX=1;
    private int numSlotsY=5;


    public void reload(){
        ItemBean[] itemBeanList=bagController.getItemBeanList(1);


        for(int i=20;i<=24;i++){
            if(itemBeanList[i]!=null && itemBeanList[i].getItemDefinition()!=null) {
                slot[i-20].setIconView(new IconView(itemBeanList[i]));
            }else{
                slot[i-20].setIconView(null);
            }
        }

    }
    Div attriDiv =new Div();
    public PersonPanel(int numSlotsX, int numSlotsY) {

        super( numSlotsX,  numSlotsY);
        this.id="personalPanel";
        CoreRegistry.put(PersonPanel.class,this);
        this.setBorderColor(new Vector4f(0.5f,0.5f,0.5f,0.5f));
        this.setBackgroundColor(new Vector4f(0.5f,0.5f,0.5f,0.5f));
        this.setBorderWidth(2);
        this.setMarginLeft((short)200);
        this.setWidth(0);
        attriDiv.setWidth(100);

        this.removeAllChild();
        Div div =new Div();
        div.setDisplay(HtmlObject.INLINE);
        attriDiv.setDisplay(HtmlObject.INLINE);
        div.setWidth(numSlotsX*40+1);
        slot[0] = new ItemSlotView(Constants.SLOT_TYPE_HEAD,20);
        slot[0].setListener(listener);//所有的slot都绑定了一个listener
        div.appendChild(slot[0]);

        slot[1] = new ItemSlotView(Constants.SLOT_TYPE_BODY,21);
        slot[1].setListener(listener);//所有的slot都绑定了一个listener
        div.appendChild(slot[1]);

        slot[2] = new ItemSlotView(Constants.SLOT_TYPE_LEG,22);
        slot[2].setListener(listener);//所有的slot都绑定了一个listener
        div.appendChild(slot[2]);

        slot[3] = new ItemSlotView(Constants.SLOT_TYPE_FOOT,23);
        slot[3].setListener(listener);//所有的slot都绑定了一个listener
        div.appendChild(slot[3]);

        slot[4] = new ItemSlotView(Constants.SLOT_TYPE_HAND,24);
        slot[4].setListener(listener);//所有的slot都绑定了一个listener
        div.appendChild(slot[4]);

        this.appendChild(div);
        this.appendChild(attriDiv);

        this.setPosition(HtmlObject.POSITION_ABSOLUTE);

        this.setTop(300);
        this.setLeft(600);
         bagController =  CoreRegistry.get(BagController.class);

    }

    public void update(){
        super.update();
        attriDiv.innerText=GamingState.player.getState();
    }


    
}
