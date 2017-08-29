package com.dozenx.game.engine.ui.ButtonBar.view;

import cola.machine.game.myblocks.animation.AnimationManager;
import cola.machine.game.myblocks.model.ui.html.Button;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;

/**
 * Created by dozen.zhang on 2017/5/31.
 */
public class ButtonBarView extends HtmlObject {

    public ButtonBarView(){
        this.setPosition(HtmlObject.POSITION_ABSOLUTE);
        this.setTop(700);
        this.setLeft(400);
        this.setWidth(400);
        this.setHeight(40);
        Button bagBtn =new Button();
        bagBtn.innerText = "背包";
        bagBtn.setWidth(50);
        bagBtn.setHeight(40);
        this.appendChild(bagBtn);
        bagBtn.addCallback(new Runnable() {
            @Override
            public void run() {
                //背包隐藏或者显示
                HtmlObject dom = getDocument().getElementById("bag");
                dom.setVisible(!dom.isVisible());
                Document.needUpdate=true;
            }
        });


        Button personBtn =new Button();
        personBtn.innerText = "个人属性";
        personBtn.setWidth(50);
        personBtn.setHeight(80);
        this.appendChild(personBtn);
        personBtn.addCallback(new Runnable() {
            @Override
            public void run() {
                //背包隐藏或者显示
                HtmlObject dom = getDocument().getElementById("personalPanel");
                dom.setVisible(!dom.isVisible());
                Document.needUpdate=true;
            }
        });



        Button reloatAnimation =new Button();
        reloatAnimation.innerText = "动画重置";
        reloatAnimation.setWidth(50);
        reloatAnimation.setHeight(80);
        this.appendChild(reloatAnimation);
        reloatAnimation.addCallback(new Runnable() {
            @Override
            public void run() {

                CoreRegistry.get(AnimationManager.class).init();

                Document.needUpdate=true;
            }
        });
    }
}
