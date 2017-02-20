package cola.machine.game.myblocks.animation;

import core.log.LogUtil;
import cola.machine.game.myblocks.model.Component;

import java.util.Date;

/**
 * Created by luying on 16/7/30.
 */
public class Animator {
    public boolean complete =false;
    Animation animation;
    Long startTime;
    Long endTime;
    Long currentTime;
    Component component;
    int startValue;
    int endValue;
    int longTime;
    long lastTime;
    KeyFrame[] frames ;
    int slot=0;
    public Animator(Animation animation,Component component){
//        LogUtil.println("创建新的animator");
        this.component=component;
        this.animation=animation;
        this.frames = animation.keyFrames.frames.toArray(new KeyFrame[animation.keyFrames.frames.size()]);

        timeSlice = new int[frames.length];
        this.lastTime=this.startTime=new Date().getTime();
        this.endTime=this.startTime+animation.animation_duration;
        //统计有哪些属性需要关注
        boolean rotateX=false;


        for(int i=0;i<frames.length;i++){
            timeSlice[i]=animation.animation_duration*1000/100*frames[i].num;//毫秒为单位
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
            rotateX_speed=(toFrame.transform.rotateX-component.rotateX)/betweenTime;
           // System.out.println("rotateX_speed"+rotateX_speed);
        }
        if(toFrame.transform.rotateY!=null){
            //TODO
            rotateY_speed=(toFrame.transform.rotateY-component.rotateY)/betweenTime;
        }
        if(toFrame.transform.rotateZ!=null){
            //TODO
            rotateZ_speed=(toFrame.transform.rotateZ-component.rotateZ)/betweenTime;
        }

    }
    Float rotateX_speed;
    Float rotateY_speed;
    Float rotateZ_speed;

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
              /*  if(animation.animation_fill_mode.equals("forwards"))
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

                }*/

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
                    LogUtil.println("反向运动"+direction);
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
public int count;
    public static void main(String args[]){

        Integer a = 0;
        Integer b =a;
        b=1;
       // System.out.println(a);
    }


}
