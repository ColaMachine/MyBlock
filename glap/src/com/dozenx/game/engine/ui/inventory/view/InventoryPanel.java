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

import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.ui.inventory.control.InventoryController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Matthias Mann
 */
public class InventoryPanel extends SlotPanel {
    private BagEntity bagEntity;
    private BagController bagController;

    public void reload(){
        ItemBean[] itemBeanList=bagController.getItemBeanList();

     /*   for(ItemBean itemBean :itemBeanList){
            slot[itemBean.getPosition()].setIconView(null);
        }*/
        for(ItemBean itemBean :itemBeanList){
            if(itemBean!=null){
                slot[itemBean.getPosition()].setIconView(new IconView(itemBean));
            }

        }
    }
    public InventoryPanel(int numSlotsX, int numSlotsY) {
        super(numSlotsX,numSlotsY);
        this.bagController = CoreRegistry.get(BagController.class);
        assert bagController!=null:"bagController miss in CoreRegistry";
        //Assert.checkNonNull(bagController,"bagController miss in CoreRegistry");

       // this.bagEntity = CoreRegistry.get(BagEntity.class);
        //Assert.checkNonNull(bagEntity,"bagEntity miss in CoreRegistry");


     /*   Map<Integer,ItemBean> itemEntityMap=bagController.getAllItemEntity();
        Set<Integer> set = itemEntityMap.keySet();
        for(int key:set){
            slot[key].setIconView(new IconView(itemEntityMap.get(key)));
        }*/
        reload();

        this.setPosition(HtmlObject.POSITION_ABSOLUTE);

        this.setTop(300);
        this.setLeft(300);
      //  KeyStroke ks = KeyStroke.parse("ctrl A", "copy");

      //  InputMap inputMap = InputMap.empty();
      //  inputMap.addKeyStroke(ks);
        //setCanAcceptKeyboardFocus(true);
       // this.setInputMap(inputMap);
        CoreRegistry.put(InventoryPanel.class,this);
    }


    
}
