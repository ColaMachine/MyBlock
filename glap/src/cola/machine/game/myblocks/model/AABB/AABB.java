package cola.machine.game.myblocks.model.AABB;


public class AABB {
	public float minX,minY,minZ,maxX,maxY,maxZ;

	  public boolean overlaps(AABB aabb2) {
	        return !(maxX < aabb2.minX || minX > aabb2.maxX)
	                && !(maxY < aabb2.minY || minY > aabb2.maxY)
	                && !(maxZ < aabb2.minZ || minZ > aabb2.maxZ);
	    }
}

