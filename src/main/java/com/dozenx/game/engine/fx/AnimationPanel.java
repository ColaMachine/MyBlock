package com.dozenx.game.engine.fx;

import java.io.File;
import java.util.List;

import com.dozenx.game.engine.edit.view.ColorGroup;
import com.dozenx.game.engine.item.action.ItemManager;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.model.BaseBlock;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.ImageBlock;
import cola.machine.game.myblocks.model.RotateColorBlock2;
import cola.machine.game.myblocks.switcher.Switcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class AnimationPanel extends Tab {

    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }

    final ListView<String> frameList = new ListView<String>();
    ObservableList<String> frameItems = FXCollections.observableArrayList();
    
    final ListView<String> animationNameList = new ListView<String>();
    ObservableList<String> animationNameItems = FXCollections.observableArrayList();

    public void addCreatePanel() {



        this.setText("动画编辑列表");


        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        final Button animationEditBtn = new Button("动画编辑");


        //final ScrollPane sp = new ScrollPane();
        
        
        frameList.setItems(frameItems);//ba
       
        
        //final VBox box = new VBox();
        //box.getChildren().addAll(sp);
        //box.setVgrow(sp, Priority.ALWAYS);

        frameList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + frameList.getSelectionModel().getSelectedIndex());
                if(frameList.getSelectionModel().getSelectedIndex()<0){
                    return;
                }
                GamingState.editEngine.animationFrameShowNum(frameList.getSelectionModel().getSelectedIndex());
            }
        });
        
       
        final Button addAnimationFrameBtn = new Button("增加动画帧");


        addAnimationFrameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final int size = GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                GamingState.editEngine.currentColorAddGroupAnimationFrame();
                frameItems.add("帧" + frameItems.size());
/*
                Button animationEditBtn = new Button("帧" + size);
                box.getChildren().add(animationEditBtn);


                animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //让现实当前帧
                        // Switcher.currentFrameNum = size;
                        GamingState.editEngine.animationFrameShowNum(size);


                    }
                });*/
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
                refreshFramesAndNames();
            }
        });
        selectGrid.add(saveToCurFrameBtn, 0, 0);
        selectGrid.add(addAnimationFrameBtn, 0, 1);
        selectGrid.add(animationEditBtn, 0, 2);
        selectGrid.add(playBtn, 0, 3);
        selectGrid.add(frameList, 0, 4);
        selectGrid.add(deleteBtn, 4, 0);

        Label animationMaplabel =new Label("动画名称");
        TextField animationMapTextField =new TextField("");
      //创建列表
        final Button addAnimationMapBtn = new Button("增加动画名称");
        addAnimationMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               String name = animationMapTextField.getText();
               if(StringUtil.isNotEmpty(name)){
                   GamingState.editEngine.addAnimationMap(name);
                   refreshFramesAndNames();
               }
            }
        });
        selectGrid.add(animationMaplabel, 1, 0);
        selectGrid.add(animationMapTextField, 2, 0);
        selectGrid.add(addAnimationMapBtn, 3, 0);
        
        
      
        
       
        animationNameList.setItems(animationNameItems);//ba
        selectGrid.add(animationNameList, 3, 4);
     
        //final VBox box = new VBox();
        //box.getChildren().addAll(sp);
        //box.setVgrow(sp, Priority.ALWAYS);

        animationNameList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //点了这个按钮的作用就是把当前的动画展现出来
            @Override
            public void handle(MouseEvent event) {
                if(animationNameList.getSelectionModel().getSelectedIndex()<0){
                    return;
                }
                GamingState.editEngine.animationNameShow(animationNameList.getSelectionModel().getSelectedItem());
                refreshFramesAndNames();
            }
        });

        animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //先清除原来的帧 重新同步过
              
               /* for (int i = frameItems.size() - 1; i >= 0; i--) {
                    frameItems.remove(i);
                }*/
                refreshFramesAndNames();

            }
        });
        
      
        
      
        this.setContent(selectGrid);

    }
    Stage primaryStage;
    public AnimationPanel(Stage primaryStage) {

        this.primaryStage = primaryStage;

        addCreatePanel();


    }
    
    public void refreshFramesAndNames(){
        frameItems.clear();
        animationNameItems.clear();
        int size = GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();
        for (int i = 0; i < size; i++) {
            
            frameItems.add("帧" + i);
            /*Button animationEditBtn = new Button("帧" + i);
            box.getChildren().add(animationEditBtn);
            final int nowIndex = i;
            animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //让现实当前帧
                    // Switcher.currentFrameNum = size;
                    GamingState.editEngine.animationFrameShowNum(nowIndex);


                }
            });*/
        }
        
        List<String> names = GamingState.editEngine.getAnimationName();
        animationNameItems.clear();
        for(String name:names){
            animationNameItems.add(name);
        }
    }

}
