package com.kovizone.tool.core;

import com.kovizone.tool.core.constant.CharConstant;

import java.util.List;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class StringUtil {

    /**
     * 判断空
     *
     * @param arg 对象
     * @return 是否空
     */
    public static boolean isEmpty(CharSequence arg) {
        return arg == null || "".equals(arg.toString().trim());
    }

    /**
     * 判断非空
     *
     * @param arg 对象
     * @return 是否非空
     */
    public static boolean isNotEmpty(String arg) {
        return !isEmpty(arg);
    }

    /**
     * 限制字符串长度
     *
     * @param s      原文
     * @param length 限制长度
     * @return 限制长度，超过的部分隐藏
     */
    public static String limitLength(String s, int length) {
        return limitLength(s, length, "…");
    }

    /**
     * 限制字符串长度
     *
     * @param s      原文
     * @param length 限制长度
     * @param suffix 受到限制时的后缀
     * @return 限制长度，超过的部分隐藏
     */
    public static String limitLength(String s, int length, String suffix) {
        if (s == null) {
            return null;
        }
        String str = s.trim();
        if (length <= -999) {
            return str;
        }
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - suffix.length()) + suffix;
    }

    /**
     * 首字母大写
     *
     * @param arg 字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstCase(String arg) {
        if (isEmpty(arg)) {
            return arg;
        }
        char[] cs = arg.toCharArray();
        if (cs[0] >= CharConstant.FIRST_LOWER_LETTER && cs[0] <= CharConstant.LAST_LOWER_LETTER) {
            cs[0] -= (CharConstant.FIRST_LOWER_LETTER - CharConstant.FIRST_UPPER_LETTER);
        }
        return String.valueOf(cs);
    }

    /**
     * 首字母小写
     *
     * @param arg 字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstCase(String arg) {
        if (isEmpty(arg)) {
            return arg;
        }
        char[] chars = arg.toCharArray();
        if (chars[0] >= CharConstant.FIRST_UPPER_LETTER && chars[0] <= CharConstant.LAST_UPPER_LETTER) {
            chars[0] += (CharConstant.FIRST_LOWER_LETTER - CharConstant.FIRST_UPPER_LETTER);
        }
        return String.valueOf(chars);
    }

    /**
     * 从字符串中选出符合正则表达式的子串
     *
     * @param arg   字符串
     * @param regex 正则表达式
     * @return 子串集
     * @see RegexUtil#pick(String, String)
     */
    public static List<String> pick(String arg, String regex) {
        return RegexUtil.pick(arg, regex);
    }

    /**
     * arg中是否含有ss中的某个元素
     *
     * @param arg 字符串
     * @param ss  子串集
     * @return 是否含有
     */
    public static boolean contains(String arg, CharSequence... ss) {
        if (ss != null && isNotEmpty(arg)) {
            for (CharSequence s : ss) {
                if (arg.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

}
