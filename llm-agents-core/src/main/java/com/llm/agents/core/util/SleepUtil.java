package com.llm.agents.core.util;

import java.util.concurrent.Callable;

public class SleepUtil {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(long millis, Callable<Void> onInterrupt) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            try {
                onInterrupt.call();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
