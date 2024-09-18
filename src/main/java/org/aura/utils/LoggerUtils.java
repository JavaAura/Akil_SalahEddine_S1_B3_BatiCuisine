package org.aura.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);
    private LoggerUtils(){}
    public static Logger getLogger(){
        return logger;
    }
}
