package cola.machine.game.myblocks.switcher;

public class Switcher {
	public static  boolean IS_GOD = false;
	public static boolean  PRINT_SWITCH=true;
    public static int CAMERA_2_PLAYER=100;
    public static boolean MOUSE_AUTO_CENTER=false;
    public static boolean MOUSE_CANCELBUBLE=false;
    public static int CAMERA_MODEL=1;

    public static int gameState=0;//0菜单 1游戏 2暂停 -1 退出

    public static int yangjiao_d=89;
    public static int fujiao_d=-89;
    public static boolean  test=true;
    public static float YANGJIAO=yangjiao_d/180f*3.14f;

    public static float FUJIAO=fujiao_d/180f *3.14f;

    public static boolean isChat =false;

    public static boolean SHADER_ENABLE=true;

    public static boolean edit =true;

    public static boolean size =true;


    public static int boxSelectMode =1;
    public static int singleSelectMode =2;
    public static int shootMode =3;
    public static int brushMode =4;
    public static int faceSelectMode =5;

    public static int shootComponentMode = 6;
    public static int textureMode = 7;

    public static int pullMode =8;
    public static int  pushMode = 9 ;

    public static int shapeBlockMode =0;
    public static int shapeLineMode =1;
    public static int shapeCircleMode =2;



    public static int shapeMode = shapeBlockMode;//shapeBlockMode;




    public static int mouseState = boxSelectMode;

    public static boolean isEditComponent =false;

    public static int COLORBLOCK=0;
    public static int ROTATECOLORBLOCK=1;
    public static int IMAGEBLOCK=2;
    public static int COMPONENTBLOCK=3;
    public static int BLOCKTYPE= COLORBLOCK;
    /**是否隐藏背景地形 用在编辑的时候*/
    public static boolean hideTerrain =false;
}
