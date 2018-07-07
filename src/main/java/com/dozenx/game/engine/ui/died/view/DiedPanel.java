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
package com.dozenx.game.engine.ui.died.view;

import cola.machine.game.myblocks.bean.BagEntity;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.ui.html.Button;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.edit.view.MouseClickHandler;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.ui.inventory.control.BagController;
import com.dozenx.game.engine.ui.inventory.view.IconView;
import com.dozenx.game.engine.ui.inventory.view.SlotPanel;
import core.log.LogUtil;

import javax.vecmath.Vector4f;

/**
 *
 * @author Matthias Mann
 */
public class DiedPanel extends HtmlObject {
    public DiedPanel() {
        this.id="bag";



        this.setPosition(HtmlObject.POSITION_ABSOLUTE);

        this.setTop(0);
        this.setLeft(0);

        this.setWidth(Constants.WINDOW_WIDTH);
        this.setHeight(Constants.WINDOW_HEIGHT);
        this.setBackgroundColor(Constants.RGBA_GRAY_TRANSPARENT);
        final Button button =new Button("重新开始");
        button.setPosition(HtmlObject.POSITION_ABSOLUTE);
        button.setTop((Constants.WINDOW_HEIGHT-50)/2);
        button.setLeft((Constants.WINDOW_WIDTH-100)/2);
        this.setVisible(false);
        this.appendChild(button);
        button.setFontSize(50);
        button.setColor(Constants.RGBA_RED);
        button.setBackgroundColor(Constants.RGBA_GRAY);
        button.addEventListener("click",new MouseClickHandler(){
            public void run(){
                GamingState.player.nowHP=100;
                hide();
                LogUtil.println("重新复活了");
            }

        });
      //  KeyStroke ks = KeyStroke.parse("ctrl A", "copy");

      //  InputMap inputMap = InputMap.empty();
      //  inputMap.addKeyStroke(ks);
        //setCanAcceptKeyboardFocus(true);
       // this.setInputMap(inputMap);
        CoreRegistry.put(DiedPanel.class,this);


    }
    public void hide(){
        this.setVisible(false);
    }

    
}
