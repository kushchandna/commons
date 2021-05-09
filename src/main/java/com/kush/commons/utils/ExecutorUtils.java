package com.kush.commons.utils;

import java.util.concurrent.Executor;

public class ExecutorUtils {

    public static Executor newThreadExecutor() {
        return cmd -> new Thread(cmd).start();
    }

    public static Executor sameThreadExecutor() {
        return cmd -> cmd.run();
    }
}
