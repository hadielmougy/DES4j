package io.github.des4j;

public class TimeRange {
    public final int start;
    public final int end;

    public TimeRange(int start, int end) {
        this.start = Util.validateIsPositive(start);
        this.end   = Util.validateIsPositive(end);
    }

    public static TimeRange of(int start, int end) {
        return new TimeRange(start, end);
    }

    public static TimeRange of(int fixed) {
        return new TimeRange(fixed, fixed);
    }
}
