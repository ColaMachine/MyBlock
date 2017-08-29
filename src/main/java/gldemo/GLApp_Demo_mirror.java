        package gldemo;

        import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
        import glapp.GLApp;
        import glapp.GLCam;
        import glapp.GLCamera;
        import glmodel.GLModel;
        import glmodel.GL_Matrix;
        import glmodel.GL_Vector;
        import org.lwjgl.input.Keyboard;
        import org.lwjgl.opengl.GL11;
        import org.lwjgl.util.glu.GLU;

        import java.nio.ByteBuffer;
        import java.nio.ByteOrder;
        import java.nio.DoubleBuffer;
        import java.nio.FloatBuffer;

        /**
         * Render a scene with two cameras, one still and one moving.  Hit SPACE to
         * toggle viewpoints between the two cameras.  Uses the GLCamera class to
         * hold camera position and orientation, and the GLCam class to move the
         * current camera in response to arrow key events.
         * <P>
         * @see glapp.GLCamera.java
         * @see glapp.GLCam.java
         * <P>
         * napier at potatoland dot org
         */


        public class GLApp_Demo_mirror extends GLApp {
            // Handle for texture
            int sphereTextureHandle = 0;
            int groundTextureHandle = 0;

            static float	LightAmb[] = {0.7f, 0.7f, 0.7f, 1.0f};				// 环境光

            static float	LightDif[] = {1.0f, 1.0f, 1.0f, 1.0f};				// 漫射光

            static float	LightPos[] = {4.0f, 4.0f, 6.0f, 1.0f};				// 灯光的位置

            float		xrot		=  0.0f;						// X方向的旋转角度

            float		yrot		=  0.0f;						// Y方向的旋转角的

            float		xrotspeed	=  1.0f;							// X方向的旋转速度

            float		yrotspeed	=  1.0f;							// Y方向的旋转速度

            float		zoom		= -6.0f;						// 移入屏幕7个单位

            float		height		=  2.0f;
            // Light position: if last value is 0, then this describes light direction.  If 1, then light position.
            float lightPosition[]= { 0f, 4f, -3f, 0f };
            // Camera position
            float[] cameraPos = {0f,3f,20f};

            // two cameras and a cam to move them around scene
            GLCamera camera1 = new GLCamera();
            GLCamera camera2 = new GLCamera();
            GLCam cam = new GLCam(camera1);

            // vectors used to orient airplane motion
            GL_Vector UP = new GL_Vector(0,1,0);
            GL_Vector ORIGIN = new GL_Vector(0,0,0);

            // for earth rotation
            float degrees = 0;

            // model of airplane and sphere displaylist for earth
            GLModel airplane;
            int earth;

            // shadow handler will draw a shadow on floor plane

            public GL_Vector airplanePos;

            FloatBuffer bbmatrix = GLApp.allocFloats(16);

            /**
             * Initialize application and run main loop.
             */
            public static void main(String args[]) {
                LWJGLHelper.initNativeLibs();
                GLApp_Demo_mirror demo = new GLApp_Demo_mirror();
                demo.VSyncEnabled = true;
                demo.fullScreen = false;
                demo.displayWidth = 800;
                demo.displayHeight = 600;
                demo.run();  // will call init(), render(), mouse functions
            }

            /**
             * Initialize the scene.  Called by GLApp.run()
             */
            public void setup()
            {
                eqr=
                ByteBuffer.allocateDirect(4 * SIZE_DOUBLE).order(ByteOrder.nativeOrder()).asDoubleBuffer();
                eqr.put(0.0f).put(-1.0f).put(0.0f).put(0.0f);

                // setup and enable perspective
                setPerspective();

                // Create a light (diffuse light, ambient light, position)


                // Create a directional light (light green, to simulate reflection off grass)
                setLight( GL11.GL_LIGHT2,
                        new float[] { 0.15f, 0.4f, 0.1f, 1.0f },  // diffuse color
                        new float[] { 0.0f, 0.0f, 0.0f, 1.0f },   // ambient
                        new float[] { 0.0f, 0.0f, 0.0f, 1.0f },   // specular
                        new float[] { 0.0f, -10f, 0.0f, 0f } );   // direction (pointing up)
                GL11.glShadeModel(GL11.GL_SMOOTH);
                GL11.glClearColor(0.2f, 0.5f, 1.0f, 1.0f);
                GL11.glClearDepth(1.0f);
                GL11.glClearStencil(0);

                GL11.glEnable(GL11.GL_DEPTH_TEST);

                GL11.glDepthFunc(GL11.GL_LEQUAL);

                GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

                GL11.glEnable(GL11.GL_TEXTURE_2D);


                /*setLight( GL11.GL_LIGHT1,
                        new float[] { 1f, 1f, 1f, 1f },
                        new float[] { 0.5f, 0.5f, .53f, 1f },
                        new float[] { 1f, 1f, 1f, 1f },
                        lightPosition );*/

                /*setLight( GL11.GL_LIGHT0,
                        LightAmb,
                        LightDif , LightDif ,
                        LightPos );*/

                // enable lighting and texture rendering
                GL11.glEnable(GL11.GL_LIGHTING);


                // Enable alpha transparency (so text will have transparent background)
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                // Create texture for spere
                sphereTextureHandle = makeTexture("glap/assets.images/earth.gif");

                // Create texture for ground plane
                groundTextureHandle = makeTexture("glap/assets.images/grass_1_512.jpg",true,true);

                // set camera 1 position
                camera1.setCamera(0,0,-1, 0,-1f,0, 0,1,0);

                // load the airplane model and make it a display list

                // make a sphere display list
                earth = beginDisplayList(); {
                renderSphere();
            }
                endDisplayList();


                // make a shadow handler
                // params:
                //		the light position,
                //		the plane the shadow will fall on,
                //		the color of the shadow,
                // 		this application,
                // 		the function that draws all objects that cast shadows
            }


            /**
             * set the field of view and view depth.
             */
            public static void setPerspective()
            {
                // select projection matrix (controls perspective)
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glLoadIdentity();
                // fovy, aspect ratio, zNear, zFar
                GLU.gluPerspective(50f,         // zoom in or out of view
                        aspectRatio, // shape of viewport rectangle
                        .1f,         // Min Z: how far from eye position does view start
                        500f);       // max Z: how far from eye position does view extend
                // return to modelview matrix
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
            }

            /**
             * Render one frame.  Called by GLApp.run().
             */
            public void draw() {


                // user keystrokes adjust camera position
                cam.handleNavKeys((float)GLApp.getSecondsPerFrame());

                // combine user camera motion with current camera position (so user can look around while on the airplane)

                // clear depth buffer and color
               // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
               // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
                // select model view for subsequent transforms
              //  GL11.glMatrixMode(GL11.GL_MODELVIEW);
               // GL11.glLoadIdentity();

                // do gluLookAt() with camera position, direction, orientation
                //cam.render();

               DrawGLScene();

            }

            public void DrawFloor() {

                // draw the ground plane
               // GL11.glPushMatrix();
                {// GL11.glTranslated(0,1.5,0);
                   // GL11. glRotatef(30, 1.0f, 0.0f, 0.0f);
                   // GL11.glScalef(15f, .01f, 15f);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
                    //renderCube();

                    GL11.glBegin(GL11.GL_QUADS);

                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);

                    GL11. glTexCoord2f(0.0f, 0.0f);					// 左下

                    GL11.glVertex3f(-4.0f, 0.0f, 8.0f);


                    GL11.glTexCoord2f(1.0f, 0.0f);					// 右下

                    GL11.glVertex3f( 4.0f, 0.0f, 4.0f);






                    GL11.glTexCoord2f(1.0f, 1.0f);					// 右上

                    GL11.glVertex3f( 4.0f, 0.0f,-4.0f);




                    GL11.glTexCoord2f(0.0f, 1.0f);					// 左上

                    GL11.glVertex3f(-4.0f, 0.0f,-4.0f);


                    GL11.glEnd();
                }//GL11.glPopMatrix();
            }
            public void DrawObject() {
                //GL11.glPushMatrix();
                // draw the earth
                { GL11.glColor3f(1.0f, 1.0f, 1.0f);
                   // GL11.glTranslated(0,-1.5,0);
                    //GL11.glRotatef(rotation, 0, 1, 0);  // rotate around Y axis
                   // GL11.glScalef(0.35f, 0.35f, 0.35f);          // scale up
                   // GL11. glRotatef(xrot, 1.0f, 0.0f, 0.0f);					// Rotate On The X Axis
                   // GL11. glRotatef(yrot, 0.0f, 1.0f, 0.0f);					// Rotate On The Y Axis
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
                    callDisplayList(earth);
                }
               // GL11.glPopMatrix();
                // draw the earth
               // GL11.glPushMatrix();
                {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
                   // GL11.glScalef(0.35f, 0.35f, 0.35f);
                   					// Rotate On The Y Axis
                    // scale up
                    GL11. glEnable( GL11.GL_BLEND);						// 启用混合

                    GL11. glBlendFunc( GL11.GL_SRC_ALPHA,  GL11.GL_ONE);					// 把原颜色的40%与目标颜色混合

                    GL11. glEnable( GL11.GL_TEXTURE_GEN_S);						// 使用球映射

                    GL11.glEnable( GL11.GL_TEXTURE_GEN_T);

                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.groundTextureHandle);
                    callDisplayList(earth);
                } //GL11.glPopMatrix();
                GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
                GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
                GL11.glDisable(GL11.GL_BLEND);

            }
            public void DrawObject1() {
                //GL11.glPushMatrix();
                // draw the earth
                { GL11.glColor3f(1.0f, 1.0f, 1.0f);
                    // GL11.glTranslated(0,-1.5,0);
                    //GL11.glRotatef(rotation, 0, 1, 0);  // rotate around Y axis
                    // GL11.glScalef(0.35f, 0.35f, 0.35f);          // scale up
                    // GL11. glRotatef(xrot, 1.0f, 0.0f, 0.0f);					// Rotate On The X Axis
                    // GL11. glRotatef(yrot, 0.0f, 1.0f, 0.0f);					// Rotate On The Y Axis
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
                    callDisplayList(earth);
                }


            }
            /**
             * Given position of object and target, create matrix to
             * orient object so it faces target.
             */
            public void billboardPoint(GL_Vector bbPos, GL_Vector targetPos, GL_Vector targetUp)
            {
                // direction the billboard will be facing (looking):
                GL_Vector look = GL_Vector.sub(targetPos,bbPos).normalize();

                // billboard Right vector is perpendicular to Look and Up (the targets Up vector)
                GL_Vector right = GL_Vector.crossProduct(targetUp,look).normalize();

                // billboard Up vector is perpendicular to Look and Right
                GL_Vector up = GL_Vector.crossProduct(look,right).normalize();

                // Create a 4x4 matrix that will orient the object at bbPos to face targetPos
                GL_Matrix.createBillboardMatrix(bbmatrix, right, up, look, bbPos);

                // apply the billboard matrix
                GL11.glMultMatrix(bbmatrix);
            }

            public void mouseMove(int x, int y) {
            }

            public void mouseDown(int x, int y) {
            }

            public void mouseUp(int x, int y) {
            }

            public void keyDown(int keycode) {
                if (Keyboard.KEY_A == keycode) {
                    zoom +=0.05f;
                }
                if (Keyboard.KEY_Z == keycode) {
                    zoom -=0.05f;
                }
                if (Keyboard.KEY_RIGHT == keycode) {
                    yrotspeed += 0.08f;
                }
                if (Keyboard.KEY_LEFT == keycode) {
                    yrotspeed -= 0.08f;
                }

                if (Keyboard.KEY_DOWN == keycode) {
                    xrotspeed += 0.08f;
                }
                if (Keyboard.KEY_UP == keycode) {
                    xrotspeed -= 0.08f;
                }

                if (Keyboard.KEY_W == keycode) {
                    height +=0.03f;
                }
                if (Keyboard.KEY_S == keycode) {
                    height -=0.03f;
                }




            }

            public void keyUp(int keycode) {
            }

            DoubleBuffer eqr ;

            public void DrawGLScene(){
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
                // double eqr1[] = {0.0f,-1.0f, 0.0f, 0.0f};
                // DoubleBuffer eqr = DoubleBuffer.wrap(eqr1);


                GL11.glLoadIdentity();
                //camera1.setCamera(0,0,0, 0,-1f,0, 0,1,0);
                GL11. glTranslatef(0.0f, -0.6f, zoom);

                GL11.glColorMask(false, false, false, false);// forbit the specify color be written to frame buffer

                GL11. glEnable(GL11.GL_STENCIL_TEST);

                GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 1);			// 设置蒙板测试总是通过，参考值设为1，掩码值也设为1

                GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);		// 设置当深度测试不通过时，保留蒙板中的值不变。如果通过则使用参考值替换蒙板值

                GL11.glDisable(GL11.GL_DEPTH_TEST);				// 禁用深度测试

                DrawFloor();					// 绘制地面

                GL11.glEnable(GL11.GL_DEPTH_TEST);						//启用深度测试

                GL11.glColorMask(true, true, true, true);						// 可以绘制颜色

                GL11.glStencilFunc(GL11.GL_EQUAL, 1, 1);					//下面的设置指定当我们绘制时，不改变蒙板缓存区的值

                GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
                eqr.position(0);
                GL11.glEnable(GL11.GL_CLIP_PLANE0);						// 使用剪切平面
                GL11.glClipPlane(GL11.GL_CLIP_PLANE0, eqr);					// 设置剪切平面为地面，并设置它的法线为向下

                GL11. glPushMatrix();							// 保存当前的矩阵

                GL11.glScalef(1.0f, -1.0f, 1.0f);

                GLApp.setLightPosition(GL11.GL_LIGHT0,LightPos);
                GL11.glTranslatef(0.0f, height, 0.0f);

                GL11. glRotatef(xrot, 1.0f, 0.0f, 0.0f);						// 设置球旋转的角度

                GL11. glRotatef(yrot, 0.0f, 1.0f, 0.0f);
                DrawObject();
                GL11.glPopMatrix();

                									// Pop The Matrix Off The Stack
                GL11.glDisable( GL11.GL_CLIP_PLANE0);							// Disable Clip Plane For Drawing The Floor
                GL11. glDisable( GL11.GL_STENCIL_TEST);							// We Don't Need The Stencil Buffer Any More (Disable)
                setLightPosition( GL11.GL_LIGHT0, LightPos);		// Set Up Light0 Position
                GL11. glEnable( GL11.GL_BLEND);									// Enable Blending (Otherwise The Reflected Object Wont Show)
                GL11. glDisable( GL11.GL_LIGHTING);								// Since We Use Blending, We Disable Lighting
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);					// Set Color To White With 80% Alpha
                GL11.glBlendFunc( GL11.GL_SRC_ALPHA,  GL11.GL_ONE_MINUS_SRC_ALPHA);	// Blending Based On Source Alpha And 1 Minus Dest Alpha
                DrawFloor();										// Draw The Floor To The Screen
                GL11.glEnable( GL11.GL_LIGHTING);								// Enable Lighting
                GL11.glDisable( GL11.GL_BLEND);								// Disable Blending
                GL11. glTranslatef(0.0f, height, 0.0f);					// Position The Ball At Proper Height
                GL11. glRotatef(xrot, 1.0f, 0.0f, 0.0f);					// Rotate On The X Axis
                 GL11. glRotatef(yrot, 0.0f, 1.0f, 0.0f);					// Rotate On The Y Axis
                DrawObject1();										// Draw The Ball
                xrot += xrotspeed;									// Update X Rotation Angle By xrotspeed
                yrot += yrotspeed;									// Update Y Rotation Angle By yrotspeed
                GL11. glFlush();											// Flush The GL Pipeline
            }
        }