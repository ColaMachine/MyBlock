package com.dozenx.game.engine.fx;

import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
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
public class ImagePanel extends Tab {
    private GraphicsContext gc;
    private FlowPane flowPane =new FlowPane();
    public void add(Node node){
        flowPane.getChildren().add(node);
    }
    public ImagePanel(final Stage primaryStage) {
        GridPane selectGrid = new GridPane();
        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));



        //double width, double height
        this.setContent(selectGrid);
    this.setText("图片资源");
        // CheckBox
//        CheckBox checkBox = new CheckBox("Check Box");
//
//        add(checkBox);
        final ToggleGroup group = new ToggleGroup();
        // RadioButton
        RadioButton radioButton = new RadioButton("size");
        add(radioButton);
        radioButton.setSelected(true);
        radioButton.setToggleGroup(group);
        radioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.size = true;
            }
        });
        RadioButton radioButton2 = new RadioButton("position");
        add(radioButton2);
        radioButton2.setToggleGroup(group);
        radioButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Switcher.size = false;
            }
        });






   /*     fileChooser.showOpenDialog(stage);*/
        // xoffset


        //init(  root );
        final MyCanvas canvas = new MyCanvas(600, 600);

       /* canvas.widthProperty().bind(root.widthProperty().subtract(20));
        canvas.heightProperty().bind(root.heightProperty().subtract(20));*/

        gc=canvas.getGraphicsContext2D();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(PathManager.getInstance().getHomePath().toFile());
        fileChooser.setTitle("加载组件");
        final Button openButton = new Button("加载图片");


        final TextField xInput = new TextField("0");
        xInput.setPrefWidth(110);
        final Button saveTextureButton = new Button("保存选中纹理");

        //遍历所有的image下的纹理

        final ListView<String> imageList = new ListView<String>();

        ObservableList<String> imageItems = FXCollections.observableArrayList(
        );

        HashMap<String, GLImage> imageMap = TextureManager.imageMap;


        Iterator it = imageMap.keySet().iterator();
        while (it.hasNext()) {
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			request.setAttribute((String)entry.getKey(),entry.getValue());


            String key = (String) it.next();
            imageItems.add(key);


        }


        final ListView<String> textureListView = new ListView<String>();
        final ObservableList<String> textureItems = FXCollections.observableArrayList();

        final HashMap<String, TextureInfo> textureMap = TextureManager.textureInfoMap;


        Iterator texutreit = textureMap.keySet().iterator();
        while (texutreit.hasNext()) {


            String key = (String) texutreit.next();
            textureItems.add(key);


        }
        imageList.setItems(imageItems);
        imageList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + imageList.getSelectionModel().getSelectedItem());
                String imageName = imageList.getSelectionModel().getSelectedItem();
                GLImage image = TextureManager.getImage(imageName);
              /*  IntBuffer byteBuffer =IntBuffer.wrap(image.pixels);*/

                //加载所有的相关的textureinfo
                textureItems.clear();
                textureItems.add("空");
                Iterator texutreit = textureMap.keySet().iterator();
                while (texutreit.hasNext()) {


                    String key = (String) texutreit.next();
                    TextureInfo ti = textureMap.get(key);
                    if (ti.imageName.equals(imageName))
                        textureItems.add(key);


                }

                canvas.drawImage(image.img);
            }
        });

        textureListView.setItems(textureItems);
        textureListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String textureInfoName = textureListView.getSelectionModel().getSelectedItem();
                System.out.println("clicked on " + textureInfoName);

                TextureInfo ti = TextureManager.getTextureInfo(textureInfoName);

                if(ti!=null ){
                    gc.setStroke(Color.BLUE);
                    canvas.drawSelect(ti.minX, 1 - ti.maxY, ti.maxX, 1 - ti.minY);

                }
                GamingState.editEngine.nowTextureInfo = ti;

            }
        });

        selectGrid.add(imageList,0,1);


        selectGrid.add(textureListView,1,1);
        //add(textureListView);

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    canvas.drawImage(file);
                }
            }
        });
        final TextField saveTextureText = new TextField("纹理文件名");

        saveTextureButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(canvas.mouseStartX);

                String imageName = imageList.getSelectionModel().getSelectedItem();
                String textureName = textureListView.getSelectionModel().getSelectedItem();


                String textTureName = saveTextureText.getText();
                int minX = (int) (canvas.mouseStartX / canvas.getWidth() * canvas.imgheight);
                int minY = (int) (canvas.mouseStartY / canvas.getHeight() * canvas.imgwidth);

                int maxX = (int) (canvas.mouseEndX / canvas.getWidth() * canvas.imgheight);
                int maxY = (int) (canvas.mouseEndY / canvas.getHeight() * canvas.imgwidth);
                int w = maxX - minX;
                int h = maxY - minY;
                minY = canvas.imgwidth - maxY;


                StringBuffer sb = new StringBuffer();
                //生成json文件
                sb.append("[{").append("name:'").append(textTureName).append("',")
                        .append("xywh:'").append(minX).append(",").append(minY).append(",").append(w).append(",").append(h).append("',")
                        .append("").append("image:'").append(imageName).append("'}]");
                try {
                    FileUtil.writeFile(PathManager.getInstance().getHomePath().resolve("config/texture").resolve(textTureName + ".cfg").toFile(), sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //更新 内存信息
                TextureInfo ti = new TextureInfo(imageName, minX,
                        minY,
                        w,
                        h, textTureName
                );
                TextureManager.textureInfoMap.put(textTureName, ti);
                /*{
                    name: "mantle",
                            xywh:"16, 224, 16, 16,",
                        image:"terrain",
                    // color:"7,67,93,1"
                },
*/
            }
        });
        selectGrid.add(openButton,0,0);
        selectGrid.add(canvas,0,2,2,1);

        selectGrid.add(saveTextureText,1,0);
        selectGrid.add(saveTextureButton,2,0);


    }

}
