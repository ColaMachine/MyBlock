package com.dozenx.game.engine.fx;

import java.io.File;
import java.util.List;

import com.dozenx.util.FileUtil;

import cola.machine.game.myblocks.engine.paths.PathManager;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class CompListPanel extends Tab {

    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }



    public void addCreatePanel() {



        this.setText("组件列表");


        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        List<File> list = FileUtil.listFile(PathManager.getInstance().getHomePath().resolve("save/component").toFile());
        final VBox box = new VBox();
        selectGrid.add(box, 0, 0);
        Button refreshBtn = new Button("刷新");

        box.getChildren().add(refreshBtn);

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




        this.setContent(selectGrid);

    }

    public CompListPanel(Stage primaryStage) {



        addCreatePanel();


    }

}
