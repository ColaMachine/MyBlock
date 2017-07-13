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
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import de.matthiasmann.twl.Event;

/**
 *
 * @author Matthias Mann
 */
public class BoxPanel extends SlotPanel {
    private BagEntity bagEntity;
    private BagController bagController;

    public void reload(){
        ItemBean[] itemBeanList=bagController.getItemBeanList();


        for(int i=0;i<20;i++){
            if(itemBeanList[i]!=null &&itemBeanList[i].getItemDefinition()!=null ) {
                slot[i].setIconView(new IconView(itemBeanList[i]));
            }else{
                slot[i].setIconView(null);
            }
        }

    }
    public BoxPanel(int numSlotsX, int numSlotsY) {
        super(numSlotsX,numSlotsY);
        this.id="box";
        this.bagController = CoreRegistry.get(BagController.class);
        assert bagController!=null:"bagController miss in CoreRegistry";

        reload();

        this.setPosition(HtmlObject.POSITION_ABSOLUTE);

        this.setTop(300);
        this.setLeft(300);

        CoreRegistry.put(BoxPanel.class,this);

        reload();
    }


    public boolean handleEvent(Event evt) {

        switch (evt.getType()) {
            case KEY_PRESSED:
                switch (evt.getKeyCode()) {
                    case Event.KEY_ESCAPE:
                        this.setVisible(false);Document.needUpdate=true;
                        this.giveupKeyboardFocus();
                        Switcher.isChat=false;
                        return true;

                }

        }
        if (super.handleEvent(evt)) {
            return true;
        }



        return  true;
    }
    
}
