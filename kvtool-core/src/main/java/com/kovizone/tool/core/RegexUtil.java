package com.kovizone.tool.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class RegexUtil {

    /**
     * 从字符串中选出符合正则表达式的子串
     *
     * @param arg   字符串
     * @param regex 正则表达式
     * @return 子串集
     * @see StringUtil#pick(String, String)
     */
    public static List<String> pick(String arg, String regex) {
        Pattern r = Pattern.compile(regex);
        List<String> ss = new ArrayList<>();
        Matcher matcher = r.matcher(arg);
        while (matcher.find()) {
            String group = matcher.group();
            if (StringUtil.isNotEmpty(group)) {
                ss.add(group);
            }
        }
        return ss;
    }
}
