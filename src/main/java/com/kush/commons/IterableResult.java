package com.kush.commons;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public interface IterableResult<T> extends Iterable<T> {

    Stream<T> stream();

    default long count() {
        return -1L;
    }

    default boolean isCountKnown() {
        return count() >= 0L;
    }

    default Stream<T> parallelStream() {
        return stream();
    }

    @Override
    default Iterator<T> iterator() {
        return stream().iterator();
    }

    static <T> IterableResult<T> empty() {
        return new DefaultIterableResult<>(Stream.empty(), 0L);
    }

    static <T> IterableResult<T> onStream(Stream<T> stream) {
        return () -> stream;
    }

    static <T> IterableResult<T> onCollections(Collection<Collection<T>> collections) {
        long count = 0L;
        for (Collection<T> collection : collections) {
            count += collection.size();
        }
        long finalCount = count;
        return new IterableResult<T>() {

            @Override
            public Stream<T> stream() {
                return collections.stream().flatMap(Collection::stream);
            }

            @Override
            public Stream<T> parallelStream() {
                return collections.parallelStream().flatMap(Collection::parallelStream);
            }

            @Override
            public long count() {
                return finalCount;
            }
        };
    }

    static <T> IterableResult<T> onCollection(Collection<T> collection) {
        return new DefaultIterableResult<T>(collection.stream(), collection.size()) {

            @Override
            public Stream<T> parallelStream() {
                return collection.parallelStream();
            }

            @Override
            public Iterator<T> iterator() {
                return collection.iterator();
            }
        };
    }

    @SafeVarargs
    static <T> IterableResult<T> onValues(T... values) {
        return new DefaultIterableResult<>(Arrays.stream(values), values.length);
    }

    static <T> IterableResult<T> concat(Collection<IterableResult<T>> results) {
        long count = 0L;
        for (IterableResult<T> result : results) {
            if (!result.isCountKnown()) {
                count = -1L;
                break;
            }
            count += result.count();
        }
        return new DefaultIterableResult<>(results.stream().flatMap(IterableResult::stream), count);
    }

    static class DefaultIterableResult<T> implements IterableResult<T> {

        private final Stream<T> stream;
        private final long count;

        private DefaultIterableResult(Stream<T> stream, long count) {
            this.stream = stream;
            this.count = count;
        }

        @Override
        public Stream<T> stream() {
            return stream;
        }

        @Override
        public long count() {
            return count;
        }
    }
}
