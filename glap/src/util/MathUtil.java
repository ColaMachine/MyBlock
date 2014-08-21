package util;

public class MathUtil {
	public static int getNearOdd(float num){//奇数
		int x=(int)Math.rint((double)num);
		//判断x是偶数还是奇数 
		//对2取模
		
		if(x%2==1){
			//是奇数
			return (int)x;
		}else{
			//是偶数
			if(x>num){
				return x-1;
				
			}else{
				return x+1;
			}
		}
		
	}
	
public static int getNearEven(float num){//偶数
			int x=(int)Math.rint((double)num);
			//判断x是偶数还是奇数 
			//对2取模
			
			if(x%2==0){
				//是奇数
				return (int)x;
			}else{
				//是偶数
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
