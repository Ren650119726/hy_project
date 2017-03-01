package com.mockuai.virtualwealthcenter.core.util;

import org.slf4j.Logger;

/**
 * Created by edgar.zr on 12/26/15.
 */
public class LogUtil {

    public static void info(Logger logger, String format, Object... args) {
        logger.info(format, args);
    }

    public static void debug(Logger logger, String format, Object... args) {
        logger.debug(format, args);
    }
}