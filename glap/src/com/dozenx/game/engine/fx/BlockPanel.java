package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.ColorBlock;
import cola.machine.game.myblocks.model.ImageBlock;
import cola.machine.game.myblocks.model.RotateColorBlock2;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.switcher.Switcher;
import com.dozenx.util.FileUtil;
import glapp.GLImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class BlockPanel extends Tab {

    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }



    public void addCreatePanel() {



        this.setText("方块");


        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));
        // Button 1
        Button addBlockBtn = new Button("在原点添加方块");
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

        Button shootComponentBtn = new Button("喷射组件");
        shootComponentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.shootComponentMode;
            }
        });

        Button brushBtn = new Button("涂色");
        brushBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.brushMode;
            }
        });
        ;


        Button textureBtn = new Button("纹理");
        textureBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.mouseState = Switcher.textureMode;
            }
        });
        ;

        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.copySelect();
            }
        });










      /*  final TextField xInput = new TextField("0");
        xInput.setPrefWidth(110);

        final TextField yInput = new TextField("0");
        yInput.setPrefWidth(110);

        final TextField zInput = new TextField("0");
        zInput.setPrefWidth(110);

        final TextField widthInput = new TextField("0");
        widthInput.setPrefWidth(110);


        final TextField heightInput = new TextField("0");
        heightInput.setPrefWidth(110);


        final TextField thickInput = new TextField("0");
        thickInput.setPrefWidth(110);*/
        Button seperate = new Button("打散");

        selectGrid.add(addBlockBtn, 0, 0);
        selectGrid.add(deleteBtn, 0, 1);
        selectGrid.add(copyButton, 0, 2);
        selectGrid.add(textureBtn, 1, 2);
        selectGrid.add(shootBtn, 0, 3);
        selectGrid.add(shootComponentBtn, 1, 3);
        selectGrid.add(brushBtn, 0, 4);
        selectGrid.add(seperate, 1, 4);


        final Label xLabel = new Label("x");
        Button xMi = new Button("-");
        Button xadd = new Button("+");

        final Label yLabel = new Label("y");
        Button yMi = new Button("-");
        Button yadd = new Button("+");

        final Label zLabel = new Label("z");
        Button zMi = new Button("-");
        Button zadd = new Button("+");

        selectGrid.add(xLabel, 0, 5);
        selectGrid.add(xMi, 1, 5);
        selectGrid.add(xadd, 2, 5);
        selectGrid.add(yLabel, 0, 6);
        selectGrid.add(yMi, 1, 6);
        selectGrid.add(yadd, 2, 6);
        selectGrid.add(zLabel, 0, 7);
        selectGrid.add(zMi, 1, 7);
        selectGrid.add(zadd, 2, 7);


        Label widthLabel = new Label("宽度");
        Label heightLabel = new Label("高度");
        Label thickLabel = new Label("厚度");


        Button widthMiBtn = new Button("-");
        Button heightMiBtn = new Button("-");
        Button thickMiBtn = new Button("-");

        Button widthAddBtn = new Button("+");
        Button heightAddBtn = new Button("+");
        Button thickAddBtn = new Button("+");


        selectGrid.add(widthLabel, 0, 8);
        selectGrid.add(widthMiBtn, 1, 8);
        selectGrid.add(widthAddBtn, 2, 8);
        selectGrid.add(heightLabel, 0, 9);
        selectGrid.add(heightMiBtn, 1, 9);
        selectGrid.add(heightAddBtn, 2, 9);
        selectGrid.add(thickLabel, 0, 10);
        selectGrid.add(thickMiBtn, 1, 10);
        selectGrid.add(thickAddBtn, 2, 10);


        final Label rotatexLabel = new Label("rotatex");
        Button rotatexMi = new Button("-");
        Button rotatexadd = new Button("+");

        final Label rotateyLabel = new Label("rotatey");
        Button rotateyMi = new Button("-");
        Button rotateyadd = new Button("+");

        final Label rotatezLabel = new Label("rotatez");
        Button rotatezMi = new Button("-");
        Button rotatezadd = new Button("+");

        selectGrid.add(rotatexLabel, 0, 11);
        selectGrid.add(rotatexMi, 1, 11);
        selectGrid.add(rotatexadd, 2, 11);
        selectGrid.add(rotateyLabel, 0, 12);
        selectGrid.add(rotateyMi, 1, 12);
        selectGrid.add(rotateyadd, 2, 12);
        selectGrid.add(rotatezLabel, 0, 13);
        selectGrid.add(rotatezMi, 1, 13);
        selectGrid.add(rotatezadd, 2, 13);
        Button roateBtn = new Button("rotate");
        roateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.readyShootBlock = new RotateColorBlock2();

            }
        });

        Button colorBtn = new Button("color");
        colorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //  Switcher.BLOCKTYPE=Switcher.COLORBLOCK;
                GamingState.editEngine.readyShootBlock = new ColorBlock();

            }
        });

        Button imageBlockBtn = new Button("image");
        imageBlockBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Switcher.BLOCKTYPE=Switcher.COLORBLOCK;
                GamingState.editEngine.readyShootBlock = new ImageBlock();

            }
        });

        TextField danweiText = new TextField();
        selectGrid.add(colorBtn, 0, 14);
        selectGrid.add(roateBtn, 1, 14);
        selectGrid.add(imageBlockBtn, 2, 14);
        selectGrid.add(danweiText, 0, 15);
        danweiText.setText("1");



        final TextField centerXTxt = new TextField() ;
        final TextField centerYTxt = new TextField() ;
        final TextField centerZTxt = new TextField() ;
        selectGrid.add(centerXTxt, 0, 16);
        selectGrid.add(centerYTxt, 1, 16);
        selectGrid.add(centerZTxt, 2, 16);

        Button setCenterBtn = new Button("set center");

        setCenterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.setCenter(Float.valueOf(centerXTxt.getText()),Float.valueOf(centerYTxt.getText()),Float.valueOf(centerZTxt.getText()));

            }
        });
        selectGrid.add(setCenterBtn, 3, 16);


        rotatexMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustRotatex(-Float.valueOf(danweiText.getText()));

            }
        });
        rotatexadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustRotatex(Float.valueOf(danweiText.getText()));

            }
        });

        rotateyMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustRotateY(-Float.valueOf(danweiText.getText()));

            }
        });
        rotateyadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustRotateY(Float.valueOf(danweiText.getText()));

            }
        });


        rotatezMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustRotateZ(-Float.valueOf(danweiText.getText()));

            }
        });
        rotatezadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustRotateZ(Float.valueOf(danweiText.getText()));

            }
        });


        seperate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.seperateSelect();

            }
        });

        xMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(-Float.valueOf(danweiText.getText()), false);

            }
        });
        xadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(Float.valueOf(danweiText.getText()), false);

            }
        });

        yMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(-Float.valueOf(danweiText.getText()), false);

            }
        });
        yadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(Float.valueOf(danweiText.getText()), false);

            }
        });


        zMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(-Float.valueOf(danweiText.getText()), false);

            }
        });
        zadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(Float.valueOf(danweiText.getText()), false);

            }
        });

        widthMiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(-Float.valueOf(danweiText.getText()), true);

            }
        });
        widthAddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(Float.valueOf(danweiText.getText()), true);

            }
        });

        heightMiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(-Float.valueOf(danweiText.getText()), true);

            }
        });
        heightAddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(Float.valueOf(danweiText.getText()), true);

            }
        });

        thickMiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(-Float.valueOf(danweiText.getText()), true);

            }
        });
        thickAddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(Float.valueOf(danweiText.getText()), true);

            }
        });


        flowPane.getChildren().add(selectGrid);

    }

    public BlockPanel(Stage primaryStage) {

        //double width, double height
        this.setContent(flowPane);

        addCreatePanel();


    }

}
