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
import javafx.scene.layout.*;
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
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
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
        root.getChildren().add(addAnimationEditPanel());


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
                GamingState.editEngine.setColor((float)color.getRed(),(float)color.getGreen(),(float)color.getBlue(),(float)color.getOpacity());
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
        });;
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
        selectGrid.add(shootBtn, 0, 3);   selectGrid.add(shootComponentBtn, 1, 3);
        selectGrid.add(brushBtn, 0,4);  selectGrid.add(seperate, 1, 4);




        final Label xLabel = new Label("x");
        Button xMi= new Button("-");
        Button xadd= new Button("+");

        final Label yLabel = new Label("y");
        Button yMi= new Button("-");
        Button yadd= new Button("+");

        final Label zLabel = new Label("z");
        Button zMi= new Button("-");
        Button zadd= new Button("+");

        selectGrid.add(xLabel, 0,5);selectGrid.add(xMi, 1,5); selectGrid.add(xadd, 2,5);
        selectGrid.add(yLabel, 0,6);selectGrid.add(yMi, 1,6); selectGrid.add(yadd, 2,6);
        selectGrid.add(zLabel, 0,7); selectGrid.add(zMi, 1,7); selectGrid.add(zadd, 2,7);


        Label widthLabel  =new Label("宽度");
        Label heightLabel  =new Label("高度");
        Label thickLabel  =new Label("厚度");


        Button widthMiBtn= new Button("-");
        Button heightMiBtn= new Button("-");
        Button thickMiBtn= new Button("-");

        Button widthAddBtn= new Button("+");
        Button heightAddBtn= new Button("+");
        Button thickAddBtn= new Button("+");


        selectGrid.add(widthLabel, 0,8);selectGrid.add(widthMiBtn, 1,8); selectGrid.add(widthAddBtn, 2,8);
        selectGrid.add(heightLabel, 0,9);selectGrid.add(heightMiBtn, 1,9); selectGrid.add(heightAddBtn, 2,9);
        selectGrid.add(thickLabel, 0,10); selectGrid.add(thickMiBtn, 1,10); selectGrid.add(thickAddBtn, 2,10);



        final Label rotatexLabel = new Label("rotatex");
        Button rotatexMi= new Button("-");
        Button rotatexadd= new Button("+");

        final Label rotateyLabel = new Label("rotatey");
        Button rotateyMi= new Button("-");
        Button rotateyadd= new Button("+");

        final Label rotatezLabel = new Label("rotatez");
        Button rotatezMi= new Button("-");
        Button rotatezadd= new Button("+");

        selectGrid.add(rotatexLabel, 0,5);selectGrid.add(rotatexMi, 1,5); selectGrid.add(rotatexadd, 2,5);
        selectGrid.add(rotateyLabel, 0,6);selectGrid.add(rotateyMi, 1,6); selectGrid.add(rotateyadd, 2,6);
        selectGrid.add(rotatezLabel, 0,7); selectGrid.add(rotatezMi, 1,7); selectGrid.add(rotatezadd, 2,7);

        rotatexMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(-1,false);

            }
        });
        rotatexadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(1,false);

            }
        });

        rotateyMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(-1,false);

            }
        });
        rotateyadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(1,false);

            }
        });


        rotatezMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(-1,false);

            }
        });
        rotatezadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(1,false);

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
                GamingState.editEngine.adjustWidth(-1,false);

            }
        });
        xadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(1,false);

            }
        });

        yMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(-1,false);

            }
        });
        yadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(1,false);

            }
        });


        zMi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(-1,false);

            }
        });
        zadd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(1,false);

            }
        });

        widthMiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(-1,true);

            }
        });
        widthAddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustWidth(1,true);

            }
        });

        heightMiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(-1,true);

            }
        });
        heightAddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustHeight(1,true);

            }
        });

        thickMiBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(-1,true);

            }
        });
        thickAddBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamingState.editEngine.adjustThick(1,true);

            }
        });




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

        final Button editComponentButton = new Button("组件编辑");


        editComponentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

               Switcher.isEditComponent=!Switcher.isEditComponent;
                if(Switcher.isEditComponent) {
                    //Border border = new Border();
                    //editComponentButton.setBorder();
                    GamingState.editEngine.enterComponentEdit();
                }
               //s GamingState.editEngine.buildComponent();

            }
        });

        selectGrid.add(xoffsetLabel, 0, 0); selectGrid.add(xoffsetInput, 1, 0);
        selectGrid.add(yoffsetLabel, 0, 1); selectGrid.add(yoffsetInput, 1, 1);
        selectGrid.add(zoffsetLabel, 0, 2); selectGrid.add(zoffsetInput, 1, 2);

        selectGrid.add(xzoomLabel, 0, 3); selectGrid.add(xzoomInput, 1, 3);
        selectGrid.add(yzoomLabel, 0, 4); selectGrid.add(yzoomInput, 1, 4);
        selectGrid.add(zzoomLabel, 0, 5); selectGrid.add(zzoomInput, 1, 5);

        selectGrid.add(componentAdjust, 0, 6);  selectGrid.add(buildComponentButton, 1, 6);
        selectGrid.add(editComponentButton, 0, 7);

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
        Button refreshBtn = new Button("刷新");

        box.getChildren().add(refreshBtn);

        for( File file : list){
            Button component = new Button();
            component.setText(file.getName());
            final String name = file.getName();
            GamingState.editEngine.readAndLoadColorGroupFromFile(file);
            box.getChildren().add(component);
            component.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GamingState.editEngine.changeCurrentComponent(name);
                }
            });
        }

        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //获取名称
                for(int i=box.getChildren().size()-1;i>=1;i--){
                    box.getChildren().remove(i);
                }

                List<File> list = FileUtil.listFile(PathManager.getInstance().getHomePath().resolve("save/component").toFile());

                for( File file : list){
                    Button component = new Button();
                    component.setText(file.getName());
                    final String name = file.getName();
                    GamingState.editEngine.readAndLoadColorGroupFromFile(file);
                    box.getChildren().add(component);
                    component.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {


                            GamingState.editEngine.changeCurrentComponent(name);





                        }
                    });
                }

            }
        });










        titlePane.setContent(selectGrid);
        return titlePane;
    }

    public TitledPane addAnimationEditPanel(){

        TitledPane titlePane = new TitledPane();
        titlePane.setText("动画");
        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        final Button animationEditBtn = new Button("动画编辑");


        final ScrollPane sp = new ScrollPane();

        final   VBox box = new VBox();
        box.getChildren().addAll(sp);
        box.setVgrow(sp, Priority.ALWAYS);



        animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //先清除原来的帧 重新同步过

                for(int i =box.getChildren().size()-1;i>=0;i--){
                    box.getChildren().remove(i);
                }
                int size =GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();
                for(int i=0;i<size;i++){
                    Button animationEditBtn = new Button("帧"+i);
                    box.getChildren().add(animationEditBtn);
                    final int nowIndex =i;
                    animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //让现实当前帧
                            // Switcher.currentFrameNum = size;
                            GamingState.editEngine.animationFrameShowNum(nowIndex);


                        }
                    });
                }

            }
        });
        final Button addAnimationFrameBtn = new Button("增加动画帧");


        addAnimationFrameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final   int size =GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                GamingState.editEngine.currentColorAddGroupAnimationFrame();

                Button animationEditBtn = new Button("帧"+size);
                box.getChildren().add(animationEditBtn);


                animationEditBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //让现实当前帧
                        // Switcher.currentFrameNum = size;
                        GamingState.editEngine.animationFrameShowNum(size);


                    }
                });
            }
        });
        final Button saveToCurFrameBtn = new Button("保存到当前帧");


        saveToCurFrameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final   int size =GamingState.editEngine.getCurrentColorGroupAnimationFrameCount();

                GamingState.editEngine.saveToCurFrame();




            }
        });

        final Button playBtn = new Button("播放");
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(GamingState.editEngine.playAnimation()){
                    playBtn.setText("当前播放 按下停止");
                }else{
                    playBtn.setText("当前停止 按下播放");
                }
            }
        });

        final Button deleteBtn = new Button("delete");
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                GamingState.editEngine.deleteCurFrame();

            }
        });
        selectGrid.add(saveToCurFrameBtn,0,0);
        selectGrid.add(addAnimationFrameBtn,0,1);
        selectGrid.add(animationEditBtn,0,2);
        selectGrid.add(playBtn,0,3);
        selectGrid.add(box,0,4);
        selectGrid.add(deleteBtn,0,5);



        titlePane.setContent(selectGrid);
        return titlePane;
    }
}
