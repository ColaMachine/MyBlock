package cola.machine.game.myblocks.resource;

import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by luying on 14/10/22.
 */
public interface ResourceSource {
	

    /**
     * @return The identifier for this asset source
     */
    String getSourceId();

    /**
     * The URL(s) related to a URI. There may be multiple
     *
     * @param uri
     * @return The url equivalent of this uri
     */
    List<URL> get(ResourceUri uri);

    Iterable<ResourceUri> list();

    Iterable<ResourceUri> list(ResourceType type);

    List<URL> getOverride(ResourceUri uri);

    Iterable<ResourceUri> listOverrides();

    Collection<URL> getDelta(ResourceUri uri);
}
