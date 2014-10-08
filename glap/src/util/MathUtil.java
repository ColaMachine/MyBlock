package util;

import cola.machine.game.myblocks.world.chunks.Vector3i;

public class MathUtil {
	public static int getNearOdd(float num){//����
		int x=(int)Math.rint((double)num);
		//�ж�x��ż�������� 
		//��2ȡģ
		
		if(x%2==1){
			//������
			return (int)x;
		}else{
			//��ż��
			if(x>num){
				return x-1;
				
			}else{
				return x+1;
			}
		}
		
	}
	
public static int getNearEven(float num){//ż��
			int x=(int)Math.rint((double)num);
			//�ж�x��ż�������� 
			//��2ȡģ
			
			if(x%2==0){
				//������
				return (int)x;
			}else{
				//��ż��
				if(x>num){
					return x-1;
				}else{
					return x+1;
				}
			}
			
		
	}
public static  float distance(int preX, int preY, int x, int y) {
	// System.out.printf(" %d %d %d %d  \r\n",preX,preY,x,y);
	int diffX = x - preX;
	int diffY = y - preY;
	return (float) Math.sqrt((double) (diffX * diffX + diffY * diffY));
}
public static  float distance(float preX, float preY, float x, float y) {
	// System.out.printf(" %d %d %d %d  \r\n",preX,preY,x,y);
	float diffX = x - preX;
	float diffY = y - preY;
	return (float) Math.sqrt(diffX * diffX + diffY * diffY);
}

public static int floor(float f) {
	return (int)Math.floor(f);
}
public static int getBelongChunkInt(float f){
	int g=(int)(f/16);
	if(f<0)g-=1;
	return g;
}
public static int getOffesetChunk(float f){
	int g=floor(f)%16;
	if( g<0)g+=16;
	return g;
}
    public static int getChunkX(float x){
       return  (int)(x/16);
    }
    public static int getChunkY(float y){
        return  (int)(y/16);
    }
    public static int getChunkZ(float z){
        return  (int)(z/16);
    }



    public static double[] result(double x,double y){
        double a;
        double b;
        double c;
        double x1;
        double x2;
        a=1;
        b=-Math.pow(x*x+9/80*y*y,1.0/3);
        c=-1+9/4*y*y+x*x;
       double nanda= b*b-4*a*c;

        if(nanda>=0){
            x1=( -b+Math.pow(nanda,1.0/2))/2;
            x2=( -b-Math.pow(nanda,1.0/2))/2;
            return new double[]{x1,x2};
        }


        return null;
     }
    public static double[] shengjin(double y,double z){

        double _a=-y*y+z*z-1;
        double _b=-z*z*z*9.0/80;
        double _c=-y*y*z*z*z;
        double _d = 9.0/4;
        double a= _d*_d;
        double b=2*_a*_d+_a*_d*_d;
        double c=_a*_a*_d+2*_a*_a*_d+_b;
        double d=_c+_a*_a*_a;
        System.out.printf("a: %f  b:%f c:%f d:%f",a,b,c,d);
        if(a==0){
            return new double[]{0,0};
        }


        double A= b*b-3*a*c;
        double B=B*c-9*a*d;
        double C = c*c-3*b*d;
        double part1 =    ( 27 *a*a*d-9*a*b*c+2*b*b*b)/(54*a*a*a);
        double part2=(3*a*c-b*b)/(9*a*a);
        double part3=part1*part1+part2*part2*part2;

        if(part3<0){
            return new double[]{0,0};
        }
        double part4=part1+Math.pow(part3,1.0/2);
        if(part4<0){
            return new double[]{0,0};
        }
        double part5=part1-Math.pow(part3,1.0/2);
        if(part5<0){
            return new double[]{0,0};
        }
        double x= -b/(3*a)+
                Math.pow(
                        part4
                        ,  1.0/2)+
                Math.pow(
                        part5
                        ,  1.0/2);

        return new double[]{1,x};
    }
    public static double[] y13(double y,double z){

        double _a=-y*y+z*z-1;
        double _b=-z*z*z*9.0/80;
        double _c=-y*y*z*z*z;
        double _d = 9.0/4;
        double a= _d*_d;
        double b=2*_a*_d+_a*_d*_d;
        double c=_a*_a*_d+2*_a*_a*_d+_b;
        double d=_c+_a*_a*_a;
System.out.printf("a: %f  b:%f c:%f d:%f",a,b,c,d);
        if(a==0){
            return new double[]{0,0};
        }

        double part1 =    ( 27 *a*a*d-9*a*b*c+2*b*b*b)/(54*a*a*a);
        double part2=(3*a*c-b*b)/(9*a*a);
        double part3=part1*part1+part2*part2*part2;

        if(part3<0){
            return new double[]{0,0};
        }
        double part4=part1+Math.pow(part3,1.0/2);
        if(part4<0){
            return new double[]{0,0};
        }
        double part5=part1-Math.pow(part3,1.0/2);
        if(part5<0){
            return new double[]{0,0};
        }
        double x= -b/(3*a)+
                Math.pow(
                        part4
                        ,  1.0/2)+
                Math.pow(
                        part5
                        ,  1.0/2);

        return new double[]{1,x};
    }
    public static double[] y12(double y,double z){

        double _a=-y*y+z*z-1;
        double _b=z*z*z/10;
        double _c=y*y*z*z*z;
        double a= 8;
        double b=12*_a-_b;
        double c=6*_a*_a;
        double d=_a*_a*_a-c;

        if(a==0){
            return new double[]{0,0};
        }

        double part1 =    ( 27 *a*a*d-9*a*b*c+2*b*b*b)/(54*a*a*a);
        double part2=(3*a*c-b*b)/(9*a*a);
        double part3=part1*part1+part2*part2*part2;

        if(part3<0){
            return new double[]{0,0};
        }
        double part4=part1+Math.pow(part3,1.0/2);
        if(part4<0){
            return new double[]{0,0};
        }
        double part5=part1-Math.pow(part3,1.0/2);
        if(part5<0){
            return new double[]{0,0};
        }
        double x= -b/(3*a)+
                Math.pow(
                       part4
                        ,  1.0/2)+
        Math.pow(
                 part5
                ,  1.0/2);

        return new double[]{1,x};
    }
    public static void main(String args[]){
        //System.out.println(y12(0,0)[0]);
        //System.out.println(  Math.ceil(1.001));
        System.out.println(  y13(0,0)[0]);
      ;
       /* double x=-3;
        x+=1.0/10;
        System.out.println(x);
    double y;
        double acc =10;
        for( x=-3;x<=3;x+=1.0/acc) {
            for (y = -3; y <= 3; y += 1.0 / acc) {
                int index = (int) (((x + 3) * 6*acc + (y + 3)) * acc);
                System.out.println(index);
            }
        }*/
    }

}
