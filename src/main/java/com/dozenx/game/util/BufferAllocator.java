package com.dozenx.game.util;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:23 2019/10/11
 * @Modified By:
 */
public interface BufferAllocator {

    void destroyDirectBuffer(Buffer toBeDestroyed);

    ByteBuffer allocate(int size);

}