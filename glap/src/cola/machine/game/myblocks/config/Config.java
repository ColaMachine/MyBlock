package cola.machine.game.myblocks.config;

import cola.machine.game.myblocks.engine.paths.PathManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by luying on 14-10-5.
 */
public class Config {
    private static final Logger logger =LoggerFactory.getLogger(Config.class);

    private SystemConfig system = new SystemConfig();

    private PlayerConfig player =new PlayerConfig();
}
