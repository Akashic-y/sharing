package com.yn.common.util;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author YN
 *
 */
public class CommonTools {
	private static Logger logger = LoggerFactory.getLogger(CommonTools.class);

	/**
	 * 是否不为空
	 * 
	 * 
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(String value) {
		return !isNull(value);
	}

	/**
	 * 是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * 执行cmd命令
	 * 
	 * @param cmd
	 */
	public static void execCmd(String cmd) {
		StringBuffer str = new StringBuffer();
		str.append("cmd.exe /c \"").append(cmd).append("\"");
		try {
			Runtime.getRuntime().exec(str.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 执行cmd命令并返回影响字符串
	 * 
	 * @param cmd
	 * @return
	 */
	public static String getExecCmdResult(String cmd) {
		StringBuffer sb = new StringBuffer();
		StringBuffer str = new StringBuffer();
		str.append("cmd /c \"").append(cmd).append("\"");
		logger.info(str.toString());
		try {
			Process ls_proc = Runtime.getRuntime().exec(str.toString());
			ls_proc.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(new DataInputStream(ls_proc.getInputStream()),"GBK"));
			String ss;
			while ((ss = in.readLine()) != null) {
				sb.append(ss).append("\n");
			}
			in.close();
		} catch (IOException | InterruptedException e) {
			logger.error(e.getMessage());
		}
		logger.info(sb.toString());
		return sb.toString();
	}

	public static void main(String[] args) {
		String execCmdResult = getExecCmdResult("ipconfig");
//		String execCmdResult = getExecCmdResult("ping www.qq.com");
		System.out.println(execCmdResult);
	}
}
