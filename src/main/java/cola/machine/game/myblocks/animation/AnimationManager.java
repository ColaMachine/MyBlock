package cola.machine.game.myblocks.animation;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.model.BoneRotateImageBlock;
import cola.machine.game.myblocks.model.RotateImageBlock;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.dozenx.game.engine.element.bean.Component;
import com.dozenx.util.FileUtil;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by colamachine on 16-7-25.
 */
public class AnimationManager {
    //componentid:action ==> animation animation 是绑定在一个节点上的一类动画
    HashMap<String, Animation> domAnimationsMap = new HashMap<>();
    //framesName ==> keyframes 是animation对应的关键帧
    HashMap<String, KeyFrames> keyFramesMap = new HashMap<>();
    //animator 是动画的执行过程
    List<Animator> animators = new ArrayList<>();
    //id:action==>animator
    HashMap<String, Animator> id2animatorMap = new HashMap<>();

    public AnimationManager() {
        this.init();
        CoreRegistry.put(AnimationManager.class, this);

    }

    public void clear(Component component) {

        Iterator it = id2animatorMap.entrySet().iterator();
        int hashCode = component.hashCode();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) it.next();
            String id = entry.getKey();

            if (id.startsWith(hashCode + component.name)) {
                id2animatorMap.get(id).complete = true;
                if (id.endsWith("died")) {
                    LogUtil.println("移除动画");
                }
            }

        }
        if (component.children.size() > 0) {
            for (int i = 0; i < component.children.size(); i++) {
                clear(component.getChildren(i));
            }
        }

    }

    public void apply(BoneRotateImageBlock component, String action) {
           if(action.equals("kan"))
            LogUtil.println("action");

        Animation animation = domAnimationsMap.get(component.name + ":" + action);


        if (animation != null) {

            if (id2animatorMap.get(component.hashCode() + component.name + ":" + action) != null) {
                // LogUtil.println(component.hashCode()+component.name+":"+action+"动画已经存在");
                return;
                //animatorMap.get(component.id).complete=true;
            } else {
                Animator animator = new Animator(animation, component);
                animators.add(animator);
                id2animatorMap.put(component.hashCode() + component.name + ":" + action, animator);
            }

        }
        if (component.children.size() > 0) {
            for (int i = 0; i < component.children.size(); i++) {
                if (component.children.get(i) instanceof BoneRotateImageBlock)
                    apply((BoneRotateImageBlock) component.children.get(i), action);
            }
        }
    }

    public void update() throws Exception {
        Long now = new Date().getTime();
        for (int i = animators.size() - 1; i >= 0; i--) {
            // LogUtil.println(now+"正在画:"+i);
//            LogUtil.println("判断两个元素是否相同:"+(animators.get(0).component == animators.get(1).component));
            Animator animator = animators.get(i);
            // LogUtil.println("画完前:"+animator.component.rotateX);

            if (animator.complete) {
                id2animatorMap.remove(animator.component.hashCode() + animator.component.name + ":" + animator.animation.action);
                // id2animatorMap.put(animator.component.id+":"+animator.animation.action,null);
//             LogUtil.println("移除动画" + animators.get(i).animation.action);
                // if(animators.get(i).animation.action.equals("died")){
                //LogUtil.println("移除动画" + animators.get(i).animation.action);
                // }
                animators.remove(i);

            } else {
                animator.process5(now);
            }

            //LogUtil.println("画完后:"+animator.component.rotateX);
        }
    }

    public void readConfigFile(String path) throws Exception {
        StringBuffer content = new StringBuffer();
        File file = new File(path);
        try {

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                line = br.readLine();

                while (line != null) {
                    if (!line.trim().startsWith("//")) {
                        content.append(line);
                    }

                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<CssBlock> blocks = new ArrayList<CssBlock>();
        // String result = conf_read_token();
        int pos = 0;
        for (; ; ) {
            pos = readBlock(blocks, content, pos);
            pos++;
            if (pos >= content.length()) {
                break;
            }
            if (pos == 0) {
                throw new Exception("解析错误");
            }
        }
        // System.out.println(blocks.size());
        for (int i = 0; i < blocks.size(); i++) {
            CssBlock block = blocks.get(i);
            if (block.title.startsWith("@keyframes")) {//如果是keyframes 就解析keyframe 否则就是animation
                KeyFrames keyFrames = new KeyFrames();
                keyFrames.name = block.title.replace("@keyframes", "").trim();
                for (int j = 0; j < block.blocks.size(); j++) {
                    keyFrames.frames.add(parseKeyFrame(block.blocks.get(j)));
                }

                // keyFrames.from=parseKeyFrame(block.blocks.get(0));
                //  keyFrames.to=parseKeyFrame(block.blocks.get(block.blocks.size()-1));
                keyFramesMap.put(keyFrames.name, keyFrames);


            } else {
                Animation animation = new Animation();
                animation = parseAnimation(block);
                domAnimationsMap.put(animation.domName + ":" + animation.action, animation);
            }
        }
        Iterator aniIter = domAnimationsMap.entrySet().iterator();
        while (aniIter.hasNext()) {
            Map.Entry entry = (Map.Entry) aniIter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();

            Animation animation = ((Animation) val);
            animation.setKeyFrames(keyFramesMap.get(animation.animation_name));
            parseTo200msFrame(animation);
        }
        //将动作按每200ms 分割 成制定动画

    }

    public static void parseTo200msFrame(Animation animation){
        if(animation.domName.equals("human_left_leg")){
            LogUtil.println(animation.domName);
        }
        long beginTime=System.currentTimeMillis();
        long addTime = 0 ;
        while(addTime<animation.allTime){
            BoneRotateImageBlock component =  new BoneRotateImageBlock();
            Animator animator =new Animator(animation,component);
            animator.startTime=beginTime;
            animator.lastTime = beginTime;
            try {
                animator.process6(beginTime+addTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Transform transform =new Transform();
            transform.rotateX = component.transform.rotateX;
            transform.rotateY = component.transform.rotateY;
            transform.rotateZ = component.transform.rotateZ;

            transform.translateX = component.transform.translateX;
            transform.translateY = component.transform.translateY;
            transform.translateZ = component.transform.translateZ;

            transform.scaleX = component.transform.scaleX;
            transform.scaleY = component.transform.scaleY;
            transform.scaleZ = component.transform.scaleZ;

            animation.transformsPer200ms.add(transform);

            addTime+=20;
        }
    }

    private Animation parseAnimation(CssBlock block) throws Exception {
        String[] title = block.title.split(":");
        Animation animation = new Animation();
        animation.domName = title[0].trim();
        animation.action = title[1].trim();

        for (int i = 0; i < block.contents.size(); i++) {
            String content = block.contents.get(i);
            String[] contentAry = content.split(":");
            String key = contentAry[0].trim();
            String value = contentAry[1].trim();



           /* try {
                Field field = animation.getClass().getDeclaredField(contentAry[0]);
                if(field!=null ){
                    field.set(animation,);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }*/

            if (key.equals("animation-name")) {
                animation.animation_name = value;
            } else if (key.equals("animation-duration")) {
                animation.animation_duration = Integer.valueOf(value.replace("s", ""));
            } else if (key.equals("animation-timing-function")) {
                animation.animation_timing_function = value;
            } else if (key.equals("animation-delay")) {
                animation.animation_delay = Integer.valueOf(value.replace("s", ""));
            } else if (key.equals("animation-iteration-count")) {
                if (value.equals("infinite")) {
                    animation.animation_iteration_count = 1000;
                } else
                    animation.animation_iteration_count = Integer.valueOf(value.replace("s", ""));
            } else if (key.equals("animation-direction")) {
                animation.animation_direction = value;
            } else if (key.equals("animation-fill-mode")) {
                animation.animation_fill_mode = value;
            } else {
                throw new Exception("animation has no property :" + key);
            }

        }

        //计算全周期时间
        animation.oneTime = animation.animation_duration * 1000;
        animation.allTime = animation.animation_iteration_count * animation.oneTime;

        return animation;
    }

    public KeyFrame parseKeyFrame(CssBlock block) throws Exception {
        String percent = block.title.trim();
        KeyFrame keyFrame
                = new KeyFrame();
        if (!percent.endsWith("%")) {
            throw new Exception("参数应为19%的格式");

        }

        String numStr = this.getMatch(percent, "(\\d+)%", 1);
        keyFrame.num = Integer.valueOf(numStr);

        for (int i = 0; i < block.contents.size(); i++) {

            String content = block.contents.get(i);
            if(content.indexOf("scale")!=-1){
                LogUtil.println("has scale");
            }
            String[] arr = content.split(":");

            if (arr[0].trim().equals("transform")) {
                Transform transform = new Transform();
                keyFrame.transform = transform;
                Pattern p = Pattern.compile("rotate([XYZ])\\((-?\\d+)deg\\)");

                String[] tranformValue = arr[1].split("\\),");
                for (int j = 0; j < tranformValue.length; j++) {//一般只有一项transform: xxxxx
                    String itemStr = tranformValue[j].trim();
                    if (itemStr.endsWith(";")) {
                        itemStr = itemStr.substring(0, itemStr.length() - 1);
                    }
                    if (!itemStr.endsWith(")")) {
                        itemStr+=")";

                    }
                    if(itemStr.startsWith(",")){
                        itemStr = itemStr.substring(0, itemStr.length() - 1);
                    }


                    if (itemStr.startsWith("rotateX(")) {
                        String value = StringUtil.getContentBetween(itemStr, "(", ")");
                        value=value.replace("deg","");
                        transform.rotateX = Integer.valueOf(value);
                    } else if (itemStr.startsWith("rotateY(")) {
                        String value = StringUtil.getContentBetween(itemStr, "(", ")");value=value.replace("deg","");
                        transform.rotateY = Integer.valueOf(value);
                    } else if (itemStr.startsWith("rotateZ(")) {
                        String value = StringUtil.getContentBetween(itemStr, "(", ")");value=value.replace("deg","");
                        transform.rotateZ = Integer.valueOf(value);
                    } else if (itemStr.startsWith("translate(")) {
                        String xyz = StringUtil.getContentBetween(itemStr, "(", ")");
                        String[] xyzAry = xyz.split(",");
                        transform.translateX = Float.valueOf(xyzAry[0]);
                        transform.translateY = Float.valueOf(xyzAry[1]);
                        transform.translateZ = Float.valueOf(xyzAry[2]);


                    } else if (itemStr.startsWith("scale(")) {
                        String xyz = StringUtil.getContentBetween(itemStr, "(", ")");
                        String[] xyzAry = xyz.split(",");
                        transform.scaleX = Float.valueOf(xyzAry[0]);
                        transform.scaleY = Float.valueOf(xyzAry[1]);
                        transform.scaleZ = Float.valueOf(xyzAry[2]);
                        if(transform.scaleX<0.5){
                            LogUtil.println(transform.scaleX+"");
                        }
                    }


//                    Matcher m=p.matcher(tranformValue[j].trim());
//                    while(m.find()){
//                        //LogUtil.println("总数:"+m.groupCount()+"找到一条transform value:"+m.group(1));
////                    LogUtil.println(m.group(1) + ":" + m.group(2));
//                        if (m.group(1).equals("X")) {
//                            transform.rotateX = Integer.valueOf(m.group(2));
//                        } else if (m.group(1).equals("Y")) {
//                            transform.rotateY = Integer.valueOf(m.group(2));
//                        } else if (m.group(1).equals("Z")) {
//                            transform.rotateZ = Integer.valueOf(m.group(2));
//                        }
//                    }
                }

            }
        }
        return keyFrame;
    }

    public String getMatch(String content, String pattern, int index) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        if (m.find()) {
            // LogUtil.println(m.group(index)+":"+m.group(2));
            return m.group(index);

        }


        return null;
    }

    public int readBlock(List<CssBlock> blocks, StringBuffer content, int pos) {
        int start = pos;
        int line = 0;
//        int pos=0;
        boolean lastIsAWorld = false;
        boolean thisIsAWorld = false;

        CssBlock block = new CssBlock();
        boolean blockBegin = false;
        int blockIndex = 0;

        for (int length = content.length(); pos < length; pos++) {

            char ch = content.charAt(pos);
            // System.out.println(ch);
            switch (ch) {
                    /*case ' ':*/
                case '\t':
                case ';':
                   /* case '\n':*/
                    thisIsAWorld = false;
                    break;
                case '{':
                    blockBegin = true;
                    if (blockIndex == 0) {
                        block = new CssBlock();
                        blocks.add(block);
                        blockIndex++;
                    } else {
                        //  System.out.println("find a new block "+ content.substring(start,start+10));
                        pos = readBlock(block.blocks, content, start);
                        thisIsAWorld = false;
                        ch = content.charAt(pos);
                    }
                    thisIsAWorld = false;
                    break;
                case '}':
                   /* blockIndex--;
                    if(blockIndex==0){
                        blockBegin=false;
                    }
                    thisIsAWorld=false;*/
                    return pos;
                default:
                    thisIsAWorld = true;

            }

            //如果之前是控制字符 现在是文本字符 那么说明开始了正常
            if (thisIsAWorld && !lastIsAWorld) {
                start = pos;
            }

            if (lastIsAWorld && !thisIsAWorld) {//如果之前是文本字符 现在是控制字符说明读完了一个 world
                String s = content.substring(start, pos);
                if (block.title == null) {
                    block.title = s.trim();
                    //System.out.println("title"+s.trim());
                } else {
                    //这里还要判断读到的是不是一个}
                    if (ch != '}') {
                        block.contents.add(s.trim());
                        //System.out.println("content" + s.trim());
                    }
                }
                //表示读到一个单词

            }
            if (thisIsAWorld) {
                lastIsAWorld = true;

            } else {
                lastIsAWorld = false;
            }
        }
        return 0;
    }

    public void init() {
        domAnimationsMap.clear();
        //framesName ==> keyframes 是animation对应的关键帧
        keyFramesMap.clear();
        //animator 是动画的执行过程
        animators.clear();
        //id:action==>animator
        id2animatorMap.clear();
        /*Pattern p=Pattern.compile("rotate([XYZ])\\((\\d+)deg\\)");
        Matcher m=p.matcher("rotateX(12deg)rotateY(12deg)");
        while(m.find()){

            LogUtil.println(m.group(1));

        }*/


        //   AnimationManager manager =new AnimationManager();
        List<File> fileLists = FileUtil.listFile(PathManager.getInstance().getHomePath().resolve("config/animation").toFile());
        try {
            for (File file : fileLists) {
                this.readConfigFile(file.getPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
