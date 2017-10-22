package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.engine.BlockEngine;
import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.GameEngine;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.model.ui.html.*;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.ui.login.LoginDemo;
import com.dozenx.game.engine.Role.bean.Player;
import com.dozenx.game.engine.Role.controller.LivingThingManager;
import com.dozenx.game.engine.command.LoginCmd;
import com.dozenx.game.engine.command.PlayerSynCmd;
import com.dozenx.game.font.Glyph;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.network.client.Client;
import com.dozenx.game.network.client.bean.GameCallBackTask;
import com.dozenx.game.opengl.util.OpenglUtils;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.vecmath.Vector4f;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class StartMenuState implements GameState {
   //Bag bag;
    public static String catchThing;
    //LearnOpenglColor learnOpenglColor;
    GUI gui;
    Document document =new Document();

    EditField userName ;
    EditField pwd ;
    private Map<Character, Glyph> glyphs = new HashMap<>();
    public void init(GameEngine engine) {  OpenglUtils.checkGLError();
//        if (i == 127) {
//            continue;
//        }
//        char c = (char) i;
//        BufferedImage charImage = createCharImage(font, c, antiAlias);
//
//        int charWidth = charImage.getWidth();
//        int charHeight = charImage.getHeight();


        Client client = new Client();
        client.start();
        CoreRegistry.put(Client.class, client);

        TextureManager textureManager =new TextureManager();

        ShaderManager manager =  ShaderManager.getInstance();
        manager.init();
       // manager.createProgram(manager.uiShaderConfig);
        //manager.initUniform(manager.uiShaderConfig);



        Div div2=new Div();
        Div div3=new Div();
       // document.body.appendChild(div2);
        //document.body.appendChild(div3);

        Div div =new Div();div.setId("1");
        div.setMarginLeft((short)(Constants.WINDOW_WIDTH/2-100));
        div.setMarginTop((short)(Constants.WINDOW_HEIGHT/2-50));
        Label userNameLable = new Label("用户名:");
        userNameLable.setWidth(50);
        //userNameLable.setFontSize(55);
        userNameLable.setColor(new Vector4f(1,1,1,1));
        div.appendChild(userNameLable);
         userName = new EditField();
         pwd = new EditField();
        div.appendChild(userName);
       Br br = new Br();
        br.setTop(12);
        div.appendChild(br);
        Label pwdLable = new Label("密码:");
        pwdLable.setWidth(50);
        div.appendChild(pwdLable);
        pwdLable.setColor(new Vector4f(1,1,1,1));
        div.appendChild(pwd);

        div.setBorderWidth(2);
        div.setBorderColor(new Vector4f(1,0,0,1));
        div.appendChild(new Br());

        Button button =new Button();
        button .setColor(new Vector4f(0,0,0,1));
        button.innerText="登录";
        button.setFontSize(34);
        button.textAlign="center";
        //button.setTextAlign("center");
        button.setWidth(100);
        button.setPaddingTop((short)15);
        button.setHeight(50);
      //  button.setBackgroundImage(new Image(TextureManager.getTextureInfo("gridimage")));
        button.setTop(25);
        button.setBorderWidth(2);
        //button.setBorderColor(new Vector4f(1,0,0,1));
        button.setBackgroundImage(new Image(TextureManager.getTextureInfo("button")));
        button.addCallback(new Runnable() {
            @Override
            public void run() {
                emulateLogin();
               // LogUtil.println("nihao");
            }
        });
       // button.setHeight(30);
        //button.setBackgroundColor(new Vector4f(1,1,1,0));
        div.appendChild(button);
        document.body.appendChild(div);
       /* Div gridDiv =new Div();
        gridDiv.setWidth(500);
        gridDiv.setHeight(200);
        gridDiv.setTop(20);
        gridDiv.setBackgroundImage(new Image(TextureManager.getTextureInfo("button")));
        document.body.appendChild(gridDiv);*/
        //gridDiv.setBorderWidth(2);
        //gridDiv.setBorderColor(new Vector4f(1,0,0,1));
     // document.body.appendChild(new TextureEditPanel());
       // div=new Div();
       // bag =new Bag();

        //div.setId("bag");
        //document.body.appendChild(div3);
        //document.body.appendChild(div2);
        //document.body.appendChild(div);
       /* div.appendChild(div3);
        div.appendChild(div2);
        //div.margin="0 auto";
       // div.setWidth(100);
       // div.setHeight(100);
        div.setLeft(110);
        div.setTop(110);
        div.setFontSize(24);
        div2.setFontSize(24);
        div3.setFontSize(24);
        //div.bottom=100;
        div.setBorderWidth(10);
       div.setInnerText("一行白鹭路上青天");*/
        //div.background_image="soil";
        //TextureInfo batTexture= TextureManager.getTextureInfo("soil");
       // div.set(new Vector4f(1,0.5f,0.5f,1));
       // div.setBackgroundImage(new Image(TextureManager.getTextureInfo("fur_pants")));
        div.setBorderColor(new Vector4f(1,0.5f,0.5f,1));
        //div.setBackgroundColor(new Vector4f(1,1f,1f,1));
        //div.update();

      /*  EditField editField =new EditField();
        //Image image = TextureManager.createImage("");
        //editField.setBackgroundImage(new GridImage());
        editField.setWidth(100);
        editField.multiLine =true;
       editField.setBackgroundColor(new Vector4f(1,0.85f,0.85f,1));
        editField.setHeight(100);
        editField.setFontSize(25);
        editField.setLeft(210);
        editField.setTop(110);
        editField.setText("你好啊");
        editField.display=HtmlObject.BLOCK;
        editField.setBorderWidth(10);
        document.body.appendChild(editField);
        editField.setBorderColor(new Vector4f(1,0.5f,0.5f,1));*/

        //div.margin="0 auto";
        div2.setWidth(100);
        div2.setBackgroundColor(new Vector4f(1,0.85f,0.85f,1));
        div2.setHeight(100);
        div2.setLeft(210);
        div2.setTop(110);
        div2.setInnerText("两只黄鹂鸣翠柳");
        //div.bottom=100;
        div2.setBorderWidth(10);
        //div.background_image="soil";
        //TextureInfo batTexture= TextureManager.getTextureInfo("soil");
        // div.set(new Vector4f(1,0.5f,0.5f,1));
       // div2.setBackgroundImage(new Image(TextureManager.getTextureInfo("iron_helmet_front")));
        div2.setBorderColor(new Vector4f(1,0.5f,0.5f,1));
        //div.setBackgroundColor(new Vector4f(1,1f,1f,1));
       // div2.update();
        OpenglUtils.checkGLError();


        div3.setWidth(100);
        div3.setHeight(100);
        div3.setLeft(310);
        div3.setLeft(310);
        div3.setInnerText("三个铜板买来的");
        //div.bottom=100;
        div3.setBorderWidth(10);
        //div.background_image="soil";
        //TextureInfo batTexture= TextureManager.getTextureInfo("soil");
        // div.set(new Vector4f(1,0.5f,0.5f,1));
       // div3.setBackgroundImage(new Image(TextureManager.getTextureInfo("zhongwen")));
        div3.setBorderColor(new Vector4f(1,0.5f,0.5f,1));
        //div.setBackgroundColor(new Vector4f(1,1f,1f,1));


        try {


            LWJGLRenderer renderer = new LWJGLRenderer();//调用lwjgl能力
            renderer.setUseSWMouseCursors(true);   OpenglUtils.checkGLError();

            //ChatDemo chat = new ChatDemo();
            //GameUIDemo gameUI = new GameUIDemo();
            LoginDemo loginDemo = new LoginDemo();
            gui = new GUI(loginDemo, renderer);
if(!Switcher.SHADER_ENABLE) {
    ThemeManager theme = ThemeManager.createThemeManager(
            LoginDemo.class.getResource("login.xml"), renderer);
    gui.applyTheme(theme);
}
        } catch (Exception e) {
            e.printStackTrace();
        }   OpenglUtils.checkGLError();

        //ShaderUtils.twoDColorBuffer.rewind();   OpenglUtils.checkGLError();
        document.needUpdate=true;
        // this.setPerspective();
        //div.shaderRender();   OpenglUtils.checkGLError();
       // div2.shaderRender();   OpenglUtils.checkGLError();
        //div3.shaderRender();   OpenglUtils.checkGLError();
        //bag.shaderRender();


       // ShaderUtils.update2dColorVao();   OpenglUtils.checkGLError();
        /*ShaderManager shaderManager =new ShaderManager();
        shaderManager.init();*/
        //learnOpenglColor=new LearnOpenglColor();

       /* GLImage image;
        image= OpenglUtils.makeTexture("assets/images/items.png");
        glBindTexture(GL_TEXTURE_2D, image.textureHandle);*/

    }

    public void dispose() {

    }


    public void handleInput(float delta) {


        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {

               /* gui.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState());*/
                document.handleKey(
                        Keyboard.getEventKey(),
                        Keyboard.getEventCharacter(),
                        Keyboard.getEventKeyState());

            }
        }
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                document.handleMouse(
                        Mouse.getEventX(), gui.getHeight() - Mouse.getEventY() - 1,
                        Mouse.getEventButton(), Mouse.getEventButtonState());

            }
        }


    }


    public void update(float delta) {
        //TODO recover
        ShaderManager.uiShaderConfig.getVao().getVertices().clear();
       if( Document.needUpdate){

            document.update();


            //ShaderUtils.twoDColorBuffer.clear();   OpenglUtils.checkGLError();
            //ShaderManager.uiShaderConfig.getVao().getVertices().clear();   OpenglUtils.checkGLError();

            // this.setPerspective();
          //  document.update();
            //div.shaderRender();   OpenglUtils.checkGLError();
            // div2.shaderRender();   OpenglUtils.checkGLError();
            //div3.shaderRender();   OpenglUtils.checkGLError();
            //bag.shaderRender();

          //  ShaderUtils.update2dColorVao();   OpenglUtils.checkGLError();

            Document.needUpdate=false;
       }
    }

    public float rotation = 0f;

    @Override
    public void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

       /* try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //bag.shaderRender();
        //glUseProgram(0);

      // gui.update();
        //OpenglUtils.drawRect();
        //print(30, viewportH - 135, "fps:" );
        //  GLApp.drawRect(1,1,50,50);
       /* count++;
        if(count<1000)*/
        //ShaderUtils.finalDraw(ShaderManager.uiShaderConfig);//2DImage
    /*  ShaderManager.uiShaderConfig.getVao().getVertices().rewind();
        ShaderUtils.draw2dColor(new Vector4f(1,1,1,1),0,0,0.004f,1,1);   OpenglUtils.checkGLError();
        ShaderUtils.createVao(ShaderManager.uiShaderConfig,ShaderManager.uiShaderConfig.getVao(),new int[]{3,2,1,4});*/
        document.render();
       // ShaderUtils.finalDraw(ShaderManager.livingThingShaderConfig);//2DImage
      //  ShaderUtils.finalDraw(ShaderManager.terrainShaderConfig);//2DImage
      //  ShaderUtils.finalDraw(ShaderManager.lightShaderConfig);//2DImage




        //ShaderUtils.finalDraw2DColor();
        try{
           // Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
   // int count =10;
   void emulateLogin() {
       if(BlockEngine.engine!=null ){

         //  CoreRegistry.get(Client.class).send(new LoginCmd(userName.getText(),pwd.getText()));
          // ResultDTO dto = CoreRegistry.get(LoginClientController.class).login(userName.getText(),pwd.getText());
           GameCallBackTask task = new GameCallBackTask(){
               @Override
               public void run(){
                    if(getResult().getResult()==0){


                        if(getResult().getMsg()!=null){
                            PlayerSynCmd cmd =new  PlayerSynCmd(getResult().getMsg());
                            //PlayerStatus status = JSON.parseObject(getResult().getMsg(),PlayerStatus.class);
                            GamingState state = new GamingState();
                            Constants.USER_ID=cmd.getPlayerStatus().getId();
                            Constants.userName = userName.getText();

                            //进行加载

                            //创建Human
                           Player player = new Player(cmd.getPlayerStatus().getId());
                            player.setInfo(cmd.getPlayerStatus() );//要等所有的东西都除湿玩了才能运作
                            //human.setPlayerStatus(status);
                            CoreRegistry.put(Player.class, player);

                            LivingThingManager livingThingManager =new LivingThingManager();
                            CoreRegistry.put(LivingThingManager.class,livingThingManager);

                            livingThingManager.setPlayer(player);
                            //livingThingManager.add(livingThing);
                            state.player = player;
                            BlockEngine.engine.changeState(state);
                            /*int threadId = (int)(Math.random()*100000);

                            GameCallBackTask task = new GameCallBackTask() {
                                @Override
                                public void run() {

                                }
                            };
                            Client.taskMap.put(threadId, task);

                            GetCmd getCmd =new GetCmd(threadId);*/



                        }

                    }else{
                        return;
                    }
               }
           };
           int threadId = (int)(Math.random()*100000);
           Client.SyncTaskMap.put(threadId, task);

           CoreRegistry.get(Client.class).send(new LoginCmd(userName.getText(),pwd.getText(),threadId));

         /*  synchronized (task) {
               try {
                   task.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           LogUtil.println("接收到回执");
           task.run();*/
       }
       else {
           NuiManager nuiManager = CoreRegistry.get(NuiManager.class);
           nuiManager.startGame();
       }
      /* GUI gui = getGUI();
       if(gui != null) {
           // step 1: disable all controls
           efName.setEnabled(false);
           efPassword.setEnabled(false);
           btnLogin.setEnabled(false);

           // step 2: get name & password from UI
           String name = efName.getText();
           String pasword = efPassword.getText();
           System.out.println("Name: " + name + " with a " + pasword.length() + " character password");

           Constants.userName = name;
           //Client client =CoreRegistry.get(Client.class);
           //client.send("newborn:1,1,1,1,1");
           //UserService action =new UserAction();
           // action.
           // step 3: start a timer to simulate the process of talking to a remote server
           Timer timer = gui.createTimer();
           timer.setCallback(new Runnable() {
               public void run() {
                   // once the timer fired re-enable the controls and clear the password
                   efName.setEnabled(true);
                   efPassword.setEnabled(true);
                   efPassword.setText("");
                   efPassword.requestKeyboardFocus();
                   btnLogin.setEnabled(true);
               }
           });
           timer.setDelay(2500);
           timer.start();

            *//* NOTE: in a real app you would need to keep a reference to the timer object
             * to cancel it if the user closes the dialog which uses the timer.
             * @see Widget#beforeRemoveFromGUI(de.matthiasmann.twl.GUI)
             *//*
       }*/
   }
    @Override
    public boolean isHibernationAllowed() {
        return false;
    }
}
