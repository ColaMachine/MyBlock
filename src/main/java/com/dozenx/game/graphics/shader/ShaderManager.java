package com.dozenx.game.graphics.shader;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.modes.GamingState;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import com.dozenx.game.opengl.util.OpenglUtils;
import com.dozenx.game.opengl.util.ShaderConfig;
import com.dozenx.game.opengl.util.ShaderUtils;
import com.dozenx.game.opengl.util.Vao;
import com.dozenx.util.FloatBufferWrap;
import com.dozenx.util.StringUtil;
import core.log.LogUtil;
import glmodel.GL_Matrix;
import glmodel.GL_Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Util;

import javax.vecmath.Point4f;
import javax.vecmath.Vector2f;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by luying on 16/11/14.
 */
public class ShaderManager {


    //Vao terrainVao = new Vao();

    /*public  int terrainProgramId;
    public  int VaoId;
    public  int VboId;
    public  int viewPosLoc;
    public  int LightProgramId;
    public  int viewLoc;//glGetUniformLocation(ProgramId,"view");
    public  int lightViewLoc;// glGetUniformLocation(LightProgramId,"view");
    */

    /*int lightColorLoc;
   int lightPosInTerrainLoc;
   int lightPosLoc;*/

    public ShaderManager() {
        LogUtil.println("hello");
    }

    public static ShaderManager instance;

    public static ShaderManager getInstance() {
        if (instance == null) {
            instance = new ShaderManager();
            return instance;
        }
        return instance;
    }

    //地形
    public static ShaderConfig terrainShaderConfig = null;



    static {
        if (Constants.SHADOW_ENABLE) {
            OpenglUtils.checkGLError();
            terrainShaderConfig = new ShaderConfig("terrain", "boxwithshadow.frag", "boxwithshadow.vert",new int[]{3,3,3,1});
            //terrainShaderConfig = new ShaderConfig("multilightwithshadow", "multilightwithshadow.frag", "multilightwithshadow.vert", new int[]{3, 3, 3, 1});
        } else {
             terrainShaderConfig = new ShaderConfig("terrain", "box.frag", "box.vert",new int[]{3,3,3,1});

            //terrainShaderConfig = new ShaderConfig("terrain", "multilight.frag", "multilight.vert", new int[]{3, 3, 3, 1});
            //terrainShaderConfig = new ShaderConfig("terrain", "boxangle.frag", "boxangle.vert");
        }
    }
    //public static ShaderConfig terrainShaderConfig = new ShaderConfig("terrain", "box.frag", "box.vert");

    // public static ShaderConfig terrainShaderConfig = new ShaderConfig("terrain", "boxwithshadow.frag", "boxwithshadow.vert");
    //灯光方块
    public static ShaderConfig lightShaderConfig = new ShaderConfig("light", "light.frag", "light.vert", new int[]{3, 3});
    //天空盒子
    public static ShaderConfig skyShaderConfig = new ShaderConfig("sky", "light.frag", "light.vert", new int[]{3, 3});
    //ui
    public static ShaderConfig uiShaderConfig = new ShaderConfig("ui", "2dimg.frag", "2dimg.vert", new int[]{3, 2, 1, 4});
    //攻击物体
    public static ShaderConfig attackShaderConfig = new ShaderConfig("attack", "2dimg.frag", "2dimg.vert", new int[]{3, 3, 1, 4});
    //生物
    public static ShaderConfig livingThingShaderConfig = new ShaderConfig("living", "box.frag", "box.vert", new int[]{3, 3, 3, 1});
    //扔在地上的东关系
    public static ShaderConfig anotherShaderConfig = new ShaderConfig("another", "3dimg.frag", "3dimg.vert", new int[]{3, 3, 3, 1});

//现在的流程shadow==> shaderGeometryPass==>   shaderLightPass  ==>ssao ==> ssao blur
    //扔在地上的东西
    public static ShaderConfig dropItemShaderConfig = new ShaderConfig("drop", "3dimg.frag", "3dimg.vert", new int[]{3, 3, 3, 1});
    //伤害字符
    public static ShaderConfig uifloatShaderConfig = new ShaderConfig("ui", "2dimg.frag", "2dimg.vert", new int[]{3, 2, 1, 4});
    //阴影
    public static ShaderConfig shadowShaderConfig = new ShaderConfig("shadow", "shadow.frag", "shadow.vert", new int[]{3, 3, 3, 1});
    //hdr
    public static ShaderConfig hdrShaderConfig = new ShaderConfig("hdr", "hdr.frag", "hdr.vert", new int[]{2, 2});
    //bloom
    public static ShaderConfig bloomShaderConfig = new ShaderConfig("bloom", "bloom.frag", "bloom.vert", new int[]{2, 2});
    //gaosi
    public static ShaderConfig gaosiShaderConfig = new ShaderConfig("gaosi", "gaosi.frag", "gaosi.vert", new int[]{2, 2});

    //gaosihebing
    public static ShaderConfig gaosihebingShaderConfig = new ShaderConfig("gaosihebing", "gaosihebing.frag", "gaosihebing.vert", new int[]{2, 2});

    //灯光方块
    public static ShaderConfig lineShaderConfig = new ShaderConfig("line", "line.frag", "line.vert", new int[]{3});
    public HashMap<String, ShaderConfig> configMap = new HashMap<>();


    //ssao 进行屏幕渲染的时候 把纹理的坐标位置 法线 颜色 深度 进行缓存 如果要进行环境光遮蔽 那么输出的坐标是法线都是 视线空间的
    public static ShaderConfig shaderGeometryPass = new ShaderConfig("shaderGeometryPass", "gbuffer.frag", "gbuffer.vert", new int[]{3, 3, 3, 1});

    static {


        if(Constants.SSAO_ENABLE)

        {

            if (Constants.SHADOW_ENABLE)

            {
                shaderGeometryPass = new ShaderConfig("shaderGeometryPass", "ssao_gbuffer_shadow.frag", "ssao_gbuffer_shadow.vert", new int[]{3, 3, 3, 1});

            }else{
                shaderGeometryPass = new ShaderConfig("shaderGeometryPass", "ssao_gbuffer.frag", "ssao_gbuffer.vert", new int[]{3, 3, 3, 1});

            }
        }

    }
    public static ShaderConfig shaderLightingPass =null;

    static {


    if(Constants.SSAO_ENABLE)

    {
         shaderLightingPass = new ShaderConfig("shaderLightingPass", "ssao_lighting.frag", "ssao.vert", new int[]{3, 2});
    }else{
        shaderLightingPass = new ShaderConfig("shaderLightingPass", "deferred_shading.frag", "deferred_shading.vert", new int[]{3, 2});
    }

}
    public static ShaderConfig shaderSSAO = new ShaderConfig("ssao",  "ssao.frag","ssao.vert",new int[]{3,2});

    public static ShaderConfig shaderSSAOBlur = new ShaderConfig("ssaoblur", "ssao_blur.frag","ssao.vert", new int[]{3,2});


    //public static Vao blockVao = new Vao(ShaderManager.anotherShaderConfig);
    //透视矩阵
   public static GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
    //相机
    FloatBuffer cameraViewBuffer = BufferUtils.createFloatBuffer(16);
    public Hdr hdr;
    public Bloom bloom ;
    public static  Shadow shadow;//阴影
    
    public DelayBuffer delay;//延迟

    public SSAOBuffer ssao;//环境光遮蔽
    public void registerConfig(ShaderConfig config) throws Exception {
        if (StringUtil.isBlank(config.getName())) {
            throw new Exception("not allow shader config has no name!");
        }
        configMap.put(config.getName(), config);

    }



    public void init() {

      //  bloom.init();

        //公用的阴影
        if (Constants.SHADOW_ENABLE) {
            shadow =new Shadow();
            //shadowInit();
            this.createProgram(shadowShaderConfig);
            this.initUniform(shadowShaderConfig);


            shadow.lightSpaceMatrixId= glGetUniformLocation(terrainShaderConfig.getProgramId(), "lightSpaceMatrix");

        }
        
        

        //这里需要initHdr产生的hdrFBO
        //initBloom();
        //Vao terrainVao = new Vao();
        this.createProgram(terrainShaderConfig);
        //terrainShaderConfig.getVao().setVertices(BufferUtils.createFloatBuffer(902400));
        this.createProgram(lightShaderConfig);
        this.createProgram(skyShaderConfig);
        this.createProgram(uiShaderConfig);
        this.createProgram(livingThingShaderConfig);
        this.createProgram(anotherShaderConfig);
        this.createProgram(dropItemShaderConfig);
        this.createProgram(uifloatShaderConfig);
        this.createProgram(lineShaderConfig);  
        
       
        
        OpenglUtils.checkGLError();



        // this.CreateTerrainVAO();
        // ShaderUtils.initProModelView(terrainShaderConfig);
        //ShaderUtils.initProModelView(lightShaderConfig);
        //ShaderUtils.initProModelView(livingThingShaderConfig);

        this.initUniform(terrainShaderConfig);


        this.initUniform(lightShaderConfig);
        this.initUniform(livingThingShaderConfig);
        this.initUniform(uiShaderConfig);
        this.initUniform(skyShaderConfig);
        this.initUniform(anotherShaderConfig);
        this.initUniform(dropItemShaderConfig);
        this.initUniform(uifloatShaderConfig);
        this.initUniform(lineShaderConfig);
        if(Constants.DELAY_ENABLE){
            this.createProgram(shaderGeometryPass);  
            this.initUniform(shaderGeometryPass);
            
            this.createProgram(shaderLightingPass);  
            this.initUniform(shaderLightingPass);
           
            
           
            
            delay = new DelayBuffer();

            if(Constants.SSAO_ENABLE){
                this.createProgram(shaderSSAO);
                this.initUniform(shaderSSAO);

                this.createProgram(shaderSSAOBlur);
                this.initUniform(shaderSSAOBlur);
                ssao =new SSAOBuffer();
            }
        }
       
        // this.initUniform(shadowShaderConfig);

        //this.createProgram(lightShaderConfig);
        this.CreateLightVAO(lightShaderConfig);
        // this.CreateUiVAO(uiShaderConfig);
        //this.CreateTerrainVAO(terrainShaderConfig);
        //  this.CreateLivingVAO(livingThingShaderConfig);
        //this.uniformLight();

        //;
      
            
        if (Constants.HDR_ENABLE) {
            hdr =new Hdr();
            this.createProgram(hdrShaderConfig);
            this.initUniform(hdrShaderConfig);
        }
        if(Constants.GAOSI_ENABLE) {
            this.createProgram(gaosiShaderConfig);
            this.createProgram(bloomShaderConfig);
            this.createProgram(gaosihebingShaderConfig);
            this.initUniform(gaosiShaderConfig);
            this.initUniform(bloomShaderConfig);
            this.initUniform(gaosihebingShaderConfig);
            bloom = new Bloom();
            bloom.initVaoAndBindTexture(this);


        }


    }


//    public void initBloom1(){
//        //创建hdrFBo帧缓冲
//       // int hdrFBO = glGenFramebuffers();
//
//        //hdrFBO还是空的
//        glBindFramebuffer(GL_FRAMEBUFFER, bloom.lightFBO); OpenglUtils.checkGLError();
////        int colorTexture0= glGenTextures();
////        int colorTexture1= glGenTextures();
//        int colorTextureAry[] = {glGenTextures(),glGenTextures()}; OpenglUtils.checkGLError();
//
//        for(int i=0;i<2;i++){
//            glBindTexture(GL_TEXTURE_2D, colorTextureAry[i]); OpenglUtils.checkGLError();
//
//            glTexImage2D(
//                    GL_TEXTURE_2D, 0, GL_RGB16F, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, GL_RGB, GL_FLOAT, (ByteBuffer)null
//            ); OpenglUtils.checkGLError();
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);OpenglUtils.checkGLError();
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);OpenglUtils.checkGLError();
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);OpenglUtils.checkGLError();
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);OpenglUtils.checkGLError();
//            // 帧缓冲连接上纹理
//            glFramebufferTexture2D(
//                    GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, colorTextureAry[i], 0
//            ); OpenglUtils.checkGLError();
//
//        }
//
//        int attachments[]={GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1};
//         bloomAttachments = BufferUtils.createIntBuffer(2);//
//        bloomAttachments.put(GL_COLOR_ATTACHMENT0).put(GL_COLOR_ATTACHMENT1);
//        bloomAttachments.rewind();
//        GL20.glDrawBuffers( bloomAttachments);OpenglUtils.checkGLError();
//        //glDrawBuffers(intBuffer);
//        glBindFramebuffer(GL_FRAMEBUFFER, 0); OpenglUtils.checkGLError();
//    }
    //存放2 3 附件
    //public IntBuffer bloomAttachments;




    //应该删除了
//    public void CreateTerrainVAO1() {
//
//
//        int VaoId = glGenVertexArrays();
//        OpenglUtils.checkGLError();
//
//        glBindVertexArray(VaoId);
//        OpenglUtils.checkGLError();
//        this.CreateTerrainVBO123();
//
//        glBindVertexArray(0);
//        OpenglUtils.checkGLError();
//    }

//    public void CreateTerrainVBO123() {
//        //顶点 vbo
//        //create vbo 创建vbo  vertex buffer objects
//        float VerticesArray[] = {-0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
//                0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
//                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
//                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
//                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
//                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
//
//                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
//                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
//                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
//                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
//                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
//                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
//
//                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
//                -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
//                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
//                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
//                -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
//                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
//
//                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
//                0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
//                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
//                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
//                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
//                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
//
//                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
//                0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
//                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
//                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
//                -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
//                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
//
//                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
//                0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
//                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
//                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
//                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
//                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f};
//        FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);
//
//
//        Vertices.put(VerticesArray);
//        Vertices.rewind(); // rewind, otherwise LWJGL thinks our buffer is empty
//        int VboId = glGenBuffers();//create vbo
//
//
//        glBindBuffer(GL_ARRAY_BUFFER, VboId);//bind vbo
//        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data
//
//        // System.out.println("float.size:" + FlFLOAToat.SIZE);
//        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
//
//        OpenglUtils.checkGLError();
//        glEnableVertexAttribArray(0);
//        OpenglUtils.checkGLError();
//
//        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
//
//        OpenglUtils.checkGLError();
//        glEnableVertexAttribArray(1);
//        OpenglUtils.checkGLError();
//
//
//    }


//    public Vector2f wordPositionToXY(GL_Vector position, GL_Vector cameraPosition, GL_Vector viewDir) {
//
//        GL_Matrix view =
//                GL_Matrix.LookAt(cameraPosition, viewDir);
//
//        GL_Matrix modal = GL_Matrix.translateMatrix(position.x, position.y, position.z);
//        GL_Matrix step1 = GL_Matrix.multiply(projection, view);
//        GL_Matrix step2 = GL_Matrix.multiply(step1, modal);
//        Point4f final4f = GL_Matrix.multiply(step2,
//
//                new javax.vecmath.Point4f(position.x, position.y, position.z, 0f)
//        );
//
//        return new Vector2f(final4f.x, final4f.y);
//    }

    public static void humanPosChangeListener() {


       /* GamingState.instance.lightPos.x= GamingState.instance.player.position.x;
        GamingState.instance.lightPos.y= GamingState.instance.player.position.y+30;
        GamingState.instance.lightPos.z= GamingState.instance.player.position.z;*/

        /*glUseProgram(lightShaderConfig.getProgramId());

        GL_Matrix model = GL_Matrix.translateMatrix( GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        glUniformMatrix4(lightShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();


        glUseProgram(skyShaderConfig.getProgramId());


        glUniformMatrix4(skyShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();
        lightPosChangeListener();*/

    }


    public  void lightPosChangeListener() {
//        GamingState.instance.lightPos.x=1;
//        GamingState.instance.lightPos.y=10;
//        GamingState.instance.lightPos.z=0;

//        float near_plane = 1.0f, far_plane = 107.5f;
//        GL_Matrix ortho = GL_Matrix.ortho(-10.0f, 10.0f, -10.0f, 10.0f, near_plane, far_plane);



       // GL_Matrix lightViewMatrix =  ShaderManager.getInstance().shadow.lightViewMatrix ;

        // if(!Constants.SHADOW_ENABLE) {
        //影响所有的地形里的灯光位置
        glUseProgram(terrainShaderConfig.getProgramId());
        OpenglUtils.checkGLError();
        glUniform3f(terrainShaderConfig.getLightPosLoc(), GamingState.instance.lightPos.x, GamingState.instance.lightPos.y, GamingState.instance.lightPos.z);
        OpenglUtils.checkGLError();
        if(Constants.SHADOW_ENABLE) {
            glUniformMatrix4(terrainShaderConfig.getShadowLightViewLoc(), false, shadow.getLightViewMatrix().toFloatBuffer());
        }

//        int lightSpaceMatrixLoc = glGetUniformLocation(terrainShaderConfig.getProgramId(), "lightSpaceMatrix");
//        //config.setProjLoc(projectionLoc);
//        OpenglUtils.checkGLError();
//        if (lightSpaceMatrixLoc >= 0) {
//            // GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
//            // FloatBuffer lightViewBuffer = BufferUtils.createFloatBuffer(16);
//            // OpenglUtils.checkGLError();
//            // //光源的视角
//            GL_Matrix lightSpaceMatrix =  shadow.adjustLightViewMatrix(GamingState.instance.lightPos,GamingState.instance.player.position);
//
//            terrainShaderConfig.setShadowLightViewLoc(lightSpaceMatrixLoc);
//            glUniformMatrix4(terrainShaderConfig.getShadowLightViewLoc(), false, shadow.getLightViewMatrix().toFloatBuffer());
//            //config.setViewLoc(viewLoc);
//            OpenglUtils.checkGLError();
//        }
        OpenglUtils.checkGLError();
        if(Constants.SHADOW_ENABLE && Constants.DELAY_ENABLE) {
            glUseProgram(shaderGeometryPass.getProgramId());
            glUniformMatrix4(shaderGeometryPass.getShadowLightViewLoc(), false, shadow.getLightViewMatrix().toFloatBuffer());

        }
        OpenglUtils.checkGLError();
        // }
        //影响实际的白色灯光方块的位置
        glUseProgram(lightShaderConfig.getProgramId());

        GL_Matrix model = GL_Matrix.translateMatrix(GamingState.instance.lightPos.x, GamingState.instance.lightPos.y, GamingState.instance.lightPos.z);
        glUniformMatrix4(lightShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();
        //========================shadow 的shader 里===============================
        //影响阴影渲染里的灯光位置

        if(shadowShaderConfig.getProgramId()>=0){
            glUseProgram(shadowShaderConfig.getProgramId());


        }
        OpenglUtils.checkGLError();
        if(Constants.SHADOW_ENABLE){
            shadow.setLightViewMatrix( GL_Matrix.multiply(ShaderUtils.ortho, GL_Matrix.LookAt(GamingState.instance.lightPos, new GL_Vector(-0.5f, -0.5f,0.5f).normalize())));
            shadowShaderConfig.use();;
            glUniformMatrix4(shadowShaderConfig.getShadowLightViewLoc(), false, shadow.getLightViewMatrix().toFloatBuffer());
        }

        //int lightSpaceMatrixLoc = glGetUniformLocation(shadowShaderConfig.getProgramId(), "lightSpaceMatrix");
        //config.setProjLoc(projectionLoc);
        OpenglUtils.checkGLError();
        //光源的视角
        if(Constants.SHADOW_ENABLE) {
            glUniformMatrix4(shadowShaderConfig.getShadowLightViewLoc(), false, shadow.getLightViewMatrix().toFloatBuffer());
        }

        //========================================gbuffer geometry ==============================
        OpenglUtils.checkGLError();
        if(Constants.SSAO_ENABLE) {shaderGeometryPass.use();
            shaderGeometryPass.setVec3("lightPos",GamingState.instance.lightPos);
            //glUniform3f(shaderGeometryPass.getLightPosLoc(), GamingState.instance.lightPos.x, GamingState.instance.lightPos.y, GamingState.instance.lightPos.z);
        }
        OpenglUtils.checkGLError();
        //hdr
//        glUseProgram(hdrShaderConfig.getProgramId());
//
//        GL_Matrix model = GL_Matrix.translateMatrix(GamingState.instance.lightPos.x, GamingState.instance.lightPos.y, GamingState.instance.lightPos.z);
//        glUniformMatrix4(hdrShaderConfig.getModelLoc(), false, model.toFloatBuffer());
//        OpenglUtils.checkGLError();

        // glUseProgram(livingThingShaderConfig.getProgramId());

        // glUniform3f(livingThingShaderConfig.getLightPosLoc(), GamingState.instance.lightPos.x,  GamingState.instance.lightPos.y,  GamingState.instance.lightPos.z);
        //OpenglUtils.checkGLError();
        /*
        glUseProgram(livingThingShaderConfig.getProgramId());


        glUniformMatrix4(livingThingShaderConfig.getModelLoc(), false, model.toFloatBuffer());
        OpenglUtils.checkGLError();*/
        GamingState.lightPosChanged=false;
        glUseProgram(0);
    }


    public void initUniform(ShaderConfig config) {
        LogUtil.println("begin initUniform:" + config.getName());
        /*
        uniform mat4 projection;
        uniform mat4 view;
        uniform mat4 model;
        uniform vec3 objectColor;物体的颜色
        uniform vec3 lightColor;灯光的颜色
        uniform vec3 lightPos;灯光的位置
        uniform vec3 viewPos;相机的位置*/


        //GL_Matrix view= GL_Matrix.translateMatrix(0,0,-3);


        //glUseProgram(ProgramId);
        int programId = config.getProgramId();  OpenglUtils.checkGLError();
        if(programId<=0){
            LogUtil.println("get error programid:"+config.getProgramId());
            System.exit(1);
        }
        glUseProgram(config.getProgramId());  OpenglUtils.checkGLError();
        ShaderUtils.printProgramLog(config.getProgramId());
        //unifrom赋值===========================================================
        //投影矩阵
        int projectionLoc = glGetUniformLocation(config.getProgramId(), "projection");
        if (projectionLoc >= 0) {
            config.setProjLoc(projectionLoc);
            OpenglUtils.checkGLError();

            glUniformMatrix4(projectionLoc, false, projection.toFloatBuffer());
            OpenglUtils.checkGLError();
        }
        //阴影灯光投影矩阵
        if(Constants.SHADOW_ENABLE) {
            int lightSpaceMatrixLoc = glGetUniformLocation(config.getProgramId(), "lightSpaceMatrix");
            //config.setProjLoc(projectionLoc);


            OpenglUtils.checkGLError();
            if (lightSpaceMatrixLoc >= 0) {
                // GL_Matrix projection = GL_Matrix.perspective3(45, (Constants.WINDOW_WIDTH) / (Constants.WINDOW_HEIGHT), 1f, 1000.0f);
                // FloatBuffer lightViewBuffer = BufferUtils.createFloatBuffer(16);
                // OpenglUtils.checkGLError();
                // //光源的视角
                config.setShadowLightViewLoc(lightSpaceMatrixLoc);
                glUniformMatrix4(lightSpaceMatrixLoc, false, shadow.getLightViewMatrix().toFloatBuffer());
                //config.setViewLoc(viewLoc);
                OpenglUtils.checkGLError();
            }
        }


        //相机位置
        GL_Matrix view;
        if (GamingState.instance != null)
            view =
                    GL_Matrix.LookAt(GamingState.instance.camera.Position, GamingState.instance.camera.ViewDir);
        else {
            view = GL_Matrix.LookAt(new GL_Vector(0, 0, 4), new GL_Vector(0, 0, -1));
        }
        view.fillFloatBuffer(cameraViewBuffer);
        int viewLoc = glGetUniformLocation(programId, "view");
        if (viewLoc >= 0) {
            config.setViewLoc(viewLoc);
            OpenglUtils.checkGLError();
            glUniformMatrix4(viewLoc, false, view.toFloatBuffer());
            OpenglUtils.checkGLError();
        }


        int modelLoc = glGetUniformLocation(programId, "model");
        if (modelLoc >= 0) {
            GL_Matrix model = GL_Matrix.rotateMatrix((float) (0 *  Constants.PI1), 0, 0);
            config.setModelLoc(modelLoc);
            OpenglUtils.checkGLError();
            glUniformMatrix4(modelLoc, false, model.toFloatBuffer());
            OpenglUtils.checkGLError();

        }
        /*viewLoc = glGetUniformLocation(terrainProgramId, "view");
        OpenglUtils.checkGLError();
        terrainShaderConfig.setViewLoc(viewLoc);*/

        //物体颜色
        int objectColorLoc = glGetUniformLocation(programId, "objectColor");
        if (objectColorLoc >= 0) {
            config.setObejctColorLoc(objectColorLoc);
            glUniform3f(objectColorLoc, 1.0f, 0.5f, 0.31f);
            OpenglUtils.checkGLError();
        }


        //环境光颜色

    /* int  lightColorLoc= glGetUniformLocation(programId, "light");
        if(lightColorLoc>0){
                config.setLightColorLoc(lightColorLoc);
            glUniform3f(lightPosInTerrainLoc, lightPos.x, lightPos.y, lightPos.z);
            OpenglUtils.checkGLError();
        }
        glUniform3f(lightColorLoc,1.0f,1f,1f);*/
        OpenglUtils.checkGLError();
        int lightPosInTerrainLoc = glGetUniformLocation(programId, "light.position");
        GL_Vector lightPos = new GL_Vector();OpenglUtils.checkGLError();
        if (GamingState.instance == null) {

        } else {
            lightPos = GamingState.instance.lightPos;
        }OpenglUtils.checkGLError();
        if (lightPosInTerrainLoc >= 0) {
            config.setLightPosLoc(lightPosInTerrainLoc);
            // LogUtil.println("light.position not found ");
            //System.exit(1);
            glUniform3f(lightPosInTerrainLoc, lightPos.x, lightPos.y, lightPos.z);
            OpenglUtils.checkGLError();
        }
        OpenglUtils.checkGLError();

        int viewPosLoc = glGetUniformLocation(programId, "viewPos");//camerapos

        if (viewPosLoc >= 0) {
            config.setViewPosLoc(viewPosLoc);
            glUniform3f(viewPosLoc, lightPos.x, lightPos.y, lightPos.z);
            OpenglUtils.checkGLError();
        }OpenglUtils.checkGLError();


        //int matAmbientLoc = glGetUniformLocation(ProgramId, "material.ambient");
        //int matDiffuseLoc = glGetUniformLocation(ProgramId, "material.diffuse");
        int matSpecularLoc = glGetUniformLocation(programId, "material.specular");
        if (matSpecularLoc >= 0) {
            glUniform3f(matSpecularLoc, 0.5f, 0.5f, 0.5f);
        }OpenglUtils.checkGLError();

        int matShineLoc = glGetUniformLocation(programId, "material.shininess");
        if (matShineLoc >= 0) {
            glUniform1f(matShineLoc, 32.0f);//光斑大小
        }OpenglUtils.checkGLError();
        //glUniform3f(matAmbientLoc, 1.0f, 0.5f, 0.31f);
        //glUniform3f(matDiffuseLoc, 1.0f, 0.5f, 0.31f);


        int lightMatAmbientLoc = glGetUniformLocation(programId, "light.ambient");
        if (lightMatAmbientLoc >= 0) {
            glUniform3f(lightMatAmbientLoc, 1f, 1f, 1f);
        }
        int lightMatDiffuseLoc = glGetUniformLocation(programId, "light.diffuse");
        if (lightMatDiffuseLoc >= 0) {
            glUniform3f(lightMatDiffuseLoc, 1f, 1f, 0.3f);
        }OpenglUtils.checkGLError();
        int lightMatSpecularLoc = glGetUniformLocation(programId, "light.specular");
        if (lightMatSpecularLoc >= 0) {
            glUniform3f(lightMatSpecularLoc,1f, 1f, 1f);
        }OpenglUtils.checkGLError();
        int lightMatShineLoc = glGetUniformLocation(programId, "light.shininess");
        if (lightMatSpecularLoc >= 0) {
            glUniform1f(lightMatShineLoc, 32.0f);
        }OpenglUtils.checkGLError();


        int lightConstantLoc = glGetUniformLocation(programId, "light.constant");
        if (lightConstantLoc >= 0) {
            glUniform1f(lightConstantLoc, 1f);
        }OpenglUtils.checkGLError();

        int lightLinearLoc = glGetUniformLocation(programId, "light.linear");
        if (lightLinearLoc >= 0) {
            glUniform1f(lightLinearLoc, 0.01f);
        }OpenglUtils.checkGLError();

        int lightQuadraticLoc = glGetUniformLocation(programId, "light.quadratic");
        if (lightQuadraticLoc >= 0) {
            glUniform1f(lightQuadraticLoc, 0.007f);
        }
        OpenglUtils.checkGLError();
     /*   for(int i=0;i<8;i++){
            int ourTexture0Loc = glGetUniformLocation(config.getProgramId(), "ourTexture"+i);
            if(ourTexture0Loc>0) {
                config.setTexture0Loc(ourTexture0Loc);
            }
        }*/
       
        OpenglUtils.checkGLError();
        int ourTexture0Loc = glGetUniformLocation(config.getProgramId(), "ourTexture0");
        if (ourTexture0Loc >= 0) {
            config.setTexture0Loc(ourTexture0Loc);
        }
        OpenglUtils.checkGLError();
        int ourTexture1Loc = glGetUniformLocation(config.getProgramId(), "ourTexture1");
        if (ourTexture1Loc >= 0) {
            config.setTexture1Loc(ourTexture1Loc);
        }OpenglUtils.checkGLError();
        int ourTexture2Loc = glGetUniformLocation(config.getProgramId(), "ourTexture2");
        if (ourTexture2Loc >= 0) {
            config.setTexture2Loc(ourTexture2Loc);
        }OpenglUtils.checkGLError();
        int ourTexture3Loc = glGetUniformLocation(config.getProgramId(), "ourTexture3");
        if (ourTexture3Loc >= 0) {
            config.setTexture3Loc(ourTexture3Loc);
        }OpenglUtils.checkGLError();
        int ourTexture4Loc = glGetUniformLocation(config.getProgramId(), "ourTexture4");
        if (ourTexture4Loc >= 0) {
            config.setTexture4Loc(ourTexture4Loc);
        }OpenglUtils.checkGLError();
        int ourTexture5Loc = glGetUniformLocation(config.getProgramId(), "ourTexture5");
        if (ourTexture5Loc >= 0) {
            config.setTexture5Loc(ourTexture5Loc);
        }OpenglUtils.checkGLError();
        int ourTexture6Loc = glGetUniformLocation(config.getProgramId(), "ourTexture6");
        if (ourTexture6Loc >= 0) {
            config.setTexture6Loc(ourTexture6Loc);
        }OpenglUtils.checkGLError();
        int ourTexture7Loc = glGetUniformLocation(config.getProgramId(), "ourTexture7");
        if (ourTexture7Loc >= 0) {
            config.setTexture7Loc(ourTexture7Loc);
        }OpenglUtils.checkGLError();
        int ourTexture8Loc = glGetUniformLocation(config.getProgramId(), "ourTexture8");
        if (ourTexture8Loc >= 0) {
            config.setTexture8Loc(ourTexture8Loc);
        }
        OpenglUtils.checkGLError();



        int ourTextureAry0Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[0]");
        if (ourTextureAry0Loc >= 0) {
            config.setTexture0Loc(ourTextureAry0Loc);
        }
        OpenglUtils.checkGLError();
        int ourTextureAry1Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[1]");
        if (ourTextureAry1Loc >= 0) {
            config.setTexture1Loc(ourTextureAry1Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry2Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[2]");
        if (ourTextureAry2Loc >= 0) {
            config.setTexture2Loc(ourTextureAry2Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry3Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[3]");
        if (ourTextureAry3Loc >= 0) {
            config.setTexture3Loc(ourTextureAry3Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry4Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[4]");
        if (ourTextureAry4Loc >= 0) {
            config.setTexture4Loc(ourTextureAry4Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry5Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[5]");
        if (ourTextureAry5Loc >= 0) {
            config.setTexture5Loc(ourTextureAry5Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry6Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[6]");
        if (ourTextureAry6Loc >= 0) {
            config.setTexture6Loc(ourTextureAry6Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry7Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[7]");
        if (ourTextureAry7Loc >= 0) {
            config.setTexture7Loc(ourTextureAry7Loc);
        }OpenglUtils.checkGLError();
        int ourTextureAry8Loc = glGetUniformLocation(config.getProgramId(), "ourTextures[8]");
        if (ourTextureAry8Loc >= 0) {
            config.setTexture8Loc(ourTextureAry8Loc);
        }OpenglUtils.checkGLError();


        for(int i=0;i<4;i++){
            int lgintLoc = glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].position");
            if (lgintLoc >= 0) {
                glUniform3f(glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].position"),
                        i*10, 2, 0);OpenglUtils.checkGLError();
                glUniform3f(glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].ambient"),
                        0.05f, 0.05f, 0.05f);OpenglUtils.checkGLError();
                glUniform3f(glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].diffuse"),
                        0.8f, 0.8f, 0.8f);OpenglUtils.checkGLError();
                glUniform3f(glGetUniformLocation(config.getProgramId(),"pointLights["+i+"].specular"),
                        1.0f, 1.0f, 1.0f);OpenglUtils.checkGLError();

                glUniform1f(glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].constant"),
                        1.0f);OpenglUtils.checkGLError();
                glUniform1f(glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].linear"),
                        0.09f);OpenglUtils.checkGLError();
                glUniform1f(glGetUniformLocation(config.getProgramId(), "pointLights["+i+"].quadratic"),
                        0.032f);OpenglUtils.checkGLError();
            }
        }

        if (Constants.SHADOW_ENABLE) {

            int depthMapLoc = glGetUniformLocation(config.getProgramId(), "shadowMap");
            OpenglUtils.checkGLError();
            if (depthMapLoc >= 0) {
                config.setDepthMapLoc(depthMapLoc);OpenglUtils.checkGLError();
                //但是不能用这个方法 这个方法的前半段是正确的 将纹理id映射到全局的texture顺序上
                //但是后半段 她会根据config的
                //这里要确保再shadow的depthMap初始化后

                int loc = ShaderUtils.getActiveTextureLoc( shadow.getDepthMap());OpenglUtils.checkGLError();

               config.setInt("shadowMap",loc);
                OpenglUtils.checkGLError();
               /* GL13.glActiveTexture(GL13.GL_TEXTURE0);
                glBindTexture(GL_TEXTURE_2D, shaderManager.depthMap);
                GL13.glActiveTexture(GL13.GL_TEXTURE9);
                //shaderManager.terrainShaderConfig.depthMapLoc
                glBindTexture(GL_TEXTURE_2D, shaderManager.depthMap);
                GL13.glActiveTexture(GL13.GL_TEXTURE8);
                //shaderManager.terrainShaderConfig.depthMapLoc
                glBindTexture(GL_TEXTURE_2D, shaderManager.depthMap);
                GL13.glActiveTexture(GL13.GL_TEXTURE7);
                //shaderManager.terrainShaderConfig.depthMapLoc
                glBindTexture(GL_TEXTURE_2D, shaderManager.depthMap);*/

            }


        }
        // glUseProgram(0);
        //glUniform1f(glGetUniformLocation(terrainProgramId, "light.constant"), 1.0f);
        // glUniform1f(glGetUniformLocation(terrainProgramId, "light.linear"), 0.07f);
        //glUniform1f(glGetUniformLocation(terrainProgramId, "light.quadratic"), 0.017f);

    }


    public void createProgram(ShaderConfig config) {
        LogUtil.println("begin create program" + config.getName());
        try {
            int programId = ShaderUtils.CreateProgram(config.getVertPath(), config.getFragPath());

            if(programId<0){
                LogUtil.println("programId:"+programId);
                System.exit(0);
            }
            config.setProgramId(programId);
            OpenglUtils.checkGLError();
            //terrainProgramId = ShaderUtils.CreateProgram("box.vert", "box.frag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void addTestTerrainVao() {
        terrainShaderConfig.addVao(new Vao("test"));
        Vao vao = new Vao("test");
        int x = 0, y = 0, z = -10;
        TextureInfo ti = TextureManager.getTextureInfo("soil");
        FloatBufferWrap veticesBuffer = vao.getVertices();

        // x += this.chunkPos.x * getChunkSizeX();
        // z += this.chunkPos.z * getChunkSizeZ();
        // this.faceIndex = 1;
        // count++;//left front top
        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1); //left front right
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.minY);

        veticesBuffer.put(x + 1);//left behind right
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);

        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.maxY);

        veticesBuffer.put(x);
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z + 1);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);

        veticesBuffer.put(ti.minX);
        veticesBuffer.put(ti.minY);


        veticesBuffer.put(x + 1); //left behind right
        veticesBuffer.put(y + 1);
        veticesBuffer.put(z);
        veticesBuffer.put(0);
        veticesBuffer.put(1);
        veticesBuffer.put(0);
        veticesBuffer.put(ti.maxX);
        veticesBuffer.put(ti.maxY);
    }

    public static void CreateTerrainVAO(ShaderConfig config, Vao vao) {
        //config.getVao().setVertices(BufferUtils.createFloatBuffer(902400));
        //glUseProgram(config.getProgramId());
        //生成vaoid
        //create vao
        // Vao vao =config.getVao();

        /*VaoId = glGenVertexArrays();
        Util.checkGLError();

        glBindVertexArray(VaoId);
        Util.checkGLError();
        this.CreateTerrainVBO();

        glBindVertexArray(0);
        Util.checkGLError();
        */
        int length = 10;
        if (vao.getVaoId() > 0) {


            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        } else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            //  glBindVertexArray(vao.getVaoId());
            int VboId = glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        int position = vao.getVertices().position();
      /*  if(vao.getVertices().position()==0){

        }*/
        assert vao.getVertices().position() > 0;
        vao.setPoints(vao.getVertices().position() / length);
        assert vao.getVaoId() > 0;
        assert vao.getVaoId() > 0;
        assert vao.getPoints() > 0;
        //LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
        vao.getVertices().rewind();
        // vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo

        vao.getVertices().glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.


        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, length * 4, 0);

        Util.checkGLError();
        glEnableVertexAttribArray(0);
        Util.checkGLError();

        glVertexAttribPointer(1, 3, GL_FLOAT, false, length * 4, 3 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(1);
        Util.checkGLError();

        glVertexAttribPointer(2, 3, GL_FLOAT, false, length * 4, 6 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(2);
        Util.checkGLError();

        glVertexAttribPointer(3, 1, GL_FLOAT, false, length * 4, 9 * 4);
        Util.checkGLError();
        glEnableVertexAttribArray(3);
        Util.checkGLError();


        glBindVertexArray(0);
        OpenglUtils.checkGLError();
    }

//    public static void CreateLivingVAO(ShaderConfig config, Vao vao) {
//
//        int length = 10;
//        if (vao.getVaoId() > 0) {
//
//
//            //glBindVertexArray(vao.getVaoId());
//            // LogUtil.err("vao have been initialized");
//        } else {
//            vao.setVaoId(glGenVertexArrays());
//            OpenglUtils.checkGLError();
//            //  glBindVertexArray(vao.getVaoId());
//            int VboId = glGenBuffers();//create vbo
//            vao.setVboId(VboId);
//           /* int eboId = glGenBuffers();
//            vao.setEboId(eboId);*/
//        }
//        //绑定vao
//        glBindVertexArray(vao.getVaoId());
//        OpenglUtils.checkGLError();
//        //create vbo
//        //顶点 vbo
//        //create vbo 创建vbo  vertex buffer objects
//        //创建顶点数组
//        int position = vao.getVertices().position();
//      /*  if(vao.getVertices().position()==0){
//
//        }*/
//        assert vao.getVertices().position() > 0;
//        vao.setPoints(vao.getVertices().position() / length);
//        assert vao.getVaoId() > 0;
//        assert vao.getVaoId() > 0;
//        assert vao.getPoints() > 0;
//        //LogUtil.println("twoDImgBuffer:"+vao.getVertices().position());
//        vao.getVertices().rewind();
//        // vao.setVertices(twoDImgBuffer);
//        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
//
//        glBufferData(GL_ARRAY_BUFFER, vao.getVertices(), GL_STATIC_DRAW);//put data
//        //create ebo
//        // float width = 1;
//        OpenglUtils.checkGLError();
//        // System.out.println("float.size:" + FlFLOAToat.SIZE);
//        //图片位置 //0代表再glsl里的变量的location位置值.
//
//
//        // System.out.println("float.size:" + FlFLOAToat.SIZE);
//        glVertexAttribPointer(0, 3, GL_FLOAT, false, length * 4, 0);
//        Util.checkGLError();
//        glEnableVertexAttribArray(0);
//        Util.checkGLError();
//
//        glVertexAttribPointer(1, 3, GL_FLOAT, false, length * 4, 3 * 4);
//        Util.checkGLError();
//        glEnableVertexAttribArray(1);
//        Util.checkGLError();
//
//        glVertexAttribPointer(2, 3, GL_FLOAT, false, length * 4, 6 * 4);
//        Util.checkGLError();
//        glEnableVertexAttribArray(2);
//        Util.checkGLError();
//
//        glVertexAttribPointer(3, 1, GL_FLOAT, false, length * 4, 9 * 4);
//        Util.checkGLError();
//        glEnableVertexAttribArray(3);
//        Util.checkGLError();
//
//
//        glBindVertexArray(0);
//        OpenglUtils.checkGLError();
//    }

//    public void CreateTerrainProgram(ShaderConfig config) {
//        try {
//            int terrainProgramId = ShaderUtils.CreateProgram(config.getVertPath(), config.getFragPath());
//            config.setProgramId(terrainProgramId);
//            //terrainProgramId = ShaderUtils.CreateProgram("box.vert", "box.frag");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }
//
//    public void CreateLightProgram(ShaderConfig config) {
//        try {
//            int LightProgramId = ShaderUtils.CreateProgram(config.getVertPath(), config.getFragPath());
//            config.setProgramId(LightProgramId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }

    public void CreateLightVAO(ShaderConfig config) {
        glUseProgram(config.getProgramId());
        //int lightVaoId = glGenVertexArrays();
        //config.getVao().setVaoId(lightVaoId);
        //config.getVao().setVaoId(lightVaoId);
        OpenglUtils.checkGLError();

       // glBindVertexArray(lightVaoId);
       // config.getVao().setVaoId(lightVaoId);
       // OpenglUtils.checkGLError();

        this.CreateLightVBO(config);
        glBindVertexArray(0);
        OpenglUtils.checkGLError();
        glUseProgram(0);

    }

    public void CreateUiVAO(ShaderConfig config) {


        //生成vaoid
        //create vao
        Vao vao = config.getVao();
        if (vao.getVaoId() > 0) {
            //glBindVertexArray(vao.getVaoId());
            // LogUtil.err("vao have been initialized");
        } else {
            vao.setVaoId(glGenVertexArrays());
            OpenglUtils.checkGLError();
            int VboId = glGenBuffers();//create vbo
            vao.setVboId(VboId);
           /* int eboId = glGenBuffers();
            vao.setEboId(eboId);*/
        }
        //绑定vao
        glBindVertexArray(vao.getVaoId());
        OpenglUtils.checkGLError();
        //create vbo
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects
        //创建顶点数组
        int position = vao.getVertices().position();
        if (vao.getVertices().position() == 0) {
            //ShaderUtils.printText("it's test ",50,50,0,24);
        }
        vao.setPoints(vao.getVertices().position() / 10);
        LogUtil.println("twoDImgBuffer:" + vao.getVertices().position());
        vao.getVertices().rewind();
        // vao.setVertices(twoDImgBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, vao.getVboId());//bind vbo
       vao.getVertices(). glBufferData(GL_ARRAY_BUFFER,  GL_STATIC_DRAW);//put data
        //create ebo
        // float width = 1;
        OpenglUtils.checkGLError();
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        //图片位置 //0代表再glsl里的变量的location位置值.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 10 * 4, 0);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();
        //纹理位置
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 10 * 4, 3 * 4);
        glEnableVertexAttribArray(1);
        //textureHandle
        glVertexAttribPointer(2, 1, GL_FLOAT, false, 10 * 4, 5 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(2);
        OpenglUtils.checkGLError();
        //color
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 10 * 4, 6 * 4);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(3);
        OpenglUtils.checkGLError();


        glBindVertexArray(0);
        OpenglUtils.checkGLError();


    }

    /**
     * 灯的白色的六面绘制
     * @param config
     */
    public void CreateLightVBO(ShaderConfig config) {

        //创建vao2=========================================================
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects

        // model = GL_Matrix.multiply(view2,model);
        // model = GL_Matrix.multiply(view2,model);
       // int VboId = glGenBuffers();//create vbo
        //config.getVao().setVboId(VboId);
        int minX = -1;
        int minY = -1;
        int minZ = -1;
        int maxX = 1;
        int maxY = 1;
        int maxZ = 1;
        GL_Vector color = new GL_Vector(1.0f, 1f, 1f);
        GL_Vector P1 = new GL_Vector(minX, minY, maxZ);
        GL_Vector P2 = new GL_Vector(maxX, minY, maxZ);
        GL_Vector P3 = new GL_Vector(maxX, minY, minZ);
        GL_Vector P4 = new GL_Vector(minX, minY, minZ);

        GL_Vector P5 = new GL_Vector(minX, maxY, maxZ);
        GL_Vector P6 = new GL_Vector(maxX, maxY, maxZ);
        GL_Vector P7 = new GL_Vector(maxX, maxY, minZ);
        GL_Vector P8 = new GL_Vector(minX, maxY, minZ);
        Vao vao = config.getVao();
        FloatBufferWrap floatBuffer = vao.getVertices();
        floatBuffer.rewind();
        ShaderUtils.draw3dColorSimple(P1, P2, P6, P5, new GL_Vector(0, 0, -1f), color, floatBuffer, config);


        //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P3,P4,P8,P7,new GL_Vector(0,0,-1f),front);
        ShaderUtils.draw3dColorSimple(P3, P4, P8, P7, new GL_Vector(0, 0, 1), color, floatBuffer, config);

        //ShaderUtils.drawImage(ShaderManager.livingThingShaderConfig,ShaderManager.livingThingShaderConfig.getVao(),P5,P6,P7,P8,new GL_Vector(0,1,0f),front);
        ShaderUtils.draw3dColorSimple(P5, P6, P7, P8, new GL_Vector(0, -1, 0), color, floatBuffer, config);

        ShaderUtils.draw3dColorSimple(P4, P3, P2, P1, new GL_Vector(0, 1, 0), color, floatBuffer, config);

        ShaderUtils.draw3dColorSimple(P2, P3, P7, P6, new GL_Vector(1, 0, 0f), color, floatBuffer, config);

        ShaderUtils.draw3dColorSimple(P4, P1, P5, P8, new GL_Vector(-1, 0, 0), color, floatBuffer, config);
        ShaderUtils.freshVao(config, config.getVao());
    }


    public void CreateSkyVBO(ShaderConfig config) {

        //创建vao2=========================================================
        //顶点 vbo
        //create vbo 创建vbo  vertex buffer objects

        // model = GL_Matrix.multiply(view2,model);
        int VboId = glGenBuffers();//create vbo
        config.getVao().setVboId(VboId);
        GL_Vector p1 = new GL_Vector(-0.5f, -0.5f, -0.5f);
        float VerticesArray[] = {-0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,

                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,

                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,

                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f};
        FloatBuffer Vertices = BufferUtils.createFloatBuffer(VerticesArray.length);


        Vertices.put(VerticesArray);
        config.getVao().setPoints(Vertices.position() / 6);
        Vertices.rewind(); //


        glBindBuffer(GL_ARRAY_BUFFER, config.getVao().getVboId());//bind vbo
        OpenglUtils.checkGLError();
        glBufferData(GL_ARRAY_BUFFER, Vertices, GL_STATIC_DRAW);//put data
        // System.out.println("float.size:" + FlFLOAToat.SIZE);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
        OpenglUtils.checkGLError();
        glEnableVertexAttribArray(0);
        OpenglUtils.checkGLError();
    }

    // int lightModelLoc;

    public void uniformLight() {
        GL_Matrix model = GL_Matrix.translateMatrix(GamingState.instance.lightPos.x, GamingState.instance.lightPos.y, GamingState.instance.lightPos.z);
        GL_Matrix view = GL_Matrix.translateMatrix(0, 0, 0);
        glUseProgram(lightShaderConfig.getProgramId());
        int lightProjectionLoc = glGetUniformLocation(lightShaderConfig.getProgramId(), "projection");
        glUniformMatrix4(lightProjectionLoc, false, projection.toFloatBuffer());
        OpenglUtils.checkGLError();
        int lightModelLoc = glGetUniformLocation(lightShaderConfig.getProgramId(), "model");
        lightShaderConfig.setModelLoc(lightModelLoc);
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        OpenglUtils.checkGLError();

        //  int lightViewLoc = ;
        lightShaderConfig.setViewLoc(glGetUniformLocation(lightShaderConfig.getProgramId(), "view"));
        glUniformMatrix4(lightShaderConfig.getViewLoc(), false, view.toFloatBuffer());
        OpenglUtils.checkGLError();

    }

    public void initViewModelProjectionLoc(ShaderConfig config, GL_Matrix model, GL_Matrix view) {
        assert config.getProgramId() > 0;
        glUseProgram(config.getProgramId());
        int lightProjectionLoc = glGetUniformLocation(config.getProgramId(), "projection");
        glUniformMatrix4(lightProjectionLoc, false, projection.toFloatBuffer());
        OpenglUtils.checkGLError();
        int lightModelLoc = glGetUniformLocation(config.getProgramId(), "model");
        lightShaderConfig.setModelLoc(lightModelLoc);
        glUniformMatrix4(lightModelLoc, false, model.toFloatBuffer());
        OpenglUtils.checkGLError();
        //  int lightViewLoc = ;
        lightShaderConfig.setViewLoc(glGetUniformLocation(lightShaderConfig.getProgramId(), "view"));
        glUniformMatrix4(lightShaderConfig.getViewLoc(), false, view.toFloatBuffer());
        OpenglUtils.checkGLError();
    }

/*
    public void cameraPosChangeListener() {
        LogUtil.println("please use camera changeCallBack");
        GL_Matrix view =
                GL_Matrix.LookAt( GamingState.instance.camera.Position,  GamingState.instance.camera.ViewDir);
        view.fillFloatBuffer(cameraViewBuffer);

        glUseProgram(terrainShaderConfig.getProgramId());


        glUniformMatrix4(terrainShaderConfig.getViewLoc(), false, cameraViewBuffer);
        OpenglUtils.checkGLError();
        //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(terrainShaderConfig.getViewPosLoc(),  GamingState.instance.camera.Position.x,  GamingState.instance.camera.Position.y,  GamingState.instance.camera.Position.z);
        OpenglUtils.checkGLError();


        glUseProgram(lightShaderConfig.getProgramId());
        OpenglUtils.checkGLError();
        //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(lightShaderConfig.getViewLoc(), false, view.toFloatBuffer());

        OpenglUtils.checkGLError();

      *//*  glUseProgram(livingThingShaderConfig.getProgramId());
        OpenglUtils.checkGLError();
        //lightViewLoc= glGetUniformLocation(LightProgramId,"view");


        glUniformMatrix4(livingThingShaderConfig.getViewLoc(), false, view.toFloatBuffer());

        OpenglUtils.checkGLError();*//*


        glUseProgram(livingThingShaderConfig.getProgramId());


        glUniformMatrix4(livingThingShaderConfig.getViewLoc(), false, cameraViewBuffer);
        OpenglUtils.checkGLError();
        //viewPosLoc= glGetUniformLocation(ProgramId,"viewPos");


        glUniform3f(livingThingShaderConfig.getViewPosLoc(),  GamingState.instance.camera.Position.x,  GamingState.instance.camera.Position.y,  GamingState.instance.camera.Position.z);
        OpenglUtils.checkGLError();


    }*/

    public void update() {

    }

    public void render() {
LogUtil.println("hello");
    }


}
