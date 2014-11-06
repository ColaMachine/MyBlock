package cola.machine.game.myblocks.config;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.module.DependencyResolver;

import java.util.Arrays;

/**
 * Created by luying on 14/11/4.
 */
public final class BindsConfig {/*
    private static final Logger logger= LoggerFactory.getLogger(BindsConfig.class);

    private ListMultimap<SimpleUri,Input > data = ArrayListMultimap.create();

    public BindsConfig(){

    }

    public void setBinds(BindsConfig other){
        data.clear();
        data.putAll(other.data);
    }

    public Collection<Input> getBinds(SimpleUri uri){
        return data.get(uri);

    }

    public boolean hasBinds(SimpleUri uri){
        return !data.get(uri).isEmpty();

    }

    public  void setBinds(SimpleUri bindUri,Input... inputs){
        setBinds(bindUri, Arrays.asList(inputs));
    }

    public void setBinds(SimpleUri bindUri,Iterable<Input> inputs){
        Set<Input> uniqueInputs= Sets.newLinkedHashSet(inputs);
        Iterator<Input> iterator=data.values().iterator();
        while(iterator.hasNext()){
            Input i =iterator.next();
            if(uniqueInputs.contains(i)){
                iterator.remove();
            }
        }

        data.replaceValues(bindUri.uniqueInputs);
    }

    public static BindsConfig createDefault(){
        ModuleManager moduleManager= CoreRegistry.get(ModuleManager.class);
        BindsConfig config =new BindsConfig();
        DependencyResolver resolver =new DependencyResolver(moduleManager.getRegistry());
    }*/
}
