package org.newdawn.slick.util;

/**
 * Created by colamachine on 19-9-14.
 */
public interface LogSystem {

    void error(String var1, Throwable var2);

    void error(Throwable var1);

    void error(String var1);

    void warn(String var1);

    void warn(String var1, Throwable var2);

    void info(String var1);

    void debug(String var1);
}
