package com.llm.agents.core.util.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    protected static final AtomicInteger POOL_COUNTER = new AtomicInteger(1);
    protected final AtomicInteger mThreadCounter;
    protected final String mPrefix;
    protected final boolean mDaemon;
    protected final ThreadGroup mGroup;

    public NamedThreadFactory() {
        this("pool-" + POOL_COUNTER.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        this.mThreadCounter = new AtomicInteger(1);
        this.mPrefix = prefix + "-thread-";
        this.mDaemon = daemon;
        SecurityManager s = System.getSecurityManager();
        this.mGroup = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = this.mPrefix + this.mThreadCounter.getAndIncrement();
        Thread ret = new Thread(this.mGroup, runnable, name, 0L);
        ret.setDaemon(this.mDaemon);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return this.mGroup;
    }
}
