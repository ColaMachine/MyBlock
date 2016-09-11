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

import cola.machine.game.myblocks.bean.ItemEntity;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.renderer.AnimationState.StateKey;
import de.matthiasmann.twl.renderer.Font;
import de.matthiasmann.twl.renderer.Image;

/**
 *
 * @author Matthias Mann
 */
public class ItemWrap extends Widget {


    private ItemEntity itemEntity;

    private Image icon;

    private  TextureInfo icon2;

    private Label label;


    private ParameterMap icons;

    public ItemWrap(ItemEntity itemEntity) {
        this.itemEntity=itemEntity;
       // label =new Label();
      // label.setText(""+itemEntity.getNum());
       // add(label);
        //label.setPosition(30,30);
        icon2= TextureManager.getTextureInfo(itemEntity.getName());

    }
    public int getNum(){
        return itemEntity.getNum();
    }
    public void  setNum(int num){
        this.itemEntity.setNum(num);
    }
    public String getItem() {
        return itemEntity.getName();
    }

    public void setItem(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
        findIcon();//根据name 查找
    }




    public Image getIcon() {
        return icon;
    }
    public TextureInfo getIcon2() {
        return icon2;
    }
    public void setIcon(Image icon) {
        this. icon=icon;
    }
    public void setIcon(TextureInfo icon) {
        this. icon2=icon;
    }

  //  @Override//静态绘制
   /* protected void paintWidget(GUI gui) {

            //icon.draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            icon2.draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            //font.drawText(getAnimationState(),getInnerX()+15,getInnerY()+15,num+"");

    }*/


    //@Override
    protected void applyTheme(ThemeInfo themeInfo) {
        super.applyTheme(themeInfo);
        icons = themeInfo.getParameterMap("icons");

        findIcon();
      //  this.setBackground(icon);
    }
    
    private void findIcon() {
        if(itemEntity == null || icons == null) {
            icon = null;
        } else {
            icon2= TextureManager.getTextureInfo(itemEntity.getName());
            icon = icons.getImage(itemEntity.getName());
        }
    }
}
