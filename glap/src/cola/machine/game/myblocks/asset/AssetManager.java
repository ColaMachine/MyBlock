package cola.machine.game.myblocks.asset;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.module.ModuleEnvironment;

import java.nio.file.Files;
import java.util.Locale;

/**
 * Created by luying on 14-10-15.
 */
public class AssetManager {

    private static final Logger logger = LoggerFactory.getLogger(AssetManager.class);

    private ModuleEnvironment environment;//the file root archive location
    private Map<Name,AssetSource> assetSources=Maps.newHashMap();
    private Map<AssetType,Map<String,AssetLoader<?>>> assetLoaders=Maps.newEnumMap(AssetType.class);

  //  private Map<Name,AssetSource> assetSources = Maps.newHashMap();
    private Map<AssetUri,Asset<?>> assetCache=Maps.newHashMap();
    private Map<AssetUri,AssetSource> overrides=Maps.newHashMap();
    private Map<AssetType,AssetFactory<?,?> factories>=Maps.newHashMap();
    private ListMultimap<AssetType.AssetResolver<?,?>> resolvers=ArrayListMultimap.create();

    public AssetManager(ModuleEnviroment enviroment){
        for(AssetType type:AssetType.values()){
            uriLookup.put(type,HashBasedTable.<Name,Name,AssetUri>create());
        }
        setEnviroment(environment);
    }
    public ModuleEnviroment getEnvironment(){
        return environment;
    }
    public void setEnvironment(ModuleEnvironment environment){
        this.environment=environment;
        assetSources.clear();
        for(Module module:environment){
            Collection<Path> location =module.getLocations();
            if(!location.isEmpty()){
                List<AssetSource> sources=Lists.newArrayList();
               for(Path path:location){
                   sources.add(createAssetSource(module.getId(),path));
               }
                AssetSource source =new AssetSourceCollection(module.getId(),sources);
                assetSources.put(source.getSourceId(),source);
                for(AssetUri asset :source.list()){
                    uriLookup.get(asset.getAssetType()).put(asset.getAssetName(),asset.getModuleName(),asset);
                }

            }
        }

        applyOverrides();
        refresh();
    }

    private AssetSource createAssetSource(Name id,Path path){
        if(Files.isRegularFile(path)){
            return new ArchiveSource(id,path.toFile(),Constants.ASSET_SUBDIRECTORY,Constants.OVERRIDES_SUBDIRECTORY,
                    Constants.DELTAS_SUBDIRECTORY);
        }else{
            return new DirectorySource(id,
                    path.resolve(Constants.ASSETS_SUBDIRECTORY),
                    path.resolve(Constants.OVERRIDES_SUBDIRECTORY),
                    path.resolve(Constants.DELTAS_SUBDIRECTORY));

            )

        }
    }

    public <T extends Asset<U>,U extends AssetData> void addResolver(AssetType assetType,AssetResolver<T,U> resolver){
        resolvers.put(assetType,resolver);
    }

    public void setAssetFactory(AssetType type,AssetFactory<?,?> factory){
        factories.put(type,factory);
    }

    public List<AssetUri> resolveAll(AssetType type,String name){
        AssetUri uri=new AssetUri(type,name);
        if(uri.isValid()){
            return Lists.newArrayList(uri);
        }
        return resolveAll(type, new Name(name));
    }

    public List<AssetUri> resolveAll(AssetType type ,Name name){
        List<AssetUri> results=Lists.newArrayList(uriLookup.get(type).row(name).values());
        for(AssetResolver<?,?> resolver:resolvers.get(type)){
            Asseturi additionalUri=resolver.resolve(name);
            if(additionalUri!=null){
                results.add(additionalUri);
            }
        }
        return results;
    }

    public AssetUri resolve(AssetType type,String name){
        List<AssetUri> possibilities=resolveAll(type,name);
        switch (possibilities.size()){
            case 0:
                logger.warn("Failed to resolve {}:{}",type,name);
                return null;
            case 1:
                return possibilities.get(0);
            default:
                Module context =ModuleContext.getContext();
                if(context!=null){
                    Set<Name> dependencies=environment.getDependencyNamesOf(context.getId());
                    Iterator<AssetUri> iterator=possibilities.iterator();
                    while(iterator.hashNext()){
                        AssetUri possibleUri=iterator.next();
                        if(context.getId(0).equals(possibleUri.getModuleName())){
                            return possibleUri;
                        }
                        if(!dependencies.contains(possibleUri.getModuleName())){
                            iterator.remove();
                        }
                    }
                    if(possibilities.size()==1){
                        return possibilities.get(0);
                    }
                }
                logger.warn("Failed to resolve {}:{} - too many valid mathces {}",type,name,possibilities);
                return null;
        }
    }





    //load asset


    public <T extends Asset<?>> T resolveAndTryLoad(AssetType type,String name,Class<?> assetClass){
        Asset<?> result =resolveAndTryLoad(type,name);
        if(assetClass.isInstance(result)){
            return assetClass.cast(result);
        }
        return null;
    }

    public Asset<?> resolveAndLoad(AssetType type,String name){
        AssetUri uri =resolve(type,name);
        if(uri!=null){
            return loadAsset(uri,false);
        }
        return null;
    }
    public Asset<?> resolveAndTryLoad(AssetType type,String name){
        AssetUri uri =resolve(type,name);
        if(uri!=null){
            return loadAsset(uri,true);
        }
        return null;
    }




    public <T extends Asset<?>> T tryLoadAsset(AssetUri uri,Class<T> type){
        Asset<?> asset= loadAsset(uri,true);
        if(type.isInstance(asset)){
            return type.cast(asset);
        }
        return  null;
    }


    public Asset<?> loadAsset(AssetUri uri){
        return loadAsset(uri,true);
    }

    public <D extends AssetData> void reload(Asset<D> asset){
        AssetData data=loadAssetData(Asset.getURI(),false);
        if(data!=null){
            asset.reload((D)data);
        }
    }

    public <T extends Asset<?>> T loadAsset(AssetUri uri,Class<T> assetClass){
        Asset<?> result =loadAsset(uri,true);
        if(assetClass.isInstance(result)){
            return assetClass.cast(result);
        }
        return null;
    }


    //load assetdata
    public <T extends Assetdata> T resolveAndLoadData(AssetType type,String name,Class<T> dataClass){
        AssetData data =resolveAndLoadData(type,name);
        if(dataClass.isInstance(data)){
            return dataClass.cast(data);
        }return null;
    }
    public <T extends AssetData> T resolveAndTryLoadData(AssetType type,String name,Class<T> dataClass){
        AssetData data =resolveAndTryLoadData(type,name);
        if(dataClass.isInstance(data)){
            return dataClass.cast(data);
        }
        return null;
    }
    public AssetData resolveAndLoadData(AssetType type,String name){
        AssetUri uri =resolve(type,name);
        if(uri!=null){
            return loadAssetData(uri,true);
        }
        return null;
    }
    public AssetData resolveAndTryLoadData(AssetType type,String name){
        AssetUri uri =resolve(type,name);
        if(uri!=null){
            return loadAssetData(uri,false);
        }
        return null;
    }


    public <T>T tryLoadAssetData(AssetUri uri,Class<T> type){
        AssetData data =loadAssetData(uri,false);
        if(type.isInstance(data)){
            return type.cast(data);
        }
        return null;
    }

    public <T>T loadAssetData(AssetUri uri,Class<T> type){
        AssetData data =loadAssetData(uri,true);
        if(type.isInstance(data)){
            return type.cast(data);
        }
        return null;
    }

    public void register(AssetType type ,String extension,AssetLoader<?> loader){
        Map<String,AssetLoader<?>> assetTypeMap = assetLoaders.get(type);
        if(assetTypeMap==null){
            assetTypeMap=Maps.newHashMap();
            assetLoaders.put(type,assetTypeMap);
        }
        assetTypeMap.put(extension.toLowerCase(),loader);
    }
    private AssetData loadAssetData(AssetUri uri,boolean logErrors){
        if(!uri.isValid()){
            return null;
        }
        List<URL> urls=getAssetURLs(uri);
        if(urls.size()==0){
            if(logErrors){
                logger.warn("Unable to resolve asset {}",uri);
            }
            return null;
        }

        for(URL url : urls){
            int extensionIndex=url.toString().lastIndexOf('.');
            if(extensionIndex==-1){
                continue;
            }

            String extension=url.toString().subString(extensionIndex+1).tolowerCase(Locale.ENGLISH);
            Map<String,AssetLoader<?>> extensionMap=assetLoaders.get(uri.getAssetType());
            if(extensionMap==null) {
                continue;
            }

            AssetLoader<?> loader = extensionMap.get(extension);
            if(loader==null){
                continue;
            }

            Module module = environment.get(uri.getModuleName());
            List<URL> deltas;
            if(uri.getAssetType().isDeltaSupported()){
                deltas= Lists.newArrayList();
                for(Module deltaModule:environment.getModuleIdsOrderedByDependencies()){
                    AssetSource source =assetSources.get(deltaModule.getId());
                    if(source != null ){
                        deltas.addAll(source.getDelta(uri));
                    }
                }
            } else {
                deltas=Collections.emptyList();

            }
        }

    }

}
