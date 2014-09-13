package util;

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
public static void main(String args[]){
	System.out.println(MathUtil.floor((float)(-10)/16));
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
}
