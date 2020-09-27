package com.kovizone.tool.core;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ObjectUtil {

    /**
     * 判断空
     *
     * @param arg 对象
     * @return 是否空
     */
    public static boolean isEmpty(Object arg) {
        return arg == null || StringUtil.isEmpty(arg.toString());
    }

    /**
     * 判断非空
     *
     * @param arg 对象
     * @return 是否非空
     */
    public static boolean isNotEmpty(Object arg) {
        return !isEmpty(arg);
    }

    /**
     * 关闭多个资源
     *
     * @param autoCloseables 需要关闭的资源
     */
    public static void close(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
