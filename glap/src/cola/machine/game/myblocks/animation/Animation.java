package cola.machine.game.myblocks.animation;

/**
 * Created by colamachine on 16_7_27.
 */
public class Animation {
    String domName;
    String action;
    String animation_name;
    Integer  animation_duration;
    String animation_timing_function;
    Integer animation_delay;
    String animation_fill_mode;
    String animation_direction;
    Integer animation_iteration_count;
    KeyFrames keyFrames;
    public void setKeyFrames( KeyFrames keyFrames){
        this.keyFrames =   keyFrames;
    }
}
