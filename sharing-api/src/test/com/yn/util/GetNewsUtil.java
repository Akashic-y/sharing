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

    //文件保存路径
    private static String path = "D:\\";

    //请求地址
    private static String url = "tr";

    //用户名
    private static String open_id = "";

    //循环次数
    private static int timer = 99;

    public static void main(String[] args) {
//        getNewPeopleList();
//        getPeopleNewList();
//        getAllCompanyNews();
//        getAllCompanyNotice();
        String str = "onclick=\"news_info('92f999bb650d4c219122d7d7f9b48250')\">";
        getMatcher(str);
    }

    /**
     * @Desc 新员工
     * @Author yn
     * @Date 14:51 2020/3/12 0012
     */
    public static void getNewPeopleList() {
        getNewByType("132c8e7757a044968e6ec7fbcc6e423c","132c8e7757a044968e6ec7fbcc6e423c",
                "b249fbac2cc44a50944feb9dcc6a279d","newStaff");
    }

    /**
     * @Desc 人事公告
     * @Author yn
     * @Date 14:51 2020/3/12 0012
     */
    public static void getPeopleNewList() {
        getNewByType("24d324e25105437688d5d073bc7a9e14","24d324e25105437688d5d073bc7a9e14",
                "ba0be40d7f4f4e8a830f60a1b699667c","peopleNews");
    }

    /**
     * @Desc 获取所有company新闻
     * @Author yn
     * @Date 16:51 2020/3/27 0027
     */
    public static void getAllCompanyNews(){
        getNewByType("132c8e7757a044968e6ec7fbcc6e423c","132c8e7757a044968e6ec7fbcc6e423c",
                "","allNews");
    }

    /**
     * @Desc 获取company公告
     * @Author yn
     * @Date 16:52 2020/3/27 0027
     */
    public static void getAllCompanyNotice() {
        getNewByType("24d324e25105437688d5d073bc7a9e14","24d324e25105437688d5d073bc7a9e14",
                "","allNotice");
    }

    public static void getNewByType(String news_type,String plate_pid,String plate_id,String fileName) {
        String filePath = path +fileName+".txt";
        String fin = path +fileName+"ByDate.txt";
        File f = new File(filePath);
        new File(fin);
        Map<String,String> param = new HashMap<>();
        param.put("open_id",open_id);
        param.put("co_id","10001");
        param.put("news_type",news_type);
        param.put("plate_pid",plate_pid);
        param.put("plate_id",plate_id);
        int i = 1;
        try {
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            FileWriter fw2 = new FileWriter(fin);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            for (;i < timer; i++) {
                param.put("pageNumber",i+"");
                String rs = HttpKit.doPost(url, param);
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

    /**
     * @Desc 获取标签里面的内容
     * @Author yn
     * @Date 10:09 2020/3/12 0012
     */
    public static List<String> getMatcher(String str){
        ArrayList<String> list = new ArrayList<>();
        String regex=".*onclick=\"news_info('(.*?)')\">";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while(m.find()){
            list.add(m.group(1));
        }
        return list;
    }

}
