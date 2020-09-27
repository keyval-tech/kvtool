package com.kovizone.tool.core.function;

/**
 * 循环体方法
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
@FunctionalInterface
public interface ForEachInxBigFunction<T, U> {

    /**
     * 执行方法
     *
     * @param t 循环对象1
     * @param i 循环对象1索引
     * @param u 循环对象2
     * @param j 循环对象2索引
     * @return 返回false停止循环
     */
    boolean execute(T t, Integer i, U u, Integer j);
}
