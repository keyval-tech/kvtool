package com.kovizone.tool.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ThreadUtil {

    private final static String DEFAULT_THREAD_POOL_NAME = "AsynchronousTaskPool";

    private final static Map<String, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_CACHE = new HashMap<>();

    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName) {
        return getThreadPoolExecutor(poolName, 1, 1, 100L);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName, int maximumPoolSize) {
        return getThreadPoolExecutor(poolName, maximumPoolSize, maximumPoolSize, 100L);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName, int maximumPoolSize, long keepAliveTime) {
        return getThreadPoolExecutor(poolName, maximumPoolSize, maximumPoolSize, keepAliveTime);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        String key = poolName + "-" + keepAliveTime + "-" + maximumPoolSize + "-" + corePoolSize;
        ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_EXECUTOR_CACHE.get(key);
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown() || threadPoolExecutor.isTerminated() || threadPoolExecutor.isTerminating()) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    new ThreadFactoryBuilder().setNameFormat(poolName + "-%d").build());
            THREAD_POOL_EXECUTOR_CACHE.put(key, threadPoolExecutor);
        }
        return threadPoolExecutor;
    }

    public static void start(Runnable task) {
        start(DEFAULT_THREAD_POOL_NAME, task);
    }

    public static void start(String poolName, Runnable task) {
        getThreadPoolExecutor(poolName).execute(task);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
