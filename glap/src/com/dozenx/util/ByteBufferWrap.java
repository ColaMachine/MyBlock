package com.dozenx.util;

/**
 * Created by dozen.zhang on 2016/10/10.
 */

import cola.machine.game.myblocks.engine.Constants;

import java.io.UnsupportedEncodingException;
import java.nio.*;
import java.nio.charset.Charset;

public class ByteBufferWrap
{

    private ByteBuffer buffer;
    public ByteBufferWrap(){
        this.buffer= ByteBuffer.allocate(256);

    }
    public ByteBufferWrap put(int val){
        buffer.put(ByteUtil.getNewBytes(val));
        return this;
    }
    public ByteBufferWrap putLenStr(String str){
        byte[] bytes=str.getBytes(Constants.CHARSET);
        buffer.put(ByteUtil.getNewBytes(bytes.length));
        buffer.put(bytes);
        return this;
    }

    public byte[] array(){
        return buffer.array();
    }

    public static void main(String[] args){
        ByteBuffer buffer =ByteBuffer.allocate(256);
        buffer.put((byte)1);
        System.out.println(buffer.array().length);
    }
}
