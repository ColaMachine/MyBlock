package cola.machine.game.myblocks.persistence;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cola.machine.game.myblocks.math.Region3i;
import cola.machine.game.myblocks.math.Vector3i;
import cola.machine.game.myblocks.persistence.impl.StorageManagerInternal;
import cola.machine.game.myblocks.persistence.internal.*;
public  interface  StorageManager{
    ChunkStore loadChunkStore(Vector3i chunkPos);
    boolean containsChunkStoreFor(Vector3i chunkPos);
}
