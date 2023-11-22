package com.nasida.core.pool;

import java.util.concurrent.*;

/**
 * @author yanasida
 * @date 2023/11/22 16:20
 */
public class ThreadPoolHelper {

    private static final ThreadPoolExecutor POOL_EXECUTOR;
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR;

    static {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
        SCHEDULED_EXECUTOR = executor;
        POOL_EXECUTOR = executor;
    }

    private ThreadPoolHelper() {
    }


    public static void execute(Runnable callable) {
        POOL_EXECUTOR.execute(callable);
    }

    public static void submit(Runnable callable) {
        POOL_EXECUTOR.submit(callable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return POOL_EXECUTOR.submit(callable);
    }

    public static void scheduled(Runnable command,
                                 long initialDelay,
                                 long period,
                                 TimeUnit unit) {
        SCHEDULED_EXECUTOR.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public static <V> Future<V> scheduled(Callable<V> callable,
                                          long delay, TimeUnit unit) {
        return SCHEDULED_EXECUTOR.schedule(callable, delay, unit);
    }

    public static boolean remove(Runnable runnable) {
        return POOL_EXECUTOR.remove(runnable);
    }

    public static <V> boolean cancel(Future<V> future) {
        if (future == null) {
            return true;
        }
        return future.cancel(true);
    }
}
