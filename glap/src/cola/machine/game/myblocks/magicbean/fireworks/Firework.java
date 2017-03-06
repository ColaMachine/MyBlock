package cola.machine.game.myblocks.magicbean.fireworks;

import com.dozenx.game.engine.Role.bean.Player;
import glmodel.GL_Vector;
import org.lwjgl.opengl.GL11;

import cola.machine.game.myblocks.manager.TextureManager;
import gldemo.Particles;

public class Firework {
    int MAX_PARTICLES = 100;//
    boolean rainbow = true;
    boolean sp;
    boolean rp;
    float slowdown = 2.0f;
    float xspeed;
    float yspeed;
    float zoom = -40f;
    int loop;
    int col;
    int delay;
    Particles particle[] = new Particles[MAX_PARTICLES];
    static float colors[][] = // 彩虹颜色

            {

                    {1.0f, 0.5f, 0.5f}, {1.0f, 0.75f, 0.5f}, {1.0f, 1.0f, 0.5f},
                    {0.75f, 1.0f, 0.5f},

                    {0.5f, 1.0f, 0.5f}, {0.5f, 1.0f, 0.75f}, {0.5f, 1.0f, 1.0f},
                    {0.5f, 0.75f, 1.0f},

                    {0.5f, 0.5f, 1.0f}, {0.75f, 0.5f, 1.0f}, {1.0f, 0.5f, 1.0f},
                    {1.0f, 0.5f, 0.75f}

            };

    public Firework() {//首先得知摄像机和光斑之间的向量
        for (loop = 0; loop < MAX_PARTICLES; loop++) // 初始化所有的粒子

        {
            particle[loop] = new Particles();
            particle[loop].active = true; // 使所有的粒子为激活状态

            particle[loop].life = 1.0f; // 所有的粒子生命值为最大

            // 我们通过给定的值来设定粒子退色快慢.每次粒子被拉的时候life随着fade而减小.结束的数值将是0~99中的任意一个,然后平分1000份来得到一个很小的浮点数.最后我们把结果加上0.003f来使fade速度值不为0

            particle[loop].fade = (float) (Math.random() * 100) / 1000.0f + 0.003f; // 随机生成衰减速率

            // 既然粒子是活跃的,而且我们又给它生命,下面将给它颜色数值.一开始,我们就想每个粒子有不同的颜色.我怎么做才能使每个粒子与前面颜色箱里的颜色一一对应那?数学很简单,我们用loop变量乘以箱子中颜色的数目与粒子最大值(MAX_PARTICLES)的余数.这样防止最后的颜色数值大于最大的颜色数值(12).举例:900*(12/900)=12.1000*(12/1000)=12,等等

            particle[loop].r =(float)Math.random(); //colors[(int) (Math.random() * 12)][0]; // 粒子的红色颜色

            particle[loop].g = (float)Math.random(); //colors[(int) (Math.random() * 12)][1]; // 粒子的绿色颜色

            particle[loop].b = (float)Math.random(); //colors[(int) (Math.random() * 12)][2]; // 粒子的蓝色颜色

            // 现在设定每个粒子移动的方向和速度.我们通过将结果乘于10.0f来创造开始时的爆炸效果.我们将会以任意一个正或负值结束.这个数值将以任意速度,任意方向移动粒子.

            particle[loop].xi = (float) ((Math.random() * 50) - 26.0f) * 10.0f; // 随机生成X轴方向速度

            particle[loop].yi = (float) ((Math.random() * 50) - 25.0f) * 10.0f; // 随机生成Y轴方向速度

            particle[loop].zi = (float) ((Math.random() * 50) - 25.0f) * 10.0f; // 随机生成Z轴方向速度

            // 最后,我们设定加速度的数值.不像一般的加速度仅仅把事物拉下,我们的加速度能拉出,拉下,拉左,拉右,拉前和拉后粒子.开始我们需要强大的向下加速度.为了达到这样的效果我们将xg设为0.0f.在x方向没有拉力.我们设yg为-0.8f来产生一个向下的拉力.如果值为正则拉向上.我们不希望粒子拉近或远离我们,所以将zg设为0.0f

            particle[loop].xg = 0.0f; // 设置X轴方向加速度为0

            particle[loop].yg = -0.8f; // 设置Y轴方向加速度为-0.8

            particle[loop].zg = 0.0f; // 设置Z轴方向加速度为0

        }
    }

    public void render(Player player) {
        //GL_Vector vector =human.WalkDir;
        //vector.set(-human.WalkDir.x,-human.WalkDir.y,-human.WalkDir.z);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHT1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,
                TextureManager.getImage("particle").textureHandle);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR); //GL11.GL_NEAREST);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); //GL11.GL_NEAREST);
        //GL11.glLoadIdentity();
        // GL11.glDisable(GL11.GL_TEXTURE);
        GL11.glShadeModel(GL11.GL_SMOOTH);                            // Enable Smooth Shading
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                    // Black Background
        GL11.glClearDepth(1.0f);                                    // Depth Buffer Setup
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);                                    // Enable Blending
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);                    // Type Of Blending To Perform
        // GL11.glHint( GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);	// Really Nice Perspective Calculations
        // GL11.glHint( GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);				// Really Nice Point Smoothing

        // GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glTranslated(1, 4, -0);
        for (loop = 0; loop < MAX_PARTICLES; loop++) // 循环所有的粒子

        {
            // 首先我们做的事物是检查粒子是否活跃.如果不活跃,则不被更新.在这个程序中,它们始终活跃.但是在你自己的程序中,你可能想要使某粒子不活跃
            if (particle[loop].active) // 如果粒子为激活的

            {
                // 下面三个变量是我们确定x,y和z位置的暂时变量.注意:在z的位置上我们加上zoom以便我们的场景在以前的基础上再移入zoom个位置.particle[loop].x告诉我们要画的x的位置.particle[loop].y告诉我们要画的y的位置.particle[loop].z告诉我们要画的z的位置

                float x = particle[loop].x; // 返回X轴的位置

                float y = particle[loop].y; // 返回Y轴的位置

                float z = particle[loop].z; // 返回Z轴的位置
                // 既然知道粒子位置,就能给粒子上色.particle[loop].r保存粒子的亮红,particle[loop].g保存粒子的亮绿,particle[loop].b保存粒子的亮蓝.注意我用alpha为粒子生命.当粒子要燃尽时,它会越来越透明直到它最后消失.这就是为什么粒子的生命不应该超过1.0f.如果你想粒子燃烧时间长,可降低fade减小的速度

                // 设置粒子颜色

                GL11.glColor4f(particle[loop].r, particle[loop].g,
                        particle[loop].b, particle[loop].life);
                // 我们有粒子的位置,并设置颜色了.所以现在我们来画我们的粒子.我们用一个三角形带来代替一个四边形这样使程序运行快一点.很多3D
                // card画三角形带比画四边形要快的多.有些3D
                // card将四边形分成两个三角形,而有些不.所以我们按照我们自己的想法来,所以我们来画一个生动的三角形带

                GL11.glBegin(GL11.GL_TRIANGLE_STRIP); // 绘制三角形带

                // 从红宝书引述:三角形带就是画一连续的三角形(三个边的多角形)使用vertices
                // V0,V1,V2,然后V2,V1,V3(注意顺序),然后V2,V3,V4等等.画三角形的顺序一样才能保证三角形带为相同的表面.要求方向是很重要的,例如:剔除,最少用三点来画当第一个三角形使用vertices0,1和2被画.如果你看图片你将会理解用顶点0,1和2构造第一个三角形(顶端右边,顶端左边,底部的右边).第二个三角形用点vertices2,1和3构造.再一次,如果你注意图片,点vertices2,1和3构造第二个三角形(底部右边,顶端左边,底部左边).注意:两个三角形画点顺序相同.我看到很多的网站要求第二个三角形反方向画.这是不对的.Opengl从新整理顶点来保证所有的三角形为同一方向!注意:你在屏幕上看见的三角形个数是你叙述的顶点的个数减2.在程序中在我们有4个顶点,所以我们看见二个三角形


                // GLApp.drawCursor().renderCube(x,y,z);
                GL_Vector right = GL_Vector.add(new GL_Vector(particle[loop].x,particle[loop].y,particle[loop].z), GL_Vector.multiply(player.RightVector,
                        0.5f));
                GL_Vector left = GL_Vector.add(right, GL_Vector.multiply(player.RightVector,
                        -1f));
                GL_Vector lefttop = GL_Vector.add(left, GL_Vector.multiply(player.UpVector,
                        0.5f));
                GL_Vector righttop = GL_Vector.add(right, GL_Vector.multiply(player.UpVector,
                        0.5f));
                GL11.glTexCoord2d(1, 1);
               // GL11.glVertex3f(x + 0.5f, y + 0.5f, z);
                GL11.glVertex3f(righttop.x, righttop.y ,righttop. z);
                GL11.glTexCoord2d(0, 1);
                //GL11.glVertex3f(x - 0.5f, y + 0.5f, z);
                GL11.glVertex3f(lefttop.x, lefttop.y ,lefttop. z);
                GL11.glTexCoord2d(1, 0);
                //GL11.glVertex3f(x + 0.5f, y - 0.5f, z);
                GL11.glVertex3f(right.x, right.y ,right. z);
                GL11.glTexCoord2d(0, 0);
               // GL11.glVertex3f(x - 0.5f, y - 0.5f, z);
                GL11.glVertex3f(left.x, left.y ,left. z);

                // 最后我们告诉Opengl我们画完三角形带
                GL11.glEnd();

                // 现在我们能移动粒子.下面公式可能看起来很奇怪,其实很简单.首先我们取得当前粒子的x位置.然后把x运动速度加上粒子被减速1000倍后的值.所以如果粒子在x轴(0)上屏幕中心的位置,运动值(xi)为x轴方向+10(移动我们为右),而slowdown等于1,我们移向右边以10/(1*1000)或
                // 0.01f速度.如果增加slowdown值到2我们只移动0.005f.希望能帮助你了解slowdown如何工作.那也是为什么用10.0f乘开始值来叫象素移动快速,创造一个爆发效果.y和z轴用相同的公式来计算附近移动粒子
                particle[loop].x += particle[loop].xi / (slowdown * 1000); // 更新X坐标的位置

                particle[loop].y += particle[loop].yi / (slowdown * 1000); // 更新Y坐标的位置

                particle[loop].z += particle[loop].zi / (slowdown * 1000); // 更新Z坐标的位置

                // 在计算出下一步粒子移到那里,开始考虑重力和阻力.在下面的第一行,将阻力(xg)和移动速度(xi)相加.我们的移动速度是10和阻力是1.每时每刻粒子都在抵抗阻力.第二次画粒子时,阻力开始作用,移动速度将会从10掉到9.第三次画粒子时,阻力再一次作用,移动速度降低到8.如果粒子燃烧为超过10次重画,它将会最后结束,并向相反方向移动.因为移动速度会变成负值.阻力同样使用于y和z移动速度

                particle[loop].xi += particle[loop].xg; // 更新X轴方向速度大小

                particle[loop].yi += particle[loop].yg; // 更新Y轴方向速度大小

                particle[loop].zi += particle[loop].zg; // 更新Z轴方向速度大小
                // 下行将粒子的生命减少.如果我们不这么做,粒子无法烧尽.我们用粒子当前的life减去当前的fade值.每粒子都有不同的fade值,因此他们全部将会以不同的速度烧尽

                particle[loop].life -= particle[loop].fade; // 减少粒子的生命值

                // 现在我们检查当生命为零的话粒子是否活着

                if (particle[loop].life < 0.0f) // 如果粒子生命值小于0

                {

                    // 如果粒子是小时(烧尽),我们将会使它复原.我们给它全值生命和新的衰弱速度.

                    particle[loop].life = 1.0f; // 产生一个新的粒子

                    particle[loop].fade = (float) (Math.random() * 100) / 1000.0f + 0.003f; // 随机生成衰减速率

                    // 我们也重新设定粒子在屏幕中心放置.我们重新设定粒子的x,y和z位置为零
                    particle[loop].x = 0.0f; // 新粒子出现在屏幕的中央

                    particle[loop].y = 0.0f;

                    particle[loop].z = 0.0f;



                    // 在粒子从新设置之后,将给它新的移动速度/方向.注意:我增加最大和最小值,粒子移动速度为从50到60的任意值,但是这次我们没将移动速度乘10.我们这次不想要一个爆发的效果,而要比较慢地移动粒子.也注意我把xspeed和x轴移动速度相加,y轴移动速度和yspeed相加.这个控制粒子的移动方向.
//                    particle[loop].xi = xspeed
//                            + (float) ((Math.random() * 60) - 32.0f); // 随机生成粒子速度
//
//                    particle[loop].yi = yspeed
//                            + (float) ((Math.random() * 60) - 30.0f);
//
//                    particle[loop].zi = (float) ((Math.random() * 60) - 30.0f);

                    particle[loop].xi = (float) ((Math.random() * 50) - 26.0f) * 10.0f; // 随机生成X轴方向速度

                    particle[loop].yi = (float) ((Math.random() * 50) - 25.0f) * 10.0f; // 随机生成Y轴方向速度

                    particle[loop].zi = (float) ((Math.random() * 50) - 25.0f) * 10.0f; // 随机生成Z轴方向速度

                    // 最后我们分配粒子一种新的颜色.变量col保存一个数字从1到11(12种颜色),我们用这个变量去找红,绿,蓝亮度在颜色箱里面.下面第一行表示红色的强度,数值保存在colors[col][0].所以如果col是0,红色的亮度就是1.0f.绿色的和蓝色的值用相同的方法读取.如果你不了解为什么红色亮度为1.0f那col就为0.我将一点点的解释.看着程序的最前面.找到那行:static
                    // GLfloat
                    // colors[12][3].注意:12行3列.三个数字的第一行是红色强度.第二行是绿色强度而且第三行是蓝色强度.[0],[1]和[2]下面描述的1st,2nd和3rd就是我刚提及的.如果col等于0,我们要看第一个组.11
                    // 是最後一个组(第12种颜色).
                    particle[loop].r = colors[(int) (12 * Math.random())][0]; // 设置粒子颜色

                    particle[loop].g = colors[(int) (12 * Math.random())][1];

                    particle[loop].b = colors[(int) (12 * Math.random())][2];

                }

                // 下行描述加速度的数值是多少.通过小键盘8号键,我们增加yg(y
                // 地心引力)值.这引起向上的力.如果这个程序在循环外面,那么我们必须生成另一个循环做相同的工作,因此我们最好放在这里
            }
        }
        GL11.glTranslated(-1, -4, 0);
        // do gluLookAt() with camera position, direction, orientation
        GL11.glEnable(GL11.GL_LIGHT1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

}
