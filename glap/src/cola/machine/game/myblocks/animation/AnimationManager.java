package cola.machine.game.myblocks.animation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by colamachine on 16-7-25.
 */
public class AnimationManager {
    HashMap<String,Animation> domAnimations=new HashMap<>();
    HashMap<String ,KeyFrames > keyFrames= new HashMap<>();
    public void readConfigFile(String path) throws Exception {
        StringBuffer content = new StringBuffer();
        File file =new File(path);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                 line= br.readLine();
                while(line!=null){
                    content.append(line);
                    line=br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<CssBlock> blocks= new ArrayList<CssBlock>();
           // String result = conf_read_token();
        int pos=0;
        for(;;){
             pos  = readBlock(blocks,content,pos);
            pos++;
            if(pos>=content.length()){
                break;
            }
            if(pos==0){
                throw new Exception("解析错误");
            }
        }
    System.out.println(blocks.size());
        for(int i=0;i<blocks.size();i++){
            CssBlock block = blocks.get(i);
            if(block.title.startsWith("@keyframes"){
                KeyFrames keyFrames=new KeyFrames();
                keyFrames.name= block.title.replace("@keyframes","").trim();
                keyFrames.from=parseKeyFrame(block.blocks.get(0));
                keyFrames.to=parseKeyFrame(block.blocks.get(block.blocks.size()-1));

            }
        }

    }

    public void KeyFrame parseKeyFrame(CssBlock block){
        String percent= block.title.trim();
        List<co>
    }

    public int readBlock( List<CssBlock> blocks,StringBuffer content,int pos){
        int start=pos;
        int line=0;
//        int pos=0;
        boolean lastIsAWorld=false;
        boolean thisIsAWorld=false;

        CssBlock block=new CssBlock();
        boolean blockBegin=false;
        int blockIndex=0;

        for(int length=content.length();pos<length;pos++){

            char ch = content.charAt(pos);
           // System.out.println(ch);
            switch (ch){
                    /*case ' ':*/
                case '\t':
                case ';':
                   /* case '\n':*/
                    thisIsAWorld=false;
                    break;
                case '{':
                    blockBegin=true;
                    if(blockIndex==0){
                        block=new CssBlock();
                        blocks.add(block);
                        blockIndex++;
                    }else{
                        System.out.println("find a new block "+ content.substring(start,start+10));
                        pos=readBlock(block.blocks,content,start);
                        thisIsAWorld=false;
                       ch = content.charAt(pos);
                    }
                    thisIsAWorld=false;
                  break;
                case '}':
                   /* blockIndex--;
                    if(blockIndex==0){
                        blockBegin=false;
                    }
                    thisIsAWorld=false;*/
                    return pos;
                default:
                    thisIsAWorld=true;

            }

            //如果之前是控制字符 现在是文本字符 那么说明开始了正常
            if(thisIsAWorld&&!lastIsAWorld){
                start=pos;
            }

            if(lastIsAWorld&& !thisIsAWorld){//如果之前是文本字符 现在是控制字符说明读完了一个 world
                String s= content.substring(start,pos);
                if(block.title==null){
                    block.title=s.trim();
                    System.out.println("title"+s.trim());
                }else{
                    //这里还要判断读到的是不是一个}
                    if(ch!='}') {
                        block.contents.add(s.trim());
                        System.out.println("content" + s.trim());
                    }
                }
                //表示读到一个单词

            }
            if(thisIsAWorld){
                lastIsAWorld=true;

            }else{
                lastIsAWorld=false;
            }
        }
        return 0;
    }
    public static void main(String args[]){
        AnimationManager manager =new AnimationManager();
        try {
            manager.readConfigFile("/home/colamachine/workspace/MyBlock/glap/src/cola/machine/game/myblocks/animation/animation.cfg");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
