package gldemo.learnOpengl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import com.dozenx.util.FileUtil;
import glapp.GLApp;
import glapp.GLImage;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by luying on 16/10/23.
 */
public class FatherLeanr {
    public int VboId;
    public int VertexShaderId;
    public int ProgramId;
    public FloatBuffer Vertices ;
    public int eboId;
    public int textureHandle;
    public int FragmentShaderId;
    public int VaoId;
    public static final int DISPLAY_HEIGHT = 600; // window width
    public static final int DISPLAY_WIDTH = 600; // window height
    public static final int POSITION_INDEX = 0; // index of vertex attribute "in_Position"
    public static final int COLOR_INDEX = 1; // index of vertex attribute "in_Color"
    public static final int FLOAT_NUM_BYTES; // sizeof(float) in bytes
    public static final int INT_NUM_BYTES; // sizeof(int) in bytes
    public static final int VEC4_BYTES; // sizeof(vec4) in bytes

    public GL_Vector cameraPos = new GL_Vector(0,0,3);
    public GL_Vector viewDir = new GL_Vector(0,0,-1);
    public  GL_Vector up = new GL_Vector(0,1,0);


    static {
        FLOAT_NUM_BYTES = Float.SIZE / Byte.SIZE;
        INT_NUM_BYTES = Integer.SIZE / Byte.SIZE;
        VEC4_BYTES = 4 * FLOAT_NUM_BYTES;
    }
    private final String WINDOW_TITLE = "The Quad: colored";


    public void create() throws LWJGLException, Exception {
// Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat);

            GL11.glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // GL11.glMatrixMode(GL11.GL_PROJECTION);
        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        //GL11.glViewport(0, 0, WIDTH, HEIGHT);

        //OpenGL
        initGL();
        resizeGL();
    }

    public void resizeGL() {
        glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }

    public void initGL() throws IOException {
       // GL11.glEnable(GL11.GL_DEPTH_TEST);
     /*   glClearColor(0, 0, 0, 0);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);*/

        CreateVertShaders();
        CreateFragShaders();
        //CreateMatrix();
        CreateProgram();
        CreateVBO();
        CreateTexture();
    }



    public void CreateVBO(){

    }

    public String getVertPath(){
        return "";
    }
    public String getFragPath(){
        return "";
    }

    //public String vertPath ="box.vert";
  //  public String fragPath ="box.frag";
    //创建顶点着色器
    void CreateVertShaders() throws IOException {



        //顶点着色器
        //编译顶点着色器

        //GLSL opengl shader language


        // Put the shaders "chap2.vert" and "chap2.frag" in the working
        // directory ...

        //==========================================================
        // Load and compile vertex shader

        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+this.getVertPath()).toString());
        //创建着色器
        VertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        Util.checkGLError();
        //源码
        glShaderSource(VertexShaderId, VertexShader);
        Util.checkGLError();
        //编译
        glCompileShader(VertexShaderId);
        Util.checkGLError();
        //打印日志

        // Print possible compile errors
        //System.out.println("Vertex shader compilation:");
        printShaderLog(VertexShaderId);
        Util.checkGLError();


    }
    public int  CreateVertShaders(String path) throws IOException {

        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+path).toString());
        //创建着色器
        int  newShaderId = glCreateShader(GL_VERTEX_SHADER);
        Util.checkGLError();
        //源码
        glShaderSource(newShaderId, VertexShader);
        Util.checkGLError();
        //编译
        glCompileShader(newShaderId);
        Util.checkGLError();
        //打印日志
        printShaderLog(newShaderId);
        Util.checkGLError();
        return newShaderId;
    }
    public int CreateFragShaders(String path) throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+path).toString());

        int newFragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        Util.checkGLError();

        glShaderSource(newFragmentShaderId, FragmentShader);
        Util.checkGLError();

        glCompileShader(newFragmentShaderId);
        Util.checkGLError();

        // Print possible compile errors
        System.out.println("Fragment shader compilation:");
        printShaderLog(newFragmentShaderId);
        return newFragmentShaderId;

    }
    void CreateFragShaders() throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/"+this.getFragPath()).toString());

        FragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
        Util.checkGLError();

        glShaderSource(FragmentShaderId, FragmentShader);
        Util.checkGLError();

        glCompileShader(FragmentShaderId);
        Util.checkGLError();

        // Print possible compile errors
        System.out.println("Fragment shader compilation:");
        printShaderLog(FragmentShaderId);


    }

    public int CreateProgram(String vertexPath,String fragPath)throws Exception {
        int vertShaderId =this.CreateVertShaders(vertexPath);
        int fragShaderId = this.CreateFragShaders(fragPath);
        int programId = CreateProgram(vertShaderId,fragShaderId);
        return programId;

    }

    public void   CreateProgram(){
        ProgramId = glCreateProgram();
        Util.checkGLError();

        // Attach vertex shader
        glAttachShader(ProgramId, VertexShaderId);
        Util.checkGLError();

        // Attach fragment shader
        glAttachShader(ProgramId, FragmentShaderId);
        Util.checkGLError();

        // We tell the program how the vertex attribute indices will map
        // to named "in" variables in the vertex shader. This must be done
        // before compiling.
        // glBindAttribLocation(ProgramId, POSITION_INDEX, "in_Position");
        // glBindAttribLocation(ProgramId, COLOR_INDEX, "in_Color");

        glLinkProgram(ProgramId);
        Util.checkGLError();

        // Print possible compile errors
        System.out.println("Program linking:");
        printProgramLog(ProgramId);


        //System.out.println("transformLoc:"+transformLoc);
        Util.checkGLError();
        //glUseProgram(ProgramId);
        // Util.checkGLError();


    }
    public int CreateProgram(int vertexShaderId, int fragmentShaderId){
        int newProgramId = glCreateProgram();
        Util.checkGLError();

        // Attach vertex shader
        glAttachShader(newProgramId, vertexShaderId);
        Util.checkGLError();

        // Attach fragment shader
        glAttachShader(newProgramId, fragmentShaderId);
        Util.checkGLError();

        // We tell the program how the vertex attribute indices will map
        // to named "in" variables in the vertex shader. This must be done
        // before compiling.
        // glBindAttribLocation(ProgramId, POSITION_INDEX, "in_Position");
        // glBindAttribLocation(ProgramId, COLOR_INDEX, "in_Color");

        glLinkProgram(newProgramId);
        Util.checkGLError();

        // Print possible compile errors
        System.out.println("Program linking:");
        printProgramLog(newProgramId);


        //System.out.println("transformLoc:"+transformLoc);
        Util.checkGLError();
        //glUseProgram(ProgramId);
        // Util.checkGLError();
        return newProgramId;


    }

    public  void CreateTexture(){



        GLImage textureImg;
        try {

            textureImg = GLApp.loadImage(PathManager.getInstance().getHomePath().resolve("assets/images/items.png").toUri());//
            //Image image=        ImageIO.read(new File(installPath.resolve(textureImagePath).toUri()));
            if (textureImg != null) {

                textureHandle=GL11.glGenTextures();

                GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
                // set texture parameters
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); //GL11.GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); //GL11.GL_NEAREST);


                // textureImg.textureHandle = GLApp.makeTexture(textureImg);

                GL11.glTexImage2D(GL11.GL_TEXTURE_2D,
                        0, 						// level of detail
                        GL11.GL_RGBA8,			// internal format for texture is RGB with Alpha
                        textureImg.w, textureImg.h, 					// size of texture image
                        0,						// no border
                        GL11.GL_RGBA, 			// incoming pixel format: 4 bytes in RGBA order
                        GL11.GL_UNSIGNED_BYTE,	// incoming pixel data type: unsigned bytes
                        textureImg.pixelBuffer);

                // GLApp.makeTextureMipMap(textureHandle, textureImg);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Print log of program object
     *
     * @param programId
     */
    public static void printProgramLog(int programId) {
        int logLength = glGetProgram(programId, GL_INFO_LOG_LENGTH);
        Util.checkGLError();

        System.out.println("  Log (length " + logLength + " chars)");
        String log = glGetProgramInfoLog(programId, logLength);
        Util.checkGLError();
        for (String line : log.split("\n")) {
            System.out.println("  " + line);
        }
        System.out.println("");
    }







    /**
     * Print log of shader object.
     *
     * @param id
     */
    public static void printShaderLog(int id) {
        int logLength = glGetShader(id, GL_INFO_LOG_LENGTH);
        Util.checkGLError();

        System.out.println("  Log (length " + logLength + " chars)");
        String log = glGetShaderInfoLog(id, logLength);
        Util.checkGLError();
        for (String line : log.split("\n")) {
            System.out.println("  " + line);
        }
        System.out.println("");
    }

    public String readShaderSourceCode(String filePath) throws IOException {
        return  FileUtil.readFile2Str(filePath);


    }

    void DestroyShaders() {

        glUseProgram(0);

        glDetachShader(ProgramId, VertexShaderId);
        glDetachShader(ProgramId, FragmentShaderId);

        glDeleteShader(FragmentShaderId);
        glDeleteShader(VertexShaderId);

        glDeleteProgram(ProgramId);

        Util.checkGLError();
    }
    void DestroyVBO() {

        glDisableVertexAttribArray(0);
        Util.checkGLError();

       /* glDisableVertexAttribArray(COLOR_INDEX);
        Util.checkGLError();*/

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        Util.checkGLError();



        glDeleteBuffers(VboId);
        Util.checkGLError();



        glBindVertexArray(0);
        Util.checkGLError();

    }
    void Cleanup() {
        DestroyShaders();
        DestroyVBO();
    }
    public void cameraPosChangeListener(GL_Vector cameraPos){
        GL_Matrix view=
                GL_Matrix.LookAt(cameraPos,viewDir);



        int viewLoc= glGetUniformLocation(ProgramId,"view");


        glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
    }

    public void processKeyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            cameraPos=GL_Vector.add(cameraPos, GL_Vector.multiplyWithoutY(viewDir,
                    -0.1f));
           cameraPosChangeListener(cameraPos);

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            cameraPos=GL_Vector.add(cameraPos, GL_Vector.multiplyWithoutY(viewDir,
                    0.1f));
            cameraPosChangeListener(cameraPos);

        }


                if (Keyboard.isKeyDown( Keyboard.KEY_A) ){

                    GL_Vector right = GL_Vector.crossProduct(viewDir,up);
                    cameraPos=GL_Vector.add(cameraPos, GL_Vector.multiplyWithoutY(right,
                            -0.1f));
                    cameraPosChangeListener(cameraPos);

                }
                if (Keyboard.isKeyDown(  Keyboard.KEY_D)) {

                    GL_Vector right = GL_Vector.crossProduct(viewDir,up);
                    cameraPos=GL_Vector.add(cameraPos, GL_Vector.multiplyWithoutY(right,
                            0.1f));
                    cameraPosChangeListener(cameraPos);

                }

                if ( Keyboard.isKeyDown( Keyboard.KEY_Q)) {

                    GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(5)/5,
                            0);
                    viewDir = M.transform(viewDir);

                    cameraPosChangeListener(cameraPos);

                }


                if (Keyboard.isKeyDown( Keyboard.KEY_E)) {

                    GL_Matrix M = GL_Matrix.rotateMatrix(0, (float) Math.toRadians(-5)/5,
                            0);
                    viewDir = M.transform(viewDir);


                    cameraPosChangeListener(cameraPos);

                }

                // pass key event to handler
                //System.out.println("Character"+Keyboard.getEventCharacter());

                // pass key event to handler
                //LogUtil.println("Character"+Keyboard.getEventCharacter());



    }

    public void processMouse() {
    }
    public void update(){

    }
    public void destroy() {
        Cleanup();
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }
    public void run() throws LWJGLException {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (Display.isVisible()) {
                processKeyboard();
                processMouse();
                update();
                render();
            } else {
                if (Display.isDirty()) {
                    render();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }

            Display.update();
            Display.sync(60);
        }
    }
    public void render()throws LWJGLException{

    }
}
