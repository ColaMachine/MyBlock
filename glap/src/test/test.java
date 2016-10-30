package test;

import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import glapp.GLApp;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;


public class test {
	public static void main(String args[]){
		System.out.println((int)0.999);;
	}
	
	public BufferedImage getGrayPicture(BufferedImage originalImage)
	{	
		originalImage.getColorModel();
		System.out.println(originalImage.getColorModel());
		int green=0,red=0,blue=0,rgb;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		for(int i = originalImage.getMinX();i < imageWidth ;i++)
			{
				for(int j = originalImage.getMinY();j < imageHeight ;j++)
				{
//ͼƬ�����ص���ʵ�Ǹ������������}��forѭ��4��ÿ�����ؽ��в���
					Object data = originalImage.getRaster().getDataElements(i, j, null);//��ȡ�õ����أ�����object���ͱ�ʾ
				
					red = originalImage.getColorModel().getRed(data);
					blue = originalImage.getColorModel().getBlue(data);
					green = originalImage.getColorModel().getGreen(data);
					red = (red*3 + green*6 + blue*1)/10;
					green = red;
					blue = green;
/*
���ｫr��g��b��ת��Ϊrgbֵ����ΪbufferedImageû���ṩ���õ�����ɫ�ķ�����ֻ������rgb��rgb���Ϊ8388608�����������ֵʱ��Ӧ��ȥ255*255*255��16777216
*/
					rgb = (red*256 + green)*256+blue;
					if(rgb>8388608)
					{
						rgb = rgb - 16777216;
					}
//��rgbֵд��ͼƬ
					System.out.printf(" %d %d %d \r\n",red,blue,green);
				}
				
			}
		   
        return originalImage;   
	}
	
	public BufferedImage getGrayPictureAPI(BufferedImage originalImage)
	{
		BufferedImage grayPicture;
		int imageWidth = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		
		grayPicture = new BufferedImage(imageWidth, imageHeight,   
                BufferedImage.TYPE_3BYTE_BGR);
		ColorConvertOp cco = new ColorConvertOp(ColorSpace   
                .getInstance(ColorSpace.CS_GRAY), null);   
        cco.filter(originalImage, grayPicture);   
        return grayPicture;   
	}
    int testDisplayId;
    public void testBeginDisplayList(){

        TextureInfo ti = TextureManager.getTextureInfo("human");
        //  GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti.textureHandle);
        ti.bind();
        GLApp.callDisplayList(this.testDisplayId);

    }
    public void testDraw(){
        // draw the ground plane
       /* GL11.glPushMatrix();
        {
            // GL11.glTranslatef(4f, 33f, 1f); // down a bit
            GL11.glScalef(1f, 1f, 1f);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
            renderCube();

            GL11.glTranslated(2f,2f,2f);
            renderCube();
        }
        GL11.glPopMatrix();*/

        //airplaneShadow.drawShadow();
        //DrawObject();
        //testBeginDisplayList();

        testChunk();

    }

    /*  setLight( GL11.GL_LIGHT2,
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // diffuse color
        		new float[] { 0.2f, 0.2f, 0.2f, 1.0f },   // ambient
        		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },   // specular
        		new float[]{-25,32,10,1} );                         // position
*/
//		 setLight(GL11.GL_LIGHT2, new float[] { 100f, 100f, 100f, 1.0f}, // diffuse // color
//		  new float[] { 1f, 1f, 1f, 1f }, // ambient
//		  new  float[] { 1f, 1f, 1f, 1f }, // specular
//		  new float[] { 1,0,0, 1f
//		  }); // direction (pointing
//		 setLightPosition( GL11.GL_LIGHT2, new float[]{-22,50,1,1} );

    // up)

    // set global light
    //FloatBuffer ltAmbient = allocFloats(new float[] { 3.0f, 3.0f, 3.0f,
    //		1.0f });
    // ltAmbient.flip();
    //GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ltAmbient);
    //GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_FALSE);
//
    //GL11.glEnable(GL11.GL_AUTO_NORMAL);


    // Enable alpha transparency (so text will have transparent background)

    // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    // Create texture for spere
    //sphereTextureHandle = makeTexture("assets.images/background.png");
    //humanTextureHandle = makeTexture("assets.images/2000.png");
    //skyTextureHandle = makeTexture("assets.images/sky180.png");
    //crossTextureHandle = makeTexture("assets.images/gui.png");
    //waterTextureHandle = TextureManager.getIcon("water").textureHandle;
    //textureImg = loadImage("assets.images/gui.png");
    // set camera 1 position


    //human.setHuman(-25, 50, 1, 1, 0, 0, 0, 1, 0);

//        human2.setHuman(1, 1, 1, 0, 0, 1, 0, 1, 0);

    // human.startWalk();



    // make a shadow handler
    // params:
    // the light position,
    // the plane the shadow will fall on,
    // the color of the shadow,
    // this application,
    // the function that draws all objects that cast shadows
    // airplaneShadow = new GLShadowOnPlane(lightPosition, new float[]
    // {0f,1f,0f,3f}, null, this, method(this,"drawObjects"));
    // water=new Water();
    // water.setCenter(2, 2, 2);
    // org.lwjgl.input.Keyboard.enableRepeatEvents(true);


    //skysphere.render();


    //drawShip();
    //drawLine();
    //sword.y=human.Position.y+4;
    //sword.render();
    // print( 30, viewportH- 45, "position x:"+human.oldPosition.x +" y:"+human.oldPosition.y+" z:"+human.oldPosition.z);
      /*  print( 30, viewportH- 45, "Use arrow keys to navigate:");
        print( 30, viewportH- 80, "Left-Right arrows rotate camera", 1);
        print( 30, viewportH-100, "Up-Down arrows move camera forward and back", 1);
        print( 30, viewportH-120, "PageUp-PageDown move vertically", 1);
        print( 30, viewportH-140, "SPACE key switches cameras", 1);*/

    // float[] vector=new float[3];
    //GLApp.project(0,0,-5,vector);
    //  LogUtil.println("x:"+vector[0]+"x:"+vector[1]+"x:"+vector[2]);
    // GLApp.drawCircle((int)vector[0],(int)vector[1],10,5);
    //GLApp.print((int)vector[0],(int)vector[1],"nihao");
    //OpenglUtil.glFillRect(int)vector[0],(int)vector[1],);
    //}
    //CoreRegistry.get(NuiManager.class).render();
    public void testChunk(){
        TextureInfo ti = TextureManager.getTextureInfo("soil");
        ti.bind();
        GL11.glBegin(GL11.GL_QUADS);
       // chunk.render();
        GL11.glEnd();
    }



    public void initDisplayList(){
        testDisplayId = GLApp.beginDisplayList();
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face

        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);//0~4  0~12
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P1);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P2);	// Bottom Right ǰ����
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P6);	// Top Right ǰ����
        GL11.glTexCoord2f(0f, 3/8f); glVertex3fv(P5);	// Top Left	ǰ����
        // Back Face

        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P3);	// Bottom Left ������
        GL11.glTexCoord2f(1/16f, 0f); glVertex3fv(P4);	// Bottom Right ������
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P8);	// Top Right ������
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P7);	// Top Left ������

        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);

        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P5);	// Bottom Left ǰ����
        GL11.glTexCoord2f(2/16f, 3/8f); glVertex3fv(P6);	// Bottom Right ǰ����
        GL11.glTexCoord2f(2/16f, 4/8f); glVertex3fv(P7);	// Top Right ������
        GL11.glTexCoord2f(1/16f, 4/8f); glVertex3fv(P8);	// Top Left ������
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P4);	// Top Right ������
        GL11.glTexCoord2f(2/16f, 3/8f); glVertex3fv(P3);	// Top Left ������
        GL11.glTexCoord2f(2/16f, 4/8f);glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 4/8f);glVertex3fv(P1);	// Bottom Right ǰ����
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P2);	// Bottom Left ǰ����
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P3);	// Bottom Right ������
        GL11.glTexCoord2f(1/16f, 3/8f); glVertex3fv(P7);	// Top Right ������
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P6);	// Top Left ǰ����

        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0f, 0f); glVertex3fv(P4);	// Bottom Left ������
        GL11.glTexCoord2f(1/16f, 0f);glVertex3fv(P1);	// Bottom Right ǰ����
        GL11.glTexCoord2f(1/16f, 3/8f);glVertex3fv(P5);	// Top Right ǰ����
        GL11.glTexCoord2f(0f, 3/8f);glVertex3fv(P8);	// Top Leftǰ����
        GL11.glEnd();

        GL11.glEndList();
    }
    public void glVertex3fv(GL_Vector p){
        GL11.glVertex3f(p.x,p.y,p.z);
    }

    GL_Vector P1=new GL_Vector(-0.25f,-1.5f,0.25f);
    GL_Vector P2=new GL_Vector(0.25f,-1.5f,0.25f);
    GL_Vector P3=new GL_Vector(0.25f,-1.5f,-0.25f);
    GL_Vector P4=new GL_Vector(-0.25f,-1.5f,-0.25f);
    GL_Vector P5=new GL_Vector(-0.25f,0f,0.25f);
    GL_Vector P6=new GL_Vector(0.25f,0f,0.25f);
    GL_Vector P7=new GL_Vector(0.25f,0f,-0.25f);
    GL_Vector P8=new GL_Vector(-0.25f,0f,-0.25f);


    GL_Vector[] vertexs={
            P1,P2,P6,P5,
            P3,P4,P8,P7,
            P5,P6,P7,P8,
            P4,P3,P2,P1,
            P2,P3,P7,P6,
            P4,P1,P5,P8
    };
}
