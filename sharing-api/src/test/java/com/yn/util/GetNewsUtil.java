package com.yn.util;

import com.yn.common.util.HttpKit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetNewsUtil {

    public static void main(String[] args) {
//        getNewPeopleList();
        getPeopleNewList();
    }

    /**
     * @Desc 新员工
     * @Author yn
     * @Date 14:51 2020/3/12 0012
     */
    public static void getNewPeopleList() {
        String path = "D:\\newPeople.txt";
        String fin = "D:\\newPeopleByDate.txt";
        File f = new File(path);
        new File(fin);
        Map<String,String> param = new HashMap<>();
        param.put("open_id","huang-bin");
        param.put("co_id","10001");
        param.put("news_type","132c8e7757a044968e6ec7fbcc6e423c");
        param.put("plate_pid","132c8e7757a044968e6ec7fbcc6e423c");
        param.put("plate_id","b249fbac2cc44a50944feb9dcc6a279d");
        int i = 1;
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            FileWriter fw2 = new FileWriter(fin);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            for (;i < 99; i++) {
                param.put("pageNumber",i+"");
                String rs = HttpKit.doPost("http://mp.wexcxc.com/sys/qywx/news/list", param);
                if(rs.contains("没有数据")){
                    break;
                }
//                System.out.println(rs);
                List<String> list = getMatcher(rs.trim(), "h4");
                List<String> time = getMatcher(rs.trim(), "span");
                for (int k = 0; k < list.size(); k++) {
                    bw2.write(list.get(k) + "---------------------" + time.get(k));
                    bw2.newLine();
                }
                bw.write(rs.trim());
                bw.flush();
                bw2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("绝对路径--------------------" + f.getAbsolutePath());
        System.out.println("一共-------" + (i-1));
    }

    /**
     * @Desc 人事公告
     * @Author yn
     * @Date 14:51 2020/3/12 0012
     */
    public static void getPeopleNewList() {
        String path = "D:\\peopleNews.txt";
        String fin = "D:\\peopleNewsByDate.txt";
        File f = new File(path);
        new File(fin);
        Map<String,String> param = new HashMap<>();
        param.put("open_id","huang-bin");
        param.put("co_id","10001");
        param.put("news_type","24d324e25105437688d5d073bc7a9e14");
        param.put("plate_pid","24d324e25105437688d5d073bc7a9e14");
        param.put("plate_id","ba0be40d7f4f4e8a830f60a1b699667c");
        int i = 1;
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            FileWriter fw2 = new FileWriter(fin);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            for (;i < 99; i++) {
                param.put("pageNumber",i+"");
                String rs = HttpKit.doPost("http://mp.wefddfwwev.com/sys/qywx/news/list", param);
                if(rs.contains("没有数据")){
                    break;
                }
//                System.out.println(rs);
                List<String> list = getMatcher(rs.trim(), "h4");
                List<String> time = getMatcher(rs.trim(), "span");
                for (int k = 0; k < list.size(); k++) {
                    bw2.write(list.get(k) + "---------------------" + time.get(k));
                    bw2.newLine();
                }
                bw.write(rs.trim());
                bw.flush();
                bw2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("绝对路径--------------------" + f.getAbsolutePath());
        System.out.println("一共-------" + (i-1));
    }

    /**
     * @Desc 获取标签里面的内容
     * @Author yn
     * @Date 10:09 2020/3/12 0012
     */
    public static List<String> getMatcher(String str, String tag){
        ArrayList<String> list = new ArrayList<>();
        String regex=".*<"+tag+".*?>(.*?)</"+tag+">";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while(m.find()){
            list.add(m.group(1));
        }
        return list;
    }
}
