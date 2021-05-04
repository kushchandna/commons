package com.kush.commons.ranges;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RangeSetUnionTest {

    @Parameters
    public static Object[][] getTestScenarios() {
        return new Object[][] {
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[10 - 20]"), is("[10 - 20]", "[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[20 - 30)"), is("[20 - 40]")),
                assertThatUnionOf(rangeSet("(30 - 40]"), with("[20 - 30]"), is("[20 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[20 - 30]"), is("[20 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[25 - 35]"), is("[25 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[25 - 35)"), is("[25 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[30 - 30]"), is("[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[30 - 40]"), is("[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[30 - 35]"), is("[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[32 - 37]"), is("[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[35 - 40]"), is("[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[35 - 45]"), is("[30 - 45]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("(35 - 45]"), is("[30 - 45]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[40 - 40]"), is("[30 - 40]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[40 - 50]"), is("[30 - 50]")),

                assertThatUnionOf(rangeSet("[30 - 40]"), with("(40 - 50]"), is("[30 - 50]")),
                assertThatUnionOf(rangeSet("[30 - 40)"), with("(40 - 50]"), is("[30 - 40)", "(40 - 50]")),
                assertThatUnionOf(rangeSet("[30 - 40)"), with("[40 - 50]"), is("[30 - 50]")),
                assertThatUnionOf(rangeSet("[30 - 40]"), with("[50 - 60]"), is("[30 - 40]", "[50 - 60]")),

                assertThatUnionOf(rangeSet("[30 - 40]", "[70 - 80]"), with("[50 - 60]"), is("[30 - 40]", "[50 - 60]", "[70 - 80]")),
                assertThatUnionOf(rangeSet("[30 - 40]", "[70 - 80]"), with("[35 - 40]"), is("[30 - 40]", "[70 - 80]")),
                assertThatUnionOf(rangeSet("[30 - 40]", "[70 - 80]"), with("[35 - 75]"), is("[30 - 80]")),

                assertThatUnionOf(rangeSet("[30 - 40]"), with("(* - *)"), is("(* - *)")),
                assertThatUnionOf(rangeSet("(* - *)"), with("[30 - 40]"), is("(* - *)")),
                assertThatUnionOf(rangeSet("(* - 30)"), with("[30 - 40]"), is("(* - 40]")),
                assertThatUnionOf(rangeSet("(* - 30]"), with("[30 - 40]"), is("(* - 40]")),
                assertThatUnionOf(rangeSet("(* - 35)"), with("[30 - 40]"), is("(* - 40]")),
                assertThatUnionOf(rangeSet("(30 - *)"), with("[30 - 40]"), is("[30 - *)")),
        };
    }


    private final RangeSet<Integer> rangeSet;
    private final RangeSet<Integer> unioning;
    private final RangeSet<Integer> expectedUnion;

    public RangeSetUnionTest(RangeSet<Integer> rangeSet, RangeSet<Integer> unioning,
            RangeSet<Integer> expectedUnion) {
        this.rangeSet = rangeSet;
        this.unioning = unioning;
        this.expectedUnion = expectedUnion;
    }

    @Test
    public void runTest() throws Exception {
        RangeSet<Integer> actualUnion = rangeSet.union(unioning);
        assertThat(actualUnion, Matchers.is(equalTo(expectedUnion)));

        actualUnion = unioning.union(rangeSet);
        assertThat(actualUnion, Matchers.is(equalTo(expectedUnion)));
    }

    private static RangeSet<Integer> is(String... ranges) {
        return rangeSet(ranges);
    }

    private static RangeSet<Integer> with(String... ranges) {
        return rangeSet(ranges);
    }

    private static RangeSet<Integer> rangeSet(String... ranges) {
        return RangeSets.on(Arrays.stream(ranges)
            .map(StringRangeAdapter::parseInt)
            .collect(Collectors.toList()));
    }

    private static Object[] assertThatUnionOf(RangeSet<Integer> rangeSet, RangeSet<Integer> intersecting,
            RangeSet<Integer> expectedIntersection) {
        return new Object[] {
                rangeSet,
                intersecting,
                expectedIntersection
        };
    }
}
