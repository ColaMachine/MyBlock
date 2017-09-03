package com.dozenx.game.engine.fx;

import java.io.File;
import java.util.List;

import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.ImageBlock;
import cola.machine.game.myblocks.model.RotateColorBlock2;
import cola.machine.game.myblocks.switcher.Switcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class FilePanel extends Tab {

    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }



    public void addCreatePanel() {



        this.setText("组件列表");


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
        Label compIdLabel = new Label("物品id");
        final TextField compIdText = new TextField("代保存组件名称");
        Label compNameLabel = new Label("物品名称");
        final TextField compNameText = new TextField("代保存组件名称");
        compNameText.setPrefWidth(110);


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
                String text = compNameText.getText();
                if (StringUtil.isNotEmpty(text)) {
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


        final CheckBox cb1 = new CheckBox();

        cb1.setText("是否怪物");
       
        Label label  =new Label("脚本:");
        final TextArea scriptTxt =new TextArea();

        componentSave2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //获取名称
                String text = compNameText.getText();
                String id = compIdText.getText();
                if (StringUtil.isNotEmpty(text)) {
                    String script = scriptTxt.getText();
                    GamingState.editEngine.saveSelectAsColorGroup(Integer.valueOf(id),text, cb1.isSelected(),script);
                }
            }
        });
        

        selectGrid.add(saveBtn, 0, 0);
        selectGrid.add(readsaveBtn, 0, 1);
        selectGrid.add(compIdLabel, 0, 2);selectGrid.add(compIdText, 1, 2);
        selectGrid.add(compNameLabel, 0, 3);selectGrid.add(compNameText, 1, 3);
        selectGrid.add(componentSave, 0, 4);
        selectGrid.add(openButton, 0, 5);
        selectGrid.add(componentSave2, 0, 6);
   
        selectGrid.add(cb1, 0, 7);
        selectGrid.add(label, 0, 8);
        selectGrid.add(scriptTxt, 1, 8);
        
        
      
        this.setContent(selectGrid);

    }
    Stage primaryStage;
    public FilePanel(Stage primaryStage) {

        this.primaryStage = primaryStage;

        addCreatePanel();


    }

}
