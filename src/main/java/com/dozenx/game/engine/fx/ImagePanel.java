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
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
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

        //double width, double height
        this.setContent(flowPane);
    this.setText("图片资源");
        // CheckBox
        CheckBox checkBox = new CheckBox("Check Box");

        add(checkBox);
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
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.CORAL);
        add(colorPicker);



        Button setColorBtn = new Button("设置颜色");

        add(setColorBtn);
        setColorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.setColor((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());
            }
        });


        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = colorPicker.getValue();
                GamingState.editEngine.red = (float) color.getRed();
                GamingState.editEngine.green = (float) color.getGreen();
                GamingState.editEngine.blue = (float) color.getBlue();
            }
        });





   /*     fileChooser.showOpenDialog(stage);*/
        // xoffset


        //init(  root );
        final MyCanvas canvas = new MyCanvas(600, 600);

       /* canvas.widthProperty().bind(root.widthProperty().subtract(20));
        canvas.heightProperty().bind(root.heightProperty().subtract(20));*/


        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(PathManager.getInstance().getHomePath().toFile());
        fileChooser.setTitle("加载组件");
        final Button openButton = new Button("加载图片");


        final TextField xInput = new TextField("0");
        xInput.setPrefWidth(110);
        final Button saveTextureButton = new Button("保存选中纹理");

        //遍历所有的image下的纹理

        final ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList(
        );

        HashMap<String, GLImage> imageMap = TextureManager.imageMap;


        Iterator it = imageMap.keySet().iterator();
        while (it.hasNext()) {
//			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
//			request.setAttribute((String)entry.getKey(),entry.getValue());


            String key = (String) it.next();
            items.add(key);


        }


        final ListView<String> textureListView = new ListView<String>();
        final ObservableList<String> textureItems = FXCollections.observableArrayList();

        final HashMap<String, TextureInfo> textureMap = TextureManager.textureInfoMap;


        Iterator texutreit = textureMap.keySet().iterator();
        while (texutreit.hasNext()) {


            String key = (String) texutreit.next();
            textureItems.add(key);


        }
        list.setItems(items);
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + list.getSelectionModel().getSelectedItem());
                String imageName = list.getSelectionModel().getSelectedItem();
                GLImage image = TextureManager.getImage(imageName);
              /*  IntBuffer byteBuffer =IntBuffer.wrap(image.pixels);*/

                //加载所有的相关的textureinfo
                textureItems.clear();
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

                canvas.drawSelect(ti.minX, 1 - ti.maxY, ti.maxX, 1 - ti.minY);
                GamingState.editEngine.nowTextureInfo = ti;
            }
        });


        add(list);


        add(textureListView);

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

                String imageName = list.getSelectionModel().getSelectedItem();
                String textureName = textureListView.getSelectionModel().getSelectedItem();


                String textTureName = saveTextureText.getText();
                int minX = (int) (canvas.mouseStartX / canvas.getWidth() * canvas.imgwidth);
                int minY = (int) (canvas.mouseStartY / canvas.getHeight() * canvas.imgheight);

                int maxX = (int) (canvas.mouseEndX / canvas.getWidth() * canvas.imgwidth);
                int maxY = (int) (canvas.mouseEndY / canvas.getHeight() * canvas.imgheight);
                int w = maxX - minX;
                int h = maxY - minY;
                minY = canvas.imgheight - maxY;


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


        add(openButton);
        add(canvas);
        add(saveTextureText);
        add(saveTextureButton);

    }

}
