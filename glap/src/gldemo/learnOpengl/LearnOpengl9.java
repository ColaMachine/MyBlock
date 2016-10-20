package gldemo.learnOpengl;

import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.utilities.concurrency.LWJGLHelper;
import glapp.GLApp;
import glapp.GLImage;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import javax.vecmath.Point4f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by dozen.zhang on 2016/10/11.
 */
public class LearnOpengl9 {

    int VboId;
    int VertexShaderId;
   FloatBuffer Vertices ;

    int FragmentShaderId;
    public static final int DISPLAY_HEIGHT = 600; // window width
    public static final int DISPLAY_WIDTH = 600; // window height
    public static final int POSITION_INDEX = 0; // index of vertex attribute "in_Position"
    public static final int COLOR_INDEX = 1; // index of vertex attribute "in_Color"
    public static final int FLOAT_NUM_BYTES; // sizeof(float) in bytes
    public static final int INT_NUM_BYTES; // sizeof(int) in bytes
    public static final int VEC4_BYTES; // sizeof(vec4) in bytes

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
            Display.create(pixelFormat, contextAtrributes);

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
    FloatBuffer matrixBuffer;
    public void CreateMatrix(){
        GL_Matrix matrix ;

        GL_Matrix matrix1= GL_Matrix.rotateMatrix(0,0,45);
        GL_Matrix matrix2=GL_Matrix.translateMatrix(0.5f,0.5f,0.5f);
        matrix= GL_Matrix.multiply(matrix2,matrix1);
       // matrix=matrix1;
         matrixBuffer =BufferUtils.createFloatBuffer(16);
        float[][] arr= matrix.exportToArray();
       /* matrixBuffer.put(1).put(0).put(0).put(0)
                .put(0).put(1).put(0).put(0).
                put(0).put(0).put(1).put(0).
                put(0).put(0).put(0).put(1);*/
        for(int i = 0;i<4;i++){//数据居然要从上往下算第一列 再第二列传数据
            for(int j=0;j<4;j++){
            matrixBuffer.put(arr[j][i]);
            }

        }
        matrixBuffer.rewind();
    }


    int textureHandle = 0;
    public  void CreateTexture(){



        GLImage textureImg;
        try {

            textureImg = GLApp.loadImage(PathManager.getInstance().getHomePath().resolve("images/items.png").toUri());//
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
    int VaoId;



    //创建顶点着色器
    void CreateVertShaders() throws IOException {



        //顶点着色器
        //编译顶点着色器

        //GLSL opengl shader language


        // Put the shaders "chap2.vert" and "chap2.frag" in the working
        // directory ...

        //==========================================================
        // Load and compile vertex shader

        String VertexShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/chapt9.vert").toString());
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
    void CreateFragShaders() throws IOException {
        String FragmentShader = readShaderSourceCode( PathManager.getInstance().getInstallPath().resolve("src/gldemo/learnOpengl/chapt8.frag").toString());

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


        //System.out.println("transformLoc:"+transformLoc);
        Util.checkGLError();
        //glUseProgram(ProgramId);
       // Util.checkGLError();



    }
    int eboId;

    void CreateVBO(){
        VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();

        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        float z=0f;
        float multi=1;
        float VerticesArray[]= {0.5f*multi, 0.5f*multi,z, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top Right
                0.5f*multi, -0.5f*multi, z, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // Bottom Right
                -0.5f*multi, -0.5f*multi,z, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom Left
                -0.5f*multi, 0.5f*multi, z, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Top Left
        };
        GL_Matrix model= GL_Matrix.rotateMatrix((float)(45*3.14/180.0),0,0);

        GL_Matrix view= GL_Matrix.translateMatrix(0,0,3);
        GL_Matrix projection= GL_Matrix.perspective(45,600/600,1f,1000.0f);
        for(int i=0;i<4;i++){
            GL_Vector neVector =new GL_Vector(VerticesArray[0+i*8],VerticesArray[1+i*8],VerticesArray[2+i*8]);
            //Point4f
            GL_Vector vector2 =  GL_Matrix.multiply(model,neVector);
            GL_Vector vector3 =  GL_Matrix.multiply(view,vector2);
            GL_Vector vector =  GL_Matrix.multiply(vector3,projection);
            vector.x=vector.x/vector3.z;
            vector.y=vector.y/vector3.z;
            vector.z=-1*vector.z/vector3.z;
        /*    GL_Vector vector =  GL_Matrix.multiply(neVector,projection);
            vector.x=vector.x/neVector.z;
            vector.y=vector.y/neVector.z;
            vector.z=-1*vector.z/neVector.z;
               vector =  GL_Matrix.multiply(model,vector);
             vector =  GL_Matrix.multiply(view,vector);*/



            if(vector.x>1 ||vector. x<-1  || vector.y<-1 ||vector. y>1 || vector.z>1 || vector.z<-1){
                System.out.print("x:"+VerticesArray[0+i*8]+"y:"+VerticesArray[1+i*8]+"z:"+VerticesArray[2+i*8] +" ====> "+vector+"\n");
            }
            VerticesArray[0+i*8]= vector.x;
            VerticesArray[1+i*8]= vector.y;
            VerticesArray[2+i*8]=vector.z;

        }
        Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
        Vertices.put(VerticesArray);
        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
        VboId=glGenBuffers();//create vbo


        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data

        int[] indices={
                0,1,3,
                1,2,3
        };
        IntBuffer Indices = BufferUtils.createIntBuffer(indices.length);
        Indices.put(indices);
        Indices.rewind();

        eboId = glGenBuffers();
        Util.checkGLError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Indices,GL_STATIC_DRAW);

       // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8* 4, 3*4);

        Util.checkGLError();
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8* 4, 6*4);

        Util.checkGLError();
        glEnableVertexAttribArray(2);

        Util.checkGLError();
        glBindVertexArray(0);
        Util.checkGLError();

        glUseProgram(ProgramId);


        int projectionLoc= glGetUniformLocation(ProgramId,"projection");
        int modelLoc= glGetUniformLocation(ProgramId,"model");
        int viewLoc= glGetUniformLocation(ProgramId,"view");
       // projectionLoc=0;
        //modelLoc=1;
       // viewLoc=2;
      //  GL_Matrix model= GL_Matrix.rotateMatrix((float)(45.0*3.14/180.0),0,0);

       // GL_Matrix view= GL_Matrix.translateMatrix(0,0,0);
       // GL_Matrix projection= GL_Matrix.perspective(90,600/600,1f,1000.0f);
       //GL_Matrix finalMatrix =  GL_Matrix.multiply(GL_Matrix.multiply(projection,view),model);
       // GL_Vector vector = finalMatrix.multiply(projection,new GL_Vector(0f,4f,-4f));
      /*  GL_Vector vector = GL_Matrix.multiply(new GL_Vector(0f,4f,4f),projection);
        vector.x=vector.x/4;
        vector.y=vector.y/4;
        vector.z=vector.z/4;*/
        glUniformMatrix4(modelLoc,  false,model.toFloatBuffer() );
       //glUniformMatrix4(modelLoc,  false,GL_Matrix.rotateMatrix((float)(45.0*3.14/180.0),0,0).toFloatBuffer() );
       // glUniformMatrix4(modelLoc,  false,GL_Matrix.multiply(GL_Matrix.translateMatrix(0.0f,0f,-100),GL_Matrix.rotateMatrix((float)(45*3.14/180),0,0)).toFloatBuffer() );
       glUniformMatrix4(viewLoc,  false,view.toFloatBuffer() );
      //glUniformMatrix4(viewLoc,  false,GL_Matrix.translateMatrix(0.0f,0f,-1.2f).toFloatBuffer() );
       //glUniformMatrix4(projectionLoc,  false,GL_Matrix.perspective2(45.0f,600/600,-0.1f,-100.0f).toReverseFloatBuffer() );

     glUniformMatrix4(projectionLoc,  false,projection.toFloatBuffer() );
      /*  <Matrix:
        1.0007966,0.0,0.0,0.0,
                0.0,1.0007966,0.0,0.0,
                0.0,0.0,-1.101001,-1.0,
                0.0,0.0,-0.2002002,0.0>
*/
        // matrixBuffer.rewind();
        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
       // Util.checkGLError();


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


        Long time =System.currentTimeMillis();
        float greenValue = (float)(Math.sin(time.doubleValue())/2+0.5);

        glUseProgram(this.ProgramId);

        int projectionLoc= glGetUniformLocation(ProgramId,"projection");
        int modelLoc= glGetUniformLocation(ProgramId,"model");
        int viewLoc= glGetUniformLocation(ProgramId,"view");
        // projectionLoc=0;
        //modelLoc=1;
        // viewLoc=2;
       // glUniformMatrix4(modelLoc,  false,new GL_Matrix().toFloatBuffer() );
        glUniformMatrix4(modelLoc,  false,GL_Matrix.rotateMatrix((float)(45.0*3.14/180.0),0,0).toFloatBuffer() );
        // glUniformMatrix4(modelLoc,  false,GL_Matrix.multiply(GL_Matrix.translateMatrix(0.0f,0f,-100),GL_Matrix.rotateMatrix((float)(45*3.14/180),0,0)).toFloatBuffer() );
        //glUniformMatrix4(viewLoc,  false,new GL_Matrix().toFloatBuffer() );
        glUniformMatrix4(viewLoc,  false,GL_Matrix.translateMatrix(0.0f,0f,-3f).toFloatBuffer() );
        //glUniformMatrix4(projectionLoc,  false,GL_Matrix.perspective2(45.0f,600/600,-0.1f,-100.0f).toReverseFloatBuffer() );

        glUniformMatrix4(projectionLoc,  false,GL_Matrix.perspective3(90.0f,600/600,0.1f,100.0f).toFloatBuffer() );

       /* int transformLoc= glGetUniformLocation(ProgramId,"transform");
        glUniformMatrix4(0,  false,matrixBuffer );
        matrixBuffer.rewind();*/
        glBindTexture(GL_TEXTURE_2D, this.textureHandle);
        glBindVertexArray(VaoId);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);

        Util.checkGLError();
    }


    public static void main(String[] args) {
        LWJGLHelper.initNativeLibs();//加载lib包
        LearnOpengl9 main = null;
        try {
            main = new LearnOpengl9();
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
