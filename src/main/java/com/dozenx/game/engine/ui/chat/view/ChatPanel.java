package com.dozenx.game.engine.ui.chat.view;

/**
 * Created by colamachine on 16-6-24.
 */

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.html.Div;
import cola.machine.game.myblocks.model.ui.html.Document;
import cola.machine.game.myblocks.model.ui.html.EditField;
import cola.machine.game.myblocks.model.ui.html.HtmlObject;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.game.engine.command.SayCmd;
import com.dozenx.game.network.client.Client;
import com.dozenx.util.StringUtil;
import de.matthiasmann.twl.Event;

import javax.vecmath.Vector4f;
import java.util.LinkedList;

public class ChatPanel extends HtmlObject {
    public  final StringBuilder sb;
   /* private final HTMLTextAreaModel textAreaModel;
    private final TextArea textArea;*/
    private final EditField editField;
    private static int showCount=5;

    private static int maxCount =30;
    //private final ScrollPane scrollPane;
    private int curColor;
    private final Client client ;
    private final Div div ;
    public ChatPanel() {
        this.setBorderColor(new Vector4f(1,1,1,1));
        this.setBorderWidth(1);
        this.position=HtmlObject.POSITION_ABSOLUTE;
        this.setTop(Constants.WINDOW_HEIGHT-180-40);
        this.setLeft(0);
        //setTitle("Chat");
        CoreRegistry.put(ChatPanel.class,this);
        this.sb = new StringBuilder();
        /*this.textAreaModel = new HTMLTextAreaModel();
        this.textArea = new TextArea(textAreaModel);*/
        this.editField = new EditField();
        editField.setBorderColor(new Vector4f(1,1,1,1));
        editField.setBorderWidth(1);
        editField.setWidth(200);
        editField.setHeight(80);
         div =new Div();
        div.setBorderColor(new Vector4f(1,1,1,1));
        div.setBorderWidth(1);
        div.setWidth(200);
        div.setHeight(100);
        div.display=HtmlObject.BLOCK;
        div.innerText="无消息";
        this.appendChild(div);
        this.appendChild( editField);
        CoreRegistry.put(ChatPanel.class , this);
         client =CoreRegistry.get(Client.class);
        //client.start();
        editField.addCallback(new EditField.Callback() {
            public void callback(int key) {//调用顺序 gui的 handlekey
                //System.out.println(key);

                if(key == Event.KEY_RETURN) {

                    // cycle through 3 different colors/font styles

                    //client.send("say:"+Constants.userName+":"+editField.getText());
                    //Switcher.isChat=false;
                    String text =editField.getText();
                    if(StringUtil.isBlank(text)){
                        return;
                    }
                    text=text.trim();
                    if(text.startsWith("/createboxopen")){
                        TextureManager.createBoxShape();
                    }
                    if(text.startsWith("/createboxclose")){
                        TextureManager.createBoxCloseShape();
                    }
                    if(text.startsWith("/chunk")) {
                        String[] arr = text.split(" ");
                        if(arr.length>=4  && StringUtil.isNumeric(arr[3])) {
                            if(arr[1].equals("reload")){
                                if(StringUtil.isNumeric(arr[2])){
                                    int x=Integer.valueOf(arr[2]);

                                    if(StringUtil.isNumeric(arr[3])){
                                        int z=Integer.valueOf(arr[3]);

                                            //重新加载对应的方块区域
                                        CoreRegistry.get(WorldRendererLwjgl.class).updateChunksInProximity(true);
                                   //   CoreRegistry.get(ChunkProvider.class).createOrLoadChunk(new Vector3i(x,0,z));
                                    }
                                }
                            }
                        }
                    }
                    client.send(new SayCmd(0,Constants.userName,editField.getText()));
                    //appendRow("color"+curColor, editField.getText());
                    editField.setText("");
                    /*guiInstance.*/giveupKeyboardFocus();
                    //editField.setVisible(false);
                    /*editField.setText("");
                    curColor = (curColor + 1) % 3;
                    editField.giveupKeyboardFocus();
                    editField.setVisible(false);
                    Switcher.isChat=false;*/
                   // Switcher.isChat=false;
                  //  hide();
                }else  if(key == Event.KEY_ESCAPE) {
                    /*guiInstance.*/giveupKeyboardFocus();
                   // Switcher.isChat=false;

                }
            }
        });
        //scrollpane < textArea < textAreaModel
       /* textArea.addCallback(new TextArea.Callback() {
            public void handleLinkClicked(String href) {
                Sys.openURL(href);
            }
        });

        scrollPane = new ScrollPane(textArea);
        scrollPane.setFixed(ScrollPane.Fixed.HORIZONTAL);*/

      /*  DialogLayout l = new DialogLayout();//一种布局
        l.setTheme("content");
        l.setHorizontalGroup(l.createParallelGroup(scrollPane, editField));//意思是editField放入scrollpane中
        l.setVerticalGroup(l.createSequentialGroup(scrollPane, editField));*/

       // add(l);

       // appendRow("default", "Welcome to the chat demo. Type your messages below :)");
    }
    protected void keyboardFocusChildChanged(HtmlObject child) {
        /*if(child!=null){
            Switcher.isChat=true;
        }else{
            Switcher.isChat=false;
        }*/
    }
    protected void keyboardFocusGained() {
        Switcher.isChat=true;
    }
    protected void keyboardFocusLost() {
       Switcher.isChat=false;
    }
    public void check(){

        while(client.messages.size()>0 && client.messages.peek()!=null){
            SayCmd  msg = (SayCmd) client.messages.pop();
            appendRow("color"+curColor, msg.getMsg());
            Document.needUpdate=true;
        }
    }
    /*public void paintWidget(Document gui){
        while(client.messages.size()>0 && client.messages.peek()!=null){
            String msg = client.messages.pop();
            appendRow("color"+curColor, msg);

        }
    }*/

    public LinkedList<String> contents = new LinkedList<>();
    public void setFocus(){
        this.editField.requestKeyboardFocus();
    }
    public void showEdit(){
        this.editField.setVisible(true);
    }
    public  void appendRow(String font, String text) {
        //this.div.innerText = this.div.innerText+"\n"+text;
        contents.add(text);

        //如果超过制定条数 删除之前的数据
        if(contents.size()>maxCount){
            int size = contents.size();
            for(int i=0;i<size-maxCount;i++ ){
                contents.poll();
            }
           // contents.remove(0)
        }
        //清空
        this.div.innerText="";
        //默认显示倒数10条

        int  startIndex = 0;
        if(contents.size()>showCount){
            startIndex = contents.size()-showCount;

        }
        int endIndex = contents.size();
        for(int i=startIndex;i<endIndex;i++){
            this.div.innerText += "\n"+contents.get(i);
        }
        //this.div.innerText = this.div.innerText+"\n"+text;
         Document.needUpdate=true;
    }

    public void appendString(String message){
        sb.append(message);
    }
//public void hide(){
  //  this.fadeToHide(5);

    //this.fadeTo(Color.,1000);
    //this.setTheme(null);
//}
    private boolean isURLChar(char ch) {
        return (ch == '.') || (ch == '/') || (ch == '%') ||
                (ch >= '0' && ch <= '9') ||
                (ch >= 'a' && ch <= 'z') ||
                (ch >= 'A' && ch <= 'Z');
    }
}
