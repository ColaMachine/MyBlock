package cola.machine.game.myblocks.skill;

import com.dozenx.game.network.server.bean.LivingThingBean;
import glmodel.GL_Vector;

/**
 * Created by luying on 16/9/25.
 */
public class DropBall extends  Ball{
    GL_Vector direction;
    float sumDistance=0;
    int distance=16;
    boolean died=false;
    int width;
    float speed;
    int height;

    LivingThingBean from ;


    public DropBall(int id, GL_Vector position, GL_Vector direction, float speed, Integer itemType, LivingThingBean from) {
        super(id, position, direction, speed, itemType, from);
    }

    public void update(){

    }

    public void render(){

    }


}
