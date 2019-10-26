package edu.cad.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.OptionalInt;

public class Utils {
    private Utils() {
        // utility classes should not be instantiated.
    }

    public static boolean isNumber(String value) {
        return NumberUtils.isParsable(value);
    }

    public static OptionalInt parseInteger(String value) {
        return NumberUtils.isParsable(value) ? OptionalInt.of(Integer.valueOf(value)) : OptionalInt.empty();
    }
}

