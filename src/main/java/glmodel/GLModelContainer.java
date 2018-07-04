package glmodel;

import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.ShaderUtils;
import org.lwjgl.opengl.GL11;

/** 
 *  This class loads and renders a mesh from an OBJ file format.  The mesh can have
 *  multiple materials, including texture assets.images.
 *
 *  Uses GL_Mesh to load a .obj file and GLMaterialLIb to load the .mtl file.  It
 *  assumes the .obj .mtl and any texture assets.images are present in the same folder.
 *
 *  Also has a function, renderTextured() to draw a mesh with no groups or materials.
 *  The entire mesh will be drawn as one group of triangles with one texture.
 */
public class GLModelContainer {
    public int index=0;
    public int length=0;
   public GLModel[] glModels;

    public GLModelContainer(String name,int max,int step){
        GLModel[] humanWalks = new GLModel[max/step];
        for(int i=0;i<max/step;i++){
            GLModel glModel =new GLModel("config/obj/"+name+"_"+i*step+".obj");
            humanWalks[i]=glModel;
        }
        this.glModels = humanWalks;
        length=max/step;
    }
    public GLModelContainer(GLModel[] glModels){
        this.glModels = glModels;
        length=glModels.length;
    }
    public void next(){
        index ++;
        if(index>=length){
            index=0;
        }
    }
}
