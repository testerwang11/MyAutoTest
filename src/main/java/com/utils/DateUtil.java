package com.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtil {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSSS = "yyyyMMddHHmmSSS";

    public static String YYYY_MM_DD_HH_MM_SSS = "yyyy-MM-dd HH:mm:SSS";

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    public static Date getDate(int n) {
        Calendar calstart = Calendar.getInstance();
        calstart.setTime(new Date());
        calstart.add(Calendar.DAY_OF_WEEK, n);
        return calstart.getTime();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SSS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 当前时间加减
     *
     * @param amount
     * @return
     */
    public static String showDatePlus(int amount) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, amount);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(c.getTime());
        return date;
    }

    /**
     * 获取和当前时间差n小时的时间
     *
     * @param n
     * @return now+n小时的时间（n=1为一小时后，n=-1为一小时前）
     */
    public static String getBeforeOrNextHour(int n) {
        String strSysTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, n);
        strSysTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        return strSysTime;
    }

    /**
     * 获取当月天数
     *
     * @param monthofYear 2017-10
     * @return
     */
    public static int getLastDayInMonth(String monthofYear) {
        String year = monthofYear.split("\\-")[0];
        String month = monthofYear.split("\\-")[1];
        int m = Integer.parseInt(month);
        int[] has31days = {1, 3, 5, 7, 8, 10, 12};
        int[] has30days = {4, 6, 9, 11};
        int[] has28days = {2};
        for (int i : has31days) {
            if (m == i) {
                return 31;
            }
        }
        for (int i : has30days) {
            if (m == i) {
                return 30;
            }
        }
        for (int i : has28days) {
            if (m == i) {
                if (isLeapYear(year)) {
                    return 29;
                }
                return 28;
            }
        }
        return 0;
    }

    /**
     * 判断是否为闰年
     *
     * @param y
     * @return
     */
    public static boolean isLeapYear(String y) {
        int year = Integer.parseInt(y);
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 == 0 && year % 400 != 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String formatDate(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 获取时间差
     *
     * @param date
     * @param date2
     * @return 时间差， date-date2,单位毫秒
     */
    public static long diffDay(Date date, Date date2) {
        return Long.valueOf((date.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24 * 60 * 60 * 1000));
    }

    /**
     * 获取时间差
     *
     * @param date
     * @param date2
     * @return 时间差， date-date2,单位毫秒
     */
    public static long diffDay(String date, String date2) {
        try {
            long s1 = new SimpleDateFormat(YYYYMMDDHHMMSSS).parse(date).getTime();
            long s2 = new SimpleDateFormat(YYYYMMDDHHMMSSS).parse(date2).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            return Long.valueOf((new SimpleDateFormat(YYYYMMDDHHMMSSS).parse(date).getTime() - new SimpleDateFormat(YYYYMMDDHHMMSSS).parse(date2).getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间加减(正为加，负为减)
     *
     * @param date
     * @param i
     * @return
     * @throws ParseException
     */
    public static String addDate(String date, int i) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();

        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(d);
        cl.add(Calendar.DAY_OF_YEAR, i);
        d = cl.getTime();
        return df.format(d);
    }

    public static void main(String[] args) {
        System.out.println(diffDay("201707191430000", "201707191430200"));
    }
}
