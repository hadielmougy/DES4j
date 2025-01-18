package io.github.des4j;

public class Util {

    public static int validateIsPositive(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        return num;
    }

    public static int validateNotNegative(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must not be negative");
        }
        return num;
    }
}
