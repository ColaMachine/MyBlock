package cola.machine.game.myblocks.input;

import org.lwjgl.input.Keyboard;

/**
 * Created by luying on 14-9-19.
 */
public class MouseEventReceiver {
    public boolean mouseMoveListen=false;
    public boolean mouseRClickListen=false;
    public boolean mouseLClickListen=false;
    public void mouseMove(int x,int y ){
        //TODO
        if(mouseMoveListen){
            mouseMoveHandle(x,y);


        }

    }
    public void mouseClick(int x,int y){
        if(Keyboard.isKeyDown(0)){

        }
    }
    public void mouseMoveHandle(int x,int y){

    }
    public void mouseRCLickHandle(int x,int y){//receive the mouse key up

    }

    public void mouseLCLickHandle(int x,int y){

    }
}
