package cola.machine.game.myblocks.model.human;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.Connector;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import glmodel.GL_Vector;

/**
 * Created by luying on 16/7/24.
 */
public class Player {
    TextureManager textureManager ;
    float HAND_HEIGHT=1.5f;
    float HAND_WIDTH=0.5f;
    float HAND_THICK=0.5f;

    float BODY_HEIGHT=1.5f;
    float BODY_WIDTH=1f;
    float BODY_THICK=0.5f;


    float LEG_HEIGHT=1.5f;
    float LEG_WIDTH=0.5f;
    float LEG_THICK=0.5f;

    float HEAD_HEIGHT=1f;
    float HEAD_WIDTH=1f;
    float HEAD_THICK=1f;

    public Component bodyComponent = new Component(BODY_WIDTH,BODY_HEIGHT,BODY_THICK);


    public Player(TextureManager textureManager){

        this.textureManager=textureManager;
        bodyComponent.setOffsetPosition(new GL_Vector(0,4,0));
        bodyComponent.setEightFace("humanBody",textureManager);
        //body
    //lhand
        Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lHandComponent.setEightFace("humanHand", textureManager);
        lHandComponent.id="lHumanHand";
        Connector body_lhand = new Connector(lHandComponent,new GL_Vector(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(0,HAND_HEIGHT*3/4,HAND_THICK/2));

        bodyComponent.addConnector(body_lhand);

        //rhand
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rHandComponent.setEightFace("humanHand",textureManager);
        rHandComponent.id="rHumanHand";
        Connector body_rhand = new Connector(rHandComponent,new GL_Vector(0,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addConnector(body_rhand);

       //lleg
        Component lleg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        lleg.setEightFace("humanLeg",textureManager);
        lleg.id="lHumanLeg";
        Connector body_lleg = new Connector(lleg,new GL_Vector(LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addConnector(body_lleg);


        //rleg
        Component rleg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        rleg.setEightFace("humanLeg",textureManager);
        rleg.id="rHumanLeg";
        Connector body_rleg = new Connector(rleg,new GL_Vector(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addConnector(body_rleg);

        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("humanHead",textureManager);
        Connector body_head= new Connector(head,new GL_Vector(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new GL_Vector(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addConnector(body_head);




    }

    public void render(){
        bodyComponent.render();
    }
}
