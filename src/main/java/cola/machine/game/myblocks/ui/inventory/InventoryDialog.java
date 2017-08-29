/*
 * Copyright (c) 2008-2012, Matthias Mann
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
import cola.machine.game.myblocks.ui.test.FadeFrame;


/**
 * A simple login panel
 * 
 * @author Matthias Mann
 */
public class InventoryDialog extends FadeFrame {




    final InventoryPanel inventoryPanel;

    boolean quit;

    public InventoryDialog() {

        
        inventoryPanel = new InventoryPanel(Constants.BAG_NUM_SLOTS_X, Constants.BAG_NUM_SLOTS_Y);//10行5列
        

        setTitle("Inventory");//整个包裹的标题
        setResizableAxis(ResizableAxis.BOTH);//是否可以调整大小
        add(inventoryPanel);//添加panel
        addCloseCallback(new Runnable() {
            @Override
            public void run() {
                setVisible(false);
            }
        });

        setTheme("inventorydemo");
       /* this.positionFrame();*/
    }

   /* void positionFrame() {
        frame.adjustSize();//调整包裹大小 刚好包裹格子
        frame.setPosition(
                getInnerX() + (getInnerWidth() - frame.getWidth())/2,
                getInnerY() + (getInnerHeight() - frame.getHeight())/2);//调整位置
    }

    @Override
    protected void layout() {
        super.layout();

    }
    */
}
