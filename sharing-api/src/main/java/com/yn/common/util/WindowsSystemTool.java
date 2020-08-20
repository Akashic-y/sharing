package com.yn.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.management.OperatingSystemMXBean;

/*****
 * <p>Title: WindowsSystemTool</p>
 * <p>Description: 取得windows系统下的cpu、内存信息</p>
 * @author yn
 * @version 1.0
 */
public class WindowsSystemTool {
    private static final Logger logger = LoggerFactory.getLogger(WindowsSystemTool.class);

    private static final int CPUTIME = 5000;
    private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;

    /**
     * 获取内存使用率
     *
     * @return
     */
    protected static double getWindowsMemery() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();            // 总的物理内存+虚拟内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();    // 剩余的物理内存
        return (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
    }

    /**
     * 获得cpu使用率
     *
     * @return
     */
    protected static double getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息

            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return PERCENT * (busytime) * 1.0 / (busytime + idletime);
            } else {
                return 0;
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return 0;
        }
    }

    /**
     * 获取widnows网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            process = Runtime.getRuntime().exec("ipconfig /all");
            // windows下的命令，显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("physical address");
                // 寻找标示字符串[physical address]
                if (index >= 0) {                                    // 找到了
                    index = line.indexOf(":");                        // 寻找":"的位置
                    if (index >= 0) {
                        mac = line.substring(index + 1).trim();        //取出mac地址并去除2边空格
                    }
                    break;
                }
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                logger.error(e1.getMessage());
            }
        }

        return mac;
    }

    /**
     * 读取cpu相关信息
     *
     * @param proc
     * @return
     */
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }

            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }

                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.contains("wmic.exe")) {
                    continue;
                }

                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                if (caption.equals("System Idle Process")
                        || caption.equals("System")) {
                    if (s1.length() > 0) {
                        idletime += Long.parseLong(s1);
                    }
                    if (s2.length() > 0) {
                        idletime += Long.parseLong(s2);
                    }
                    continue;
                }

                if (s1.length() > 0) {
                    kneltime += Long.parseLong(s1);
                }
                if (s2.length() > 0) {
                    usertime += Long.parseLong(s2);
                }
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     *
     * @param src       要截取的字符串
     * @param start_idx 开始坐标（包括该坐标)
     * @param end_idx   截止坐标（包括该坐标）
     * @return
     */
    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        StringBuilder tgt = new StringBuilder();
        for (int i = start_idx; i <= end_idx; i++) {
            tgt.append((char) b[i]);
        }
        return tgt.toString();
    }
}
