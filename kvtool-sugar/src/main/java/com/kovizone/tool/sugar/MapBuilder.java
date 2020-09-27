package com.kovizone.tool.sugar;

import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * Map构造器
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class MapBuilder {

    /**
     * 生成Map构造辅助器
     *
     * @param key   Key实例
     * @param value Value实例
     * @param <K>   Key泛型
     * @param <V>   Value泛型
     * @return Map构造辅助器
     */
    public static <K, V> MapBuildHelper<K, V> put(K key, V value) {
        return put(true, key, value);
    }

    /**
     * 生成Map构造辅助器
     *
     * @param condition 若为false，key和value将不会被添加
     * @param key       Key实例
     * @param value     Value实例
     * @param <K>       Key泛型
     * @param <V>       Value泛型
     * @return Map构造辅助器
     */
    public static <K, V> MapBuildHelper<K, V> put(BooleanSupplier condition, K key, V value) {
        if (condition.getAsBoolean()) {
            return new MapBuildHelper<>(key, value);
        } else {
            return new MapBuildHelper<>();
        }
    }

    /**
     * 生成Map构造辅助器
     *
     * @param condition 若为false，key和value将不会被添加
     * @param key       Key实例
     * @param value     Value实例
     * @param <K>       Key泛型
     * @param <V>       Value泛型
     * @return Map构造辅助器
     */
    public static <K, V> MapBuildHelper<K, V> put(boolean condition, K key, V value) {
        if (condition) {
            return new MapBuildHelper<>(key, value);
        } else {
            return new MapBuildHelper<>();
        }
    }

    public static class MapBuildHelper<K, V> {

        private List<Node<K, V>> nodeList;

        protected MapBuildHelper() {
            super();
        }

        protected MapBuildHelper(K key, V value) {
            super();
            nodeList = new ArrayList<>();
            nodeList.add(new Node<K, V>(key, value));
        }

        /**
         * 添加元素
         *
         * @param key   Key实例
         * @param value Value实例
         * @return Map构造辅助器
         */
        public MapBuildHelper<K, V> put(K key, V value) {
            return put(true, key, value);
        }

        /**
         * 添加元素
         *
         * @param condition 若为false，key和value将不会被添加
         * @param key       Key实例
         * @param value     Value实例
         * @return Map构造辅助器
         */
        public MapBuildHelper<K, V> put(boolean condition, K key, V value) {
            if (condition) {
                nodeList.add(new Node<K, V>(key, value));
            }
            return this;
        }

        /**
         * 添加元素
         *
         * @param condition 若为false，key和value将不会被添加
         * @param key       Key实例
         * @param value     Value实例
         * @return Map构造辅助器
         */
        public MapBuildHelper<K, V> put(BooleanSupplier condition, K key, V value) {
            if (condition.getAsBoolean()) {
                nodeList.add(new Node<K, V>(key, value));
            }
            return this;
        }

        public Map<K, V> map() {
            return hashMap();
        }

        public Map<K, V> hashMap() {
            Map<K, V> map = new HashMap<>(nodeList.size());
            nodeList.forEach(node -> map.put(node.getKey(), node.getValue()));
            return map;
        }

        public Map<K, V> hashTable() {
            Map<K, V> map = new Hashtable<>(nodeList.size());
            nodeList.forEach(node -> map.put(node.getKey(), node.getValue()));
            return map;
        }

        public Map<K, V> linkedHashMap() {
            Map<K, V> map = new LinkedHashMap<>(nodeList.size());
            nodeList.forEach(node -> map.put(node.getKey(), node.getValue()));
            return map;
        }

        public Map<K, V> treeMap() {
            Map<K, V> map = new TreeMap<>();
            nodeList.forEach(node -> map.put(node.getKey(), node.getValue()));
            return map;
        }
    }

    protected static class Node<K, V> {
        K key;
        V value;

        protected Node(K key, V value) {
            super();
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

}
