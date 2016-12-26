package com.dozenx.game.font;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.util.UUIDUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
            return new Font("宋体", Font.PLAIN, (int)fontSize);
        }
    }
    public static void ttf(){
        float fontSize =24;
        int color =0x000000;
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
        g.setColor(Color.BLACK);
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
    private static Font getFont(){
        return new Font("Fixedsys",Font.CENTER_BASELINE,18);
    }
    private static int rotate_value=5;//摇摆幅度
    private static void drawString(Graphics2D  g,char car,int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
        int rotateAngle = random.nextInt(rotate_value * 2) - rotate_value;
        //g.translate(random.nextInt(3), random.nextInt(3));
        int _x = random.nextInt(3);
        int _y = random.nextInt(3);
        g.translate(13 * i + _x, 16 + _y);
        g.rotate(rotateAngle * Math.PI / 180);
        g.drawString(car + "", 0, 0);
        g.rotate(-rotateAngle * Math.PI / 180);
        g.translate(-13 * i - _x, -16 - _y);
        // g.drawString(rand,13*i, 16);
    }
    public static void getImgRandcode(String str,String filename) throws FileNotFoundException, IOException {
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(80,26,BufferedImage.TYPE_INT_BGR);
        Graphics2D  g = (Graphics2D )image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, 80, 26);
        g.setFont(new Font("Times New Roman",Font.CENTER_BASELINE,18));
        g.setColor(new Color(50,50,50));
        //绘制干扰线

        //绘制随机字符
        /*for(int i=1;i<=str.length();i++){
            drawString(g,str.charAt(i-1),i);
        }*/
        g.setFont(getFont());
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
        float fontSize =24;
        int color =0x000000;
        int count =list.size();
        String out = new File("").getAbsolutePath();
        //out+"/fontawesome-webfont.ttf";//
        String name ="icon.png";
        int padding =2;
        int w_num=24;
        int charSize =(int)fontSize;//(int ) (padding*2+fontSize);
        int imgSize=charSize*w_num;
        BufferedImage image =new BufferedImage(imgSize,charSize*count/w_num, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        //Font font =loadFont(fontPath,fontSize);
        g.setFont(getFont());

        //g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(new Color(color,true));

        FontMetrics fm =g.getFontMetrics();
        int stringWidth =fm.stringWidth("你");
        int stringAscent = fm.getAscent();//上升
        int stringDecent = fm.getDescent();//下降
        int x = image.getWidth()/2 -stringWidth/2;
        int y = image.getHeight()/2 + (stringAscent-stringDecent)/2;
        //g.setColor(Color.WHITE);
        //g.fillRect(0, 0, image.getWidth(), image.getHeight());
       g.setColor(Color.BLACK);
        g.drawString("啊",x,y);
        // ImageUtil.compressForFix("/Users/luying/Documents/workspace/calendar/src/main/webapp/static/img/a0.jpg");
        //Iterator it = glyphMap.entrySet().iterator();
        int i=0;
        int x_offset = 0;
        int y_offset =0;
        HashMap<Character,Glyph> glyphMap =new HashMap<Character,Glyph>();
        StringBuffer sb =new StringBuffer();

        for(Character  car : list){


            i++;

            x_offset= i%w_num*charSize;
            y_offset= i /w_num*charSize;
            Glyph glyph= new Glyph((int)fontSize,(int)fontSize,x_offset,y_offset);
            glyphMap.put(car,glyph);
            if(i /w_num>=count/w_num){
                break;

            }
            ttf2jpg(car, g, x_offset, y_offset);
            sb.append(car).append(" ").append(x_offset).append(" ").append(y_offset).append(" ").append(charSize).append(" ").append(charSize).append("\r\n");
        }
        try {
            FileUtil.writeFile(new File("cn.txt"), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public static void ttf2jpg(char car , Graphics g,int x,int y){
       // System.out.println("x:"+x+" y:"+y);
       // g.translate(x,y);
        //g.translate(13 * i + _x, 16 + _y);
        //g.drawString(car + "啊", x, y);
        //g.translate(-x,-y);
            g.drawString(car + "", x, y+24);



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
            List<String> lists = FileUtil.readFile2List("/Users/luying/Documents/workspace/calendar/normalcn");
            int count =0 ;
            for (String line : lists) {
                if (!StringUtil.isBlank(line)) {
                    String[] ary = line.split(" ");
                    for(int i=0;i<ary.length;i++){
                        list.add(Character.valueOf(ary[i].trim().charAt(0)));
                        System.out.println(ary[i]);
                    }

                }
            }

            drawAllCharacterInOneJpg(list);
        }catch(Exception e){
            e.printStackTrace();

        }


    }

    /**
     * the cn char map the location in the png texture
     * @return
     */
    public static HashMap<Character,Glyph> readGlyph(){
        HashMap<Character,Glyph> map =new HashMap<>();
        try {
            List<String> lines= FileUtil.readFile2List(PathManager.getInstance().getHomePath().resolve("cn.txt").toString());
            for(String line:lines){
               String[] ary = line.split(" ");
                map.put(ary[0].charAt(0),new Glyph(Integer.valueOf(ary[1]),Integer.valueOf(ary[2]),Integer.valueOf(ary[3]),Integer.valueOf(ary[4])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}

