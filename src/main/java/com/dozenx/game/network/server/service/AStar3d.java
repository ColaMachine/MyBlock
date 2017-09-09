/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年9月6日 下午6:40:17
* 创建作者：张智威
* 文件名称：AStart.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.dozenx.game.network.server.service;

public class AStar3d {
  

int map[][][] = new int [Width][Height][Thick];

int map2[][] = {
        {0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,1},
        {0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
        {0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0,0,1},
        {0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1},
        {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
        {0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,1},
        {0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0}
    };


    static int MaxLength =100  ;  //用于优先队列（Open表）的数组
    static int Height    = 15  ;   //地图高度
    static int Width     = 20  ;   //地图宽度
    static int Thick     = 20  ;   //地图宽度
    static int Reachable  = 0    ; //可以到达的结点
    static int Bar        = 1   ;  //障碍物
    static int Pass       = 2   ;  //需要走的步数
    static int Source     = 3  ;   //起点
    static int Destination =4  ;   //终点
      
    final static int Sequential = 0   ;  //顺序遍历
    final static int NoSolution = 2   ;  //无解决方案
    static int Infinity   = 0xfffffff ;
      
    static int East       =(1 << 0);
    static int West =(1 << 1);
    static int North      =(1 << 2);
    static int South=(1 << 3);
    static int Up       =(1 << 4);
    static int Down =(1 << 5);
   
    
    Point[] dir = new Point[]{new Point(1,0,0),
            new Point(-1,0,0),
            new Point(0,0,1),
            new Point(0,0,-1),
         
            new Point(0,1,0),
            new Point(0,-1,0),
           };

    static MapNode graph[][][] = new MapNode[Width][Height][Thick];
    static int srcX, srcY,srcZ, dstX, dstY,dstZ;    //起始点、终点
    static CloseSet close[][][]=new CloseSet[Width][Height][Thick];
    
    
    
boolean within(int x,int y,int z){
    return (x>=0 && y>=0&&
            x<Width && y<Height && z>=0 && z<Thick);
}


//优先队列基本操作
void initOpen(Open open)    //优先队列初始化
{
    open.length = 0;        // 队内元素数初始为0
}


void push(Open q, CloseSet cls[][][], int x, int y,int z, float g)
{    //向优先队列（Open表）中添加元素
    CloseSet t;
    int i, mintag;
    cls[x][y][z].G = g;    //所添加节点的坐标
    cls[x][y][z].F = cls[x][y][z].G + cls[x][y][z].H;//该节点的f=g+h
    q.Array[q.length++] = cls[x][y][z];//移入节点
    mintag = q.length - 1;//设置最小值是最后一个元素
    for (i = 0; i < q.length - 1; i++)
    {
        if (q.Array[i].F < q.Array[mintag].F)///如果f最小的值 记录他
        {
            mintag = i;
        }
    }
    t = q.Array[q.length - 1];//交换
    q.Array[q.length - 1] = q.Array[mintag];
    q.Array[mintag] = t;    //将评价函数值最小节点置于队头
}



CloseSet shift(Open q)
{
    return q.Array[--q.length];
}

//地图初始化操作
void initClose(CloseSet cls[][][], int sx, int sy,int sz, int dx, int dy,int dz)
{    // 地图Close表初始化配置
 int i, j,k;
 for (i = 0; i < Width; i++)
 {
     for (j = 0; j < Height; j++)
     {
         
         for (k = 0; k < Thick; k++)
         {
             cls[i][j][k] = new CloseSet();
       
         cls[i][j][k].cur = graph[i][j][k];        // Close表所指节点
         cls[i][j][k].vis = !graph[i][j][k].reachable;        // 是否被访问
         cls[i][j][k].from = null;                // 所来节点
         cls[i][j][k].G = cls[i][j][k].F = 0;
         cls[i][j][k].H = Math.abs(dx - i) + 2*Math.abs(dy - j)+Math.abs(dz - k) ;    // 评价函数值
         }
     }
 }
 cls[sx][sy][sz].F = cls[sx][sy][sz].H;            //起始点评价初始值
 //    cls[sy][sy].G = 0;                        //移步花费代价值
 cls[dx][dy][dz].G = Integer.MAX_VALUE;
}
  



void initGraph( int map[][][], int sx, int sy,int sz, int dx, int dy,int dz)
{    //地图发生变化时重新构造地
    int i, j,k;
    srcX = sx;    //起点X坐标
    srcY = sy;    //起点Y坐标
    dstX = dx;    //终点X坐标
    dstY = dy;    //终点Y坐标
    for (i = 0; i < Width; i++)
    {
        for (j = 0; j < Height; j++)
            
        { 
            for (k = 0; k < Thick; k++)
        
            {
                graph[i][j][k] = new MapNode();
            
            graph[i][j][k].x = i; //地图坐标X
            graph[i][j][k].y = j; //地图坐标Y
            graph[i][j][k].z = k; //地图坐标Y
            graph[i][j][k].value = map[i][j][k];
            //有障碍物体是肯定不能通过的
            graph[i][j][k].reachable = (graph[i][j][k].value == Reachable);    // 节点可到达性
            
           
            
            graph[i][j][k].sur = 0; //邻接节点个数
            if (!graph[i][j][k].reachable)
            {
                continue;
            }
            boolean reachable =false;
            //太高也是不能通过的
            if(j>0){
                //下面有方块是可以的
                if(!graph[i][j-1][k].reachable    ){
                    reachable=true;
                }
                
                
                //下面没有方块的情况  东南西北有方块也是可以的
                
                if(i>0){
                    if(!graph[i-1][j-1][k].reachable    ){
                        reachable=true;
                    }
                }
                //不能判断还未加载的方块
                if(i<Width){
                    if(!graph[i+1][j-1][k].reachable    ){
                        reachable=true;
                    }
                }
                if(k>0){
                    if(!graph[i][j-1][k].reachable    ){
                        reachable=true;
                    }
                }
                if(k<Thick){ //不能判断还未加载的方块
                    if(!graph[i][j-1][k+1].reachable    ){
                        reachable=true;
                    }
                }
                
            }else{
                reachable=true;
            }
            
            graph[i][j][k].reachable = reachable;
            if (! graph[i][j][k].reachable)
            {
                continue;
            }
            
            if (j > 0)
            {//大家都往右上的方向机选就可以了  或者都往左下
                if (graph[i][j - 1][k].reachable)    // left节点可以到达
                {
                    graph[i][j][k].sur |= Down;//每个方向占用一个bit 如果哪个位置不可到达 哪个位置的bit就为0
                    graph[i][j - 1][k].sur |= Up;
                }
               
                
            }
            if (i > 0)
            {
                if (graph[i - 1][j][k].reachable)    // up节点可以到达
                {
                    graph[i][j][k].sur |= West;
                    graph[i - 1][j][k].sur |= East;
                }
               
            }
            if (k > 0)
            {
                if (graph[i ][j][k-1].reachable)    // up节点可以到达
                {
                    graph[i][j][k].sur |= North;
                    graph[i ][j][k-1].sur |= South;
                }
               
            }
            
            }
        }
    }
}
  
int astar()
{    // A*算法遍历
    //int times = 0;
    int i, curX, curY,curZ, surX, surY,surZ;
    float surG;
    Open q=new Open(); //Open表
    CloseSet p=new CloseSet();
  
    initOpen(q);
    initClose(close, srcX, srcY,srcZ, dstX, dstY,dstZ);
    close[srcX][srcY][srcZ].vis = true;
    push(q, close, srcX, srcY, srcZ,0);
  
    while (q.length!=0)
    {    //times++;
        p = shift(q);
        curX = p.cur.x;
        curY = p.cur.y;
        curZ=p.cur.z;
        if (p.H==0)
        {
            return Sequential;
        }
        for (i = 0; i < 8; i++)//遍历周围8个元素
        {
            if ((p.cur.sur & (1 << i))==0)//如果旁边这个元素是阻塞物体 就不进行计算
            {
                continue;
            }
            surX = curX + dir[i].x;//旁边的物体的坐标
            surY = curY + dir[i].y;
            surZ = curZ + dir[i].z;
            /*if(surX<0 || surY<0){
                continue;
            }*/
            if (!close[surX][surY][surZ].vis)//如果这个物体没有被到达过
            {
                close[surX][surY][surZ].vis = true;//设置为计算过 到达过
                close[surX][surY][surZ].from = p;//
                surG = (float)(p.G + Math.sqrt((curX - surX) * (curX - surX) + (curY - surY) * (curY - surY) + (curZ - surZ) * (curZ - surZ)));//父节点的g和子节点到他的距离的值
                push(q, close, surX, surY,surZ, surG);//压入8个的话 这里的while 就会循环8次 会压入8个之前从来没有计算过的
            }
        }
    }
    //printf("times: %d\n", times);
    return NoSolution; //无结果
}






     
      
    CloseSet getShortest()
    {    // 获取最短路径
        int result = astar();
        CloseSet p, t, q = null;
        switch(result)
        {
        case Sequential:    //顺序最近
            p = close[dstX][dstY][dstZ];
            while (p!=null)    //转置路径
            {
                t = p.from;// t 是temp
                p.from = q;//
                q = p;
                p = t;
            }
            close[srcX][srcY][srcZ].from = q.from;
            return close[srcX][srcY][srcZ];
        case NoSolution:
            return null;
        }
        return null;
    }
      
    static CloseSet start =new CloseSet();
    static int shortestep;
    int printShortest()
    {
        CloseSet p;
        int step = 0;
      
        p = getShortest();
        start = p;
        if (p==null)
        {
            return 0;
        }
        else
        {
            while (p.from!=null)
            {
                graph[p.cur.x][p.cur.y][p.cur.z].value = Pass;
               System.out.printf("（%d，%d）→\n", p.cur.x, p.cur.y);
                p = p.from;
                step++;
            }
            System.out.printf("（%d，%d）\n", p.cur.x, p.cur.y);
            graph[srcX][srcY][srcZ].value = Source;
            graph[dstX][dstY][dstZ].value = Destination;
            return step;
        }
    }
      
    void clearMap()
    {    // Clear Map Marks of Steps
        CloseSet p = start;
        while (p!=null)
        {
            graph[p.cur.x][p.cur.y][p.cur.z].value = Reachable;
            p = p.from;
        }
        graph[srcX][srcY][srcZ].value = map[srcX][srcY][srcZ];
        graph[dstX][dstY][dstZ].value = map[dstX][dstY][dstZ];
    }
      
   
      
    void printSur()
    {
        int i, j,k;
        for (i = 0; i < Height; i++)
        {
            for (j = 0; j < Width; j++)
            {for (k = 0; k < Width; k++)
            {
                System.out.printf("%02x ", graph[i][j][k].sur);
            }
            }
            System.out.println("");
        }
        System.out.println("");
    }
      
    void printH()
    {
        int i, j,k;
        for (i = 0; i < Height; i++)
        {
            for (j = 0; j < Width; j++)
            {for (k = 0; k < Width; k++)
            {
                System.out.printf("%02d ", close[i][j][k].H);
            }
            }
            System.out.println("");
        }
        System.out.println("");
    }
      public void start(){
          initGraph(map, 0, 0,0, 0, 0,0);
           //printMap();
           
           srcX=0;
           srcY=0;
           srcZ=0;
           dstX=14;
           dstY =19;
          
               if (within(srcX, srcY,srcZ) && within(dstX, dstY,dstZ))
               {
                   if ((shortestep = printShortest())>0)
                   {
                       System.out.printf("从（%d，%d）到（%d，%d）的最短步数是: %d\n",
                           srcX, srcY, dstX, dstY, shortestep);
                     //  printMap();
                       clearMap();
                      // bfs();
                       //printDepth();
                      // System.out.println((shortestep == close[dstX][dstY].G) ? "正确" : "错误");
                       clearMap();
                   }
                   else
                   {
                       System.out.printf("从（%d，%d）不可到达（%d，%d）\n",
                           srcX, srcY, dstX, dstY);
                   }
               }
               else
               {
                   System.out.printf("输入错误！");
               }
         
           
      }
   public static void main(String[] argv)
    {
       AStar3d aStar =new AStar3d();
       aStar.start();
      
    }
};

