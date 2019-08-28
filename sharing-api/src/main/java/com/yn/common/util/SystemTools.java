package com.yn.common.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: SystemTool
 * @description: 与系统相关的一些常用工具方法. 目前实现的有：获取MAC地址、IP地址、主机名
 * @author: 
 */
public class SystemTools {
	private static final Logger logger = LoggerFactory.getLogger(SystemTools.class);
	/**
	 * @return 获取CPU的使用率
	 */
	public static double getCpuRate() {
		int osType = getOptSystemType();
		double ret = 0;
		if (osType == 1) {
			ret = WindowsSystemTool.getCpuRatioForWindows();
		} else {
			try {
				ret = LinuxSystemTools.getCpuInfo();
			} catch (IOException e) {
				logger.error(e.getMessage());
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
		
		return ret;
	}

	/**
	 * @return 获取内存使用率

	 */
	public static double getMemoryRate() {
		int osType = getOptSystemType();
		double ret = 0;
		if (osType == 1) {
			ret = WindowsSystemTool.getWindowsMemery();
		} else {
			try {
				long[] memoryInfo = LinuxSystemTools.getMemInfo();
				long mem_total = memoryInfo[0];
				long mem_free = memoryInfo[1] + memoryInfo[4] + memoryInfo[5]; // + memoryInfo[6];
				ret = ((double)(mem_total - mem_free) / (double)mem_total);
				
//				ret = ((double)memoryInfo[0] - (double)memoryInfo[1]) / (double)memoryInfo[0];
			} catch (IOException e) {
				logger.error(e.getMessage());
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
		return ret;
	}
	
    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }
    
    /**
     * @return 本机主机名
     */
    public static String getHostName() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        	logger.error(e.getMessage());
        }
        
        if (ia == null) {
            return null;
        } else {
            return ia.getHostName();
        }
    }
    
    /**
     * @return 本机IP 地址
     */
    public static String getIPAddress() {
        InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		
        if (ia == null) {
            return null;
        } else {
            return ia.getHostAddress();
        }
    }
    
	/**
	 * 获取本机操作系统类型
	 * @return	1表示windows操作系统，2表示Linux操作系统，3表示Unix操作系统，4表示其他
	 */
	public static int getOptSystemType() {
		int result = 4;
		Properties pro = System.getProperties();
		String systemTypeStr = pro.getProperty("os.name");
		if (CommonTools.isNotNull(systemTypeStr)) {
			systemTypeStr = systemTypeStr.toLowerCase();
			if (systemTypeStr.indexOf("windows") != -1) {
				result = 1;
			} else if (systemTypeStr.indexOf("linux") != -1) {
				result = 2;
			} else if (systemTypeStr.indexOf("unix") != -1) {
				result = 3;
			}
		}
		
 		return result;
	}
	
	/**
	 * 验证系统是否是windowns系统
	 * @return
	 */
	public static boolean isSystemWindows() {
		Properties pro = System.getProperties();
		String systemTypeStr = pro.getProperty("os.name");
		if (CommonTools.isNotNull(systemTypeStr)) {
			systemTypeStr = systemTypeStr.toLowerCase();
			if (systemTypeStr.indexOf("windows") != -1) {
				return true;
			} else if (systemTypeStr.indexOf("linux") != -1) {
				return false;
			} 
		}
		return false;
	}
    
	/**
	 * 重启操作系统
	 * @return
	 */
	public static boolean restartSystem() {
		if (getOptSystemType() == 1) {
			CommonTools.execCmd("shutdown -r -t 0");
		} else {
			try {
				Runtime.getRuntime().exec("reboot");
			} catch (IOException e) {
				logger.error(e.getMessage());
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 获取浮点型转换为字符串，保留pointNumber位小数，四舍五入
	 * 
	 * @param value
	 * @param pointNumber
	 * @return
	 */
	public static String getPointDoubleString(double value, int pointNumber) {
		StringBuffer format = new StringBuffer();
		format.append("0.");
		for (int i = 0; i < pointNumber; i++) {
			format.append("#");
		}
		DecimalFormat df = new DecimalFormat(format.toString());
		return df.format(value);
	}
	
	/**
	 * 获取指定路径（分区）的剩余空间大小。单位MB
	 * @param path  "D:\\REC\\"
	 * @return
	 */
	public static long getSpaceFreeSize(String path) {
		// File file = new File("D:\\REC\\");
		File file = new File(path);
		return file.getFreeSpace() / 1024 / 1024;
	}
	
	/**
	 * 获取指定路径（分区）的总空间大小。单位MB
	 * @param path "D:\\REC\\"
	 * @return
	 */
	public static long getSpaceTotal(String path) {
		// File file = new File("D:\\REC\\");
		File file = new File(path);
		return file.getTotalSpace() / 1024 / 1024;
	}
    
    /**
     * 获取网络带宽信息
     * @return
     */
    public static String getNetRatio() {
        String info = CommonTools.getExecCmdResult("netstat -e");
        return info;
    }
}
