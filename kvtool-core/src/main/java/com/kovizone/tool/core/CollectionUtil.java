package com.kovizone.tool.core;

import com.kovizone.tool.core.function.ForEachFunction;
import com.kovizone.tool.core.function.ForEachInxFunction;

import java.util.*;
import java.util.function.Function;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class CollectionUtil {

    /**
     * 判断空
     *
     * @param collection 集合
     * @return 是否空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断非空
     *
     * @param collection 集合
     * @return 是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 遍历，输入元素
     *
     * @param collection 集合
     * @param action     方法（输入元素）
     * @param <T>        泛型
     */
    public static <T> void forEach(Collection<T> collection, ForEachFunction<T> action) {
        if (collection != null) {
            for (T t : collection) {
                if (!action.execute(t)) {
                    break;
                }
            }
        }
    }

    /**
     * 遍历，输入元素和索引
     *
     * @param collection 集合
     * @param action     方法（输入元素和索引）
     * @param <T>        泛型
     */
    public static <T> void forEachInx(Collection<T> collection, ForEachInxFunction<T> action) {
        if (collection != null && !collection.isEmpty()) {
            int i = 0;
            for (T t : collection) {
                if (!action.execute(t, i++)) {
                    break;
                }
            }
        }
    }

    /**
     * 转换
     *
     * @param list      被转换对象集
     * @param converter 转换方法
     * @param <T>       被转换对象泛型
     * @param <R>       转换对象泛型
     * @return 转换对象集合
     */
    public static <T, R> List<R> conversion(List<T> list, Function<T, R> converter) {
        if (list == null) {
            return null;
        }
        List<R> newList = new ArrayList<>();
        list.forEach(t -> {
            newList.add(converter.apply(t));
        });
        return newList;
    }

    /**
     * 删除重复元素
     *
     * @param list 集合
     * @param <T>  泛型
     * @return 数组
     */
    public static <T> List<T> deduplication(List<T> list) {
        HashSet<T> h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 转为字符串
     *
     * @param collection 集合
     * @param <T>        泛型
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection) {
        return toString(collection, "", "", ",", null);
    }

    /**
     * 转为字符串
     *
     * @param collection 集合
     * @param converter  转换方法
     * @param <T>        泛型
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection, Function<T, String> converter) {
        return toString(collection, "", "", ",", converter);
    }


    /**
     * 转为字符串
     *
     * @param collection 集合
     * @param split      分隔符
     * @param <T>        泛型
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection, String split) {
        return toString(collection, "", "", split, null);
    }

    /**
     * 转为字符串
     *
     * @param collection 集合
     * @param split      分隔符
     * @param converter  转换方法
     * @param <T>        泛型
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection, String split, Function<T, String> converter) {
        return toString(collection, "", "", split, converter);
    }

    /**
     * 转为字符串
     *
     * @param collection 集合
     * @param prefix     元素前缀
     * @param suffix     元素后缀
     * @param split      分隔符
     * @param <T>        泛型
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection, String prefix, String suffix, String split) {
        return toString(collection, prefix, suffix, split, null);
    }

    /**
     * 转为字符串
     *
     * @param collection 集合
     * @param prefix     元素前缀
     * @param suffix     元素后缀
     * @param split      分隔符
     * @param converter  转换方法
     * @param <T>        泛型
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection, String prefix, String suffix, String split, Function<T, String> converter) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        if (prefix == null) {
            prefix = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        if (split == null) {
            split = ",";
        }
        StringBuilder text = new StringBuilder();
        String finalSplit = split;
        String finalPrefix = prefix;
        String finalSuffix = suffix;
        forEachInx(collection, (action, i) -> {
            if (i != 0) {
                text.append(finalSplit);
            }
            if (!StringUtil.isEmpty(finalPrefix)) {
                text.append(finalPrefix);
            }
            if (converter != null) {
                text.append(converter.apply(action));
            } else {
                text.append(action);
            }
            if (!StringUtil.isEmpty(finalSuffix)) {
                text.append(finalSuffix);
            }
            return true;
        });
        return text.toString();
    }
}
