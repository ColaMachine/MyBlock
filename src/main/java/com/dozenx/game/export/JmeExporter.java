package com.dozenx.game.export;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 16:38 2019/10/11
 * @Modified By:
 */

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
public interface JmeExporter {

    /**
     * Export the {@link Savable} to an OutputStream.
     *
     * @param object The savable to export
     * @param f The output stream
     * @throws IOException If an io exception occurs during export
     */
    public void save(Savable object, OutputStream f) throws IOException;

    /**
     * Export the {@link Savable} to a file.
     *
     * @param object The savable to export
     * @param f The file to export to
     * @throws IOException If an io exception occurs during export
     */
    public void save(Savable object, File f) throws IOException;

    /**
     *
     * @param object The object to retrieve an output capsule for.
     */
//    public OutputCapsule getCapsule(Savable object);
}
