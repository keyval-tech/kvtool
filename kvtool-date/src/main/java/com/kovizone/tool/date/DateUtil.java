package com.kovizone.tool.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class DateUtil {

    private final static Map<String, DateFormat> DATE_FORMAT_MAP = new HashMap<>();

    static {
        DATE_FORMAT_MAP.put("yyyyMMdd HHmmss SSS", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
        DATE_FORMAT_MAP.put("yyyyMMddHHmmssSSS", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
        DATE_FORMAT_MAP.put("yyyyMMdd HHmmss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
        DATE_FORMAT_MAP.put("yyyyMMddHHmmss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
        DATE_FORMAT_MAP.put("yyyy-MM-dd HH:mm:ss SSS", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
        DATE_FORMAT_MAP.put("yyyy-MM-dd HH:mm:ss.SSS", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        DATE_FORMAT_MAP.put("yyyy-MM-dd HH:mm:ss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        DATE_FORMAT_MAP.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));
        DATE_FORMAT_MAP.put("HH:mm:ss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        DATE_FORMAT_MAP.put("HH:mm:ss.SSS", new SimpleDateFormat("HH:mm:ss.SSS"));
        DATE_FORMAT_MAP.put("HH:mm:ss SSS", new SimpleDateFormat("HH:mm:ss SSS"));
    }

    /**
     * 获取DateFormat
     *
     * @param format 时间格式
     * @return 时间格式化对象
     */
    public static DateFormat getDateFormat(String format) {
        DateFormat dateFormat = DATE_FORMAT_MAP.get(format);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(format);
            DATE_FORMAT_MAP.put(format, dateFormat);
        }
        return dateFormat;
    }

    /**
     * 格式化当前时间为字符串
     *
     * @param format 日期格式化
     * @return 时间字符串
     */
    public static String formatDate(String format) {
        return formatDate(new Date(), format);
    }

    /**
     * 格式化日期为字符串
     *
     * @param date   日期时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        return getDateFormat(format).format(date);
    }

    public static Date parse(String dateString) {
        Collection<DateFormat> dateFormats = DATE_FORMAT_MAP.values();
        for (DateFormat dateFormat : dateFormats) {
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException ignored) {
            }
        }
        return null;
    }

    /**
     * 格式化字符串为日期
     *
     * @param dateString 时间字符串
     * @param format     时间格式
     * @return 时间对象
     */
    public static Date parse(String dateString, String format) {
        try {
            return getDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
