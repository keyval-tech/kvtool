package com.kovizone.tool.core;

import com.kovizone.tool.core.StringUtil;

import java.util.Arrays;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ArrayUtil {


    /**
     * 数组叠加
     *
     * @param original 原数组
     * @param append   叠加数组
     * @param <T>      泛型
     * @return 新数组
     */
    public static <T> T[] addAll(T[] original, T[] append) {
        if (original == null) {
            return append;
        }
        if (append == null) {
            return original;
        }
        T[] fullArray = Arrays.copyOf(original, original.length + append.length);

        int i = original.length;
        for (T t : append) {
            fullArray[i++] = t;
        }
        return fullArray;
    }

    /**
     * 去掉数组中的空项
     *
     * @param original 数组
     * @param <T>      泛型
     * @return 新数组
     */
    public static <T> T[] trim(T[] original) {
        if (original == null) {
            return null;
        }
        int nullSize = 0;
        for (int i = 0; i < original.length; i++) {
            if (original[i] == null) {
                if (i < original.length - 1) {
                    for (int j = i + 1; j < original.length; j++) {
                        if (original[j] != null) {
                            original[i] = original[j];
                            original[j] = null;
                            break;
                        }
                    }
                    if (original[i] == null) {
                        nullSize++;
                    }
                } else {
                    nullSize++;
                }
            }
        }
        return Arrays.copyOf(original, original.length - nullSize);
    }

    /**
     * 转为字符串
     *
     * @param arg 数组
     * @return 字符串
     */
    public static String toString(Object[] arg) {
        return toString(arg, null, null, null);
    }

    /**
     * 转为字符串
     *
     * @param arg   数组
     * @param split 分隔符
     * @return 字符串
     */
    public static String toString(Object[] arg, String split) {
        return toString(arg, null, null, split);
    }

    /**
     * 转为字符串
     *
     * @param arg    数组
     * @param prefix 节点前缀
     * @param suffix 节点后缀
     * @param split  分隔符
     * @return 字符串
     */
    public static String toString(Object[] arg, String prefix, String suffix, String split) {
        if (arg == null) {
            return null;
        }
        if (split == null) {
            split = ",";
        }
        if (prefix == null) {
            prefix = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arg.length; i++) {
            Object o = arg[i];
            if (i > 0) {
                stringBuilder.append(split);
            }
            if (StringUtil.isNotEmpty(prefix)) {
                stringBuilder.append(prefix);
            }
            stringBuilder.append(o);
            if (StringUtil.isNotEmpty(suffix)) {
                stringBuilder.append(suffix);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * arg中是否含有s
     *
     * @param arg 数组
     * @param s   节点
     * @param <T> 泛型
     * @return 是否含有
     */
    public static <T> boolean contains(T[] arg, T s) {
        if (arg != null && s != null) {
            for (T o : arg) {
                if (o.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }
}
