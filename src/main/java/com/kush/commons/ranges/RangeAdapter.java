package com.kush.commons.ranges;

public interface RangeAdapter<T, R> {

    Range<R> toRange(T value);

    T fromRange(Range<R> range);
}
