package com.dozenx.game.engine.edit.view;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureCfgBean;
import cola.machine.game.myblocks.model.ui.html.*;
import com.alibaba.fastjson.JSON;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import de.matthiasmann.twl.Event;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by luying on 17/2/9.
 */
public class TextureEditPanel extends HtmlObject{
    //private HtmlObject chooseObj = null;
    public TextureEditPanel(){
        List<String> list=null;
        try {
            list = FileUtil.readFile2List(PathManager.getInstance().getHomePath().resolve("newItems.cfg").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final HtmlObject parent =this;
        final int bili =2;
        final Div wrap =new Div();
        int totalWidth = Constants.WINDOW_WIDTH-100;
        int totalHeight =Constants.WINDOW_HEIGHT-100;
        wrap.setBackgroundColor(new Vector4f(0,1,0,1));
        this.setPosition(HtmlObject.POSITION_ABSOLUTE);
        //wrap.setPosition(HtmlObject.POSITION_ABSOLUTE);
        wrap.setBackgroundImage(new Image(TextureManager.getTextureInfo("items")));
        wrap.setBorderWidth(1);
        wrap.setBorderColor(new Vector4f(1, 1, 1, 1));
        wrap.setWidth(totalWidth);
        wrap.setHeight(totalHeight);
       /* final EditField titleEdit = new EditField();
        titleEdit.addCallback(new EditField.Callback() {
            @Override
            public void callback(int key) {
                if(key == Event.KEY_RETURN) {

                    chooseObj.id = titleEdit.getText();
                }
            }
        });*/
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                Div div =new Div();
                div.setWidth(totalWidth/16);
                div.setHeight(totalHeight/16);
                div.setBorderWidth(1);
                div.setBorderColor(new Vector4f(1, 1, 1, 1));
              /* div.click=new Runnable() {
                   @Override
                   public void run() {
                      // chooseObj=get;
                       titleEdit.setText(getId());
                       Document.needUpdate=true;
                   }
               };*/
                div.setDisplay(HtmlObject.INLINE);
                EditField edit = new EditField();
                int index =i*16+j;
                if(list!=null && list.size()>index){
                    String name = list.get(index);

                    try {
                        if (StringUtil.isNotEmpty(name)) {
                            if(name.lastIndexOf(',') == name.length()-1){
                                name = name.substring(0,name.length()-1);
                            }
                            TextureCfgBean textureCfgBeanList = JSON.parseObject(name, TextureCfgBean.class);
                            edit.setText(textureCfgBeanList.getName());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                edit.setBackgroundColor(null);//new Vector4f(1,1,1,0.5f)
                edit.setColor(new Vector4f(1,1,1,1));
                div.appendChild(edit);
                wrap.appendChild(div);

            }
            //this.appendChild(new Br());
        }
        Button button =new Button();
        button.innerText="保存";
        button.setBackgroundColor(new Vector4f(1,0,0,1));
        button.setFontSize(24);
        button.setBorderWidth(1);
        this.appendChild(wrap);
        this.appendChild(button);

       /* this.appendChild(edit);
        edit.setFontSize(18);*/

        button.addCallback(new Runnable() {

            @Override
            public void run() {
                StringBuffer sb =new StringBuffer();
                for(int i=0;i<wrap.childNodes.size();i++){
                    if(   wrap.childNodes.get(i) instanceof Button ){
                        break;
                    }
                    Div div =(Div)wrap.childNodes.get(i);


                    EditField edit = (EditField)div.getChildNodes().get(0);
                    if(edit!=null && StringUtil.isNotEmpty(edit.getText())){
                        int left = i % 16 * 16;
                        int top = 16*16-(i / 16)*16-16;
                        int width =16;
                        int height =16;
                        String name =edit.getText().trim();

                        sb.append("{name:").append("\"").append(name).append("\",")
                                .append("xywh:\"").append(left).append(",").append(top).append(",").append(width).append(",").append(height).append("\",")
                                .append("image:").append("\"items\"").append("},\n");

                    }else{
                        sb.append("\n");
                    }

                }
                try {
                    FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("newItems.cfg").toFile(),sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
