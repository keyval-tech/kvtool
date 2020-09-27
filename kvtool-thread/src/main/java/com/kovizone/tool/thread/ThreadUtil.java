package com.kovizone.tool.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ThreadUtil {

    private ThreadUtil() {
    }

    /**
     * 默认线程池名
     */
    private final static String DEFAULT_THREAD_POOL_NAME = "AsynchronousTaskPool";

    /**
     * 线程池缓存
     */
    private final static Map<String, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_CACHE = new HashMap<>();


    /**
     * 获取线程池
     * <p>线程池基本大小为1</p>
     * <p>线程池中允许的最大线程数为1</p>
     * <p>线程池中空闲线程等待工作的超时时间为100ms</p>
     *
     * @param poolName 线程池名
     * @return 线程池
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName) {
        return getThreadPoolExecutor(poolName, 1, 1, 100L);
    }

    /**
     * 获取线程池
     * <p>线程池基本大小等同于线程池中允许的最大线程数</p>
     * <p>线程池中空闲线程等待工作的超时时间为100ms</p>
     *
     * @param poolName        线程池名
     * @param maximumPoolSize 线程池中允许的最大线程数
     * @return 线程池
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName, int maximumPoolSize) {
        return getThreadPoolExecutor(poolName, maximumPoolSize, maximumPoolSize, 100L);
    }

    /**
     * 获取线程池
     * <p>线程池基本大小等同于线程池中允许的最大线程数</p>
     *
     * @param poolName        线程池名
     * @param maximumPoolSize 线程池中允许的最大线程数
     * @param keepAliveTime   线程池中空闲线程等待工作的超时时间
     * @return 线程池
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(String poolName, int maximumPoolSize, long keepAliveTime) {
        return getThreadPoolExecutor(poolName, maximumPoolSize, maximumPoolSize, keepAliveTime);
    }

    /**
     * 获取线程池
     *
     * @param poolName        线程池名
     * @param corePoolSize    线程池基本大小
     * @param maximumPoolSize 线程池中允许的最大线程数
     * @param keepAliveTime   线程池中空闲线程等待工作的超时时间（单位：毫秒）
     * @return 线程池
     */
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

    /**
     * 启动一个线程
     *
     * <p>使用该方法将共用一个预设默认线程池</p>
     *
     * @param task 线程任务
     */
    public static void start(Runnable task) {
        start(DEFAULT_THREAD_POOL_NAME, task);
    }

    /**
     * 启动一个线程
     *
     * @param poolName 线程池名
     * @param task     线程任务
     */
    public static void start(String poolName, Runnable task) {
        getThreadPoolExecutor(poolName).execute(task);
    }

    /**
     * 无抛出异常睡眠
     *
     * @param millis 单位：毫秒
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
