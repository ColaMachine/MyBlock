package com.dozenx.game.engine.fx;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by dozen.zhang on 2017/8/14.
 */
public class MyCanvas extends Canvas {
    public int mouseStartX;
    public int mouseStartY;
    public int mouseEndX;
    public int mouseEndY;
    public int imgheight;
    public int imgwidth;
    Image image;
    private GraphicsContext gc;
    public MyCanvas(double width, double height) {
        super(width, height);


        gc = getGraphicsContext2D();


        // 在用户拖动鼠标时擦除部分内容
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        gc.clearRect(0, 0, 600, 600);
                        gc.drawImage(image, 0, 0, 600, 600);
                        //gc.save();
                        mouseEndX = (int) e.getX();
                        mouseEndY = (int) e.getY();
                        gc.strokeRect(mouseStartX, mouseStartY, mouseEndX - mouseStartX, mouseEndY - mouseStartY);
                        //gc.restore();

                    }
                });
        this.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseStartX = (int) e.getX();
                        mouseStartY = (int) e.getY();
                    }
                });
        //draw(gc);
    }

    public void drawImage(File file) {
        try {

            image = new Image(new FileInputStream(file));
            imgheight = (int) image.getWidth();
            imgwidth = (int) image.getHeight();
            gc.drawImage(image, 0, 0, 600, 600);
           /* this.setWidth(width);

            this.setHeight(height);
            this.resize(width,height);*/


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void drawImage(ByteBuffer buffer) {
        try {
            InputStream inputStream = new ByteArrayInputStream(buffer.array());
            image = new Image(inputStream);
            imgwidth = (int) image.getWidth();
            imgheight = (int) image.getHeight();
            gc.drawImage(image, 0, 0, 600, 600);
           /* this.setWidth(width);

            this.setHeight(height);
            this.resize(width,height);*/


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void drawImage(BufferedImage bf) {
        try {

            gc.clearRect(0, 0, this.getWidth(), this.getHeight());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bf, "png", out);
            InputStream inputStream = new ByteArrayInputStream(out.toByteArray());

            image = new Image(inputStream);
            imgheight = (int) image.getWidth();
            imgwidth = (int) image.getHeight();
            gc.drawImage(image, 0, 0, 600, 600);
            /*this.setWidth(width);

            this.setHeight(height);
            this.resize(width,height);*/


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void draw(GraphicsContext gc) {

       /* gc.save();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);                //设置线的宽度
        gc.strokeLine(0, 0, 50, 50);       //绘制直线
        gc.restore();

        //绘制椭圆
        gc.save();
        gc.setFill(Color.YELLOWGREEN);
        gc.strokeOval(0, 80, 50, 50);
        gc.fillOval(100, 80, 50, 50);
        gc.restore();

        //绘制矩形
        gc.save();
        gc.setStroke(Color.CHOCOLATE);
        gc.fillRect(0, 150, 50, 50);
        gc.strokeRect(100, 150, 50, 50);
        gc.restore();

        //绘制圆角矩形
        gc.save();
        gc.setFill(Color.CHOCOLATE);
        gc.fillRoundRect(0, 220, 50, 50, 15, 15);
        gc.strokeRoundRect(100, 220, 50, 50, 15, 15);
        gc.restore();

        //绘制扇形
        gc.save();
        gc.setStroke(Color.CHOCOLATE);
        gc.fillArc(10, 300, 30, 30, 40, 280, ArcType.OPEN);
        gc.fillArc(60, 300, 30, 30, 40, 280, ArcType.CHORD);
        gc.fillArc(110, 300, 30, 30, 40, 280, ArcType.ROUND);
        gc.strokeArc(10, 340, 30, 30, 40, 280, ArcType.OPEN);
        gc.strokeArc(60, 340, 30, 30, 40, 280, ArcType.CHORD);
        gc.strokeArc(110, 340, 30, 30, 40, 280, ArcType.ROUND);
        gc.restore();

        //绘制多边形
        gc.save();
        gc.setFill(Color.RED);
        gc.setStroke(Color.CHOCOLATE);
        gc.fillPolygon(new double[]{0, 40, 50, 60, 100, 70, 85, 50, 15, 30}, new double[]{440,440,400,440,440,  460,500,470,500,460}, 10);
        gc.strokePolygon(new double[]{0, 40, 50, 60, 100, 70, 85, 50, 15, 30}, new double[]{440,440,400,440,440,  460,500,470,500,460}, 10);
        gc.restore();*/
    }

    public void drawSelect(float minX, float minY, float maxX, float maxY) {
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        gc.drawImage(image, 0, 0, 600, 600);
        double height = this.getHeight();
        double widht = this.getWidth();
        gc.strokeRect(minX * widht, minY * height, (maxX - minX) * widht, (maxY - minY) * height);
    }
}
