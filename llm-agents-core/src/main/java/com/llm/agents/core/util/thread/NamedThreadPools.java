
package com.llm.agents.core.util.thread;

import java.util.concurrent.*;

public class NamedThreadPools {

    public static ExecutorService newFixedThreadPool(String prefix) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        return newFixedThreadPool(nThreads, prefix);
    }


    public static ExecutorService newFixedThreadPool(int nThreads, String name) {
        return Executors.newFixedThreadPool(nThreads, new NamedThreadFactory(name));
    }


    public static ExecutorService newCachedThreadPool(String name) {
        return newCachedThreadPool(new NamedThreadFactory(name));
    }


    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory);
    }


    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, String name) {
        return newScheduledThreadPool(corePoolSize, new NamedThreadFactory(name));
    }


    public static ScheduledExecutorService newScheduledThreadPool(
            int corePoolSize, ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }
}
