package cola.machine.game.myblocks.input;

public enum ActivateMode {
PRESS(true,false),
RELEASE(false,true),
BOTH(true,true);
private boolean activatedOnPress;
private boolean activatedOnRelease;
private ActivateMode(boolean activatedOnPress,boolean activatedOnRelease){
	this.activatedOnPress=activatedOnPress;
	this.activatedOnRelease=activatedOnRelease;
}
public boolean isActivatedOnPress(){
	return activatedOnPress;
}
public boolean isActivatedOnRelease(){
	return activatedOnRelease;
}
}
