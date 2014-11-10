package cola.machine.game.myblocks.input;

public enum SendEventMode {
	
	ALWAYS{

		@Override
		public boolean shouldSendEvent(float oldValue, float newValue) {
			return true;
		}
		
	},
	WHEN_NON_ZERO{

		@Override
		public boolean shouldSendEvent(float oldValue, float newValue) {
			
			return newValue!=0;
		}
		
	},
	WHEN_CHANGED{

		@Override
		public boolean shouldSendEvent(float oldValue, float newValue) {
			// TODO Auto-generated method stub
			return oldValue!=newValue;
		}
		
	};
	
	public abstract boolean shouldSendEvent(float oldValue,float newValue);
}
