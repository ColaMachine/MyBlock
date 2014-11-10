package cola.machine.game.myblocks.entitySystem.entity;

import cola.machine.game.myblocks.asset.AssetUri;
import cola.machine.game.myblocks.engine.entitySystem.event.Event;
import cola.machine.game.myblocks.entitySystem.MutableComponentContainer;
import cola.machine.game.myblocks.entitySystem.prefab.Prefab;
import cola.machine.game.myblocks.registry.CoreRegistry;

public abstract class EntityRef implements MutableComponentContainer{
	public static final EntityRef NULL =NullEntityRef.getInstance();
	public abstract EntityRef copy();
	public abstract boolean exists();
	public abstract boolean isActive();
	public abstract void destroy();
	public abstract <T extends Event> T send(T event);
	public abstract int getId();
	public abstract boolean isPersistent();
	
	public abstract void setPersistent(boolean persistent);
	public abstract boolean isAlwaysRelevant();
	
	public abstract void setAlwaysRelevent(boolean alwaysRelevant);
	public abstract EntityRef getOwner();
	public abstract  void setOwner(EntityRef owner);
	public abstract Prefab getParentPrefab();
	public abstract AssetUri getPrefabURI();
//	public String toFullDescription(){
//		EntitySerializer serializer= new EntitySerializer((EngineEntityManager) CoreRegistry.get(EntityManager.class));
//		
//	}
}
