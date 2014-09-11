package gldemo;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

import glapp.*;

/**
 * Run a bare-bones GLApp. Draws one white triangle centered on screen.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering, ie. creates a
 * window, sets the display mode, inits mouse and keyboard, then runs a loop
 * that calls draw().
 * <P>
 * napier at potatoland dot org
 */
public class GLApp_Demo_bitmap_font extends GLApp {

	/**
	 * Start the application. run() calls setup(), handles mouse and keyboard
	 * input, calls render() in a loop.
	 */
	public static void main(String args[]) {
		// create the app
		GLApp_Demo_bitmap_font demo = new GLApp_Demo_bitmap_font();

		// set title, window size
		demo.window_title = "Hello World";
		demo.displayWidth = 640;
		demo.displayHeight = 480;

		// start running: will call init(), setup(), draw(), mouse functions
		demo.run();
	}

	/**
	 * Initialize the scene. Called by GLApp.run(). For now the default settings
	 * will be fine, so no code here.
	 */
	byte[] f_bit = { (byte)0xc0, (byte)0x00, (byte)0xc0, //2个BYte 2*8位代表了一个横向的16个像素低
			(byte)0x00, (byte)0xc0, (byte)0x00,
			(byte)0xc0, 0x00, (byte)0xc0,
			0x00, (byte)0xff, 0x00, 
			(byte)0xff, 0x00, (byte)0xc0, 
			0x00, (byte)0xc0, 0x00,
			(byte)0xc0,0x00, (byte)0xff, 
			(byte)0xc0, (byte)0xff,(byte) 0xc0 };
	byte[] e_bit = { (byte)0xc0, (byte)0x00, (byte)0xc0, 
			(byte)0x00, (byte)0xc0, (byte)0x00,
			(byte)0xc0, 0x00, (byte)0xc0,
			0x00, (byte)0xff, 0x00, 
			(byte)0xff, 0x00, (byte)0xc0, 
			0x00, (byte)0xc0, 0x00,
			(byte)0xc0,0x00, (byte)0xff, 
			(byte)0xc0, (byte)0xff,(byte) 0xc0 };
	public void setup() {
		// GL11.glViewport(-2, -2, 4, 4);
		// GL11.glMatrixMode(GL11.GL_PROJECTION);
		// GL11.glLoadIdentity();//����ǰ���û����ϵ��ԭ���Ƶ�����Ļ���ģ�������һ��λ����..
		// GLU.gluOrtho2D(-2f, -2f, 2f,
		// 2f);//����ֱ���(���½�x���,���Ͻ�x���,���½�y���,���Ͻ�y���)�������ȫ����ڴ������½�--ԭ��),
		GL11.glPointSize(1);
		//create bitmap data for font f
		
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
		 bf =BufferUtils.createByteBuffer(f_bit.length);
		bf.put(f_bit);
		bf.flip();
	}
	ByteBuffer bf ;
	/**
	 * Render one frame. Called by GLApp.run().
	 */

	

	public void draw() {
		// Clear screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		// Select The Modelview Matrix (controls model orientation)
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// Reset the coordinate system to center of screen
		GL11.glLoadIdentity();

		// Place the viewpoint
		GLU.gluLookAt(5f, 5f, 10f, // eye position (10 units in front of the
									// origin)
				0f, 0f, 0f, // target to look at (the origin)
				0f, 1f, 0f); // which way is up (Y axis)//�ĸ������
		// draw a triangle centered around 0,0,0
		GL11.glBegin(GL11.GL_LINES); // draw triangles
		GL11.glVertex3f(0, 0, 1); // A1-A2
		GL11.glVertex3f(1, 0, 1);

		GL11.glVertex3f(1, 0, 1); // A2-A3
		GL11.glVertex3f(1, 0, 0);

		GL11.glVertex3f(1, 0, 0); // A3-A4
		GL11.glVertex3f(0, 0, 0);

		GL11.glVertex3f(0, 0, 1); // A4-A1
		GL11.glVertex3f(0, 0, 0);

		GL11.glVertex3f(0, 0, 1); // A1-A5
		GL11.glVertex3f(0, 1, 1);

		GL11.glVertex3f(1, 0, 1); // A2-A6
		GL11.glVertex3f(1, 1, 1);

		GL11.glVertex3f(1, 0, 0); // A3-A7
		GL11.glVertex3f(1, 1, 0);

		GL11.glVertex3f(0, 0, 0); // A4-A8
		GL11.glVertex3f(0, 1, 0);

		GL11.glVertex3f(0, 1, 1); // A5-A6
		GL11.glVertex3f(1, 1, 1);

		GL11.glVertex3f(1, 1, 1); // A6-A7
		GL11.glVertex3f(1, 1, 0);

		GL11.glVertex3f(1, 1, 0); // A7-A8
		GL11.glVertex3f(0, 1, 0);

		GL11.glVertex3f(0, 1, 0); // A8-A5
		GL11.glVertex3f(0, 1, 1);
		GL11.glEnd();
		
		//GL11.glClearColor(1, 1, 1, 1);
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glRasterPos3i(0,0,0);
		//GL13.glWindowPos(0,0);
		GL11.glColor3f(1f,1f,1f);
		
		//System.out.println(bf.remaining());;
		//System.out.println(bf.remaining());;
		GL11.glBitmap(16, 12, 0, 0, 11, 0,bf);
		/*width : 被显示的位图图像的像素宽度。
		height : 被显示的位图图像的像素高度。
		xorig : 位图图像中的起源的 x 位置。 原点被指从该位图与权利和正半轴的方向上的左下角。
		yorig : 位图图像中的起源的 y 位置。 原点被指从该位图与权利和负半轴的方向上的左下角。
		xmove : 源位图中 x 宽度值大小。
		ymove : 源位图中 y 宽度值大小。
		bitmap : 位图图像的地址。*/
		GL11.glBitmap(10, 12, 0, 0, 11, 0,bf);
		GL11.glBitmap(10, 12, 0, 0, 11, 0,bf);
		bf.rewind();
		
	}
}
