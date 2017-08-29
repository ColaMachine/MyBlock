package cola.machine.game.myblocks.input.internal;

import cola.machine.game.myblocks.engine.SimpleUri;
import cola.machine.game.myblocks.engine.Time;
import cola.machine.game.myblocks.entitySystem.entity.EntityRef;
import cola.machine.game.myblocks.input.*;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.registry.CoreRegistry;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.vecmath.Vector3f;
import java.util.List;
import java.util.Set;

public class BindableButtonImpl implements BindableButton{
	private SimpleUri id;
	private String displayName;
	private BindButtonEvent buttonEvent;
	private Set<Input> activeInputs=Sets.newHashSet();
	
	private List<BindButtonSubscriber> subscribers =Lists.newArrayList();
	private ActivateMode mode =ActivateMode.BOTH;
	private boolean repeating;
	private int repeatTime;
	private long lastActivateTime;
	private boolean consumedActivation;
	private Time time;
	
	public BindableButtonImpl(SimpleUri id,String displayName,BindButtonEvent event){
		this.id=id;
		this.displayName=displayName;
		this.buttonEvent=event;
		time=CoreRegistry.get(Time.class);
	}
	@Override
	public SimpleUri gtId() {
		return id;
	}
	@Override
	public String getDisplayName() {
		return displayName;
	}
	@Override
	public void setMode(ActivateMode mode) {
		this.mode=mode;
		
	}
	@Override
	public ActivateMode getMode() {
		return mode;
	}
	@Override
	public void setRepeating(boolean repeating) {
		this.repeating=repeating;
		
	}
	@Override
	public boolean isRepeating() {
		return repeating;
	}
	public void setRepeatTime(int repeatTimeMs){
		this.repeatTime =repeatTime;
		
	}
	public int getRepeatTime(){
		return repeatTime;
	}
	@Override
	public ButtonState getState() {
		return (activeInputs.isEmpty()||consumedActivation)?ButtonState.UP:ButtonState.DOWN;
	}
	@Override
	public void subscribe(BindButtonSubscriber subscriber) {
		subscribers.add(subscriber);
		
	}
	
	public void unsubscribe(BindButtonSubscriber subscriber){
		subscribers.remove(subscriber);
	}
	public boolean updateBindState(Input input,
			boolean pressed,
			float delta,
			EntityRef[] inputEntities,
			EntityRef target,
			Vector3i targetBlockPos,
			Vector3f hitPosition,
			Vector3f hitNormal,
			boolean initialKeyConsumed){
		boolean keyConsumed = initialKeyConsumed;
		if(pressed){
			boolean previouslyEmpty=activeInputs.isEmpty();
			activeInputs.add(input);
			if(previouslyEmpty && mode.isActivatedOnPress()){
				lastActivateTime = time.getGameTimeInMs();
				consumedActivation = keyConsumed;
				if(!keyConsumed){
					keyConsumed = triggerOnPress(delta,target);
				}
				if(!keyConsumed){
					buttonEvent.prepare(id, ButtonState.DOWN, delta);
					buttonEvent.setTargetInfo(target,targetBlockPos,hitPosition,hitNormal);
					for(EntityRef entity:inputEntities){
						//entity.send(buttonEvent);
						if(buttonEvent.isConsumed()){
							break;
						}
					}
					keyConsumed=buttonEvent.isConsumed();
				}
			}
		}else if(!activeInputs.isEmpty()){
			activeInputs.remove(input);
			if(activeInputs.isEmpty()&& mode.isActivatedOnRelease()){
				if(!keyConsumed){
					keyConsumed=triggerOnRelease(delta,target);
				}
				if(!keyConsumed){
					buttonEvent.prepare(id,ButtonState.UP,delta);
					buttonEvent.setTargetInfo(target, targetBlockPos, hitPosition, hitNormal);
					for(EntityRef entity:inputEntities){
						//entity.send(buttonEvent);
						if(buttonEvent.isConsumed()){
							break;
						}
					}
					keyConsumed=buttonEvent.isConsumed();
				}
			}
		}
		return keyConsumed;
	}
	private boolean triggerOnPress(float delta,EntityRef target){
		for(BindButtonSubscriber subscriber:subscribers){
			if(subscriber.onPress(delta, target)){
				return true;
			}
		}
		return false;
	}
	
	private boolean triggerOnRelease(float delta,EntityRef target){
		for(BindButtonSubscriber subscriber:subscribers){
			if(subscriber.onRelease(delta, target)){
				return true;
			}
		}
		return false;
	}
	
	
	private boolean triggerOnRepeat(float delta,EntityRef target){
		for(BindButtonSubscriber subscriber:subscribers){
			if(subscriber.onRepeat(delta, target)){
				return true;
			}
		}
		return false;
	}
	
	  @Override
	    public String toString() {
	        return "BindableButtonEventImpl [" + id + ", \"" + displayName + "\", " + buttonEvent + "]"; 
	    }
}
