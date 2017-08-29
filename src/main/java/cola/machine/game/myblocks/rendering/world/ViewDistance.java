package cola.machine.game.myblocks.rendering.world;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public enum ViewDistance {
	LEGALLY_BLIND("Legally Blind",0,3),
	NEAR("Near",1,8),
	MODERATE("Moderate",2,16),
	FAR("Far",3,32),
	ULTRA("Ultra",4,64),
	MEGA("Mega",5,128),
	EXTREME("Extreme",6,521);
	
	private static TIntObjectMap<ViewDistance> indexLookup=new TIntObjectHashMap<>();
	
	private String displayName;
	private int chunkDistance;
	private int index;
	
	static {
		for (ViewDistance viewDistance: ViewDistance.values()){
			indexLookup.put(viewDistance.getIndex(),viewDistance);
		}
	}

	private ViewDistance(String displayName,int index, int chunkDistance){
		this.displayName=displayName;
		this.index=index;
		this.chunkDistance= chunkDistance;
	}
	public int getChunkDistance(){
		return chunkDistance;
	}
	
	public int getIndex(){
		return index;
	}
	
	public static ViewDistance forIndex(int viewDistanceLevel){
		ViewDistance result =indexLookup.get(viewDistanceLevel);
		if(result == null){
			return LEGALLY_BLIND;
		}
		return result;
		
	}
	  public String toString() {
	        return displayName;
	    }
}
