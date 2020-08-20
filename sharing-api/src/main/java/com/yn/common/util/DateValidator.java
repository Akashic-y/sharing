package com.yn.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 时间校验器
 *
 * @author YN 2017年2月6日 上午9:38:11
 */
public class DateValidator {
    /**
     * 根据时间格式获取所匹配的正则
     */
    public static final String DateReg = "(((19)|([2][0-2]))([0-9]{2}))[(-|年|/)]?([0-1]?[0-9]{1})[(-|月|/)]?(([0-3]?[0-9]{1})[日]?(((\\s)+([0-2]?[0-9]?)(([:]([0-6]?[0-9]?){1}([:]([0-6]?[0-9]?)?)))?))?)?";

    public static final TimeZone zone = TimeZone.getTimeZone("GMT");
    /**
     * 各种时间格式<br/>
     * format1:yyyy年MM月dd日 HH:mm:ss<br/>
     * format2:yyyy年MM月dd日 HH:mm<br/>
     * format3:yyyy年MM月dd日 HH<br/>
     * format4:yyyy年MM月dd日<br/>
     * <p>
     * format5:yyyy-MM-dd HH:mm:ss<br/>
     * format6:yyyy-MM-dd HH:mm<br/>
     * format7:yyyy-MM-dd HH<br/>
     * format8:yyyy-MM-dd<br/>
     * <p>
     * format9:yyyy/MM/dd HH:mm:ss<br/>
     * format10:yyyy/MM/dd HH:mm<br/>
     * format11:yyyy/MM/dd HH<br/>
     * format12:yyyy/MM/dd<br/>
     * <p>
     * format13:yyyyMMdd HH:mm:ss<br/>
     * format14:yyyyMMdd HH:mm<br/>
     * format15:yyyyMMdd HH<br/>
     * format16:yyyyMMdd
     * <p>
     * format17：yyyyMM<br/>
     * format18：yyyy/MM<br/>
     * format19：yyyy-MM<br/>
     * format20：yyyy年MM月
     */
    public final static Map<String, String> DateRegList = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("format1", "yyyy年MM月dd日  HH:mm:ss");
            put("format2", "yyyy年MM月dd日  HH:mm");
            put("format3", "yyyy年MM月dd日  HH");
            put("format4", "yyyy年MM月dd日");

            put("format5", "yyyy-MM-dd HH:mm:ss");
            put("format6", "yyyy-MM-dd HH:mm");
            put("format7", "yyyy-MM-dd HH");
            put("format8", "yyyy-MM-dd");

            put("format9", "yyyy/MM/dd HH:mm:ss");
            put("format10", "yyyy/MM/dd HH:mm");
            put("format11", "yyyy/MM/dd HH");
            put("format12", "yyyy/MM/dd");

            put("format13", "yyyyMMdd HH:mm:ss");
            put("format14", "yyyyMMdd HH:mm");
            put("format15", "yyyyMMdd HH");
            put("format16", "yyyyMMdd");

            put("format17", "yyyyMM");
            put("format18", "yyyy/MM");
            put("format19", "yyyy-MM");
            put("format20", "yyyy年MM月");
        }
    };

    /**
     * 日期校验
     *
     * @param dateStr
     * @return
     */
    public static Boolean isDate(String dateStr) {
        return dateStr.matches(DateReg);
    }

    /**
     * 将字符串按格式转换为Date<br/>
     * format1:yyyy年MM月dd日 HH:mm:ss<br/>
     * format2:yyyy年MM月dd日 HH:mm<br/>
     * format3:yyyy年MM月dd日 HH<br/>
     * format4:yyyy年MM月dd日<br/>
     * <p>
     * format5:yyyy-MM-dd HH:mm:ss<br/>
     * format6:yyyy-MM-dd HH:mm<br/>
     * format7:yyyy-MM-dd HH<br/>
     * format8:yyyy-MM-dd<br/>
     * <p>
     * format9:yyyy/MM/dd HH:mm:ss<br/>
     * format10:yyyy/MM/dd HH:mm<br/>
     * format11:yyyy/MM/dd HH<br/>
     * format12:yyyy/MM/dd<br/>
     * <p>
     * format13:yyyyMMdd HH:mm:ss<br/>
     * format14:yyyyMMdd HH:mm<br/>
     * format15:yyyyMMdd HH<br/>
     * format16:yyyyMMdd
     * <p>
     * format17：yyyyMM<br/>
     * format18：yyyy/MM<br/>
     * format19：yyyy-MM<br/>
     * format20：yyyy年MM月
     *
     * @param as_rq
     * @param as_lx
     * @return
     */
    public static Date getdate(String as_rq, String as_lx) {
        Date ldt_rq = null;
        SimpleDateFormat df = new SimpleDateFormat(DateRegList.get(as_lx));
        df.setLenient(false);// 严格控制输入
        try {
            ldt_rq = df.parse(as_rq);
        } catch (Exception e) {
            return null;
        }
        return ldt_rq;
    }

    /**
     * 无法判断时，传入时间字符串，若无法匹配，返回null
     *
     * @param dateStr
     * @return
     */
    public static Date getdate(String dateStr) {
        Date date = null;
        try {
            int Strlength = dateStr.length();

            if (dateStr.contains("-")) {
                /**
                 * 格式为yyyy-MM-dd +时
                 */
                switch (Strlength) {
                    case 7:
                        date = getdate(dateStr, "format19");
                        break;
                    case 10:
                        date = getdate(dateStr, "format8");
                        break;
                    case 13:
                        date = getdate(dateStr, "format7");
                        break;
                    case 16:
                        date = getdate(dateStr, "format6");
                        break;
                    case 19:
                        date = getdate(dateStr, "format5");
                        break;
                }
            } else if (dateStr.contains("/")) {
                /**
                 * 格式为yyyy/MM/dd+
                 */
                switch (Strlength) {
                    case 7:
                        date = getdate(dateStr, "format18");
                        break;
                    case 10:
                        date = getdate(dateStr, "format12");
                        break;
                    case 13:
                        date = getdate(dateStr, "format11");
                        break;
                    case 16:
                        date = getdate(dateStr, "format10");
                        break;
                    case 19:
                        date = getdate(dateStr, "format9");
                        break;
                }
            } else if (dateStr.contains("年")) {
                /**
                 * 格式为yyyy年MM月dd日+
                 */
                switch (Strlength) {
                    case 8:
                        date = getdate(dateStr, "format20");
                        break;
                    case 11:
                        date = getdate(dateStr, "format4");
                        break;
                    case 14:
                        date = getdate(dateStr, "format3");
                        break;
                    case 17:
                        date = getdate(dateStr, "format2");
                        break;
                    case 20:
                        date = getdate(dateStr, "format1");
                        break;
                }
            } else {
                /**
                 * 格式为yyyyMMdd时
                 */
                switch (Strlength) {
                    case 6:
                        date = getdate(dateStr, "format17");
                        break;
                    case 8:
                        date = getdate(dateStr, "format16");
                        break;
                    case 11:
                        date = getdate(dateStr, "format15");
                        break;
                    case 14:
                        date = getdate(dateStr, "format14");
                        break;
                    case 17:
                        date = getdate(dateStr, "format13");
                        break;
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return date;
    }

    /**
     * 将一个已知格式的String格式日期按指定格式输出，若不能转换则输出null<br/>
     * format1:yyyy年MM月dd日 HH:mm:ss<br/>
     * format2:yyyy年MM月dd日 HH:mm<br/>
     * format3:yyyy年MM月dd日 HH<br/>
     * format4:yyyy年MM月dd日<br/>
     * <p>
     * format5:yyyy-MM-dd HH:mm:ss<br/>
     * format6:yyyy-MM-dd HH:mm<br/>
     * format7:yyyy-MM-dd HH<br/>
     * format8:yyyy-MM-dd<br/>
     * <p>
     * format9:yyyy/MM/dd HH:mm:ss<br/>
     * format10:yyyy/MM/dd HH:mm<br/>
     * format11:yyyy/MM/dd HH<br/>
     * format12:yyyy/MM/dd<br/>
     * <p>
     * format13:yyyyMMdd HH:mm:ss<br/>
     * format14:yyyyMMdd HH:mm<br/>
     * format15:yyyyMMdd HH<br/>
     * format16:yyyyMMdd
     * <p>
     * format17：yyyyMM<br/>
     * format18：yyyy/MM<br/>
     * format19：yyyy-MM<br/>
     * format20：yyyy年MM月
     *
     * @param Date
     * @param front_fmt
     * @param target_fmt
     * @return
     */
    public static String ChangeDateFormat(String Date, String front_fmt, String target_fmt) {
        if (Date != null && !Date.equals("")) {
            SimpleDateFormat front_sdf = new SimpleDateFormat(DateRegList.get(front_fmt));
            SimpleDateFormat target_sdf = new SimpleDateFormat(DateRegList.get(target_fmt));
            try {
                return target_sdf.format(front_sdf.parse(Date));
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 将一个未知格式的String格式日期按指定格式输出，若不能转换则输出null<br/>
     * format1:yyyy年MM月dd日 HH:mm:ss<br/>
     * format2:yyyy年MM月dd日 HH:mm<br/>
     * format3:yyyy年MM月dd日 HH<br/>
     * format4:yyyy年MM月dd日<br/>
     * <p>
     * format5:yyyy-MM-dd HH:mm:ss<br/>
     * format6:yyyy-MM-dd HH:mm<br/>
     * format7:yyyy-MM-dd HH<br/>
     * format8:yyyy-MM-dd<br/>
     * <p>
     * format9:yyyy/MM/dd HH:mm:ss<br/>
     * format10:yyyy/MM/dd HH:mm<br/>
     * format11:yyyy/MM/dd HH<br/>
     * format12:yyyy/MM/dd<br/>
     * <p>
     * format13:yyyyMMdd HH:mm:ss<br/>
     * format14:yyyyMMdd HH:mm<br/>
     * format15:yyyyMMdd HH<br/>
     * format16:yyyyMMdd
     * <p>
     * format17：yyyyMM<br/>
     * format18：yyyy/MM<br/>
     * format19：yyyy-MM<br/>
     * format20：yyyy年MM月
     *
     * @param Date
     * @param fmt
     * @return
     */
    public static String ChangeDateFormat(String Date, String fmt) {
        if (Date != null && !Date.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat(DateRegList.get(fmt));
            return sdf.format(getdate(Date));
        } else {
            return null;
        }
    }

    /**
     * 将Date类型日期按指定格式输出<br/>
     * format1:yyyy年MM月dd日 HH:mm:ss<br/>
     * format2:yyyy年MM月dd日 HH:mm<br/>
     * format3:yyyy年MM月dd日 HH<br/>
     * format4:yyyy年MM月dd日<br/>
     * <p>
     * format5:yyyy-MM-dd HH:mm:ss<br/>
     * format6:yyyy-MM-dd HH:mm<br/>
     * format7:yyyy-MM-dd HH<br/>
     * format8:yyyy-MM-dd<br/>
     * <p>
     * format9:yyyy/MM/dd HH:mm:ss<br/>
     * format10:yyyy/MM/dd HH:mm<br/>
     * format11:yyyy/MM/dd HH<br/>
     * format12:yyyy/MM/dd<br/>
     * <p>
     * format13:yyyyMMdd HH:mm:ss<br/>
     * format14:yyyyMMdd HH:mm<br/>
     * format15:yyyyMMdd HH<br/>
     * format16:yyyyMMdd
     * <p>
     * format17：yyyyMM<br/>
     * format18：yyyy/MM<br/>
     * format19：yyyy-MM<br/>
     * format20：yyyy年MM月
     *
     * @param Date
     * @param fmt
     * @return
     */
    public static String ChangeDateFormat(Date Date, String fmt) {
        if (Date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DateRegList.get(fmt));
            sdf.setTimeZone(zone);
            return sdf.format(Date);
        } else {
            return null;
        }
    }

    /**
     * 获取某年某月的最后一天，返回yyyy-MM-dd
     * <p>
     * 将Date类型日期按指定格式输出<br/>
     * format1:yyyy年MM月dd日 HH:mm:ss<br/>
     * format2:yyyy年MM月dd日 HH:mm<br/>
     * format3:yyyy年MM月dd日 HH<br/>
     * format4:yyyy年MM月dd日<br/>
     * <p>
     * format5:yyyy-MM-dd HH:mm:ss<br/>
     * format6:yyyy-MM-dd HH:mm<br/>
     * format7:yyyy-MM-dd HH<br/>
     * format8:yyyy-MM-dd<br/>
     * <p>
     * format9:yyyy/MM/dd HH:mm:ss<br/>
     * format10:yyyy/MM/dd HH:mm<br/>
     * format11:yyyy/MM/dd HH<br/>
     * format12:yyyy/MM/dd<br/>
     * <p>
     * format13:yyyyMMdd HH:mm:ss<br/>
     * format14:yyyyMMdd HH:mm<br/>
     * format15:yyyyMMdd HH<br/>
     * format16:yyyyMMdd<br/>
     * <p>
     * format17：yyyyMM<br/>
     * format18：yyyy/MM<br/>
     * format19：yyyy-MM<br/>
     * format20：yyyy年MM月
     *
     * @return
     */
    public static String getLastDayOfMonth(String date, String fmt) {
        Calendar cal = Calendar.getInstance();
        Date rs = getdate(date, fmt);
        if (rs != null) {
            cal.setTime(rs);
            // 获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            // 设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            // 格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cal.getTime());
        }
        return "参数有误！";
    }
}
