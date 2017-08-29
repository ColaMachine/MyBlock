package cola.machine.game.myblocks.icon;

import cola.machine.game.myblocks.container.Slot;

public interface Icon {
	static double width=0;
	static double height=0;
	
	public void renderIn(Slot slot);
}
