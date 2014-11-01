package cola.machine.game.myblocks.rendering.nui.layers.mainMenu.videoSettings;

public enum CameraSetting {
	NORMAL("Normal",1),
	SMOOTH("Smooth",5),
	CINEMATIC("Cinematic",60);
	private String displayName;
	private int smoothingFrames;
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getSmoothingFrames() {
		return smoothingFrames;
	}

	public void setSmoothingFrames(int smoothingFrames) {
		this.smoothingFrames = smoothingFrames;
	}

	private CameraSetting(String displayName,int smoothingFrames){
		this.displayName=displayName;
		this.smoothingFrames=smoothingFrames;
		
	}
	
}
