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

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.ui.inventory.control.BagController;

/**
 *
 * @author Matthias Mann
 */
public class PersonPanel extends SlotPanel {
    private BagEntity bagEntity;
    private BagController bagController;
    private int numSlotsX=1;
    private int numSlotsY=5;




    public PersonPanel(int numSlotsX, int numSlotsY) {
      
        super( numSlotsX,  numSlotsY);

        slot[0] = new ItemSlot(Constants.SLOT_TYPE_HEAD);
        slot[0].setListener(listener);//所有的slot都绑定了一个listener
        add(slot[0]);

        slot[1] = new ItemSlot(Constants.SLOT_TYPE_BODY);
        slot[1].setListener(listener);//所有的slot都绑定了一个listener
        add(slot[1]);

        slot[2] = new ItemSlot(Constants.SLOT_TYPE_LEG);
        slot[2].setListener(listener);//所有的slot都绑定了一个listener
        add(slot[2]);

        slot[3] = new ItemSlot(Constants.SLOT_TYPE_FOOT);
        slot[3].setListener(listener);//所有的slot都绑定了一个listener
        add(slot[3]);

        slot[4] = new ItemSlot(Constants.SLOT_TYPE_HAND);
        slot[4].setListener(listener);//所有的slot都绑定了一个listener
        add(slot[4]);





    }


    
}
