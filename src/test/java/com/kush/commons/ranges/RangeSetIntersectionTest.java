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
public class RangeSetIntersectionTest {

    @Parameters
    public static Object[][] getTestScenarios() {
        return new Object[][] {
                assertThatIntersectionOf(rangeSet("[30 - 50]"), with("[30 - 50]"), is("[30 - 50]")),
                assertThatIntersectionOf(rangeSet("[10 - 20]"), with("[30 - 40]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 50]"), with("[50 - 70]"), is("[50 - 50]")),

                assertThatIntersectionOf(rangeSet("[30 - 50]"), with("(30 - 50)"), is("(30 - 50)")),
                assertThatIntersectionOf(rangeSet("[30 - 50]"), with("(50 - 70]"), is(emptyRangeSet())),

                assertThatIntersectionOf(rangeSet("[10 - 70]"), with("[30 - 50]"), is("[30 - 50]")),
                assertThatIntersectionOf(rangeSet("[10 - 10]", "[20 - 20]"), with("[10 - 10]"), is("[10 - 10]")),

                assertThatIntersectionOf(rangeSet("[10 - 30]", "[50 - 70]"), with("[20 - 60]"), is("[20 - 30]", "[50 - 60]")),
                assertThatIntersectionOf(rangeSet("[10 - 30]", "[50 - 70]"), with("[55 - 65)"), is("[55 - 65)")),

                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[10 - 20]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[20 - 30)"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("(30 - 40]"), with("[20 - 30]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[20 - 30]"), is("[30 - 30]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[25 - 35]"), is("[30 - 35]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[25 - 35)"), is("[30 - 35)")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[30 - 30]"), is("[30 - 30]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[30 - 40]"), is("[30 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[30 - 35]"), is("[30 - 35]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[32 - 37]"), is("[32 - 37]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[35 - 40]"), is("[35 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[35 - 45]"), is("[35 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("(35 - 45]"), is("(35 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[40 - 40]"), is("[40 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[40 - 50]"), is("[40 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("(40 - 50]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 40)"), with("(40 - 50]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 40)"), with("[40 - 50]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("[50 - 60]"), is(emptyRangeSet())),

                assertThatIntersectionOf(rangeSet("[30 - 40]", "[70 - 80]"), with("[50 - 60]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("[30 - 40]", "[70 - 80]"), with("[35 - 40]"), is("[35 - 40]")),
                assertThatIntersectionOf(rangeSet("[30 - 40]", "[70 - 80]"), with("[35 - 75]"), is("[35 - 40]", "[70 - 75]")),

                assertThatIntersectionOf(rangeSet("[30 - 40]"), with("(* - *)"), is("[30 - 40]")),
                assertThatIntersectionOf(rangeSet("(* - *)"), with("[30 - 40]"), is("[30 - 40]")),
                assertThatIntersectionOf(rangeSet("(* - 30)"), with("[30 - 40]"), is(emptyRangeSet())),
                assertThatIntersectionOf(rangeSet("(* - 30]"), with("[30 - 40]"), is("[30 - 30]")),
                assertThatIntersectionOf(rangeSet("(* - 35)"), with("[30 - 40]"), is("[30 - 35)")),
                assertThatIntersectionOf(rangeSet("(30 - *)"), with("[30 - 40]"), is("(30 - 40]")),

        };
    }

    private final RangeSet<Integer> rangeSet;
    private final RangeSet<Integer> intersecting;
    private final RangeSet<Integer> expectedIntersection;

    public RangeSetIntersectionTest(RangeSet<Integer> rangeSet, RangeSet<Integer> intersecting,
            RangeSet<Integer> expectedIntersection) {
        this.rangeSet = rangeSet;
        this.intersecting = intersecting;
        this.expectedIntersection = expectedIntersection;
    }

    @Test
    public void runTest() throws Exception {
        RangeSet<Integer> actualIntersection = rangeSet.intersect(intersecting);
        assertThat(actualIntersection, Matchers.is(equalTo(expectedIntersection)));

        actualIntersection = intersecting.intersect(rangeSet);
        assertThat(actualIntersection, Matchers.is(equalTo(expectedIntersection)));
    }

    private static String[] emptyRangeSet() {
        return new String[0];
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

    private static Object[] assertThatIntersectionOf(RangeSet<Integer> rangeSet, RangeSet<Integer> intersecting,
            RangeSet<Integer> expectedIntersection) {
        return new Object[] {
                rangeSet,
                intersecting,
                expectedIntersection
        };
    }
}
