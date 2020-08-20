package com.yn.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*****
 * <p>Title: LinuxSystemInfoTools</p>
 * <p>Description: 取得linux系统下的cpu、内存信息</p>
 * @author
 * @version 1.0
 */

public class LinuxSystemTools {
    private static final Logger logger = LoggerFactory.getLogger(LinuxSystemTools.class);

    /**
     * get memory by used info
     *
     * @return int[] result result.length==4;int[0]=MemTotal;int[1]=MemFree;int[2]=SwapTotal;int[3]=SwapFree;
     * @throws IOException
     * @throws InterruptedException
     */
    protected static long[] getMemInfo() {
        BufferedReader br = null;
        try {
            File file = new File("/proc/meminfo");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            long[] result = new long[7];
            String str = null;
            StringTokenizer token = null;
            while ((str = br.readLine()) != null) {
                token = new StringTokenizer(str);
                if (!token.hasMoreTokens()) {
                    continue;
                }
                str = token.nextToken();
                if (!token.hasMoreTokens()) {
                    continue;
                }

                if (str.equalsIgnoreCase("MemTotal:")) {
                    result[0] = Long.parseLong(token.nextToken());
                } else if (str.equalsIgnoreCase("MemFree:")) {
                    result[1] = Long.parseLong(token.nextToken());
                } else if (str.equalsIgnoreCase("SwapTotal:")) {
                    result[2] = Long.parseLong(token.nextToken());
                } else if (str.equalsIgnoreCase("SwapFree:")) {
                    result[3] = Long.parseLong(token.nextToken());
                } else if (str.equalsIgnoreCase("Buffers:")) {
                    result[4] = Long.parseLong(token.nextToken());
                } else if (str.equalsIgnoreCase("Cached:")) {
                    result[5] = Long.parseLong(token.nextToken());
                } else if (str.equalsIgnoreCase("SwapCached:")) {
                    result[6] = Long.parseLong(token.nextToken());
                }
            }
            return result;
        } catch (NumberFormatException | IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return new long[0];
    }

    /***
     * get cpu info
     *
     * @return double
     * @throws IOException
     * @throws InterruptedException
     */
    protected static double getCpuInfo() {
        BufferedReader br = null;
        try {
            File file = new File("/proc/stat");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringTokenizer token = new StringTokenizer(br.readLine());
            token.nextToken();
            long user1 = Long.parseLong(token.nextToken());
            long nice1 = Long.parseLong(token.nextToken());
            long sys1 = Long.parseLong(token.nextToken());
            long idle1 = Long.parseLong(token.nextToken());

            Thread.sleep(500);
            br.close();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            token = new StringTokenizer(br.readLine());
            token.nextToken();
            // int user2 = Integer.parseInt(token.nextToken());
            long user2 = Long.parseLong(token.nextToken());
            long nice2 = Long.parseLong(token.nextToken());
            long sys2 = Long.parseLong(token.nextToken());
            long idle2 = Long.parseLong(token.nextToken());

            return (double) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) / (double) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
        } catch (NumberFormatException | IOException | InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return 0;
    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
     *
     * @return mac地址
     */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process;
        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");                    // linux下的命令，一般取eth0作为本地主网卡

            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int index;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("hwaddr");                        // 寻找标示字符串[hwaddr]
                if (index >= 0) {                                                    // 找到了
                    mac = line.substring(index + "hwaddr".length() + 1).trim();        // 取出mac地址并去除2边空格
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
}