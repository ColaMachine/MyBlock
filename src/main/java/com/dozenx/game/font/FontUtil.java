package com.dozenx.game.font;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;

import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.util.UUIDUtil;
import core.log.LogUtil;
import org.lwjgl.system.MemoryUtil;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.*;
import java.util.List;

/**
 * Created by luying on 16/12/24.
 */
public class FontUtil {

    public static Font loadFont(String fontFileName, float fontSize){
        try{
            File file =new File(fontFileName);
            if(!file.exists()){
                System.out.println("找不到字体文件");
            }
            FileInputStream aixing =new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT,aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;

        }catch(Exception e ){
            e.printStackTrace();
            return new Font("微软雅黑", Font.BOLD , (int)fontSize);
        }
    }
    /**
     * Creates a font texture from specified AWT font.
     *
     * @param font      The AWT font
     * @param antiAlias Wheter the font should be antialiased or not
     *
     * @return Font texture
     */
    Map<Character,Glyph> glyphs =new HashMap<>();
    private int fontHeight;
    private TextureInfo createFontTexture(java.awt.Font font, boolean antiAlias) {
        /* Loop through the characters to get charWidth and charHeight */
        int imageWidth = 0;
        int imageHeight = 0;

        /* Start at char #32, because ASCII 0 to 31 are just control codes */
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                /* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            imageWidth += ch.getWidth()+4;
            imageHeight = Math.max(imageHeight, ch.getHeight()+4);
        }

        fontHeight = imageHeight;

        /* Image for the texture */
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;

        /* Create image for the standard chars, again we omit ASCII 0 to 31
         * because they are just control codes */
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                /* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            /* Create glyph and draw char on image */
            Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0f);
            g.drawImage(charImage, x, 0, null);
            x += ch.width;
            glyphs.put(c, ch);
        }

        /* Flip image Horizontal to get the origin to bottom left */
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        /* Get charWidth and charHeight of image */
        int width = image.getWidth();
        int height = image.getHeight();

        /* Get pixel data of image */
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        /* Put pixel data into a ByteBuffer */
        ByteBuffer buffer = ByteBuffer.allocate(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[i * width + j];
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        /* Do not forget to flip the buffer! */
        buffer.flip();

        /* Create texture */
        //silvertiger.tutorial.lwjgl.graphic.Texture
        TextureInfo fontTexture = TextureInfo.createTexture(width, height, buffer);
       // MemoryUtil.memFree(buffer);
        return fontTexture;
    }
    private TextureInfo createFontTexture(java.awt.Font font, boolean antiAlias,List<Character> list) {
        /* Loop through the characters to get charWidth and charHeight */
        int imageWidth = 0;
        int imageHeight = 0;

        /* Start at char #32, because ASCII 0 to 31 are just control codes */
        for (int i = 0; i < list.size(); i++) {

            char c = list.get(i);
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;

        /* Image for the texture */
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;

        /* Create image for the standard chars, again we omit ASCII 0 to 31
         * because they are just control codes */
        for (int i = 0; i < list.size(); i++) {
            if (i == 127) {
                /* ASCII 127 is the DEL control code, so we can skip it */
                continue;
            }
            char c = list.get(i);
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                /* If char image is null that font does not contain the char */
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            /* Create glyph and draw char on image */
            Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0f);
            g.drawImage(charImage, x, 0, null);
            x += ch.width;
            glyphs.put(c, ch);
        }

        FileOutputStream fos =null;
        try{
            fos =new FileOutputStream(PathManager.getInstance().getHomePath().resolve("hello.png").toFile());
            ImageIO.write(image,"png",fos);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        /* Flip image Horizontal to get the origin to bottom left */
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    /* BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image2.setData(image.getData());*/
//        BufferedImage dstImage = new BufferedImage(image.getWidth(), image.getHeight(),
//                BufferedImage.TYPE_INT_ARGB);
//
//        dstImage.getGraphics().drawImage(
//                image, 0, 0, image.getWidth(), image.getHeight(), null);
        image = operation.filter(image, null);

        /* Get charWidth and charHeight of image */
        int width = image.getWidth();
        int height = image.getHeight();

        /* Get pixel data of image */
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);




        /* Put pixel data into a ByteBuffer */
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[i * width + j];
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        /* Do not forget to flip the buffer! */
        buffer.flip();

        /* Create texture */
        //silvertiger.tutorial.lwjgl.graphic.Texture
        TextureInfo fontTexture = TextureInfo.createTexture(width, height, buffer);
        fontTexture.setImgHeight(height);
        fontTexture.setImgWidth(width);
//        MemoryUtil.memFree(buffer);
        return fontTexture;
    }

    /**
     * Creates a char image from specified AWT font and char.
     *
     * @param font      The AWT font
     * @param c         The char
     * @param antiAlias Wheter the char should be antialiased or not
     *
     * @return Char image
     */
    private BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get char charWidth and charHeight */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        /* Check if charWidth is 0 */
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }
    public static void ttf(){
        float fontSize =24;
        int color =0xffffff;
        String out = new File("").getAbsolutePath();
        //out+"/fontawesome-webfont.ttf";//
        String  fontPath = "/Users/luying/Documents/workspace/calendar/src/main/webapp/static/fonts/fontawesome-webfont.ttf";//
        System.out.println(fontPath);
        String name ="icon.png";
        String text ="\uf133";
        int padding =2;
        /*InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br =new BufferedReader(isr);

        try{
            args = br.readLine().split("\\s+");

        }catch(Exception e ){
            e.printStackTrace();
        }

        if(args !=null ){
            for(int i=0;i<args.length;i+=2){
                if(i==args.length-1){
                    continue;
                }
                String key = args[i];
                String value =args[i+1];
                if("--name".equals(key)){
                    name = value;
                }else if("--text".equals(key)){
                    text =value;
                }else if("--padding".equals(key)){
                    padding = Integer.parseInt(value);
                }else if("--fontpath".equals(key)){
                    fontPath =value;
                }else if("--fontpath".equals(key)){
                    fontPath =value;
                }else if("--out".equals(key)){
                    out = value;
                }else if("--size".equals(key)){
                    fontSize =Float.valueOf(value);
                }else if("--color".equals(key)){
                    color =Integer.decode(value);
                }
            }


        }*/

        int imgSize =(int ) (padding*2+fontSize);
        BufferedImage image =new BufferedImage(imgSize,imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        Font font =loadFont(fontPath,fontSize);
        g.setFont(font);

        //g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(new Color(color,true));

        FontMetrics fm =g.getFontMetrics();
        int stringWidth =fm.stringWidth(text);
        int stringAscent = fm.getAscent();//上升
        int stringDecent = fm.getDescent();//下降
        int x = image.getWidth()/2 -stringWidth/2;
        int y = image.getHeight()/2 + (stringAscent-stringDecent)/2;
        //g.setColor(Color.WHITE);
        //g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.WHITE);
        g.drawString(text,x,y);
        // ImageUtil.compressForFix("/Users/luying/Documents/workspace/calendar/src/main/webapp/static/img/a0.jpg");

        g.dispose();
        FileOutputStream fos =null;
        try{
            fos =new FileOutputStream(out+"/"+name);
            ImageIO.write(image,"png",fos);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){


        try {
            generateCN2500();
            //ttf();
            //getImgRandcode("1234","hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Random random = new Random();
    private static Font getFont(int fontSize){
        return new Font("微软雅黑",Font.PLAIN ,fontSize);
    }
    private static int rotate_value=5;//摇摆幅度
    private static void drawString(Graphics2D  g,char car,int i) {
        int fontSize =10;
        g.setFont(getFont(fontSize));
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        int rotateAngle = random.nextInt(rotate_value * 2) - rotate_value;
        //g.translate(random.nextInt(3), random.nextInt(3));
        int _x = random.nextInt(3);
        int _y = random.nextInt(3);
        AttributedString ats = new AttributedString(car+"");
        ats.addAttribute(TextAttribute.FONT, getFont(18), 0, 1);
        AttributedCharacterIterator iter = ats.getIterator();
                          /* 添加水印的文字和设置水印文字出现的内容 ----位置 */

        g.translate(13 * i + _x, 16 + _y);
        g.rotate(rotateAngle * Math.PI / 180);
      //  g.drawString(iter, width - w, height - h);
        g.drawString(iter, 0, 0);
        g.rotate(-rotateAngle * Math.PI / 180);
        g.translate(-13 * i - _x, -16 - _y);
        // g.drawString(rand,13*i, 16);
    }
    public static void getImgRandcode(String str,String filename) throws FileNotFoundException, IOException {
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(80,26,BufferedImage.TYPE_INT_BGR);
        Graphics2D  g = (Graphics2D )image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, 80, 26);
        g.setFont(new Font("Times New Roman",Font.BOLD ,18));
        g.setColor(new Color(50,50,50));
        //绘制干扰线

        //绘制随机字符
        /*for(int i=1;i<=str.length();i++){
            drawString(g,str.charAt(i-1),i);
        }*/
        g.setFont(getFont(18));
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        int rotateAngle = random.nextInt(rotate_value * 2) - rotate_value;
        //g.translate(random.nextInt(3), random.nextInt(3));
        int _x = random.nextInt(3);
        int _y = random.nextInt(3);
        g.translate(13 * 1 + _x, 26 + _y);
        g.rotate(rotateAngle * Math.PI / 180);
        g.drawString(str + "", 0, 0);
        g.rotate(-rotateAngle * Math.PI / 180);
        g.translate(-13 * 0 - _x, -16 - _y);
        //System.out.println(randomString);
        g.dispose();
        if(StringUtil.isBlank(filename)){
            filename = UUIDUtil.getUUID()+".jpg";
        }

        File file= PathManager.getInstance().getHomePath().resolve(filename+".jpg").toFile();
        if(file.exists()){
            System.out.println("文件已经存在");
        }else{
            //如果要创建的多级目录不存在才需要创建。
            file.createNewFile();
        }
       // ByteArrayOutputStream  baos = new ByteArrayOutputStream();
       // ImageIO.write(image, "JPEG",baos);//将内存中的图片通过流动形式输出到客户端
       // byte[] bytes = baos.toByteArray();
        //BASE64Encoder encoder = new BASE64Encoder();
        //String  result = encoder.encodeBuffer(bytes).trim();
        ImageIO.write(image, "JPEG",file);//将内存中的图片通过流动形式输出到客户端
        //return new String[]{Config.getInstance().getImage().getVcodeDir()+"/"+filename+".jpg",str,result};
    }


    public static void drawAllCharacterInOneJpg(List<Character> list){
        int fontSize =30;
       // int color =0x123566;
        int count =list.size();
        int paddingTop=2;
        int paddingLeft =2;
        int paddingRight=3;
        int paddingBottom=5;
        String out = new File("").getAbsolutePath();
        //out+"/fontawesome-webfont.ttf";//
        String name ="zhongwen.png";
       // int padding =2;
        int colNum=24;
        int rowNum=count / colNum;
        int charWidth =paddingLeft+paddingRight+fontSize;
        int charHeight=paddingTop+paddingRight+fontSize;
        int imgWidth=charWidth*colNum;
        int imgHeight = rowNum* (charHeight+3);


        BufferedImage image =new BufferedImage(imgWidth,imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        //Font font =loadFont(fontPath,fontSize);
        Font font = getFont(fontSize);
       // font.get
        g.setFont(getFont(fontSize));

        //g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        //g.setColor(new Color(color,true));

        FontMetrics fm =g.getFontMetrics();

        //g.setColor(Color.WHITE);
        //g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.WHITE);
        //g.drawString("啊",x,y);
        // ImageUtil.compressForFix("/Users/luying/Documents/workspace/calendar/src/main/webapp/static/img/a0.jpg");
        //Iterator it = glyphMap.entrySet().iterator();
        int i=0;
        int x_offset = 0;
        int y_offset =0;
        HashMap<Character,Glyph> glyphMap =new HashMap<Character,Glyph>();
        StringBuffer sb =new StringBuffer();

       /* AttributedString ats = new AttributedString(car+"");
        ats.addAttribute(TextAttribute.FONT, getFont(fontHeight), 0, 1);
        ats.addAttribute(TextAttribute.KERNING,0);
        AttributedCharacterIterator iter = ats.getIterator();*/


        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setStroke(new BasicStroke(2f));
        g.setColor(Color.WHITE);

        for(Character  car : list){

            int stringWidth =fm.stringWidth(car+"");
            int stringHeight = fm.getHeight();
            int stringAscent = fm.getAscent();//上升
            int stringDecent = fm.getDescent();//下降
           // int stringWidth = stringWidth/2;

            //int y = image.getHeight()/2 + (stringAscent-stringDecent)/2;

            x_offset= i%colNum*charWidth;
            y_offset= i /colNum*(charHeight);
            Glyph glyph= new Glyph((int)x_offset,(int)y_offset,charWidth,charHeight);
            glyphMap.put(car,glyph);
            if(i /colNum>=rowNum){
                break;
            }

            ttf2jpg(car, g, x_offset, y_offset,stringAscent);
            sb.append(car).append(" ").append(x_offset+1).append(" ").append(y_offset+1).append(" ").append(charWidth-1).append(" ").append(charHeight-1).append("\r\n");
            i++;
           /* if(i==10){
                break;
            }*/
        }
        try {
            LogUtil.println(PathManager.getInstance().getInstallPath().resolve("wordLocation.txt").toString());
            FileUtil.writeFile(PathManager.getInstance().getInstallPath().resolve("wordLocation.txt").toFile(), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.dispose();
        FileOutputStream fos =null;
        try{
            fos =new FileOutputStream(PathManager.getInstance().getHomePath().resolve(name).toFile());
            ImageIO.write(image,"png",fos);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

public static TextureInfo ti ;
    public static Map<Character,Glyph> drawAllCharacterInOneJpg1(List<Character> list){
      FontUtil fontUtil =new FontUtil();
       TextureInfo ti =  fontUtil.createFontTexture(getFont(16),true,list);
        TextureManager.textureInfoMap.put("zhongwen",ti);//.getTextureInfo("zhongwen");
        FontUtil.ti = ti;
//StringBuffer sb =new StringBuffer();
//        for(Character  car : list){
//
//          Glyph glyph = fontUtil.glyphs.get(car);
//
//            sb.append(car).append(" ").append(glyph.x).append(" ").append(glyph.y).append(" ").append(24).append(" ").append(24).append("\r\n");
//
//
//        }
//        try {
//           // LogUtil.println(PathManager.getInstance().getInstallPath().resolve("wordLocation.txt").toString());
//            FileUtil.writeFile(PathManager.getInstance().getInstallPath().resolve("wordLocation.txt").toFile(), sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

     return fontUtil.glyphs;

    }
    public static void ttf2jpg(char car , Graphics g,int x,int y,int fontHeight){
       // System.out.println("x:"+x+" y:"+y);

      //  g.setColor(Color.BLACK);
        g.drawString(String.valueOf(car), x, y+fontHeight);




    }

    public static void ttf3jpg(char car , Graphics2D g2,int x,int y,int fontHeight,int fontSize){

//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

//shadowGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout tl = new TextLayout(car+"",getFont(fontSize), frc);
        Shape sha = tl.getOutline(AffineTransform.getTranslateInstance(x, y+28));
        g2.setColor(Color.WHITE);
        g2.draw(sha);
        g2.setColor(Color.WHITE);
        g2.fill(sha);




    }
    public static void generateCN(){
        try {
            HashMap<Character,Glyph> cnMap = new HashMap<Character,Glyph>();
            List<String> lists = FileUtil.readFile2List("/Users/luying/Documents/workspace/calendar/gbk2unicode");
            int count =0 ;
            for (String line : lists) {
                if (!StringUtil.isBlank(line)) {
                    String[] ary = line.split("  ");

                    String cn = ary[3];
                    //System.out.println(cn);
                    cnMap.put(cn.charAt(0),null);
                    count++;
                }
            }

            //drawAllCharacterInOneJpg(cnMap,count);
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public static void generateCN2500(){
        try {
            List<Character> list = new ArrayList<Character>();
            List<String> lists = FileUtil.readFile2List(PathManager.getInstance().getInstallPath().resolve("normalcn").toString());
            //读取所有文字
            int count =0 ;
            for (String line : lists) {
                if (!StringUtil.isBlank(line)) {
                    String[] ary = line.split(" ");
                    for(int i=0;i<ary.length;i++){
                        list.add(Character.valueOf(ary[i].trim().charAt(0)));//按行读取文字 然后进行空格分割 放入list
                        //System.out.println(ary[i]);
                    }

                }
            }

            drawAllCharacterInOneJpg(list);
        }catch(Exception e){
            e.printStackTrace();

        }


    }

    public static Map<Character,Glyph> zhongwenMap= readGlyph();
    /**
     * the cn char map the location in the png texture
     * @return
     */
    public static Map<Character,Glyph> readAdvanceGlyph(){


        try {
            //先读取所有字符
//            List<Character> list = new ArrayList<Character>();
//            List<String> lists = FileUtil.readFile2List(PathManager.getInstance().getInstallPath().resolve("normalcn").toString());
//            //读取所有文字
//            int count =0 ;
//            for (String line : lists) {
//                if (!StringUtil.isBlank(line)) {
//                    String[] ary = line.split(" ");
//                    for(int i=0;i<ary.length;i++){
//                        list.add(Character.valueOf(ary[i].trim().charAt(0)));//按行读取文字 然后进行空格分割 放入list
//                        //System.out.println(ary[i]);
//                    }
//
//                }
//            }
            List<Character> list = new ArrayList<Character>();
           String s = "用户名密码登录:1234567890asdfghjklzxcvbm,./';[]";
            for(int i=0;i<s.length();i++){
                list.add(Character.valueOf(s.charAt(i)));//按行读取文字 然后进行空格分割 放入list
                //System.out.println(ary[i]);
            }
            //再绘制所有文字
            return drawAllCharacterInOneJpg1(list);
        }catch(Exception e){
            e.printStackTrace();

        }

        return null;

    }
    /**
     * the cn char map the location in the png texture
     * @return
     */
    public static HashMap<Character,Glyph> readGlyph(){
        HashMap<Character,Glyph> map =new HashMap<>();
        try {
            List<String> lines= FileUtil.readFile2List(PathManager.getInstance().getInstallPath().resolve("wordLocation.txt").toString());
            for(String line:lines){
              //  LogUtil.println(line);
               String[] ary = line.split(" ");
                map.put(ary[0].charAt(0),new Glyph(Integer.valueOf(ary[1]),Integer.valueOf(ary[2]),Integer.valueOf(ary[3]),Integer.valueOf(ary[4])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        FontUtil.ti=TextureManager.getTextureInfo("zhongwen");
        return map;
    }

}

