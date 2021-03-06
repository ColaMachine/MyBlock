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

import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.model.ui.html.Image;
import com.dozenx.game.engine.edit.view.MouseClickHandler;
import com.dozenx.game.engine.item.bean.ItemBean;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import core.log.LogUtil;

import javax.vecmath.Vector4f;

/**
 * iconview 就是 itembean 的展现层
 * @author Matthias Mann
 */
public class IconView extends HtmlObject {
   // public int type ;

    /*public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }*/
    public IconView(){
        /*this.setBorderColor(new Vector4f(1,1,1,1));
        this.setBorderWidth(1);
        this.setWidth(40);
        this.setHeight(40);
        */

    }

   /* public SkillDefinition getSkillDefinition() {
        return skillDefinition;
    }*/

    /*public void setSkillDefinition(SkillDefinition skillDefinition) {
        this.skillDefinition = skillDefinition;
    }*/

    private ItemDefinition itemDefinition;
   // private SkillDefinition skillDefinition;


    private ItemBean itemBean;
/*
    private Image icon;

    private  TextureInfo icon2;




    private ParameterMap icons;*/

    public IconView(ItemBean itemBean) {
        this.itemBean = itemBean;
        this.innerText=itemBean.getNum()+"";
        this.setBorderColor(new Vector4f(1,1,1,1));
        this.setBorderWidth(1);
        this.setWidth(40);
        this.setHeight(40);
       // label =new Label();
      // label.setText(""+itemBean.getNum());
       // add(label);
        //label.setPosition(30,30);
        if(itemBean==null || itemBean.getItemDefinition() == null || itemBean.getItemDefinition().getItemModel()==null){
            LogUtil.err(" null ");
        }
         this.setBackgroundImage(new Image(itemBean.getItemDefinition().getItemModel().getIcon()));
   /*     icon2= TextureManager.getItemCfg(itemBean.getName()).getIcon();
        if(icon2==  null){
            assert icon2!=null;
        }*/
        this.itemDefinition = itemBean.getItemDefinition();
        this.childNodes.clear();

     final   Div itemDescribeDiv = new Div();

        itemDescribeDiv.setVisible(false);
        itemDescribeDiv.setWidth(50);
        //div.setHeight(50);
        itemDescribeDiv.setFontSize(10);
        itemDescribeDiv.setInnerText( itemBean.getItemDefinition().getName()+"*"+itemBean.getNum());
        itemDescribeDiv.setBackgroundColor(new Vector4f(1,1,1,1));
        this.appendChild(itemDescribeDiv);
      //  this.title ="+\"x\"+itemBean.getItemDefinition().getName()";
        if(itemDefinition==  null) {
            assert this.itemDefinition!=null;
        }
        this.addEventListener("mouseover", new MouseClickHandler() {
            @Override
            public void run() {
               // LogUtil.println("show"+parentNode.id);
                //                LogUtil.println("show"+parentNode.id +"parentNode.x:"+parentNode.getMinX()+"parentNode.y:"+parentNode.getMinY()+"+evt.x:"+this.evt.getMouseX()+"y:"+evt.getMouseY());
                itemDescribeDiv.setVisible(true);
                //div.setInnerText(itemBean.getItemDefinition().getName()+"*"+itemBean.getNum());
                Document.needUpdate=true;
            }
        });
        this.addEventListener("mouseout", new MouseClickHandler() {
            @Override
            public void run() {
                //    LogUtil.println("hide"+parentNode.id +"parentNode.x:"+parentNode.getMinX()+"parentNode.y:"+parentNode.getMinY()+"+evt.x:"+this.evt.getMouseX()+"y:"+evt.getMouseY());
                itemDescribeDiv.setVisible(false);
                Document.needUpdate=true;
            }
        });
    }
    /*public IconView(SkillDefinition skillDefinition) {
        this.skillDefinition=skillDefinition;
        // label =new Label();
        // label.setText(""+itemBean.getNum());
        // add(label);
        //label.setPosition(30,30);
       // icon2= TextureManager.getItemCfg(skillDefinition.getIcon().getName()).getIcon();
       *//* if(icon2==  null){
            assert icon2!=null;
        }*//*
        this.itemDefinition = TextureManager.getItemCfg(itemBean.getName());
        if(itemDefinition==  null) {
            LogUtil.err("itemDefinition should not be null");
            assert this.itemDefinition!=null;
        }
    }*/
    public int getNum(){
        return itemBean.getNum();
    }
    public void  setNum(int num){
        this.itemBean.setNum(num);
        this.innerText=num+"";
        Document.needUpdate=true;
    }
    public String getItem() {
        return itemBean.getItemDefinition().getName();
    }

    public void setItem(ItemBean itemBean) {
        this.itemBean = itemBean;
        //findIcon();//根据name 查找
    }




  /*  public Image getIcon() {
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
    }*/

  //  @Override//静态绘制
   /* protected void paintWidget(GUI gui) {

            //icon.draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            icon2.draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight());
            //font.drawText(getAnimationState(),getInnerX()+15,getInnerY()+15,num+"");

    }*/


   /* //@Override
    protected void applyTheme(ThemeInfo themeInfo) {
        //super.applyTheme(themeInfo);
        icons = themeInfo.getParameterMap("icons");

        findIcon();
      //  this.setBackground(icon);
    }*/

   /* private void findIcon() {
        if(itemBean == null || icons == null) {
            icon = null;
        } else {
            icon2= TextureManager.getTextureInfo(itemBean.getName());
           // icon = icons.getImage(itemBean.getName());
        }
    }*/


    public ItemDefinition getItemDefinition() {
        return itemDefinition;
    }

    public void setItemDefinition(ItemDefinition itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

   /* public void setIcon2(TextureInfo icon2) {
        this.icon2 = icon2;
    }


*//*
    public void setLabel(Label label) {
        this.label = label;
    }*//*

    public ParameterMap getIcons() {
        return icons;
    }*/

 /*   public void setIcons(ParameterMap icons) {
        this.icons = icons;
    }*/

}
