package cola.machine.game.myblocks.entitySystem.entity;

import cola.machine.game.myblocks.asset.AssetUri;
import cola.machine.game.myblocks.engine.entitySystem.event.Event;
import cola.machine.game.myblocks.entitySystem.Component;
import cola.machine.game.myblocks.entitySystem.prefab.Prefab;

import java.util.Collections;

public final class NullEntityRef extends EntityRef{
	private static NullEntityRef  instatnce =new NullEntityRef();
	
	private NullEntityRef(){
		
	}
	public static NullEntityRef getInstance(){
		return instatnce;
	}
	@Override
	public EntityRef copy() {
		
		return this;
	}

	@Override
	public boolean isActive() {
		return false;
	}
	public boolean exists(){
		return false;
	}
	
	public boolean hasComponent(Class<? extends Component> compoent){
		return false;
	}
	@Override
	public void destroy() {
		
	}



	@Override
	public int getId() {
		return 0;
	}

	@Override
	public boolean isPersistent() {
		return false;
	}

	@Override
	public void setPersistent(boolean persistent) {
		
	}

	@Override
	public boolean isAlwaysRelevant() {
		return false;
	}

	@Override
	public void setAlwaysRelevent(boolean alwaysRelevant) {
		
	}

	@Override
	public EntityRef getOwner() {
		return null;
	}

	@Override
	public void setOwner(EntityRef owner) {
		
	}

	@Override
	public Prefab getParentPrefab() {
		return null;
	}

	@Override
	public AssetUri getPrefabURI() {
		return null;
	}
	@Override
	public <T extends Component> T addComponent(T component) {
		return null;
	}
	@Override
	public void removeComponent(Class<? extends Component> componentClass) {
		
	}
	@Override
	public void saveComponent(Component component) {
		
	}
	@Override
	public <T extends Component> T getComponent(Class<T> componentClass) {
		return null;
	}
	@Override
	public Iterable<Component> iterateComponents() {
		return Collections.emptyList();
	}
	@Override
	public <T extends Event> T send(T event) {
		return event;
	}
	
	public String toString(){
		return "EntityRef{"+ "id="+"0"+
				'}';
	}
	public int hashCoe(){
		return 0;
	}
	
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if( o instanceof EntityRef){
			return !((EntityRef)o).exists();
		}
		return o==null;
	}
	
}
