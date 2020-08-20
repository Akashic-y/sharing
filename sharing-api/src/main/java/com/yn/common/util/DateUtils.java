package com.yn.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author YN
 * @ClassName: CDate
 * @Description: 处理日期
 * @date 2019年8月27日 下午5:52:04
 */
public class DateUtils {

    /*
     * 方法功能：获得当前运行毫秒 参数说明： 返回值：
     */
    public static long getcurrenttimemillis() {
        return System.currentTimeMillis();
    }

    /*
     * 方法功能：获得当前时间 参数说明： 返回值：
     */
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss SSS";// 默认日期格式
    public static final String DATEFORMAT2 = "yyyy-MM-dd HH:mm:ss";// 默认日期格式
    public static final String DATEFORMAT3 = "yyyy-MM-dd";// 默认日期格式
    public static final String DATEFORMAT4 = "yyyy-MM";// 默认日期格式
    public static final String DATEFORMAT5 = "yyyyMMdd_HH_mm_ss_SSS";// 默认日期格式

    public static String getcurrentdate() {
        return getdateformat(new Date(), DATEFORMAT);
    }

    public static String getcurrentdate2() {
        return getdateformat(new Date(), DATEFORMAT2);
    }

    public static String getcurrentdate3() {
        return getdateformat(new Date(), DATEFORMAT3);
    }

    public static String getcurrentdate4() {
        return getdateformat(new Date(), DATEFORMAT5);
    }

    /*
     * 方法功能：获得当前时间 参数说明： 返回值：
     */
    public static Date getcurdate() {
        return new Date();
    }

    public static Calendar getCalender() {
        return Calendar.getInstance();
    }

    public static String getdate(Date adt_rq) {
        return getdateformat(adt_rq, DATEFORMAT);
    }

    // 根据格式返回日期字符串
    public static String getdateformat(String as_format) {
        return getdateformat(getcurdate(), as_format);
    }

    // 根据格式返回日期字符串
    public static String getdateformat(Date adt_rq, String as_format) {
        /*
         * y 年号，如 1996 M 月份，如 July 或者 07<<<-------月份大写 MM d 月中第几天，如 12 H 小时(24制)，如 0、17
         * m 分钟,如 32 s 钞钟，如55 S 微钞，如978 E 星期几，如 Tuesday D 一年中的第几天，如 189 w week in year
         * (Number) 27 W week in month (Number) 2 a am/pm marker (Text) PM k hour in day
         * (1~24) (Number) 24 K hour in am/pm (0~11) (Number) 0 z time zone (Text)
         * Pacific Standard Time ' escape for text (Delimiter) '' single quote (Literal)
         */

        try {
            SimpleDateFormat dateformat = null;
            if (as_format.equals("dd-MMM1-yy")) {
                dateformat = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
            } else if (as_format.equals("dd-MMM2-yy")) {
                dateformat = new SimpleDateFormat("dd-MMM-yy");
            } else {
                dateformat = new SimpleDateFormat(as_format);
            }
            return dateformat.format(adt_rq);
        } catch (Exception ex) {
            return "";
        }
    }

    // 获得当前日期增加指定天数后的日期
    public static Date getnext(Date adt_rq, int ai_days) {
//         int year = Integer.parseInt(getdateinfo(adt_rq,"yyyy"));
//         int month = Integer.parseInt(getdateinfo(adt_rq,"month")) - 1;
//         int date = Integer.parseInt(getdateinfo(adt_rq,"day"));
//
//         GregorianCalendar nextDay = new GregorianCalendar(year, month, date);
//         nextDay.add(GregorianCalendar.DATE,ai_days);
//         Date l_date = nextDay.getTime();
//         return l_date;
        return getnext(adt_rq, ADDTYPE_DAY, ai_days);
    }

    public static final byte ADDTYPE_DAY = 0x01;// 天
    public static final byte ADDTYPE_HOUR = 0x02;// 小时
    public static final byte ADDTYPE_MINUTE = 0x03;// 分
    public static final byte ADDTYPE_SECONDS = 0x04; // 秒
    public static final byte ADDTYPE_MONTH = 0x05; // 月
    public static final byte ADDTYPE_YEAR = 0x06; // 年
    // 获得当前时间增加指定分钟数后的时间

    public static Date getnext(Date adt_rq, byte lb_type, int ai_step) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getmonth(adt_rq)) - 1;
        int date = Integer.parseInt(getdateformat(adt_rq, "dd"));
        int hh = Integer.parseInt(getdateformat(adt_rq, "HH"));
        int mm = Integer.parseInt(getdateformat(adt_rq, "mm"));
        int ss = Integer.parseInt(getdateformat(adt_rq, "ss"));
        GregorianCalendar nextDay = new GregorianCalendar(year, month, date, hh, mm, ss);
        switch (lb_type) {
            case ADDTYPE_YEAR:
                nextDay.add(GregorianCalendar.YEAR, ai_step);
                break;
            case ADDTYPE_MONTH:
                nextDay.add(GregorianCalendar.MONTH, ai_step);
                break;
            case ADDTYPE_DAY:
                nextDay.add(GregorianCalendar.DATE, ai_step);
                break;
            case ADDTYPE_HOUR:
                nextDay.add(GregorianCalendar.HOUR, ai_step);
                break;
            case ADDTYPE_MINUTE:
                nextDay.add(GregorianCalendar.MINUTE, ai_step);
                break;
            case ADDTYPE_SECONDS:
                nextDay.add(GregorianCalendar.SECOND, ai_step);
                break;
        }
        Date l_date = nextDay.getTime();
        return l_date;
    }

    // 获得两个日期间隔时间
    public static long getruntime(Date adt_rq1, Date adt_rq2, String as_lx) {
        long ll_return = 0;
        switch (as_lx) {
            case "秒":
                ll_return = (adt_rq2.getTime() - adt_rq1.getTime()) / 1000;
                break;
            case "分":
                ll_return = (adt_rq2.getTime() - adt_rq1.getTime()) / 60000;
                break;
            case "小时":
                ll_return = (adt_rq2.getTime() - adt_rq1.getTime()) / 3600000;
                break;
            case "天":
                ll_return = (adt_rq2.getTime() - adt_rq1.getTime()) / (24 * 3600000);
                break;
            case "月":
                ll_return = Long.parseLong(getmonth(adt_rq2)) - Long.parseLong(getmonth(adt_rq1));
                ll_return += (Long.parseLong(getyear(adt_rq2)) - Long.parseLong(getyear(adt_rq1))) * 12;
//             if(ll_return<0) ll_return+=12;
                break;
            case "年":
                ll_return = Long.parseLong(getyear(adt_rq2)) - Long.parseLong(getyear(adt_rq1));
                break;
            case "毫秒":
                ll_return = (adt_rq2.getTime() - adt_rq1.getTime());
                break;
        }

        return ll_return;
    }

    // 获得两个日期间隔时间
    public static String getruntime2(Date adt_rq1, Date adt_rq2) {
        return getruntime2(adt_rq1, adt_rq2, "毫秒");
    }

    public static String getruntime2(Date adt_rq1, Date adt_rq2, String as_lx) {
        StringBuffer lsb_rtn = new StringBuffer(50);
        long ll_ms = adt_rq2.getTime() - adt_rq1.getTime();
//         System.out.println(ll_ms);
        String[] larray_1 = {"天", "小时", "分", "秒", "毫秒"};
        int[] larray_2 = {24 * 3600000, 3600000, 60000, 1000, 1};
        int li_count = larray_1.length;
        for (int li_1 = 0; li_1 < li_count; li_1++) {
            if (ll_ms == 0)
                return lsb_rtn.toString();
            long ll_item = ll_ms / larray_2[li_1];
            if (ll_item > 0) {
                lsb_rtn.append(ll_item);
                lsb_rtn.append(larray_1[li_1]);
                ll_ms -= ll_item * larray_2[li_1];
            }
            if (larray_1[li_1].equals(as_lx))
                return lsb_rtn.toString();
        }
        return lsb_rtn.toString();
    }

    // 获得从指定日期到现在的运行时间
    public static String getruntime(Date adt_rq1) {
        Date ldt_2 = new Date();
        return DateUtils.getdate(ldt_2) + ":" + DateUtils.getruntime2(adt_rq1, ldt_2);
    }

    // 将字符串转化为日期
    public static Date getdate(String as_rq, String as_lx) {
        Date ldt_rq = null;
        SimpleDateFormat df = null;
        if (as_lx.equals("dd-MMM1-yy")) {
            df = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
        } else if (as_lx.equals("dd-MMM2-yy")) {
            df = new SimpleDateFormat("dd-MMM-yy");
        } else {
            df = new SimpleDateFormat(as_lx);
        }
        df.setLenient(false);// 严格控制输入
        try {
            ldt_rq = df.parse(as_rq);
        } catch (Exception ex) {
            return null;
        }
        return ldt_rq;
    }

    public static Date getdate(String as_date) {
        Date ldt_rq;
        try {
            int li_len = as_date.length();
            if (as_date.contains("-") && li_len == 10) {
                ldt_rq = getdate(as_date, "yyyy-MM-dd");
            } else if (as_date.contains("-") && li_len >= 8 && li_len <= 9) {
                ldt_rq = getdate(as_date, "yyyy-M-d");
            } else if (as_date.contains("-") && li_len == 16) {
                ldt_rq = getdate(as_date, "yyyy-MM-dd HH:mm");
            } else if (as_date.contains("-") && li_len == 19) {
                ldt_rq = getdate(as_date, "yyyy-MM-dd HH:mm:ss");
            } else if (as_date.contains("-") && li_len == 23) {
                ldt_rq = getdate(as_date, "yyyy-MM-dd HH:mm:ss SSS");
            } else if (as_date.contains("-") && li_len >= 21 && li_len <= 22) {
                ldt_rq = getdate(as_date, "yyyy-M-d HH:mm:ss SSS");
            } else if (as_date.contains("-") && li_len == 7) {
                ldt_rq = getdate(as_date, "yyyy-MM");
            } else if (as_date.contains("-") && li_len == 6) {
                ldt_rq = getdate(as_date, "yyyy-M");

            } else if (as_date.contains("/") && li_len == 10) {
                ldt_rq = getdate(as_date, "yyyy/MM/dd");
            } else if (as_date.contains("/") && li_len >= 8 && li_len <= 9) {
                ldt_rq = getdate(as_date, "yyyy/M/d");
            } else if (as_date.contains("/") && li_len == 16) {
                ldt_rq = getdate(as_date, "yyyy/MM/dd HH:mm");
            } else if (as_date.contains("/") && li_len == 19) {
                ldt_rq = getdate(as_date, "yyyy/MM/dd HH:mm:ss");
            } else if (as_date.contains("/") && li_len == 23) {
                ldt_rq = getdate(as_date, "yyyy/MM/dd HH:mm:ss SSS");
            } else if (as_date.contains("/") && li_len >= 21 && li_len <= 22) {
                ldt_rq = getdate(as_date, "yyyy/M/d HH:mm:ss SSS");
            } else if (as_date.contains("/") && li_len == 7) {
                ldt_rq = getdate(as_date, "yyyy/MM");
            } else if (as_date.contains("/") && li_len == 6) {
                ldt_rq = getdate(as_date, "yyyy/M");

            } else if (li_len == 8) {
                ldt_rq = getdate(as_date, "yyyyMMdd");
            } else if (li_len == 6) {
                ldt_rq = getdate(as_date, "yyyyMM");
            } else if (li_len == 4) {
                ldt_rq = getdate(as_date, "yyyy");
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
        return ldt_rq;
    }

    // 获得日期
    public static Date getdate(long al_time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(al_time);
        return cal.getTime();
    }

    public static Date getdate(final int yyyy, final int month, final int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(yyyy, month - 1, day);
        return cal.getTime();
    }

    public static Date getdate(final int yyyy, final int month, final int day, final int hour, final int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(yyyy, month - 1, day, hour, min);
        return cal.getTime();
    }

    // 判断是否为闰年
    public static boolean pdisleap(int ai_year) {
        Calendar cal = Calendar.getInstance();
        return ((GregorianCalendar) cal).isLeapYear(ai_year);
    }
//    /**
//#   * 判断是否润年
//#   *
//#   * @param ddate
//#   * @return
//#   */
//    public static boolean isLeapYear(String ddate) {
//        /**
//#    * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
//#    * 3.能被4整除同时能被100整除则不是闰年
//#    */
//        Date d = strToDate(ddate);
//        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
//        gc.setTime(d);
//        int year = gc.get(Calendar.YEAR);
//        if ((year % 400) == 0)
//            return true;
//        else if ((year % 4) == 0) {
//            if ((year % 100) == 0)
//                return false;
//            else
//                return true;
//        } else
//            return false;
//    }

    // 判断是否为日期
    public static boolean pdisdate(String as_date) {
        Date ldt_rq = null;
        ldt_rq = getdate(as_date);
        return ldt_rq != null;
    }

    //  判断是否为日期
    public static boolean pdisdate(String as_date, String as_lx) {
        Date ldt_rq = null;
        ldt_rq = getdate(as_date, as_lx);
        return ldt_rq != null;
    }

    //  判断是否为生日
    public static boolean pdissr(String as_sr) {
        int li_len = as_sr.length();
        StringBuffer lsb_rq = new StringBuffer(10);
        String ls_lx = null;
        lsb_rq.append("2000");
        if (as_sr.contains("-") && li_len == 5) {
            lsb_rq.append("-");
            lsb_rq.append(as_sr);
            ls_lx = "yyyy-MM-dd";
        } else if (as_sr.contains("-") && li_len >= 3 && li_len <= 4) {
            lsb_rq.append("-");
            lsb_rq.append(as_sr);
            ls_lx = "yyyy-M-d";
        } else if (as_sr.contains("/") && li_len == 5) {
            lsb_rq.append("/");
            lsb_rq.append(as_sr);
            ls_lx = "yyyy/MM/dd";
        } else if (as_sr.contains("/") && li_len >= 3 && li_len <= 4) {
            lsb_rq.append("/");
            lsb_rq.append(as_sr);
            ls_lx = "yyyy/M/d";
        } else {
            return false;
        }
        return pdisdate(lsb_rq.toString(), ls_lx);
    }

    // 获得指定日期在年中位于第几天
    public static int getdayofyear(Date adt_rq) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(getyear(adt_rq)), Integer.parseInt(getmonth(adt_rq)) - 1,
                Integer.parseInt(getday(adt_rq)));
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    // 获得指定年第几天的日期
    public static Date getdayofyear(int ai_year, int ai_day) {
        Calendar cal = Calendar.getInstance();
        cal.set(ai_year, Calendar.DECEMBER, 31);
        cal.set(Calendar.DAY_OF_YEAR, ai_day);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        return getdate(year + "-" + month + "-" + day, "yyyy-M-d");
    }

    // 获得指定年天数
    public static int getdayofyear(int ai_year) {
        Calendar cal = Calendar.getInstance();
        cal.set(ai_year, Calendar.DECEMBER, 31);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public static String getcurrentyear() {
        // 获得当前年份
        return getdateformat(getcurdate(), "yyyy");
    }

    public static String getyear(Date adt_rq) {
        // 获得指定年份
        return getdateformat(adt_rq, "yyyy");
    }

    // 计算某天属于哪一半年
    public static String getbannian(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));
        if (month >= 0 && month <= 6) {
            return year + "/上";
        } else {
            return year + "/下";
        }
    }

    // 计算某天属于哪一半年
    public static String getbannianms(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        if (month >= 0 && month <= 6) {
            return year + "上半年";
        } else {
            return year + "下半年";
        }
    }

    // 计算某天属于哪一季
    public static String getji(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        if (month >= 0 && month <= 3) {
            return year + "/1";
        } else if (month >= 4 && month <= 6) {
            return year + "/2";
        } else if (month >= 7 && month <= 9) {
            return year + "/3";
        } else {
            return year + "/4";
        }
    }

    //  计算某天属于哪一季
    public static String getjims(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        if (month >= 0 && month <= 3) {
            return year + "第一季度";
        } else if (month >= 4 && month <= 6) {
            return year + "第二季度";
        } else if (month >= 7 && month <= 9) {
            return year + "第三季度";
        } else {
            return year + "第四季度";
        }
    }

    // 根据月份获得季度的开始月份和结束月份
    public static String[] getjiyf(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        String ls_yf1, ls_yf2;
        if (month >= 0 && month <= 3) {
            ls_yf1 = year + "-01";
            ls_yf2 = year + "-03";
        } else if (month >= 4 && month <= 6) {
            ls_yf1 = year + "-04";
            ls_yf2 = year + "-06";
        } else if (month >= 7 && month <= 9) {
            ls_yf1 = year + "-07";
            ls_yf2 = year + "-09";
        } else {
            ls_yf1 = year + "-10";
            ls_yf2 = year + "-12";
        }
        return new String[]{ls_yf1, ls_yf2};
    }

    public static String getmonth(Date adt_rq) {
        // 获得指定月份
        return getdateformat(adt_rq, "MM");
    }

    // 提取一个月中的最后一天
    public static Date getMonthEnd(int ai_year, int ai_month) {
        String str = "";
        String ls_year = String.valueOf(ai_year);
        String ls_month = String.valueOf(ai_month);

        if (ai_month < 10) {
            ls_month = "0" + ls_month;
        }
        if (ai_month == 1 || ai_month == 3 || ai_month == 5 || ai_month == 7 || ai_month == 8 || ai_month == 10
                || ai_month == 12) {
            str = "31";
        } else if (ai_month == 4 || ai_month == 6 || ai_month == 9 || ai_month == 11) {
            str = "30";
        } else {
            if (pdisleap(ai_year)) {
                str = "29";
            } else {
                str = "28";
            }
        }
        str = ls_year + "-" + ls_month + "-" + str;
        return getdate(str, "yyyy-MM-dd");
    }

    // 提取一个月中的最后一天
    public static Date getMonthEnd(Date adt_rq) {
        return getMonthEnd(Integer.parseInt(DateUtils.getyear(adt_rq)), Integer.parseInt(DateUtils.getmonth(adt_rq)));
    }

    // 提取一个月中的最后一天
    public static String getMonthEnd(String adt_rq) {
        return getdateformat(getMonthEnd(getdate(adt_rq)), DATEFORMAT3);
    }

    // 提取一个月中的第一天
    public static Date getMonthFirst(Date adt_rq) {
        return DateUtils.getdate(DateUtils.getyear(adt_rq) + "-" + DateUtils.getmonth(adt_rq) + "-01");
    }

    // 获得当月第一个星期一的日期
    public static Date getMonthFirstMonday(Date adt_rq) {
        int weeknum = getweek(getMonthFirst(adt_rq));
        if (weeknum == 1) {
            return getMonthFirst(adt_rq);
        } else {
            return getnext(getMonthFirst(adt_rq), (7 - weeknum + 1));
        }
    }

    // 计算某天属于哪一旬
    public static String getxun(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        String ls_month = String.valueOf(month);
        if (month < 10)
            ls_month = "0" + month;
        int day = Integer.parseInt(getdateformat(adt_rq, "dd"));
        if (day >= 0 && day <= 10) {
            return year + "/" + ls_month + "/1";
        } else if (day >= 11 && day <= 20) {
            return year + "/" + ls_month + "/2";
        } else {
            return year + "/" + ls_month + "/3";
        }
    }

    public static String getxunms(Date adt_rq) {
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        String ls_month = String.valueOf(month);
        if (month < 10)
            ls_month = "0" + month;
        int day = Integer.parseInt(getdateformat(adt_rq, "dd"));
        if (day >= 0 && day <= 10) {
            return year + "年" + ls_month + "月上旬";
        } else if (day >= 11 && day <= 20) {
            return year + "年" + ls_month + "月中旬";
        } else {
            return year + "年" + ls_month + "月下旬";
        }
    }

    // 根据日期获得周数
    public static final byte WEEKTYPE_WEEKOFMONTH = 0x01;
    public static final byte WEEKTYPE_WEEKOFYEAR = 0x02;
    public static final byte WEEKTYPE_CHINAWEEKOFYEAR = 0x03;// 中国周

    public static String getweek2(Date adt_rq, byte ab_weektype) {
        int li_week = getweek(adt_rq, ab_weektype);
        if (li_week < 10)
            return "0" + li_week;
        return String.valueOf(li_week);
    }

    public static int getweek(Date adt_rq, byte ab_weektype) {
        String ls_lx = "weekOfYear";
        switch (ab_weektype) {
            case WEEKTYPE_WEEKOFMONTH:
                ls_lx = "weekOfMonth";
                break;
            case WEEKTYPE_WEEKOFYEAR:
                ls_lx = "weekOfYear";
                break;
            case WEEKTYPE_CHINAWEEKOFYEAR:
                ls_lx = "chinaweekOfYear";
                break;
        }
        return getweek(adt_rq, ls_lx);
    }

    public static int getweek(Date adt_rq, String as_lx) {
        int li_return = 0;
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(getdateformat(adt_rq, "yyyy"));
        int month = Integer.parseInt(getdateformat(adt_rq, "MM"));

        int day = Integer.parseInt(getdateformat(adt_rq, "dd"));
        cal.set(year, month - 1, day);
        switch (as_lx) {
            case "weekOfMonth":
                li_return = cal.get(Calendar.WEEK_OF_MONTH);
                break;
            case "weekOfYear":
                li_return = cal.get(Calendar.WEEK_OF_YEAR);
                break;
            case "chinaweekOfYear":
                int li_day = getdayofyear(adt_rq);
                if (li_day % 7 == 0) {
                    li_return = li_day / 7;
                } else {
                    li_return = li_day / 7 + 1;
                }
                break;
        }
        return li_return;
    }

    // 计算某天星期几
    public static int getweek(Date adt_rq) {
        String ls_year = getdateformat(adt_rq, "yyyy");
        int li_c, li_y, li_m, li_d;

        li_c = Integer.parseInt(ls_year.substring(0, ls_year.length() - 2));
        li_y = Integer.parseInt(ls_year.substring(ls_year.length() - 2));
        li_m = Integer.parseInt(getdateformat(adt_rq, "MM"));
        li_d = Integer.parseInt(getdateformat(adt_rq, "dd"));
        return (li_y + (li_y / 4) + (li_c / 4) - 2 * li_c + (26 * (li_m + 1) / 10) + li_d - 1 + 7) % 7;
    }

    /**
     * # * 根据一个日期，返回是星期几的字符串 # * # * @param sdate # * @return #
     */
    public static String getWeekStr(Date adt_rq) {
        String str = String.valueOf(getweek(adt_rq));
        switch (str) {
            case "0":
                str = "星期日";
                break;
            case "1":
                str = "星期一";
                break;
            case "2":
                str = "星期二";
                break;
            case "3":
                str = "星期三";
                break;
            case "4":
                str = "星期四";
                break;
            case "5":
                str = "星期五";
                break;
            case "6":
                str = "星期六";
                break;
        }
        return str;
    }

    /**
     * 方法功能：根据日期获得星期的第一天 参数列表：ab_china:是否中国星期(中国为星期一，其它为星期日) 返回值：成功：对应日期，错误：null
     */
    public static Date getWeekFirst(Date adt_rq, boolean ab_china) {
        String str = String.valueOf(getweek(adt_rq));
        int li_pyl = 0;
        if (ab_china) {
            li_pyl = 1 - Integer.parseInt(str);
            if (li_pyl == 1)
                li_pyl = -7;
        } else {
            li_pyl = -Integer.parseInt(str);
        }
        return getnext(adt_rq, li_pyl);
    }

    /**
     * 方法功能：根据日期获得星期的最后一天 参数列表：ab_china:是否中国星期(中国为星期日，其它为星期六) 返回值：成功：对应日期，错误：null
     */
    public static Date getWeekEnd(Date adt_rq, boolean ab_china) {
        String str = String.valueOf(getweek(adt_rq));
        int li_pyl = 0;
        if (ab_china) {
            li_pyl = 7 - Integer.parseInt(str);
            if (li_pyl == 7)
                li_pyl = 0;
        } else {
            li_pyl = 6 - Integer.parseInt(str);
        }
        return getnext(adt_rq, li_pyl);
    }

    /* 根据日期获得节日名称 */
    public static String getjr(Date adt_rq) {
        String str = DateUtils.getdateformat(adt_rq, "MMdd");
        return getjr(str);
    }

    public static String getjr(String str) {
        if ("0101".equals(str)) {
            str = "元旦";
        } else if ("0214".equals(str)) {
            str = "情人节";
        } else if ("0308".equals(str)) {
            str = "妇女节";
        } else if ("0312".equals(str)) {
            str = "植树节";
        } else if ("0401".equals(str)) {
            str = "愚人节";
        } else if ("0501".equals(str)) {
            str = "劳动节";
        } else if ("0504".equals(str)) {
            str = "青年节";
        } else if ("0601".equals(str)) {
            str = "儿童节";
        } else if ("0701".equals(str)) {
            str = "建党节";
        } else if ("0801".equals(str)) {
            str = "建军节";
        } else if ("0910".equals(str)) {
            str = "教师节";
        } else if ("1001".equals(str)) {
            str = "国庆节";
        } else if ("1031".equals(str)) {
            str = "万圣节";
        } else if ("1112".equals(str)) {
            str = "孙中山诞辰";
        } else if ("1225".equals(str)) {
            str = "圣诞节";
        } else if ("1226".equals(str)) {
            str = "毛泽东诞辰";
        } else if ("0520".equals(str)) {
            str = "母亲节";
        } else if ("0630".equals(str)) {
            str = "父亲节";
        } else if ("1144".equals(str)) {
            str = "感恩节";
        } else {
            str = "";
        }
        return str;
    }

    /* 根据日期获得星座 */
    public static String getxz(Date adt_rq) {
        String str = DateUtils.getdateformat(adt_rq, "MMdd");
        return getxz(str);
    }

    public static String getxz(String str) {
        if (str.length() != 4)
            return "";
        if (str.compareTo("0101") >= 0 && str.compareTo("1231") <= 0) {
        } else {
            return "";
        }
        String[][] con = {{"水瓶座", "0122", "0221"}, {"双鱼座", "0222", "0321"}, {"白羊座", "0322", "0420"},
                {"金牛座", "0421", "0521"}, {"双子座", "0522", "0621"}, {"巨蟹座", "0621", "0721"},
                {"狮子座", "0722", "0821"}, {"处女座", "0822", "0921"}, {"天秤座", "0922", "1021"},
                {"天蝎座", "1022", "1121"}, {"射手座", "1122", "1221"}, {"摩羯座", "1222", "0121"}};
        for (String[] strings : con) {
            if (str.compareTo(strings[1]) >= 0 && str.compareTo(strings[2]) <= 0) {
                return strings[0];
            } else if (strings[1].compareTo(strings[2]) >= 0) {
                if (str.compareTo(strings[1]) >= 0 || str.compareTo(strings[2]) <= 0) {
                    return strings[0];
                }
            }
        }
        return "";
    }

    // 获得指定天
    public static String getday(Date adt_rq) {
        return getdateformat(adt_rq, "dd");
    }

    // 判断指定天是否与当前日期匹配,ai_day:天，ai_pyl:偏移量(单位:天)
    public static boolean mathDay(Date adt_rq, int ai_pyl) {
        try {
            Date ldt_rq1 = getnext(new Date(), -ai_pyl);
            Date ldt_rq2 = getnext(new Date(), ai_pyl);
            if (ldt_rq1.compareTo(adt_rq) <= 0 && ldt_rq2.compareTo(adt_rq) >= 0) {
                return true;
            }
        } catch (java.lang.Throwable ex) {
            return false;
        }
        return false;
    }

    public Date IDT_RQ1 = null;// 运行开始时间

    /**
     * 方法功能：开始运行 参数说明： 返回值：
     */
    public void start() {
        IDT_RQ1 = getcurdate();
    }

    /**
     * 方法功能：结束运行 参数说明： 返回值：运行时间 long-毫秒数,string-运行时间描述
     */
    public long end2() {
        if (IDT_RQ1 == null)
            return 0;
        return getruntime(IDT_RQ1, getcurdate(), "毫秒");
    }

    public String end() {
        return getruntime2(IDT_RQ1, getcurdate());
    }

    /**
     * 两日期的时间 天数 差
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定时间的+/- 一天
     */
    public static String checkOption(String option, String _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        Date date = null;

        try {
            date = sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            cl.setTime(date);
            if ("pre".equals(option)) {
                // 时间减一天
                cl.add(Calendar.DAY_OF_MONTH, -1);
            } else if ("next".equals(option)) {
                // 时间加一天
                cl.add(Calendar.DAY_OF_YEAR, 1);
            } else {
                // donothing
            }
            date = cl.getTime();
            return sdf.format(date);
        }
        return "参数有误！";
    }

    public static List<String> getWeeks(String date, String formatPattern, boolean isCN) throws ParseException {
        List<String> list = new ArrayList<>();
        Calendar c = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        c.setTime(format.parse(date));
        int currentYear = c.get(Calendar.YEAR);
        int weekIndex = c.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 && isCN) {
            c.add(Calendar.DAY_OF_MONTH, -1);
            String d = format.format(c.getTime());
            list = getWeeks(d, formatPattern, true);
        } else {
            c.setWeekDate(currentYear, weekIndex, 1);
            for (int i = 1; i <= 8; i++) {
                c.add(Calendar.DATE, 1);
                list.add(format.format(c.getTime()));
            }
        }
        return list;
    }

    /**
     * @param year
     * @param month
     * @return 返回指定的年-月
     */
    public static String getYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        return new SimpleDateFormat("yyyy-MM").format(cal.getTime());
    }

    public static String getNowYearMonth() {
        return new SimpleDateFormat(DATEFORMAT4).format(new Date());
    }

    /**
     * @param standard  yyyy-MM
     * @param startDate yyyy-MM-dd
     * @param endDate   yyyy-MM-dd
     * @return
     */
    public static boolean compareDate(String standard, String startDate, String endDate) {
        startDate = startDate.substring(0, startDate.lastIndexOf("-"));
        endDate = endDate.substring(0, endDate.lastIndexOf("-"));
        return standard.equals(startDate) && standard.equals(endDate);
    }

    public static String addAssignDays(String _date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);
        cl.add(Calendar.DAY_OF_YEAR, days);
        date = cl.getTime();
        return sdf.format(date);
    }

    /**
     * 两个日期相差几个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonthSpace(String date1, String date2) {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(sdf.parse(date1));
        } catch (ParseException e) {
            return -1;
        }
        try {
            c2.setTime(sdf.parse(date2));
        } catch (ParseException e) {
            return -1;
        }

        result = 12 * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) + c2.get(Calendar.MONTH)
                - c1.get(Calendar.MONTH);

        return result;

    }

    public static Date parseStringDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    public static Date getNowDate() throws ParseException {
        return new SimpleDateFormat(DATEFORMAT3).parse(getcurrentdate3());
    }

    public static void main(String[] args) {
//		System.out.println(getdateformat(getWeekFirst(getdate("2015-04-01"), true),"yyyy-MM-dd"));
//    	Calendar a = Calendar.getInstance();  
//        a.set(Calendar.YEAR, Integer.parseInt("2015-02-02".substring(0, 4)));  
//        a.set(Calendar.MONTH, Integer.parseInt("2015-02-02".substring(5, 7)) - 1);  
//        a.set(Calendar.DATE, 1);  
//        a.roll(Calendar.DATE, -1);
//        System.out.println(a.get(Calendar.DATE));
//    	System.out.println("2015-04-02".substring(0, 4));
//    	System.out.println("2015-04-02".substring(5, 7));
//		
//    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//    	System.out.println(sdf.format(getnext(getdate("2015-04-01"),7-2)));
        System.out.println(getdate(1420041600000L));
    }

    /**
     * 操作日期
     *
     * @author 力
     */

    /**
     * 得到两个时间之间为几个月 输入格式：XXXX-XX,XXXX-XX
     */
    public static int getMonths(String[] date1, String[] date2) {
        // 获取年,月数
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int year2 = Integer.parseInt(date2[0]);
        int month2 = Integer.parseInt(date2[1]);
        // 通过年,月差计算月份差
        return (year2 - year1) * 12 + (month2 - month1) + 1;
    }

    /**
     * @param date
     * @param monthNum
     * @return 2017年2月3日
     * @title 指定日期增加月份
     * @author hhm
     * @opType add
     */
    public static String addMonth(String date, int monthNum) {
        Calendar calendar = getCalender();
        calendar.setTime(getdate(date, DateUtils.DATEFORMAT3));
        calendar.add(Calendar.MONTH, monthNum);
        return DateFormatUtils.format(calendar, DateUtils.DATEFORMAT3);
    }

    public static String getThen() {
        int hour = getCalender().get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 8) {
            return "早上好";
        } else if (hour >= 8 && hour < 11) {
            return "上午好";
        } else if (hour >= 11 && hour < 13) {
            return "中午好";
        } else if (hour >= 13 && hour < 18) {
            return "下午好";
        } else {
            return "晚上好";
        }
    }
}
