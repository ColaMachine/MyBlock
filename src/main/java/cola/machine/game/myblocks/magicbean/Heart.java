package cola.machine.game.myblocks.magicbean;

import com.dozenx.util.MathUtil;
import glapp.GLApp;
import org.lwjgl.opengl.GL11;

public class Heart {
	int handleId;
public void create(){
	 handleId = GLApp.beginDisplayList();
     GL11.glPointSize(5);

     double x;
     double y;
     int acc=10;
     double yrange=3;
     double xrange=3;
     //正面数组
     //正面数组
     //侧面数组
     double[] arr = new double[2*((int)((2*xrange+1)*acc))*((int)(yrange*acc))];
     //double[] arr2 = new double[4*((int)(xrange*acc))*((int)(yrange*acc))+100];
   //  GL11.glBegin(GL11.GL_POINTS);
     for(x=-xrange;x<=xrange;x+=1.0/acc){
         for(y=-yrange;y<=yrange;y+=1.0/acc){
             double[] result= MathUtil.shengjin(x,y);
             int index =(int)(((x+xrange)*2*yrange*acc+y+yrange)*acc);
             if(result!=null ){

                 arr[index]=result[0];
                 //arr2[index]=-result[0];
                 //arr[index]=result[0];
                 //System.out.println("heart:" + result[0]);
                 //GL11. glVertex3f((float)x,(float)result[0],(float)y);
                 //GL11. glVertex3f((float)x,-(float)result[0],(float)y);

                // GL11. glVertex3f((float)x,(float)y,(float)result[0]);
               //  GL11. glVertex3f((float)x,(float)y,-(float)result[0]);
                 // GL11. glVertex3f((float)x,(float)result[1],(float)y);
                 //  GL11. glVertex3f((float)x,(float)result[2],(float)y);
                 //  GL11. glVertex3f((float)x,(float)result[1],(float)y);
             }else{
                 arr[index]=-1;
             }
         }
     }
     // Create texture for spere
    // GL11.glEnd();
     GL11.glBegin(GL11.GL_TRIANGLES);
     for( x=-xrange;x<xrange-1.0/acc;x+=1.0/acc){
         for( y=-yrange;y<yrange-1.0/acc;y+=1.0/acc){
             int index =(int)(((x+xrange)*2*yrange*acc+(y+yrange))*acc);
             //System.out.println(index);
             double result=0;
             try {
                 result = arr[index];
             }catch (Exception e){
                 e.printStackTrace();
             }
            // if(result!=-1 &&result<=0)
             if(result >=0 && result <=1 ){
                 if(result >=0 ){

                 }else{
                     System.out.println("123:"+Double.isNaN(result));
                 }
                 if(x<xrange-1.0/acc&& y<yrange){
                     try {
                     double right = arr[(int)(((x+xrange +1.0/acc)*2*yrange*acc+(y+yrange))*acc)];

                     double right_up = arr[(int) (((x + xrange + 1.0 / acc) * 2 * yrange * acc + (y + yrange + 1.0 / acc)) * acc)];

                     double up =arr[(int)(((x+xrange )*2*yrange*acc+(y+yrange+1.0/acc))*acc)];

                         if(x+xrange>0){
                             double left=arr[(int)(((x+xrange -1.0/acc)*2*yrange*acc+(y+yrange))*acc)];
                             double left_up=arr[(int)(((x+xrange -1.0/acc)*2*yrange*acc+(y+yrange+1.0/acc))*acc)];

                             //double right_down=arr[(int)(((x+xrange +1.0/acc)*2*yrange*acc+(y+yrange-1.0/acc))*acc)];
                            // double down=arr[(int)(((x+xrange )*2*yrange*acc+(y+yrange-1.0/acc))*acc)];
                             if((left==-1|| left_up ==-1)&&up!=-1){
                                 //System.out.println(result);
                                 GL11. glVertex3f((float)x,(float)(y),-(float)result);
                                 GL11. glVertex3f((float)(x),(float)y,(float)result);
                                 GL11. glVertex3f((float)(x),(float)(y+1.0/acc),(float)up);

                                 GL11. glVertex3f((float)(x),(float)(y+1.0/acc),(float)up>=1?0:(float)up);
                                 GL11. glVertex3f((float)(x),(float)(y+1.0/acc),-(float)up);
                                 GL11. glVertex3f((float)x,(float)(y),-(float)result);
                             }
                             if((up==-1|| right_up==-1)&&right!=-1){
                                 //System.out.println(result);
                                 GL11. glVertex3f((float)x,(float)(y),-(float)result);
                                 GL11. glVertex3f((float)(x),(float)y,(float)result);
                                 GL11. glVertex3f((float)(x+1.0/acc),(float)(y),(float)right);

                                 GL11. glVertex3f((float)(x+1.0/acc),(float)(y),(float)right);
                                 GL11. glVertex3f((float)(x+1.0/acc),(float)(y),-(float)right);
                                 GL11. glVertex3f((float)x,(float)(y),-(float)result);
                             }

                             if((right==-1||right_up==-1)&&up!=-1){
                                 //System.out.println(result);
                                 GL11. glVertex3f((float)x,(float)(y),(float)result);
                                 GL11. glVertex3f((float)(x),(float)y,-(float)result);
                                 GL11. glVertex3f((float)(x),(float)(y+1.0/acc),-(float)up);

                                 GL11. glVertex3f((float)(x),(float)(y+1.0/acc),-(float)up);
                                 GL11. glVertex3f((float)(x),(float)(y+1.0/acc),(float)up);
                                 GL11. glVertex3f((float)x,(float)(y),(float)result);
                             }
                         }

                     if(right==-1)right=0;
                         if(right_up==-1)right_up=0;
                         if(up==-1)up=0;
                     if(right!=0 && right_up!=0 && up!=0) {//如果没有的话就要去对立面找一下
                         GL11. glVertex3f((float)x,(float)(y+1.0/acc),(float)up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)y,(float)right);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)(y+1.0/acc),(float)right_up);

                         GL11. glVertex3f((float)x,(float)y,(float)result);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)y,(float)right);
                         GL11. glVertex3f((float)x,(float)(y+1.0/acc),(float)up);


                         GL11. glVertex3f((float)x,(float)(y+1.0/acc),-(float)up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)(y+1.0/acc),-(float)right_up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)y,-(float)right);

                         GL11. glVertex3f((float)x,(float)y,-(float)result);
                         GL11. glVertex3f((float)x,(float)(y+1.0/acc),-(float)up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)y,-(float)right);
                     }
                     }catch(Exception e){
                         e.printStackTrace();
                     }
                 }
             }
            result=-result ;
             if(result!=0 ){
                 if(x<xrange-1.0/acc&& y<yrange){
                     double   right_down = arr[(int)(((x+xrange +1.0/acc)*2*yrange*acc+(y+yrange))*acc)];
                     double   right_up = arr[(int)(((x+xrange +1.0/acc)*2*yrange*acc+(y+yrange+1.0/acc))*acc)];
                     double up =arr[(int)(((x+xrange )*2*yrange*acc+(y+yrange+1.0/acc))*acc)];

                     if(right_down==-1)right_down=0;
                     if(right_up==-1)right_up=0;
                     if(up==-1)up=0;

                     if(right_down!=0 && right_up!=0 && up!=0) {/*
                         GL11. glVertex3f((float)x,(float)(y+1.0/acc),-(float)up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)(y+1.0/acc),-(float)right_up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)y,-(float)right_down);

                         GL11. glVertex3f((float)x,(float)y,(float)result);
                         GL11. glVertex3f((float)x,(float)(y+1.0/acc),-(float)up);
                         GL11. glVertex3f((float)(x+1.0/acc),(float)y,-(float)right_down);*/
                     }
                 }
                 //System.out.println("heart:" + result[0]);
                 // GL11. glVertex3f((float)x,(float)y,(float)result[0]);
                 //  GL11. glVertex3f((float)x,(float)y,(float)result[1]);
             }


         }
     }


     //blockRepository.reBuild("soil");
    GL11. glEnd();
     GLApp.endDisplayList();
}
}
