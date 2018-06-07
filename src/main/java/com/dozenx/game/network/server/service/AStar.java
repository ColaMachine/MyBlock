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

public class AStar {
  

public int map[][] = {
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
      
    static int Reachable  = 0    ; //可以到达的结点
    static int Bar        = 1   ;  //障碍物
    static int Pass       = 2   ;  //需要走的步数
    static int Source     = 3  ;   //起点
    static int Destination =4  ;   //终点
      
    final static int Sequential = 0   ;  //顺序遍历
    final static int NoSolution = 2   ;  //无解决方案
    static int Infinity   = 0xfffffff ;
      
    static int East       =(1 << 0);
    static int South_East =(1 << 1);
    static int South      =(1 << 2);
    static int South_West =(1 << 3);
    static int West       =(1 << 4);
    static int North_West =(1 << 5);
    static int North      =(1 << 6);
    static int North_East =(1 << 7);
    
    Point[] dir = new Point[]{new Point(0,1),
            new Point(1,1),
            new Point(1,0),
            new Point(1,-1),
            new Point(0,-1),
            new Point(-1,-1),
            new Point(-1,0),
            new Point(-1,1)};

    static MapNode graph[][] = new MapNode[Height][Width];
    static int srcX, srcY, dstX, dstY;    //起始点、终点
    static CloseSet close[][]=new CloseSet[Height][Width];;
    
    
    
boolean within(int x,int y){
    return (x>=0 && y>=0&&
            x<Height && y<Width);
}


//优先队列基本操作
void initOpen(Open open)    //优先队列初始化
{
    open.length = 0;        // 队内元素数初始为0
}


void push(Open q, CloseSet cls[][], int x, int y, float g)
{    //向优先队列（Open表）中添加元素
    CloseSet t;
    int i, mintag;
    cls[x][y].G = g;    //所添加节点的坐标
    cls[x][y].F = cls[x][y].G + cls[x][y].H;//该节点的f=g+h
    q.Array[q.length++] = cls[x][y];//移入节点
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
void initClose(CloseSet cls[][], int sx, int sy, int dx, int dy)
{    // 地图Close表初始化配置
 int i, j;
 for (i = 0; i < Height; i++)
 {
     for (j = 0; j < Width; j++)
     {cls[i][j] = new CloseSet();
         cls[i][j].cur = graph[i][j];        // Close表所指节点
         cls[i][j].vis = !graph[i][j].reachable;        // 是否被访问
         cls[i][j].from = null;                // 所来节点
         cls[i][j].G = cls[i][j].F = 0;
         cls[i][j].H = Math.abs(dx - i) + Math.abs(dy - j);    // 评价函数值
     }
 }
 cls[sx][sy].F = cls[sx][sy].H;            //起始点评价初始值
 //    cls[sy][sy].G = 0;                        //移步花费代价值
 cls[dx][dy].G = Integer.MAX_VALUE;
}
  



void initGraph( int map[][], int sx, int sy, int dx, int dy)
{    //地图发生变化时重新构造地
    int i, j;
    srcX = sx;    //起点X坐标
    srcY = sy;    //起点Y坐标
    dstX = dx;    //终点X坐标
    dstY = dy;    //终点Y坐标
    for (i = 0; i < Height; i++)
    {
        for (j = 0; j < Width; j++)
        {graph[i][j] = new MapNode();
            graph[i][j].x = i; //地图坐标X
            graph[i][j].y = j; //地图坐标Y
            graph[i][j].value = map[i][j];
            graph[i][j].reachable = (graph[i][j].value == Reachable);    // 节点可到达性
            graph[i][j].sur = 0; //邻接节点个数
            if (!graph[i][j].reachable)
            {
                continue;
            }
            if (j > 0)
            {
                if (graph[i][j - 1].reachable)    // left节点可以到达
                {
                    graph[i][j].sur |= West;//每个方向占用一个bit 如果哪个位置不可到达 哪个位置的bit就为0
                    graph[i][j - 1].sur |= East;
                }
                if (i > 0)
                {
                    if (graph[i - 1][j - 1].reachable
                        && graph[i - 1][j].reachable
                        && graph[i][j - 1].reachable)    // up-left节点可以到达
                    {
                        graph[i][j].sur |= North_West;
                        graph[i - 1][j - 1].sur |= South_East;
                    }
                }
            }
            if (i > 0)
            {
                if (graph[i - 1][j].reachable)    // up节点可以到达
                {
                    graph[i][j].sur |= North;
                    graph[i - 1][j].sur |= South;
                }
                if (j < Width - 1)
                {
                    if (graph[i - 1][j + 1].reachable
                        && graph[i - 1][j].reachable
                        && map[i][j + 1] == Reachable) // up-right节点可以到达
                    {
                        graph[i][j].sur |= North_East;
                        graph[i - 1][j + 1].sur |= South_West;
                    }
                }
            }
        }
    }
}

int bfs()
{
    int times = 0;
    int i, curX, curY, surX, surY;
    int f = 0, r = 1;
    CloseSet p;
    CloseSet q[] =new CloseSet[MaxLength];
    q[0]=close[srcX][srcY] ;
  
    initClose(close, srcX, srcY, dstX, dstY);
    close[srcX][srcY].vis = true;
  
    while (r != f)
    {
        p = q[f];
        f = (f + 1) % MaxLength;
        curX = p.cur.x;
        curY = p.cur.y;
        for (i = 0; i < 8; i++)
        {
            if (0==(p.cur.sur & (1 << i)))
            {
                continue;
            }
            surX = curX + dir[i].x;
            surY = curY + dir[i].y;
            if (! close[surX][surY].vis)
            {
                close[surX][surY].from = p;
                close[surX][surY].vis = true;
                close[surX][surY].G = p.G + 1;
                q[r] = close[surX][surY];
                r = (r + 1) % MaxLength;
            }
        }
        times++;
    }
    return times;
}
  
int astar()
{    // A*算法遍历
    //int times = 0;
    int i, curX, curY, surX, surY;
    float surG;
    Open q=new Open(); //Open表
    CloseSet p=new CloseSet();
  
    initOpen(q);
    initClose(close, srcX, srcY, dstX, dstY);
    close[srcX][srcY].vis = true;
    push(q, close, srcX, srcY, 0);
  
    while (q.length!=0)
    {    //times++;
        p = shift(q);
        curX = p.cur.x;
        curY = p.cur.y;
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
            /*if(surX<0 || surY<0){
                continue;
            }*/
            if (!close[surX][surY].vis)//如果这个物体没有被到达过
            {
                close[surX][surY].vis = true;//设置为计算过 到达过
                close[surX][surY].from = p;//
                surG = (float)(p.G + Math.sqrt((curX - surX) * (curX - surX) + (curY - surY) * (curY - surY)));//父节点的g和子节点到他的距离的值
                push(q, close, surX, surY, surG);//压入8个的话 这里的while 就会循环8次 会压入8个之前从来没有计算过的
            }
        }
    }
    //printf("times: %d\n", times);
    return NoSolution; //无结果
}






      
String Symbol[] = new String[]{"□","▓","▽","☆","◎"};
      
    void printMap()
    {
        int i, j;
        for (i = 0; i < Height; i++)
        {
            for (j = 0; j < Width; j++)
            {
                System.out.printf("%s", Symbol[graph[i][j].value]);
            }
          System.out.println("");
        }
        System.out.println("");
    }
      
    CloseSet getShortest()
    {    // 获取最短路径
        int result = astar();
        CloseSet p, t, q = null;
        switch(result)
        {
        case Sequential:    //顺序最近
            p = close[dstX][dstY];
            while (p!=null)    //转置路径
            {
                t = p.from;// t 是temp
                p.from = q;//
                q = p;
                p = t;
            }
            close[srcX][srcY].from = q.from;
            return close[srcX][srcY];
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
                graph[p.cur.x][p.cur.y].value = Pass;
               System.out.printf("（%d，%d）→\n", p.cur.x, p.cur.y);
                p = p.from;
                step++;
            }
            System.out.printf("（%d，%d）\n", p.cur.x, p.cur.y);
            graph[srcX][srcY].value = Source;
            graph[dstX][dstY].value = Destination;
            return step;
        }
    }
      
    void clearMap()
    {    // Clear Map Marks of Steps
        CloseSet p = start;
        while (p!=null)
        {
            graph[p.cur.x][p.cur.y].value = Reachable;
            p = p.from;
        }
        graph[srcX][srcY].value = map[srcX][srcY];
        graph[dstX][dstY].value = map[dstX][dstY];
    }
      
    void printDepth()
    {
        int i, j;
        for (i = 0; i < Height; i++)
        {
            for (j = 0; j < Width; j++)
            {
                if (map[i][j]!=0)
                {
                    System.out.printf("%s ", Symbol[graph[i][j].value]);
                }
                else
                {
                    System.out. printf("%2.0lf ", close[i][j].G);
                }
            }
           System.out.println("");
        }
        System.out.println("");
    }
      
    void printSur()
    {
        int i, j;
        for (i = 0; i < Height; i++)
        {
            for (j = 0; j < Width; j++)
            {
                System.out.printf("%02x ", graph[i][j].sur);
            }
            System.out.println("");
        }
        System.out.println("");
    }
      
    void printH()
    {
        int i, j;
        for (i = 0; i < Height; i++)
        {
            for (j = 0; j < Width; j++)
            {
                System.out.printf("%02d ", close[i][j].H);
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public CloseSet start(int sX,int sY,int dX,int dY){
            initGraph(map, 0, 0, 0, 0);
         
         
         srcX=sX;
         srcY=sY;
         dstX=dX;
         dstY =dY;
        
             if (within(srcX, srcY) && within(dstX, dstY))
             {
                 
                 CloseSet p;
                 int step = 0;
               
                 p = getShortest();
                 start = p;
                 
                 return start;
             }
             else
             {
                return null;
             }
       
         
    }
      public void start(){
          initGraph(map, 0, 0, 0, 0);//初始化map
           printMap();
           
           srcX=1;
           srcY=5;
           dstX=14;
           dstY =19;
          
               if (within(srcX, srcY) && within(dstX, dstY))
               {
                   if ((shortestep = printShortest())>0)
                   {
                       System.out.printf("从（%d，%d）到（%d，%d）的最短步数是: %d\n",
                           srcX, srcY, dstX, dstY, shortestep);
                       printMap();
                       clearMap();
                       bfs();
                       //printDepth();
                       System.out.println((shortestep == close[dstX][dstY].G) ? "正确" : "错误");
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
       AStar aStar =new AStar();
       aStar.start();
      
    }
};

