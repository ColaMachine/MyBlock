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
import com.dozenx.game.opengl.util.OpenglUtils;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ThemeInfo;
import org.lwjgl.opengl.GL11;


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
    byte[] yellow=new byte[]{(byte)255,(byte)255,(byte)0};
    byte[] whiteColor=new byte[]{(byte)245,(byte)245,(byte)245};
    byte[] blue=new byte[]{(byte)0,(byte)0,(byte)250};
    de.matthiasmann.twl.renderer.Font font;

    @Override
    protected void applyTheme(ThemeInfo themeInfo) {
        super.applyTheme(themeInfo);

        font = themeInfo.getFont("black");
        //findIcon();
    }
    float[] result=new float[4];
    @Override//静态绘制
    protected void paintWidget(GUI gui) {
//GL11.glPushMatrix();
        /*GLApp.project(livingThing.position.x, livingThing.position.y + 2, livingThing.position.z, result);
        GL11.glPopMatrix();
        GL_Vector.*/
//GLApp.getViewport();
        //OpenglUtil.WorldToScreen(new GL_Vector(livingThing.position.x, livingThing.position.y + 2, livingThing.position.z) );
        //result=GLApp.project((int)livingThing.position.x,(int)livingThing.position.y+2,livingThing.position.z);

        //float[] vector=new float[3];
       // GLApp.project(livingThing.position.x, livingThing.position.y + 2, livingThing.position.z,result);
        // LogUtil.println("x:"+vector[0]+"x:"+vector[1]+"x:"+vector[2]);

        if(livingThing!=null){
            //GL11.glDisable(GL11.GL_LIGHTING); // no lighting
            // GL11.glDisable(GL11.GL_DEPTH_TEST); // no depth test
            //GL11.glClearColor(0,0,0,0);
            // GL11.glDisable(GL11.GL_BLEND); // enable transparency
             //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); //
           // GL11.glDisable(GL11.glre);

            //GL11.glClearColor(255,255,255,0);
            //GL11.glClearColor(1,1,1,0);
            GL11.glColor4f(1f,1f,1f,1f);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            TextureManager.getTextureInfo("human_head_front").draw(null,this.getInnerX(),this.getInnerY(),headWidth,headHeight);

            //TextureManager.getTextureInfo("human_head_front").draw(null,(int)result[0],(int)result[1],headWidth,headHeight);


            OpenglUtils.glFillRect(this.getInnerX() + 44, this.getInnerY() + 4, 150, 20, lineWdith, borderColor, whiteColor);
            OpenglUtils.glFillRect(this.getInnerX() + 44, this.getInnerY() + 4, 150 * livingThing.nowHP / livingThing.HP, 20, lineWdith, borderColor, redColor);

            OpenglUtils.glFillRect(this.getInnerX() + 44, this.getInnerY() + 30, 150, 20, lineWdith, borderColor, whiteColor);
            OpenglUtils.glFillRect(this.getInnerX() + 44, this.getInnerY() + 30, 150 * livingThing.nowMP / livingThing.MP, 20, lineWdith, borderColor, blue);

            font.drawText(getAnimationState(),getInnerX()+44,getInnerY()+4,livingThing.nowHP+"/"+livingThing.HP);
            font.drawText(getAnimationState(),getInnerX()+44,getInnerY()+30,livingThing.nowMP+"/"+livingThing.MP);


/*
float vector[]= livingThing.vector;
        TextureManager.getTextureInfo("human_head_front").draw(null,(int)vector[0],(int)vector[1],40,40);

        GLApp.glFillRect((int)vector[0]-75,(int)vector[1], 150, 10, 4, borderColor, whiteColor);
        GLApp.glFillRect((int)vector[0]-75,(int)vector[1]+lineWdith,150*livingThing.nowBlood/livingThing.blood,10,lineWdith,borderColor,yellow);

        GLApp.glFillRect((int)vector[0]-75,(int)vector[1]+12,150,10,lineWdith,borderColor,whiteColor);
        GLApp.glFillRect((int)vector[0]-75,(int)vector[1]+12,150*livingThing.nowEnergy/livingThing.energy,10,lineWdith,borderColor,blue);

        GLApp.print((int)vector[0],(int)vector[1]+30,"hello");*/




        }

    }

}
