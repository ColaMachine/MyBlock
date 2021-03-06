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
package com.dozenx.game.engine.ui.head.view;

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.html.*;

import javax.vecmath.Vector4f;


/**
 * A simple login panel
 * 
 * @author Matthias Mann
 */
public class HeadPanel extends HtmlObject {



    LivingThing livingThing;
    //final HeadPanel headPanel;

    boolean quit;
    Div bloodBar = new Div();
    Div blueBar = new Div();

    public HeadPanel(/*LivingThing livingThing*/) {
    /*this.livingThing=livingThing;*/
    //ProgressBar pro =new ProgressBar();
        //headPanel = new HeadPanel();//10行5列
        //CoreRegistry.put(HeadPanel.class,this);
       // this.position=HtmlObject.POSITION_ABSOLUTE;
        this.setId("headPanel");
        this.setMarginTop((short)10);
        this.setMarginLeft((short)30);
        this.setBorderWidth(2);
        this.setBorderColor(new Vector4f(1,1,1,1));

        Div headImg =new Div();
        headImg.setWidth(40);
        headImg.setHeight(40);
        headImg.setBackgroundImage(new Image(TextureManager.getTextureInfo("human_head_front")));

        this.appendChild(headImg);

        Div leftDiv =new Div();
        leftDiv.setDisplay(HtmlObject.INLINE);
        leftDiv.setWidth(100);

        bloodBar.setHeight(5);
        bloodBar.setId("bloodBar");
        bloodBar.setWidth(100);
        //bloodBar.setBackgroundImage(new Image(TextureManager.getTextureInfo("human_head_front")));
        bloodBar.setBorderColor(new Vector4f(1,1,1,1));
        bloodBar.setBackgroundColor(new Vector4f(1,0,0,1));
        leftDiv.appendChild(bloodBar);

        leftDiv.appendChild(new Br());

        blueBar.setHeight(20);
        blueBar.setBorderWidth(1);
        blueBar.setBorderColor(new Vector4f(0, 0, 1, 1));
        blueBar.setWidth(100);
        blueBar.setBackgroundColor(new Vector4f(0,0,1,1));
        leftDiv.appendChild(blueBar);

        appendChild(leftDiv);
        //Div bloodColorDiv = new Div();
       // setTitle("head");//整个包裹的标题
        //setResizableAxis(ResizableAxis.NONE);//是否可以调整大小
        //add(headPanel);//添加panel
       // pro.setTheme("progressbar");
       // add(pro);
        //setMinSize(200,40);


       // setPosition(0,0);
    }
    @Override
    public void update(){

        if(livingThing!=null){

            int width_= 100*livingThing.nowHP/livingThing.HP;
            if(width_ ==0)width_=1;
            this.bloodBar.setWidth(width_);
            this.bloodBar.innerText = livingThing.nowHP+"/"+livingThing.HP;
            this.blueBar.setWidth(100*livingThing.nowMP/livingThing.MP);
            super.update();
        }else{
            this.setVisible(false);
        }


    }
    public HeadPanel bind(LivingThing livingThing){
        this.livingThing=livingThing;
        this.bloodBar.setWidth(100*livingThing.nowHP/livingThing.HP);
        this.bloodBar.innerText = livingThing.nowHP+"/"+livingThing.HP;
        this.blueBar.setWidth(100*livingThing.nowMP/livingThing.MP);
        Document.needUpdate=true;
        return this;
    }
    public void show(){
        this.setVisible(true);
    }

}
