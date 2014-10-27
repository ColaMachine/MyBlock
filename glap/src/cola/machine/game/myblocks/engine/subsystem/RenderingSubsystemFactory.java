package cola.machine.game.myblocks.engine.subsystem;

import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;

/**
 * Created by luying on 14/10/27.
 */
public interface RenderingSubsystemFactory {//可渲染的子系统工程类
	WorldRenderer createWorldRenderer(WorldProvider worldProvider,ChunkProvider chunkProvider,LocalPlayerSystem localPlayerSystem);
}
