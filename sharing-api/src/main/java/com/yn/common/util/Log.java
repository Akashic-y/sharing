package com.yn.common.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/***
 * 封装log4j接口，统一处理日志/debug信息的输出
 *
 * @author jyq
 *
 */
public class Log {
	private static Logger logger = Logger.getLogger("");
	public static String LEVEL = Logger.getRootLogger().getLevel().toString();

	/***
	 * 设定日志输出级别 error
	 */
	public static void setErrorLevel() {
		logger.setLevel(Level.ERROR);
		setLog4jConfig(Level.ERROR.toString());
	}

	/***
	 * 设定日志输出级别 debug
	 */
	public static void setDebugLevel() {
		logger.setLevel(Level.DEBUG);
		setLog4jConfig(Level.DEBUG.toString());
	}

	/***
	 * 设定日志输出级别 Warn
	 */
	public static void setWarnLevel() {
		logger.setLevel(Level.WARN);
		setLog4jConfig(Level.WARN.toString());
	}

	/***
	 * 设定日志输出级别 Info
	 */
	public static void setInfoLevel() {
		logger.setLevel(Level.INFO);
		setLog4jConfig(Level.INFO.toString());
	}

	/**
	 * 普通日志输出： debug级别
	 *
	 * @param msg
	 */
	public static void debug(String msg) {
		logger.debug(getFileNameAndLineNumber("com.szreach.rcrp.common.Log") + msg);
	}

	/**
	 * 普通日志输出： warn级别
	 *
	 * @param msg
	 */
	public static void warn(String msg) {
		logger.warn(getFileNameAndLineNumber("com.szreach.rcrp.common.Log") + msg);
	}

	/**
	 * 普通日志输出： info级别
	 *
	 * @param msg
	 */
	public static void info(String msg) {
		logger.info(getFileNameAndLineNumber("com.szreach.rcrp.common.Log") + msg);
	}

	/**
	 * 异常（Exception）日志信息的处理（等同于 Ex.printStackTrace()）:debug级别
	 *
	 * @param Ex
	 */
	public static void debugStackTrace(Throwable Ex) {
		if (Ex == null) {
			return;
		}
		StackTraceElement[] stackTraceElement = Ex.getStackTrace();
		if (stackTraceElement == null) {
			return;
		}
		logger.error(Ex.toString());
		for (int i = 0; i < stackTraceElement.length; i++) {
			logger.error(stackTraceElement[i].toString());
		}
	}

	/**
	 * 异常（Exception）日志信息的处理（等同于 Ex.printStackTrace()）:warn级别
	 *
	 * @param Ex
	 */
	public static void warnStackTrace(Throwable Ex) {
		if (Ex == null) {
			return;
		}
		StackTraceElement[] stackTraceElement = Ex.getStackTrace();
		if (stackTraceElement == null) {
			return;
		}
		logger.error(Ex.toString());
		for (int i = 0; i < stackTraceElement.length; i++) {
			logger.error(stackTraceElement[i].toString());
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
		for (int i = 0; i < stackTraceElement.length; i++) {
			logger.error(stackTraceElement[i].toString());
		}
	}

	public static void setLog4jConfig(String lvl) {
		LEVEL = lvl;
		// 修改log4j.properties日志等级级别
		ContentGenerator generator = new ContentGenerator();
		Map<String, String> data = new HashMap<String, String>();
		data.put("level", lvl);
		String path = generator.getClass().getClassLoader().getResource("/").getPath();
		try {
			generator.generatFile("log4j.properties", path, "log4j.properties", data);
		} catch (IOException e) {
			Log.warnStackTrace(e);
		} catch (TemplateException e) {
			Log.warnStackTrace(e);
		}
	}

	/**
	 * 获得文件名和行号。
	 *
	 * @param className
	 *            类名
	 * @return 文件名和行号
	 */
	public static String getFileNameAndLineNumber(String className) {
		String fname = null;
		int lineNumber = 0;
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		int ix = 0;
		while (ix < stack.length) {
			StackTraceElement frame = stack[ix];
			String cname = frame.getClassName();
			if (cname.equals(className)) {
				break;
			}
			ix++;
		}

		while (ix < stack.length) {
			StackTraceElement frame = stack[ix];
			String cname = frame.getClassName();
			if (!cname.equals(className)) {
				fname = frame.getFileName();
				lineNumber = frame.getLineNumber();
				break;
			}
			ix++;
		}

		if (null == fname) {
			return "Unknown] [";
		}

		if (lineNumber > 0) {
			return fname + ":" + lineNumber + "] [";
		} else {
			return fname + "] [";
		}
	}

}
