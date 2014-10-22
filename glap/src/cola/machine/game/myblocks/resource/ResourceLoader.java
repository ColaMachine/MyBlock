package cola.machine.game.myblocks.resource;

import java.io.InputStream;

/**
 * Created by luying on 14/10/21.
 */
public interface ResourceLoader<T extends ResourceData> {

    T load(ResourceType type,InputStream inputStream);
}
