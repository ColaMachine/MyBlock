package gldemo.learnOpengl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Util;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

/**
 * Created by dozen.zhang on 2016/10/11.
 */
public class LearnOpengl5 {

    int VboId;
    int VertexShaderId;
    FloatBuffer Vertices ;

    int FragmentShaderId;
    public static final int DISPLAY_HEIGHT = 600; // window width
    public static final int DISPLAY_WIDTH = 600; // window height
    public static final int POSITION_INDEX = 0; // index of vertex attribute "in_Position"
    public static final int COLOR_INDEX = 1; // index of vertex attribute "in_Color"


    public void create() throws LWJGLException, Exception {

        DisplayMode d = new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        Display.setDisplayMode(d);
        Display.setFullscreen(false);
        Display.setTitle("Shaders and buffers in LWJGL");
        Display.create();

        //Keyboard
        Keyboard.create();

        //Mouse
        Mouse.setGrabbed(false);
        Mouse.create();

        //OpenGL
        initGL();
        resizeGL();
    }
    public void resizeGL() {
        glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }

    public void initGL() throws IOException {

        glClearColor(0, 0, 0, 0);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);

        CreateVertShaders();
        CreateColorShaders();
        CreateProgram();
        CreateVBO();
    }
    void CreateVBO(){

        glVertexAttribPointer(0,3,GL_FLOAT,false,3*4,0);

        glEnableVertexAttribArray(0);
    }

    int ProgramId;
    void CreateProgram(){
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

        Util.checkGLError();
        glUseProgram(ProgramId);
        Util.checkGLError();

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




    //创建顶点着色器
    void CreateVertShaders() throws IOException {

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float VerticesArray[]= {-0.5f,-0.5f,0.0f,
                0.5f,-0,5f,0f,
                0.0f,0.5f,0.0f
        };
        Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo

        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        //顶点着色器
        //编译顶点着色器

        //GLSL opengl shader language


        // Put the shaders "chap2.vert" and "chap2.frag" in the working
        // directory ...

        //==========================================================
        // Load and compile vertex shader

        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/chapt5.vert").toString());
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
    void CreateColorShaders() throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/chapt5.frag").toString());

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
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String str="";
        String content ="";
        while((str = reader.readLine()) !=null){
            content+= str+"\n";
        }
        return content;

    }

    public void render() throws LWJGLException {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Util.checkGLError();

        // Use the vertex array object. This will automatically cause OpenGL
        // to read our vertex position/color data and the indices data.
        glBindVertexArray(VboId);

        // The glDrawElements function will the vertex attribute object
        // that we have bound.
        glDrawElements(
                GL_TRIANGLES,
                3, // = use all 48 indices from the indices buffer
                GL_UNSIGNED_INT,
                0);
        Util.checkGLError();
    }


    public static void main(String[] args) {
        LWJGLHelper.initNativeLibs();
        LearnOpengl5 main = null;
        try {
            main = new LearnOpengl5();
            main.create();
            main.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (main != null) {
                main.destroy();
            }
        }
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

        glDisableVertexAttribArray(POSITION_INDEX);
        Util.checkGLError();

        glDisableVertexAttribArray(COLOR_INDEX);
        Util.checkGLError();

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
    public void processKeyboard() {
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
}
