package cola.machine.game.myblocks.animation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colamachine on 16-7-25.
 */
public class AnimationManager {

    public void readConfig(String path){
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
        int start=0;
        int line=0;
//        int pos=0;
        boolean lastIsAWorld=false;
        boolean thisIsAWorld=false;
        List<CssBlock> blocks= new ArrayList<CssBlock>();
        CssBlock block=new CssBlock();
        boolean blockBegin=false;
        int blockIndex=0;

        for(int pos=0,length=content.length();pos<length;pos++){

            char ch = content.charAt(pos);

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
                        }
                        blockIndex++;
                        thisIsAWorld=false;
                        break;
                    case '}':
                        blockIndex--;
                        if(blockIndex==0){
                            blockBegin=false;
                        }
                        thisIsAWorld=false;
                        break;
                    default:
                        thisIsAWorld=true;

                }


            if(thisIsAWorld&&!lastIsAWorld){


                start=pos;
            }

            if(lastIsAWorld&& !thisIsAWorld){
                String s= content.substring(start,pos);
                if(block.title==null){
                    block.title=s;
                }else{
                    block.contents.add(s);
                }
                //表示读到一个单词
                System.out.println(content.substring(start,pos));
            }
            if(thisIsAWorld){
                lastIsAWorld=true;

            }else{
                lastIsAWorld=false;
            }


           // String result = conf_read_token();
        }

    }
    public static void main(String args[]){
        AnimationManager manager =new AnimationManager();
        manager.readConfig("/home/colamachine/workspace/MyBlock/glap/src/cola/machine/game/myblocks/animation/animation.cfg");

    }
}
