package com.yn.common.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/***
 * 封装log4j接口，统一处理日志/debug信息的输出
 *
 *
 */
public class Log {
    private static Logger logger = Logger.getLogger("");

    /***
     * 设定日志输出级别 error
     */
    public static void setErrorLevel() {
        logger.setLevel(Level.ERROR);
    }

    /***
     * 设定日志输出级别 debug
     */
    public static void setDebugLevel() {
        logger.setLevel(Level.DEBUG);

    }

    /***
     * 设定日志输出级别 Warn
     */
    public static void setWarnLevel() {
        logger.setLevel(Level.WARN);
    }

    /***
     * 设定日志输出级别 Info
     */
    public static void setInfoLevel() {
        logger.setLevel(Level.INFO);
    }

    /**
     * 普通日志输出： debug级别
     *
     * @param msg
     */
    public static void debug(String msg) {
        logger.debug(LogTools.getFileNameAndLineNumber("com.yn.common.util") + msg);
    }

    /**
     * 普通日志输出： warn级别
     *
     * @param msg
     */
    public static void warn(String msg) {
        logger.warn(LogTools.getFileNameAndLineNumber("com.yn.common.util") + msg);
    }

    /**
     * 普通日志输出： info级别
     *
     * @param msg
     */
    public static void info(String msg) {
        logger.info(LogTools.getFileNameAndLineNumber("com.yn.common.util") + msg);
    }


    /**
     * 异常（Exception）日志信息的处理（等同于 Ex.printStackTrace()）:debug级别
     *
     * @param Ex
     */
    public static void debugStackTrace(Exception Ex) {
        if (Ex == null) {
            return;
        }
        StackTraceElement[] stackTraceElement = Ex.getStackTrace();
        if (stackTraceElement == null) {
            return;
        }
        logger.error(Ex.toString());
        for (StackTraceElement traceElement : stackTraceElement) {
            logger.error(traceElement.toString());
        }
    }

    /**
     * 异常（Exception）日志信息的处理（等同于 Ex.printStackTrace()）:debug级别
     *
     * @param Ex
     */
    public static void debugStackTrace(String code, Exception Ex) {
        if (Ex == null) {
            return;
        }
        StackTraceElement[] stackTraceElement = Ex.getStackTrace();
        if (stackTraceElement == null) {
            return;
        }
        logger.error("System Error Code ->" + code + " Stack->" + Ex.toString());
        for (StackTraceElement traceElement : stackTraceElement) {
            logger.error(traceElement.toString());
        }
    }

    /**
     * 异常（Exception）日志信息的处理（等同于 Ex.printStackTrace()）:warn级别
     *
     * @param Ex
     */
    public static void warnStackTrace(Exception Ex) {
        if (Ex == null) {
            return;
        }
        StackTraceElement[] stackTraceElement = Ex.getStackTrace();
        if (stackTraceElement == null) {
            return;
        }
        logger.error(Ex.toString());
        for (StackTraceElement traceElement : stackTraceElement) {
            logger.error(traceElement.toString());
        }
    }

    /**
     * 异常（Exception）日志信息的处理（等同于 Ex.printStackTrace()）:info级别
     *
     * @param Ex
     */
    public static void infoStackTrace(Throwable Ex) {
        if (Ex == null) {
            return;
        }
        StackTraceElement[] stackTraceElement = Ex.getStackTrace();
        if (stackTraceElement == null) {
            return;
        }
        logger.error(Ex.toString());
        for (StackTraceElement traceElement : stackTraceElement) {
            logger.error(traceElement.toString());
        }
    }

}
