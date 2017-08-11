package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.util.StringUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by dozen.zhang on 2017/8/4.
 */
public class MainFrame extends Application {
    @Override
    public void start(final  Stage primaryStage) throws Exception {
        FlowPane root = new FlowPane();

        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(15,15,15,15));

        // Button 1
        Button addBlockBtn= new Button("在原点添加方块");

        root.getChildren().add(addBlockBtn);


        // Button 2
        Button button2 = new Button("删除选中");

        root.getChildren().add(button2);


        Button copyButton = new Button("复制选中");

        root.getChildren().add(copyButton);


   ;


        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              GamingState.editEngine.copySelect();
            }
        });

        // TextField
        final TextField textField = new TextField("Text Field");
        textField.setPrefWidth(110);


        root.getChildren().add(textField);

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
        addBlockBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();


                GamingState.editEngine.addBlock();
            }
        });
        Button setColorBtn = new Button("设置颜色");

        root.getChildren().add(setColorBtn);
        setColorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.setColor((float)color.getRed(),(float)color.getGreen(),(float)color.getBlue());
            }
        });
                button2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        GamingState.editEngine.deleteSelect();
                    }
                }

        );

        Button saveBtn = new Button("保存");

        root.getChildren().add(saveBtn);
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.saveWork();

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

        Button readsaveBtn = new Button("读取");

        root.getChildren().add(readsaveBtn);
        readsaveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.reloadWork();
            }
        });

        Button shootBtn = new Button("喷射");

        root.getChildren().add(shootBtn);
        shootBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Switcher.mouseState = Switcher.shootMode;
            }
        });

        Button boxSelectBtn = new Button("框体选择");

        root.getChildren().add(boxSelectBtn);
        boxSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.boxSelectMode;
            }
        });
        Button singleSelectBtn = new Button("单个选择");

        root.getChildren().add(singleSelectBtn);
        singleSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.singleSelectMode;
            }
        });

        Button seperate = new Button("打散");

        root.getChildren().add(seperate);
        seperate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.seperateSelect();

            }
        });


        Button brush = new Button("涂色");

        root.getChildren().add(brush);
        brush.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Switcher.mouseState = Switcher.brushMode;


            }
        });


        Button componentSave = new Button("保存为组件");

        root.getChildren().add(componentSave);
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

        /*Button rebuildBtn = new Button("重新组合");

        root.getChildren().add(rebuildBtn);
        rebuildBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.rebuildSelect();
                Switcher.mouseState = Switcher.selectMode;
            }
        });*/




        final FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("加载组件");
        final Button openButton = new Button("加载组件");

        root.getChildren().add(openButton);
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    GamingState.editEngine.readComponentFromFile(file);
                }
            }
        });



        final Button buildComponentButton = new Button("合成组件");

        root.getChildren().add(buildComponentButton);
        buildComponentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                    GamingState.editEngine.buildComponent();

            }
        });

   /*     fileChooser.showOpenDialog(stage);*/
        // xoffset
        final Label xoffsetLabel = new Label("x offset");
        root.getChildren().add(xoffsetLabel);
        final TextField textxoffset = new TextField("0");
        textxoffset.setPrefWidth(110);
        root.getChildren().add(textxoffset);

        //yoffset
        final Label yoffsetLabel = new Label("y offset");
        root.getChildren().add(yoffsetLabel);
        final TextField textyoffset = new TextField("0");
        textyoffset.setPrefWidth(110);
        root.getChildren().add(textyoffset);

        //zoffset
        final Label zoffsetLabel = new Label("z offset");
        root.getChildren().add(zoffsetLabel);
        final TextField textzoffset = new TextField("0");
        textzoffset.setPrefWidth(110);
        root.getChildren().add(textzoffset);



        // xzoom
        final Label xzoomLabel = new Label("xzoom");
        root.getChildren().add(xzoomLabel);
        final TextField textxzoom = new TextField("1");
        textxzoom.setPrefWidth(110);
        root.getChildren().add(textxzoom);

        //yzoom
        final Label yzoomLabel = new Label("yzoom");
        root.getChildren().add(yzoomLabel);
        final TextField textyzoom = new TextField("1");
        textyzoom.setPrefWidth(110);
        root.getChildren().add(textyzoom);

        //zzoom
        final Label zzoomLable = new Label("z zoom");
        root.getChildren().add(zzoomLable);
        final TextField textzzoom = new TextField("1");
        textzzoom.setPrefWidth(110);
        root.getChildren().add(textzzoom);


        final Button componentAdjust = new Button("组件内部调整");

        root.getChildren().add(componentAdjust);
        componentAdjust.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.adjustComponent(
                        Float.valueOf(textxzoom.getText()),
                        Float.valueOf(textyzoom.getText()),
                        Float.valueOf(textzzoom.getText()),
                Float.valueOf( textxoffset.getText()),
                Float.valueOf(textyoffset.getText()),
                        Float.valueOf(textzoffset.getText()));

            }
        });


        final Button faceSelectBtn = new Button("面选择");

        root.getChildren().add(faceSelectBtn);
        faceSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            Switcher.mouseState = Switcher.faceSelectMode;
            }
        });


        final Button animationEditBtn = new Button("动画编辑");


        final ScrollPane sp = new ScrollPane();

      final   VBox box = new VBox();
        box.getChildren().addAll(sp);
        VBox.setVgrow(sp, Priority.ALWAYS);

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
            }
        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}
