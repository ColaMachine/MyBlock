package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/8/4.
 */
public class MainFrame extends Application {
    public static final String defaultURL = "http://www.baidu.com";
    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    TabPane root;
    @Override
    public void start(final Stage primaryStage) throws Exception {
        root = new TabPane();
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        this.primaryStage = primaryStage;

       /* FlowPane root1
                = new FlowPane();


        root1.getChildren().add(sp);*/
        ScrollPane sp = new ScrollPane();

      /*  sp.setFitToHeight(true);
        sp.setFitToWidth(true);
*/



        Scene scene = new Scene(sp, 800, 800);

        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                System.out.println("Window Size Change:" + t.toString() + "," + t1.toString());
              /*  sp.setMinWidth(t.doubleValue());sp
                sp.setMinHeight(t1.doubleValue());*/
            }
        });
        sp.setContent(root);
     /*   root.setHgap(10);
        root.setVgap(20);*/

        root.setPadding(new Insets(15, 15, 15, 15));


        add(addSelectPanel());
        add(new BlockPanel(primaryStage));
        add(new FilePanel(primaryStage));
      add(new CompPanel(primaryStage));
        add(new CompListPanel(primaryStage));
       add(new AnimationPanel(primaryStage));
        add(new ImagePanel(primaryStage));
       add(new ItemPanel(primaryStage));


   /*     root.getChildren().add(addSelectPanel());
        root.getChildren().add(new BlockPanel(primaryStage));
        root.getChildren().add(addFilePanel());
        root.getChildren().add(addComponentPanel());
        root.getChildren().add(addComponentListPanel());
        root.getChildren().add(addAnimationEditPanel());
        root.getChildren().add(new ImagePanel(primaryStage));
        root.getChildren().add(new ItemPanel(primaryStage));*/
    /*   */


        primaryStage.setTitle("FlowPane Layout Demo");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
    public void add(Tab tab){
        root.getTabs().add(tab);
    }
    public Tab addSelectPanel() {

        Tab selectGridTitlePane = new Tab();

        selectGridTitlePane.setText("选择");


        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(4);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));






        Button backGameBtn = new Button("保存到场景");

        backGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //遍历所有的colorblocklist

                GamingState.editEngine.saveToGame();
            }
        });
        selectGrid.add(backGameBtn, 0, 3);







        final Button switchBtn = new Button("切换场景");

        switchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //遍历所有的colorblocklist
                Switcher.edit = !Switcher.edit;
                if(Switcher.edit ){
                    switchBtn.setText("切换场景"+"编辑中");
                }else{
                    switchBtn.setText("切换场景"+"游戏中");
                }

            }
        });
        selectGrid.add(switchBtn, 0, 4);


        Button readBlockFromGameCurrentChunkBtn = new Button("从当前场景读取方块");

        readBlockFromGameCurrentChunkBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //遍历所有的colorblocklist

                GamingState.editEngine.readBlocksFromCurrentChunk();
            }
        });
        selectGrid.add(readBlockFromGameCurrentChunkBtn, 0, 5);

        //==============从服务器同步次方块结构
        Button synChunkFromServerBtn = new Button("从服务器同步此区块");

        synChunkFromServerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //遍历所有的colorblocklist

                GamingState.editEngine.synChunkFromServer();
            }
        });
        selectGrid.add(synChunkFromServerBtn, 0, 6);


        //==============从客户端向服务器同步次方块结构
        Button synChunkFromClientBtn = new Button("向服务器同步此区块");

        synChunkFromClientBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //遍历所有的colorblocklist

                GamingState.editEngine.synChunkFromClient();
            }
        });
        selectGrid.add(synChunkFromClientBtn, 0, 7);

        selectGridTitlePane.setContent(selectGrid);
        return selectGridTitlePane;
    }


//    public Tab addFilePanel() {
//
//        Tab titlePane = new Tab();
//        titlePane.setText("文件");
//        GridPane selectGrid = new GridPane();
//        selectGrid.setVgap(5);
//        selectGrid.setPadding(new Insets(5, 5, 5, 5));
//        Button saveBtn = new Button("保存");
//
//        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                GamingState.editEngine.saveWork();
//
//            }
//        });
//        Button readsaveBtn = new Button("读取");
//        // TextField
//        Label compIdLabel = new Label("物品id");
//        final TextField compIdText = new TextField("代保存组件名称");
//        Label compNameLabel = new Label("物品名称");
//        final TextField compNameText = new TextField("代保存组件名称");
//        compNameText.setPrefWidth(110);
//
//
//        readsaveBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                GamingState.editEngine.reloadWork();
//            }
//        });
//
//
//        Button componentSave = new Button("保存为组件");
//
//
//        componentSave.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                //获取名称
//                String text = compNameText.getText();
//                if (StringUtil.isNotEmpty(text)) {
//                    GamingState.editEngine.saveSelectAsComponent(text);
//                }
//            }
//        });
//
//
//        final FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("加载组件");
//        final Button openButton = new Button("加载组件");
//
//
//        openButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                File file = fileChooser.showOpenDialog(primaryStage);
//                if (file != null) {
//                    GamingState.editEngine.readComponentFromFile(file);
//                }
//            }
//        });
//
//
//        Button componentSave2 = new Button("保存为组件2");
//
//
//        CheckBox cb1 = new CheckBox();
//
//        cb1.setText("是否怪物");
//        componentSave2.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                //获取名称
//                String text = compNameText.getText();
//                String id = compIdText.getText();
//                if (StringUtil.isNotEmpty(text)) {
//
//                    GamingState.editEngine.saveSelectAsColorGroup(Integer.valueOf(id),text, cb1.isSelected());
//                }
//            }
//        });
//
//
//
//
//        selectGrid.add(saveBtn, 0, 0);
//        selectGrid.add(readsaveBtn, 0, 1);
//        selectGrid.add(compIdLabel, 0, 2);selectGrid.add(compIdText, 1, 2);
//        selectGrid.add(compNameLabel, 0, 3);selectGrid.add(compNameText, 1, 3);
//        selectGrid.add(componentSave, 0, 4);
//        selectGrid.add(openButton, 0, 5);
//        selectGrid.add(componentSave2, 0, 6);
//        selectGrid.add(cb1, 0, 7);
//        titlePane.setContent(selectGrid);
//        return titlePane;
//    }

//    public Tab addComponentPanel() {
//
//        Tab titlePane = new Tab();
//        titlePane.setText("组件");
//        GridPane selectGrid = new GridPane();
//        selectGrid.setVgap(5);
//        selectGrid.setPadding(new Insets(5, 5, 5, 5));
//
//        //xoffset
//        final Label xoffsetLabel = new Label("x offset");
//
//        final TextField xoffsetInput = new TextField("0");
//        xoffsetInput.setPrefWidth(110);
//
//
//        //yoffset
//        final Label yoffsetLabel = new Label("y offset");
//
//        final TextField yoffsetInput = new TextField("0");
//        yoffsetInput.setPrefWidth(110);
//
//
//        //zoffset
//        final Label zoffsetLabel = new Label("z offset");
//
//        final TextField zoffsetInput = new TextField("0");
//        zoffsetInput.setPrefWidth(110);
//
//
//        // xzoom
//        final Label xzoomLabel = new Label("xzoom");
//
//        final TextField xzoomInput = new TextField("1");
//        xzoomInput.setPrefWidth(110);
//
//
//        //yzoom
//        final Label yzoomLabel = new Label("yzoom");
//
//        final TextField yzoomInput = new TextField("1");
//        yzoomInput.setPrefWidth(110);
//
//
//        //zzoom
//        final Label zzoomLabel = new Label("z zoom");
//
//        final TextField zzoomInput = new TextField("1");
//        zzoomInput.setPrefWidth(110);
//
//
//        final Button componentAdjust = new Button("组件内部调整");
//
//
//        componentAdjust.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                GamingState.editEngine.adjustComponent(
//                        Float.valueOf(xzoomInput.getText()),
//                        Float.valueOf(yzoomInput.getText()),
//                        Float.valueOf(zzoomInput.getText()),
//                        Float.valueOf(xoffsetInput.getText()),
//                        Float.valueOf(yoffsetInput.getText()),
//                        Float.valueOf(zoffsetInput.getText()));
//
//            }
//        });
//        final Button buildComponentButton = new Button("合成组件");
//
//
//        buildComponentButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                GamingState.editEngine.buildComponent();
//
//            }
//        });
//
//        final Button editComponentButton = new Button("组件编辑");
//
//
//        editComponentButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                Switcher.isEditComponent = !Switcher.isEditComponent;
//                if (Switcher.isEditComponent) {
//                    //Border border = new Border();
//                    //editComponentButton.setBorder();
//                    GamingState.editEngine.enterComponentEdit();
//                }
//                //s GamingState.editEngine.buildComponent();
//
//            }
//        });
//
//        selectGrid.add(xoffsetLabel, 0, 0);
//        selectGrid.add(xoffsetInput, 1, 0);
//        selectGrid.add(yoffsetLabel, 0, 1);
//        selectGrid.add(yoffsetInput, 1, 1);
//        selectGrid.add(zoffsetLabel, 0, 2);
//        selectGrid.add(zoffsetInput, 1, 2);
//
//        selectGrid.add(xzoomLabel, 0, 3);
//        selectGrid.add(xzoomInput, 1, 3);
//        selectGrid.add(yzoomLabel, 0, 4);
//        selectGrid.add(yzoomInput, 1, 4);
//        selectGrid.add(zzoomLabel, 0, 5);
//        selectGrid.add(zzoomInput, 1, 5);
//
//        selectGrid.add(componentAdjust, 0, 6);
//        selectGrid.add(buildComponentButton, 1, 6);
//        selectGrid.add(editComponentButton, 0, 7);
//
//        titlePane.setContent(selectGrid);
//        return titlePane;
//    }

//    public Tab addComponentListPanel() {
//
//        Tab titlePane = new Tab();
//        titlePane.setText("组件列表");
//        GridPane selectGrid = new GridPane();
//        selectGrid.setVgap(5);
//        selectGrid.setPadding(new Insets(5, 5, 5, 5));
//
//
//        List<File> list = FileUtil.listFile(PathManager.getInstance().getHomePath().resolve("save/component").toFile());
//        final VBox box = new VBox();
//        selectGrid.add(box, 0, 0);
//        Button refreshBtn = new Button("刷新");
//
//        box.getChildren().add(refreshBtn);
//
//        for (File file : list) {
//            Button component = new Button();
//            component.setText(file.getName());
//            final String name = file.getName();
//            GamingState.editEngine.readAndLoadColorGroupFromFile(file);
//            box.getChildren().add(component);
//            component.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    GamingState.editEngine.changeCurrentComponent(name);
//                }
//            });
//        }
//
//        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                //获取名称
//                for (int i = box.getChildren().size() - 1; i >= 1; i--) {
//                    box.getChildren().remove(i);
//                }
//
//                List<File> list = FileUtil.listFile(PathManager.getInstance().getHomePath().resolve("save/component").toFile());
//
//                for (File file : list) {
//                    Button component = new Button();
//                    component.setText(file.getName());
//                    final String name = file.getName();
//                    GamingState.editEngine.readAndLoadColorGroupFromFile(file);
//                    box.getChildren().add(component);
//                    component.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event) {
//
//
//                            GamingState.editEngine.changeCurrentComponent(name);
//
//
//                        }
//                    });
//                }
//
//            }
//        });
//
//
//        titlePane.setContent(selectGrid);
//        return titlePane;
//    }

    public Tab addAnimationEditPanel() {

        Tab titlePane = new Tab();
        titlePane.setText("动画");
        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        final Button animationEditBtn = new Button("动画编辑");


        final ScrollPane sp = new ScrollPane();

        final VBox box = new VBox();
        box.getChildren().addAll(sp);
        box.setVgrow(sp, Priority.ALWAYS);


        animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //先清除原来的帧 重新同步过

                for (int i = box.getChildren().size() - 1; i >= 0; i--) {
                    box.getChildren().remove(i);
                }
                int size = GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();
                for (int i = 0; i < size; i++) {
                    Button animationEditBtn = new Button("帧" + i);
                    box.getChildren().add(animationEditBtn);
                    final int nowIndex = i;
                    animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //让现实当前帧
                            // Switcher.currentFrameNum = size;
                            GamingState.editEngine.animationFrameShowNum(nowIndex);


                        }
                    });
                }

            }
        });
        final Button addAnimationFrameBtn = new Button("增加动画帧");


        addAnimationFrameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final int size = GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                GamingState.editEngine.currentColorAddGroupAnimationFrame();

                Button animationEditBtn = new Button("帧" + size);
                box.getChildren().add(animationEditBtn);


                animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //让现实当前帧
                        // Switcher.currentFrameNum = size;
                        GamingState.editEngine.animationFrameShowNum(size);


                    }
                });
            }
        });
        final Button saveToCurFrameBtn = new Button("保存到当前帧");


        saveToCurFrameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final int size = GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                GamingState.editEngine.saveToCurFrame();


            }
        });

        final Button playBtn = new Button("播放");
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (GamingState.editEngine.playAnimation()) {
                    playBtn.setText("当前播放 按下停止");
                } else {
                    playBtn.setText("当前停止 按下播放");
                }
            }
        });

        final Button deleteBtn = new Button("delete");
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.deleteCurFrame();

            }
        });
        selectGrid.add(saveToCurFrameBtn, 0, 0);
        selectGrid.add(addAnimationFrameBtn, 0, 1);
        selectGrid.add(animationEditBtn, 0, 2);
        selectGrid.add(playBtn, 0, 3);
        selectGrid.add(box, 0, 4);
        selectGrid.add(deleteBtn, 0, 5);


        titlePane.setContent(selectGrid);

        return titlePane;
    }

    private void init(FlowPane root) {
        final Stage stage = primaryStage;
        // Group group=new Group();//作为根节点，也就是root
        //  primaryStage.setScene(new Scene(group));

        WebView webView = new WebView();
        final WebEngine engine = webView.getEngine();
        engine.load(defaultURL);

        final TextField textField = new TextField(defaultURL);
        /**修改输入栏的地址，也就是访问那个网站，这个地址栏显示那个网站的地址
         * locationProperty()是获得当前页面的url封装好的ReadOnlyStringProperty对象
         */
        engine.locationProperty().addListener(new ChangeListener<String>() {


            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textField.setText(newValue);
            }
        });
        /**
         * 设置标题栏为当前访问页面的标题。
         */
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    stage.setTitle(engine.getTitle());
                }
            }
        });


        /**
         * 测试能否获得javascript上面的交互内容。
         * 可以自己写一个包含window.alert("neirong")的html进行测试。
         * 返回的是neirong
         */
        engine.setOnAlert(new EventHandler<WebEvent<String>>() {

            @Override
            public void handle(WebEvent<String> event) {
                System.out.println("this is event" + event);
            }
        });

        //加载新的地址
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engine.load(textField.getText().startsWith("http://") ? textField.getText().trim() : "http://" + textField.getText().trim());
            }
        };

        textField.setOnAction(handler);

        Button okButton = new Button("go");
        okButton.setDefaultButton(true);
        okButton.setOnAction(handler);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(textField, okButton);
        HBox.setHgrow(textField, Priority.ALWAYS);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hbox, webView);
        VBox.setVgrow(webView, Priority.ALWAYS);


        root.getChildren().add(vBox);
    }

    public class JavaApplication {
        public void exit() {
            System.out.println(123);
        }
    }

}
