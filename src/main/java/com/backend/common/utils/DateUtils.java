package com.backend.common.utils;

import io.sentry.Sentry;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 *
 * @author
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static SimpleDateFormat YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat YYYY_MM_DD_HH_MM_SS_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取当前日期加减day, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDateSubDays(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        return parseDateToStr(YYYY_MM_DD, calendar.getTime());
    }

//    public static void main(String[] args) {
//        System.out.println(getDate());
//    }

    /**
     * 获取当前日期, 默认格式为YYYY_MM_DD_HH_MM_SS
     *
     * @return String
     */
    public static String getDate1() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
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
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            Sentry.captureException(e);
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取毫秒时间戳
     *
     * @return
     * @throws Exception
     */
    public static long getTimeStamp() {
        String format = YYYY_MM_DD_HH_MM_SS_SSS.format(new Date());
        Date date = null;
        try {
            date = YYYY_MM_DD_HH_MM_SS_SSS.parse(format);
        } catch (ParseException e) {
            Sentry.captureException(e);
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 时间戳转换时间
     * @param timeStamp 时间戳
     * @return String date
     */
    public static String getSSSDateByTimeStamp(Long timeStamp) {
        return YYYY_MM_DD_HH_MM_SS_SSS.format(new Date(timeStamp));
    }

    /**
     * 时间戳转换时间
     * @param timeStamp 时间戳
     * @return String date
     */
    public static String getSSDateByTimeStamp(Long timeStamp) {
        return YYYYMMDD_HHMMSS.format(new Date(timeStamp));
    }

    /**
     * 获取时间戳
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static long getSSSTimeStamp(String dateStr) throws Exception {
        Date date = YYYY_MM_DD_HH_MM_SS_SSS.parse(dateStr + ".000");
        return date.getTime();
    }

    /**
     * 获取时间戳
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static long getSSTimeStamp(String dateStr) throws Exception {
        Date date = YYYYMMDD_HHMMSS.parse(dateStr);
        return date.getTime();
    }

    /**
     * 获取当前时间n天前x点y分z秒的时间戳
     * @param day,hours,minute,seconds
     * @return
     * @throws Exception
     */
    public static long getOnePointTimeStamp(int day, int hours, int minute, int seconds) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime().getTime();
    }
    public static long getOnePointTimeStamp(int day, int hours, int minute, int seconds, int millsSecond) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, seconds);
        return calendar.getTime().getTime();
    }

    /**
     * 获取当前时间x小时前的时间戳
     * @param hours
     * @return
     * @throws Exception
     */
    public static long getOnePointTimeStamp(int hours) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 获取当前时间x小时前的时间
     * @param hours
     * @return
     * @throws Exception
     */
    public static String getOnePointTime(int hours) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, calendar.getTime());
    }

    /**
     * 获取前n个月前路径，格式如2020-08
     * @param n
     * @return
     * @throws Exception
     */
    public static String getSomeMonthAgoPath(int n) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, n);
        Date m = calendar.getTime();
        String someMonthAgoPath = DateFormatUtils.format(m, "yyyy-MM-dd");
        return someMonthAgoPath.substring(0,someMonthAgoPath.length()-3);
    }

    /**
     * 根据参数时间加指定天数的时间
     * 时间格式：yyyy-MM-dd
     * @param baseDate
     * @param addDays
     * @return
     */
    public static String addDaysYYYYMMDD(String baseDate, int addDays) {
        Date date = parseDate(baseDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,addDays);
        date = calendar.getTime();
        return parseDateToStr(YYYY_MM_DD, date);
    }

    /**
     * 根据参数时间加指定小时的时间
     * @param baseDate
     * @param addHours
     * @return
     */
    public static String addHours(String baseDate, int addHours) {
        Date date = parseDate(baseDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, addHours);
        date = calendar.getTime();
        return parseDateToStr(YYYY_MM_DD_HH_MM, date);
    }

    /**
     * 根据参数时间加指定小时的时间
     * @param baseDate
     * @param addHours
     * @return
     */
    public static Long addHours1(String baseDate, int addHours) {
        Date date = parseDate(baseDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, addHours);
        date = calendar.getTime();
        return date.getTime();
    }

    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 改造hg比赛时间
     * 08-06 11:00a
     * 07-04 03:15p
     * 改造格式， + 12H
     * @return
     */
    public static String transformDateHg(String dateTime) {
        String year = getCurrentYear();
        int flag = dateTime.indexOf("p");
        if (flag < 0) { // 上午时间
            String date = year + "-" + dateTime.replace("a", "");
            date = addHours(date, 12);
            return date;
        } else { // 下午时间，03:15p = 15:15
            String date = year + "-" + dateTime.replace("p", "");
            date = addHours(date, 24);
            return date;
        }
    }

    /**
     * 获取hg比赛时间戳
     * 08-06 11:00a
     * 07-04 03:15p
     * 改造格式， + 12H
     * @return
     */
    public static Long getLeagueDate(String dateTime) {
        String year = getCurrentYear();
        int flag = dateTime.indexOf("p");
        if (flag < 0) { // 上午时间
            dateTime = year + "-" + dateTime.replace("a", "");
            Long date = addHours1(dateTime, 12);
            return date;
        } else { // 下午时间，03:15p = 15:15
            dateTime = year + "-" + dateTime.replace("p", "");
            Long date = addHours1(dateTime, 24);
            return date;
        }
    }
    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {

        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static void main(String[] args) {
        /*try {
            String onePointTime = getOnePointTime(-1);
            System.out.println(onePointTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
//        String s = transformDateHg("07-04 11:00a");
//        String s = transformDateHg("07-04 1:00p");
//        Long s = getLeagueDate("07-04 11:00a");
//        String weekOfDate = getWeekOfDate(new Date());
//        System.out.println(weekOfDate);
        int currentHour = getCurrentHour();
        System.out.println(currentHour);
        String dateSubDays = getDateSubDays(-1);
        System.out.println(dateSubDays);
    }

}
