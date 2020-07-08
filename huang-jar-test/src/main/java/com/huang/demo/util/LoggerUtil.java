package com.huang.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/6/24 15:44
 */
public class LoggerUtil {

    public static void printLoggerInfo(Logger logger, String title, String content){
        logger.info("============ " + title + " ==: " + content);
    }

    public static void printLoggerWarn(Logger logger, String title, String content){
        logger.warn("============ " + title + " ==: " + content);
    }

    public static void printLoggerError(Logger logger, String title, String content){
        logger.error("============ " + title + " ==: " + content);
    }
}
