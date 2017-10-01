package com.dozenx.game.engine.fx;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cola.machine.game.myblocks.engine.Constants;
import com.dozenx.game.engine.element.model.BoxModel;
import com.dozenx.game.engine.item.bean.ItemDefinition;
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
import javafx.scene.control.*;
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

        Label iconLabel = new Label("ICON");
        final TextField iconText = new TextField("icon");


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


        final CheckBox monsterCb = new CheckBox();

        monsterCb.setText("是否怪物");


        final CheckBox isPenetrateCb = new CheckBox();

        isPenetrateCb.setText("是否可以穿透");


        final CheckBox isLightCB = new CheckBox();

        isLightCB.setText("是否灯光");
        //===============

        final CheckBox isEquipCB = new CheckBox();

        isEquipCB.setText("装备");

        selectGrid.add(isEquipCB, 0, 9);

        //==============

        final CheckBox isWeaponCB = new CheckBox();

        isWeaponCB.setText("武器");

        selectGrid.add(isWeaponCB, 1, 9);

        //==============

        final CheckBox isClothCB = new CheckBox();

        isClothCB.setText("衣服");

        selectGrid.add(isClothCB, 2, 9);
        //==============

        final CheckBox hatCb = new CheckBox();

        hatCb.setText("帽子");


        selectGrid.add(hatCb,3, 9);

        //==============

        final CheckBox kuziCb = new CheckBox();

        kuziCb.setText("裤子");

        selectGrid.add(kuziCb,4, 9);

        //==============

//        TitledPane titledPane = new TitledPane();
//        titledPane.setText("\"物品属性\"");
        Label mingjieLabel =new Label("敏捷");
        final TextField mingjieText = new TextField("敏捷");
        Label zhiliLabel =new Label("智力");
        final TextField zhiliText = new TextField("智力");
        Label liliangLabel =new Label("力量");
        final TextField liliangText = new TextField("力量");
        Label tiliLabel =new Label("体力");
        final TextField tiliText = new TextField("体力");
        Label jinshenLabel =new Label("精神");
        final TextField jinshenText = new TextField("精神");

        selectGrid.add(mingjieLabel,0, 11);
        selectGrid.add(mingjieText,1, 11);

        selectGrid.add(zhiliLabel,2, 11);
        selectGrid.add(zhiliText,3, 11);

        selectGrid.add(liliangLabel,0, 12);
        selectGrid.add(liliangText,1, 12);

        selectGrid.add(tiliLabel,2, 12);
        selectGrid.add(tiliText,3, 12);

        selectGrid.add(jinshenLabel,0, 13);
        selectGrid.add(jinshenText,1, 13);


//        ItemDefinition item = new ItemDefinition();
//        item.setType(0);

       
        Label label  =new Label("脚本:");
        final TextArea scriptTxt =new TextArea();



        componentSave2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HashMap map =new HashMap<>();
                //获取名称
                String text = compNameText.getText();
                String id = compIdText.getText();
                if (StringUtil.isNotEmpty(text)) {
                    String script = scriptTxt.getText();

                    int firstType=0;
                    int secondType=0;
                    if(monsterCb.isSelected() ){
                        firstType=1;
                    }else if(isWeaponCB.isSelected() ||
                            isClothCB.isSelected() ||
                            hatCb.isSelected() ||
                            kuziCb.isSelected() ){
                        firstType = 2;
                        if(isWeaponCB.isSelected()){
                            secondType= Constants.WEAR_POSI_HAND;
                        }else if(isClothCB.isSelected()){
                            secondType=Constants.WEAR_POSI_BODY;
                        }else if(hatCb.isSelected()){
                            secondType=Constants.WEAR_POSI_HEAD;
                        }else if(kuziCb.isSelected()){
                            secondType=Constants.WEAR_POSI_LEG;

                        }

                        map.put("spirit",Integer.valueOf(jinshenText.getText()));
                        map.put("agile",Integer.valueOf(mingjieText.getText()));
                        map.put("intelli",Integer.valueOf(zhiliText.getText()));
                        map.put("strenth",Integer.valueOf(liliangText.getText()));
                        map.put("tili",Integer.valueOf(tiliText.getText()));
                    }else{
                        firstType = 0;
                    }

                    GamingState.editEngine.saveSelectAsColorGroup(Integer.valueOf(id),text, firstType,secondType,script,isPenetrateCb.isSelected(),isLightCB.isSelected(),map,iconText.getText());
                }
            }
        });
        

        selectGrid.add(saveBtn, 0, 0);
        selectGrid.add(readsaveBtn, 0, 1);
        selectGrid.add(compIdLabel, 0, 2);selectGrid.add(compIdText, 1, 2);
        selectGrid.add(iconLabel, 2, 2);selectGrid.add(iconText, 3, 2);
        selectGrid.add(compNameLabel, 0, 3);selectGrid.add(compNameText, 1, 3);
        selectGrid.add(componentSave, 0, 4);
        selectGrid.add(openButton, 0, 5);
        selectGrid.add(componentSave2, 0, 6);
   
        selectGrid.add(monsterCb, 0, 7);selectGrid.add(isPenetrateCb, 1, 7);selectGrid.add(isLightCB, 2, 7);
        selectGrid.add(label, 0, 8);

        selectGrid.add(scriptTxt, 1, 8,4,1);
        
        
      
        this.setContent(selectGrid);

    }
    Stage primaryStage;
    public FilePanel(Stage primaryStage) {

        this.primaryStage = primaryStage;

        addCreatePanel();


    }

}
