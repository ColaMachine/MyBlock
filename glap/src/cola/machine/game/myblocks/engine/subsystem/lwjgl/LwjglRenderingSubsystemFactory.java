package cola.machine.game.myblocks.engine.subsystem.lwjgl;

import cola.machine.game.myblocks.engine.subsystem.RenderingSubsystemFactory;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;

public class LwjglRenderingSubsystemFactory implements RenderingSubsystemFactory{
	
	private GLBufferPool bufferPool;
	
	public LwjglRenderingSubsytemFactory(GLBufferPool bufferPool){
		this.bufferPool = bufferPool;
	}
	@Override
	public WorldRenderer createWorldRenderer(WorldProvider worldProvider,
			ChunkProvider chunkProvider, LocalPlayerSystem localPlayerSystem) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
