package com.kovizone.tool.function;

/**
 * 循环体方法
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
@FunctionalInterface
public interface ForEachBigFunction<T, U> {

    /**
     * 执行方法
     *
     * @param t 循环对象1
     * @param u 循环对象2
     * @return 返回false停止循环
     */
    boolean execute(T t, U u);
}
