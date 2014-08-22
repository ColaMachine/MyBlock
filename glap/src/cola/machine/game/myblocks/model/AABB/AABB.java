package cola.machine.game.myblocks.model.AABB;

public class AABB {
	public float minX,minY,maxX,maxY,minZ,maxZ;
	public void reCaculate(){
		
	}
	
		
	public boolean collision(AABB a){
		
		if(minX<=a.minX && a.minX<=maxX  
				&& minY<=a.minY && a.minY<=maxY  
				&& minZ<=a.minZ && a.minZ<=maxZ  
		){
			return true;
		}else if(
				minX<=a.maxX && a.maxX<=maxX  
				&& minY<=a.minY && a.minY<=maxY  
				&& minZ<=a.minZ && a.minZ<=maxZ  
		){
			return true;
		}else if(
				minX<=a.maxX && a.maxX<=maxX  
				&& minY<=a.maxY && a.maxY<maxY  
				&& minZ<=a.minZ && a.minZ<=maxZ  
		){
			return true;
		}else if(
				minX<=a.minX && a.minX<=maxX  
				&& minY<=a.maxY && a.maxY<maxY  
				&& minZ<=a.minZ && a.minZ<=maxZ  
		){
			return true;
		}else if(minX<=a.minX && a.minX<=maxX  
				&& minY<=a.minY && a.minY<=maxY  
				&& minZ<=a.maxZ && a.maxZ<=maxZ  
		){
			return true;
		}else if(
				minX<=a.maxX && a.maxX<=maxX  
				&& minY<=a.minY && a.minY<=maxY  
				&& minZ<=a.maxZ && a.maxZ<=maxZ  
		){
			return true;
		}else if(
				minX<=a.maxX && a.maxX<=maxX  
				&& minY<=a.maxY && a.maxY<maxY  
				&& minZ<=a.maxZ && a.maxZ<=maxZ  
		){
			return true;
		}else if(
				minX<=a.minX && a.minX<=maxX  
				&& minY<=a.maxY && a.maxY<maxY  
				&& minZ<=a.maxZ && a.maxZ<=maxZ  
		){
			return true;
		}else{
			return false;
		}
	/*	if(minX<a.minX && a.minX<maxX  
				&& minY<a.minY && a.minY<maxY  
		){
			return true;
		}else if(
				minX<a.maxX && a.maxX<maxX  
				&& minY<a.minY && a.minY<maxY  
		){
			return true;
		}else if(
				minX<a.maxX && a.maxX<maxX  
				&& minY<a.maxY && a.maxY<maxY  
		){
			return true;
		}else if(
				minX<a.minX && a.minX<maxX  
				&& minY<a.maxY && a.maxY<maxY  
		){
			return true;
		}else{
			return false;
		}*/
	}
}
