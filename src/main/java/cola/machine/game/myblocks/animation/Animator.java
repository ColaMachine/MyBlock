package cola.machine.game.myblocks.animation;

import cola.machine.game.myblocks.model.BoneRotateImageBlock;
import com.dozenx.game.engine.element.bean.Component;
import core.log.LogUtil;

import java.util.Date;

/**
 *
 * animator relation with componet is animator
 * Created by luying on 16/7/30.
 */
public class Animator {
    public boolean complete =false;
    Animation animation;
    Long startTime;
    //Long endTime;
    Long currentTime;
    BoneRotateImageBlock component;
    int startValue;
    int endValue;
    int longTime;
    long lastTime;
    KeyFrame[] frames ;
    int slot=0;

    public AnimatorResult getPosition(int num,int[] ary,int allNum, boolean reverse){
        //算出他在第几个格子/
        int times = num/allNum;
        int yushu = num- times*allNum;
        int first = 0;
        int next= 0;
        float percent =0;
            if ((times & 1) != 1 || !reverse) {//说明是偶数
                for(int index =0;index<ary.length-1;index++){
                    if(yushu >=ary[index] && yushu <= ary[index+1]){
                        first = index;
                        next =index+1;
                        percent=(yushu - ary[first])/(ary[next]-ary[first]);
                        AnimatorResult animatorResult =new AnimatorResult();
                        animatorResult.first =first;
                        animatorResult.next=next;
                        animatorResult.percent =percent;
                        return animatorResult;
                    }
                }
                return null;
            } else {
                yushu=allNum -yushu;
                for(int index =0;index<ary.length-2;index++){
                    if(yushu >ary[index] && yushu < ary[index+1]){
                        first = index;
                        next =index+1;
                        percent=(yushu - ary[first])/(ary[next]-ary[first]);
                        AnimatorResult animatorResult =new AnimatorResult();
                        animatorResult.first =first;
                        animatorResult.next=next;
                        animatorResult.percent =percent;
                        return animatorResult;
                    }
                }

                return null;
            }

    }
    public class AnimatorResult{
         int first;
         int next;
        float percent;
    }
    public Animator(Animation animation,BoneRotateImageBlock component){
//        LogUtil.println("创建新的animator");
        this.component=component;
        this.animation=animation;
        this.frames = animation.keyFrames.frames.toArray(new KeyFrame[animation.keyFrames.frames.size()]);

        timeSlice = new int[frames.length];
        this.lastTime=this.startTime=new Date().getTime();
        //this.endTime=this.startTime+animation.animation_duration;
        //统计有哪些属性需要关注
        boolean rotateX=false;


        for(int i=0;i<frames.length;i++){
            timeSlice[i]=animation.animation_duration*1000/100*frames[i].num;//毫秒为单位 为什么除以100呢 当初这样写都不明白为什么了
        }
        if(frames[0].num==0){
            if(frames[1].transform.rotateX!=null ){
                component.rotateX=frames[0].transform.rotateX;
            }
            if(frames[1].transform.rotateY!=null){
                component.rotateY=frames[0].transform.rotateY;
            }
            if(frames[1].transform.rotateZ!=null){
                component.rotateZ=frames[0].transform.rotateZ;
            }
            slot=0;
            accSpeed(frames[0],frames[1],timeSlice[1]-timeSlice[0]);
        }else{
            accSpeed(0);
        }


    }

    public void accSpeed(int i){//这里的i 是以第几针为目标
        i=i+1;
        if(i>=frames.length){
            LogUtil.println("数组越界");
        }

        if(frames[i].transform.rotateX!=null ){
            rotateX_speed=(frames[i].transform.rotateX-component.rotateX)/(timeSlice[i]-(i==0?0:timeSlice[i-1]));
            //System.out.println("rotateX_speed"+rotateX_speed);
        }
        if(frames[i].transform.rotateY!=null){
            rotateY_speed=(frames[i].transform.rotateY-component.rotateY)/(timeSlice[i]-(i==0?0:timeSlice[i-1]));
        }
        if(frames[i].transform.rotateZ!=null){
            rotateZ_speed=(frames[i].transform.rotateZ-component.rotateZ)/(timeSlice[i]-(i==0?0:timeSlice[i-1]));
        }

    }

    public void accSpeed(KeyFrame fromFrame,KeyFrame toFrame,int betweenTime){//这里的i 是以第几针为目标


        if(toFrame.transform.rotateX!=null ){
            rotateX_speed=(float)(toFrame.transform.rotateX-component.transform.rotateX)/betweenTime;
           // System.out.println("rotateX_speed"+rotateX_speed);
        }
        if(toFrame.transform.rotateY!=null){

            rotateY_speed=(toFrame.transform.rotateY-(float)component.transform.rotateY)/betweenTime;
        }
        if(toFrame.transform.rotateZ!=null){

            rotateZ_speed=(toFrame.transform.rotateZ-(float)component.transform.rotateZ)/betweenTime;
        }

        //开始计算translate
        if(toFrame.transform.translateX!=null ){
            translateX_speed=(toFrame.transform.translateX-component.transform.translateX)/betweenTime;
        }
        if(toFrame.transform.translateY!=null ){
            translateY_speed=(toFrame.transform.translateY-component.transform.translateY)/betweenTime;
        }if(toFrame.transform.translateZ!=null ){
            translateZ_speed=(toFrame.transform.translateZ-component.transform.translateZ)/betweenTime;
        }


        if(toFrame.transform.scaleX!=null ){
            scaleX_speed=(toFrame.transform.scaleX-component.transform.scaleX)/betweenTime;
        }
        if(toFrame.transform.scaleY!=null ){
            scaleY_speed=(toFrame.transform.scaleY-component.transform.scaleY)/betweenTime;
        }if(toFrame.transform.scaleZ!=null ){
            scaleZ_speed=(toFrame.transform.scaleZ-component.transform.scaleZ)/betweenTime;
        }

    }
    Float rotateX_speed;
    Float rotateY_speed;
    Float rotateZ_speed;

    Float translateX_speed;
    Float translateY_speed;
    Float translateZ_speed;

    Float scaleX_speed;
    Float scaleY_speed;
    Float scaleZ_speed;

    int[] timeSlice;

    /**        speed == 0~1 slot=0
     * frame 0--------------frame 1------------------frame 2-------------end
     * @param nowTime
     * @throws Exception
     */
    boolean direction =true;


    public void process(Long nowTime) throws Exception {
        synchronized (this) {
            //LogUtil.println("nowTime" + nowTime);
            if (slot >= frames.length) {
                complete = true;
                LogUtil.println("动画结束x'd");
                return;
            }
            int timeInterval =  (int)(nowTime - startTime);
            int nowCount = timeInterval/animation.oneTime;

             int jiange =timeInterval%animation.oneTime;//算出本轮的运行时间

          //  LogUtil.println(nowTime+":"+startTime+":"+jiange+":"+count);
            if(nowCount >= animation.animation_iteration_count){//如果超出了运行的轮次
                //表示运行结束
                if(animation.animation_fill_mode.equals("forwards"))
                {
                    //保留最后动画
                    //LogUtil.println("forward");
                    if(frames[0].transform.rotateX!=null){
                        component.rotateX=frames[0].transform.rotateX;
                    }
                    if(frames[0].transform.rotateY!=null){
                        component.rotateY=frames[0].transform.rotateY;
                    }
                    if(frames[0].transform.rotateZ!=null){
                        component.rotateZ=frames[0].transform.rotateZ;
                    }

                }else if(animation.animation_fill_mode.equals("backwards")){
                    if(frames[frames.length-1].transform.rotateX!=null){
                        component.rotateX=frames[frames.length-1].transform.rotateX;
                    }
                    if(frames[frames.length-1].transform.rotateY!=null){
                        component.rotateY=frames[frames.length-1].transform.rotateY;
                    }
                    if(frames[frames.length-1].transform.rotateZ!=null){
                        component.rotateZ=frames[frames.length-1].transform.rotateZ;
                    }
                }else if(animation.animation_fill_mode.equals("normal")){

                }

//                LogUtil.println("动画结束");
                //是不是在本次时间范围内
                complete=true;
                return;
            }/*else
            if(jiange>animation.animation_duration*1000*animation.animation_iteration_count){
                LogUtil.println("动画结束");
                //是不是在本次时间范围内
                complete=true;
                return;
            }*/else if(nowCount==count/*jiange<animation.oneTime*//**(count+1)*/){//判断本轮的运行是否完成
                //LogUtil.println("--------------又进入了");
                if(direction) {//判断方向 如果是正向
                    if (slot >= timeSlice.length - 1) {
                        throw new Exception("数组越界");
                    }
                    if (jiange < timeSlice[slot + 1] && jiange >= timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                        if (rotateX_speed != null) {
                            component.rotateX += rotateX_speed * (nowTime - lastTime);
                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                            //System.out.println("component.rotateX:" + component.rotateX);
                        }
                        if (rotateY_speed != null) {
                            component.rotateY += rotateY_speed * (nowTime - lastTime);
                        }
                        if (rotateZ_speed != null) {
                            component.rotateZ += rotateZ_speed * (nowTime - lastTime);
                        }
                        lastTime = nowTime;
                    } else if(jiange<timeSlice[slot ]) {
                        LogUtil.println("slot 不在本轮周期中");

                    }else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot+2 >= frames.length) {
                            LogUtil.println("进入这里就见鬼了++");
                            return;

                        }
                        slot++;
                       // LogUtil.println("slot++:"+(slot-1)+"==>"+slot);
                        //重新计算
                        accSpeed(frames[slot],frames[slot+1],timeSlice[slot+1]-timeSlice[slot]);
                    }
                }else{
                    jiange= animation.animation_duration*1000- jiange;

                    if (jiange < timeSlice[slot + 1] && jiange > timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                        if (rotateX_speed != null) {
                            component.rotateX += rotateX_speed * (nowTime - lastTime);
                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                           // System.out.println("component.rotateX:" + component.rotateX);
                        }
                        if (rotateY_speed != null) {
                            component.rotateY += rotateY_speed * (nowTime - lastTime);
                            //component.rotateY += rotateY_speed;
                        }
                        if (rotateZ_speed != null) {
                            //component.rotateZ += rotateZ_speed;
                            component.rotateZ += rotateZ_speed * (nowTime - lastTime);
                            LogUtil.println("rotateZ"+component.rotateZ);
                        }
                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot==0) {
                            throw new Exception("进入这里就见鬼了");
                        }
                        slot--;
                        //重新计算
                        accSpeed(frames[slot+1],frames[slot],timeSlice[slot+1]-timeSlice[slot]);
                    }
                }

            }else{


                if(animation.animation_direction.equals("alternate")){
                    direction=!direction;
                   // LogUtil.println("反向运动"+direction);
                }else{

                }
                count =nowCount;
               if(direction){
                   slot=0;
                   accSpeed(frames[0],frames[1],timeSlice[1]-timeSlice[0]);
               }else{
                   accSpeed(frames[frames.length-1],frames[frames.length-2],timeSlice[frames.length-1]-timeSlice[frames.length-2]);
                   //计算速度

                   //slot不用变;
               }
                //count++;startTime=nowTime;

                //LogUtil.println("count++:"+count);

            }

        }

    }



    public void process2(Long nowTime) throws Exception {
        synchronized (this) {
            //LogUtil.println("nowTime" + nowTime);
            if (slot >= frames.length) {
                complete = true;
                LogUtil.println("动画结束x'd");
                return;
            }
            int timeInterval =  (int)(nowTime - startTime);
            int nowCount = timeInterval/animation.oneTime;

            int jiange =timeInterval%animation.oneTime;//算出本轮的运行时间

            //  LogUtil.println(nowTime+":"+startTime+":"+jiange+":"+count);
            if(nowCount >= animation.animation_iteration_count){//如果超出了运行的轮次
                //表示运行结束
                if(animation.animation_fill_mode.equals("forwards"))
                {
                    //保留最后动画
                    //LogUtil.println("forward");
                    if(frames[0].transform.rotateX!=null){
                        component.rotateX=frames[0].transform.rotateX;
                    }
                    if(frames[0].transform.rotateY!=null){
                        component.rotateY=frames[0].transform.rotateY;
                    }
                    if(frames[0].transform.rotateZ!=null){
                        component.rotateZ=frames[0].transform.rotateZ;
                    }


                }else if(animation.animation_fill_mode.equals("backwards")){
                    if(frames[frames.length-1].transform.rotateX!=null){
                        component.rotateX=frames[frames.length-1].transform.rotateX;
                    }
                    if(frames[frames.length-1].transform.rotateY!=null){
                        component.rotateY=frames[frames.length-1].transform.rotateY;
                    }
                    if(frames[frames.length-1].transform.rotateZ!=null){
                        component.rotateZ=frames[frames.length-1].transform.rotateZ;
                    }
                }else if(animation.animation_fill_mode.equals("normal")){

                }

//                LogUtil.println("动画结束");
                //是不是在本次时间范围内
                complete=true;
                return;
            }/*else
            if(jiange>animation.animation_duration*1000*animation.animation_iteration_count){
                LogUtil.println("动画结束");
                //是不是在本次时间范围内
                complete=true;
                return;
            }*/else if(nowCount==count/*jiange<animation.oneTime*//**(count+1)*/){//判断本轮的运行是否完成
                //LogUtil.println("--------------又进入了");
                if(direction) {//判断方向 如果是正向
                    if (slot >= timeSlice.length - 1) {
                        throw new Exception("数组越界");
                    }
                    if (jiange < timeSlice[slot + 1] && jiange >= timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                            if(frames[slot].transform.rotateX!=null) {
                                component.rotateX = frames[slot].transform.rotateX/180f*3.14f;
                            }
                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                            //System.out.println("component.rotateX:" + component.rotateX);
                        if(frames[slot].transform.rotateY!=null) {
                            component.rotateY = frames[slot].transform.rotateY/180f*3.14f;
                        }
                        if(frames[slot].transform.rotateZ!=null) {
                            component.rotateZ = frames[slot].transform.rotateZ/180f*3.14f;
                        }
                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot+2 >= frames.length) {
                            LogUtil.println("进入这里就见鬼了++");
                            return;

                        }
                        slot++;
                        // LogUtil.println("slot++:"+(slot-1)+"==>"+slot);
                        //重新计算
                      //  accSpeed(frames[slot],frames[slot+1],timeSlice[slot+1]-timeSlice[slot]);
                    }
                }else{
                    jiange= animation.animation_duration*1000- jiange;

                    if (jiange < timeSlice[slot + 1] && jiange > timeSlice[slot ] ) {//如果当前时间在当前frame 区域内

                        if(frames[slot].transform.rotateX!=null) {
                            component.rotateX = frames[slot].transform.rotateX/180f*3.14f;
                        }
                        //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                        //System.out.println("component.rotateX:" + component.rotateX);
                        if(frames[slot].transform.rotateY!=null) {
                            component.rotateY = frames[slot].transform.rotateY/180f*3.14f;
                        }
                        if(frames[slot].transform.rotateZ!=null) {
                            component.rotateZ = frames[slot].transform.rotateZ/180f*3.14f;
                        }
//                            component.rotateX += rotateX_speed * (nowTime - lastTime);
//
//                            component.rotateY += rotateY_speed * (nowTime - lastTime);
//
//                            component.rotateZ += rotateZ_speed * (nowTime - lastTime);

                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot==0) {
                            throw new Exception("进入这里就见鬼了");
                        }
                        slot--;
                        //重新计算
                     //   accSpeed(frames[slot+1],frames[slot],timeSlice[slot+1]-timeSlice[slot]);
                    }
                }

            }else{


                if(animation.animation_direction.equals("alternate")){
                    direction=!direction;
                    // LogUtil.println("反向运动"+direction);
                }else{

                }
                count =nowCount;
                if(direction){
                    slot=0;
                  //  accSpeed(frames[0],frames[1],timeSlice[1]-timeSlice[0]);
                }else{
                   // accSpeed(frames[frames.length-1],frames[frames.length-2],timeSlice[frames.length-1]-timeSlice[frames.length-2]);
                    //计算速度

                    //slot不用变;
                }
                //count++;startTime=nowTime;

                //LogUtil.println("count++:"+count);

            }

        }

    }


    public void process3(Long nowTime) throws Exception {
        synchronized (this) {
            //LogUtil.println("nowTime" + nowTime);
            if (slot >= frames.length) {
                complete = true;
                LogUtil.println("动画结束x'd");
                return;
            }
            int timeInterval =  (int)(nowTime - startTime);
            int nowCount = timeInterval/animation.oneTime;

            int jiange =timeInterval%animation.oneTime;//算出本轮的运行时间

            //  LogUtil.println(nowTime+":"+startTime+":"+jiange+":"+count);
            KeyFrame keyFrame =null;
            if(nowCount >= animation.animation_iteration_count){//如果超出了运行的轮次
                //表示运行结束
                if(animation.animation_fill_mode.equals("forwards")||animation.animation_fill_mode.equals("normal"))
                {
                    keyFrame = frames[0];
                    //保留最后动画
                    //LogUtil.println("forward");
                }else if(animation.animation_fill_mode.equals("backwards")){
                    keyFrame = frames[frames.length-1];
                }
                //是不是在本次时间范围内
                complete=true;
                component.transform.clear();
                return;
            }else if(nowCount==count/*jiange<animation.oneTime*//**(count+1)*/){//判断本轮的运行是否完成
                //LogUtil.println("--------------又进入了");
                if(direction) {//判断方向 如果是正向
                    if (slot >= timeSlice.length - 1) {
                        throw new Exception("数组越界");
                    }
                    if (jiange < timeSlice[slot + 1] && jiange >= timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                        keyFrame =frames[slot];

                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot+2 >= frames.length) {
                            LogUtil.println("进入这里就见鬼了++");
                            return;

                        }
                        slot++;
                        // LogUtil.println("slot++:"+(slot-1)+"==>"+slot);
                        //重新计算
                        //  accSpeed(frames[slot],frames[slot+1],timeSlice[slot+1]-timeSlice[slot]);
                    }
                }else{
                    jiange= animation.animation_duration*1000- jiange;

                    if (jiange < timeSlice[slot + 1] && jiange > timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                        keyFrame = frames[slot];
                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot==0) {
                            throw new Exception("进入这里就见鬼了");
                        }
                        slot--;
                        keyFrame = frames[slot];
                        //重新计算
                    }
                }
                keyFrame =frames[slot];
            }else{
                if(animation.animation_direction.equals("alternate")){
                    direction=!direction;
                }else{
                }
                count =nowCount;
                if(direction){
                    slot=0;
                }else{
                }
                keyFrame =frames[slot];
            }

            if(keyFrame.transform.rotateX!=null){
                component.transform.rotateX=keyFrame.transform.rotateX;
            }
            if(keyFrame.transform.rotateY!=null){
                component.transform.rotateY=keyFrame.transform.rotateY;
            }
            if(keyFrame.transform.rotateZ!=null){
                component.transform.rotateZ=keyFrame.transform.rotateZ;
            }

            if(keyFrame.transform.translateX!=null){
                component.transform.translateX=keyFrame.transform.translateX;
            }
            if(keyFrame.transform.translateY!=null){
                component.transform.translateY=keyFrame.transform.translateY;
            }
            if(keyFrame.transform.translateZ!=null){
                component.transform.translateZ=keyFrame.transform.translateZ;
            }

            if(keyFrame.transform.scaleX!=null){
                component.transform.scaleX=keyFrame.transform.scaleX;
            }
            if(keyFrame.transform.scaleY!=null){
                component.transform.scaleY=keyFrame.transform.scaleY;
            }
            if(keyFrame.transform.scaleZ!=null){
                component.transform.scaleZ=keyFrame.transform.scaleZ;
            }

        }

    }






    public void process5(Long nowTime) throws Exception {
        synchronized (this) {
            //LogUtil.println("nowTime" + nowTime);
            if(this.animation.domName.equals("human_right_hand")){
                //LogUtil.println("humanleftleg");
            }
            int timeInterval =  (int)(nowTime - startTime);
            int nowCount = timeInterval/animation.oneTime;
            int index = timeInterval/20;

            if (index >= animation.transformsPer200ms.size()-1) {
                complete = true;
                LogUtil.println("动画结束x'd"+index+"componentname:"+component.name+"action:"+animation.action);
                component.transform.clear();
              //  index=0;
                return;
            }

            component.transform=animation.transformsPer200ms.get(index);
          //  index++;

        }

    }




    public void process4(Long nowTime) throws Exception {
        synchronized (this) {
            //LogUtil.println("nowTime" + nowTime);
            if(this.animation.domName.equals("human_left_leg")){
                LogUtil.println("humanleftleg");
            }
            if (slot >= frames.length) {
                complete = true;
                LogUtil.println("动画结束x'd");
                return;
            }
            int timeInterval =  (int)(nowTime - startTime);

            int shijiancha = (int)(nowTime - lastTime);
            int nowCount = timeInterval/animation.oneTime;

            int jiange =timeInterval%animation.oneTime;//算出本轮的运行时间

            //  LogUtil.println(nowTime+":"+startTime+":"+jiange+":"+count);
            KeyFrame keyFrame =null;
            if(nowCount >= animation.animation_iteration_count){//如果超出了运行的轮次
                //表示运行结束
                if(animation.animation_fill_mode.equals("forwards")||animation.animation_fill_mode.equals("normal"))
                {
                    keyFrame = frames[0];
                    //保留最后动画
                    //LogUtil.println("forward");
                }else if(animation.animation_fill_mode.equals("backwards")){
                    keyFrame = frames[frames.length-1];
                }
                //是不是在本次时间范围内
                complete=true;
                component.transform.clear();
                return;
            }else if(nowCount==count/*jiange<animation.oneTime*//**(count+1)*/){//判断本轮的运行是否完成
                //LogUtil.println("--------------又进入了");
                if(direction) {//判断方向 如果是正向
                    if (slot >= timeSlice.length - 1) {
                        throw new Exception("数组越界");
                    }
                    if (jiange < timeSlice[slot + 1] && jiange >= timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                        //keyFrame =frames[slot];

                        if (rotateX_speed != null) {
                            component.transform.rotateX += (int)(rotateX_speed * shijiancha);
                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                            //System.out.println("component.rotateX:" + component.rotateX);
                        }
                        if (rotateY_speed != null) {
                            component.transform.rotateY += (int)(rotateY_speed * shijiancha);
                        }
                        if (rotateZ_speed != null) {
                            component.transform.rotateZ += (int)(rotateZ_speed * shijiancha);
                        }


                        if (translateX_speed != null) {
                            component.transform.translateX += translateX_speed * shijiancha;
                        }
                        if (translateY_speed != null) {
                            component.transform.translateY += translateY_speed * shijiancha;
                        }
                        if (translateZ_speed != null) {
                            component.transform.translateZ += translateZ_speed * shijiancha;
                        }


                        if (scaleX_speed != null) {
                            component.transform.scaleX += scaleX_speed * shijiancha;
                        }
                        if (scaleY_speed != null) {
                            component.transform.scaleY += scaleY_speed * shijiancha;
                        }
                        if (scaleZ_speed != null) {
                            component.transform.scaleZ += scaleZ_speed * shijiancha;
                        }


                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot+1 >= frames.length-1) {
                            LogUtil.println("进入这里就见鬼了++");
                            return;

                        }
                        slot++;
                        // LogUtil.println("slot++:"+(slot-1)+"==>"+slot);
                        //重新计算
                          accSpeed(frames[slot],frames[slot+1],timeSlice[slot+1]-timeSlice[slot]);

                        if (rotateX_speed != null) {
                            component.transform.rotateX =frames[slot].transform.rotateX;

                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                            //System.out.println("component.rotateX:" + component.rotateX);
                        }
                        if (rotateY_speed != null) {
                            component.transform.rotateY = frames[slot].transform.rotateY;
                        }
                        if (rotateZ_speed != null) {
                            component.transform.rotateZ = frames[slot].transform.rotateZ;
                        }


                        if (translateX_speed != null) {
                            component.transform.translateX = frames[slot].transform.translateX;
                        }
                        if (translateY_speed != null) {
                            component.transform.translateY = frames[slot].transform.translateY;
                        }
                        if (translateZ_speed != null) {
                            component.transform.translateZ = frames[slot].transform.translateZ;
                        }


                        if (scaleX_speed != null) {
                            component.transform.scaleX = frames[slot].transform.scaleX;
                        }
                        if (scaleY_speed != null) {
                            component.transform.scaleY = frames[slot].transform.scaleY;
                        }
                        if (scaleZ_speed != null) {
                            component.transform.scaleZ = frames[slot].transform.scaleZ;
                        }

                    }
                }else{//如果是反向的
                    jiange= animation.animation_duration*1000- jiange;

                    if (jiange < timeSlice[slot + 1] && jiange > timeSlice[slot ] ) {//如果当前时间在当前frame 区域内
                        //keyFrame = frames[slot];




                        if (rotateX_speed != null) {
                            component.transform.rotateX += (int)(rotateX_speed * shijiancha);
                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                            //System.out.println("component.rotateX:" + component.rotateX);
                        }
                        if (rotateY_speed != null) {
                            component.transform.rotateY += (int)(rotateY_speed * shijiancha);
                        }
                        if (rotateZ_speed != null) {
                            component.transform.rotateZ += (int)(rotateZ_speed * shijiancha);
                        }


                        if (translateX_speed != null) {
                            component.transform.translateX += translateX_speed * shijiancha;
                        }
                        if (translateY_speed != null) {
                            component.transform.translateY += translateY_speed * shijiancha;
                        }
                        if (translateZ_speed != null) {
                            component.transform.translateZ += translateZ_speed * shijiancha;
                        }


                        if (scaleX_speed != null) {
                            component.transform.scaleX += scaleX_speed * shijiancha;
                        }
                        if (scaleY_speed != null) {
                            component.transform.scaleY += scaleY_speed * shijiancha;
                        }
                        if (scaleZ_speed != null) {
                            component.transform.scaleZ += scaleZ_speed * shijiancha;
                        }

                        lastTime = nowTime;
                    } else {//超过制定时间了
                        //需要变更到下一个frame 区域里了
                        //lastTime = nowTime;
                        if (slot==0) {
                            throw new Exception("进入这里就见鬼了");
                        }
                        slot--;

                        if (rotateX_speed != null) {
                            component.transform.rotateX =frames[slot].transform.rotateX;

                            //component.rotateX=frames[slot].transform.rotateX+rotateX_speed*(nowTime-startTime-timeSlice[slot]);
                            //System.out.println("component.rotateX:" + component.rotateX);
                        }
                        if (rotateY_speed != null) {
                            component.transform.rotateY = frames[slot].transform.rotateY;
                        }
                        if (rotateZ_speed != null) {
                            component.transform.rotateZ = frames[slot].transform.rotateZ;
                        }


                        if (translateX_speed != null) {
                            component.transform.translateX = frames[slot].transform.translateX;
                        }
                        if (translateY_speed != null) {
                            component.transform.translateY = frames[slot].transform.translateY;
                        }
                        if (translateZ_speed != null) {
                            component.transform.translateZ = frames[slot].transform.translateZ;
                        }


                        if (scaleX_speed != null) {
                            component.transform.scaleX = frames[slot].transform.scaleX;
                        }
                        if (scaleY_speed != null) {
                            component.transform.scaleY = frames[slot].transform.scaleY;
                        }
                        if (scaleZ_speed != null) {
                            component.transform.scaleZ = frames[slot].transform.scaleZ;
                        }
                       // keyFrame = frames[slot];
                        //重新计算
                    }
                }
                //keyFrame =frames[slot];
            }else{
                if(animation.animation_direction.equals("alternate")){
                    direction=!direction;
                }else{
                }
                count =nowCount;
                if(direction){
                    slot=0;
                    accSpeed(frames[0],frames[1],timeSlice[1]-timeSlice[0]);
                }else{
                    accSpeed(frames[frames.length-1],frames[frames.length-2],timeSlice[frames.length-1]-timeSlice[frames.length-2]);
                    //计算速度
                    //slot不用变;
                }
               // keyFrame =frames[slot];
            }

//            if(keyFrame.transform.rotateX!=null){
//                component.transform.rotateX=keyFrame.transform.rotateX;
//            }
//            if(keyFrame.transform.rotateY!=null){
//                component.transform.rotateY=keyFrame.transform.rotateY;
//            }
//            if(keyFrame.transform.rotateZ!=null){
//                component.transform.rotateZ=keyFrame.transform.rotateZ;
//            }
//
//            if(keyFrame.transform.translateX!=null){
//                component.transform.translateX=keyFrame.transform.translateX;
//            }
//            if(keyFrame.transform.translateY!=null){
//                component.transform.translateY=keyFrame.transform.translateY;
//            }
//            if(keyFrame.transform.translateZ!=null){
//                component.transform.translateZ=keyFrame.transform.translateZ;
//            }
//
//            if(keyFrame.transform.scaleX!=null){
//                component.transform.scaleX=keyFrame.transform.scaleX;
//            }
//            if(keyFrame.transform.scaleY!=null){
//                component.transform.scaleY=keyFrame.transform.scaleY;
//            }
//            if(keyFrame.transform.scaleZ!=null){
//                component.transform.scaleZ=keyFrame.transform.scaleZ;
//            }

        }

    }


    public void process6(Long nowTime) throws Exception {
        synchronized (this) {
            //LogUtil.println("nowTime" + nowTime);
            if(this.animation.domName.equals("human_right_hand")){
                LogUtil.println("humanleftleg");
            }



            int lastTime =  (int)(nowTime - startTime);
            AnimatorResult result = getPosition(lastTime,timeSlice,animation.oneTime,animation.animation_direction.equals("alternate"));
          KeyFrame firstFrame =   frames[result.first];
            KeyFrame nextFrame =   frames[result.next];
            Transform transform =new Transform();
            transform.rotateX = (int)((nextFrame.transform.rotateX - firstFrame.transform.rotateX)*result.percent + firstFrame.transform.rotateX);
            transform.rotateY = (int)((nextFrame.transform.rotateY - firstFrame.transform.rotateY)*result.percent + firstFrame.transform.rotateY);
            transform.rotateZ = (int)((nextFrame.transform.rotateZ - firstFrame.transform.rotateZ)*result.percent + firstFrame.transform.rotateZ);


            transform.translateX = (nextFrame.transform.translateX - firstFrame.transform.translateX)*result.percent + firstFrame.transform.translateX;
            transform.translateY = (nextFrame.transform.translateY - firstFrame.transform.translateY)*result.percent + firstFrame.transform.translateY;
            transform.translateZ = (nextFrame.transform.translateZ - firstFrame.transform.translateZ)*result.percent + firstFrame.transform.translateZ;


            transform.scaleX  = (nextFrame.transform.scaleX - firstFrame.transform.scaleX)*result.percent + firstFrame.transform.scaleX;
            transform.scaleY  = (nextFrame.transform.scaleY - firstFrame.transform.scaleY)*result.percent + firstFrame.transform.scaleY;
            transform.scaleZ  = (nextFrame.transform.scaleZ - firstFrame.transform.scaleZ)*result.percent + firstFrame.transform.scaleZ;
            component.transform=transform;

        }

    }


    public int count;
    public static void main(String args[]){

        Integer a = 0;
        Integer b =a;
        b=1;
       // System.out.println(a);
    }


}
