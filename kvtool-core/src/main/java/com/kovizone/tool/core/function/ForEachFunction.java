package com.kovizone.tool.core.function;

/**
 * 循环体方法
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
@FunctionalInterface
public interface ForEachFunction<T> {

    /**
     * 执行方法
     *
     * @param t 循环对象
     * @return 返回false停止循环
     */
    boolean execute(T t);
}
