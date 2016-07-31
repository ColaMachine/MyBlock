package cola.machine.game.myblocks.model;

import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

/**
 * Created by luying on 16/7/23.
 */
public class Connector {

   public Component child;
    GL_Vector parentLocation;
    GL_Vector childLocation;

    public Connector(Component child,
            GL_Vector parentLocation,
            GL_Vector childLocation){
        this.child = child;
        this.parentLocation=parentLocation;
        this.childLocation=childLocation;
    }

    public void render(){
        GL11.glTranslatef(parentLocation.x, parentLocation.y, parentLocation.z);
        GL11.glRotatef(child.rotateX, child.rotateY, child.rotateZ, 0);
        GL11.glTranslatef(-childLocation.x, -childLocation.y, -childLocation.z);
        child.render();
        GL11.glTranslatef(childLocation.x, childLocation.y, childLocation.z);
        GL11.glRotatef(-child.rotateX, -child.rotateY, -child.rotateZ, 0);
        GL11.glTranslatef(-parentLocation.x, -parentLocation.y, -parentLocation.z);
    }
}
