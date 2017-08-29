package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.game.engine.item.bean.ItemDefinition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class ItemPanel extends Tab {

    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }



    public ItemPanel(Stage primaryStage) {

        //double width, double height
        this.setContent(flowPane);





        this.setText("物品列表");


        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        //创建列表
        final ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(
        );
        list.setItems(items);//ba

        HashMap<Integer, ItemDefinition> imageMap = ItemManager.itemType2ItemDefinitionMap; //遍历所有的物品项目



        Iterator it = imageMap.keySet().iterator();
        while (it.hasNext()) {
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			request.setAttribute((String)entry.getKey(),entry.getValue());


            Integer id = (Integer) it.next();
            items.add(ItemManager.getItemDefinition(id).getName());


        }

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + list.getSelectionModel().getSelectedItem());
                GamingState.editEngine.readyShootBlock = ItemManager.getItemDefinition(list.getSelectionModel().getSelectedItem()).getShape();
            }
        });






        selectGrid.add(list,0,0);
        //=======刷新按钮

        Button freshBtn = new Button("刷新");

        freshBtn.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                Iterator it = imageMap.keySet().iterator();
                while (it.hasNext()) {
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			request.setAttribute((String)entry.getKey(),entry.getValue());


                    Integer id = (Integer) it.next();
                    items.add(ItemManager.getItemDefinition(id).getName());


                }
            }
        }); selectGrid.add(freshBtn,0,1);

        this.setContent(selectGrid);


    }

}
