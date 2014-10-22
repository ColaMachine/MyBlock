package cola.machine.game.myblocks.LifeThing.animal.Dinosaur;

import cola.machine.game.myblocks.repository.BlockRepository;
import glmodel.GL_Vector;

/**
 * Created by luying on 14-10-15.
 */
public class Dinosaur {
    public GL_Vector ViewDir;
    public GL_Vector RightVector;
    public GL_Vector UpVector;
    public GL_Vector WalkDir;
    public GL_Vector Position;
    public GL_Vector oldPosition=new GL_Vector();



    //descriptor

    //tail
    double TailLength=0.5;

    //body
    float bodyHeight =2f;
    float bodyWidth=1;
    float Thickness=1;


    public Dinosaur() {

    }
    public void render(){

    }
        //sword=new Sw
}
