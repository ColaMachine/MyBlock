package cola.machine.game.myblocks.engine.modes;

import cola.machine.game.myblocks.engine.GameEngine;
import cola.machine.game.myblocks.model.ui.html.*;
import com.dozenx.game.graphics.shader.ShaderManager;
import com.dozenx.game.opengl.util.OpenglUtils;

import static org.lwjgl.opengl.GL11.*;

public class LoadingState implements GameState {
    Document document =null;


    public void init(GameEngine engine) {  OpenglUtils.checkGLError();
        document = Document.getInstance();
        document.body.childNodes.clear();




        document.needUpdate=true;


        //加载纹理


    }

    public void dispose() {

    }


    public void handleInput(float delta) {



    }


    public void update(float delta) {
        //TODO recover

        glEnable(GL_TEXTURE_2D);
        OpenglUtils.checkGLError();
        ShaderManager.uiShaderConfig.getVao().getVertices().clear();
       if( Document.needUpdate){

            document.update();



            Document.needUpdate=false;
       }
    }

    public float rotation = 0f;

    @Override
    public void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        document.render();

    }
    @Override
    public boolean isHibernationAllowed() {
        return false;
    }
}
