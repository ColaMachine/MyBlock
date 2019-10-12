package com.dozenx.game.util;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:23 2019/10/11
 * @Modified By:
 */


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The factory of buffer allocators.
 *
 * @author JavaSaBR
 */
public class BufferAllocatorFactory {

    public static final String PROPERTY_BUFFER_ALLOCATOR_IMPLEMENTATION = "com.jme3.BufferAllocatorImplementation";

    private static final Logger LOGGER = Logger.getLogger(BufferAllocatorFactory.class.getName());

    protected static BufferAllocator create() {

        final String className = System.getProperty(PROPERTY_BUFFER_ALLOCATOR_IMPLEMENTATION, ReflectionAllocator.class.getName());
        try {
            return (BufferAllocator) Class.forName(className).newInstance();
        } catch (final Throwable e) {
            LOGGER.log(Level.WARNING, "Unable to access {0}", className);
            return new PrimitiveAllocator();
        }
    }
}
