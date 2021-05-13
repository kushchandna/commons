package com.kush.commons.utils;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {

    public static <T> Set<T> nonNull(Set<T> set) {
        return set == null ? Collections.emptySet() : set;
    }

    public static <K, V> Map<K, Set<V>> singletonMultiValueMap(K key, V value) {
        return singletonMap(key, singleton(value));
    }

    @SafeVarargs
    public static <T> Set<T> newHashSet(T... objects) {
        return new HashSet<>(Arrays.asList(objects));
    }
}
