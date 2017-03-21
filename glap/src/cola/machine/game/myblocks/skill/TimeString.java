package cola.machine.game.myblocks.skill;

import com.dozenx.util.TimeUtil;

/**
 * Created by dozen.zhang on 2017/3/21.
 */
public class TimeString {
    public TimeString(String text,float x,float y){
        this.text =text;
        this.x =x ;
        this.y = y ;
        this.setStartTime(TimeUtil.getNowMills());
    }
    private long startTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private String text;
    float x;
    float y;
}
