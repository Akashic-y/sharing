package com.yn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import tk.mybatis.mapper.util.StringUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TxtTest {

    @Test
    public void readAndWriteFile() {
        String oldOrder = "C:\\Users\\Administrator\\Desktop\\日常文件\\txt\\人员_20190902.txt";
        String newOrder = "C:\\Users\\Administrator\\Desktop\\日常文件\\txt\\人员_20200224.txt";
        List<String> List1=new ArrayList<>();
        List<String> List2=new ArrayList<>();
        try {
            FileReader reader = new FileReader(oldOrder);    // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            FileReader reader2 = new FileReader(newOrder);    // 建立一个对象，它把文件内容转成计算机能读懂的语言
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
            out.write("-----------------------------\r\n");// \r\n即为换行
            for(String item:List2) {
                if(!List1.contains(item)) {
                    out.write(item + "\t\r\n");// \r\n即为换行
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

    @Test
    public void testJson(){
        String path = "C:\\Users\\Administrator\\Desktop\\日常文件\\txt\\research.txt";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuffer sb = new StringBuffer();
            while (StringUtil.isNotEmpty(line = br.readLine())){
                sb.append(line);
            }
            JSONArray jsonArray = JSON.parseArray(sb.toString());
            JSONObject[] js = new JSONObject[jsonArray.size()];
            jsonArray.toArray(js);
            Map<String,String> smap = new HashMap<>();
            Map<String,String> emap = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (JSONObject j : js) {
                String authorName = j.getString("author_name");
                String date = j.getString("date");
                Date temp = sdf.parse(date);
                if(smap.containsKey(authorName)){
                    String start = smap.get(authorName);
                    String end = emap.get(authorName);
                    Date s = sdf.parse(start);
                    Date e = sdf.parse(end);
                    if(s.after(temp)){
                        smap.put(authorName,date);
                    }
                    if(e.before(temp)){
                        emap.put(authorName,date);
                    }
                }else {
                    smap.put(authorName,date);
                    emap.put(authorName,date);
                }
            }
            System.out.println(smap);
            System.out.println(emap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readLog(){
        String path = "C:\\Users\\Administrator\\Desktop\\日常文件\\txt\\cloud.txt";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Map<String,String> smap = new HashMap<>();
            Map<String,String> emap = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            while (StringUtil.isNotEmpty(line = br.readLine())){
                String[] str = line.split(",");
                if(smap.containsKey(str[1])){
                    String start = smap.get(str[1]);
                    String end = emap.get(str[1]);
                    Date s = sdf.parse(start);
                    Date e = sdf.parse(end);

                    if(s.after(sdf.parse(str[0]))){
                        smap.put(str[1],str[0]);
                    }
                    if(e.before(sdf.parse(str[0]))){
                        emap.put(str[1],str[0]);
                    }
                }else {
                    smap.put(str[1],str[0]);
                    emap.put(str[1],str[0]);
                }
            }
            FileWriter fw = new FileWriter("C:\\Users\\Administrator\\Desktop\\rs.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(String key : smap.keySet()){
                String temp = key + "    start: " + smap.get(key) + "    end: " + emap.get(key);
                bw.write(temp);
                bw.newLine();
                System.out.println(temp);
            }
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
