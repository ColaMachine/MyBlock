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

import cola.machine.game.myblocks.lifething.bean.LivingThing;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.ui.test.FadeFrame;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ProgressBar;
import de.matthiasmann.twl.ThemeInfo;
import de.matthiasmann.twl.renderer.*;
import org.lwjgl.opengl.GL11;
import util.OpenglUtil;

import java.awt.*;


/**
 * A simple login panel
 * 
 * @author Matthias Mann
 */
public class HeadDialog extends FadeFrame {



    LivingThing livingThing;
    final HeadPanel headPanel;

    boolean quit;

    public HeadDialog(/*LivingThing livingThing*/) {
    /*this.livingThing=livingThing;*/
    //ProgressBar pro =new ProgressBar();
        headPanel = new HeadPanel();//10行5列
        
        setTheme("chat");
        setTitle("head");//整个包裹的标题
        setResizableAxis(ResizableAxis.NONE);//是否可以调整大小
        add(headPanel);//添加panel
       // pro.setTheme("progressbar");
       // add(pro);
        setMinSize(200,40);


        setPosition(0,0);
    }
    public HeadDialog bind(LivingThing livingThing){
        this.livingThing=livingThing;
        return this;
    }
    public void show(){
        this.setVisible(true);
    }

    final int headWidth =40;
    final int headHeight=40;
    final int space=2;
    final int bloodWdith=150;
    final int bloodHeight=20;
    final int heightSpace = 10;
    int lineWdith=1;
    byte[] borderColor=new byte[]{0,0,0};
    byte[] redColor=new byte[]{(byte)245,(byte)0,(byte)0};
    byte[] whiteColor=new byte[]{(byte)245,(byte)245,(byte)245};
    byte[] blue=new byte[]{(byte)0,(byte)0,(byte)250};
    de.matthiasmann.twl.renderer.Font font;

    @Override
    protected void applyTheme(ThemeInfo themeInfo) {
        super.applyTheme(themeInfo);

        font = themeInfo.getFont("black");
        //findIcon();
    }

    @Override//静态绘制
    protected void paintWidget(GUI gui) {
        if(livingThing!=null){
            TextureManager.getTextureInfo("human_head_front").draw(null,this.getInnerX(),this.getInnerY(),headWidth,headHeight);



            OpenglUtil.glFillRect(this.getInnerX()+44,this.getInnerY()+4,150,20,lineWdith,borderColor,whiteColor);
            OpenglUtil.glFillRect(this.getInnerX()+44,this.getInnerY()+4,150*livingThing.nowBlood/livingThing.blood,20,lineWdith,borderColor,redColor);

            OpenglUtil.glFillRect(this.getInnerX()+44,this.getInnerY()+30,150,20,lineWdith,borderColor,whiteColor);
            OpenglUtil.glFillRect(this.getInnerX()+44,this.getInnerY()+30,150*livingThing.nowEnergy/livingThing.energy,20,lineWdith,borderColor,blue);

            font.drawText(getAnimationState(),getInnerX()+44,getInnerY()+4,livingThing.nowBlood+"/"+livingThing.blood);
            font.drawText(getAnimationState(),getInnerX()+44,getInnerY()+30,livingThing.nowEnergy+"/"+livingThing.energy);

        }

    }

}
