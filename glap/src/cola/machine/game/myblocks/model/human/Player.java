package cola.machine.game.myblocks.model.human;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.Component;
import cola.machine.game.myblocks.model.Connector;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.assets.texture.Texture;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

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


    public Player(){


        //bodyComponent.setOffsetPosition(new GL_Vector(0,4,0));
        bodyComponent.id="human_body";
        bodyComponent.setEightFace("human_body");
        //body
    //lhand
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rHandComponent.setEightFace("human_hand");
        rHandComponent.id="rHumanHand";
        Connector body_rhand = new Connector(rHandComponent,new GL_Vector(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(0,HAND_HEIGHT*3/4,HAND_THICK/2));

        bodyComponent.addConnector(body_rhand);

        //小手
       /* Component rh= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rh.setEightFace("human_hand", textureManager);
        rh.id="rh";
        Connector rhc = new Connector(rh,new GL_Vector(HAND_WIDTH/2,0,HAND_THICK/2),new GL_Vector(HAND_WIDTH/2,HAND_HEIGHT,HAND_THICK/2));

        rHandComponent.addConnector(rhc);*/

        //rhand
        Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lHandComponent.setEightFace("human_hand");
        lHandComponent.id="lHumanHand";
        Connector body_lhand = new Connector(lHandComponent,new GL_Vector(0,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addConnector(body_lhand);

        /*Component lh= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lh.setEightFace("human_hand", textureManager);
        lh.id="lh";
        Connector lhc = new Connector(lh,new GL_Vector(HAND_WIDTH/2,0,HAND_THICK/2),new GL_Vector(HAND_WIDTH/2,HAND_HEIGHT,HAND_THICK/2));

        lHandComponent.addConnector(lhc);*/

       //lleg
        Component human_l_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_b_leg.setEightFace("human_leg");
        human_l_b_leg.id="human_l_b_leg";
        Connector human_l_b_leg_con = new Connector(human_l_b_leg,new GL_Vector(LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addConnector(human_l_b_leg_con);


       /* Component human_l_s_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_s_leg.setEightFace("human_leg", textureManager);
        human_l_s_leg.id="human_l_s_leg";
        Connector human_l_s_leg_con = new Connector(human_l_s_leg,new GL_Vector(LEG_WIDTH/2,0,LEG_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));

        human_l_b_leg.addConnector(human_l_s_leg_con);*/


        //rleg
        Component human_r_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_b_leg.setEightFace("human_leg");
        human_r_b_leg.id="human_r_b_leg";
        Connector human_r_b_leg_con = new Connector(human_r_b_leg,new GL_Vector(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addConnector(human_r_b_leg_con);


        /*Component human_r_s_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_s_leg.setEightFace("human_leg", textureManager);
        human_r_s_leg.id="human_r_s_leg";
        Connector human_r_s_leg_con = new Connector(human_r_s_leg,new GL_Vector(LEG_WIDTH/2,0,LEG_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));

        human_r_b_leg.addConnector(human_r_s_leg_con);*/
        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("human_head");
        Connector body_head= new Connector(head,new GL_Vector(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new GL_Vector(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addConnector(body_head);




    }
    public Player(TextureManager textureManager){

        this.textureManager=textureManager;
        //bodyComponent.setOffsetPosition(new GL_Vector(0,4,0));
        bodyComponent.id="human_body";
        bodyComponent.setEightFace("human_body");
        //body
        //lhand
        Component rHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rHandComponent.setEightFace("human_hand");
        rHandComponent.id="rHumanHand";
        Connector body_rhand = new Connector(rHandComponent,new GL_Vector(BODY_WIDTH,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(0,HAND_HEIGHT*3/4,HAND_THICK/2));

        bodyComponent.addConnector(body_rhand);

        //小手
       /* Component rh= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        rh.setEightFace("human_hand", textureManager);
        rh.id="rh";
        Connector rhc = new Connector(rh,new GL_Vector(HAND_WIDTH/2,0,HAND_THICK/2),new GL_Vector(HAND_WIDTH/2,HAND_HEIGHT,HAND_THICK/2));

        rHandComponent.addConnector(rhc);*/

        //rhand
        Component lHandComponent= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lHandComponent.setEightFace("human_hand");
        lHandComponent.id="lHumanHand";
        Connector body_lhand = new Connector(lHandComponent,new GL_Vector(0,BODY_HEIGHT*3/4,BODY_THICK/2),new GL_Vector(HAND_WIDTH,HAND_HEIGHT*3/4,HAND_THICK/2));
        bodyComponent.addConnector(body_lhand);

        /*Component lh= new Component(HAND_WIDTH,HAND_HEIGHT,HAND_THICK);
        lh.setEightFace("human_hand", textureManager);
        lh.id="lh";
        Connector lhc = new Connector(lh,new GL_Vector(HAND_WIDTH/2,0,HAND_THICK/2),new GL_Vector(HAND_WIDTH/2,HAND_HEIGHT,HAND_THICK/2));

        lHandComponent.addConnector(lhc);*/

        //lleg
        Component human_l_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_b_leg.setEightFace("human_leg");
        human_l_b_leg.id="human_l_b_leg";
        Connector human_l_b_leg_con = new Connector(human_l_b_leg,new GL_Vector(LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));
        bodyComponent.addConnector(human_l_b_leg_con);


       /* Component human_l_s_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_l_s_leg.setEightFace("human_leg", textureManager);
        human_l_s_leg.id="human_l_s_leg";
        Connector human_l_s_leg_con = new Connector(human_l_s_leg,new GL_Vector(LEG_WIDTH/2,0,LEG_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));

        human_l_b_leg.addConnector(human_l_s_leg_con);*/


        //rleg
        Component human_r_b_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_b_leg.setEightFace("human_leg");
        human_r_b_leg.id="human_r_b_leg";
        Connector human_r_b_leg_con = new Connector(human_r_b_leg,new GL_Vector(BODY_WIDTH-LEG_WIDTH/2,0,BODY_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,LEG_THICK/2));
        bodyComponent.addConnector(human_r_b_leg_con);


        /*Component human_r_s_leg= new Component(LEG_WIDTH,LEG_HEIGHT,LEG_THICK);
        human_r_s_leg.setEightFace("human_leg", textureManager);
        human_r_s_leg.id="human_r_s_leg";
        Connector human_r_s_leg_con = new Connector(human_r_s_leg,new GL_Vector(LEG_WIDTH/2,0,LEG_THICK/2),new GL_Vector(LEG_WIDTH/2,LEG_HEIGHT,BODY_THICK/2));

        human_r_b_leg.addConnector(human_r_s_leg_con);*/
        //head

        Component head= new Component(HEAD_WIDTH,HEAD_HEIGHT,HEAD_THICK);
        head.setEightFace("human_head");
        Connector body_head= new Connector(head,new GL_Vector(BODY_WIDTH/2,BODY_HEIGHT,BODY_THICK/2),new GL_Vector(HEAD_WIDTH/2,0,HEAD_THICK/2));
        bodyComponent.addConnector(body_head);




    }


    public void render(){
       // GL11. glEnable(GL11.GL_DEPTH_TEST);
       // GL11.glDisable(GL11.GL_BLEND);
      //  GL11.glDepthRange(0,10);
        bodyComponent.render();
    }
}
