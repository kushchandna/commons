package com.kush.commons.utils;

import java.util.Comparator;
import java.util.function.Function;

public class ObjectUtils {

    public static <T, R> R nullOrElse(T object, Function<T, R> function) {
        return object == null ? null : function.apply(object);
    }

    public static <T> boolean executeIfTrue(boolean value, Runnable task) {
        if (value) {
            task.run();
        }
        return value;
    }

    public static <T> T max(T o1, T o2, Comparator<T> comparator) {
        int comparision = comparator.compare(o1, o2);
        return comparision <= 0 ? o1 : o2;
    }

    public static <T> T min(T o1, T o2, Comparator<T> comparator) {
        int comparision = comparator.compare(o1, o2);
        return comparision >= 0 ? o1 : o2;
    }
}
