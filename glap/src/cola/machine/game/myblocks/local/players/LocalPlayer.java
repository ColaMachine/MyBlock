package cola.machine.game.myblocks.local.players;

import cola.machine.game.myblocks.entitySystem.entity.EntityRef;
import cola.machine.game.myblocks.network.ClientComponent;

public class LocalPlayer {
	private EntityRef clientEntity =EntityRef.NULL;
	public LocalPlayer(){
		
	}
	
	public void setClientEntity(EntityRef entity){
		this.clientEntity =entity;
		ClientComponent clientComp =entity.getComponent(ClientComponent.class);
		if(clientComp!=null){
			clientComp.local=true;
			entity.saveComponent(clientComp);
		}
		
	}
	
	public EntityRef getClientEntity(){
		return clientEntity;
	}
	
	public EntityRef getCharacterEntity(){
		ClientComponent client = clientEntity.getComponent(ClientComponent.class);
		if(client != null ){
			return client.character;
		}
		return EntityRef.NULL;
	}
	
	public boolean isValid(){
		EntityRef characterEntity=getCharacterEntity();
		return characterEntity.exists()&&characterEntity.hasComponent(LocationComponent.class) && characterEntity.hasComponent(CharacterComponent.class)
	}
	
}
