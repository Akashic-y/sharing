package com.yn.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadTxtUtil {
    public static void main(String args[]) {
        readAndWriteFile();
    }

    /**
     * 读入TXT文件
     */
    public static void readAndWriteFile() {
        String pathname = "C:\\Users\\Administrator\\Desktop\\日常文件\\人员.txt";
        String pathname2 = "C:\\Users\\Administrator\\Desktop\\日常文件\\人员_20191127.txt";
        List<String> List1=new ArrayList<>();
        List<String> List2=new ArrayList<>();
        try {
            FileReader reader = new FileReader(pathname);    // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            FileReader reader2 = new FileReader(pathname2);    // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br2 = new BufferedReader(reader2);
            String line;

            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line.replaceAll("-", ""));
            }
            String[] split = sb.toString().split(";");
            List1.addAll(Arrays.asList(split));

            StringBuffer sb2 = new StringBuffer();
            while ((line = br2.readLine()) != null) {
                sb2.append(line.replaceAll("-", ""));
            }
            String[] split2 = sb2.toString().split(";");
            List2.addAll(Arrays.asList(split2));

            //下面是写入文件
            File writeName = new File("C:\\Users\\Administrator\\Desktop\\output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            int i = 0;
            int j = 0;
            for(String item:List1) {
                if(!List2.contains(item)) {
                    out.write(item + "\t\r\n");// \r\n即为换行
                    System.out.println(item);
                    i++;
                }
            }
            System.out.println("------------------");
            for(String item:List2) {
                if(!List1.contains(item)) {
                    System.out.println(item);
                    j++;
                }
            }
            System.out.println("------出-----"+i+"-----入-------"+j);
            out.flush(); // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
