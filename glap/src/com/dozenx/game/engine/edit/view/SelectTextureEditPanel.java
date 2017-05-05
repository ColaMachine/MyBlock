package com.dozenx.game.engine.edit.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureCfgBean;
import cola.machine.game.myblocks.model.ui.html.*;
import com.alibaba.fastjson.JSON;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import de.matthiasmann.twl.Event;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import java.io.IOException;
import java.util.List;

/**
 * Created by luying on 17/2/9.
 */
public class SelectTextureEditPanel extends HtmlObject{
    //private HtmlObject chooseObj = null;
    HtmlObject parent = this;
    Div selectBox =new Div();
    Div image = new Div();
    Div form  = new Div();
    public SelectTextureEditPanel(){
        this.setBorderWidth(1);
        this.id="SelectTextureEditPanel";
        this.setBorderColor(new Vector4f(1, 1, 1, 1));
        this.setPosition(HtmlObject.POSITION_ABSOLUTE);
        image.id="image";
        image.setWidth(400);
        image.setHeight(400);
        image.setLeft(100);
        image.setLeft(100);
        image.setBorderWidth(1);
        image.setBorderColor(new Vector4f(1, 1, 1, 1));
        //image.setPosition(HtmlObject.POSITION_ABSOLUTE);
        image.setBackgroundImage(new Image(TextureManager.getTextureInfo("cross")));
        /*this.texture.minX=0;
        this.texture.minY=0;
        this.texture.maxX=1;
        this.texture.maxY=1;*/
        selectBox.setWidth(10);
        selectBox.setHeight(10);
        selectBox.setBorderWidth(1);
        selectBox.setBorderColor(new Vector4f(1, 1, 1, 1));
        selectBox.setPosition(HtmlObject.POSITION_ABSOLUTE);

        image.appendChild(selectBox);
         final EditField input =new EditField();
    this.appendChild(image);
        this.appendChild(form);

        image.onClick =new MouseClickHandler() {
            @Override
            public void run() {
                if (evt.getType() == Event.Type.MOUSE_BTNDOWN) {
                    LogUtil.println("点击");
                    if (point == 0) {
                        firstPoint.x = evt.getMouseX() - image.getPosX();
                        firstPoint.y = evt.getMouseY() - image.getPosY();
                        point++;
                    } else {
                        secondPoint.x = evt.getMouseX() - image.getPosX();
                        secondPoint.y = evt.getMouseY() - image.getPosY();

                        selectBox.setLeft((int) Math.min(firstPoint.x, secondPoint.x));

                        selectBox.setTop((int) Math.min(firstPoint.y, secondPoint.y));
                        selectBox.setWidth((int) Math.abs(firstPoint.x - secondPoint.x));
                        selectBox.setHeight((int) Math.abs(firstPoint.y - secondPoint.y));
                        Document.needUpdate = true;

                        //弹出新的编写名称的输入框

                        point = 0;
                    }

                }
            }
        }
        ;
        form.appendChild(input);
        form.appendChild(new Br());
        Button save =new Button("保存");
        form.appendChild(save);
        save.addCallback(new Runnable() {
            @Override
            public void run() {
                //记录所有的xywh 然后计算所有
                TextureCfgBean cfgBean = new TextureCfgBean();
                int x = selectBox.getLeft();
                int y = selectBox.getTop();
                int w = selectBox.getWidth();
                int h = selectBox.getHeight();
                int imageWidht = image.getBackgroundImage().getTexture().getImgWidth();
                int imageHeight = image.getBackgroundImage().getTexture().getImgHeight();

                x = imageWidht * x / image.getWidth();
                y = imageHeight * (image.getHeight() - y - h) / image.getHeight();
                w = imageWidht * w / image.getWidth();
                h = imageHeight * h / image.getHeight();
                String xywh = x + "," + y + "," + w + "," + h;
                cfgBean.setXywh(xywh);
                cfgBean.setImage(image.getBackgroundImage().getTexture().imageName);
                cfgBean.setName(input.getText());

                LogUtil.println(JSON.toJSONString(cfgBean));
            }
        });
    }

     Vector2f firstPoint =new Vector2f();
         Vector2f secondPoint =new Vector2f();
         int point =0 ;
   /* @Override
    protected boolean handleEvent(Event evt) {

        if(super.handleEvent(evt)) {
            return true;
        }

            if (evt.getType() == Event.Type.MOUSE_CLICKED) {
                LogUtil.println("点击");
                if (point == 0) {
                    firstPoint.x = evt.getMouseX() - this.getLeft();
                    firstPoint.y = evt.getMouseY() - this.getTop();
                    point++;
                } else {
                    secondPoint.x = evt.getMouseX() - this.getLeft();
                    secondPoint.y = evt.getMouseY() - this.getTop();

                    selectBox.setLeft((int) Math.min(firstPoint.x, secondPoint.x));

                    selectBox.setTop((int) Math.min(firstPoint.y, secondPoint.y));
                    selectBox.setWidth((int) Math.abs(firstPoint.x - secondPoint.x));
                    selectBox.setHeight((int) Math.abs(firstPoint.y - secondPoint.y));
                    Document.needUpdate = true;

                    //弹出新的编写名称的输入框

                    point = 0;
                }
                return true;
            }


        return true;
    }*/
}
