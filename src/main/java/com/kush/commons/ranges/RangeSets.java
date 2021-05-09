package com.kush.commons.ranges;

import static java.util.Comparator.naturalOrder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RangeSets {

    private static final boolean IS_NULL_HIGH;

    static {
        boolean isNullHigh = false;
        try {
            isNullHigh = readIsNullHighProperty(isNullHigh);
        } catch (Exception e) {
        }
        IS_NULL_HIGH = isNullHigh;
    }

    private static boolean readIsNullHighProperty(boolean defaultIsNullHigh) throws Exception {
        return Boolean.parseBoolean(System.getProperty("isNullHigh", String.valueOf(defaultIsNullHigh)));
    }

    public static <T> RangeSet<T> on(Comparator<T> comparator, List<Range<T>> ranges) {
        RangeOperator<T> rangeOperator = createRangeOperator(comparator);
        RangeSet<T> rangeSet = RangeSet.empty(rangeOperator);
        for (Range<T> range : ranges) {
            rangeSet = rangeSet.union(RangeSet.withRange(rangeOperator, range));
        }
        return rangeSet;
    }

    public static <T extends Comparable<T>> RangeSet<T> on(List<Range<T>> ranges) {
        return RangeSets.on(naturalOrder(), ranges);
    }

    public static <T extends Comparable<T>> RangeSet<T> on(Range<T> range) {
        return on(Arrays.asList(range));
    }

    public static <T extends Comparable<T>> RangeSet<T> empty() {
        RangeOperator<T> rangeOperator = createRangeOperator(Comparator.<T>naturalOrder());
        return RangeSet.empty(rangeOperator);
    }

    private static <T> RangeOperator<T> createRangeOperator(Comparator<T> comparator) {
        return new RangeOperator<>(comparator, IS_NULL_HIGH);
    }
}
