package com.kovizone.tool.sugar;

import java.util.*;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class CollectionBuilder {

    public static <E> CollectionBuildHelper<E> add(E element) {
        return add(true, element);
    }

    public static <E> CollectionBuildHelper<E> add(boolean condition, E element) {
        if (condition) {
            return new CollectionBuildHelper<>(element);
        }
        return new CollectionBuildHelper<>();
    }

    public static class CollectionBuildHelper<E> {

        private List<E> elementList;

        protected CollectionBuildHelper() {
            super();
        }

        protected CollectionBuildHelper(E element) {
            super();
            elementList = new ArrayList<>();
            elementList.add(element);
        }

        public CollectionBuildHelper<E> add(E element) {
            return add(true, element);
        }

        public CollectionBuildHelper<E> add(boolean condition, E element) {
            if (condition) {
                elementList.add(element);
            }
            return this;
        }

        public List<E> list() {
            return arrayList();
        }

        public List<E> arrayList() {
            return new ArrayList<>(elementList);
        }

        public List<E> vector() {
            return new Vector<>(elementList);
        }

        public List<E> linkedList() {
            return new LinkedList<>(elementList);
        }

        public Set<E> set() {
            return hashSet();
        }

        public Set<E> hashSet() {
            return new HashSet<>(elementList);
        }

        public Set<E> treeSet() {
            return new TreeSet<>(elementList);
        }
    }
}
