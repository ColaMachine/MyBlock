package com.dozenx.game.export;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:38 2019/10/11
 * @Modified By:
 */

import java.io.IOException;

/**
 * <code>Savable</code> is an interface for objects that can be serialized
 * using jME's serialization system.
 *
 * @author Kirill Vainer
 */
public interface Savable {
    void write(JmeExporter ex) throws IOException;
    void read(JmeImporter im) throws IOException;
}
