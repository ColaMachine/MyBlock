package cola.machine.game.myblocks.model;


public interface  Block {
	public String getName();
	
	public void render();
	public void setCenter(int x,int y,int z);
	public int getX();
	public int getY();
	public int getZ();
}
