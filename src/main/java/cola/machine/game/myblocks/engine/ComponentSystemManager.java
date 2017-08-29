package cola.machine.game.myblocks.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.terasology.module.ModuleEnvironment;

public class ComponentSystemManager {
	private static final Logger logger =LoggerFactory.getLogger(ComponentSystemManager.class);
/*
	private Map<String,ComponentSystem> namedLookup=Maps.newHashMap();
	private List<UpdateSubscriberSystem> updateSubscribers=Lists.newArrayList();
	private List<RenderSystem> renderSubscribers=Lists.newArrayList();
	private List<ComponentSystem> store=Lists.newArrayList();
	private Console console;

	private boolean initialised;

	public ComponentSystemManager(){

	}

	public void loadSystems(ModuleEnvironment environment,NetworkMode netMode){
		DisplayDevice displayDevice=CoreRegistry.get(DisplayDevice.class);
		boolean siHeadless = displayDevice.isHeadless();

		ListMultimap<Name,Class<?>> systemsByModule =ArrayListMultimap.create();
		for(Class<?> type:environment.getTypesAnnotatedWith(RegisterSystem.class)){
			if(!ComponentSystem.class.isAssignableFrom(type)){
				logger.error("Cannot load {} , must be a subclass of ComponentSystem",type.getSimpleName());
				continue;
			}
			Name moduleId =environment.getModuleProviding(type);
			RegisterSystem registerInfo =type.getAnnotation(RegisterSystem.class);
			if(registerInfo.value().isValidFor(netMode,isHeadless)){
				systemsByModule.put(moduleId,type);
			}
		}

		for(Module module : environment.getModuleOrderedByDependecies)
	}*/
}
