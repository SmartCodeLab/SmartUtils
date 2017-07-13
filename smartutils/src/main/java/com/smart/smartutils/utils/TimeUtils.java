package com.smart.smartutils.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fengjh on 2016/1/29.
 */
public class TimeUtils {


    private static final long ONE_MINUTE = 60L;
    private static final long ONE_HOUR = 60 * 60L;
    private static final long ONE_DAY = 24 * 60 * 60L;
    private static final long ONE_WEEK = 7 * 24 * 60 * 60L;
    private static final long ONE_MONTH = 4 * 7 * 24 * 60 * 60L;
    private static final long ONE_YEAR = 12 * 4 * 7 * 24 * 60 * 60L;

    private static final String ONE_JUST = "刚刚";
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_WEEK_AGO = "周前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";


    //检查输入的日期格式是否正确
    public static boolean checkDate(String date) {
        String DatePattern = "^(?:([0-9]{4}-(?:(?:0?[1,3-9]|1[0-2])-(?:29|30)|"
                + "((?:0?[13578]|1[02])-31)))|"
                + "([0-9]{4}-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1\\d|2[0-8]))|"
                + "(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|"
                + "(?:0[48]00|[2468][048]00|[13579][26]00))-0?2-29)))$";
        Pattern p = Pattern.compile(DatePattern);
        Matcher m = p.matcher(date);
        boolean b = m.matches();
        return b;
    }

    public static String getTime(long millisconds) {
        long timeInterval = System.currentTimeMillis() / 1000L - millisconds;
        long mill = timeInterval;//秒前
        long minute = (timeInterval / ONE_MINUTE);//分钟前
        long hour = (timeInterval / ONE_HOUR);//小时前
        long day = (timeInterval / ONE_DAY);//天前
        long week = (timeInterval / ONE_WEEK);//周前
        long month = (timeInterval / ONE_MONTH);//月前
        long year = (timeInterval / ONE_YEAR);//年前

        if (year > 0) {
            return year + ONE_YEAR_AGO;
        } else if (month > 0) {
            return month + ONE_MONTH_AGO;
        } else if (week > 0) {
            return week + ONE_WEEK_AGO;
        } else if (day > 0) {
            return day + ONE_DAY_AGO;
        } else if (hour > 0) {
            return hour + ONE_HOUR_AGO;
        } else if (minute > 0) {
            return minute + ONE_MINUTE_AGO;
        } else if (mill < 15) {
            return ONE_JUST;
        } else {
            return mill + ONE_SECOND_AGO;
        }
    }

    /**
     * @param millisconds
     * @param thisYearPattern "MM月dd日","MM-dd"
     * @param lastYearPattern "yyyy年MM月dd日","yyyy-MM-dd"
     * @return
     */
    public static String getFormatTime(long millisconds, String thisYearPattern, String lastYearPattern) {
        long timeInterval = System.currentTimeMillis() / 1000L - millisconds;
        long mill = timeInterval;//秒前
        long minute = (timeInterval / ONE_MINUTE);//分钟前
        long hour = (timeInterval / ONE_HOUR);//小时前
        long day = (timeInterval / ONE_DAY);//天前
        long week = (timeInterval / ONE_WEEK);//周前
        long month = (timeInterval / ONE_MONTH);//月前
        long year = (timeInterval / ONE_YEAR);//年前

        if (hour >= 24) {
            //获取当年
            int thisYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
            //获取往年
            int publishYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(millisconds * 1000)));
            LogUtils.i("TimeUtils", "getFormatTime=thisYear=" + thisYear);
            LogUtils.i("TimeUtils", "getFormatTime=publishYear=" + publishYear);
            if (publishYear >= thisYear) {
                //MM-dd
                SimpleDateFormat timeFormat = new SimpleDateFormat(thisYearPattern);
                Date date = new Date(millisconds * 1000);
                return timeFormat.format(date);
            } else {
                //yyyy-MM-dd
                SimpleDateFormat timeFormat = new SimpleDateFormat(lastYearPattern);
                Date date = new Date(millisconds * 1000);
                return timeFormat.format(date);
            }
            //return hour + ONE_HOUR_AGO;
        } else if (hour > 0) {
            return hour + ONE_HOUR_AGO;
        } else if (minute > 0) {
            return minute + ONE_MINUTE_AGO;
        } else if (mill < 15) {
            return ONE_JUST;
        } else {
            return mill + ONE_SECOND_AGO;
        }
    }

    public static String getFormatActiveTime(long millisconds, String thisPattern) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(thisPattern);
        Date date = new Date(millisconds * 1000);
        return timeFormat.format(date);
    }

    public static boolean canBuy(long start, long end) {
        String startTime = getFormatActiveTime(start, "yyyy.MM.dd") + ".00.00.00";
        String endTime = getFormatActiveTime(end, "yyyy.MM.dd") + ".23.59.59";
        long startStamp = date2TimeStamp(startTime, "yyyy.MM.dd.HH.mm.ss");
        long endStamp = date2TimeStamp(endTime, "yyyy.MM.dd.hh.mm.ss");
        long timeInterval = System.currentTimeMillis() / 1000L;
        if (timeInterval >= startStamp && timeInterval <= endStamp) {
            return true;
        }
        return false;
    }

    public static long date2TimeStamp(String data, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            long stamp = sdf.parse(data).getTime() / 1000;
            return stamp;
        } catch (Exception e) {

        }
        return 0L;
    }
}
