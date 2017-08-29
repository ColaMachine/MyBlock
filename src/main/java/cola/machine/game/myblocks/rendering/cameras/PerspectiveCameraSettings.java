package cola.machine.game.myblocks.rendering.cameras;

import cola.machine.game.myblocks.rendering.nui.layers.mainMenu.videoSettings.CameraSetting;

public class PerspectiveCameraSettings {
	private CameraSetting cameraSetting;
	public PerspectiveCameraSettings(CameraSetting cameraSetting){
		this.cameraSetting=cameraSetting;
	}
	public CameraSetting getCameraSetting(){
		return cameraSetting;
	}
	public void setCameraSetting(CameraSetting cameraSeetting){
		this.cameraSetting=cameraSetting;
	}
	public int getSmoothingFramesCount(){
		return cameraSetting.getSmoothingFrames();
	}
}	
