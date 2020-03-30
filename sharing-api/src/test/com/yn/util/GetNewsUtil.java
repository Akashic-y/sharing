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

    //获取列表url
    private static String url = "";

    //获取详情url
    private static String detailUrl = "";

    //用户名 带-
    private static String open_id = "";

    //循环次数
    private static int timer = 99;

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();   //获取开始时间

//        getNewPeopleList();
//        getPeopleNewList();
        getAllCompanyNews();
        getAllCompanyNotice();

        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
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
                "","allNews",true);
    }

    /**
     * @Desc 获取company公告
     * @Author yn
     * @Date 16:52 2020/3/27 0027
     */
    public static void getAllCompanyNotice() {
        getNewByType("24d324e25105437688d5d073bc7a9e14","24d324e25105437688d5d073bc7a9e14",
                "","allNotice",true);
    }

    /**
     * @Desc 通过新闻类型获取新闻
     * @param getDetail 是否获取详情
     * @Author yn
     * @Date 15:09 2020/3/30 0030
     */
    public static void getNewByType(String news_type,String plate_pid,String plate_id,String fileName,boolean getDetail) {
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
            BufferedWriter detailBw = null;
            if(getDetail){
                String detailFile = path +fileName+"Detail.txt";;
                FileWriter detailFw = new FileWriter(detailFile);
                detailBw = new BufferedWriter(detailFw);
            }
            for (;i < timer; i++) {
                param.put("pageNumber",i+"");
                String rs = HttpKit.doPost(url, param);
                if(rs.contains("没有数据")){
                    break;
                }
//                System.out.println(rs);
                List<String> list = getMatcher(rs.trim(), "h4");
                List<String> time = getMatcher(rs.trim(), "span");
                if(getDetail){
                    List<String> newsIds = getNewsIds(rs.trim());
                    getNewsDetail(newsIds,detailBw);
                }
                for (int k = 0; k < list.size(); k++) {
                    bw2.write(list.get(k) + "---------------------" + time.get(k));
                    bw2.newLine();
                }
                bw.write(rs.trim().replaceAll("&nbsp;",""));
                bw.flush();
                bw2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("绝对路径--------------------" + f.getAbsolutePath());
        System.out.println("一共-------" + (i-1) +"页");
    }

    /**
     * @Desc 获取新闻详情，写入文件
     * @Author yn
     * @Date 15:09 2020/3/30 0030
     */
    private static void getNewsDetail(List<String> newsIds, BufferedWriter detailBw) {
        Map<String,String> param = new HashMap<>();
        param.put("open_id",open_id);
        for (String id : newsIds) {
//            System.out.println(id);
            param.put("newsId",id);
            String rs = HttpKit.doPost(detailUrl, param).replaceAll("[\\t\\n\\r]", "").replaceAll("&nbsp;","");
            List<String> detail = getMatcher(rs, "div class=\"news\"","div");
            for (String str : detail) {
                try {
                    detailBw.write(str);
                    detailBw.newLine();
                    detailBw.write("--------------------------------------------------------");
                    detailBw.newLine();
                    detailBw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Desc 获取详情
     * @Author yn
     * @Date 14:59 2020/3/30 0030
     */
    public static void getNewByType(String news_type,String plate_pid,String plate_id,String fileName) {
        getNewByType(news_type,plate_pid,plate_id,fileName,false);
    }

    /**
     * @Desc 标签头与标签尾相同时的匹配
     * @Author yn
     * @Date 10:09 2020/3/12 0012
     */
    public static List<String> getMatcher(String str, String tag){
        return getMatcher(str,tag,tag);
    }

    /**
     * @Desc 获取标签里面的内容
     * @Author yn
     * @Date 15:38 2020/3/30 0030
     */
    public static List<String> getMatcher(String str, String tag,String endTag){
        ArrayList<String> list = new ArrayList<>();
        String regex=".*<"+tag+".*?>(.*?)</"+endTag+">";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while(m.find()){
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * @Desc 获取新闻id
     * @Author yn
     * @Date 2020年3月30日14:57:55
     */
    public static List<String> getNewsIds(String str){
        ArrayList<String> list = new ArrayList<>();
        String regex="news_info\\('(.*?)'\\)";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while(m.find()){
            list.add(m.group(1));
        }
        return list;
    }

}
