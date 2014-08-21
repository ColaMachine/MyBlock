package util;

public class MathUtil {
	public static int getNearOdd(float num){//����
		int x=(int)Math.rint((double)num);
		//�ж�x��ż���������� 
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
			//�ж�x��ż���������� 
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
	System.out.println(getNearOdd(10f));
}
}
