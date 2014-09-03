package cola.machine.game.myblocks.state;

import javax.vecmath.Vector3f;

import org.terasology.config.Config;
import org.terasology.engine.ComponentSystemManager;
import org.terasology.engine.subsystem.lwjgl.GLBufferPool;
import org.terasology.logic.manager.WorldTimeEventManager;
import org.terasology.logic.players.LocalPlayerSystem;
import org.terasology.math.TeraMath;
import org.terasology.monitoring.PerformanceMonitor;
import org.terasology.physics.bullet.BulletPhysics;
import org.terasology.registry.CoreRegistry;
import org.terasology.rendering.cameras.OculusStereoCamera;
import org.terasology.rendering.cameras.PerspectiveCamera;
import org.terasology.rendering.logic.LightComponent;
import org.terasology.rendering.primitives.ChunkTessellator;
import org.terasology.rendering.world.ChunkUpdateManager;
import org.terasology.rendering.world.Skysphere;
import org.terasology.world.WorldCommands;
import org.terasology.world.WorldProvider;
import org.terasology.world.chunks.ChunkProvider;

public class WorldRenderer {

    public void update(float delta) {

        PerformanceMonitor.startActivity("Update Tick");
        updateTick(delta);
        PerformanceMonitor.endActivity();

        worldProvider.processPropagation();

        // Free unused space
        PerformanceMonitor.startActivity("Update Chunk Cache");
        chunkProvider.update();
        PerformanceMonitor.endActivity();

        PerformanceMonitor.startActivity("Update Close Chunks");
        updateChunksInProximity(false);
        PerformanceMonitor.endActivity();

        PerformanceMonitor.startActivity("Skysphere");
        skysphere.update();
        PerformanceMonitor.endActivity();

        if (activeCamera != null) {
            activeCamera.update(delta);
        }

        if (lightCamera != null) {
            positionLightCamera();
            lightCamera.update(delta);
        }

        // And finally fire any active events
        PerformanceMonitor.startActivity("Fire Events");
        worldTimeEventManager.fireWorldTimeEvents();
        PerformanceMonitor.endActivity();

        smoothedPlayerSunlightValue = TeraMath.lerpf(smoothedPlayerSunlightValue, getSunlightValue(), delta);
    }
    
    
    public WorldRendererLwjgl(WorldProvider worldProvider, ChunkProvider chunkProvider, LocalPlayerSystem localPlayerSystem, GLBufferPool bufferPool) {
        this.chunkProvider = chunkProvider;
        this.worldProvider = worldProvider;
        bulletPhysics = new BulletPhysics(worldProvider);
        chunkTessellator = new ChunkTessellator(worldProvider, bufferPool);
        skysphere = new Skysphere(this);
        chunkUpdateManager = new ChunkUpdateManager(chunkTessellator, worldProvider);
        worldTimeEventManager = new WorldTimeEventManager(worldProvider);

        // TODO: won't need localPlayerSystem here once camera is in the ES proper
        systemManager = CoreRegistry.get(ComponentSystemManager.class);
        if (CoreRegistry.get(Config.class).getRendering().isOculusVrSupport()) {
            localPlayerCamera = new OculusStereoCamera();
        } else {
            localPlayerCamera = new PerspectiveCamera(CoreRegistry.get(Config.class).getRendering().getCameraSettings());
        }
        activeCamera = localPlayerCamera;

        mainDirectionalLight.lightType = LightComponent.LightType.DIRECTIONAL;
        mainDirectionalLight.lightColorAmbient = new Vector3f(1.0f, 1.0f, 1.0f);
        mainDirectionalLight.lightColorDiffuse = new Vector3f(1.0f, 1.0f, 1.0f);
        mainDirectionalLight.lightAmbientIntensity = 1.0f;
        mainDirectionalLight.lightDiffuseIntensity = 2.0f;
        mainDirectionalLight.lightSpecularIntensity = 0.0f;

        localPlayerSystem.setPlayerCamera(localPlayerCamera);
        config = CoreRegistry.get(Config.class);
        CoreRegistry.get(ComponentSystemManager.class).register(new WorldCommands(chunkProvider));
        initTimeEvents();
    }
}
