package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.switcher.Switcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class CompPanel extends Tab {

    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }



    public void addCreatePanel() {



        this.setText("组件");


        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));
       
        
        
      

        //xoffset
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
                        Float.valueOf(xoffsetInput.getText()),
                        Float.valueOf(yoffsetInput.getText()),
                        Float.valueOf(zoffsetInput.getText()));

            }
        });
        final Button buildAnimationComponentButton = new Button("合成动画组件");


        buildAnimationComponentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.buildAnimationComponent();

            }
        });


        final Button buildComponentButton = new Button("合成普通组件");


        buildComponentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.buildComponent();

            }
        });

        final Button editComponentButton = new Button("组件编辑");


        editComponentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Switcher.isEditComponent = !Switcher.isEditComponent;
                if (Switcher.isEditComponent) {
                    //Border border = new Border();
                    //editComponentButton.setBorder();
                    GamingState.editEngine.enterComponentEdit();
                    editComponentButton.setText("退出组件编辑模式");
                }else{
                    GamingState.editEngine.currentChoosedGroupForEdit=null;
                    editComponentButton.setText("进入组件编辑模式");
                }
                //s GamingState.editEngine.buildComponent();

            }
        });

        selectGrid.add(xoffsetLabel, 0, 0);
        selectGrid.add(xoffsetInput, 1, 0);
        selectGrid.add(yoffsetLabel, 0, 1);
        selectGrid.add(yoffsetInput, 1, 1);
        selectGrid.add(zoffsetLabel, 0, 2);
        selectGrid.add(zoffsetInput, 1, 2);

        selectGrid.add(xzoomLabel, 0, 3);
        selectGrid.add(xzoomInput, 1, 3);
        selectGrid.add(yzoomLabel, 0, 4);
        selectGrid.add(yzoomInput, 1, 4);
        selectGrid.add(zzoomLabel, 0, 5);
        selectGrid.add(zzoomInput, 1, 5);

        selectGrid.add(componentAdjust, 0, 6);
        selectGrid.add(buildComponentButton, 1, 6);
        selectGrid.add(buildAnimationComponentButton, 2, 6);
        selectGrid.add(editComponentButton, 0, 7);

       
        
        
        
      
        this.setContent(selectGrid);

    }

    public CompPanel(Stage primaryStage) {



        addCreatePanel();


    }

}
