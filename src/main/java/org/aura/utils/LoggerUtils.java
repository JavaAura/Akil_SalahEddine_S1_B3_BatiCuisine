package org.aura.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtils {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    public static void logInfo(String message){
          logger.info(message);
    }
    public static void logWarn(String message){
        logger.warn(message);
    }
    public static void logError(String message){
        logger.error(message);
    }

}
