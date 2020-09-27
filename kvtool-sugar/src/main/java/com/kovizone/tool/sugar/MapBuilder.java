package com.kovizone.tool.sugar;

import java.util.*;

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
        return new MapBuildHelper<K, V>(key, value);
    }

    /**
     * 生成排序Map构造辅助器，若Key类型实现了排序，则最终可生成treeMap
     *
     * @param key   Key实例
     * @param value Value实例
     * @param <K>   Key泛型
     * @param <V>   Value泛型
     * @return 排序Map构造辅助器
     */
    public static <K extends Comparable<K>, V> ComparableMapBuildHelper<K, V> put(K key, V value) {
        return new ComparableMapBuildHelper<K, V>(key, value);
    }

    public static class ComparableMapBuildHelper<K extends Comparable<K>, V> extends MapBuildHelper<K, V> {

        protected ComparableMapBuildHelper(K key, V value) {
            super(key, value);
        }

        /**
         * 添加元素
         *
         * @param key   Key实例
         * @param value Value实例
         * @return 排序Map构造辅助器
         */
        @Override
        public ComparableMapBuildHelper<K, V> put(K key, V value) {
            super.put(key, value);
            return this;
        }

        public Map<K, V> treeMap() {
            Map<K, V> map = new TreeMap<>();
            getNodeList().forEach(node -> map.put(node.getKey(), node.getValue()));
            return map;
        }
    }

    public static class MapBuildHelper<K, V> {

        private List<Node<K, V>> nodeList;

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
            nodeList.add(new Node<K, V>(key, value));
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

        protected List<Node<K, V>> getNodeList() {
            return nodeList;
        }

        protected void setNodeList(List<Node<K, V>> nodeList) {
            this.nodeList = nodeList;
        }
    }

    protected static class ComparableNode<K extends Comparable<K>, V> extends Node<K, V> {
        protected ComparableNode(K key, V value) {
            super(key, value);
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
