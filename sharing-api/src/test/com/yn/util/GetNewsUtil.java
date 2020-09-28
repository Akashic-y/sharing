package com.yn.util;

import com.yn.common.util.HttpKit;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetNewsUtil {

    //文件保存路径
    private static String path = "D:\\";

    //获取列表url
    private static String url = "";

    //获取详情url
    private static String detailUrl = "";

    //获取详情url
    private static String imgServerPath = "";

    //用户名 带-
    private static String open_id = "";

    //循环次数
    private static int timer = 99;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();   //获取开始时间

//        getNewStaffList("D:\\allNewsByDate.txt","D:\\allNoticeByDate.txt");
//        getPeopleNewList();
//        getAllCompanyNews();
//        getAllCompanyNotice();
//        downloadImg(path + "allNewsDetail.txt");
        compareEmployees("C:\\Users\\Administrator\\Desktop\\日常文件\\txt\\人员_20200824.txt",
                "D:\\newStaffByDate.txt");
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }

    /**
     * @Desc 新员工
     * @Author yn
     * @Date 14:51 2020/3/12 0012
     */
    public static void getNewStaffList(String allNewsByDatePath, String allNoticeByDatePath) {
//        getNewByType("132c8e7757a044968e6ec7fbcc6e423c", "132c8e7757a044968e6ec7fbcc6e423c",
//                "b249fbac2cc44a50944feb9dcc6a279d", "newStaff");

        try {
            String fin = path + "newStaffByDate.txt";
            File writeName = new File(fin);
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            FileReader reader = new FileReader(allNewsByDatePath);
            BufferedReader br = new BufferedReader(reader);
            FileReader reader2 = new FileReader(allNoticeByDatePath);
            BufferedReader br2 = new BufferedReader(reader2);
            String line;

            while ((line = br2.readLine()) != null) {
                if(line.contains("入职") || line.contains("加入") || line.contains("回归")){
                    out.write(line + "\r\n");
                }
            }

            while ((line = br.readLine()) != null) {
                if(line.contains("入职") || line.contains("加入") || line.contains("回归")){
                    out.write(line + "\r\n");
                }
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Desc 人事公告
     * @Author yn
     * @Date 14:51 2020/3/12 0012
     */
    public static void getPeopleNewList() {
        getNewByType("24d324e25105437688d5d073bc7a9e14", "24d324e25105437688d5d073bc7a9e14",
                "ba0be40d7f4f4e8a830f60a1b699667c", "peopleNews");
    }

    /**
     * @Desc 获取所有company新闻
     * @Author yn
     * @Date 16:51 2020/3/27 0027
     */
    public static void getAllCompanyNews() {
        getNewByType("132c8e7757a044968e6ec7fbcc6e423c", "132c8e7757a044968e6ec7fbcc6e423c",
                "", "allNews", true);
    }

    /**
     * @Desc 获取company公告
     * @Author yn
     * @Date 16:52 2020/3/27 0027
     */
    public static void getAllCompanyNotice() {
        getNewByType("24d324e25105437688d5d073bc7a9e14", "24d324e25105437688d5d073bc7a9e14",
                "", "allNotice", true);
    }

    /**
     * @param getDetail 是否获取详情
     * @Desc 通过新闻类型获取新闻
     * @Author yn
     * @Date 15:09 2020/3/30 0030
     */
    public static void getNewByType(String news_type, String plate_pid, String plate_id, String fileName, boolean getDetail) {
        String filePath = path + fileName + ".txt";
        String fin = path + fileName + "ByDate.txt";
        File f = new File(filePath);
        new File(fin);
        Map<String, String> param = new HashMap<>();
        param.put("open_id", open_id);
        param.put("co_id", "10001");
        param.put("news_type", news_type);
        param.put("plate_pid", plate_pid);
        param.put("plate_id", plate_id);
        int i = 1;
        try {
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            FileWriter fw2 = new FileWriter(fin);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            BufferedWriter detailBw = null;
            if (getDetail) {
                String detailFile = path + fileName + "Detail.txt";
                FileWriter detailFw = new FileWriter(detailFile);
                detailBw = new BufferedWriter(detailFw);
            }
            for (; i < timer; i++) {
                param.put("pageNumber", i + "");
                String rs = HttpKit.doPost(url, param);
                if (rs.contains("没有数据")) {
                    break;
                }
//                System.out.println(rs);
                List<String> list = getMatcher(rs.trim(), "h4");
                List<String> time = getMatcher(rs.trim(), "span");
                if (getDetail) {
                    List<String> newsIds = getNewsIds(rs.trim());
                    getNewsDetail(newsIds, detailBw);
                }
                for (int k = 0; k < list.size(); k++) {
                    bw2.write(list.get(k) + "---------------------" + time.get(k));
                    bw2.newLine();
                }
                bw.write(rs.trim().replaceAll("&nbsp;", ""));
                bw.flush();
                bw2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("绝对路径--------------------" + f.getAbsolutePath());
        System.out.println("一共-------" + (i - 1) + "页");
    }

    /**
     * @Desc 获取新闻详情，写入文件
     * @Author yn
     * @Date 15:09 2020/3/30 0030
     */
    private static void getNewsDetail(List<String> newsIds, BufferedWriter detailBw) {
        Map<String, String> param = new HashMap<>();
        param.put("open_id", open_id);
        for (String id : newsIds) {
//            System.out.println(id);
            param.put("newsId", id);
            String rs = HttpKit.doPost(detailUrl, param).replaceAll("[\\t\\n\\r]", "").replaceAll("&nbsp;", "");
            List<String> detail = getMatcher(rs, "div class=\"news\"", "div");
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
    public static void getNewByType(String news_type, String plate_pid, String plate_id, String fileName) {
        getNewByType(news_type, plate_pid, plate_id, fileName, false);
    }

    /**
     * @Desc 标签头与标签尾相同时的匹配
     * @Author yn
     * @Date 10:09 2020/3/12 0012
     */
    public static List<String> getMatcher(String str, String tag) {
        return getMatcher(str, tag, tag);
    }

    /**
     * @Desc 获取标签里面的内容
     * @Author yn
     * @Date 15:38 2020/3/30 0030
     */
    public static List<String> getMatcher(String str, String tag, String endTag) {
        ArrayList<String> list = new ArrayList<>();
        String regex = ".*<" + tag + ".*?>(.*?)</" + endTag + ">";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * @Desc 获取新闻id
     * @Author yn
     * @Date 2020年3月30日14:57:55
     */
    public static List<String> getNewsIds(String str) {
        ArrayList<String> list = new ArrayList<>();
        String regex = "news_info\\('(.*?)'\\)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * @Desc 获取src属性
     * @Author yn
     * @Date 20:08 2020/3/30 0030
     */
    public static List<String> getSrc(String str) {
        ArrayList<String> list = new ArrayList<>();
        String regex = "src=\"(.*?)\"";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * @Desc 获取文件中img标签的src属性 下载图片
     * @Author yn
     * @Date 20:02 2020/3/30 0030
     */
    public static void downloadImg(String fileName) {
        try {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 6,
                    200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000));
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                List<String> imgSrcs = getSrc(line);
                for (String str : imgSrcs) {
                    String[] split = str.split("/");
                    String imgPath;
                    imgPath = path + "img\\" + split[split.length - 1];
                    MyTask myTask = new MyTask(str.startsWith("http") ? str : imgServerPath + str, imgPath);
                    executor.execute(myTask);
//                    downloadPicture(str.startsWith("http")?str : imgServerPath + str,imgPath);
                }
            }
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Desc 链接url下载图片
     * @Author yn
     * @Date 10:49 2020/3/31 0031
     */
    public static void downloadPicture(String urlStr, String path) {
        URL url;
        try {
            File file = new File(path);
            if (!file.exists()) {
                url = new URL(urlStr);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
//            BASE64Encoder encoder = new BASE64Encoder();
//            String encode = encoder.encode(buffer);//返回Base64编码过的字节数组字符串
//            System.out.println(encode);
                fileOutputStream.write(output.toByteArray());
                dataInputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
//            System.out.println(urlStr);
            e.printStackTrace();
        }
    }

    /**
     * @Desc 以前入职和在职比较详情
     * @Author yn
     * @Date 18:24 2020/8/24 0024
     */
    public static void compareEmployees(String employeeTxtPath,String newsTxtPath){
        List<String> newsList=new ArrayList<>();
        List<String> employeeList=new ArrayList<>();
        try {
            FileReader reader = new FileReader(employeeTxtPath);
            BufferedReader br = new BufferedReader(reader);
            FileReader reader2 = new FileReader(newsTxtPath);
            BufferedReader br2 = new BufferedReader(reader2);
            File writeName = new File("C:\\Users\\Administrator\\Desktop\\employeeInInfo.txt");
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter inFile = new BufferedWriter(writer);
            File employeeOutInfo = new File("C:\\Users\\Administrator\\Desktop\\employeeOutInfo.txt");
            employeeOutInfo.createNewFile();
            FileWriter outWriter = new FileWriter(employeeOutInfo);
            BufferedWriter outFile = new BufferedWriter(outWriter);
            Map<String,String> map = new HashMap<>();
            Map<String,String> isOldMap = new HashMap<>();
            String line;

            while ((line = br2.readLine()) != null) {
                newsList.add(line);
            }

            while ((line = br.readLine()) != null) {
                String[] split = line.split(";");
                for (String employeeName : split) {
                    if(employeeName.contains("-")) employeeName = employeeName.split("-")[0];
                    employeeList.add(employeeName);
                }
            }

            for (String news : newsList) {
                map.put(news,"");
                StringBuilder value = new StringBuilder();
                for (String employeeName : employeeList) {
                    if(news.contains(employeeName)){
                        value.append(employeeName).append(",");
                        isOldMap.put(employeeName,"N");
                    }
                }
                map.put(news,value.toString());
            }

            //下面是写入文件
            for (String news : newsList) {
                if("".equals(map.get(news))){
                    outFile.write(news + "\r\n");
                }else {
                    inFile.write(news +" In: " +map.get(news) + "\r\n");
                }
            }

            inFile.write("老-----------------------------\r\n");
            for (String employeeName : employeeList) {
                if(isOldMap.get(employeeName) == null)
                    inFile.write(employeeName + "\r\n");
            }
            inFile.flush();
            outFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class MyTask implements Runnable {

    private String urlStr;
    private String path;

    public MyTask(String urlStr, String path) {
        this.urlStr = urlStr;
        this.path = path;
    }

    @Override
    public void run() {
//        System.out.println(urlStr);
        GetNewsUtil.downloadPicture(urlStr, path);
//        System.out.println("task "+path+"执行完毕");
    }
}
