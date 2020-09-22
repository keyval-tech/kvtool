package com.kovizone.tool.core;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ObjectUtil {

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

    public static boolean isEmpty(Object arg) {
        return arg == null || StringUtil.isEmpty(arg.toString());
    }

    public static boolean isNotEmpty(Object arg) {
        return !isEmpty(arg);
    }
}
