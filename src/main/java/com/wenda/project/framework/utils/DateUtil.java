package com.wenda.project.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @author ycl
 */
public class DateUtil {
    public static final SimpleDateFormat FORMAT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat FORMAT_DATE_ONLY = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FORMAT_TIME_ONLY = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat FORMAT_WORD_DATE_ONLY = new SimpleDateFormat("yyyy年MM月dd日");

    public static Date parse(String dateStr) throws ParseException {
        return FORMAT_DEFAULT.parse(dateStr);
    }

    public static Date parse(String dateStr, SimpleDateFormat format) throws ParseException {
        if (format == null) {
            format = FORMAT_DEFAULT;
        }
        return format.parse(dateStr);
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DEFAULT.format(date);
    }


    public static String format(Date date, SimpleDateFormat format) {
        if (date == null || format == null) {
            return null;
        }
        return format.format(date);
    }

    public static String format(Date date, String format) {
        if (date == null || format == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    /**
     * 获取截止日期
     *
     * @param startTime
     * @param timeNum
     * @return
     */
    public static Date getAfterYear(Date startTime, Integer timeNum) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.YEAR, timeNum);// 把日期往后增加几年.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 获取之后的秒
     *
     * @param startTime
     * @param timeNum
     * @return
     */
    public static Date getNextSecond(Date startTime, Integer timeNum) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.SECOND, timeNum);// 把日期往后增加1s.整数往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 获取某个日起是星期几，返回 1 - 7 星期1就是1，星期天就是7
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int d = calendar.get(Calendar.DAY_OF_WEEK);
        //星期六用7表示，老外的星期天用1表示，星期一用2表示
        if (d == 1) {
            return 7;
        } else {
            return d - 1;
        }
    }

    /**
     * date1 - date2 相差天数
     *
     * @param date1 null  表示当前时间
     * @param date2 null  表示当前时间
     * @return
     */
    public static int getDiffDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        if (date1 != null) {
            calendar1.setTime(date1);
        }
        Calendar calendar2 = Calendar.getInstance();
        if (date2 != null) {
            calendar2.setTime(date2);
        }
        return calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(calendar2.DAY_OF_YEAR);
    }

    /**
     * 根据年月日时分秒创建一个时间
     * 年月日，时分秒只要一个为空，就用当前时间替代
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date createTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        Calendar calendar = Calendar.getInstance();
        if (year != null) {
            calendar.set(Calendar.YEAR, year);
        }

        if (month != null) {
            //月份从0开始算的
            calendar.set(Calendar.MONTH, month + 1);
        }

        if (day != null) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }

        if (hour != null) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }

        if (minute != null) {
            calendar.set(Calendar.MINUTE, minute);
        }

        if (second != null) {
            calendar.set(Calendar.SECOND, second);
        }
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date date = createTime(1996, 4, 1, 21, 12, 32);
        System.out.println(format(date));

        Date date1 = new Date(date.getTime() + 28000);
        System.out.println(format(date1));
    }
}
