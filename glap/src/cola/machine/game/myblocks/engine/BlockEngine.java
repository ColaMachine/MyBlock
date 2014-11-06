package cola.machine.game.myblocks.engine;

import cola.machine.game.myblocks.engine.modes.GameState;
import cola.machine.game.myblocks.engine.paths.PathManager;
import cola.machine.game.myblocks.engine.subsystem.EngineSubsystem;
import cola.machine.game.myblocks.engine.subsystem.lwjgl.LwjglGraphics;
import cola.machine.game.myblocks.engine.subsystem.lwjgl.LwjglInput;
import cola.machine.game.myblocks.engine.subsystem.lwjgl.LwjglTimer;
import cola.machine.game.myblocks.model.textture.TextureInfo;
import cola.machine.game.myblocks.utilities.concurrency.Task;
import cola.machine.game.myblocks.utilities.concurrency.TaskMaster;
import glapp.*;
import glmodel.GLModel;
import glmodel.GL_Vector;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Deque;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.crashreporter.CrashReporter;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import time.Time;
import cola.machine.game.myblocks.config.Config;
import cola.machine.game.myblocks.control.DropControlCenter;
import cola.machine.game.myblocks.control.MouseControlCenter;
import cola.machine.game.myblocks.item.weapons.Sword;
import cola.machine.game.myblocks.logic.players.LocalPlayerSystem;
import cola.machine.game.myblocks.magicbean.fireworks.Firework;
import cola.machine.game.myblocks.manager.TextureManager;
import cola.machine.game.myblocks.model.human.Human;
import cola.machine.game.myblocks.model.ui.NuiManager;
import cola.machine.game.myblocks.persistence.StorageManager;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.physic.BulletPhysics;
import cola.machine.game.myblocks.registry.CoreRegistry;
import cola.machine.game.myblocks.rendering.world.WorldRenderer;
import cola.machine.game.myblocks.rendering.world.WorldRendererLwjgl;
import cola.machine.game.myblocks.repository.BlockRepository;
import cola.machine.game.myblocks.resource.ResourceManager;
import cola.machine.game.myblocks.switcher.Switcher;
import cola.machine.game.myblocks.world.Skysphere;
import cola.machine.game.myblocks.world.WorldProvider;
import cola.machine.game.myblocks.world.block.BlockManager;
import cola.machine.game.myblocks.world.block.internal.BlockManagerImpl;
import cola.machine.game.myblocks.world.chunks.ChunkProvider;
import cola.machine.game.myblocks.world.chunks.LocalChunkProvider;
import cola.machine.game.myblocks.world.chunks.Internal.GeneratingChunkProvider;
import cola.machine.game.myblocks.world.generator.WorldGenerators.PerlinWorldGenerator;
import cola.machine.game.myblocks.world.internal.WorldProviderWrapper;

/**
 * Run a bare-bones GLApp. Draws one white triangle centered on screen.
 * <p/>
 * GLApp initializes the LWJGL environment for OpenGL rendering, ie. creates a
 * window, sets the display mode, inits mouse and keyboard, then runs a loop
 * that calls draw().
 * <p/>
 * napier at potatoland dot org
 */
public class BlockEngine implements GameEngine{
   private static final Logger logger =LoggerFactory.getLogger(BlockEngine.class);
	
   private GameState currentState;
   private boolean initialised;
   private boolean running;
   private boolean disposed;
   private GameState pendingState;
   
   private Config config;
   private EngineTime time ;
   private final TaskMaster<Task> commonThreadPool=TaskMaster.createFIFOTaskMaster("common",16);
  private boolean hibernationAllowed;
  
  private boolean gameFocused =true;
  private Set<StateChangeSubscriber> stateChangeSubscribers =Sets.newLinkedHashSet();
   private Deque<EngineSubsystem> subsystems;
	public BlockEngine(Collection<EngineSubsystem> subsystems){
		this.subsystems=Queues.newArrayDeque(subsystems);
	}
	
	public Iterable<EngineSubsystem> getSubsystems(){
		return subsystems;
	}

    public static void main(String args[]) {
    	boolean crashReportEnabled =true;
        try {
            PathManager.getInstance().useDefaultHomePath();

            Collection<EngineSubsystem> subsystemList;

            subsystemList = Lists.<EngineSubsystem>newArrayList(new LwjglGraphics(),new LwjglTimer(),new LwjglInput());

            // create the app
            BlockEngine engine = new BlockEngine(subsystemList);

          try{
              engine.init();
        	 // engine.run(new StateMainMenu());
          }finally {
              try {
                  engine.dispose();
              } catch (Exception e) {
                  // Just log this one to System.err because we don't want it 
                  // to replace the one that came first (thrown above).
                  e.printStackTrace();
              }
          }
        }catch(RuntimeException | IOException e){
        	Path logPath=Paths.get(".");
        	try{
        		Path gameLogPath = PathManager.getInstance().getLogPath();
        		if(gameLogPath!=null){
        			logPath =gameLogPath;
        		}
        	}catch (Exception eat){
        		
        	}
        	if(crashReportEnabled){
        		Path logFile =logPath.resolve("game.log");
        		
        		CrashReporter.report(e, logFile);
        	}
            e.printStackTrace();
            System.exit(0);
        }
    }


    @Override
    public void init() {
    	 initConfig();//初始化配置
    	 for(EngineSubsystem subsystem:subsystems){
    		 subsystem.preInitialise();//预先初始化各种设备
    	 }
    	 
    	 for(EngineSubsystem subsystem:subsystems){
    		 subsystem.postInitialise(config);//预先初始化各种设备
    	 }
    }

    @Override
    public void run(GameState initialState) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    @Override
    public GameState getState() {
        return null;
    }

    @Override
    public void changeState(GameState newState) {

    }

    @Override
    public void submitTask(String name, Runnable task) {

    }

    @Override
    public boolean isHibernationAllowed() {
        return false;
    }

    @Override
    public void setHibernationAlllowed(boolean allowed) {

    }

    @Override
    public boolean hasFocus() {
        return false;
    }

    @Override
    public boolean hasMouseFocus() {
        return false;
    }

    @Override
    public void setFocus(boolean focused) {

    }

    @Override
    public void subscribeToStateChange(StateChangeSubscriber subscriber) {

    }

    @Override
    public void unsubscribeToStateChange(StateChangeSubscriber subscriber) {

    }
    

    private void initConfig() {
    	
    	 config = new Config();
       /* if (Files.isRegularFile(Config.getConfigFile())) {
            try {
                config = Config.load(Config.getConfigFile());//加载配置文件
            } catch (IOException e) {
                logger.error("Failed to load config", e);
               
            }
        } else {
            config = new Config();
        }*/
       /* if (!config.getDefaultModSelection().hasModule(Constants.CORE_MODULE)) {
            config.getDefaultModSelection().addModule(Constants.CORE_MODULE);
        }*/
       
        //logger.info("Video Settings: " + config.getRendering().toString());
        CoreRegistry.putPermanently(Config.class, config);
    }

}
