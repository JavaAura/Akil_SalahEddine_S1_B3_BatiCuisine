package org.aura.utils;

import java.util.logging.Logger;

public final class LoggerUtils {

    private static final Logger logger = Logger.getLogger(LoggerUtils.class.getName());
    private LoggerUtils(){}
    public static Logger getLogger(){
        return logger;
    }
}
