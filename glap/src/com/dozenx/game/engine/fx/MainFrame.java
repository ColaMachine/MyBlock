package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/8/4.
 */
public class MainFrame extends Application {
    Stage primaryStage;

    @Override
    public void start(final  Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FlowPane root = new FlowPane();
        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(15,15,15,15));
        root.getChildren().add(addSelectPanel());
        root.getChildren().add(addCreatePanel());
        root.getChildren().add(addFilePanel());
        root.getChildren().add(addComponentPanel());
        root.getChildren().add(addComponentListPanel());


        // CheckBox
        CheckBox checkBox = new CheckBox("Check Box");

        root.getChildren().add(checkBox);
        final ToggleGroup group = new ToggleGroup();
        // RadioButton
        RadioButton radioButton = new RadioButton("size");
        root.getChildren().add(radioButton);
        radioButton.setSelected(true);
        radioButton.setToggleGroup(group);
        radioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.size =true;
            }
        });
        RadioButton radioButton2 = new RadioButton("position");
        root.getChildren().add(radioButton2);
        radioButton2.setToggleGroup(group);
        radioButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.size =false;
            }
        });
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.CORAL);
        root.getChildren().add(colorPicker);
        Scene scene = new Scene(root, 550, 250);

        primaryStage.setTitle("FlowPane Layout Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button setColorBtn = new Button("设置颜色");

        root.getChildren().add(setColorBtn);
        setColorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.setColor((float)color.getRed(),(float)color.getGreen(),(float)color.getBlue());
            }
        });



        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.red= (float)color.getRed();
                GamingState.editEngine.green= (float)color.getGreen();
                GamingState.editEngine.blue= (float)color.getBlue();
            }
        });





   /*     fileChooser.showOpenDialog(stage);*/
        // xoffset






        final Button animationEditBtn = new Button("动画编辑");


        final ScrollPane sp = new ScrollPane();

      final   VBox box = new VBox();
        box.getChildren().addAll(sp);
        box.setVgrow(sp, Priority.ALWAYS);


        root.getChildren().add(box);

        root.getChildren().add(animationEditBtn);
        animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int size =GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();
                for(int i=0;i<size;i++){
                    Button animationEditBtn = new Button("帧"+i);
                    box.getChildren().add(animationEditBtn);

                }

            }
        });
        final Button addAnimationFrameBtn = new Button("增加动画帧");

        root.getChildren().add(addAnimationFrameBtn);
        addAnimationFrameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int size =GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                GamingState.editEngine.currentColorAddGroupAnimationFrame();

                Button animationEditBtn = new Button("帧"+size);
                box.getChildren().add(animationEditBtn);


                animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int size =GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                        GamingState.editEngine.currentColorAddGroupAnimationFrame();

                        Button animationEditBtn = new Button("帧"+size);
                        box.getChildren().add(animationEditBtn);



                    }
                });
            }
        });



    }
    public TitledPane addSelectPanel(){

        TitledPane selectGridTitlePane = new TitledPane();

        selectGridTitlePane.setText("选择");



        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(4);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        final Button faceSelectBtn = new Button("面选择");


        faceSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Switcher.mouseState = Switcher.faceSelectMode;
            }
        });
        selectGrid.add(faceSelectBtn, 0, 0);


        Button boxSelectBtn = new Button("框体选择");


        boxSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.boxSelectMode;
            }
        });
        Button singleSelectBtn = new Button("单个选择");


        singleSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.singleSelectMode;
            }
        });

        selectGrid.add(boxSelectBtn, 0, 1);
        selectGrid.add(singleSelectBtn, 0, 2);


        selectGridTitlePane.setContent(selectGrid);
        return selectGridTitlePane;
    }



    public TitledPane addCreatePanel(){

        TitledPane titlePane = new TitledPane();

        titlePane.setText("方块");



        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));





        // Button 1
        Button addBlockBtn= new Button("在原点添加方块");




        // Button 2
        Button deleteBtn = new Button("删除选中");




        Button copyButton = new Button("复制选中");




        addBlockBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



                GamingState.editEngine.addBlock();
            }
        });
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {

                    GamingState.editEngine.deleteSelect();
                }
            }

        );



        Button shootBtn = new Button("喷射");


        shootBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.shootMode;
            }
        });


        Button brushBtn = new Button("涂色");


        brushBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Switcher.mouseState = Switcher.brushMode;


            }
        });;


        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.copySelect();
            }
        });


        selectGrid.add(addBlockBtn, 0, 0);
        selectGrid.add(deleteBtn, 0, 1);
        selectGrid.add(copyButton, 0, 2);
        selectGrid.add(shootBtn, 0, 3);
        selectGrid.add(brushBtn, 0,4);

        Button seperate = new Button("打散");


        seperate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.seperateSelect();

            }
        });
        selectGrid.add(seperate, 0, 4);
        titlePane.setContent(selectGrid);
        return titlePane;
    }



    public TitledPane addFilePanel(){

        TitledPane titlePane = new TitledPane();
        titlePane.setText("文件");
        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));
        Button saveBtn = new Button("保存");

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.saveWork();

            }
        });
        Button readsaveBtn = new Button("读取");
        // TextField
        final TextField textField = new TextField("Text Field");
        textField.setPrefWidth(110);




        readsaveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.reloadWork();
            }
        });








        Button componentSave = new Button("保存为组件");


        componentSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //获取名称
                String text = textField.getText();
                if(StringUtil.isNotEmpty(text)) {
                    GamingState.editEngine.saveSelectAsComponent(text);
                }
            }
        });




        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("加载组件");
        final Button openButton = new Button("加载组件");


        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    GamingState.editEngine.readComponentFromFile(file);
                }
            }
        });


        Button componentSave2 = new Button("保存为组件2");


        componentSave2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //获取名称
                String text = textField.getText();
                if(StringUtil.isNotEmpty(text)) {
                    GamingState.editEngine.saveSelectAsColorGroup(text);
                }
            }
        });

        selectGrid.add(saveBtn, 0, 0);
        selectGrid.add(readsaveBtn, 0, 1);
        selectGrid.add(textField, 0, 2);
        selectGrid.add(componentSave, 0, 3);
        selectGrid.add(openButton, 0, 4);
        selectGrid.add(componentSave2, 0, 4);
        titlePane.setContent(selectGrid);
        return titlePane;
    }
    public TitledPane addComponentPanel(){

        TitledPane titlePane = new TitledPane();
        titlePane.setText("组件");
        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));
        final Label xoffsetLabel = new Label("x offset");

        final TextField xoffsetInput = new TextField("0");
        xoffsetInput.setPrefWidth(110);


        //yoffset
        final Label yoffsetLabel = new Label("y offset");

        final TextField yoffsetInput = new TextField("0");
        yoffsetInput.setPrefWidth(110);


        //zoffset
        final Label zoffsetLabel = new Label("z offset");

        final TextField zoffsetInput = new TextField("0");
        zoffsetInput.setPrefWidth(110);




        // xzoom
        final Label xzoomLabel = new Label("xzoom");

        final TextField xzoomInput = new TextField("1");
        xzoomInput.setPrefWidth(110);


        //yzoom
        final Label yzoomLabel = new Label("yzoom");

        final TextField yzoomInput = new TextField("1");
        yzoomInput.setPrefWidth(110);


        //zzoom
        final Label zzoomLabel = new Label("z zoom");

        final TextField zzoomInput = new TextField("1");
        zzoomInput.setPrefWidth(110);



        final Button componentAdjust = new Button("组件内部调整");


        componentAdjust.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.adjustComponent(
                        Float.valueOf(xzoomInput.getText()),
                        Float.valueOf(yzoomInput.getText()),
                        Float.valueOf(zzoomInput.getText()),
                        Float.valueOf( xoffsetInput.getText()),
                        Float.valueOf(yoffsetInput.getText()),
                        Float.valueOf(zoffsetInput.getText()));

            }
        });
        final Button buildComponentButton = new Button("合成组件");


        buildComponentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.buildComponent();

            }
        });
        selectGrid.add(xoffsetLabel, 0, 0); selectGrid.add(xoffsetInput, 1, 0);
        selectGrid.add(yoffsetLabel, 0, 1); selectGrid.add(yoffsetInput, 1, 1);
        selectGrid.add(zoffsetLabel, 0, 2); selectGrid.add(zoffsetInput, 1, 2);

        selectGrid.add(xzoomLabel, 0, 3); selectGrid.add(xzoomInput, 1, 3);
        selectGrid.add(yzoomLabel, 0, 4); selectGrid.add(yzoomInput, 1, 4);
        selectGrid.add(zzoomLabel, 0, 5); selectGrid.add(zzoomInput, 1, 5);

        selectGrid.add(componentAdjust, 0, 6);  selectGrid.add(buildComponentButton, 1, 6);

        titlePane.setContent(selectGrid);
        return titlePane;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public TitledPane addComponentListPanel(){

        TitledPane titlePane = new TitledPane();
        titlePane.setText("组件列表");
        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        List<File> list = FileUtil.listFile(PathManager.getInstance().getHomePath().resolve("save/component").toFile());
        final   VBox box = new VBox();
        selectGrid.add(box,0,0);
        for(final File file : list){
            Button component = new Button();
            component.setText(file.getName());
            final String name = file.getName();
            box.getChildren().add(component);
            component.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {


                    GamingState.editEngine.readAndLoadColorGroupFromFile(file);





                }
            });
        }











        titlePane.setContent(selectGrid);
        return titlePane;
    }
}
