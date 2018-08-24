package edu.cad.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class Utils {
    private Utils() {
        // utility classes should not be instantiated.
    }

    public static boolean isNumber(String value) {
        return NumberUtils.isParsable(value);
    }
}
