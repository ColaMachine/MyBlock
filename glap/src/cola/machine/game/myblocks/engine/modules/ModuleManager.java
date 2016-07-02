package cola.machine.game.myblocks.engine.modules;

import cola.machine.game.myblocks.engine.Constants;
import cola.machine.game.myblocks.engine.paths.PathManager;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.terasology.module.*;
//import org.terasology.module.sandbox.APIScanner;
//import org.terasology.module.sandbox.BytecodeInjector;
//import org.terasology.module.sandbox.ModuleSecurityManager;
//import org.terasology.module.sandbox.ModuleSecurityPolicy;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ReflectPermission;
import java.net.URISyntaxException;
import java.security.Policy;
import java.util.Collections;
import java.util.Set;

/**
 * Created by luying on 14/11/5.
 */
public class ModuleManager {

/*

    public static final String SERVER_SIDE_ONLY_EXT = "serverSideOnly";

    private ModuleSecurityManager moduleSecurityManager;

    private ModuleRegistry registry;
    private ModuleEnvironment environment;
    private ModuleMetadataReader metadataReader;

    public ModuleManager() {
        metadataReader = new ModuleMetadataReader();//GsonReader
        metadataReader.registerExtension(SERVER_SIDE_ONLY_EXT, Boolean.TYPE);//only read the server side only ext proerpties
        Module engineModule;
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/assets/module.txt"))) {
            ModuleMetadata metadata = metadataReader.read(reader);
            engineModule = ClasspathModule.create(metadata, getClass(), Module.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read engine metadata", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to convert engine library location to path", e);
        }

        registry = new TableModuleRegistry();
        registry.add(engineModule);
        ModulePathScanner scanner = new ModulePathScanner(new ModuleLoader(metadataReader));
        scanner.getModuleLoader().setModuleInfoPath(Constants.MODULE_INFO_FILENAME);
        scanner.scan(registry, PathManager.getInstance().getModulePaths());

        setupSandbox();
        loadEnvironment(Sets.newHashSet(engineModule), true);
    }

    private void setupSandbox() {
        moduleSecurityManager = new ModuleSecurityManager();
        moduleSecurityManager.addAPIPackage("java.lang");
        moduleSecurityManager.addAPIPackage("java.lang.ref");
        moduleSecurityManager.addAPIPackage("java.math");
        moduleSecurityManager.addAPIPackage("java.util");
        moduleSecurityManager.addAPIPackage("java.util.concurrent");
        moduleSecurityManager.addAPIPackage("java.util.concurrent.atomic");
        moduleSecurityManager.addAPIPackage("java.util.concurrent.locks");
        moduleSecurityManager.addAPIPackage("java.util.regex");
        moduleSecurityManager.addAPIPackage("java.awt");
        moduleSecurityManager.addAPIPackage("java.awt.geom");
        moduleSecurityManager.addAPIPackage("java.awt.image");
        moduleSecurityManager.addAPIPackage("com.google.common.annotations");
        moduleSecurityManager.addAPIPackage("com.google.common.cache");
        moduleSecurityManager.addAPIPackage("com.google.common.collect");
        moduleSecurityManager.addAPIPackage("com.google.common.base");
        moduleSecurityManager.addAPIPackage("com.google.common.math");
        moduleSecurityManager.addAPIPackage("com.google.common.primitives");
        moduleSecurityManager.addAPIPackage("com.google.common.util.concurrent");
        moduleSecurityManager.addAPIPackage("gnu.trove");
        moduleSecurityManager.addAPIPackage("gnu.trove.decorator");
        moduleSecurityManager.addAPIPackage("gnu.trove.function");
        moduleSecurityManager.addAPIPackage("gnu.trove.iterator");
        moduleSecurityManager.addAPIPackage("gnu.trove.iterator.hash");
        moduleSecurityManager.addAPIPackage("gnu.trove.list");
        moduleSecurityManager.addAPIPackage("gnu.trove.list.array");
        moduleSecurityManager.addAPIPackage("gnu.trove.list.linked");
        moduleSecurityManager.addAPIPackage("gnu.trove.map");
        moduleSecurityManager.addAPIPackage("gnu.trove.map.hash");
        moduleSecurityManager.addAPIPackage("gnu.trove.map.custom_hash");
        moduleSecurityManager.addAPIPackage("gnu.trove.procedure");
        moduleSecurityManager.addAPIPackage("gnu.trove.procedure.array");
        moduleSecurityManager.addAPIPackage("gnu.trove.queue");
        moduleSecurityManager.addAPIPackage("gnu.trove.set");
        moduleSecurityManager.addAPIPackage("gnu.trove.set.hash");
        moduleSecurityManager.addAPIPackage("gnu.trove.stack");
        moduleSecurityManager.addAPIPackage("gnu.trove.stack.array");
        moduleSecurityManager.addAPIPackage("gnu.trove.strategy");
        moduleSecurityManager.addAPIPackage("javax.vecmath");
        moduleSecurityManager.addAPIPackage("com.yourkit.runtime");
        moduleSecurityManager.addAPIPackage("com.bulletphysics.linearmath");
        moduleSecurityManager.addAPIPackage("sun.reflect");
        moduleSecurityManager.addAPIClass(com.esotericsoftware.reflectasm.MethodAccess.class);
        moduleSecurityManager.addAPIClass(IOException.class);
        moduleSecurityManager.addAPIClass(InvocationTargetException.class);
        moduleSecurityManager.addAPIClass(LoggerFactory.class);
        moduleSecurityManager.addAPIClass(Logger.class);

        APIScanner apiScanner = new APIScanner(moduleSecurityManager);
        for (Module module : registry) {
            if (module.isOnClasspath()) {
                apiScanner.scan(module);
            }
        }

        moduleSecurityManager.grantFullPermission("ch.qos.logback.classic");
        moduleSecurityManager.grantPermission("com.google.gson", ReflectPermission.class);
        moduleSecurityManager.grantPermission("com.google.gson.internal", ReflectPermission.class);

        moduleSecurityManager.addAPIClass(java.nio.ByteBuffer.class);
        moduleSecurityManager.addAPIClass(java.nio.IntBuffer.class);

        Policy.setPolicy(new ModuleSecurityPolicy());
        System.setSecurityManager(moduleSecurityManager);
    }

    public ModuleRegistry getRegistry() {
        return registry;
    }

    public ModuleEnvironment getEnvironment() {
        return environment;
    }

    public ModuleEnvironment loadEnvironment(Set<Module> modules, boolean asPrimary) {
        Set<Module> finalModules = Sets.newLinkedHashSet(modules);
        for (Module module : registry) {
            if (module.isOnClasspath()) {
                finalModules.add(module);
            }
        }
        ModuleEnvironment newEnvironment = new ModuleEnvironment(finalModules, moduleSecurityManager, Collections.<BytecodeInjector>emptyList());
        if (asPrimary) {
            if (environment != null) {
                environment.close();
            }
            environment = newEnvironment;
        }
        return newEnvironment;
    }

    public ModuleMetadataReader getModuleMetadataReader() {
        return metadataReader;
    }*/
}
