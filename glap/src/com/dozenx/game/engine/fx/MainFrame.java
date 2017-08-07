package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.switcher.Switcher;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Created by dozen.zhang on 2017/8/4.
 */
public class MainFrame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane root = new FlowPane();

        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(15,15,15,15));

        // Button 1
        Button button1= new Button("在原点添加方块");

        root.getChildren().add(button1);


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
        TextField textField = new TextField("Text Field");
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
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                // Color commonColor =new Color( color.getRed() ,color.getGreen(),color.getBlue());
                ColorBlock colorBlock =new ColorBlock(0,0,0);
                colorBlock.rf =(float) color.getRed();
                colorBlock.gf =(float) color.getGreen();
                colorBlock.bf =(float) color.getBlue();
                GamingState.editEngine.colorBlockList.add(colorBlock);
                GamingState.editEngine.select(colorBlock);
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

        /*Button rebuildBtn = new Button("重新组合");

        root.getChildren().add(rebuildBtn);
        rebuildBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.rebuildSelect();
                Switcher.mouseState = Switcher.selectMode;
            }
        });*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}