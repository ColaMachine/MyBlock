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
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.log.LogUtil;
import cola.machine.game.myblocks.registry.CoreRegistry;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.Font;

/**
 *
 * @author Matthias Mann
 */
public class ToolbarPanel extends SlotPanel {


    public ToolbarPanel(int numSlotsX, int numSlotsY) {
        super( numSlotsX,  numSlotsY);






    }


    protected boolean handleEvent(Event evt) {
        super.handleEvent(evt);
        if (evt.getType()==Event.Type.KEY_PRESSED ) {
           if(evt.getKeyCode()==Event.KEY_1 && slot[0].getIconWidget().getType()==Constants.ICON_TYPE_SKILL ){
               

           }
        }





    }



    
}
