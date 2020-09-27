package com.kovizone.tool.reflex;

import com.kovizone.tool.core.function.ForEachBigFunction;
import com.kovizone.tool.core.function.ForEachInxBigFunction;
import com.kovizone.tool.core.function.SerialFunction;

import java.util.*;

/**
 * 反射工具
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class CollectionUtil extends com.kovizone.tool.core.CollectionUtil {

    /**
     * 读取值集合
     *
     * @param collection 集合
     * @param func       属性
     * @param <T>        集合泛型
     * @param <R>        值泛型
     * @return 值集合
     */
    public static <T, R> Collection<R> getValueList(Collection<T> collection, SerialFunction<T, R> func) {
        if (isEmpty(collection)) {
            return null;
        }
        List<R> fieldValueList = new ArrayList<>(collection.size());
        String fieldName = FieldUtil.getFieldName(func);
        collection.forEach(action -> {
            fieldValueList.add((R) BeanUtil.getValue(action, fieldName));
        });
        return fieldValueList;
    }

    /**
     * 转为Map
     *
     * @param list    集合
     * @param keyFunc 转为Key的属性
     * @param <T>     集合泛型
     * @param <K>     转为Key的属性泛型
     * @return 转换后的Map
     */
    public static <T, K> Map<K, T> toMapByKey(Collection<T> list, SerialFunction<T, K> keyFunc) {
        if (list == null) {
            return null;
        }
        Map<K, T> map = new HashMap<>(list.size());
        String fieldName = FieldUtil.getFieldName(keyFunc);
        list.forEach(action -> {
            map.put((K) BeanUtil.getValue(action, fieldName), action);
        });
        return map;
    }

    /**
     * 转为Map
     *
     * @param list      集合
     * @param keyFunc   转为Key的属性
     * @param valueFunc 转为Value的属性
     * @param <T>       集合泛型
     * @param <K>       转为Key的属性泛型
     * @param <V>       转为Value的属性泛型
     * @return 转换后的Map
     */
    public static <T, K, V> Map<K, V> toMap(Collection<T> list, SerialFunction<T, K> keyFunc, SerialFunction<T, V> valueFunc) {
        if (list == null) {
            return null;
        }
        Map<K, V> map = new HashMap<>(list.size());
        String keyName = FieldUtil.getFieldName(keyFunc);
        String valueName = FieldUtil.getFieldName(valueFunc);
        list.forEach(action -> {
            map.put((K) BeanUtil.getValue(action, keyName), (V) BeanUtil.getValue(action, valueName));
        });
        return map;
    }

    /**
     * 转为Map，适用于作为Key的属性存在多个Value
     *
     * @param list      集合
     * @param keyFunc   转为Key的属性
     * @param valueFunc 转为Value List的属性
     * @param <T>       集合泛型
     * @param <K>       转为Key的属性泛型
     * @param <V>       转为Value List的属性泛型
     * @return 转换后的Map
     */
    public static <T, K, V> Map<K, List<V>> toMapList(Collection<T> list, SerialFunction<T, K> keyFunc, SerialFunction<T, V> valueFunc) {
        if (list == null) {
            return null;
        }
        Map<K, List<V>> map = new HashMap<>(list.size());
        String keyName = FieldUtil.getFieldName(keyFunc);
        String valueName = FieldUtil.getFieldName(valueFunc);
        list.forEach(action -> {
            K key = (K) BeanUtil.getValue(action, keyName);
            List<V> values = map.get(key);
            if (values == null) {
                values = new ArrayList<>();
            }
            values.add((V) BeanUtil.getValue(action, valueName));
            map.put(key, values);
        });
        return map;
    }

    /**
     * 双集合遍历
     *
     * @param aList    A集合
     * @param bList    B集合
     * @param function A元素B元素处理，输入A元素、B元素
     * @param <A>      A泛型
     * @param <B>      B泛型
     */
    public static <A, B> void forEach(Collection<A> aList, Collection<B> bList, ForEachBigFunction<A, B> function) {
        if (isNotEmpty(aList) && isNotEmpty(bList)) {
            outer:
            for (A a : aList) {
                for (B b : bList) {
                    if (!function.execute(a, b)) {
                        break outer;
                    }
                }
            }
        }
    }

    /**
     * 双集合遍历，输入索引
     *
     * @param aList    A集合
     * @param bList    B集合
     * @param function A元素B元素处理，输入A元素、A元素索引、B元素、B元素索引
     * @param <A>      A泛型
     * @param <B>      B泛型
     */
    public static <A, B> void forEachInx(Collection<A> aList, Collection<B> bList, ForEachInxBigFunction<A, B> function) {
        if (isNotEmpty(aList) && isNotEmpty(bList)) {
            int i = 0;
            outer:
            for (A a : aList) {
                int j = 0;
                for (B b : bList) {
                    if (!function.execute(a, i, b, j)) {
                        break outer;
                    }
                    j++;
                }
                i++;
            }
        }
    }

}
