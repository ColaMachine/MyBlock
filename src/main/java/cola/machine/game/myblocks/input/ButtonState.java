package cola.machine.game.myblocks.input;

public enum	 ButtonState {
	DOWN(true),
	UP(false),
	REPEAT(true);
	private boolean down;
	private ButtonState(boolean down){
		this.down=down;
	}
	
	public boolean isDown(){
		return down;
	}
}
